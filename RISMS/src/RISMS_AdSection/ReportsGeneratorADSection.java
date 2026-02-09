package RISMS_AdSection;

//Standard Java imports
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//Swing
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

//iText
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// Package Inheritance
import RISMS_LogSection.LoginSection;

public class ReportsGeneratorADSection {

    private JFrame frmRismsRGS;
    private String username;
    private String user_type;
    
    public ReportsGeneratorADSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
        frmRismsRGS.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ReportsGeneratorADSection window = new ReportsGeneratorADSection();
                window.frmRismsRGS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ReportsGeneratorADSection() {
        initialize();
    }

    private void initialize() {
        frmRismsRGS = new JFrame();
        frmRismsRGS.setBackground(Color.WHITE);
        frmRismsRGS.getContentPane().setBackground(Color.WHITE);
        frmRismsRGS.setTitle("Home Page (Admin Section)");
        frmRismsRGS.setBounds(350, 100, 820, 500);
        frmRismsRGS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsRGS.getContentPane().setLayout(null);

        JPanel borderPanel = new JPanel();
        borderPanel.setBackground(new Color(0, 139, 139));
        borderPanel.setBounds(0, 0, 806, 40);
        frmRismsRGS.getContentPane().add(borderPanel);
        borderPanel.setLayout(null);

        JLabel lblDateTime = new JLabel();
        lblDateTime.setBackground(new Color(255, 255, 255));
        lblDateTime.setFont(new java.awt.Font("Segoe UI Emoji", Font.BOLD, 12));
        lblDateTime.setForeground(Color.WHITE);
        lblDateTime.setBounds(580, 5, 220, 30);
        borderPanel.add(lblDateTime);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBounds(20, 10, 300, 25);
        borderPanel.add(lblUserNUserTH3);
        lblUserNUserTH3.setForeground(new Color(255, 255, 255));
        lblUserNUserTH3.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.BOLD, 12));



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

        JPanel lBarPanel = new JPanel();
        lBarPanel.setBackground(new Color(0, 139, 139));
        lBarPanel.setBounds(0, 30, 160, 433);
        lBarPanel.setLayout(null);
        frmRismsRGS.getContentPane().add(lBarPanel);
        
        JButton btnHomePage = new JButton("Home Page");
        btnHomePage.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        btnHomePage.setFocusPainted(false);
        btnHomePage.setBounds(15, 45, 130, 40);
        lBarPanel.add(btnHomePage);
        btnHomePage.addActionListener(e -> {
        	frmRismsRGS.dispose(); 
            new HomePageADSection(username, user_type);
        });

        JButton btnAddProducts = new JButton("Add Products");
        btnAddProducts.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        btnAddProducts.setFocusPainted(false);
        btnAddProducts.setBounds(15, 110, 130, 40);
        lBarPanel.add(btnAddProducts);
        btnAddProducts.addActionListener(e -> {
        	frmRismsRGS.dispose(); 
        	new AddProductsADSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Stocks Inventory");
        btnAddToCart.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 175, 130, 40);
        lBarPanel.add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsRGS.dispose(); 
        	new StockInventoryADSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("Sales Report");
        btnViewCart.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 240, 130, 40);
        lBarPanel.add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsRGS.dispose(); 
        	new SalesReportADSection(username, user_type);
        });

        JButton btnRepGenerator = new JButton("Gen-Reports");
        btnRepGenerator.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        btnRepGenerator.setFocusPainted(false);
        btnRepGenerator.setBounds(15, 305, 130, 40);
        lBarPanel.add(btnRepGenerator);
        btnRepGenerator.addActionListener(e -> {
        	frmRismsRGS.dispose(); 
        	new ReportsGeneratorADSection(username, user_type);
        });
        
        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 370, 130, 40);
        lBarPanel.add(btnLogOut);
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsRGS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?", 
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsRGS, "You have been logged out.");
                frmRismsRGS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsRGS, "Log-out cancelled.");
            }
        });
        
        JPanel panelContents = new JPanel();
        panelContents.setBackground(new Color(0, 139, 139));
        panelContents.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        panelContents.setBounds(180, 50, 610, 400);
        panelContents.setLayout(null);
        frmRismsRGS.getContentPane().add(panelContents);
        
        JLabel lblRIH3 = new JLabel("GENERATED REPORTS");
        lblRIH3.setForeground(new Color(255, 255, 255));
        lblRIH3.setFont(new java.awt.Font("Cambria", Font.BOLD, 24));
        lblRIH3.setBounds(175, 30, 250, 25);
        panelContents.add(lblRIH3);
        
        JButton btnGSR = new JButton("Generate Sales Report");
        btnGSR.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        btnGSR.setFocusPainted(false);
        btnGSR.setBounds(200, 80, 200, 40);
        panelContents.add(btnGSR);
        btnGSR.addActionListener(e -> {
            try {
                // Create a file chooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Generated Sales Report");
                
                // Optional: set default file name
                fileChooser.setSelectedFile(new File("Sales_Report.pdf"));
                
                int userSelection = fileChooser.showSaveDialog(frmRismsRGS);
                
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    
                    // Connect to database
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");
                    String sql = "SELECT * FROM admin_sales_report ORDER BY purchase_datetime DESC LIMIT 10";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    // Create PDF document
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));
                    document.open();

                    // Fonts
                    Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
                    Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
                    Font smallFont = new Font(Font.FontFamily.HELVETICA, 10);

                    // Title and header
                    document.add(new Paragraph("Rice Inventory and Sales Management System (RISMS)", titleFont));
                    document.add(new Paragraph("Sales Report", titleFont));
                    document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now(), smallFont));
                    document.add(new Paragraph("--------------------------------------------------------------"));

                    // Iterate over result set
                    while (rs.next()) {
                        document.add(new Paragraph("Customer Name : " + rs.getString("customer_name"), normalFont));
                        document.add(new Paragraph("Order ID      : " + rs.getInt("order_id")));
                        document.add(new Paragraph("Product ID    : " + rs.getString("product_id")));
                        document.add(new Paragraph("Item Name     : " + rs.getString("item_name")));
                        document.add(new Paragraph("Price/Kilo    : ₱" + rs.getBigDecimal("price_per_kilo")));
                        document.add(new Paragraph("Stocks        : " + rs.getInt("available_stocks")));
                        document.add(new Paragraph("Total Price   : ₱" + rs.getBigDecimal("total_price")));
                        document.add(new Paragraph("Date & Time   : " + rs.getTimestamp("purchase_datetime")));
                        document.add(new Paragraph("--------------------------------------------------------------"));
                    }
                    
                    // Add extra spacing after each order
                    document.add(new Paragraph(" "));  
                    Paragraph space = new Paragraph();
                    space.setSpacingBefore(10f);
                    space.setSpacingAfter(10f);
                    document.add(space);

                    // Close everything
                    document.close();
                    rs.close();
                    stmt.close();
                    conn.close();

                    // Automatically open the PDF File
                    Desktop.getDesktop().open(new File(filePath));
                    JOptionPane.showMessageDialog(null, "Sales report generated successfully.");
                } else {
                    // User cancelled save dialog
                    JOptionPane.showMessageDialog(frmRismsRGS, "Save command cancelled by the admin.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmRismsRGS, "Error generating receipt:\n" + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        JButton btnGSIR = new JButton("Generate Stocks Report");
        btnGSIR.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        btnGSIR.setFocusPainted(false);
        btnGSIR.setBounds(200, 140, 200, 40);
        panelContents.add(btnGSIR);
        btnGSIR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/RISMS_DB";
                String user = "root";
                String password = "";

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Generating Stocks Inventory List Report");
                fileChooser.setSelectedFile(new File("Stock_Inventory_List_Report.pdf"));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT * FROM admin_stocks_inventory")) {

                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                        document.open();

                        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
                        Paragraph title = new Paragraph("Rice Stock Inventory List\n\n", titleFont);
                        title.setAlignment(Element.ALIGN_CENTER);
                        document.add(title);

                        PdfPTable table = new PdfPTable(5);
                        table.setWidthPercentage(100);
                        String[] headers = {"Item ID", "Product ID", "Item Name", "Price per Kilo", "Available Stocks"};
                        for (String header : headers) {
                            PdfPCell cell = new PdfPCell(new Phrase(header));
                            cell.setBackgroundColor(new BaseColor(211, 211, 211));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);
                        }

                        while (rs.next()) {
                            table.addCell(String.valueOf(rs.getInt("item_id")));
                            table.addCell(rs.getString("product_id"));
                            table.addCell(rs.getString("item_name"));
                            table.addCell(String.format("%.2f", rs.getDouble("price_per_kilo")));
                            table.addCell(String.valueOf(rs.getInt("available_stocks")));
                        }

                        document.add(table);
                        document.close();

                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(fileToSave);
                        }

                        JOptionPane.showMessageDialog(null, "Stocks report generated successfully.");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error generating PDF: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(frmRismsRGS, "Save command cancelled by the admin.");
                }
            }
        });
        
        JButton btnGLHR = new JButton("Generate Login History");
        btnGLHR.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        btnGLHR.setFocusPainted(false);
        btnGLHR.setBounds(200, 200, 200, 40);
        panelContents.add(btnGLHR);        
        btnGLHR.addActionListener(e -> {
            try {
                // Setup file chooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Login History Report");
                fileChooser.setSelectedFile(new File("Login_History_Report.pdf"));

                int userSelection = fileChooser.showSaveDialog(frmRismsRGS);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();

                    // Connect to database
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/risms_db", "root", "");

                    // Query login history
                    String sql = "SELECT id, username, login_timestamp FROM customer_login_history ORDER BY login_timestamp DESC";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    // Create PDF
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));
                    document.open();

                    // Fonts
                    Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
                    Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                    Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12);

                    // Title
                    document.add(new Paragraph("Rice Inventory and Sales Management System (RISMS)", titleFont));
                    document.add(new Paragraph("Login History Report", headerFont));
                    document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now()));
                    document.add(new Paragraph("--------------------------------------------------------------"));
                    document.add(new Paragraph("\n"));

                    // Table
                    PdfPTable table = new PdfPTable(3); // 3 columns
                    table.setWidthPercentage(100);
                    table.setWidths(new float[]{1f, 3f, 4f});

                    // Table headers
                    table.addCell(new PdfPCell(new Phrase("ID", headerFont)));
                    table.addCell(new PdfPCell(new Phrase("Username", headerFont)));
                    table.addCell(new PdfPCell(new Phrase("Login Timestamp", headerFont)));

                    // Rows
                    while (rs.next()) {
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(rs.getInt("id")), bodyFont)));
                        table.addCell(new PdfPCell(new Phrase(rs.getString("username"), bodyFont)));
                        table.addCell(new PdfPCell(new Phrase(rs.getTimestamp("login_timestamp").toString(), bodyFont)));
                    }

                    document.add(table);
                    document.close();
                    rs.close();
                    stmt.close();
                    conn.close();
                    
                    // Open the generated PDF
                    Desktop.getDesktop().open(new File(filePath));
                    JOptionPane.showMessageDialog(null, "Login history report generated successfully.");
                } else {
                	JOptionPane.showMessageDialog(frmRismsRGS, "Save command cancelled by the admin.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmRismsRGS, "Error generating report: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        JButton btnGCR = new JButton("Generate Customer Receipt");
        btnGCR.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        btnGCR.setFocusPainted(false);
        btnGCR.setBounds(200, 260, 200, 40);
        panelContents.add(btnGCR);
        btnGCR.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Generating Customer Receipt");
                fileChooser.setSelectedFile(new File("Customer_Receipt.pdf"));
                
                int userSelection = fileChooser.showSaveDialog(frmRismsRGS);
                
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    
                    // Connect to database
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");
                    
                    // 👇 Only get the most recent customer transaction
                    String sql = "SELECT * FROM customer_order_history ORDER BY purchase_datetime DESC LIMIT 1";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    // Create PDF document
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));
                    document.open();

                    // Fonts
                    Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
                    Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
                    Font smallFont = new Font(Font.FontFamily.HELVETICA, 10);

                    // Title and header
                    document.add(new Paragraph("Rice Inventory and Sales Management System (RISMS)", titleFont));
                    document.add(new Paragraph("Customer Receipt Report", titleFont));
                    document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now(), smallFont));
                    document.add(new Paragraph("--------------------------------------------------------------"));

                    // Only one result expected here
                    if (rs.next()) {
                        document.add(new Paragraph("Customer Name : " + rs.getString("customer_name"), normalFont));
                        document.add(new Paragraph("Order ID      : " + rs.getInt("order_id")));
                        document.add(new Paragraph("Product ID    : " + rs.getString("product_id")));
                        document.add(new Paragraph("Item Name     : " + rs.getString("item_name")));
                        document.add(new Paragraph("Price/Kilo    : ₱" + rs.getBigDecimal("price_per_kilo")));
                        document.add(new Paragraph("Stocks        : " + rs.getInt("available_stocks")));
                        document.add(new Paragraph("Total Price   : ₱" + rs.getBigDecimal("total_price")));
                        document.add(new Paragraph("Date & Time   : " + rs.getTimestamp("purchase_datetime")));
                        document.add(new Paragraph("--------------------------------------------------------------"));
                    }

                    document.add(new Paragraph(" "));
                    Paragraph space = new Paragraph();
                    space.setSpacingBefore(10f);
                    space.setSpacingAfter(10f);
                    document.add(space);

                    document.close();
                    rs.close();
                    stmt.close();
                    conn.close();

                    // ✅ Open the PDF automatically (no JOptionPane needed)
                    Desktop.getDesktop().open(new File(filePath));
                    JOptionPane.showMessageDialog(null, "Customer order history report generated successfully.");
                } else {
                    // You can keep or remove this message
                    JOptionPane.showMessageDialog(frmRismsRGS, "Save command cancelled by user.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmRismsRGS, "Error generating receipt:\n" + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        
        JButton btnGCLPR = new JButton("Generate Customer Products");
        btnGCLPR.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        btnGCLPR.setFocusPainted(false);
        btnGCLPR.setBounds(200, 320, 200, 40);
        panelContents.add(btnGCLPR);
        btnGCLPR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Document document = new Document();
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISMS_DB", "root", "");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM customer_products");

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Generating Customer Products Report");
                    fileChooser.setSelectedFile(new File("Customer_Products_Report.pdf"));
                    int userSelection = fileChooser.showSaveDialog(null);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                        document.open();

                        // Title
                        document.add(new Paragraph("Customer Products Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
                        document.add(new Paragraph(" ")); 

                        PdfPTable table = new PdfPTable(5);
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(10f);
                        table.setSpacingAfter(10f);

                        // Table Headers
                        table.addCell("Item ID");
                        table.addCell("Product ID");
                        table.addCell("Item Name");
                        table.addCell("Price Per Kilo");
                        table.addCell("Available Stocks");

                        // Table Data
                        while (rs.next()) {
                            table.addCell(String.valueOf(rs.getInt("item_id")));
                            table.addCell(rs.getString("product_id"));
                            table.addCell(rs.getString("item_name"));
                            table.addCell(String.valueOf(rs.getDouble("price_per_kilo")));
                            table.addCell(String.valueOf(rs.getInt("available_stocks")));
                        }

                        document.add(table);
                        document.close();
                        conn.close();

                        // Automatically open the PDF File AFTER closing the document
                        Desktop.getDesktop().open(fileToSave);
                        JOptionPane.showMessageDialog(null, "Customer products report generated successfully.");
                    } else {
                    	JOptionPane.showMessageDialog(frmRismsRGS, "Save command cancelled by the admin.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error generating report: " + ex.getMessage());
                }
            }
        });
    }
}