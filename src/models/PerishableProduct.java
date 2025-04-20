package models;

import java.time.LocalDate;

public class PerishableProduct extends Product {

    private LocalDate expirationDate;

    public PerishableProduct(String name,
                             double price,
                             int initialQty,
                             Category category,
                             Supplier supplier,
                             LocalDate expirationDate) {
        super(name, price, initialQty, category, supplier);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public String toString() {
        return super.toString() + " | exp=" + expirationDate;
    }
}
