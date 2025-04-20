package models;

import java.util.Objects;

public abstract class Product {
    private static int NEXT_ID = 1;

    private final int id;
    private String name;
    private double price;
    private int stockQty;

    private Category category;
    private Supplier supplier;

    protected Product(String name,
                      double price,
                      int initialQty,
                      Category category,
                      Supplier supplier) {
        this.id = NEXT_ID++;
        this.name = Objects.requireNonNull(name);
        this.price = price;
        this.stockQty = Math.max(initialQty, 0);
        this.category = Objects.requireNonNull(category);
        this.supplier = Objects.requireNonNull(supplier);
    }

    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name); }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQty() { return stockQty; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = Objects.requireNonNull(category); }

    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = Objects.requireNonNull(supplier); }

    public void adjustStock(int delta) {
        int newQty = this.stockQty + delta;
        if (newQty < 0) {
            throw new IllegalArgumentException("Stoc insuficient (cerut=" + -delta + ", disponibil=" + stockQty + ")");
        }
        this.stockQty = newQty;
    }

    public double stockValue() {
        return price * stockQty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + String.format("%.2f", price) + " lei | qty=" + stockQty;
    }
}

