package models;

import java.time.LocalDateTime;

public class SupplyTransaction {
    private static int NEXT_ID = 1;
    private final int id;
    private final Product product;
    private final int quantity;
    private final Supplier supplier;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public SupplyTransaction(Product product, int quantity, Supplier supplier) {
        this.id = NEXT_ID++;
        this.product = product;
        this.quantity = quantity;
        this.supplier = supplier;
    }

    @Override public String toString() {
        return "Supply#" + id + " [" + quantity + " x " + product.getName() +
                "] from " + supplier.getName() + " @" + timestamp;
    }
}
