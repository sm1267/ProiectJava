package models;

import java.time.LocalDate;

public final class PerishableProduct extends Product {
    private LocalDate bestBefore;

    public PerishableProduct(Integer id, String name, double price, int stockQty,
                             LocalDate bestBefore, Category cat, Supplier sup) {
        super(id, name, price, stockQty, cat, sup);
        this.bestBefore = bestBefore;
    }
    public PerishableProduct(String name, double price, int stockQty,
                             LocalDate bestBefore, Category cat, Supplier sup) {
        this(null, name, price, stockQty, bestBefore, cat, sup);
    }

    public LocalDate getBestBefore() { return bestBefore; }
    public void setBestBefore(LocalDate bestBefore) { this.bestBefore = bestBefore; }

    @Override public String getDtype() { return "PERISHABLE"; }
}
