package RISMS_CusSection;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import RISMS_LogSection.LoginSection;

public class ViewCartUCSection {

    private JFrame frmRismsVCS;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private String username;
    private String user_type;
    
    public ViewCartUCSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type; 
        initialize();
        loadCartData(username); 
        frmRismsVCS.setVisible(true);
    }

    private final String DB_URL = "jdbc:mysql://localhost:3306/RISMS_DB";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewCartUCSection window = new ViewCartUCSection();
                window.frmRismsVCS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ViewCartUCSection() {
        initialize();
        loadCartData(username); 
    }

    private void initialize() {
        frmRismsVCS = new JFrame();
        frmRismsVCS.setBackground(Color.WHITE);
        frmRismsVCS.getContentPane().setBackground(Color.WHITE);
        frmRismsVCS.setTitle("View Cart (Users/Customers Section)");
        frmRismsVCS.setBounds(350, 100, 820, 500);
        frmRismsVCS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsVCS.getContentPane().setLayout(null);

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
        frmRismsVCS.setContentPane(gradientPanel);
        frmRismsVCS.setVisible(true);

        JLabel lblDateTime = new JLabel();
        lblDateTime.setBackground(Color.BLACK);
        lblDateTime.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblDateTime.setForeground(new Color(255, 255, 255));
        lblDateTime.setBounds(590, 10, 215, 30);
        frmRismsVCS.getContentPane().add(lblDateTime);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBackground(new Color(0, 51, 51));
        lblUserNUserTH3.setBounds(20, 10, 300, 25);
        frmRismsVCS.getContentPane().add(lblUserNUserTH3);
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
        frmRismsVCS.getContentPane().add(btnHomePage);
        btnHomePage.addActionListener(e -> {
            frmRismsVCS.dispose();  
            new HomePageUCSection(username, user_type);
        });

        JButton btnListOfProducts = new JButton("List of Products");
        btnListOfProducts.setForeground(Color.WHITE);
        btnListOfProducts.setBackground(Color.BLACK);
        btnListOfProducts.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnListOfProducts.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnListOfProducts.setFocusPainted(false);
        btnListOfProducts.setBounds(15, 103, 130, 40);
        frmRismsVCS.getContentPane().add(btnListOfProducts);
        btnListOfProducts.addActionListener(e -> {
        	frmRismsVCS.dispose(); 
        	new ListOfProductsUCSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Add To Cart");
        btnAddToCart.setBackground(Color.BLACK);
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 158, 130, 40);
        frmRismsVCS.getContentPane().add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsVCS.dispose(); 
        	new AddToCartUCSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("View Cart");
        btnViewCart.setForeground(Color.WHITE);
        btnViewCart.setBackground(Color.BLACK);
        btnViewCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 213, 130, 40);
        frmRismsVCS.getContentPane().add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsVCS.dispose(); 
        	new ViewCartUCSection(username, user_type);
        });

        JButton btnPurchase = new JButton("Purchase");
        btnPurchase.setBackground(Color.BLACK);
        btnPurchase.setForeground(Color.WHITE);
        btnPurchase.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnPurchase.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnPurchase.setFocusPainted(false);
        btnPurchase.setBounds(15, 270, 130, 40);
        frmRismsVCS.getContentPane().add(btnPurchase);
        btnPurchase.addActionListener(e -> {
        	frmRismsVCS.dispose(); 
        	new PurchaseUCSection(username, user_type);
        });

        JButton btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setForeground(Color.WHITE);
        btnOrderHistory.setBackground(Color.BLACK);
        btnOrderHistory.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnOrderHistory.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnOrderHistory.setFocusPainted(false);
        btnOrderHistory.setBounds(15, 328, 130, 40);
        frmRismsVCS.getContentPane().add(btnOrderHistory);
        btnOrderHistory.addActionListener(e -> {
        	frmRismsVCS.dispose(); 
        	new OrderHistoryUCSection(username, user_type);
        });

        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setBackground(Color.BLACK);
        btnLogOut.setForeground(Color.WHITE);
        btnLogOut.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 385, 130, 40);
        frmRismsVCS.getContentPane().add(btnLogOut);
        
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsVCS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsVCS, "You have been logged out.");
                frmRismsVCS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsVCS, "Log-out cancelled.");
            }
        });

        String[] columns = {"Product ID", "Item Name", "Price P/kg", "Avail Stocks (kg)"};
        tableModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(tableModel);
        cartTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cartTable.setRowHeight(25);
        cartTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        JTableHeader header = cartTable.getTableHeader();
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Cambria", Font.BOLD, 13));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(144, 238, 144));
                c.setForeground(Color.BLACK);
                return c;
            }
        };

        for (int i = 0; i < cartTable.getColumnCount(); i++) {
            cartTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(new LineBorder(Color.WHITE, 2));
        scrollPane.setBounds(160, 45, 620, 380);
        frmRismsVCS.getContentPane().add(scrollPane);
        
        // Add double-click listener for row deletion
        cartTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && cartTable.getSelectedRow() != -1) {
                    int confirm = JOptionPane.showConfirmDialog(
                        frmRismsVCS,
                        "Do you want to delete the selected item?",
                        "Delete Confirmation",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                    	deleteSelectedItem(username);
                    }
                }
            }
        });
    }

    private void loadCartData(String customerName) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customer_cart WHERE customer_name = ?")) {

            stmt.setString(1, customerName);  
            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0);

            while (rs.next()) {
                String productId = rs.getString("product_id");
                String itemName = rs.getString("item_name");
                double pricePerKilo = rs.getDouble("price_per_kilo");
                int AVStocks = rs.getInt("available_stocks");
                String formattedPrice = String.format("%.2f", pricePerKilo);

                tableModel.addRow(new Object[]{productId, itemName, formattedPrice, AVStocks});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frmRismsVCS, "Database error: " + e.getMessage());
        }
    }


    private void deleteSelectedItem(String customerName) {
        int row = cartTable.getSelectedRow();
        if (row != -1) {
            String productId = (String) tableModel.getValueAt(row, 0);
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM customer_cart WHERE product_id = ? AND customer_name = ?")) {

                stmt.setString(1, productId);
                stmt.setString(2, customerName);

                int affected = stmt.executeUpdate();
                if (affected > 0) {
                    tableModel.removeRow(row);
                } else {
                    JOptionPane.showMessageDialog(frmRismsVCS, "Item not found in database.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frmRismsVCS, "Database error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(frmRismsVCS, "Please select an item to delete.");
        }
    }
}