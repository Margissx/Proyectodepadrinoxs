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



-- Crear la tabla 'cartas'
CREATE TABLE cartas (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        id_estado INT NOT NULL,
                        id_usuarios INT NOT NULL,
                        hash text null,
                        fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (id_estado) REFERENCES estado_cartas(id),
                        FOREIGN KEY (id_usuarios) REFERENCES usuario(id)
);

-- Insertar datos en la tabla 'rol'
INSERT INTO rol (nombre) VALUES ('Administrador');
INSERT INTO rol (nombre) VALUES ('Tecnico');
INSERT INTO rol (nombre) VALUES ('Padrino');
INSERT INTO rol (nombre) VALUES ('Infante');

-- Insertar datos en la tabla 'estado_cartas'
INSERT INTO estado_cartas (estado) VALUES ('Enviado');
INSERT INTO estado_cartas (estado) VALUES ('Pendiente de enviado');
INSERT INTO estado_cartas (estado) VALUES ('Leido');

-- Insertar datos en la tabla 'Usuarios'
INSERT INTO usuario (id_rol, nombre, correo, password) VALUES (1,'Admin', 'ppadrinodropbox@gmail.com', 'Admin123');
INSERT INTO usuario (id_rol, nombre, correo, password) VALUES (2,'Tecnico', 'ppadrinodropbox@gmail.com', 'Tecnico123');
INSERT INTO usuario (id_rol, nombre, correo, password) VALUES (3,'Padrino', 'ppadrinodropbox@gmail.com', 'Padrino123');
INSERT INTO usuario (id_rol, nombre, correo, password) VALUES (3,'Infante1', 'ppadrinodropbox@gmail.com', 'Infante123');