package models;

public final class NonPerishableProduct extends Product {
    public NonPerishableProduct(Integer id, String name, double price, int stockQty,
                                Category cat, Supplier sup) {
        super(id, name, price, stockQty, cat, sup);
    }
    public NonPerishableProduct(String name, double price, int stockQty,
                                Category cat, Supplier sup) {
        this(null, name, price, stockQty, cat, sup);
    }

    @Override public String getDtype() { return "NONPERISHABLE"; }
}

