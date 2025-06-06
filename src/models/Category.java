package models;

public class Category {
    private Integer id;
    private String  name;
    private String  description;

    public Category(Integer id, String name, String description) {
        this.id = id;  this.name = name;  this.description = description;
    }
    public Category(String name, String description) {
        this(null, name, description);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String  getName() { return name; }
    public void    setName(String name) { this.name = name; }
    public String  getDescription() { return description; }
    public void    setDescription(String description) { this.description = description; }

    @Override public String toString() {
        return "Category{id=%d, name='%s'}".formatted(id, name);
    }
}