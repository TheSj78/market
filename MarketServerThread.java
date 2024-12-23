import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * MarketServerThread
 *
 * This class is used for multiple clients to access the server (concurrency)
 */
public class MarketServerThread extends Thread {
    Socket socket;
    private static final Object LOCK = new Object();
    public MarketServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.flush();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while (true) {
                PrintWriter pw = new PrintWriter(new FileOutputStream("loginInfo.csv", true));
                BufferedReader bfr = new BufferedReader(new FileReader("loginInfo.csv"));
//                oos.reset();
                switch (Integer.parseInt(reader.readLine())) {
                    case 1 -> {
                        String user = reader.readLine();
                        String pass = reader.readLine();
                        String line = bfr.readLine();
                        boolean accFound = false;
                        boolean success = false;
                        while (line != null) {
                            String[] sep = line.split(",");
                            if (sep[0].equals(user) && sep[1].equals(pass)) {
                                accFound = true;
                                success = true;
                                if (sep[2].equals("customer")) {
                                    oos.writeObject(1);
                                    oos.flush();
                                    User currUser = new Customer(user, pass);
                                    oos.writeObject(currUser);
                                    oos.flush();
                                } else if (sep[2].equals("seller")) {
                                    oos.writeObject(2);
                                    oos.flush();
                                    ArrayList<Store> sellerStores = MarketServer.getSellerStores(user);
                                    User currUser = new Seller(user, pass, sellerStores);
                                    oos.writeObject(currUser);
                                    oos.flush();
                                }
                                break;
                            } else if (sep[0].equals(user)) {
                                oos.writeObject(0);
                                oos.flush();
                                accFound = true;
                            }
                            line = bfr.readLine();
                        }
                        if (!accFound && !success) {
                            oos.writeObject(-1);
                            oos.flush();
                        }
                    }
                    case 2 -> {
                        String newUser = reader.readLine();
                        if (newUser == null) break;
                        String newPass = reader.readLine();
                        if (newPass == null) break;
                        String l;
                        boolean found = false;
                        while ((l = bfr.readLine()) != null) {
                            String[] sep = l.split(",");
                            if (sep[0].equalsIgnoreCase(newUser)) {
                                found = true;
                            }
                        }
                        if (found) {
                            oos.writeObject(true);
                        } else {
                            oos.writeObject(false);
                        }
                        pw.print(newUser + "," + newPass);
                        pw.flush();
                        int cOrS = (int) ois.readObject();
                        if (cOrS == -1) break;
                        if (cOrS + 1 == 1) {
                            pw.println(",customer");
                            pw.flush();
                            oos.writeObject(1);
                            oos.flush();
//                        pw.close();
                        } else if (cOrS + 1 == 2) {
                            pw.println(",seller");
                            pw.flush();
                            oos.writeObject(2);
                            oos.flush();
//                        pw.close();
                        }
                    }
                    case 3 -> {
                        oos.writeObject(MarketServer.getAllProducts());
                        oos.flush();
                    }

                    case 4 -> {
                        String searchText = (String) ois.readObject();
                        oos.writeObject(MarketServer.searchProducts(searchText));
                        oos.flush();
                    }

                    case 5 -> {
                        int option = Integer.parseInt(reader.readLine());
                        if (option == 0) {
                            ArrayList<Product> products = MarketServer.sortQuantity();
                            oos.writeObject(products);
                            oos.flush();
                        } else if (option == 1) {
                            oos.writeObject(MarketServer.sortPrice());
                            oos.flush();
                        } else {
                            oos.writeObject(null);
                            oos.flush();
                        }

                    }

                    case 6 -> {
                        int option = Integer.parseInt(reader.readLine());
                        Cart customerCart = (Cart) ois.readObject();
                        ArrayList<Product> customerCartList = customerCart.getProducts();
                        if (option == 0) {
                            for(Product product : customerCartList) {
                                MarketServer.purchaseProduct(product);
                            }
                        }
                    }

                    case 7 -> {
                        Product product = (Product) ois.readObject();
//                        Product product = MarketServer.findProduct(productName);
                        oos.writeObject(product);
                        oos.flush();


                    }

                    case 8 -> {
                        Store store = (Store) ois.readObject();
                        String sellerName = reader.readLine();
                        MarketServer.addStore(store);
                    }

                    case 9 -> {
                        oos.writeObject(MarketServer.getStores());
                        oos.flush();
                        Store store = (Store) ois.readObject();
                        oos.writeObject(MarketServer.getStoreProducts(store));
                        oos.flush();
                    }

                    case 10 -> {
                        String sellerName = reader.readLine();
                        ArrayList<Store> sellerStores = MarketServer.getSellerStores(sellerName);
                        oos.writeObject(sellerStores);
                        oos.flush();
//                        Store store = (Store) ois.readObject();
//                        Product product = (Product) ois.readObject();
//                        MarketServer.addProduct(store, product);

                    }

                    case 11 -> {
                        String sellerName = reader.readLine();
                        Store store = (Store) ois.readObject();
                        int option = Integer.parseInt(reader.readLine());
                        if(option == 0) {
                            Product product = (Product) ois.readObject();
                            MarketServer.addProduct(store, product);
                        } else if (option == 1) {
                            Product product = (Product) ois.readObject();
                            MarketServer.removeProduct(store, product);
                        } else if (option == 2) {
                            Product product = (Product) ois.readObject();
                            Product newProduct = (Product) ois.readObject();
                            MarketServer.setProduct(store, product, newProduct);

                        } else if (option == 3) {
                            MarketServer.removeStore(store);
                        }

                    }

                    case 12 -> {
                        try {
                            Store store = (Store) ois.readObject();
                            String path = reader.readLine();
                            BufferedReader bfrImport = new BufferedReader(new FileReader(path));
                            synchronized (LOCK) {
                                String line = bfrImport.readLine();
                                while (line != null) {
                                    Product product = CSVHandler.lineToProduct(line, store.getName());
                                    MarketServer.addProduct(store, product);
                                    line = bfr.readLine();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    case 13 -> {
                        String sellerName = reader.readLine();
                        ArrayList<Store> sellerStores = MarketServer.getSellerStores(sellerName);
                        synchronized (LOCK) {
                            PrintWriter pwExport = new PrintWriter(new FileOutputStream(sellerName + "Products.txt"));
                            for (Store store : sellerStores) {
                                for (Product product : store.getProducts()) {
                                    pwExport.println(product.toString());
                                    pwExport.flush();
                                }
                            }
                            pw.close();
                        }



                    }

                    case 14 -> {
                        String sellerName = reader.readLine();
                        Store store = (Store) ois.readObject();
//                        int quantitySoldStore = MarketServer.quantitySoldStore(store);
//                        oos.writeObject(quantitySoldStore);
//                        oos.flush();
                        ArrayList<Product> productsSold = MarketServer.productsSold(store);
                        oos.writeObject(productsSold);
                        oos.flush();
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


