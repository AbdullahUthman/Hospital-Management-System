//package magicwindow;
//
//import javax.swing.*;
//import java.awt.*;
//import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
//
//public class MagicWindowFrame extends JFrame {
//    CardLayout cardLayout = new CardLayout();
//    JPanel contentPanel = new JPanel(cardLayout);
//
//    public MagicWindowFrame() {
//        setTitle("Magic Window Workspace");
//        setSize(1200, 9000);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        // Sidebar
//        JPanel sidebar = new JPanel(new GridLayout(4, 1));
//        JButton btnDashboard = new JButton("Dashboard");
//        JButton btnStudents = new JButton("Student CRUD");
//        JButton btnSetting = new JButton("Setting");
//        JButton btnAbout = new JButton("About");
//
//        sidebar.add(btnDashboard);
//        sidebar.add(btnStudents);
//        sidebar.add(btnSetting);
//        sidebar.add(btnAbout);
//
//        // Content Panels
//        contentPanel.add(new view.DashboardPanel(), "Dashboard");
//        contentPanel.add(new view.StudentPanel(), "Student");
//        contentPanel.add(new view.SettingPanel(), "Setting");
//        contentPanel.add(new view.AboutPanel(), "About");
//
//        btnDashboard.addActionListener(e -> cardLayout.show(contentPanel, "Dashboard"));
//        btnStudents.addActionListener(e -> cardLayout.show(contentPanel, "Student"));
//        btnSetting.addActionListener(e -> cardLayout.show(contentPanel, "Setting"));
//        btnAbout.addActionListener(e -> cardLayout.show(contentPanel, "About"));
//
//        add(sidebar, BorderLayout.WEST);
//        add(contentPanel, BorderLayout.CENTER);
//
//        setVisible(true);
//    }
//}

package magicwindow;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MagicWindowFrame extends JFrame {
    private JPanel contentPanel = new JPanel(new CardLayout());
    private JPanel sidebar = new JPanel();
    private HashMap<String, String[]> moduleSidebarItems = new HashMap<>();
    private CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
    private Font standardFont = new Font("Segoe UI", Font.PLAIN, 16);

    public MagicWindowFrame() {
        setTitle("Hospital Management System");


        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

    
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        String[] modules = {"Patient Module", "Appointment Module", "Billing System", "Admin Panel"};

        for (String module : modules) {
            JButton moduleButton = new JButton(module);
            moduleButton.setFont(standardFont);
            moduleButton.addActionListener(e -> updateSidebar(module));
            navbar.add(moduleButton);
        }
        add(navbar, BorderLayout.BEFORE_FIRST_LINE);

        
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createTitledBorder("Module Options"));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        add(sidebar, BorderLayout.WEST);

        
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(centerLabel("Welcome to Hospital Management System"), "Home");


        contentPanel.add(new view.BookAppointmentPanel(), "Book Appointment");
        contentPanel.add(new view.ViewAppointmentPanel(), "View Appointment");
        contentPanel.add(new view.EditAppointmentPanel(), "Edit Appointment");
        contentPanel.add(new view.DeleteAppointmentPanel(), "Delete Appointment");

        add(contentPanel, BorderLayout.CENTER);

        
        moduleSidebarItems.put("Appointment Module", new String[]{
                "Book Appointment", "View Appointment", "Edit Appointment", "Delete Appointment"
        });

        

        setVisible(true);
    }

    
    private void updateSidebar(String module) {
        sidebar.removeAll();
        String[] items = moduleSidebarItems.getOrDefault(module, new String[]{"No options available"});
        for (String item : items) {
            JButton itemBtn = new JButton(item);
            itemBtn.setFont(standardFont);
            itemBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemBtn.setMaximumSize(new Dimension(200, 40));
            itemBtn.addActionListener(e -> cardLayout.show(contentPanel, item));
            sidebar.add(Box.createVerticalStrut(10)); 
            sidebar.add(itemBtn);
        }
        sidebar.revalidate();
        sidebar.repaint();
    }

    
    private JPanel centerLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MagicWindowFrame::new);
    }
}
