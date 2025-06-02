package view;

import magicwindow.DBManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewAppointmentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public ViewAppointmentPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new JLabel("View Appointments");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        
        model = new DefaultTableModel(new String[]{"Appointment ID", "Patient ID", "Doctor ID", "Time Slot", "Disease"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(204, 229, 255));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setBackground(new Color(0, 153, 76));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(140, 35));
        refreshButton.addActionListener(e -> loadAppointments());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadAppointments();
    }

    private void loadAppointments() {
        model.setRowCount(0); 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DBManager.getConnection(); Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM appointments";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("AppointmentID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getString("TimeSlot"),
                        rs.getString("Disease")
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
