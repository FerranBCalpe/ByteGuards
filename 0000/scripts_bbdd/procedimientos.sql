DELIMITER //
CREATE PROCEDURE obtenerClientesEstandar()
BEGIN
    SELECT *
    FROM cliente
    INNER JOIN clienteEstandar ON cliente.idCliente = clienteEstandar.idCliente;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE obtenerClientesPremium()
BEGIN
    SELECT *
    FROM cliente
    INNER JOIN clientePremium ON cliente.idCliente = clientePremium.idCliente;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE insertarClienteEstandar(
    IN p_nombre VARCHAR(40),
    IN p_domicilio VARCHAR(40),
    IN p_nif VARCHAR(40),
    IN p_email VARCHAR(40)
)
BEGIN
    -- Insertar en la tabla cliente y obtener el id generado
    INSERT INTO cliente (nombre, domicilio, nif, email)
    VALUES (p_nombre, p_domicilio, p_nif, p_email);

    SET @last_id = LAST_INSERT_ID();

    -- Insertar en clienteEstandar usando el id generado
    INSERT INTO clienteEstandar (idCliente) VALUES (@last_id);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insertarClientePremium(
    IN p_nombre VARCHAR(40),
    IN p_domicilio VARCHAR(40),
    IN p_nif VARCHAR(40),
    IN p_email VARCHAR(40),
    IN p_descuento FLOAT
)
BEGIN
    INSERT INTO cliente (nombre, domicilio, nif, email)
    VALUES (p_nombre, p_domicilio, p_nif, p_email);

    SET @last_id = LAST_INSERT_ID();

    INSERT INTO clientePremium (idCliente, descuento) VALUES (@last_id, p_descuento);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ObtenerClienteEstandarPorID(IN cliente_id INT, OUT encontrado INT)
BEGIN
    DECLARE num_rows INT;

    -- Contar las filas que coinciden usando la columna correcta (idCliente)
    SELECT COUNT(*) INTO num_rows
    FROM clienteEstandar
    WHERE idCliente = cliente_id;

    IF num_rows > 0 THEN
        SELECT *
        FROM cliente
        WHERE idCliente = cliente_id;
        SET encontrado = num_rows;
    ELSE
        SET encontrado = 0;
    END IF;
END //
DELIMITER ;
