package co.edu.unicauca.saberpro;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.*;
import co.edu.unicauca.saberpro.repository.*;
import co.edu.unicauca.saberpro.service.*;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreguntaServiceTest {

    private IPreguntaService service;

    @BeforeEach
    void setUp() {
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());
        service = new PreguntaServiceImpl(new PreguntaRepositoryImpl(), pipeline);
    }

    private Pregunta valida() {
        return new Pregunta(1, "¿Qué es SOLID?",
                Arrays.asList("A. x", "B. y", "C. z", "D. w"), "A. x", "Borrador");
    }

    @Test
    void registraPreguntaValida() {
        assertTrue(service.registrarPregunta(valida()));
        assertEquals(1, service.obtenerTodas().size());
    }

    @Test
    void rechazaEnunciadoVacio() {
        Pregunta p = new Pregunta(2, "   ",
                Arrays.asList("A", "B", "C", "D"), "A", "Borrador");
        assertFalse(service.registrarPregunta(p));
        assertTrue(service.obtenerTodas().isEmpty());
    }

    @Test
    void rechazaOpcionesDistintasDeCuatro() {
        Pregunta p = new Pregunta(3, "Enunciado",
                Arrays.asList("A", "B", "C"), "A", "Borrador");
        assertFalse(service.registrarPregunta(p));
    }

    @Test
    void opcionesNulasSeInicializanVacias() {
        Pregunta p = new Pregunta(4, "Enunciado", null, "A", "Borrador");
        assertNotNull(p.getOpciones());
        assertFalse(service.registrarPregunta(p));
    }
}
