package magicwindow;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;




public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginFrame() {
        // Apply FlatLaf look and feel
        FlatLightLaf.setup();

        setTitle("Hospital Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        


        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Hospital Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Role ComboBox
        gbc.gridy++;
        gbc.gridwidth = 2;
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Doctor", "Patient"});
        roleComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        mainPanel.add(roleComboBox, gbc);
 
        // Username
        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Username:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        mainPanel.add(passwordField, gbc);

        // Login button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> handleLogin());

        setVisible(true);
    }

    private void handleLogin() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());
    String role = (String) roleComboBox.getSelectedItem();  // Get selected role from dropdown

    Connection conn = DBManager.getConnection();
    if (conn == null) {
        JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, role);

        ResultSet rs = stmt.executeQuery();
       if (rs.next()) {
    // Open main HMS window directly
    new MagicWindowFrame(); // Or pass parameters if needed
    this.dispose(); // Close login window
} else {
    JOptionPane.showMessageDialog(this, "❌ Invalid username, password, or role!", "Login Failed", JOptionPane.ERROR_MESSAGE);
}


        rs.close();
        stmt.close();
        DBManager.closeConnection(conn);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "❌ DB Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
