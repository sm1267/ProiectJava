package services;

import models.*;

import java.util.*;

public class InventoryService {

    private final Map<Integer, Product> products = new HashMap<>();
    private final Map<Integer, Category> categories = new HashMap<>();
    private final Map<Integer, Supplier> suppliers = new HashMap<>();

    private final SortedSet<Product> productsByName =
            new TreeSet<>(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER));

    private final List<StockChangeLog> changeHistory = new ArrayList<>();

    public Category addCategory(String name) {
        Category c = new Category(name);
        categories.put(c.getId(), c);
        return c;
    }

    public Supplier addSupplier(String name, String phone) {
        Supplier s = new Supplier(name, phone);
        suppliers.put(s.getId(), s);
        return s;
    }

    public Product addProduct(Product product) {
        products.put(product.getId(), product);
        productsByName.add(product);
        return product;
    }

    public Optional<Product> findProductById(int id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findProductsByName(String query) {
        return productsByName.stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public List<Product> listAllProducts() {
        return new ArrayList<>(productsByName);
    }

    public void sellProduct(int productId, int qty) {
        Product p = products.get(productId);
        if (p == null) throw new NoSuchElementException("Produs inexistent id=" + productId);
        p.adjustStock(-qty);
        changeHistory.add(new StockChangeLog(productId, -qty, StockChangeLog.ChangeType.SALE));
    }

    public void restockProduct(int productId, int qty) {
        Product p = products.get(productId);
        if (p == null) throw new NoSuchElementException("Produs inexistent id=" + productId);
        p.adjustStock(qty);
        changeHistory.add(new StockChangeLog(productId, qty, StockChangeLog.ChangeType.RESTOCK));
    }

    public List<Product> getProductsBelowStock(int threshold) {
        return products.values().stream()
                .filter(p -> p.getStockQty() < threshold)
                .sorted(Comparator.comparingInt(Product::getStockQty))
                .toList();
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return products.values().stream()
                .filter(p -> p.getCategory().getId() == categoryId)
                .toList();
    }

    public List<Product> getProductsBySupplier(int supplierId) {
        return products.values().stream()
                .filter(p -> p.getSupplier().getId() == supplierId)
                .toList();
    }

    public double getTotalStockValue() {
        return products.values().stream()
                .mapToDouble(Product::stockValue)
                .sum();
    }

    public List<StockChangeLog> getChangeHistory() {
        return Collections.unmodifiableList(changeHistory);
    }

    public int[] getStockPerProductIdAscending() {
        int maxId = products.keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        int[] stockArray = new int[maxId + 1];
        products.forEach((id, product) -> stockArray[id] = product.getStockQty());
        return stockArray;
    }
}