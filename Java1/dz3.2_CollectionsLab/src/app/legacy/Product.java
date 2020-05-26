package app.legacy;

public class Product {
    private String name;
    private String category;
    private byte[] internalCode;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public byte[] getInternalCode() {
        return internalCode;
    }
    public void setInternalCode(byte[] internalCode) {
        this.internalCode = internalCode;
    }
}
