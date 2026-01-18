package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "usuario_nombre", nullable = false, length = 150)
    private String usuarioNombre;

    @Column(name = "usuario_clave", nullable = false, length = 150)
    private String usuarioClave;

    @Column(name = "usuario_estado")
    private Character usuarioEstado;

    @PrePersist
    protected void onCreate() {
        if (usuarioEstado == null) {
            usuarioEstado = 'A';
        }
    }
}
