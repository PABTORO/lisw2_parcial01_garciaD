package co.edu.unicauca.saberpro.pipeline;

import co.edu.unicauca.saberpro.model.Pregunta;

public class FiltroOpciones implements IFiltroPregunta {
    @Override
    public boolean procesar(Pregunta pregunta) {
        if (pregunta == null || pregunta.getOpciones() == null) {
            return false;
        }
        // CORRECCIÓN 2: lógica corregida, estaba invertida con 4 rechazaba
        // y con diferentes valores era true, debe ser exactamente 4 opciones para retornar true
        return pregunta.getOpciones().size() == 4;
    }
}
