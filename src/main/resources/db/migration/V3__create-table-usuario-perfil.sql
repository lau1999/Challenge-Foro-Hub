CREATE TABLE usuario_perfil (
    usuario_id bigint NOT NULL,
    perfil_id bigint NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (perfil_id) REFERENCES perfil(id)
);
