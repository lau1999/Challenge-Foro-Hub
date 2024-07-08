CREATE TABLE respuesta (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mensaje text NOT NULL,
    topico_id bigint NOT NULL,
    fechaCreacion timestamp DEFAULT CURRENT_TIMESTAMP,
    autor_id bigint NOT NULL,
    solucion boolean DEFAULT false,
    CONSTRAINT fk_respuesta_topico_id FOREIGN KEY (topico_id) REFERENCES topico(id),
    CONSTRAINT fk_respuesta_autor_id FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
