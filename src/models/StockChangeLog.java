package models;

import java.time.LocalDateTime;

public class StockChangeLog {
    private Integer       id;
    private Product       product;
    private int           deltaQty;
    private String        reason;
    private LocalDateTime timestamp;

    public StockChangeLog(Integer id, Product product, int deltaQty,
                          String reason, LocalDateTime timestamp) {
        this.id=id; this.product=product; this.deltaQty=deltaQty;
        this.reason=reason; this.timestamp=timestamp;
    }
    public StockChangeLog(Product product, int deltaQty, String reason) {
        this(null, product, deltaQty, reason, null);
    }

    public Integer getId() { return id; }
    public void    setId(Integer id) { this.id = id; }
    public Product getProduct() { return product; }
    public int     getDeltaQty() { return deltaQty; }
    public String  getReason() { return reason; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override public String toString() {
        return "Log{id=%d, product=%s, delta=%d}".formatted(id, product.getName(), deltaQty);
    }
}
