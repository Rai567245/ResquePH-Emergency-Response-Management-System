package RISMS_LogSection;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import RISMS_AdSection.HomePageADSection; // ✅ Admin homepage
import RISMS_CusSection.HomePageUCSection; // ✅ Customer homepage 

public class LoginSection {

	private JFrame frmRismsLS;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				LoginSection window = new LoginSection();
				window.frmRismsLS.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public LoginSection() {
		initialize();
	}

	private void initialize() {
		frmRismsLS = new JFrame();
		frmRismsLS.setBackground(new Color(255, 255, 255));
		frmRismsLS.getContentPane().setBackground(new Color(255, 255, 255));
		frmRismsLS.setTitle("RISMS (Log-in/Sign-in Section)");
		frmRismsLS.setBounds(350, 100, 788, 520);
		frmRismsLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRismsLS.getContentPane().setLayout(null);

		ImageIcon imageIcon = new ImageIcon("src/RISMS_Images/RICE.jpg");
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
		imagePanel.setBounds(0, 0, 370, 483);
		frmRismsLS.getContentPane().add(imagePanel);

		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLogin.setBounds(541, 51, 53, 22);
		frmRismsLS.getContentPane().add(lblLogin);
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(439, 97, 75, 22);
		frmRismsLS.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(439, 144, 75, 22);
		frmRismsLS.getContentPane().add(lblPassword);

		txtUsername = new JTextField();
		txtUsername.setBounds(509, 96, 184, 27);
		txtUsername.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		frmRismsLS.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPassword.setBounds(509, 141, 184, 27);
		txtPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		frmRismsLS.getContentPane().add(txtPassword);

		JCheckBox chkShowPassword = new JCheckBox("Show password");
		chkShowPassword.setBackground(new Color(255, 255, 255));
		chkShowPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chkShowPassword.setBounds(568, 174, 136, 23);
		frmRismsLS.getContentPane().add(chkShowPassword);

		JButton btnSubmit = new JButton("Submit");
		loginPageStyleButton(btnSubmit);
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBackground(Color.BLACK);
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSubmit.setBounds(462, 203, 101, 32);
		btnSubmit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		frmRismsLS.getContentPane().add(btnSubmit);

		JButton btnRegister = new JButton("Register");
		loginPageStyleButton(btnRegister);
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(Color.BLACK);
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegister.setBounds(573, 203, 101, 32);
		btnRegister.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		frmRismsLS.getContentPane().add(btnRegister);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(428, 274, 290, 4);
		frmRismsLS.getContentPane().add(panel);

		JLabel lblRISMSH1 = new JLabel("RISMS");
		lblRISMSH1.setFont(new Font("Cambria", Font.BOLD, 50));
		lblRISMSH1.setBounds(498, 316, 153, 50);
		frmRismsLS.getContentPane().add(lblRISMSH1);

		JLabel lblQuote = new JLabel("“Where every sack counts, every grain matters.”");
		lblQuote.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblQuote.setBounds(422, 392, 314, 22);
		frmRismsLS.getContentPane().add(lblQuote);

		chkShowPassword.addActionListener(e -> {
			txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '•');
		});

		btnSubmit.addActionListener(e -> {
			String username = txtUsername.getText();
			String password = new String(txtPassword.getPassword());

			String url = "jdbc:mysql://localhost:3306/RISMS_DB";
			String dbUser = "root";
			String dbPass = "";

			try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {

				String query = "SELECT id, user_type, password FROM accounts WHERE BINARY username = ?";
				try (PreparedStatement stmt = conn.prepareStatement(query)) {
					stmt.setString(1, username);

					ResultSet rs = stmt.executeQuery();

					if (rs.next()) {
						String correctPassword = rs.getString("password");
						if (correctPassword.equals(password)) {
							String userType = rs.getString("user_type");

							insertLoginHistory(conn, username);
							JOptionPane.showMessageDialog(frmRismsLS, "Login Successful");

							if (userType.equalsIgnoreCase("Admin")) {
								new HomePageADSection(username, userType);
							} else if (userType.equalsIgnoreCase("User/Customer")) {
								new HomePageUCSection(username, userType);
							} else {
								JOptionPane.showMessageDialog(frmRismsLS, "Unknown user type.");
							}

							frmRismsLS.dispose();
						} else {
							JOptionPane.showMessageDialog(frmRismsLS, "Invalid username or password");
						}
					} else {
						JOptionPane.showMessageDialog(frmRismsLS, "Invalid username or password");
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frmRismsLS, "Database error: " + ex.getMessage());
			}
		});

		btnRegister.addActionListener(e -> {
			RegisterSection.main(null);
			frmRismsLS.dispose();
		});
	}

	private void insertLoginHistory(Connection conn, String username) {
		String insertSql = "INSERT INTO customer_login_history (username) VALUES (?)";

		try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frmRismsLS, "Failed to log login history: " + e.getMessage());
		}
	}
	
	private void loginPageStyleButton(JButton button) {
		button.setBackground(new Color(144, 238, 144));
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setFocusPainted(false);
	}
}