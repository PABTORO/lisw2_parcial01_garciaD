package co.edu.unicauca.saberpro.model;

import java.util.List;
import java.util.ArrayList;

public class Pregunta {
    private final int id;
    private final String enunciado;
    private final List<String> opciones; // Debe contener 4 opciones
    private final String respuestaCorrecta;
    private String estado; // "Borrador", "Publicada"
    private final String justificacion; // Punto 2 (Est. 09): obligatoria, mínimo 15 caracteres

    public Pregunta(int id, String enunciado, List<String> opciones, String respuestaCorrecta,
                    String estado, String justificacion) {
        this.id = id;
        this.enunciado = enunciado;
        // CORRECCIÓN 1: se garantiza la inicialización de la lista Antes se 
        // asignaba la lista directamente (this.opciones = opciones)
        //si no se enviaban opciones dejaba null
        this.opciones = (opciones == null) ? new ArrayList<>() : new ArrayList<>(opciones);
        this.respuestaCorrecta = respuestaCorrecta;
        this.estado = estado;
        this.justificacion = justificacion;
    }

    // Constructor original (compatibilidad): justificación nula
    public Pregunta(int id, String enunciado, List<String> opciones, String respuestaCorrecta, String estado) {
        this(id, enunciado, opciones, respuestaCorrecta, estado, null);
    }

    public int getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public List<String> getOpciones() { return opciones; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getJustificacion() { return justificacion; }
}
