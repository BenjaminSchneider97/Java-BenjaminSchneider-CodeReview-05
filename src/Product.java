public class Product {

    private String name;
    private String quantity;
    private double oldPrice;
    private double newPrice;
    private String imagePath;
    private String description;

    public Product(String name, String quantity, double oldPrice, double newPrice, String imagePath, String description) {
        this.name = name;
        this.quantity = quantity;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getOldPrice() {
        return this.oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return this.newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{\'" + name + "\', old=\'" + oldPrice + "\', new=\'" + newPrice + "\'}";
    }
}