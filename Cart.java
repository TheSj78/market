import java.io.*;
import java.util.ArrayList;
/**
 * Cart
 *
 * This class represents a Customer's cart, and keeps track of all the Products inside of it.
 */

public class Cart implements Serializable {
    //declarations
    private ArrayList<Product> products;

    //Constructors
    public Cart() {
        this.products = new ArrayList<Product>();
    }

    //methods
    //getters
    public ArrayList<Product> getProducts() {
        return products;
    }
    //setters
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    //other methods
    public void addProduct(Product product) {
        this.products.add(product);
    }
    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void removeAllProducts() {
        this.products.clear();
    }
    public void addAllProducts(ArrayList<Product> productArrayList) {
        this.products.addAll(productArrayList);
    }
}
