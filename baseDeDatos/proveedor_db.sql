
CREATE DATABASE IF NOT EXISTS proveedor_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE proveedor_db;

CREATE TABLE IF NOT EXISTS proveedor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    razon_social VARCHAR(100) NOT NULL,
    nombre_comercial VARCHAR(100) NOT NULL,
    ruc CHAR(11) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo_electronico VARCHAR(100),
    sitio_web VARCHAR(200),
    direccion_fisica VARCHAR(150),
    pais VARCHAR(100),
    representante_legal VARCHAR(100),
    dni_representante CHAR(8),
    tipo_proveedor ENUM('Nacional', 'Internacional'),
    categoria ENUM('Servicios', 'Productos', 'Tecnología', 'Otros'),
    facturacion_anual DECIMAL(15, 2),
    fecha_registro DATETIME,
    ultima_actualizacion DATETIME,
    estado BOOLEAN
);
