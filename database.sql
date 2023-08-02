-- Crear la base de datos 'javaproject'
CREATE DATABASE javaproject;

-- Usar la base de datos 'javaproject'
USE javaproject;

-- Crear la tabla 'rol'
CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos en la tabla 'rol'
INSERT INTO rol (nombre) VALUES ('Administrador');
INSERT INTO rol (nombre) VALUES ('Tecnico');
INSERT INTO rol (nombre) VALUES ('Padrino');
INSERT INTO rol (nombre) VALUES ('Infante');

-- Crear la tabla 'usuario'
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_rol) REFERENCES rol(id)
);

-- Crear la tabla 'estado_cartas'
CREATE TABLE estado_cartas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos en la tabla 'estado_cartas'
INSERT INTO estado_cartas (estado) VALUES ('Enviado');
INSERT INTO estado_cartas (estado) VALUES ('Leido');

-- Crear la tabla 'cartas'
CREATE TABLE cartas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_estado INT NOT NULL,
    id_usuarios INT NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_estado) REFERENCES estado_cartas(id),
    FOREIGN KEY (id_usuarios) REFERENCES usuario(id)
);