CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    status VARCHAR(100) NOT NULL,
    description TEXT,
    amount DOUBLE PRECISION NOT NULL
);