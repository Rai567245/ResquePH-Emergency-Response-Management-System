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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class OrderHistoryUCSection {

    private JFrame frmRismsOHS;
    private JTable OrderHistorytable;
    private String username;
    private String user_type;
    
    public OrderHistoryUCSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
        loadOrderHistoryData();
        frmRismsOHS.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                OrderHistoryUCSection window = new OrderHistoryUCSection();
                window.frmRismsOHS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public OrderHistoryUCSection() {
        initialize();
        loadOrderHistoryData();
    }

    private void initialize() {
        frmRismsOHS = new JFrame();
        frmRismsOHS.setBackground(Color.WHITE);
        frmRismsOHS.getContentPane().setBackground(Color.WHITE);
        frmRismsOHS.setTitle("Order History (Users/Customers Section)");
        frmRismsOHS.setBounds(350, 100, 820, 500);
        frmRismsOHS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsOHS.getContentPane().setLayout(null);

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
        frmRismsOHS.setContentPane(gradientPanel);
        frmRismsOHS.setVisible(true);

        JLabel lblDateTime = new JLabel();
        lblDateTime.setBackground(Color.BLACK);
        lblDateTime.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblDateTime.setForeground(new Color(255, 255, 255));
        lblDateTime.setBounds(590, 10, 215, 30);
        frmRismsOHS.getContentPane().add(lblDateTime);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBackground(new Color(0, 51, 51));
        lblUserNUserTH3.setBounds(20, 10, 300, 25);
        frmRismsOHS.getContentPane().add(lblUserNUserTH3);
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
        frmRismsOHS.getContentPane().add(btnHomePage);
        btnHomePage.addActionListener(e -> {
            frmRismsOHS.dispose();  
            new HomePageUCSection(username, user_type);
        });

        JButton btnListOfProducts = new JButton("List of Products");
        btnListOfProducts.setForeground(Color.WHITE);
        btnListOfProducts.setBackground(Color.BLACK);
        btnListOfProducts.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnListOfProducts.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnListOfProducts.setFocusPainted(false);
        btnListOfProducts.setBounds(15, 103, 130, 40);
        frmRismsOHS.getContentPane().add(btnListOfProducts);
        btnListOfProducts.addActionListener(e -> {
        	frmRismsOHS.dispose(); 
        	new ListOfProductsUCSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Add To Cart");
        btnAddToCart.setBackground(Color.BLACK);
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 158, 130, 40);
        frmRismsOHS.getContentPane().add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsOHS.dispose(); 
        	new AddToCartUCSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("View Cart");
        btnViewCart.setForeground(Color.WHITE);
        btnViewCart.setBackground(Color.BLACK);
        btnViewCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 213, 130, 40);
        frmRismsOHS.getContentPane().add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsOHS.dispose(); 
        	new ViewCartUCSection(username, user_type);
        });

        JButton btnPurchase = new JButton("Purchase");
        btnPurchase.setBackground(Color.BLACK);
        btnPurchase.setForeground(Color.WHITE);
        btnPurchase.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnPurchase.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnPurchase.setFocusPainted(false);
        btnPurchase.setBounds(15, 270, 130, 40);
        frmRismsOHS.getContentPane().add(btnPurchase);
        btnPurchase.addActionListener(e -> {
        	frmRismsOHS.dispose(); 
        	new PurchaseUCSection(username, user_type);
        });

        JButton btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setForeground(Color.WHITE);
        btnOrderHistory.setBackground(Color.BLACK);
        btnOrderHistory.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnOrderHistory.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnOrderHistory.setFocusPainted(false);
        btnOrderHistory.setBounds(15, 328, 130, 40);
        frmRismsOHS.getContentPane().add(btnOrderHistory);
        btnOrderHistory.addActionListener(e -> {
        	frmRismsOHS.dispose(); 
        	new OrderHistoryUCSection(username, user_type);
        });

        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setBackground(Color.BLACK);
        btnLogOut.setForeground(Color.WHITE);
        btnLogOut.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 385, 130, 40);
        frmRismsOHS.getContentPane().add(btnLogOut);
        
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsOHS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsOHS, "You have been logged out.");
                frmRismsOHS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsOHS, "Log-out cancelled.");
            }
        });
        
        // === Remove old labels from center panel
        // === Embed Order History Table instead
        String[] columnNames = { "Product ID", "Item Name", "Bought Stocks (kg)", "Total Price", "Date&Time" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        OrderHistorytable = new JTable(model);
        OrderHistorytable.setFont(new Font("Cambria", Font.PLAIN, 12));
        OrderHistorytable.setRowHeight(30);
        OrderHistorytable.setShowGrid(true);
        OrderHistorytable.setGridColor(Color.BLACK);

        JTableHeader header = OrderHistorytable.getTableHeader();
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Cambria", Font.BOLD, 12));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(144, 238, 144));
                c.setForeground(Color.BLACK);
                return c;
            }
        };

        for (int i = 0; i < OrderHistorytable.getColumnCount(); i++) {
            OrderHistorytable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(OrderHistorytable);
        scrollPane.setBounds(170, 45, 605, 380);
        scrollPane.setBorder(new LineBorder(Color.WHITE, 2));
        frmRismsOHS.getContentPane().add(scrollPane);
    }

    private void loadOrderHistoryData() {
        DefaultTableModel model = (DefaultTableModel) OrderHistorytable.getModel();
        model.setRowCount(0);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT product_id, item_name, available_stocks, total_price, purchase_datetime " +
                 "FROM customer_order_history WHERE customer_name = ? ORDER BY purchase_datetime DESC")) {

            pstmt.setString(1, username); 
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String prodID = rs.getString("product_id");
                String itemName = rs.getString("item_name");
                int AVStocks = rs.getInt("available_stocks");
                double tPrice = rs.getDouble("total_price");
                String purDaTime = rs.getString("purchase_datetime");
                model.addRow(new Object[]{prodID, itemName, AVStocks, tPrice, purDaTime});
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading order history data!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}