CREATE TABLE topico (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo varchar(200) NOT NULL UNIQUE,
    mensaje text NOT NULL,
    fecha_creacion timestamp DEFAULT CURRENT_TIMESTAMP,
    status boolean NOT NULL DEFAULT true,
    autor_id bigint NOT NULL,
    curso_id bigint NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);
