-- Insertar datos en la tabla de usuarios
INSERT IGNORE INTO users (name, email, password, image_url) VALUES
('John Doe', 'john@example.com', '123456789', 'defaultUser.jpg'),
('Jane Smith', 'jane@example.com', '987654321', 'defaultUser.jpg'),
('Alice Johnson', 'alice@example.com', 'password1', 'defaultUser.jpg'),
('Bob Brown', 'bob@example.com', 'password2', 'defaultUser.jpg'),
('Carol White', 'carol@example.com', 'password3', 'defaultUser.jpg'),
('Dave Black', 'dave@example.com', 'password4', 'defaultUser.jpg'),
('Eve Green', 'eve@example.com', 'password5', 'defaultUser.jpg'),
('Frank Harris', 'frank@example.com', 'password6', 'defaultUser.jpg'),
('Grace Lewis', 'grace@example.com', 'password7', 'defaultUser.jpg'),
('Hank Walker', 'hank@example.com', 'password8', 'defaultUser.jpg');

-- Insertar datos en la tabla de categorías
INSERT IGNORE INTO categories (name, description) VALUES
('Accesorios de Rendimiento', 'Mejora el rendimiento de tu moto'),
('Lubricantes', 'Aceites y lubricantes para motos'),
('Repuestos', 'Repuestos y partes de motos'),
('Equipamiento', 'Equipamiento para motoristas'),
('Neumáticos', 'Neumáticos de diferentes tipos y marcas'),
('Accesorios Estéticos', 'Mejora la apariencia de tu moto'),
('Herramientas', 'Herramientas para el mantenimiento de motos'),
('Ropa', 'Ropa y accesorios de protección para motoristas'),
('Electrónica', 'Componentes electrónicos para motos'),
('Protecciones', 'Protecciones para el motorista y la moto');

-- Insertar datos en la tabla de productos
INSERT IGNORE INTO products (name, description, price, image_url, category_id) VALUES
-- Accesorios de Rendimiento
('Escape Akrapovic Ninja 400', 'Escape de alto rendimiento en fibra de carbono para motocicletas kawasaki ninja 400', 1200.00, 'akrapovic.jpg', 1), -- Producto existente
('Filtro de potencia BMC', 'Filtro de aire de alto flujo para rendimiento mejorado', 75.00, 'Z900.jpg', 1),
('Filtro de aceite Hiflofiltro', 'Filtro de aceite de alto rendimiento', 15.00, 'filtroAire.jpg', 1),
('Bujías NGK Iridium', 'Bujías de alto rendimiento para motores', 25.00, 'bujias.jpg', 1),
('Kit de carburador Keihin', 'Kit de carburador de alto rendimiento', 350.00, 'carburador.jpg', 1),

-- Lubricantes
('Lubricante Motul Chain Lube', 'Lubricante para cadenas de moto', 20.00, 'lubricante.jpg', 2),
('Grasa para rodamientos Bel-Ray', 'Grasa especial para rodamientos de alta carga', 10.00, 'grasaRodamientos.jpg', 2),
('Aditivo limpiador de inyectores STP', 'Aditivo para limpiar inyectores y mejorar el rendimiento', 12.00, 'aditivo.jpg', 2),
('Aceite Repsol Moto 10W40', 'Aceite semisintético para motos de alta cilindrada', 35.00, 'aceiteRepsol.jpg', 2),

-- Repuestos
('Pastillas de freno Brembo', 'Pastillas de freno de alto rendimiento', 60.00, 'pastillasBrembo.jpg', 3),
('Cadena DID', 'Cadena reforzada para motos', 100.00, 'cadenaDid.jpg', 3),
('Amortiguador Öhlins', 'Amortiguador trasero de alto rendimiento', 800.00, 'amortiguador.jpg', 3),
('Kit de cadena y piñones AFAM', 'Kit completo de transmisión para motos deportivas', 150.00, 'kitAFAM.jpg', 3),

-- Equipamiento
('Guantes Dainese', 'Guantes de cuero con protección', 90.00, 'guantesDaineseNegros.jpg', 4),
('Botas Sidi', 'Botas de protección para motociclistas', 300.00, 'botasSidiNegras.jpg', 4),
('Pantalones Rev’it!', 'Pantalones con protecciones de kevlar', 180.00, 'pantalonesRevitNegros.jpg', 4),

-- Neumáticos
('Neumático Pirelli Diablo Rosso', 'Neumático deportivo de alto rendimiento', 160.00, 'neuDiabloRosso.jpg', 5),
('Neumático Bridgestone Battlax', 'Neumático touring de larga duración', 140.00, 'neuBridgestoneBattlax.jpg', 5),
('Neumático Dunlop Sportmax', 'Neumático de competición para moto', 170.00, 'neuDunlopSportMax.jpg', 5),

-- Accesorios Estéticos
('Retrovisores Barracuda', 'Retrovisores de diseño deportivo', 75.00, 'retrovisorBarracuda.jpg', 6),
('Retrovisores Rizoma Stealth', 'Retrovisores / alerones de diseño deportivo', 80.00, 'rizomaStealthMirrors.jpg', 6),
('Cubiertas de motor Puig', 'Cubiertas de motor de diseño exclusivo', 90.00, 'cubiertaMotorPuigNegro.jpg', 6),
('Soporte de matrícula R&G Racing', 'Soporte de matrícula minimalista', 60.00, 'soporteMatriculaRGRacing.jpg', 6),

-- Herramientas
('Kit de herramientas CruzTOOLS', 'Kit básico de herramientas para motociclistas', 45.00, 'kitTools.jpg', 7),
('Elevador de moto Biketek', 'Elevador hidráulico para motos', 180.00, 'elevadorMotoBiketek.jpg', 7),
('Cargador de batería Optimate', 'Cargador inteligente para baterías de moto', 60.00, 'cargadorBateriaOptimate.jpg', 7),

-- Ropa
('Camiseta Alpinestars', 'Camiseta casual para motociclistas', 25.00, 'camisetaAlpinestar.jpg', 8),
('Gorra Fox Racing', 'Gorra de diseño para motociclistas', 20.00, 'gorraFox.jpg', 8),
('Mochila Kriega', 'Mochila impermeable para motociclistas', 150.00, 'mochilaKriega.jpg', 8),

-- Electrónica
('Luces LED Philips', 'Luces LED de alto rendimiento para motos', 80.00, 'lucesLedPhilips.jpg', 9),
('Alarma Xena', 'Sistema de alarma antirrobo para motos', 120.00, 'alarmaXena.jpg', 9),
('GoPro Hero 11', 'Cámara de acción para grabación en moto', 450.00, 'goproHero11.jpg', 9),

-- Protecciones
('Chaleco Airbag Dainese', 'Chaleco con airbag incorporado para protección máxima', 600.00, 'chalecoAirbagDainese.jpg', 10),
('Protector de espalda Alpinestars', 'Protección dorsal para motociclistas', 130.00, 'protectorEspaldaAlpinestar.jpg', 10),
('Guarda manos Acerbis', 'Protección para las manos en off-road', 70.00, 'guardamanosAcerbi.jpg', 10),
('Protector de cárter Givi', 'Protección para el cárter en motos off-road', 120.00, 'protectorGivi.jpg', 10),
('Slider R&G', 'Slider de protección para caídas', 95.00, 'sliderRG.jpg', 10);

-- Insertar datos en la tabla de etiquetas
INSERT IGNORE INTO tags (name) VALUES
('Akrapovic'),
('Castrol'),
('K&N'),
('Shoei'),
('Michelin'),
('Rizoma'),
('Herramientas'),
('Alpinestars'),
('Sena'),
('Dainese');

-- Asociar etiquetas a productos
INSERT IGNORE INTO product_tags (product_id, tag_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

-- Insertar datos en la tabla de carritos
INSERT IGNORE INTO carts (user_id) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10);

-- Insertar datos en la tabla de items del carrito
INSERT IGNORE INTO cart_items (cart_id, product_id, quantity) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 2),
(6, 6, 1),
(7, 7, 1),
(8, 8, 1),
(9, 9, 1),
(10, 10, 1);