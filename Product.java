import java.io.*;

/**
 * Product
 *
 * This class represents an individual Product item,
 * which keeps track of its name, description, price,
 * store name, and quantity available.
 */
public class Product implements Serializable {
    private String name;
    private String storeName;
    private String description;
    private int quantity;
    private double price;
    private int quantitySold;
    private final File allProductsList = new File("allProductsList.csv");

    public Product(String name, String storeName, String description, int quantity, double price, int quantitySold) throws IOException, InvalidProductNameException {
        this.name = name;
        this.storeName = storeName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.quantitySold = quantitySold;

    }

    // default constructor
    public Product() {
        this.name = "";
        this.storeName = "";
        this.description = "";
        this.quantity = 0;
        this.price = 0.0;
        this.quantitySold = 0;
    }


    //getters

    // returns the name of this Product
    public String getName() {
        return name;
    }

    // returns the name of the Store that sells this Product
    public String getStoreName() {
        return storeName;
    }

    // returns the description of this Product
    public String getDescription() {
        return description;
    }

    // returns the number of products in stock
    public int getQuantity() {
        return quantity;
    }

    // returns the price of this product
    public double getPrice() {
        return price;
    }

    // returns the number of products sold
    public int getQuantitySold() {
        return quantitySold;
    }

    //setters

    // sets this name to given name
    public void setName(String name) {
        this.name = name;
    }

    // sets this storeName to given storeName
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    // sets this description to given description
    public void setDescription(String description) {
        this.description = description;
    }

    // sets this quantity to given quantity
    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    // sets this price to given price
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    // sets this quantitySold to given quantitySold
    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    //other methods

    // purchases this product and returns true if product was successfully bought and false otherwise
    public boolean purchase() {
        if (this.quantity > 0) {
            this.quantity--;
            this.quantitySold++;
            return true;
        }
        return false;
    }

    // returns a listingPageString of the format "Name: ...
    //                                            Store: ...
    //                                            Description: ..."
    public String listingPageString() {
        return String.format(
                "Name: %s\nStore Name: %s\nDescription: %s",
                this.name, this.storeName, this.description
        );
    }

    // returns a CSV (comma separated values) string with the format "name,storeName,description,quantity,price"
    public String csvString() {
        return String.format(
                "%s/%.2f/%d/%d/%s",
                this.name, this.price, this.quantity, this.quantitySold, this.description);
    }

    // compares this Product with the given Object and returns true if they are equal according to the given conditions
    // and false otherwise
    @Override
    public boolean equals(Object p) {
        if (p instanceof Product) {
            Product product = (Product) p;
            return this.name.equals(product.getName())
                    && this.storeName.equals(product.getStoreName())
                    && this.description.equals(product.getDescription())
                    && this.quantity == product.getQuantity()
                    && this.price == product.getPrice()
                    && this.quantitySold == product.getQuantitySold();
        }
        return false;
    }

    // returns a String version of this Product
    @Override
    public String toString() {
        return String.format(
                "Name: %s\nStore Name: %s\nDescription: %s\nQuantity: %d\nPrice: $%.2f\nQuantity Sold: %d",
                this.name, this.storeName, this.description, this.quantity, this.price, this.quantitySold
        );
    }
}
