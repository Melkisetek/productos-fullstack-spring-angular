-- ============================================================
-- Sistema de Gestión de Inventarios
-- Script de inicialización de base de datos (MariaDB / MySQL)
--
-- Uso:
--   mariadb -u root -p < database/seed.sql
--
-- Este script es idempotente: puede ejecutarse varias veces sin
-- duplicar la base de datos, las tablas ni el usuario demo.
-- ============================================================

CREATE DATABASE IF NOT EXISTS gestion_db_api
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestion_db_api;

-- ------------------------------------------------------------
-- Tabla de usuarios (autenticación con Spring Security + JWT)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    usuario  VARCHAR(50)  NOT NULL,
    password VARCHAR(60)  NOT NULL,
    rol      VARCHAR(60)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_usuario (usuario),
    UNIQUE KEY uk_usuarios_password (password)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Tabla de productos (CRUD)
-- Hibernate también puede crearla con ddl-auto=update, pero se
-- incluye aquí para que el script sea autosuficiente.
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS producto (
    id     INT          NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NULL,
    precio DOUBLE       NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Usuario demo
--   Usuario:    user1
--   Contraseña: admin123
--   Rol:        ADMIN
--
-- La contraseña está almacenada como hash BCrypt (60 caracteres).
-- Nunca se guarda la contraseña en texto plano.
--
-- Para generar un hash nuevo:
--   1. Edita la contraseña en GenerarPassword.java.
--   2. Ejecuta la clase desde tu IDE o con Maven.
--   3. Reemplaza el hash de este INSERT.
-- ------------------------------------------------------------
INSERT INTO usuarios (usuario, password, rol)
VALUES (
    'user1',
    '$2a$10$QxbdIlU3zvZGTltHRcFBWesrFy87ieSF53P01I/bOcnnUfnh/Gtsa',
    'ADMIN'
)
ON DUPLICATE KEY UPDATE
    password = '$2a$10$QxbdIlU3zvZGTltHRcFBWesrFy87ieSF53P01I/bOcnnUfnh/Gtsa',
    rol = 'ADMIN';
