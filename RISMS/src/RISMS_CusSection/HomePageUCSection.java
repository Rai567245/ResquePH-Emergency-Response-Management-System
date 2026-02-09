package RISMS_CusSection;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import RISMS_LogSection.LoginSection;

public class HomePageUCSection {

    private JFrame frmRismsHPS;
    private String username;
    private String user_type;
    
    public HomePageUCSection(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                HomePageUCSection window = new HomePageUCSection();
                window.frmRismsHPS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public HomePageUCSection() {
        initialize();
    }	

    private void initialize() {
    	JFrame frmRismsHPS = new JFrame();
        frmRismsHPS.setTitle("Home Page (Users/Customers Section)");
        frmRismsHPS.setBounds(350, 100, 820, 500);
        frmRismsHPS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        frmRismsHPS.setContentPane(gradientPanel);
        frmRismsHPS.setVisible(true);
        
        ImageIcon imageIcon = new ImageIcon("src/RISMS_Images/RICEFIELD.jpg"); 
        Image image = imageIcon.getImage();

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        imagePanel.setBounds(160, 45, 625, 380);
        frmRismsHPS.getContentPane().add(imagePanel);
        imagePanel.setLayout(null);
        
        JLabel lblDateTime = new JLabel();
        lblDateTime.setBackground(Color.BLACK);
        lblDateTime.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblDateTime.setForeground(new Color(255, 255, 255));
        lblDateTime.setBounds(590, 10, 215, 30);
        frmRismsHPS.getContentPane().add(lblDateTime);
        
        JLabel lblUserNUserTH3 = new JLabel("👤 " + username + " (" + user_type + ")");
        lblUserNUserTH3.setBackground(new Color(0, 51, 51));
        lblUserNUserTH3.setBounds(20, 10, 300, 25);
        frmRismsHPS.getContentPane().add(lblUserNUserTH3);
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

        JLabel lblRISMSH1 = new JLabel("RISMS");
        lblRISMSH1.setForeground(new Color(255, 255, 255));
        lblRISMSH1.setFont(new Font("Cambria", Font.BOLD, 46));
        lblRISMSH1.setBounds(57, 85, 150, 50);
        imagePanel.add(lblRISMSH1);

        JLabel lblRIH3 = new JLabel("Rice Inventory and");
        lblRIH3.setForeground(new Color(255, 255, 255));
        lblRIH3.setFont(new Font("Cambria", Font.BOLD, 24));
        lblRIH3.setBounds(60, 145, 220, 25);
        imagePanel.add(lblRIH3);

        JLabel lblSMSH3 = new JLabel("Sales Management System");
        lblSMSH3.setForeground(new Color(255, 255, 255));
        lblSMSH3.setFont(new Font("Cambria", Font.BOLD, 24));
        lblSMSH3.setBounds(60, 185, 300, 25);
        imagePanel.add(lblSMSH3);
        
        JPanel line = new JPanel();
        line.setBackground(new Color(255, 255, 255));
        line.setBounds(60, 230, 420, 5);
        imagePanel.add(line);
        
        JLabel lblWELCOMEH1 = new JLabel("Welcome To Our Shop!");
        lblWELCOMEH1.setForeground(new Color(255, 255, 255));
        lblWELCOMEH1.setFont(new Font("Cambria", Font.BOLD, 40));
        lblWELCOMEH1.setBounds(60, 250, 450, 50);
        imagePanel.add(lblWELCOMEH1);
        
        JButton btnHomePage = new JButton("Home Page");
        btnHomePage.setForeground(Color.WHITE);
        btnHomePage.setBackground(Color.BLACK);
        btnHomePage.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnHomePage.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnHomePage.setFocusPainted(false);
        btnHomePage.setBounds(15, 45, 130, 40);
        frmRismsHPS.getContentPane().add(btnHomePage);
        btnHomePage.addActionListener(e -> {
            frmRismsHPS.dispose();  
            new HomePageUCSection(username, user_type);
        });

        JButton btnListOfProducts = new JButton("List of Products");
        btnListOfProducts.setForeground(Color.WHITE);
        btnListOfProducts.setBackground(Color.BLACK);
        btnListOfProducts.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnListOfProducts.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnListOfProducts.setFocusPainted(false);
        btnListOfProducts.setBounds(15, 103, 130, 40);
        frmRismsHPS.getContentPane().add(btnListOfProducts);
        btnListOfProducts.addActionListener(e -> {
        	frmRismsHPS.dispose(); 
        	new ListOfProductsUCSection(username, user_type);
        });

        JButton btnAddToCart = new JButton("Add To Cart");
        btnAddToCart.setBackground(Color.BLACK);
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setBounds(15, 158, 130, 40);
        frmRismsHPS.getContentPane().add(btnAddToCart);
        btnAddToCart.addActionListener(e -> {
        	frmRismsHPS.dispose(); 
        	new AddToCartUCSection(username, user_type);
        });
        
        JButton btnViewCart = new JButton("View Cart");
        btnViewCart.setForeground(Color.WHITE);
        btnViewCart.setBackground(Color.BLACK);
        btnViewCart.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnViewCart.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnViewCart.setFocusPainted(false);
        btnViewCart.setBounds(15, 213, 130, 40);
        frmRismsHPS.getContentPane().add(btnViewCart);
        btnViewCart.addActionListener(e -> {
        	frmRismsHPS.dispose(); 
        	new ViewCartUCSection(username, user_type);
        });

        JButton btnPurchase = new JButton("Purchase");
        btnPurchase.setBackground(Color.BLACK);
        btnPurchase.setForeground(Color.WHITE);
        btnPurchase.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnPurchase.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnPurchase.setFocusPainted(false);
        btnPurchase.setBounds(15, 270, 130, 40);
        frmRismsHPS.getContentPane().add(btnPurchase);
        btnPurchase.addActionListener(e -> {
        	frmRismsHPS.dispose(); 
        	new PurchaseUCSection(username, user_type);
        });

        JButton btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setForeground(Color.WHITE);
        btnOrderHistory.setBackground(Color.BLACK);
        btnOrderHistory.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnOrderHistory.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnOrderHistory.setFocusPainted(false);
        btnOrderHistory.setBounds(15, 328, 130, 40);
        frmRismsHPS.getContentPane().add(btnOrderHistory);
        btnOrderHistory.addActionListener(e -> {
        	frmRismsHPS.dispose(); 
        	new OrderHistoryUCSection(username, user_type);
        });

        JButton btnLogOut = new JButton("Log-out");
        btnLogOut.setBackground(Color.BLACK);
        btnLogOut.setForeground(Color.WHITE);
        btnLogOut.setBorder(new LineBorder(new Color(255, 255, 255), 2)); 
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBounds(15, 385, 130, 40);
        frmRismsHPS.getContentPane().add(btnLogOut);
        
        btnLogOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frmRismsHPS,
                "Are you sure you want to log out?",
                "Going Back To Login Section?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frmRismsHPS, "You have been logged out.");
                frmRismsHPS.dispose();
                LoginSection.main(null);
            } else {
                JOptionPane.showMessageDialog(frmRismsHPS, "Log-out cancelled.");
            }
        });
    }
}