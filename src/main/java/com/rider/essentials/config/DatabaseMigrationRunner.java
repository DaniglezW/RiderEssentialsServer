package com.rider.essentials.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class DatabaseMigrationRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseMigrationRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void runMigrations() {
        log.info("Running database schema migrations...");
        migrateProducts();
        migrateUsers();
        migrateLegacyCartTables();
        migrateCartItems();
        migrateWishlistItems();
        migrateUserBikes();
        migrateOrders();
        log.info("Database schema migrations completed.");
    }

    private void migrateProducts() {
        exec("ALTER TABLE products ADD COLUMN IF NOT EXISTS brand VARCHAR(80)");
        exec("ALTER TABLE products ADD COLUMN IF NOT EXISTS stock_quantity INTEGER");
        exec("ALTER TABLE products ADD COLUMN IF NOT EXISTS rating DOUBLE PRECISION");
        exec("ALTER TABLE products ADD COLUMN IF NOT EXISTS review_count INTEGER");
        exec("ALTER TABLE products ADD COLUMN IF NOT EXISTS sku VARCHAR(30)");
        exec("ALTER TABLE products ADD COLUMN IF NOT EXISTS compatible_makes VARCHAR(500)");
    }

    private void migrateUsers() {
        if (!tableExists("users")) {
            createUsersTable();
            return;
        }

        if (columnExists("users", "id") && !columnExists("users", "user_id")) {
            log.info("Renaming users.id -> user_id");
            exec("ALTER TABLE users RENAME COLUMN id TO user_id");
        } else if (columnExists("users", "userid") && !columnExists("users", "user_id")) {
            log.info("Renaming users.userid -> user_id");
            exec("ALTER TABLE users RENAME COLUMN userid TO user_id");
        }

        if (!columnExists("users", "user_id") || hasNullUserIds() || !isUserIdPrimaryKey() || hasLegacyUserColumns()) {
            log.warn("Rebuilding users table (incompatible legacy schema)");
            dropDependentUserTables();
            exec("DROP TABLE IF EXISTS users CASCADE");
            createUsersTable();
            return;
        }

        exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS name VARCHAR(255)");
        exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS email VARCHAR(255)");
        exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS created_at TIMESTAMP");

        exec("UPDATE users SET name = 'Rider' WHERE name IS NULL");
        exec("UPDATE users SET email = 'legacy_' || user_id || '@rider.local' WHERE email IS NULL");
        exec("UPDATE users SET created_at = NOW() WHERE created_at IS NULL");

        exec("ALTER TABLE users ALTER COLUMN name SET DEFAULT 'Rider'");
        exec("ALTER TABLE users ALTER COLUMN name SET NOT NULL");
        exec("ALTER TABLE users ALTER COLUMN email SET NOT NULL");
        exec("ALTER TABLE users ALTER COLUMN created_at SET DEFAULT NOW()");
        exec("ALTER TABLE users ALTER COLUMN created_at SET NOT NULL");
    }

    private boolean hasLegacyUserColumns() {
        return columnExists("users", "username")
                || columnExists("users", "password")
                || columnExists("users", "image_url")
                || columnExists("users", "image")
                || columnExists("users", "updated_at");
    }

    private boolean hasNullUserIds() {
        if (!columnExists("users", "user_id")) {
            return false;
        }
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE user_id IS NULL",
                Long.class
        );
        return count != null && count > 0;
    }

    private boolean isUserIdPrimaryKey() {
        Boolean isPk = jdbcTemplate.queryForObject("""
                SELECT EXISTS (
                    SELECT 1
                    FROM information_schema.table_constraints tc
                    JOIN information_schema.key_column_usage kcu
                        ON tc.constraint_name = kcu.constraint_name
                        AND tc.table_schema = kcu.table_schema
                    WHERE tc.table_schema = 'public'
                      AND tc.table_name = 'users'
                      AND tc.constraint_type = 'PRIMARY KEY'
                      AND kcu.column_name = 'user_id'
                )
                """, Boolean.class);
        return Boolean.TRUE.equals(isPk);
    }

    private void createUsersTable() {
        exec("""
                CREATE TABLE users (
                    user_id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL DEFAULT 'Rider',
                    email VARCHAR(255) NOT NULL UNIQUE,
                    created_at TIMESTAMP NOT NULL DEFAULT NOW()
                )
                """);
    }

    private void migrateLegacyCartTables() {
        if (tableExists("cart_items") && !columnExists("cart_items", "user_id")) {
            log.info("Dropping legacy cart_items table (old schema)");
            exec("DROP TABLE IF EXISTS cart_items CASCADE");
        }
        if (tableExists("carts")) {
            log.info("Dropping legacy carts table");
            exec("DROP TABLE IF EXISTS carts CASCADE");
        }
    }

    private void migrateCartItems() {
        if (!tableExists("cart_items")) {
            exec("""
                    CREATE TABLE cart_items (
                        cart_item_id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL REFERENCES users(user_id),
                        product_id BIGINT NOT NULL REFERENCES products(product_id),
                        quantity INTEGER NOT NULL DEFAULT 1,
                        size VARCHAR(20) NOT NULL DEFAULT 'X',
                        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                        CONSTRAINT uk_cart_user_product_size UNIQUE (user_id, product_id, size)
                    )
                    """);
        } else {
            exec("ALTER TABLE cart_items ADD COLUMN IF NOT EXISTS size VARCHAR(20) NOT NULL DEFAULT 'X'");
            exec("ALTER TABLE cart_items ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT NOW()");
        }
    }

    private void migrateWishlistItems() {
        if (!tableExists("wishlist_items")) {
            exec("""
                    CREATE TABLE wishlist_items (
                        wishlist_item_id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL REFERENCES users(user_id),
                        product_id BIGINT NOT NULL REFERENCES products(product_id),
                        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                        CONSTRAINT uk_wishlist_user_product UNIQUE (user_id, product_id)
                    )
                    """);
        }
    }

    private void migrateUserBikes() {
        if (!tableExists("user_bikes")) {
            exec("""
                    CREATE TABLE user_bikes (
                        bike_id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL REFERENCES users(user_id),
                        make VARCHAR(80) NOT NULL,
                        model VARCHAR(120) NOT NULL,
                        year INTEGER NOT NULL,
                        nickname VARCHAR(100),
                        is_primary BOOLEAN NOT NULL DEFAULT FALSE,
                        created_at TIMESTAMP NOT NULL DEFAULT NOW()
                    )
                    """);
        }
    }

    private void migrateOrders() {
        if (!tableExists("orders")) {
            exec("""
                    CREATE TABLE orders (
                        order_id BIGSERIAL PRIMARY KEY,
                        order_code VARCHAR(30) NOT NULL UNIQUE,
                        user_id BIGINT NOT NULL REFERENCES users(user_id),
                        subtotal NUMERIC(10,2) NOT NULL,
                        shipping NUMERIC(10,2) NOT NULL,
                        total NUMERIC(10,2) NOT NULL,
                        status VARCHAR(30) NOT NULL,
                        payment_method VARCHAR(30) NOT NULL,
                        full_name VARCHAR(255) NOT NULL,
                        street VARCHAR(255) NOT NULL,
                        city VARCHAR(100) NOT NULL,
                        postal_code VARCHAR(20) NOT NULL,
                        country VARCHAR(80) NOT NULL,
                        phone VARCHAR(30) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT NOW()
                    )
                    """);
        }
        if (!tableExists("order_items")) {
            exec("""
                    CREATE TABLE order_items (
                        order_item_id BIGSERIAL PRIMARY KEY,
                        order_id BIGINT NOT NULL REFERENCES orders(order_id),
                        product_id BIGINT NOT NULL,
                        product_name VARCHAR(255) NOT NULL,
                        price NUMERIC(10,2) NOT NULL,
                        quantity INTEGER NOT NULL,
                        size VARCHAR(20) NOT NULL,
                        image_base64 TEXT,
                        brand VARCHAR(80)
                    )
                    """);
        }
    }

    private void dropDependentUserTables() {
        exec("DROP TABLE IF EXISTS order_items CASCADE");
        exec("DROP TABLE IF EXISTS orders CASCADE");
        exec("DROP TABLE IF EXISTS wishlist_items CASCADE");
        exec("DROP TABLE IF EXISTS user_bikes CASCADE");
        exec("DROP TABLE IF EXISTS cart_items CASCADE");
        exec("DROP TABLE IF EXISTS carts CASCADE");
    }

    private boolean tableExists(String tableName) {
        Boolean exists = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = ?)",
                Boolean.class,
                tableName
        );
        return Boolean.TRUE.equals(exists);
    }

    private boolean columnExists(String tableName, String columnName) {
        Boolean exists = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = ? AND column_name = ?)",
                Boolean.class,
                tableName,
                columnName
        );
        return Boolean.TRUE.equals(exists);
    }

    private void exec(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            log.error("Migration failed: {} — {}", e.getMessage(), sql.trim().substring(0, Math.min(80, sql.length())));
            throw new IllegalStateException("Database migration failed: " + e.getMessage(), e);
        }
    }
}
