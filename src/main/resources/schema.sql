
-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    image BLOB,
    image_url VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Crear tabla de categorías
CREATE TABLE IF NOT EXISTS categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Crear tabla de productos
CREATE TABLE IF NOT EXISTS products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image BLOB,
    image_url VARCHAR(100),
    category_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL
);

-- Crear tabla de etiquetas
CREATE TABLE IF NOT EXISTS tags (
    tag_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Crear tabla de etiquetas de productos
CREATE TABLE IF NOT EXISTS product_tags (
    product_id INT REFERENCES products(product_id),
    tag_id INT REFERENCES tags(tag_id),
    PRIMARY KEY (product_id, tag_id)
);

-- Crear tabla de carritos
CREATE TABLE IF NOT EXISTS carts (
    cart_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Crear tabla de items del carrito
CREATE TABLE IF NOT EXISTS cart_items (
    cart_item_id SERIAL PRIMARY KEY,
    cart_id INT REFERENCES carts(cart_id),
    product_id INT REFERENCES products(product_id),
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);