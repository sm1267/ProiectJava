package models;

import java.time.LocalDateTime;

public class StockChangeLog {
    public enum ChangeType { SALE, RESTOCK }

    private final int productId;
    private final int qtyChange;
    private final ChangeType type;
    private final LocalDateTime timestamp;

    public StockChangeLog(int productId, int qtyChange, ChangeType type) {
        this.productId = productId;
        this.qtyChange = qtyChange;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public int getProductId() { return productId; }
    public int getQtyChange() { return qtyChange; }
    public ChangeType getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + type + "] product=" + productId + " qty=" + (qtyChange > 0 ? "+" : "") + qtyChange + " @ " + timestamp;
    }
}