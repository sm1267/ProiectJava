package models;

import java.time.LocalDateTime;

public class SaleTransaction {
    private static int NEXT_ID = 1;
    private final int id;
    private final Product product;
    private final int quantity;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public SaleTransaction(Product product, int quantity) {
        this.id = NEXT_ID++;
        this.product = product;
        this.quantity = quantity;
    }

    @Override public String toString() {
        return "Sale#" + id + " [" + quantity + " x " + product.getName() + "] @" + timestamp;
    }
}
