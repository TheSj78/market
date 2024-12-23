import java.io.IOException;
import java.util.ArrayList;
/**
 * CSVHandler
 *
 * This class handles some CSV operations for other classes
 */
public class CSVHandler {
    public static Store lineToStore(String line) throws InvalidProductNameException, IOException {
        String storeName = "";
        String sellerName = "";
        try {
            int sellerNameIdx = line.indexOf(";");
            sellerName = line.substring(0, sellerNameIdx);
            storeName = line.substring(sellerNameIdx + 1, line.indexOf(","));
            line = line.substring(line.indexOf(",") + 1);
            String[] products = line.split(",");
            ArrayList<Product> productList = new ArrayList<>();
            for (String productDetail : products) {
                String[] product = productDetail.split("/");
                String name = product[0];
                double price = Double.parseDouble(product[1]);
                int quantity = Integer.parseInt(product[2]);
                int quantitySold = Integer.parseInt(product[3]);
                String description = product[4];
                productList.add(new Product(name, storeName, description, quantity, price, quantitySold));
            }
            return new Store(productList, storeName, sellerName);
        } catch (Exception e) {
            //System.out.println("Error in CSVHandler: lineToStore. Details below:\n" + e.getStackTrace());
            //return null;
            return new Store(new ArrayList<Product>(), storeName, sellerName);
        }
    }

    public static Product lineToProduct(String line, String storeName) {

        try {
            String[] product = line.split(",");
            String name = product[0];
            double price = Double.parseDouble(product[1]);
            int quantity = Integer.parseInt(product[2]);
            int quantitySold = Integer.parseInt(product[3]);
            String description = product[4];

            return new Product(name, storeName, description, quantity, price, quantitySold);
        } catch (Exception e) {
            System.out.println("Error in CSVHandler: lineToStore. Details below:\n" + e.getStackTrace());
        }
        return null;
    }
}
