package RISMS_AdSection;

// AWT imports
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
// SQL imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
// Time & Locale imports
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

// Custom import
import RISMS_LogSection.LoginSection;

public class SalesReportADSection {

    private JFrame frmRismsSRS;
    private JTable OrderHistorytable;
    private String username;
    private String user_type;
    
    public SalesReportADSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
        loadOrderHistoryData();
        frmRismsSRS.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SalesReportADSection window = new SalesReportADSection();
                window.frmRismsSRS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SalesReportADSection() {
        initialize();
        loadOrderHistoryData();
    }

    private void initialize() {
        frmRismsSRS = new JFrame();
        frmRismsSRS.setBackground(Color.WHITE);
        frmRismsSRS.getContentPane().setBackground(Color.WHITE);
        frmRismsSRS.setTitle("Sales Report (Admin Section)");
        frmRismsSRS.setBounds(350, 100, 820, 500);
        frmRismsSRS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsSRS.getContentPane().setLayout(null);

        JPanel borderPanel = new JPanel();
        borderPanel.setBackground(new Color(0, 139, 139));
        borderPanel.setBounds(0, 0, 806, 40);
        frmRismsSRS.getContentPane().add(borderPanel);
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
            String formattedDateTime = zdt.format(formatter);
            String country = locale.getDisplayCountry();

            lblDateTime.setText(formattedDateTime + " - " + country);
        });
        timer.start();

        // Sidebar panel
        JPanel lBarPanel = new JPanel();
        lBarPanel.setBackground(new Color(0, 139, 139));
        lBarPanel.setBounds(0, 40, 160, 423);
        lBarPanel.setLayout(null);
        frmRismsSRS.getContentPane().add(lBarPanel);
        
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
            frmRismsSRS.dispose(); 
            new HomePageADSection(username, user_type);
        });

        JButton btnAddProducts = new JButton("Add Products");
        btnAddProducts.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAddProducts.setFocusPainted(false);
        btnAddProducts.setBounds(15, 100, 130, 40);
        lBarPanel.add(btnAddProducts);
        btnAddProducts.addActionListener(e -> {
        	frmRismsSRS.dispose(); 
        	new AddProductsADSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Stocks Inventory");
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 165, 130, 40);
        lBarPanel.add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsSRS.dispose(); 
        	new StockInventoryADSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("Sales Report");
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 230, 130, 40);
        lBarPanel.add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsSRS.dispose(); 
        	new SalesReportADSection(username, user_type);
        });

        JButton btnRepGenerator = new JButton("Gen-Reports");
        btnRepGenerator.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRepGenerator.setFocusPainted(false);
        btnRepGenerator.setBounds(15, 295, 130, 40);
        lBarPanel.add(btnRepGenerator);
        btnRepGenerator.addActionListener(e -> {
        	frmRismsSRS.dispose(); 
        	new ReportsGeneratorADSection(username, user_type);
        });
        
        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 360, 130, 40);
        lBarPanel.add(btnLogOut);
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsSRS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsSRS, "You have been logged out.");
                frmRismsSRS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsSRS, "Log-out cancelled.");
            }
        });


        // === Remove old labels from center panel
        // === Embed Order History Table instead
        String[] columnNames = {"Customer Name", "Product ID", "Item Name", "Price P/kg", "Bought Stocks (kg)", "Total Price", "Date&Time" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        OrderHistorytable = new JTable(model);
        OrderHistorytable.setFont(new Font("Cambria", Font.PLAIN, 10));
        OrderHistorytable.setRowHeight(30);
        OrderHistorytable.setShowGrid(true);
        OrderHistorytable.setGridColor(Color.BLACK);

        // Header styling
        JTableHeader header = OrderHistorytable.getTableHeader();
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Cambria", Font.BOLD, 8));

        // Row styling
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(144, 238, 144));
                } else if (row % 2 == 0) {
                    c.setBackground(new Color(144, 238, 144)); 
                } else {
                    c.setBackground(new Color(144, 238, 144)); 
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        };

        for (int i = 0; i < OrderHistorytable.getColumnCount(); i++) {
            OrderHistorytable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(OrderHistorytable);
        scrollPane.setBounds(170, 50, 623, 403);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
        frmRismsSRS.getContentPane().add(scrollPane);
    }

    /**
     * Loads order history data from database into the JTable.
     */
    private void loadOrderHistoryData() {
        DefaultTableModel model = (DefaultTableModel) OrderHistorytable.getModel();
        model.setRowCount(0); // Clear previous data

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");
            Statement stmt = conn.createStatement();
            String query = "SELECT customer_name, order_id, product_id, item_name, price_per_kilo, available_stocks, total_price, purchase_datetime FROM admin_sales_report ORDER BY purchase_datetime DESC";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
            	String cusName = rs.getString("customer_name");
            	String prodID = rs.getString("product_id");
                String itemName = rs.getString("item_name");      
                double prcPKg = rs.getDouble("price_per_kilo");
                int pStocks = rs.getInt("available_stocks");
                int tPrice = rs.getInt("total_price");
                String purDaTime = rs.getString("purchase_datetime"); 
                model.addRow(new Object[] { cusName, prodID, itemName, prcPKg, pStocks, tPrice, purDaTime });
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error loading order history data!", "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}