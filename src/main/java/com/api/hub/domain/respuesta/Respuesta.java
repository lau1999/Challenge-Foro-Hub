package com.api.hub.domain.respuesta;


import com.api.hub.domain.topico.Topico;
import com.api.hub.domain.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuesta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mensaje;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id", nullable = false)
    //@JsonBackReference
    private Topico topico;

    @Column(name = "fechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    private String solucion;


    public Respuesta(DatosRegistroRespuesta datosEntradaRespuestas) {

        this.mensaje = datosEntradaRespuestas.mensaje();
        this.topico = datosEntradaRespuestas.topico();
        this.autor = datosEntradaRespuestas.autor();
        this.solucion = datosEntradaRespuestas.solucion();

    }

    public void actualizarDatos(DatosActualizarRespuesta datosActualizarRespuesta) {
        if(datosActualizarRespuesta.mensaje() != null) {
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
        if(datosActualizarRespuesta.solucion() != null) {
            this.solucion = datosActualizarRespuesta.solucion() ;
        }
    }


}
