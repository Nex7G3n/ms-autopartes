-- Insertar datos en la tabla 'marcas'
INSERT INTO marcas (nombre, pais_origen) VALUES ('Toyota', 'Japón');
INSERT INTO marcas (nombre, pais_origen) VALUES ('Ford', 'Estados Unidos');
INSERT INTO marcas (nombre, pais_origen) VALUES ('BMW', 'Alemania');
INSERT INTO marcas (nombre, pais_origen) VALUES ('Hyundai', 'Corea del Sur');

-- Insertar datos en la tabla 'modelos'
-- Modelos para Toyota
INSERT INTO modelos (nombre, anio, marca_id) VALUES ('Corolla', '2022', (SELECT id FROM marcas WHERE nombre = 'Toyota'));
INSERT INTO modelos (nombre, anio, marca_id) VALUES ('Hilux', '2023', (SELECT id FROM marcas WHERE nombre = 'Toyota'));
INSERT INTO modelos (nombre, anio, marca_id) VALUES ('Rav4', '2021', (SELECT id FROM marcas WHERE nombre = 'Toyota'));

-- Modelos para Ford
INSERT INTO modelos (nombre, anio, marca_id) VALUES ('F-150', '2023', (SELECT id FROM marcas WHERE nombre = 'Ford'));
INSERT INTO modelos (nombre, anio, marca_id) VALUES ('Focus', '2020', (SELECT id FROM marcas WHERE nombre = 'Ford'));

-- Modelos para BMW
INSERT INTO modelos (nombre, anio, marca_id) VALUES ('Serie 3', '2024', (SELECT id FROM marcas WHERE nombre = 'BMW'));

-- Modelos para Hyundai
INSERT INTO modelos (nombre, anio, marca_id) VALUES ('Tucson', '2023', (SELECT id FROM marcas WHERE nombre = 'Hyundai'));

-- Insertar datos en la tabla 'piezas'
INSERT INTO piezas (nombre, categoria, descripcion) VALUES ('Faro Delantero Izquierdo', 'Iluminación', 'Faro delantero para el lado izquierdo del vehículo');
INSERT INTO piezas (nombre, categoria, descripcion) VALUES ('Pastillas de Freno Delanteras', 'Frenos', 'Juego de pastillas de freno para ruedas delanteras');
INSERT INTO piezas (nombre, categoria, descripcion) VALUES ('Filtro de Aire', 'Motor', 'Filtro de aire para el motor');
INSERT INTO piezas (nombre, categoria, descripcion) VALUES ('Parachoques Trasero', 'Carrocería', 'Parachoques para la parte trasera del vehículo');
INSERT INTO piezas (nombre, categoria, descripcion) VALUES ('Espejo Retrovisor Derecho', 'Exterior', 'Espejo retrovisor para el lado derecho');

-- Insertar datos en la tabla 'autopartes'
-- Autopartes para Toyota Corolla 2022
INSERT INTO autopartes (codigo_producto, modelo_id, pieza_id, precio, stock, estado)
VALUES ('TC22-FD-IZQ-001', (SELECT id FROM modelos WHERE nombre = 'Corolla' AND anio = '2022'), (SELECT id FROM piezas WHERE nombre = 'Faro Delantero Izquierdo'), 150.00, 10, 'Disponible');
INSERT INTO autopartes (codigo_producto, modelo_id, pieza_id, precio, stock, estado)
VALUES ('TC22-PFD-002', (SELECT id FROM modelos WHERE nombre = 'Corolla' AND anio = '2022'), (SELECT id FROM piezas WHERE nombre = 'Pastillas de Freno Delanteras'), 80.50, 25, 'Disponible');

-- Autopartes para Ford F-150 2023
INSERT INTO autopartes (codigo_producto, modelo_id, pieza_id, precio, stock, estado)
VALUES ('FF23-FA-003', (SELECT id FROM modelos WHERE nombre = 'F-150' AND anio = '2023'), (SELECT id FROM piezas WHERE nombre = 'Filtro de Aire'), 35.75, 50, 'Disponible');
INSERT INTO autopartes (codigo_producto, modelo_id, pieza_id, precio, stock, estado)
VALUES ('FF23-PT-004', (SELECT id FROM modelos WHERE nombre = 'F-150' AND anio = '2023'), (SELECT id FROM piezas WHERE nombre = 'Parachoques Trasero'), 300.00, 5, 'Disponible');

-- Autopartes para BMW Serie 3 2024
INSERT INTO autopartes (codigo_producto, modelo_id, pieza_id, precio, stock, estado)
VALUES ('BM24-ERD-005', (SELECT id FROM modelos WHERE nombre = 'Serie 3' AND anio = '2024'), (SELECT id FROM piezas WHERE nombre = 'Espejo Retrovisor Derecho'), 120.00, 12, 'Disponible');
