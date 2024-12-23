import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Market Client
 * <p>
 * This class represents the client side of the market
 */
public class MarketClient {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 4242);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.flush();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        SwingUtilities.invokeLater(new Runnable() {
            JButton login;
            JButton signup;
            JTextField username;
            JPasswordField password;
            JLabel userPrompt;
            JLabel passPrompt;
            User currUser;
            final boolean cont = false;
            JFrame frame;
            JButton viewMarket;
            JButton sortMarket;
            JButton addToCart;
            JButton viewCart;
            JButton exportHistory;
            JButton searchMarket;
            JButton shopByStore;
            JButton viewCustomerDashboard;
            JButton editAccount;
            JButton delAccount;
            JButton displayStore;
            JButton editStore;
            JButton openStore;
            JButton importProducts;
            JButton viewSellerDashboard;
            JButton exportProducts;
            JButton editAcc;
            JButton delAcc;
            final GridLayout gridLayout = new GridLayout(0, 2);


            public void run() {

                frame = new JFrame("Market");
                frame.setSize(400, 200);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

                userPrompt = new JLabel("Username: ");
                userPrompt.setBounds(100, 8, 70, 20);
                passPrompt = new JLabel("Password:");
                passPrompt.setBounds(100, 55, 70, 20);
                username = new JTextField(10);
                username.setBounds(100, 27, 193, 28);
                password = new JPasswordField(10);
                password.setBounds(100, 75, 193, 28);
                login = new JButton("Login");
                login.addActionListener(actionListener);
                login.setBounds(100, 110, 90, 25);
                signup = new JButton("Sign-Up");
                signup.addActionListener(actionListener);
                signup.setBounds(200, 110, 90, 25);
                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.add(userPrompt);
                panel.add(username);
                panel.add(passPrompt);
                panel.add(password);
                panel.add(login);
                panel.add(signup);
                frame.add(panel);
            }


            final ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == login) {
                        try {
                            login();

                            if (currUser instanceof Customer) {
                                frame.setVisible(false);
                                JFrame customerFrame = new JFrame("Customer");
                                Container content = customerFrame.getContentPane();
                                customerFrame.setSize(600, 400);
                                customerFrame.setLocationRelativeTo(null);
                                customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                customerFrame.setVisible(true);

                                viewMarket = new JButton("View Market");
                                viewMarket.addActionListener(customerActionListener);
                                sortMarket = new JButton("Sort Market");
                                sortMarket.addActionListener(customerActionListener);
                                addToCart = new JButton("Add to Cart");
                                addToCart.addActionListener(customerActionListener);
                                viewCart = new JButton("View Cart");
                                viewCart.addActionListener(customerActionListener);
                                exportHistory = new JButton("Export Product History");
                                exportHistory.addActionListener(customerActionListener);
                                searchMarket = new JButton("Search Market");
                                searchMarket.addActionListener(customerActionListener);
                                shopByStore = new JButton("Shop by Store");
                                shopByStore.addActionListener(customerActionListener);
                                viewCustomerDashboard = new JButton("View Dashboard");
                                viewCustomerDashboard.addActionListener(customerActionListener);
                                editAccount = new JButton("Edit Account");
                                editAccount.addActionListener(customerActionListener);
                                delAccount = new JButton("Delete Account");
                                delAccount.addActionListener(customerActionListener);

                                GridLayout gridLayout = new GridLayout(0, 2);
                                content.setLayout(gridLayout);
                                content.add(viewMarket);
                                content.add(searchMarket);
                                content.add(sortMarket);
                                content.add(shopByStore);
                                content.add(viewCart);
                                content.add(addToCart);
                                content.add(viewCustomerDashboard);
                                content.add(exportHistory);
                                content.add(editAccount);
                                content.add(delAccount);

                            } else if (currUser instanceof Seller) {
                                frame.setVisible(false);
                                JFrame sellerFrame = new JFrame("Seller");
                                Container content = sellerFrame.getContentPane();
                                sellerFrame.setSize(600, 400);
                                sellerFrame.setLocationRelativeTo(null);
                                sellerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                sellerFrame.setVisible(true);

                                displayStore = new JButton("Display Stores");
                                displayStore.addActionListener(sellerActionListener);
                                editStore = new JButton("Edit Store");
                                editStore.addActionListener(sellerActionListener);
                                openStore = new JButton("Open a Store");
                                openStore.addActionListener(sellerActionListener);
                                importProducts = new JButton("Import Products");
                                importProducts.addActionListener(sellerActionListener);
                                viewSellerDashboard = new JButton("View Dashboard");
                                viewSellerDashboard.addActionListener(sellerActionListener);
                                exportProducts = new JButton("Export Products");
                                exportProducts.addActionListener(sellerActionListener);
                                editAcc = new JButton("Edit Account");
                                editAcc.addActionListener(sellerActionListener);
                                delAcc = new JButton("Delete Account");
                                delAcc.addActionListener(sellerActionListener);
                                GridLayout gridLayout = new GridLayout(0, 2);

                                content.setLayout(gridLayout);
                                content.add(displayStore);
                                content.add(editStore);
                                content.add(openStore);
                                content.add(importProducts);
                                content.add(viewSellerDashboard);
                                content.add(exportProducts);
                                content.add(editAcc);
                                content.add(delAcc);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                    } else if (e.getSource() == signup) {
                        try {
                            signup();
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            };

            final ActionListener customerActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == viewMarket) {
                        try {
                            writer.println(3);
                            writer.flush();
                            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
                            if (products == null) {
                                JOptionPane.showMessageDialog(null,
                                        "No Products Available! Check Back Later.", "Customer",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                JList list = new JList(products.toArray());
                                JPanel panel = new JPanel();
                                Border border = BorderFactory.createTitledBorder("List of All Products");
                                panel.setBorder(border);
                                panel.add(new JScrollPane(list));
                                JOptionPane.showMessageDialog(null, panel);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == searchMarket) {
                        try {
                            writer.println(4);
                            writer.flush();
                            String searchText = JOptionPane.showInputDialog(
                                    null, "Enter Search Text", "Customer",
                                    JOptionPane.PLAIN_MESSAGE);
                            oos.writeObject(searchText);
                            oos.flush();
                            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
                            if (products == null) {
                                JOptionPane.showMessageDialog(null,
                                        "No Products Matched the Search.", "Customer",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                JList list = new JList(products.toArray());
                                JPanel panel = new JPanel();
                                Border border = BorderFactory.createTitledBorder("Search Results");
                                panel.setBorder(border);
                                panel.add(new JScrollPane(list));
                                JOptionPane.showMessageDialog(null, panel);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    if (e.getSource() == sortMarket) {
                        try {
                            writer.println(5);
                            writer.flush();
                            String[] buttons = {"Quantity", "Price"};
                            int option = JOptionPane.showOptionDialog(null,
                                    "Sort by Quantity or Price?", "Customer",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, buttons, null);
                            writer.println(option);
                            writer.flush();
                            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
                            if (products == null) {
                                JOptionPane.showMessageDialog(null,
                                        "No Products to Sort!", "Customer", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JList list = new JList(products.toArray());
                                JPanel panel = new JPanel();
                                Border border = BorderFactory.createTitledBorder("Sorted Products.");
                                panel.setBorder(border);
                                panel.add(new JScrollPane(list));
                                JOptionPane.showMessageDialog(null, panel);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    if (e.getSource() == viewCart) {
                        try {
                            writer.println(6);
                            writer.flush();
                            Cart customerCart = ((Customer) currUser).getShoppingList();
                            JList cart = new JList(customerCart.getProducts().toArray());
                            JPanel panel = new JPanel();
                            Border border = BorderFactory.createTitledBorder(currUser.getEmail() + "'s Cart");
                            panel.setBorder(border);
                            panel.add(new JScrollPane(cart));
                            panel.add(new JLabel("OK to \nContinue."));
                            JOptionPane.showMessageDialog(null, panel);
                            String[] buttons = {"Checkout", "Remove Product from Cart", "Empty Cart"};
                            int option = JOptionPane.showOptionDialog(null,
                                    "Select an Option.", "Customer",
                                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, buttons, null);
                            writer.println(option);
                            writer.flush();
                            oos.writeObject(customerCart);
                            oos.flush();
                            if (option == 0) {
                                ((Customer) currUser).purchaseItems();
                            } else if (option == 1) {
                                Product product = (Product) JOptionPane.showInputDialog(null,
                                        "Select a Product.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                        null, customerCart.getProducts().toArray(), null);
                                ((Customer) currUser).removeFromCart(product);
                            } else if (option == 2) {
                                ((Customer) currUser).removeAll();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == addToCart) {
                        try {
                            writer.println(3);
                            writer.flush();
                            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
                            writer.println(7);
                            writer.flush();
                            Product product = (Product) JOptionPane.showInputDialog(null,
                                    "Select a Product.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                    null, products.toArray(), null);
                            oos.writeObject(product);
                            oos.flush();
                            Product productSelect = (Product) ois.readObject();
                            if (product == null) {
                                JOptionPane.showMessageDialog(null, "Product Not Found.",
                                        "Customer", JOptionPane.ERROR_MESSAGE);
                            } else {
                                ((Customer) currUser).addToCart(productSelect);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == exportHistory) {
                        try {
                            ((Customer) currUser).exportProductHistory();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == shopByStore) {
                        try {
                            writer.println(9);
                            writer.flush();
                            ArrayList<Store> stores = (ArrayList<Store>) ois.readObject();
                            if (stores == null) {
                                JOptionPane.showMessageDialog(null, "No Stores Available.",
                                        "Customer", JOptionPane.ERROR_MESSAGE);
                                oos.writeObject(null);
                                oos.flush();
                                return;
                            }
                            Store store = (Store) JOptionPane.showInputDialog(null,
                                    "Select a Store.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                    null, stores.toArray(), null);
                            oos.writeObject(store);
                            oos.flush();
                            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
                            if (products == null) {
                                JOptionPane.showMessageDialog(null, "No Available Products.",
                                        "Customer", JOptionPane.ERROR_MESSAGE);
                                oos.writeObject(store);
                                oos.flush();
                                return;
                            }
                            Product product = (Product) JOptionPane.showInputDialog(null,
                                    "Select a Product.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                    null, products.toArray(), null);
                            int option = JOptionPane.showOptionDialog(null, "Add to Cart?",
                                    "Customer", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, null, null);
                            if (option == 0) {
                                ((Customer) currUser).addToCart(product);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == viewCustomerDashboard) {
                        try {
                            BufferedReader bfr = new BufferedReader(new FileReader("data\\customers\\" +
                                    currUser.getEmail() + "\\history.csv"));
                            String line = bfr.readLine();
                            HashMap<String, Integer> map = new HashMap<String, Integer>();
                            while (line != null) {
                                String[] s = line.split("/");
                                if (map.containsKey(s[5])) {
                                    map.put(s[5], map.get(s[5]) + 1);
                                } else {
                                    map.put(s[5], 1);
                                }
                                line = bfr.readLine();
                            }

                            ArrayList<String> stringRep = new ArrayList<>();
                            for (String shop : map.keySet()) {
                                stringRep.add("You have bought " + map.get(shop) + " items from " + shop + ".");
                            }

                            JList list = new JList(stringRep.toArray());
                            JPanel panel = new JPanel();
                            Border border = BorderFactory.createTitledBorder("List of All Stores");
                            panel.setBorder(border);
                            panel.add(new JScrollPane(list));
                            JOptionPane.showMessageDialog(null, panel);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }


                    if (e.getSource() == editAccount) {
                        try {
                            String newUser = JOptionPane.showInputDialog(null,
                                    "Enter New Username", "Customer", JOptionPane.PLAIN_MESSAGE);
                            String newPassword = JOptionPane.showInputDialog(null,
                                    "Enter New Password", "Customer", JOptionPane.PLAIN_MESSAGE);
                            currUser.editAccount(newUser, newPassword, "customer");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == delAccount) {
                        try {
                            currUser.deleteAccount("customer");
                            JOptionPane.showMessageDialog(null,
                                    "Account Deleted! Please Exit.",
                                    "Customer", JOptionPane.PLAIN_MESSAGE);


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            };

            final ActionListener sellerActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == displayStore) {
                        try {
                            writer.println(10);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                            ArrayList<Store> userStores = (ArrayList<Store>) ois.readObject();
                            JList list = new JList(userStores.toArray());
                            JPanel panel = new JPanel();
                            Border border = BorderFactory.createTitledBorder("List of All Stores");
                            panel.setBorder(border);
                            panel.add(new JScrollPane(list));
                            JOptionPane.showMessageDialog(null, panel);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == openStore) {
                        try {
                            oos.flush();
                            writer.println(8);
                            writer.flush();
                            String storeName = JOptionPane.showInputDialog(null,
                                    "Enter Store Name", "Seller", JOptionPane.PLAIN_MESSAGE);
                            ((Seller) currUser).openStore(storeName, ((Seller) currUser).getName());
                            Store store = ((Seller) currUser).getStore(storeName);
                            oos.writeObject(store);
                            oos.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == editStore) {
                        try {
                            writer.println(10);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                            ArrayList<Store> userStores = (ArrayList<Store>) ois.readObject();
                            writer.println(11);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                            Store store = (Store) JOptionPane.showInputDialog(null,
                                    "Select a Store.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                    null, userStores.toArray(), null);
                            oos.writeObject(store);
                            oos.flush();
                            String[] buttons = {"Add Product", "Remove Product", "Edit Product", "Close Store"};
                            int option = JOptionPane.showOptionDialog(null,
                                    "Select an Option.", "Customer",
                                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, buttons, null);
                            writer.println(option);
                            writer.flush();
                            if (option == 0) {
                                Product newProduct;
                                while (true) {
                                    try {
                                        String name = JOptionPane.showInputDialog(null,
                                                "Enter Product Name", "Seller",
                                                JOptionPane.PLAIN_MESSAGE);
                                        String desc = JOptionPane.showInputDialog(null,
                                                "Enter Product Description", "Seller",
                                                JOptionPane.PLAIN_MESSAGE);
                                        double price = Double.parseDouble(JOptionPane.showInputDialog(
                                                null, "Enter Product Price", "Seller",
                                                JOptionPane.PLAIN_MESSAGE));
                                        int quantity = Integer.parseInt(JOptionPane.showInputDialog(
                                                null, "Enter Product Quantity", "Seller",
                                                JOptionPane.PLAIN_MESSAGE));
                                        newProduct = new Product(name, store.getName(), desc, quantity, price,
                                                0);
                                        break;
                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(null, "Invalid Input",
                                                "Seller", JOptionPane.ERROR_MESSAGE);
                                    }
                                }

                                oos.writeObject(newProduct);
                                oos.flush();
                            } else if (option == 1) {
                                Product product = (Product) JOptionPane.showInputDialog(null,
                                        "Select a Product.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                        null, store.getProducts().toArray(), null);
                                oos.writeObject(product);
                                oos.flush();
                            } else if (option == 2) {
                                Product product = (Product) JOptionPane.showInputDialog(null,
                                        "Select a Product.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                        null, store.getProducts().toArray(), null);
                                String newName = JOptionPane.showInputDialog(null,
                                        "Enter New Product Name", "Seller", JOptionPane.PLAIN_MESSAGE);
                                String newDesc = JOptionPane.showInputDialog(null,
                                        "Enter New Product Description", "Seller",
                                        JOptionPane.PLAIN_MESSAGE);
                                double newPrice = Double.parseDouble(JOptionPane.showInputDialog(null,
                                        "Enter New Product Price", "Seller", JOptionPane.PLAIN_MESSAGE));
                                int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(null,
                                        "Enter New Product Quantity", "Seller",
                                        JOptionPane.PLAIN_MESSAGE));
                                Product newProduct = new Product(newName, store.getName(), newDesc, newQuantity,
                                        newPrice, product.getQuantitySold());
                                oos.writeObject(product);
                                oos.flush();
                                oos.writeObject(newProduct);
                                oos.flush();
                            } else if (option == 3) {
                                try {
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == importProducts) {
                        try {
                            writer.println(10);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                            ArrayList<Store> userStores = (ArrayList<Store>) ois.readObject();
                            writer.println(12);
                            writer.flush();
                            Store store = (Store) JOptionPane.showInputDialog(null,
                                    "Select a Store.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                    null, userStores.toArray(), null);
                            oos.writeObject(store);
                            oos.flush();
                            String path = JOptionPane.showInputDialog(null,
                                    "Enter File Path", "Seller", JOptionPane.PLAIN_MESSAGE);
                            writer.println(path);
                            writer.flush();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == exportProducts) {
                        try {
                            writer.println(10);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                            ArrayList<Store> userStores = (ArrayList<Store>) ois.readObject();
                            writer.println(13);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (e.getSource() == viewSellerDashboard) {
                        try {
                            writer.println(10);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                            ArrayList<Store> userStores = (ArrayList<Store>) ois.readObject();
                            writer.println(14);
                            writer.flush();
                            writer.println(((Seller) currUser).getName());
                            writer.flush();
                            Store store = (Store) JOptionPane.showInputDialog(null,
                                    "Select a Store.", "Customer", JOptionPane.PLAIN_MESSAGE,
                                    null, userStores.toArray(), null);
                            oos.writeObject(store);
                            oos.flush();
                            ArrayList<Product> productsSold = (ArrayList<Product>) ois.readObject();
                            ArrayList<String> stringRep = new ArrayList<>();
                            for (Product product : productsSold) {
                                stringRep.add(product.getName() + " - " + product.getQuantitySold() + " Sold.");
                            }
                            JList list = new JList(stringRep.toArray());
                            JPanel panel = new JPanel();
                            Border border = BorderFactory.createTitledBorder("List of All Stores");
                            panel.setBorder(border);
                            panel.add(new JScrollPane(list));
                            JOptionPane.showMessageDialog(null, panel);


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }

                    if (e.getSource() == editAcc) {
                        try {
                            String newUser = JOptionPane.showInputDialog(null,
                                    "Enter New Username", "Seller", JOptionPane.PLAIN_MESSAGE);
                            String newPassword = JOptionPane.showInputDialog(null,
                                    "Enter New Password", "Seller", JOptionPane.PLAIN_MESSAGE);
                            currUser.editAccount(newUser, newPassword, "seller");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (e.getSource() == delAcc) {
                        try {
                            currUser.deleteAccount("seller");
                            JOptionPane.showMessageDialog(null, "Account Deleted! Please Exit.",
                                    "Seller", JOptionPane.PLAIN_MESSAGE);


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }


                }

            };


            public void login() throws IOException, ClassNotFoundException {
                writer.println(1);
                writer.flush();
                String user = username.getText();
                writer.println(user);
                writer.flush();
                String pass = password.getText();
                writer.println(pass);
                writer.flush();
                int result = (int) ois.readObject();

                if (result == -1) {
                    JOptionPane.showMessageDialog(null, "Account doesn't exist! Sign-up.",
                            "Market", JOptionPane.ERROR_MESSAGE);
                } else if (result == 0) {
                    JOptionPane.showMessageDialog(null, "Incorrect Password",
                            "Market", JOptionPane.ERROR_MESSAGE);
                } else {
                    currUser = (User) ois.readObject();
                }
            }

            public void signup() throws IOException, ClassNotFoundException {
                writer.println(2);
                writer.flush();
                String user = JOptionPane.showInputDialog(null, "Enter Username",
                        "Market", JOptionPane.PLAIN_MESSAGE);
                writer.println(user);
                writer.flush();
                String pass = JOptionPane.showInputDialog(null, "Enter Password",
                        "Market", JOptionPane.PLAIN_MESSAGE);
                writer.println(pass);
                writer.flush();
                boolean found = (boolean) ois.readObject();
                if (found) {
                    JOptionPane.showMessageDialog(null, "Account Exists! Log-in.",
                            "Market", JOptionPane.ERROR_MESSAGE);
                } else {
                    Object[] options = {"Customer", "Seller"};
                    int result = JOptionPane.showOptionDialog(null,
                            "Are you a Customer or Seller?", "Market",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
                    oos.writeObject(result);
                    oos.flush();
                    int cOrS = (int) ois.readObject();
                }
            }
        });
    }
}