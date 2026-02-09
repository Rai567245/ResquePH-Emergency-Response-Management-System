package RISMS_CusSection;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import RISMS_LogSection.LoginSection;

public class PurchaseUCSection {

    private JFrame frmRismsPS;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JTextField product_id;
    private JTextField item_name;
    private JTextField price_per_kilo;
    private JTextField available_stocks;
    private JTextField total_price;
    private String username;
    private String user_type;
    
    public PurchaseUCSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
        frmRismsPS.setVisible(true);
    }
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PurchaseUCSection window = new PurchaseUCSection();
                window.frmRismsPS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public PurchaseUCSection() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmRismsPS = new JFrame();
        frmRismsPS.setBackground(Color.WHITE);
        frmRismsPS.getContentPane().setBackground(Color.WHITE);
        frmRismsPS.setTitle("Purchase (Users/Customers Section)");
        frmRismsPS.setBounds(350, 100, 820, 500);
        frmRismsPS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsPS.getContentPane().setLayout(null);

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
        frmRismsPS.setContentPane(gradientPanel);
        frmRismsPS.setVisible(true);

        JLabel lblDateTime = new JLabel();
        lblDateTime.setBackground(Color.BLACK);
        lblDateTime.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblDateTime.setForeground(new Color(255, 255, 255));
        lblDateTime.setBounds(590, 10, 215, 30);
        frmRismsPS.getContentPane().add(lblDateTime);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBackground(new Color(0, 51, 51));
        lblUserNUserTH3.setBounds(20, 10, 300, 25);
        frmRismsPS.getContentPane().add(lblUserNUserTH3);
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
        frmRismsPS.getContentPane().add(btnHomePage);
        btnHomePage.addActionListener(e -> {
            frmRismsPS.dispose();  
            new HomePageUCSection(username, user_type);
        });

        JButton btnListOfProducts = new JButton("List of Products");
        btnListOfProducts.setForeground(Color.WHITE);
        btnListOfProducts.setBackground(Color.BLACK);
        btnListOfProducts.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnListOfProducts.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnListOfProducts.setFocusPainted(false);
        btnListOfProducts.setBounds(15, 103, 130, 40);
        frmRismsPS.getContentPane().add(btnListOfProducts);
        btnListOfProducts.addActionListener(e -> {
        	frmRismsPS.dispose(); 
        	new ListOfProductsUCSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Add To Cart");
        btnAddToCart.setBackground(Color.BLACK);
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 158, 130, 40);
        frmRismsPS.getContentPane().add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsPS.dispose(); 
        	new AddToCartUCSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("View Cart");
        btnViewCart.setForeground(Color.WHITE);
        btnViewCart.setBackground(Color.BLACK);
        btnViewCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 213, 130, 40);
        frmRismsPS.getContentPane().add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsPS.dispose(); 
        	new ViewCartUCSection(username, user_type);
        });

        JButton btnPurchase = new JButton("Purchase");
        btnPurchase.setBackground(Color.BLACK);
        btnPurchase.setForeground(Color.WHITE);
        btnPurchase.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnPurchase.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnPurchase.setFocusPainted(false);
        btnPurchase.setBounds(15, 270, 130, 40);
        frmRismsPS.getContentPane().add(btnPurchase);
        btnPurchase.addActionListener(e -> {
        	frmRismsPS.dispose(); 
        	new PurchaseUCSection(username, user_type);
        });

        JButton btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setForeground(Color.WHITE);
        btnOrderHistory.setBackground(Color.BLACK);
        btnOrderHistory.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnOrderHistory.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnOrderHistory.setFocusPainted(false);
        btnOrderHistory.setBounds(15, 328, 130, 40);
        frmRismsPS.getContentPane().add(btnOrderHistory);
        btnOrderHistory.addActionListener(e -> {
        	frmRismsPS.dispose(); 
        	new OrderHistoryUCSection(username, user_type);
        });

        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setBackground(Color.BLACK);
        btnLogOut.setForeground(Color.WHITE);
        btnLogOut.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 385, 130, 40);
        frmRismsPS.getContentPane().add(btnLogOut);
        
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsPS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsPS, "You have been logged out.");
                frmRismsPS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsPS, "Log-out cancelled.");
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
        panelContents.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelContents.setBounds(164, 45, 621, 381);
        frmRismsPS.getContentPane().add(panelContents);
        panelContents.setLayout(null);

        String[] columns = {"Product ID", "Item Name", "Price P/kg", "Avail Stocks (kg)"};
        tableModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(tableModel);
        cartTable.setFont(new Font("SansSerif", Font.PLAIN, 8));
        cartTable.setRowHeight(30);
        cartTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 8));

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBounds(240, 50, 360, 240);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelContents.add(scrollPane);

        cartTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    
                    Object prodID = cartTable.getValueAt(selectedRow, 0);
                    Object itName = cartTable.getValueAt(selectedRow, 1);
                    Object prcPKg = cartTable.getValueAt(selectedRow, 2);
                    Object availStock = cartTable.getValueAt(selectedRow, 3);
                    
                    product_id.setText(prodID.toString());
                    item_name.setText(itName.toString());
                    price_per_kilo.setText(prcPKg.toString());
                    available_stocks.setText(availStock.toString());
                    calculateTotalPrice(); 
                }
            }
        });

        JLabel lblProdID = new JLabel("Product ID:");
        lblProdID.setForeground(new Color(0, 0, 0));
        lblProdID.setFont(new Font("Tahoma", Font.BOLD, 8));
        lblProdID.setBounds(50, 80, 75, 20);
        panelContents.add(lblProdID);

        JLabel lblItName = new JLabel("Item Name:");
        lblItName.setForeground(new Color(0, 0, 0));
        lblItName.setFont(new Font("Tahoma", Font.BOLD, 8));
        lblItName.setBounds(50, 120, 75, 20);
        panelContents.add(lblItName);

        JLabel lblPrc = new JLabel("Price (P/kg):");
        lblPrc.setForeground(new Color(0, 0, 0));
        lblPrc.setFont(new Font("Tahoma", Font.BOLD, 8));
        lblPrc.setBounds(40, 160, 82, 20);
        panelContents.add(lblPrc);
        
        JLabel lblSrc = new JLabel("Kilograms:");
        lblSrc.setForeground(new Color(0, 0, 0));
        lblSrc.setFont(new Font("Tahoma", Font.BOLD, 8));
        lblSrc.setBounds(53, 200, 64, 20);
        panelContents.add(lblSrc);
        
        JLabel lblTotPrc = new JLabel("Total Price:");
        lblTotPrc.setForeground(new Color(0, 0, 0));
        lblTotPrc.setFont(new Font("Tahoma", Font.BOLD, 8));
        lblTotPrc.setBounds(50, 240, 75, 20);
        panelContents.add(lblTotPrc);

        product_id = new JTextField();
        product_id.setFont(new Font("Tahoma", Font.PLAIN, 8));
        product_id.setColumns(10);
        product_id.setBounds(100, 80, 120, 20);
        panelContents.add(product_id);

        item_name = new JTextField();
        item_name.setFont(new Font("Tahoma", Font.PLAIN, 8));
        item_name.setColumns(10);
        item_name.setBounds(100, 120, 120, 20);
        panelContents.add(item_name);

        price_per_kilo = new JTextField();
        price_per_kilo.setFont(new Font("Tahoma", Font.PLAIN, 8));
        price_per_kilo.setColumns(10);
        price_per_kilo.setBounds(100, 160, 120, 20);
        panelContents.add(price_per_kilo);
        
        available_stocks = new JTextField();
        available_stocks.setFont(new Font("Tahoma", Font.PLAIN, 8));
        available_stocks.setText("");
        available_stocks.setColumns(10);
        available_stocks.setBounds(100, 200, 120, 20);
        panelContents.add(available_stocks);
        
        total_price = new JTextField();
        total_price.setFont(new Font("Tahoma", Font.PLAIN, 8));
        total_price.setText("");
        total_price.setColumns(10);
        total_price.setBounds(100, 240, 120, 20);
        panelContents.add(total_price);
        
        // Add DocumentListeners to update total price automatically
        price_per_kilo.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculateTotalPrice(); }
            public void removeUpdate(DocumentEvent e) { calculateTotalPrice(); }
            public void changedUpdate(DocumentEvent e) { calculateTotalPrice(); }
        });

        available_stocks.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculateTotalPrice(); }
            public void removeUpdate(DocumentEvent e) { calculateTotalPrice(); }
            public void changedUpdate(DocumentEvent e) { calculateTotalPrice(); }
        });

        JButton btnPCH = new JButton("Purchase");
        btnPCH.setForeground(Color.WHITE);
        btnPCH.setBackground(Color.BLACK);
        btnPCH.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnPCH.setFocusPainted(false);
        btnPCH.setBounds(90, 310, 100, 40);
        panelContents.add(btnPCH);
        btnPCH.addActionListener(e -> {
            String id = product_id.getText().trim();
            String name = item_name.getText().trim();
            String price = price_per_kilo.getText().trim();
            String qty = available_stocks.getText().trim();

            if (id.isEmpty() || name.isEmpty() || price.isEmpty() || qty.isEmpty()) {
                JOptionPane.showMessageDialog(frmRismsPS, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int purchaseQty = Integer.parseInt(qty);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");
                conn.setAutoCommit(false); 

                // 1. Get current stock from customer_products
                PreparedStatement getCustomerStock = conn.prepareStatement("SELECT available_stocks FROM customer_products WHERE product_id = ?");
                getCustomerStock.setString(1, id);
                ResultSet rs1 = getCustomerStock.executeQuery();

                if (!rs1.next()) {
                    JOptionPane.showMessageDialog(frmRismsPS, "Product not found in customer_products.", "Error", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    conn.close();
                    return;
                }

                int customerStock = rs1.getInt("available_stocks");
                if (purchaseQty > customerStock) {
                    JOptionPane.showMessageDialog(frmRismsPS, "Insufficient stock in customer_products.", "Stock Error", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    conn.close();
                    return;
                }

                // 2. Get current stock from admin_stocks_inventory
                PreparedStatement getInventoryStock = conn.prepareStatement("SELECT available_stocks FROM admin_stocks_inventory WHERE product_id = ?");
                getInventoryStock.setString(1, id);
                ResultSet rs2 = getInventoryStock.executeQuery();

                if (!rs2.next()) {
                    JOptionPane.showMessageDialog(frmRismsPS, "Product not found in admin_stocks_inventory.", "Error", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    conn.close();
                    return;
                }

                int inventoryStock = rs2.getInt("available_stocks");
                if (purchaseQty > inventoryStock) {
                    JOptionPane.showMessageDialog(frmRismsPS, "Insufficient stock in inventory.", "Stock Error", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    conn.close();
                    return;
                }

                // 3. Update customer_products
                PreparedStatement updateCustomerStock = conn.prepareStatement("UPDATE customer_products SET available_stocks = ? WHERE product_id = ?");
                updateCustomerStock.setInt(1, customerStock - purchaseQty);
                updateCustomerStock.setString(2, id);
                updateCustomerStock.executeUpdate();

                // 4. Update admin_stocks_inventory
                PreparedStatement updateInventoryStock = conn.prepareStatement("UPDATE admin_stocks_inventory SET available_stocks = ? WHERE product_id = ?");
                updateInventoryStock.setInt(1, inventoryStock - purchaseQty);
                updateInventoryStock.setString(2, id);
                updateInventoryStock.executeUpdate();

                // 5. Insert into order history
                String insertQuery = "INSERT INTO customer_order_history (customer_name, product_id, item_name, price_per_kilo, available_stocks, total_price) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertOrderHistory = conn.prepareStatement(insertQuery);
                insertOrderHistory.setString(1, username);  
                insertOrderHistory.setString(2, id);
                insertOrderHistory.setString(3, name);
                insertOrderHistory.setDouble(4, Double.parseDouble(price));
                insertOrderHistory.setInt(5, purchaseQty);
                double totalPrice = Double.parseDouble(price) * purchaseQty; 
                insertOrderHistory.setDouble(6, totalPrice);
                insertOrderHistory.executeUpdate();
                
                // 6. Insert into sales report
                String insertQuery2 = "INSERT INTO admin_sales_report (customer_name, product_id, item_name, price_per_kilo, available_stocks, total_price) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertSalesReport = conn.prepareStatement(insertQuery2);
                insertSalesReport.setString(1, username);  
                insertSalesReport.setString(2, id);
                insertSalesReport.setString(3, name);
                insertSalesReport.setDouble(4, Double.parseDouble(price));
                insertSalesReport.setInt(5, purchaseQty);
                double totalSales = Double.parseDouble(price) * purchaseQty; 
                insertSalesReport.setDouble(6, totalSales);
                insertSalesReport.executeUpdate();
                
                // 7. Delete from customer_cart
                PreparedStatement deleteFromCart = conn.prepareStatement("DELETE FROM customer_cart WHERE product_id = ?");
                deleteFromCart.setString(1, id);
                deleteFromCart.executeUpdate();

                conn.commit();
                conn.close();

                JOptionPane.showMessageDialog(frmRismsPS, "Purchase successful! Stock updated.");
                tableModel.setRowCount(0); 

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frmRismsPS, "Error processing purchase.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnShowCart = new JButton("Show Cart");
        btnShowCart.setBackground(Color.BLACK);
        btnShowCart.setForeground(Color.WHITE);
        btnShowCart.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnShowCart.setFocusPainted(false);
        btnShowCart.setBounds(360, 310, 120, 40);
        panelContents.add(btnShowCart);
        
        JPanel purchasePanel = new JPanel();
        purchasePanel.setBounds(25, 50, 213, 240);
        panelContents.add(purchasePanel);
  
        btnShowCart.addActionListener(e -> {
            loadCartData(username);
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(frmRismsPS, "The cart is empty.", "Cart Status", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void calculateTotalPrice() {
        try {
            double price = Double.parseDouble(price_per_kilo.getText());
            int sacks = Integer.parseInt(available_stocks.getText());
            double total = price * sacks;
            total_price.setText(String.format("%.2f", total));
        } catch (NumberFormatException e) {
            total_price.setText("");
        }
    }

    /**
     * Load cart data from the database.
     */
    private void loadCartData(String customerName) {
        try {
            tableModel.setRowCount(0); // Clear existing rows
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");

            String query = "SELECT * FROM customer_cart WHERE customer_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username); 

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String prodID = rs.getString("product_id");
                String itName = rs.getString("item_name");
                double prcPKg = rs.getDouble("price_per_kilo");
                int AVStocks = rs.getInt("available_stocks");

                tableModel.addRow(new Object[]{prodID, itName, prcPKg, AVStocks});
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frmRismsPS, "Error loading cart data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}