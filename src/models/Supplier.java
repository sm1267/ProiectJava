package models;

import java.util.Objects;

public class Supplier {
    private static int NEXT_ID = 1;

    private final int id;
    private String name;
    private String phone;

    public Supplier(String name, String phone) {
        this.id = NEXT_ID++;
        this.name = Objects.requireNonNull(name);
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name); }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Supplier{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
