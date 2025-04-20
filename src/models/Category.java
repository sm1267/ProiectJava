package models;
import java.util.Objects;

public class Category {
    private static int NEXT_ID = 1;

    private final int id;
    private String name;
    private String description;

    public Category(String name) {
        this(name, "");
    }

    public Category(String name, String description) {
        this.id = NEXT_ID++;
        this.name = Objects.requireNonNull(name);
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name); }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
