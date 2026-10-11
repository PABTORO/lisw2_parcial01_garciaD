package co.edu.unicauca.saberpro;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.*;
import co.edu.unicauca.saberpro.repository.PreguntaRepositoryImpl;
import co.edu.unicauca.saberpro.service.IPreguntaService;
import co.edu.unicauca.saberpro.service.PreguntaServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class FiltroJustificacionTest {

    private final FiltroJustificacion filtro = new FiltroJustificacion();

    private Pregunta conJustificacion(String justificacion) {
        return new Pregunta(1, "Enunciado válido",
                Arrays.asList("A", "B", "C", "D"), "A", "Borrador", justificacion);
    }

    @Test
    void rechazaJustificacionNula() {
        assertFalse(filtro.procesar(conJustificacion(null)));
    }

    @Test
    void rechazaJustificacionConMenosDe15Caracteres() {
        assertFalse(filtro.procesar(conJustificacion("corta")));
        assertFalse(filtro.procesar(conJustificacion("12345678901234"))); // 14
    }

    @Test
    void rechazaJustificacionSoloEspacios() {
        assertFalse(filtro.procesar(conJustificacion("                    ")));
    }

    @Test
    void aceptaJustificacionDeExactamente15Caracteres() {
        assertTrue(filtro.procesar(conJustificacion("123456789012345")));
    }

    @Test
    void aceptaJustificacionLarga() {
        assertTrue(filtro.procesar(conJustificacion("Porque separa responsabilidades en etapas.")));
    }

    @Test
    void rechazaPreguntaNula() {
        assertFalse(filtro.procesar(null));
    }

    @Test
    void pipelineCompletoRechazaPreguntaSinJustificacion() {
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());
        pipeline.addFiltro(new FiltroJustificacion());
        IPreguntaService service = new PreguntaServiceImpl(new PreguntaRepositoryImpl(), pipeline);

        assertFalse(service.registrarPregunta(conJustificacion("corta")));
        assertTrue(service.obtenerTodas().isEmpty());

        assertTrue(service.registrarPregunta(conJustificacion("Justificación suficientemente larga")));
        assertEquals(1, service.obtenerTodas().size());
    }
}
