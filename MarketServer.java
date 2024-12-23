import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * MarketServer
 * The server class for the marketplace 
 */
public class MarketServer {

    //    private static ArrayList<Product> products;
    private static ArrayList<Store> stores = new ArrayList<>();
    //private static HashMap<Store, ArrayList<Product>> market = new HashMap<>();
    private static File marketData;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loadDatabase();
        ServerSocket serverSocket = new ServerSocket(4242);

        while (true) {
            Socket socket = serverSocket.accept();
            MarketServerThread serverThread = new MarketServerThread(socket);
            serverThread.start();
        }


    }

    synchronized public static void addProduct(Store toAdd, Product product) throws FileNotFoundException {
        for (Store store : stores) {
            if (store.equals(toAdd)) {
                ArrayList<Product> products = store.getProducts();
                products.add(product);
            }
        }
        updateDatabase();
    }


//    synchronized public static int quantitySoldStore(Store toShow) {
//        int totalQuantity = 0;
//        for(Store store : stores) {
//            if(store.equals(toShow)) {
//                for(Product product : store.getProducts()) {
//                    totalQuantity += product.getQuantitySold();
//                }
//            }
//        }
//
//        return totalQuantity;
//    }

    synchronized public static ArrayList<Product> productsSold(Store toShow) {
        ArrayList<Product> productsSold = new ArrayList<>();
        for (Store store : stores) {
            if (store.equals(toShow)) {
                for (Product product : store.getProducts()) {
                    if (product.getQuantitySold() > 0) {
                        productsSold.add(product);
                    }
                }
            }
        }
        return productsSold;
    }


    synchronized public static void addStore(Store store) throws FileNotFoundException {
        stores.add(store);
        updateDatabase();
    }

    synchronized public static void removeProduct(Store toRemove, Product product) throws FileNotFoundException {
        for (Store store : stores) {
            if (store.equals(toRemove)) {
                ArrayList<Product> products = store.getProducts();
                products.remove(product);
                updateDatabase();

            }
        }
    }

    synchronized public static void removeStore(Store store) throws FileNotFoundException {
        stores.remove(store);
        for (Store s : stores) {
            if (s.equals(store)) {
                stores.remove(s);
                updateDatabase();
                return;
            }
        }
    }

    synchronized public static ArrayList<Product> getAllProducts() {
        try {
            ArrayList<Product> products = new ArrayList<>();
            if (stores == null) return null;
            for (Store store : stores) {
                ArrayList<Product> temp = store.getProducts();
                products.addAll(temp);
            }

            return products;
        } catch (Exception e) {
            return null;
        }
    }

//    synchronized public static void updateDatabase() throws FileNotFoundException {
//        PrintWriter pw = new PrintWriter(new FileOutputStream("marketDatabase.csv", false));
//        for (Product product : products) {
//            pw.println(product.csvString());
//            pw.flush();
//        }
//        pw.close();
//    }

    synchronized public static ArrayList<Product> sortQuantity() {
        try {
            ArrayList<Product> quantitySort = new ArrayList<>();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                quantitySort.addAll(products);
            }

            Collections.sort(quantitySort, new Comparator<Product>() {
                @Override
                public int compare(Product p1, Product p2) {
                    double dif = p1.getQuantity() - p2.getQuantity();
                    if (dif > 0) return 1;
                    if (dif < 0) return -1;
                    return 0;
                }
            });
            return quantitySort;
        } catch (Exception e) {
            return null;
        }
    }

    synchronized public static ArrayList<Product> sortPrice() {
        try {
            ArrayList<Product> priceSort = new ArrayList<>();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                priceSort.addAll(products);
            }

            Collections.sort(priceSort, new Comparator<Product>() {
                @Override
                public int compare(Product p1, Product p2) {
                    double dif = p1.getPrice() - p2.getPrice();
                    if (dif > 0) return 1;
                    if (dif < 0) return -1;
                    return 0;
                }
            });

            return priceSort;
        } catch (Exception e) {
            return null;
        }

    }

    synchronized public static ArrayList<Product> searchProducts(String searchText) {
        ArrayList<Product> productSearch = new ArrayList<>();
        for (Store store : stores) {
            ArrayList<Product> products = store.getProducts();
            for (Product product : products) {
                if (product.getName().contains(searchText) || product.getStoreName().contains(searchText) ||
                        product.getDescription().contains(searchText)) {
                    productSearch.add(product);
                }
            }
        }
        return productSearch;
    }

    synchronized public static void setProduct(Store toEdit, Product product, Product newProduct)
            throws FileNotFoundException {
        for (Store store : stores) {
            if (toEdit.equals(store)) {
                store.getProducts().set(store.getProducts().indexOf(product), newProduct);
                updateDatabase();
            }
//            if (.equals(product)) {
//                p.setName(newProduct.getName());
//                p.setStoreName(newProduct.getStoreName());
//                p.setDescription(newProduct.getDescription());
//                p.setQuantity(newProduct.getQuantity());
//                p.setPrice(newProduct.getPrice());
//            }
        }
    }

//    synchronized public static void purchaseProduct(Product product) throws FileNotFoundException {
//        for(int i = 0; i < products.size(); i++) {
//            if (products.get(i).equals(product)) {
//                products.get(i).setQuantity(products.get(i).getQuantity() - 1);
//                products.get(i).setQuantitySold(products.get(i).getQuantitySold() + 1);
//
//            }
//        }
//        updateDatabase();
//    }

    synchronized public static void purchaseProduct(Product toPurchase) throws FileNotFoundException {
        for (Store store : stores) {
            for (Product product : store.getProducts()) {
                if (product.equals(toPurchase)) {
                    product.purchase();
                    updateDatabase();
                }
            }
        }

    }

    synchronized public static void loadDatabase() {
        //stores.clear();
        try {
            FileReader fr = new FileReader(new File("data/marketData.csv"));
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                stores.add(CSVHandler.lineToStore(line));
                line = bfr.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    synchronized public static ArrayList<Store> getSellerStores(String sellerName) {
        try {
            FileReader fr = new FileReader(new File("data/marketData.csv"));
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            ArrayList<Store> sellerStores = new ArrayList<>();
            while (line != null) {
                String[] s = line.split(";");
                if (s[0].equalsIgnoreCase(sellerName)) {
                    sellerStores.add(CSVHandler.lineToStore(line));
                }
                line = bfr.readLine();
            }

            return sellerStores;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    synchronized public static void updateDatabase() {
        try {
            FileReader fr = new FileReader("data\\marketData.csv");
            BufferedReader bfr = new BufferedReader(fr);
            PrintWriter pw = new PrintWriter(new FileOutputStream("data\\marketData.csv", false));
            //String line = bfr.readLine();
            for (Store store : stores) {
                try {
                    String s = store.toMarketString();
                    pw.println(store.toMarketString());
                    pw.flush();
                    //line = bfr.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }


    synchronized public static ArrayList<Store> getStores() {
        return stores;
    }


    synchronized public static ArrayList<Product> getStoreProducts(Store toView) {
        if (stores == null) return null;
        for (Store store : stores) {
            if (toView.equals(store)) {
                return store.getProducts();
            }
        }
        return null;
    }


    synchronized public static Product findProduct(String name) {
        try {
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    if (product.getName().equalsIgnoreCase(name)) {
                        return product;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


}

