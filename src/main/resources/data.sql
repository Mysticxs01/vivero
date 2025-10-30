-- Datos de prueba para el sistema de gestión de vivero

-- Productores
INSERT INTO productores (documento, nombre, apellido, telefono, correo) VALUES
('1234567890', 'Juan', 'Pérez', '3001234567', 'juan.perez@email.com'),
('0987654321', 'María', 'González', '3109876543', 'maria.gonzalez@email.com'),
('1122334455', 'Carlos', 'Rodríguez', '3201122334', 'carlos.rodriguez@email.com'),
('5544332211', 'Ana', 'Martínez', '3155443322', 'ana.martinez@email.com'),
('6677889900', 'Luis', 'López', '3186677889', 'luis.lopez@email.com');

-- Fincas
INSERT INTO fincas (numero_catastro, municipio, productor_id) VALUES
('CAT-001-2024', 'Medellín', 1),
('CAT-002-2024', 'Envigado', 1),
('CAT-003-2024', 'Rionegro', 2),
('CAT-004-2024', 'La Ceja', 3),
('CAT-005-2024', 'El Retiro', 4),
('CAT-006-2024', 'Guarne', 5);

-- Viveros
INSERT INTO viveros (codigo, tipo_cultivo, finca_id) VALUES
('VIV-001', 'Café', 1),
('VIV-002', 'Plátano', 1),
('VIV-003', 'Aguacate', 2),
('VIV-004', 'Cítricos', 3),
('VIV-005', 'Tomate', 4),
('VIV-006', 'Flores', 5),
('VIV-007', 'Fresa', 6);

-- Productos de Control - Base
INSERT INTO productos_control (registroica, nombre_producto, frecuencia_aplicacion, valor) VALUES
('ICA-HONGO-001', 'Fungicida Premium', 15, 45000.00),
('ICA-HONGO-002', 'Antifúngico Natural', 20, 38000.00),
('ICA-PLAGA-001', 'Insecticida Orgánico', 10, 52000.00),
('ICA-PLAGA-002', 'Control de Plagas Total', 12, 48000.00),
('ICA-FERT-001', 'Fertilizante NPK 10-10-10', 30, 75000.00),
('ICA-FERT-002', 'Abono Orgánico Completo', 25, 65000.00);

-- Productos Control Hongo
INSERT INTO productos_control_hongo (id, nombre_hongo, periodo_carencia) VALUES
(1, 'Roya del Café', 7),
(2, 'Mildiu Polvoriento', 10);

-- Productos Control Plaga
INSERT INTO productos_control_plaga (id, periodo_carencia) VALUES
(3, 5),
(4, 7);

-- Productos Control Fertilizante
INSERT INTO productos_control_fertilizante (id, fecha_ultima_aplicacion) VALUES
(5, '2024-10-01'),
(6, '2024-10-15');

-- Labores
INSERT INTO labores (fecha, descripcion, vivero_id, producto_control_id) VALUES
('2024-10-28', 'Aplicación de fungicida preventivo', 1, 1),
('2024-10-27', 'Control de plagas en cultivo', 1, 3),
('2024-10-26', 'Fertilización de plantas jóvenes', 2, 5),
('2024-10-25', 'Tratamiento contra roya', 3, 2),
('2024-10-24', 'Control de insectos', 4, 4),
('2024-10-23', 'Aplicación de abono orgánico', 5, 6),
('2024-10-22', 'Poda sanitaria', 6, NULL),
('2024-10-21', 'Riego profundo', 7, NULL),
('2024-10-20', 'Transplante de plántulas', 1, NULL),
('2024-10-19', 'Control fitosanitario', 2, 1);
