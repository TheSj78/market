import java.io.*;
import java.util.*;
/**
 * Store
 *
 * This class represents one of the Seller's Store.
 */
public class Store implements Serializable {
    ArrayList<Product> products;
    File productFile;
    //creates name
    String name;
    String sellerName = "";
    public Store(ArrayList<Product> products, String name,  String sellerName) throws InvalidProductNameException {
        this.name = name;
        this.products = products;
        this.sellerName = sellerName;
    }
    public Store(String name) throws InvalidProductNameException {
        this.name = name;
        products = new ArrayList<>();
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void reloadCsv() {
        try {
            File file = new File(this.getName() + "ProductFile.csv");
            PrintWriter pw = new PrintWriter(new FileWriter(file, false));
            for (int i = 0; i < products.size(); i++) {
                pw.println(products.get(i).toString());
                pw.flush();
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void reloadFromCsv() throws InvalidProductNameException {
        try {
            productFile = new File(this.getName() + "ProductFile.csv");
            productFile.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(this.getName() + "ProductFile.csv"));
            this.products = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] params = line.split(",");
                Product product = new Product(params[0], params[1], params[2], Integer.parseInt(params[3]),
                        Double.parseDouble(params[4]), Integer.parseInt(params[5]));
                products.add(product);
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InvalidProductNameException e) {
            e.printStackTrace();
        }
    }

//    public void removeStore() throws FileNotFoundException {
//        this.setName(null);
//        products.clear();
//        updateProductFile();
//        productFile.delete();
//    }

    public boolean removeProduct(String productName) throws FileNotFoundException, InvalidProductNameException {
        this.reloadFromCsv();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(productName)) {
                products.remove(i);
                this.reloadCsv();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() == Store.class) {
            return this.getName().equalsIgnoreCase((((Store) object).getName()));
        } else {
            return false;
        }
    }

    public boolean editProduct(String productName, String newProductName, String newStoreName, String newDesc,
                               int newQuant, double newPrice) throws IOException, InvalidProductNameException {
        reloadFromCsv();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(productName)) {
                Product newProduct = new Product(newProductName, newStoreName, newDesc, newQuant,
                        newPrice, products.get(i).getQuantitySold());
                products.set(i, newProduct);
                this.reloadCsv();
                return true;
            }
        }
        return false;
    }

    // adds a product to the ArrayList of Product objects

    public void addProduct(Product product) throws FileNotFoundException {
        products.add(product);
        this.reloadCsv();
    }

    // returns the name of this Store
    public String getName() {
        return name;
    }


    public void setName(String name) throws InvalidProductNameException {
        this.name = name;
        this.reloadFromCsv();
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setStoreName(name);
        }
        this.reloadCsv();
    }


    // returns the ArrayList of Product objects of this store

    public ArrayList<Product> getProducts() {
        return products;
    }


    public String toString() {
        String s = this.getName() + ",";
        for (Product product : this.getProducts()) {
            s += product.csvString() + ",";
        }
        return s;
    }

    public String toMarketString() {
        String s = sellerName + ";" + this.getName() + ",";
        for (Product product : this.getProducts()) {
            s += product.csvString() + ",";
        }
        return s;
    }
}
