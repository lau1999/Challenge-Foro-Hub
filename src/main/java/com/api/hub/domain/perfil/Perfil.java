package com.api.hub.domain.perfil;

import com.api.hub.domain.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Perfil")
@Table(name = "perfil")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(mappedBy = "perfiles", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    public Perfil(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Perfil{nombre='" + nombre + ", usuarios=" + usuarios + "}";
    }
}
