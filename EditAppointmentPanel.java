package view;

import magicwindow.DBManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditAppointmentPanel extends JPanel {
    private JTextField appointmentIdField, dateField, timeField;
    private JButton updateButton;

    public EditAppointmentPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Edit Appointment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

    
        gbc.gridy++;
        add(new JLabel("Appointment ID:"), gbc);
        appointmentIdField = new JTextField(15);
        gbc.gridx = 1;
        add(appointmentIdField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("New Appointment Date (YYYY-MM-DD):"), gbc);
        dateField = new JTextField(15);
        gbc.gridx = 1;
        add(dateField, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("New Time Slot (HH:MM:SS):"), gbc);
        timeField = new JTextField(15);
        gbc.gridx = 1;
        add(timeField, gbc);

    
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        updateButton = new JButton("Update Appointment");
        updateButton.setBackground(new Color(0, 153, 76));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateButton.setFocusPainted(false);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateButton.addActionListener(e -> updateAppointment());
        add(updateButton, gbc);
    }

    private void updateAppointment() {
        String aid = appointmentIdField.getText().trim();
        String date = dateField.getText().trim();
        String time = timeField.getText().trim();

        if (aid.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DBManager.getConnection()) {
                
                String checkSql = "SELECT * FROM appointments WHERE AppointmentID = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, Integer.parseInt(aid));
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    String updateSql = "UPDATE appointments SET AppointmentDate = ?, TimeSlot = ? WHERE AppointmentID = ?";
                    PreparedStatement ptst = conn.prepareStatement(updateSql);
                    ptst.setString(1, date);
                    ptst.setString(2, time);
                    ptst.setInt(3, Integer.parseInt(aid));

                    ptst.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
                    appointmentIdField.setText("");
                    dateField.setText("");
                    timeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Appointment ID not found!", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating appointment:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
