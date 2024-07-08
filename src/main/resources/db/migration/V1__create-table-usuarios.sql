CREATE TABLE usuarios (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre varchar(255) NOT NULL,
    correo_electronico varchar(100) NOT NULL UNIQUE,
    contrasena varchar(100) NOT NULL
);
