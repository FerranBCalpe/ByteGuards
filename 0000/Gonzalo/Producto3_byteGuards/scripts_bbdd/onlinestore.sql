DROP DATABASE IF EXISTS OnlineStore_ByteGuards;
CREATE DATABASE IF NOT EXISTS OnlineStore_ByteGuards DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE onlineStore_byteGuards;

CREATE TABLE IF NOT EXISTS articulo (
	idArticulo int NOT NULL AUTO_INCREMENT,
	descripcion varchar(40) NOT NULL,
	precio float NOT NULL,
	gastosEnvio float NOT NULL,
	tiempoPreparacion int NOT NULL,
	PRIMARY KEY (idArticulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS cliente (
	idCliente int NOT NULL AUTO_INCREMENT,
    nombre varchar(40) NOT NULL,
    domicilio varchar(40) NOT NULL,
    nif varchar(40) NOT NULL,
    email varchar(40) NOT NULL,
    PRIMARY KEY (idCliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS clienteEstandar (
	idCliente int NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (idCliente),
    FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS clientePremium (
	idCliente int NOT NULL AUTO_INCREMENT,
    descuento float NOT NULL,
    PRIMARY KEY (idCliente),
    FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS pedido (
	idPedido int NOT NULL AUTO_INCREMENT,
    numPedido int NOT NULL,
    cantidad int NOT NULL,
    fecha date NOT NULL,
    idCliente int NOT NULL,
    idArticulo int NOT NULL,
    PRIMARY KEY (idPedido),
    FOREIGN KEY (idCliente) REFERENCES cliente (idCliente),
    FOREIGN KEY (idArticulo) REFERENCES articulo (idArticulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;