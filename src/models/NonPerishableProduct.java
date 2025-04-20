package models;

public class NonPerishableProduct extends Product {
    public NonPerishableProduct(String name,
                                double price,
                                int initialQty,
                                Category category,
                                Supplier supplier) {
        super(name, price, initialQty, category, supplier);
    }
}
