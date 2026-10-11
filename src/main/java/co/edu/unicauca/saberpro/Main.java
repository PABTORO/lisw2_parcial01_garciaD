package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.*;
import co.edu.unicauca.saberpro.repository.*;
import co.edu.unicauca.saberpro.service.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BANCO DE PREGUNTAS SABER PRO ===");

        // Configuración de dependencias (DIP)
        IPreguntaRepository repository = new PreguntaRepositoryImpl();
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());
        pipeline.addFiltro(new FiltroJustificacion()); // Punto 2 (Estudiante 09)

        // CORRECCIÓN 4: se inyecta el pipeline real en lugar de null
        IPreguntaService service = new PreguntaServiceImpl(repository, pipeline);

        // Pregunta de prueba
        Pregunta p1 = new Pregunta(
                1,
                "¿Cuál es el objetivo principal del patrón Pipes and Filters?",
                Arrays.asList("A. Dividir proceso en etapas", "B. Crear BD", "C. Usar MVC", "D. Ninguna"),
                "A. Dividir proceso en etapas",
                "Borrador",
                "El patrón separa el proceso en etapas independientes y reutilizables."
        );

        // Pregunta con justificación muy corta (<15): debe ser rechazada por FiltroJustificacion
        Pregunta p2 = new Pregunta(
                2,
                "¿Qué principio SOLID promueve depender de abstracciones?",
                Arrays.asList("A. SRP", "B. DIP", "C. OCP", "D. LSP"),
                "B. DIP",
                "Borrador",
                "muy corta"
        );

        boolean exito = service.registrarPregunta(p1);
        if (exito) {
            System.out.println(" Pregunta registrada con éxito. Total en banco: " + service.obtenerTodas().size());
        } else {
            System.out.println(" No se pudo registrar la pregunta debido a fallos en el pipeline.");
        }

        // Demostración del rechazo por justificación insuficiente
        boolean exito2 = service.registrarPregunta(p2);
        System.out.println(exito2
                ? " Pregunta 2 registrada."
                : " Pregunta 2 rechazada (justificación obligatoria, mínimo 15 caracteres).");
        System.out.println(" Total final en banco: " + service.obtenerTodas().size());
    }
}
