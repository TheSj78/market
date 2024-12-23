import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Customer
 * This class extends User and represents a Customer accessing the marketplace
 *
 */
public class Customer extends User implements Serializable {
    //declarations
    private Cart boughtProducts;
    private Cart shoppingList;
    private String path;
    File shoppingCart;
    File history;
    public Customer(String email, String password) throws IOException, InvalidProductNameException {
        super(email, password);
        this.boughtProducts = new Cart();
        this.shoppingList = new Cart();
        this.path = "data/customers/" + email.split("@")[0] + "/";
        reloadData();
    }
//methods
    //getters

    // returns the ArrayList boughtProducts (list of products bought)
    public Cart getBoughtProducts() throws IOException {
        return boughtProducts;
    }
    //returns the items in the current shopping cart
    public Cart getShoppingList() throws IOException {
        return shoppingList;
    }
    //removes all items in the shopping cart
    public void removeAll() throws IOException {
        this.shoppingList.removeAllProducts();
        updateShoppingListFile();
    }

    //setters

    // sets the ArrayList boughtProducts to the given boughtProducts
    public void setBoughtProducts(Cart boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    //other methods

    // a method that reloads data for shoppingCart file and history file each time a customer object is made
    public void reloadData() throws IOException, InvalidProductNameException {

        Files.createDirectories(Paths.get(path));
        shoppingCart = new File( path + "shoppingCart.csv");
        shoppingCart.createNewFile();
        history = new File(path + "history.csv");
        history.createNewFile();
        BufferedReader bfrCart = new BufferedReader(new FileReader(path + "shoppingCart.csv"));
        BufferedReader bfrHist = new BufferedReader(new FileReader(path + "history.csv"));

        String l;
        while ((l = bfrCart.readLine()) != null) {
            String[] params = l.split("/");
            Product product = new Product(params[0], params[5], params[4], Integer.parseInt(params[2]),
                    Double.parseDouble(params[1]), Integer.parseInt(params[3]));
            shoppingList.addProduct(product);
        }

        String s;
        while ((s = bfrHist.readLine()) != null) {
            String[] params = s.split("/");
            Product product = new Product(params[0], params[5], params[4], Integer.parseInt(params[2]),
                    Double.parseDouble(params[1]), Integer.parseInt(params[3]));
            boughtProducts.addProduct(product);
        }
    }

    // adds a product to shoppingList cart
    public void addToCart(Product product) throws IOException {
        shoppingList.addProduct(product);
        this.updateShoppingListFile();

    }

    // removes a product shoppingList cart
    public void removeFromCart(Product product) throws IOException {
        shoppingList.removeProduct(product);
        this.updateShoppingListFile();
    }

    // updates the shoppingList cart
    public void updateShoppingListFile() throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(path + "shoppingCart.csv", false));
        for (Product product : shoppingList.getProducts()) {
            pw.println(product.csvString() + "/" + product.getStoreName());
        }
        pw.flush();
        pw.close();
    }

    // updates the customer history file
    public void updateHistoryFile() throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("data\\customers\\" +
                this.getEmail() + "\\shoppingCart.csv"));
        PrintWriter pw = new PrintWriter(new FileOutputStream("data\\customers\\" +
                this.getEmail() + "\\history.csv", true));
        String line = bfr.readLine();
        while(line != null) {
            pw.println(line);
            pw.flush();
            line = bfr.readLine();
        }
        pw.close();
    }


    // purchases items in shoppingList cart
    public void purchaseItems() throws IOException {
        boughtProducts.addAllProducts(shoppingList.getProducts());
        updateHistoryFile();
        shoppingList.removeAllProducts();
        updateShoppingListFile();
    }

    public void exportProductHistory() throws IOException {
        String fileName = this.getEmail() + "History.csv";
        File f = new File(fileName);
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(fos);

        for (int i = 0; i < boughtProducts.getProducts().size(); i++) {
            Product current = boughtProducts.getProducts().get(i);
            pw.println(current.toString());
            pw.flush();
//            pw.println(current.getName() + "," + current.getStoreName() + "," + current.getDescription() + "," +
//                    current.getQuantity() + "," + current.getPrice() + "," + current.getQuantitySold());
        }
        pw.close();
    }

    @Override
    public void viewDashboard() throws IOException {
        File f = new File(path + "allProductsList.csv");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        ArrayList<String> stores = new ArrayList<>();
        ArrayList<Integer> productsSold = new ArrayList<>();
        while (line != null) {
            String[] lineArray = line.split(",");
            boolean exists = false;
            for (int i = 0; i < stores.size(); i++) {
                if (stores.contains(lineArray[1])) {
                    exists = true;
                    productsSold.set(i, productsSold.get(i) + Integer.parseInt(lineArray[5]));
                    break;
                }
            }
            if (!exists) {
                stores.add(lineArray[1]);
                productsSold.add(Integer.parseInt(lineArray[5]));
            }
        }

        int n = stores.size();
        if (n > 0) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (productsSold.get(j) > productsSold.get(j + 1)) {
                        int tempSold = productsSold.get(j);
                        productsSold.set(j, productsSold.get(j + 1));
                        productsSold.set(j + 1, tempSold);

                        String tempStore = stores.get(j);
                        stores.set(j, stores.get(j + 1));
                        stores.set(j + 1, tempStore);
                    }
                }
            }
        }

        for (int i = 0; i < stores.size(); i++) {
            System.out.printf("Store %s - No. of products sold: %d\n", stores.get(i), productsSold.get(i));
        }
        br.close();

        //bought products part of view dashboard
        stores = new ArrayList<>();
        ArrayList<String> products = new ArrayList<>();

        for (Product product : boughtProducts.getProducts()) {
            BufferedReader br1 = new BufferedReader(fr);

            line = br1.readLine();
            while (line != null) {
                String[] lineArray = line.split(",");
                if (product.getName().equals(lineArray[0]) && product.getStoreName().equals(lineArray[1])) {
                    if (!stores.contains(lineArray[1])) {
                        stores.add(lineArray[1]);
                        products.add(lineArray[0]);
                    } else {
                        String temp = products.get(stores.indexOf(lineArray[1]));
                        temp += ", " + lineArray[0];
                        products.set(stores.indexOf(lineArray[1]), temp);
                    }
                }

                line = br.readLine();
            }

            for (int i = 0; i < stores.size(); i++) {
                System.out.printf("Store %s - Products bought: %s\n", stores.get(i), products.get(i));
            }
        }
    }

    public void deleteAccount() {
        File f = new File(path + "shoppingCart.csv");
        f.delete();
        f = new File(path + "history.csv");
        f.delete();
        f = new File(path);
        f.delete();

        reset();
    }

    public void reset() {
        this.boughtProducts = new Cart();
        this.shoppingList = new Cart();
        this.path = "";
        shoppingCart = null;
        history = null;
    }
}
