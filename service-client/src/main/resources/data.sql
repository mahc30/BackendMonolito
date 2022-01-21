INSERT INTO ciudad (nombre) VALUES( 'Medellin');
INSERT INTO ciudad (nombre) VALUES('Bogotá');
INSERT INTO ciudad (nombre) VALUES( 'Barranquilla');

INSERT INTO tipoid(tipo) VALUES('TI');
INSERT INTO tipoid(tipo) VALUES('CC');

INSERT INTO cliente(nombres, apellidos, numeroIdentificacion, ciudad_id, tipo_id, edad, fotoMongoId)
VALUES ('Miguel', 'Hincapie C', '100203403', 1, 1, 21, 'todo');

INSERT INTO cliente(nombres, apellidos, numeroIdentificacion, ciudad_id, tipo_id, edad, fotoMongoId)
VALUES ('Carlos', 'Apellido Original', '244645', 1, 2, 55, 'todo');

INSERT INTO cliente(nombres, apellidos, numeroIdentificacion, ciudad_id, tipo_id, edad, fotoMongoId)
VALUES ('Eclipse', 'Foundation', '4984989', 3, 1, 44, 'todo');