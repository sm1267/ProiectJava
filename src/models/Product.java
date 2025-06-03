package models;

public abstract class Product implements Comparable<Product> {
    private Integer   id;
    private String    name;
    private double    price;
    private int       stockQty;
    private Category  category;
    private Supplier  supplier;

    protected Product(Integer id, String name, double price, int stockQty,
                      Category category, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
        this.category = category;
        this.supplier = supplier;
    }

    protected Product(String name, double price, int stockQty,
                      Category category, Supplier supplier) {
        this(null, name, price, stockQty, category, supplier);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public Category getCategory() {
        return category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public abstract String getDtype();

    @Override
    public int compareTo(Product other) {
        if (this.name == null && other.name == null) {
            return 0;
        } else if (this.name == null) {
            return -1;
        } else if (other.name == null) {
            return 1;
        }
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return "%s{id=%d, name='%s', price=%.2f}"
                .formatted(getClass().getSimpleName(), id, name, price);
    }
}

