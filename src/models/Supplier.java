package models;

public class Supplier {
    private Integer id;
    private String  name;
    private String  phone;
    private String  email;

    public Supplier(Integer id, String name, String phone, String email) {
        this.id=id; this.name=name; this.phone=phone; this.email=email;
    }
    public Supplier(String name, String phone, String email) {
        this(null, name, phone, email);
    }

    public Integer getId() { return id; }
    public void    setId(Integer id) { this.id = id; }
    public String  getName() { return name; }
    public void    setName(String name) { this.name = name; }
    public String  getPhone() { return phone; }
    public void    setPhone(String phone) { this.phone = phone; }
    public String  getEmail() { return email; }
    public void    setEmail(String email) { this.email = email; }

    @Override public String toString() {
        return "Supplier{id=%d, name='%s'}".formatted(id, name);
    }
}

