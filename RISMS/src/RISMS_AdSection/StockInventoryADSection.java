package RISMS_AdSection;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
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

public class StockInventoryADSection {

    private JFrame frmRismsSIS;
    private JTable table;
    private DefaultTableModel model;
    private Connection conn;
    private String username;
    private String user_type;
    
    public StockInventoryADSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
        loadDataFromDatabase();
        frmRismsSIS.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	StockInventoryADSection window = new StockInventoryADSection();
                window.frmRismsSIS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public StockInventoryADSection() {
        initialize();
        loadDataFromDatabase();
    }

    private void initialize() {
    	frmRismsSIS = new JFrame();
    	frmRismsSIS.setBackground(Color.WHITE);
    	frmRismsSIS.getContentPane().setBackground(Color.WHITE);
        frmRismsSIS.setTitle("Stocks Inventory (Admin Section)");
        frmRismsSIS.setBounds(350, 100, 820, 500);
        frmRismsSIS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsSIS.getContentPane().setLayout(null);

        JPanel borderPanel = new JPanel();
        borderPanel.setBackground(new Color(0, 139, 139));
        borderPanel.setBounds(0, 0, 806, 40);
        frmRismsSIS.getContentPane().add(borderPanel);
        borderPanel.setLayout(null);

        JLabel lblDateTime = new JLabel();
        lblDateTime.setFont(new Font("Cambria", Font.BOLD, 13));
        lblDateTime.setForeground(Color.WHITE);
        lblDateTime.setBounds(580, 5, 220, 30);
        borderPanel.add(lblDateTime);

        Timer timer = new Timer(1000, e -> {
            ZoneId zone = ZoneId.systemDefault();
            ZonedDateTime zdt = ZonedDateTime.now(zone);
            Locale locale = Locale.getDefault();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
            lblDateTime.setText(zdt.format(formatter) + " - " + locale.getDisplayCountry());
        });
        timer.start();

        JPanel lBarPanel = new JPanel();
        lBarPanel.setBackground(new Color(0, 139, 139));
        lBarPanel.setBounds(0, 40, 160, 423);
        lBarPanel.setLayout(null);
        frmRismsSIS.getContentPane().add(lBarPanel);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBounds(20, 10, 300, 25);
        borderPanel.add(lblUserNUserTH3);
        lblUserNUserTH3.setForeground(new Color(255, 255, 255));
        lblUserNUserTH3.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        
        JButton btnHomePage = new JButton("Home Page");
        btnHomePage.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnHomePage.setFocusPainted(false);
        btnHomePage.setBounds(15, 35, 130, 40);
        lBarPanel.add(btnHomePage);
        btnHomePage.addActionListener(e -> {
            frmRismsSIS.dispose(); 
            new HomePageADSection(username, user_type);
        });

        JButton btnAddProducts = new JButton("Add Products");
        btnAddProducts.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAddProducts.setFocusPainted(false);
        btnAddProducts.setBounds(15, 100, 130, 40);
        lBarPanel.add(btnAddProducts);
        btnAddProducts.addActionListener(e -> {
        	frmRismsSIS.dispose(); 
        	new AddProductsADSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Stocks Inventory");
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 165, 130, 40);
        lBarPanel.add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsSIS.dispose(); 
        	new StockInventoryADSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("Sales Report");
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 230, 130, 40);
        lBarPanel.add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsSIS.dispose(); 
        	new SalesReportADSection(username, user_type);
        });

        JButton btnRepGenerator = new JButton("Gen-Reports");
        btnRepGenerator.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRepGenerator.setFocusPainted(false);
        btnRepGenerator.setBounds(15, 295, 130, 40);
        lBarPanel.add(btnRepGenerator);
        btnRepGenerator.addActionListener(e -> {
        	frmRismsSIS.dispose(); 
        	new ReportsGeneratorADSection(username, user_type);
        });
        
        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 360, 130, 40);
        lBarPanel.add(btnLogOut);
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsSIS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsSIS, "You have been logged out.");
                frmRismsSIS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsSIS, "Log-out cancelled.");
            }
        });
        
        String[] columnNames = {"Item ID", "Product ID", "Item Name", "Price P/kg", "Available Stocks (kg)"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        
        // Table setup
        table = new JTable(model);
        table.setFont(new Font("Cambria", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Cambria", Font.BOLD, 10));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(144, 238, 144));
                c.setForeground(Color.BLACK);
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(186, 66, 592, 330);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
        frmRismsSIS.getContentPane().add(scrollPane);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(678, 406, 100, 30);
        btnSave.setBackground(new Color(65, 105, 225));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(e -> saveChangesToDatabase());
        frmRismsSIS.getContentPane().add(btnSave);

        JButton btnAdd = new JButton("Add");
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAdd.setFocusPainted(false);
        btnAdd.setBackground(new Color(0, 139, 139));
        btnAdd.setBounds(469, 406, 100, 30);
        btnAdd.addActionListener(e -> addToCustomerProducts());
        frmRismsSIS.getContentPane().add(btnAdd);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setFocusPainted(false);
        btnDelete.setBackground(new Color(255, 0, 0));
        btnDelete.setBounds(574, 406, 100, 30);
        frmRismsSIS.getContentPane().add(btnDelete);
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a product to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product from customer_products?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");

                int itemId = (int) model.getValueAt(selectedRow, 0); 

                String deleteSQL = "DELETE FROM customer_products WHERE item_id = ?";
                PreparedStatement pst = conn.prepareStatement(deleteSQL);
                pst.setInt(1, itemId);
                int affectedRows = pst.executeUpdate();
                pst.close();
                conn.close();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(null, "Product deleted from customer_products.");
                } else {
                    JOptionPane.showMessageDialog(null, "No matching product found in customer_products.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error deleting product: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void loadDataFromDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");

            String sql = "SELECT * FROM admin_stocks_inventory";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String productId = rs.getString("product_id");
                String itemName = rs.getString("item_name");
                double pricePerKilo = rs.getDouble("price_per_kilo");
                int availableStocks = rs.getInt("available_stocks");

                model.addRow(new Object[]{itemId, productId, itemName, pricePerKilo, availableStocks});
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveChangesToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");

            for (int i = 0; i < model.getRowCount(); i++) {
                int itemId = (int) model.getValueAt(i, 0);
                int availableStocks = Integer.parseInt(model.getValueAt(i, 4).toString());

                // Update stocks_inventory
                String sqlStock = "UPDATE admin_stocks_inventory SET available_stocks = ? WHERE item_id = ?";
                PreparedStatement pstStock = conn.prepareStatement(sqlStock);
                pstStock.setInt(1, availableStocks);
                pstStock.setInt(2, itemId);
                pstStock.executeUpdate();
                pstStock.close();

                // Also update customer_products
                String sqlCustomer = "UPDATE customer_products SET available_stocks = ? WHERE item_id = ?";
                PreparedStatement pstCustomer = conn.prepareStatement(sqlCustomer);
                pstCustomer.setInt(1, availableStocks);
                pstCustomer.setInt(2, itemId);
                pstCustomer.executeUpdate();
                pstCustomer.close();
            }

            conn.close();
            JOptionPane.showMessageDialog(null, "Changes saved successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving changes: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void addToCustomerProducts() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to add.");
            return;
        }

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");

            int itemId = (int) model.getValueAt(selectedRow, 0);

            // Check if product is already in customer_products
            String checkSql = "SELECT * FROM customer_products WHERE item_id = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, itemId);
            rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Product is already added to customer products!");
                return;
            }
            
            String productId = model.getValueAt(selectedRow, 1).toString();
            String itemName = model.getValueAt(selectedRow, 2).toString();
            double pricePerKilo = Double.parseDouble(model.getValueAt(selectedRow, 3).toString());
            int AVStocks = Integer.parseInt(model.getValueAt(selectedRow, 4).toString());

            String insertSql = "INSERT INTO customer_products (item_id, product_id, item_name, price_per_kilo, available_stocks) VALUES (?, ?, ?, ?, ?)";
            insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, itemId);
            insertStmt.setString(2, productId);
            insertStmt.setString(3, itemName);
            insertStmt.setDouble(4, pricePerKilo);
            insertStmt.setInt(5, AVStocks);

            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Product added to customer products!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error adding product: " + e.getMessage());
            // You can comment out the next line if you don't want it printed to the console:
            // e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (checkStmt != null) checkStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                // Silent close
            }
        }
    }
}