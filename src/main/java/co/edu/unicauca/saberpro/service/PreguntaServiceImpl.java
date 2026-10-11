package co.edu.unicauca.saberpro.service;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.ValidadorPipeline;
import co.edu.unicauca.saberpro.repository.IPreguntaRepository;
import java.util.List;

public class PreguntaServiceImpl implements IPreguntaService {
    private final IPreguntaRepository repository;
    private final ValidadorPipeline pipeline;

    public PreguntaServiceImpl(IPreguntaRepository repository, ValidadorPipeline pipeline) {
        this.repository = repository;
        this.pipeline = pipeline;
    }

    // CORRECCIÓN 3: El método se llamaba "guardarPregunta", por lo que no implementaba
    // el contrato de IPreguntaService (registrarPregunta)
    @Override
    public boolean registrarPregunta(Pregunta pregunta) {
        if (pipeline.ejecutar(pregunta)) {
            return repository.guardar(pregunta);
        }
        return false;
    }

    @Override
    public List<Pregunta> obtenerTodas() {
        return repository.listar();
    }
}
