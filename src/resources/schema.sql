CREATE TABLE categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(100) NOT NULL,
                            description VARCHAR(255)
);

CREATE TABLE suppliers (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(120) NOT NULL,
                           phone VARCHAR(40),
                           email VARCHAR(80)
);

CREATE TABLE products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(120) NOT NULL,
                          price DOUBLE NOT NULL,
                          stock_qty INT NOT NULL,
                          best_before DATE,
                          category_id INT NOT NULL,
                          supplier_id INT NOT NULL,
                          dtype VARCHAR(30) NOT NULL,
                          FOREIGN KEY (category_id) REFERENCES categories(id),
                          FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

CREATE TABLE stock_change_logs (
                                   id INT PRIMARY KEY AUTO_INCREMENT,
                                   product_id INT NOT NULL,
                                   delta_qty INT NOT NULL,
                                   reason VARCHAR(150),
                                   ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   FOREIGN KEY (product_id) REFERENCES products(id)
);
