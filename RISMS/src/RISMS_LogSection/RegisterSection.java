package RISMS_LogSection;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterSection {

    private JFrame frmRismsRS;
    private JTextField txtNewUsername;
    private JPasswordField txtNewPassword;
    private JComboBox<String> cmbTypeOfUser;

    private final String DB_URL = "jdbc:mysql://localhost:3306/RISMS_DB";
    private final String DB_USER = "root";
    private final String DB_PASS = ""; 

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RegisterSection window = new RegisterSection();
                window.frmRismsRS.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RegisterSection() {
        initialize();
    }

    private void initialize() {
        frmRismsRS = new JFrame();
        frmRismsRS.getContentPane().setBackground(new Color(255, 255, 255));
        frmRismsRS.setTitle("RISMS (Registration Section)");
        frmRismsRS.setBounds(350, 100, 788, 520);
        frmRismsRS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRismsRS.getContentPane().setLayout(null);
        
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
        imagePanel.setBounds(380, 0, 395, 483);
        frmRismsRS.getContentPane().add(imagePanel);

        JLabel lblRegister = new JLabel("REGISTER");
        lblRegister.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblRegister.setBounds(150, 50, 100, 22);
        frmRismsRS.getContentPane().add(lblRegister);

        JLabel lblNewUsername = new JLabel("New Username:");
        lblNewUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewUsername.setBounds(50, 90, 120, 22);
        frmRismsRS.getContentPane().add(lblNewUsername);

        txtNewUsername = new JTextField();
        txtNewUsername.setBounds(150, 90, 180, 27);
        frmRismsRS.getContentPane().add(txtNewUsername);
        txtNewUsername.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        txtNewUsername.setColumns(10);

        JLabel lblNewPassword = new JLabel("New Password:");
        lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewPassword.setBounds(50, 130, 120, 22);
        frmRismsRS.getContentPane().add(lblNewPassword);

        txtNewPassword = new JPasswordField();
        txtNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtNewPassword.setBounds(150, 130, 180, 27);
        txtNewPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        frmRismsRS.getContentPane().add(txtNewPassword);

        JCheckBox chkShowNewPassword = new JCheckBox("Show password");
        chkShowNewPassword.setBackground(new Color(255, 255, 255));
        chkShowNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
        chkShowNewPassword.setBounds(200, 165, 130, 23);
        frmRismsRS.getContentPane().add(chkShowNewPassword);

        JLabel lblClassification = new JLabel("Type of User:");
        lblClassification.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblClassification.setBounds(60, 200, 120, 22);
        frmRismsRS.getContentPane().add(lblClassification);

        cmbTypeOfUser = new JComboBox<>(new String[]{"User/Customer"});
        cmbTypeOfUser.setBackground(new Color(255, 255, 255));
        cmbTypeOfUser.setBounds(150, 200, 180, 27);
        cmbTypeOfUser.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        frmRismsRS.getContentPane().add(cmbTypeOfUser);

        JButton btnSubmit = new JButton("Submit");
        registrationPageStyleButton(btnSubmit);
        btnSubmit.setBackground(new Color(0, 0, 0));
        btnSubmit.setForeground(new Color(255, 255, 255));
        btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSubmit.setBounds(195, 240, 120, 35);
        frmRismsRS.getContentPane().add(btnSubmit);

        JButton btnBackToLogin = new JButton("Back");
        registrationPageStyleButton(btnBackToLogin);
        btnBackToLogin.setBackground(new Color(0, 0, 0));
        btnBackToLogin.setForeground(new Color(255, 255, 255));  
        btnBackToLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoginSection.main(null);
        		frmRismsRS.dispose();
        	}
        });
        
        btnBackToLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBackToLogin.setBounds(65, 240, 120, 35);
        frmRismsRS.getContentPane().add(btnBackToLogin);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(40, 310, 290, 4);
        frmRismsRS.getContentPane().add(panel);
        
        JLabel lblRISMSH1 = new JLabel("RISMS");
        lblRISMSH1.setFont(new Font("Cambria", Font.BOLD, 50));
        lblRISMSH1.setBounds(106, 334, 153, 50);
        frmRismsRS.getContentPane().add(lblRISMSH1);
        
        JLabel lblQuote = new JLabel("“Where every sack counts, every grain matters.”");
        lblQuote.setFont(new Font("Cambria", Font.PLAIN, 14));
        lblQuote.setBounds(40, 404, 314, 22);
        frmRismsRS.getContentPane().add(lblQuote);

        chkShowNewPassword.addActionListener(e -> {
            txtNewPassword.setEchoChar(chkShowNewPassword.isSelected() ? (char) 0 : '•');
        });

        btnSubmit.addActionListener(e -> registerUser());
    }
    
    private void registerUser() {
        String newUser = txtNewUsername.getText().trim();
        String newPass = new String(txtNewPassword.getPassword());
        String typeOfUser = (String) cmbTypeOfUser.getSelectedItem();

        // Validation checks
        if (newUser.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(frmRismsRS, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!newUser.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,14}$")) {
            JOptionPane.showMessageDialog(frmRismsRS, "Username must be 6–14 characters long and contain both letters and numbers only.", "Invalid Username", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!newPass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,}$")) {
            JOptionPane.showMessageDialog(frmRismsRS, "Password must be at least 8 characters and include uppercase, lowercase, number, and special character.", "Weak Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO accounts (username, password, user_type) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newUser);       
            pstmt.setString(2, newPass);
            pstmt.setString(3, typeOfUser);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frmRismsRS, "Registered successfully!");

            LoginSection.main(null);
            frmRismsRS.dispose();

        } catch (SQLIntegrityConstraintViolationException dup) {
            JOptionPane.showMessageDialog(frmRismsRS, "Username/password already exists.", "Duplicate Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmRismsRS, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    
 // Reusable button styling method
    private void registrationPageStyleButton(JButton button) {
        button.setBackground(new Color(144, 238, 144));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setFocusPainted(false); // This removes the blue outline
    }
}