package RISMS_CusSection;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import RISMS_LogSection.LoginSection;

public class AddToCartUCSection {

    private JFrame frmRismsATCS;
    private JTextField product_id;
    private JTextField item_name;
    private JTextField price_per_kilo;
    private JTextField available_stocks;
    private String username;
    private String user_type;
    
    public AddToCartUCSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
        frmRismsATCS.setVisible(true);
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/RISMS_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	AddToCartUCSection window = new AddToCartUCSection();
                window.frmRismsATCS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AddToCartUCSection() {
        initialize();
    }

    private void initialize() {
        frmRismsATCS = new JFrame();
        frmRismsATCS.setBackground(Color.WHITE);
        frmRismsATCS.getContentPane().setBackground(Color.WHITE);
        frmRismsATCS.setTitle("Add To Cart (Users/Customers Section)");
        frmRismsATCS.setBounds(350, 100, 820, 500);
        frmRismsATCS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsATCS.getContentPane().setLayout(null);
       
        JPanel gradientPanel = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(36, 37, 42);    
                Color color2 = new Color(36, 37, 42);    
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, width, 0, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

        gradientPanel.setLayout(null); 
        frmRismsATCS.setContentPane(gradientPanel);
        frmRismsATCS.setVisible(true);

        JLabel lblDateTime = new JLabel();
        lblDateTime.setBackground(Color.BLACK);
        lblDateTime.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblDateTime.setForeground(new Color(255, 255, 255));
        lblDateTime.setBounds(590, 10, 215, 30);
        frmRismsATCS.getContentPane().add(lblDateTime);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBackground(new Color(0, 51, 51));
        lblUserNUserTH3.setBounds(20, 10, 300, 25);
        frmRismsATCS.getContentPane().add(lblUserNUserTH3);
        lblUserNUserTH3.setForeground(new Color(255, 255, 255));
        lblUserNUserTH3.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));


        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ZoneId zone = ZoneId.systemDefault();
                ZonedDateTime zdt = ZonedDateTime.now(zone);
                Locale locale = Locale.getDefault();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
                String formattedDateTime = zdt.format(formatter);
                String country = locale.getDisplayCountry();

                lblDateTime.setText(formattedDateTime + " - " + country);
            }
        });
        timer.start();
        
        JButton btnHomePage = new JButton("Home Page");
        btnHomePage.setForeground(Color.WHITE);
        btnHomePage.setBackground(Color.BLACK);
        btnHomePage.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnHomePage.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnHomePage.setFocusPainted(false);
        btnHomePage.setBounds(15, 45, 130, 40);
        frmRismsATCS.getContentPane().add(btnHomePage);
        btnHomePage.addActionListener(e -> {
            frmRismsATCS.dispose();  
            new HomePageUCSection(username, user_type);
        });

        JButton btnListOfProducts = new JButton("List of Products");
        btnListOfProducts.setForeground(Color.WHITE);
        btnListOfProducts.setBackground(Color.BLACK);
        btnListOfProducts.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnListOfProducts.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnListOfProducts.setFocusPainted(false);
        btnListOfProducts.setBounds(15, 103, 130, 40);
        frmRismsATCS.getContentPane().add(btnListOfProducts);
        btnListOfProducts.addActionListener(e -> {
        	frmRismsATCS.dispose(); 
        	new ListOfProductsUCSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Add To Cart");
        btnAddToCart.setBackground(Color.BLACK);
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 158, 130, 40);
        frmRismsATCS.getContentPane().add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsATCS.dispose(); 
        	new AddToCartUCSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("View Cart");
        btnViewCart.setForeground(Color.WHITE);
        btnViewCart.setBackground(Color.BLACK);
        btnViewCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 213, 130, 40);
        frmRismsATCS.getContentPane().add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsATCS.dispose(); 
        	new ViewCartUCSection(username, user_type);
        });

        JButton btnPurchase = new JButton("Purchase");
        btnPurchase.setBackground(Color.BLACK);
        btnPurchase.setForeground(Color.WHITE);
        btnPurchase.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnPurchase.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnPurchase.setFocusPainted(false);
        btnPurchase.setBounds(15, 270, 130, 40);
        frmRismsATCS.getContentPane().add(btnPurchase);
        btnPurchase.addActionListener(e -> {
        	frmRismsATCS.dispose(); 
        	new PurchaseUCSection(username, user_type);
        });

        JButton btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setForeground(Color.WHITE);
        btnOrderHistory.setBackground(Color.BLACK);
        btnOrderHistory.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnOrderHistory.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnOrderHistory.setFocusPainted(false);
        btnOrderHistory.setBounds(15, 328, 130, 40);
        frmRismsATCS.getContentPane().add(btnOrderHistory);
        btnOrderHistory.addActionListener(e -> {
        	frmRismsATCS.dispose(); 
        	new OrderHistoryUCSection(username, user_type);
        });

        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setBackground(Color.BLACK);
        btnLogOut.setForeground(Color.WHITE);
        btnLogOut.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 385, 130, 40);
        frmRismsATCS.getContentPane().add(btnLogOut);
        
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsATCS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsATCS, "You have been logged out.");
                frmRismsATCS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsATCS, "Log-out cancelled.");
            }
        });

        JPanel panelContents = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 77, 64);    
                Color color2 = new Color(0, 150, 136);    
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, width, 0, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panelContents.setBackground(new Color(0, 139, 139)); 
        panelContents.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        panelContents.setBounds(159, 45, 625, 379);
        frmRismsATCS.getContentPane().add(panelContents);
        panelContents.setLayout(null);
        
        JLabel lblAddToCart = new JLabel("Add To Cart");
        lblAddToCart.setForeground(new Color(255, 255, 255));
        lblAddToCart.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddToCart.setFont(new Font("Cambria", Font.BOLD, 24));
        lblAddToCart.setBounds(145, 20, 310, 50);
        panelContents.add(lblAddToCart);

        JLabel lblItemId = new JLabel("Product ID:");
        lblItemId.setForeground(new Color(255, 255, 255));
        lblItemId.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblItemId.setBounds(170, 80, 75, 22);
        panelContents.add(lblItemId);

        JLabel lblItemName = new JLabel("Item Name:");
        lblItemName.setForeground(new Color(255, 255, 255));
        lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblItemName.setBounds(170, 140, 75, 22);
        panelContents.add(lblItemName);

        JLabel lblPrice = new JLabel("Price Per Kilo:");
        lblPrice.setForeground(new Color(255, 255, 255));
        lblPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPrice.setBounds(158, 200, 95, 22);
        panelContents.add(lblPrice);

        JLabel lblSacks = new JLabel("Avail (kg):");
        lblSacks.setForeground(new Color(255, 255, 255));
        lblSacks.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSacks.setBounds(180, 260, 68, 22);
        panelContents.add(lblSacks);

        product_id = new JTextField();
        product_id.setBounds(250, 80, 185, 27);
        product_id.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelContents.add(product_id);

        item_name = new JTextField();
        item_name.setBackground(new Color(255, 255, 255));
        item_name.setEditable(false);
        item_name.setBounds(250, 140, 185, 27);
        item_name.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelContents.add(item_name);
        
        price_per_kilo = new JTextField();
        price_per_kilo.setBackground(new Color(255, 255, 255));
        price_per_kilo.setEditable(false); 
        price_per_kilo.setBounds(250, 200, 185, 27);
        price_per_kilo.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelContents.add(price_per_kilo);

        available_stocks = new JTextField();
        available_stocks.setBounds(250, 260, 185, 27);
        available_stocks.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelContents.add(available_stocks);

        JButton btnATC = new JButton("Add To Cart");
        btnATC.setForeground(Color.WHITE);
        btnATC.setBackground(Color.BLACK);
        btnATC.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnATC.setFocusPainted(false);
        btnATC.setBounds(175, 310, 120, 40);
        panelContents.add(btnATC);

        // Auto-fill listener
        product_id.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String prodId = product_id.getText().trim();
                if (!prodId.isEmpty()) {
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        // INCLUDE  in your SELECT query
                        String query = "SELECT item_name, price_per_kilo FROM customer_products WHERE product_id = ?";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, prodId);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            item_name.setText(rs.getString("item_name"));
                            price_per_kilo.setText(String.valueOf(rs.getDouble("price_per_kilo")));
                        } else {
                            item_name.setText("");
                            price_per_kilo.setText("");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnATC.addActionListener(e -> {
            String prodId = product_id.getText().trim();
            String sackText = available_stocks.getText().trim();
            
            if (prodId.isEmpty() || sackText.isEmpty()) {
                JOptionPane.showMessageDialog(frmRismsATCS, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int sackAmount = Integer.parseInt(sackText);

                if (sackAmount <= 0) {
                    JOptionPane.showMessageDialog(frmRismsATCS, "Values must be greater than 0.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT item_name, price_per_kilo, available_stocks FROM customer_products WHERE product_id = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, prodId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        String itemName = rs.getString("item_name");
                        double price = rs.getDouble("price_per_kilo");
                        int availableStocks = rs.getInt("available_stocks");

                        if (sackAmount > availableStocks) {
                            JOptionPane.showMessageDialog(frmRismsATCS, "Stock exceeded. Available: " + availableStocks, "Stock Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String insert = "INSERT INTO customer_cart (customer_name, product_id, item_name, price_per_kilo, available_stocks) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insert);

                        insertStmt.setString(1, username); 
                        insertStmt.setString(2, prodId);
                        insertStmt.setString(3, itemName);
                        insertStmt.setDouble(4, price);
                        insertStmt.setInt(5, sackAmount);

                        insertStmt.executeUpdate();


                        JOptionPane.showMessageDialog(frmRismsATCS, "Item added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frmRismsATCS, "Product ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frmRismsATCS, "Please enter valid integer values (no decimals).", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        JButton btnCR = new JButton("Clear/Reset");
        btnCR.setForeground(Color.WHITE);
        btnCR.setBackground(Color.BLACK);
        btnCR.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCR.setFocusPainted(false);
        btnCR.setBounds(305, 310, 120, 40);
        panelContents.add(btnCR);
        btnCR.addActionListener(e -> {
            product_id.setText("");
            item_name.setText("");
            price_per_kilo.setText("");
            available_stocks.setText("");
        }); 
    }
}


