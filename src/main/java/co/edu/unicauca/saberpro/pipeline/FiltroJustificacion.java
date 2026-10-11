package co.edu.unicauca.saberpro.pipeline;

import co.edu.unicauca.saberpro.model.Pregunta;

//la justificación es obligatoria.
//Rechaza la pregunta si la justificación es nula o tiene menos de 15 caracteres
public class FiltroJustificacion implements IFiltroPregunta {

    public static final int LONGITUD_MINIMA = 15;

    @Override
    public boolean procesar(Pregunta pregunta) {
        if (pregunta == null || pregunta.getJustificacion() == null) {
            return false;
        }
        return pregunta.getJustificacion().trim().length() >= LONGITUD_MINIMA;
    }
}
