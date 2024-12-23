import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Seller
 * <p>
 * This class extends User, and represents a Seller accessing the Marketplace.
 */
public class Seller extends User implements Serializable {

    private ArrayList<Store> stores = new ArrayList<>();
    private ArrayList<Customer> customers;


    public Seller(String email, String password, ArrayList<Store> stores)
            throws IOException, InvalidProductNameException {
        super(email, password);
        this.stores = stores;
        reloadFromCsv();
    }


    public boolean openStore(String storeName, String sellerName) throws IOException {
        Store store;
        try {
            store = new Store(storeName);
            store.setSellerName(sellerName);
            stores.add(store);
        } catch (InvalidProductNameException e) {
            return false;
        }

        File newFile = new File("data\\sellers\\" + this.getName() + "\\" + store.getName() + ".csv");
        newFile.createNewFile();

     //   reloadCsv();
        return true;
    }

//    public void closeStore(Store store) throws IOException {
//        Files.delete(Paths.get("data\\sellers\\" + this.getName() + "\\" + store.getName() + ".csv"));
//    }

    public Store getStore(String storeName) {
        for (Store store : stores) {
            if (store.getName().equalsIgnoreCase(storeName)) {
                return store;
            }
        }

        return null;
    }

    public void reloadCsv() {
        try {
            File dir = new File("data\\sellers\\" + this.getName());
            File[] directoryListing = dir.listFiles();
            for (File child : directoryListing) {
                PrintWriter pw = new PrintWriter(new FileWriter(child, false));
                pw.println(this.findStore(child.getName().substring(0, child.getName().lastIndexOf("."))));
                pw.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Store findStore(String name) {
        try {
            for (Store store : stores) {
                if (store.getName().equalsIgnoreCase(name)) {
                    return store;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void reloadFromCsv() throws InvalidProductNameException {
        try {
            Files.createDirectories(Paths.get("data\\sellers\\" + this.getName()));
//            File file = new File("data/sellers/" + this.getName() + "/" + this.getName() + ".csv");
//            file.createNewFile();
            File dir = new File("data\\sellers\\" + this.getName());
            File[] directoryListing = dir.listFiles();
            for (File child : directoryListing) {
                // Do something with child
                BufferedReader bfr = new BufferedReader(new FileReader("data\\sellers\\" + this.getName() + "\\" + child.getName()));
                String line;
                while ((line = bfr.readLine()) != null) {
                    Store tempStore = CSVHandler.lineToStore(line);
                    stores.add(tempStore);
                }

                //this.reloadCsv();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void importProducts(String textFile, Store store) throws IOException, InvalidProductNameException {
        FileReader fr = new FileReader(new File(textFile));
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            String[] params = line.split(",");
            Product product = new Product(params[0], params[1], params[2], Integer.parseInt(params[3]),
                    Double.parseDouble(params[4]), Integer.parseInt(params[4]));
            stores.get(stores.indexOf(store)).addProduct(product);
            line = bfr.readLine();
        }
    }

    public void exportProducts(String textFile) throws IOException {
        File file = new File(textFile);
        PrintWriter pw = new PrintWriter(file);
        for (int i = 0; i < stores.size(); i++) {
            ArrayList<Product> products = stores.get(i).getProducts();
            pw.println(stores.get(i).getName());
            for (int j = 0; j < products.size(); j++) {
                pw.println(products.get(i).csvString());
                pw.flush();
            }
            pw.println();
            pw.flush();
        }
    }


    public ArrayList<Store> getStores() {
        return this.stores;
    }


    public String getName() {
        return this.getEmail().split("@")[0];
    }

    public void setStoreName(String storeName, String name) throws InvalidProductNameException {
     //   this.reloadFromCsv();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                stores.get(i).setName(name);
            }
        }
     //   this.reloadCsv();
    }

    public void addProducts(String storeName, Product product)
            throws InvalidProductNameException, FileNotFoundException {
    //    this.reloadFromCsv();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                stores.get(i).addProduct(product);
            }
        }
      //  this.reloadCsv();
    }

    public void removeProduct(String storeName, String productName) throws InvalidProductNameException,
            FileNotFoundException {
    //    this.reloadFromCsv();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                stores.get(i).removeProduct(productName);
            }
        }
     //   this.reloadCsv();
    }

    public ArrayList<String> viewSellerDashboard() throws IOException {
        ArrayList<String> dashboardData = new ArrayList<>();
        for (Customer customer : customers) {
            dashboardData.add(String.format("Customer %s-Items Purchased: %d\n",
                    customer.getEmail().split("@")[0],
                    customer.getBoughtProducts().getProducts().size()));
        }
        //iterates through purchased products
        for (Customer customer : customers) {
            ArrayList<Product> currentProducts = customer.getBoughtProducts().getProducts();
            for (Product currentProduct : currentProducts) {
                dashboardData.add(String.format("Product %s-Number of Sales: %f\n", currentProduct.getName(),
                        currentProduct.getQuantitySold() * currentProduct.getPrice()));
            }

        }
        return dashboardData;
    }


}
