package view;

import magicwindow.DBManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DeleteAppointmentPanel extends JPanel {
    private JTable appointmentTable;
    private JTextField appointmentIdField;
    private JButton deleteButton, viewButton;

    public DeleteAppointmentPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Delete Appointment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(new Color(204, 0, 0));
        add(title, BorderLayout.NORTH);

    
        appointmentTable = new JTable(new DefaultTableModel(
                new Object[]{"AppointmentID", "PatientID", "DoctorID", "Date", "Time Slot", "Disease", "Status"}, 0));
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Delete by Appointment ID"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel idLabel = new JLabel("Appointment ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        bottomPanel.add(idLabel, gbc);

        appointmentIdField = new JTextField(10);
        gbc.gridx = 1;
        bottomPanel.add(appointmentIdField, gbc);

        deleteButton = new JButton("Delete Appointment");
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deleteButton.setFocusPainted(false);
        gbc.gridx = 2;
        bottomPanel.add(deleteButton, gbc);

        viewButton = new JButton("View All Appointments");
        viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 3;
        bottomPanel.add(viewButton, gbc);

        add(bottomPanel, BorderLayout.SOUTH);

    
        viewButton.addActionListener(e -> loadAppointments());
        deleteButton.addActionListener(e -> deleteAppointment());
    }

    private void loadAppointments() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DBManager.getConnection();
            String sql = "SELECT * FROM appointments";
            PreparedStatement ptst = conn.prepareStatement(sql);
            ResultSet rs = ptst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("AppointmentID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getDate("AppointmentDate"),
                        rs.getTime("TimeSlot"),
                        rs.getString("Disease"),
                        rs.getString("Status")
                });
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAppointment() {
        String idText = appointmentIdField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Appointment ID.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Appointment ID " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DBManager.getConnection();

                String sql = "DELETE FROM appointments WHERE AppointmentID = ?";
                PreparedStatement ptst = conn.prepareStatement(sql);
                ptst.setInt(1, id);
                int rows = ptst.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Appointment deleted successfully.");
                    loadAppointments();
                    appointmentIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Appointment ID not found.", "Delete Failed", JOptionPane.ERROR_MESSAGE);
                }

                conn.close();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Appointment ID must be a number.", "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting appointment:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
