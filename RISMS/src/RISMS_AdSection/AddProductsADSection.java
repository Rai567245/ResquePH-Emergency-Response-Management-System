package RISMS_AdSection;

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
import java.sql.SQLException;
import java.sql.Types;
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

public class AddProductsADSection {

    private JFrame frmRismsAPS;
    private JTextField product_id;
    private JTextField item_name;
    private JTextField price_per_kilo;
    private JTextField available_stocks;
    private JTextField item_id;
    private String username;
    private String user_type;

    public AddProductsADSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
        frmRismsAPS.setVisible(true);
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/RISMS_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AddProductsADSection window = new AddProductsADSection();
                window.frmRismsAPS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AddProductsADSection() {
        initialize();
    }

    private void initialize() {
        frmRismsAPS = new JFrame();
        frmRismsAPS.setBackground(Color.WHITE);
        frmRismsAPS.getContentPane().setBackground(Color.WHITE);
        frmRismsAPS.setTitle("Add Products (Admin Section)");
        frmRismsAPS.setBounds(350, 100, 820, 500);
        frmRismsAPS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsAPS.getContentPane().setLayout(null);

        // Custom JPanel with gradient paint
        JPanel gradientPanel = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(36, 37, 42);     // Dark green
                Color color2 = new Color(36, 37, 42);    // Slightly lighter dark green
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, width, 0, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        
        gradientPanel.setLayout(null); // So you can manually place components
        frmRismsAPS.setContentPane(gradientPanel);
        frmRismsAPS.setVisible(true);
        
        JLabel lblDateTime = new JLabel();
        lblDateTime.setBackground(new Color(255, 255, 255));
        lblDateTime.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblDateTime.setForeground(Color.WHITE);
        lblDateTime.setBounds(590, 10, 220, 30);
        frmRismsAPS.getContentPane().add(lblDateTime);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBounds(20, 15, 300, 25);
        frmRismsAPS.getContentPane().add(lblUserNUserTH3);
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
        btnHomePage.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnHomePage.setFocusPainted(false);
        btnHomePage.setBounds(15, 45, 130, 40);
        frmRismsAPS.getContentPane().add(btnHomePage);
        btnHomePage.addActionListener(e -> {
            frmRismsAPS.dispose(); 
            new HomePageADSection(username, user_type);
        });

        JButton btnAddProducts = new JButton("Add Products");
        btnAddProducts.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAddProducts.setFocusPainted(false);
        btnAddProducts.setBounds(15, 115, 130, 40);
        frmRismsAPS.getContentPane().add(btnAddProducts);
        btnAddProducts.addActionListener(e -> {
        	frmRismsAPS.dispose(); 
        	new AddProductsADSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Stocks Inventory");
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 183, 130, 40);
        frmRismsAPS.getContentPane().add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsAPS.dispose(); 
        	new StockInventoryADSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("Sales Report");
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 248, 130, 40);
        frmRismsAPS.getContentPane().add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsAPS.dispose(); 
        	new SalesReportADSection(username, user_type);
        });

        JButton btnRepGenerator = new JButton("Gen-Reports");
        btnRepGenerator.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRepGenerator.setFocusPainted(false);
        btnRepGenerator.setBounds(15, 318, 130, 40);
        frmRismsAPS.getContentPane().add(btnRepGenerator);
        btnRepGenerator.addActionListener(e -> {
        	frmRismsAPS.dispose(); 
        	new ReportsGeneratorADSection(username, user_type);
        });
        
        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 385, 130, 40);
        frmRismsAPS.getContentPane().add(btnLogOut);
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsAPS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsAPS, "You have been logged out.");
                frmRismsAPS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsAPS, "Log-out cancelled.");
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
        panelContents.setBounds(160, 45, 625, 380);
        panelContents.setLayout(null);
        frmRismsAPS.getContentPane().add(panelContents);

        JLabel lblAddToCart = new JLabel("ADD PRODUCTS");
        lblAddToCart.setForeground(new Color(255, 255, 255));
        lblAddToCart.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddToCart.setFont(new Font("Cambria", Font.BOLD, 24));
        lblAddToCart.setBounds(100, 20, 400, 50);
        panelContents.add(lblAddToCart);

        JLabel lblItID = new JLabel("Item ID:");
        lblItID.setForeground(Color.WHITE);
        lblItID.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblItID.setBounds(220, 85, 63, 22);
        panelContents.add(lblItID);

        JLabel lblIProdID = new JLabel("Product ID:");
        lblIProdID.setForeground(Color.WHITE);
        lblIProdID.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIProdID.setBounds(200, 130, 75, 22);
        panelContents.add(lblIProdID);

        JLabel lblItName = new JLabel("Item Name:");
        lblItName.setForeground(Color.WHITE);
        lblItName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblItName.setBounds(200, 175, 75, 22);
        panelContents.add(lblItName);

        JLabel lblPrice = new JLabel("Price Per Kilo:");
        lblPrice.setForeground(Color.WHITE);
        lblPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPrice.setBounds(185, 220, 95, 22);
        panelContents.add(lblPrice);

        JLabel lblAvStocks = new JLabel("Available Stocks:");
        lblAvStocks.setForeground(Color.WHITE);
        lblAvStocks.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblAvStocks.setBounds(165, 265, 109, 22);
        panelContents.add(lblAvStocks);

        item_id = new JTextField();
        item_id.setBounds(280, 85, 160, 25);
        panelContents.add(item_id);

        product_id = new JTextField();
        product_id.setBounds(280, 130, 160, 25);
        panelContents.add(product_id);

        item_name = new JTextField();
        item_name.setBounds(280, 175, 160, 25);
        panelContents.add(item_name);

        price_per_kilo = new JTextField();
        price_per_kilo.setBounds(280, 220, 160, 25);
        panelContents.add(price_per_kilo);

        available_stocks = new JTextField();
        available_stocks.setBounds(280, 265, 160, 25);
        panelContents.add(available_stocks);

        JButton btnAP = new JButton("Add Product");
        btnAP.setForeground(new Color(255, 255, 255));
        btnAP.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAP.setBorder(new LineBorder(Color.WHITE, 2));
        btnAP.setBackground(new Color(0, 128, 0));
        btnAP.setFocusPainted(false);
        btnAP.setBounds(130, 320, 120, 40);
        panelContents.add(btnAP);

        JButton btnDP = new JButton("Delete Product");
        btnDP.setForeground(new Color(255, 255, 255));
        btnDP.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDP.setFocusPainted(false);
        btnDP.setBorder(new LineBorder(Color.WHITE, 2));
        btnDP.setBackground(new Color(255, 0, 0));
        btnDP.setBounds(250, 320, 120, 40);
        panelContents.add(btnDP);
        btnDP.addActionListener(e -> {
            String itID = item_id.getText().trim();

            if (itID.isEmpty()) {
                JOptionPane.showMessageDialog(panelContents, "Please enter a Item ID to delete.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(frmRismsAPS, "Are you sure you want to delete the item with ID: " + itID + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) return;

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "DELETE FROM admin_stocks_inventory WHERE item_id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, itID);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(frmRismsAPS, "Product deleted successfully!");
                    item_id.setText("");
                    product_id.setText("");
                    item_name.setText("");
                    price_per_kilo.setText("");
                    available_stocks.setText("");
                } else {
                    JOptionPane.showMessageDialog(frmRismsAPS, "No product found with the given Product ID.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frmRismsAPS, "Error deleting product: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JButton btnCR = new JButton("Clear/Reset"); 
        btnCR.setForeground(new Color(255, 255, 255));
        btnCR.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCR.setBorder(new LineBorder(Color.WHITE, 2));
        btnCR.setFocusPainted(false);
        btnCR.setBackground(new Color(0, 0, 0));
        btnCR.setBounds(370, 320, 120, 40);
        panelContents.add(btnCR);
        btnAP.addActionListener(e -> {
        	String prodId = product_id.getText().trim();
        	String itemName = item_name.getText().trim();
        	String priceStr = price_per_kilo.getText().trim();
        	String stocksStr = available_stocks.getText().trim();

        	if (prodId.isEmpty() || itemName.isEmpty() || priceStr.isEmpty()) {
        	    JOptionPane.showMessageDialog(frmRismsAPS, "Please fill in all required fields.", "Missing Fields", JOptionPane.ERROR_MESSAGE);
        	    return;
        	}

        	try {
        	    double price = Double.parseDouble(priceStr);
        	    Integer stocks = stocksStr.isEmpty() ? null : Integer.parseInt(stocksStr);

        	    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        	    String query = "INSERT INTO admin_stocks_inventory (product_id, item_name, price_per_kilo, available_stocks) VALUES (?, ?, ?, ?)";
        	    PreparedStatement stmt = conn.prepareStatement(query);
        	    stmt.setString(1, prodId);
        	    stmt.setString(2, itemName);
        	    stmt.setDouble(3, price);
        	    if (stocks == null) {
        	        stmt.setNull(4, Types.INTEGER);
        	    } else {
        	        stmt.setInt(4, stocks);
        	    }

        	    int rowsInserted = stmt.executeUpdate();
        	    if (rowsInserted > 0) {
        	        JOptionPane.showMessageDialog(frmRismsAPS, "Product added successfully!");
        	        product_id.setText("");
        	        item_name.setText("");
        	        price_per_kilo.setText("");
        	        available_stocks.setText("");
        	    }
        	    conn.close();
        	} catch (NumberFormatException nfe) {
        	    JOptionPane.showMessageDialog(frmRismsAPS, "Invalid number format for price or stocks.", "Input Error", JOptionPane.ERROR_MESSAGE);
        	} catch (SQLException ex) {
        	    JOptionPane.showMessageDialog(frmRismsAPS, "Database error: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        	    ex.printStackTrace();
        	}
        });

        
        btnCR.addActionListener(e -> {
        	item_id.setText("");
            product_id.setText("");
            item_name.setText("");
            price_per_kilo.setText("");
            available_stocks.setText("");
        });
    }
}