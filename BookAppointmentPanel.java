package view;

import magicwindow.DBManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookAppointmentPanel extends JPanel {
    private JTextField pi, di, ts, ds;
    private JButton bookButton;

    public BookAppointmentPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new JLabel("Book Appointment");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 30, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel pidLabel = new JLabel("Patient ID:");
        pidLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(pidLabel, gbc);

        pi = new JTextField(20);
        gbc.gridx = 1;
        add(pi, gbc);

        JLabel didLabel = new JLabel("Doctor ID:");
        didLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(didLabel, gbc);

        di = new JTextField(20);
        gbc.gridx = 1;
        add(di, gbc);

        JLabel tsLabel = new JLabel("Time Slot:");
        tsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(tsLabel, gbc);

        ts = new JTextField(20);
        gbc.gridx = 1;
        add(ts, gbc);

        JLabel dsLabel = new JLabel("Disease:");
        dsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(dsLabel, gbc);

        ds = new JTextField(20);
        gbc.gridx = 1;
        add(ds, gbc);

        // Button
        bookButton = new JButton("Book Appointment");
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bookButton.setBackground(new Color(0, 153, 76));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookButton.setPreferredSize(new Dimension(200, 40));
        bookButton.addActionListener(e -> bookAppointment());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(bookButton, gbc);
    }

    private void bookAppointment() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DBManager.getConnection()) {
                String sql = "INSERT INTO appointments (PatientID, DoctorID, TimeSlot, Disease) VALUES (?, ?, ?, ?)";
                PreparedStatement ptst = conn.prepareStatement(sql);
                ptst.setString(1, pi.getText());
                ptst.setString(2, di.getText());
                ptst.setString(3, ts.getText());
                ptst.setString(4, ds.getText());

                ptst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Appointment booked successfully.");
            }
            pi.setText(""); di.setText(""); ts.setText(""); ds.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
