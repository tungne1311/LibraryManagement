/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagement;

/**
 *
 * @author ADMIN
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LibraryDashboard extends JFrame {
    private Container cont;
    private JButton btnLogOut;
    private JButton btnManageBooks;
    private JButton btnManageReaders;
    private JButton btnLoanReturn;
    private JButton btnStatistics;

    public LibraryDashboard() {
        initializeUI();
    }

    private void initializeUI() {
        cont = getContentPane();
        cont.setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 720);
        setTitle("Library Management Dashboard");
        cont.setBackground(new Color(253, 243, 221));

        // Nút Log Out
        btnLogOut = new JButton("Đăng xuất");
        btnLogOut.setBounds(1010, 30, 120, 30); // Kích thước nút lớn hơn cho dễ nhìn
        btnLogOut.setBackground(new Color(255, 192, 203));
        btnLogOut.setForeground(Color.darkGray);
        btnLogOut.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginUI().setVisible(true);
                dispose(); // Ẩn cửa sổ LibraryDashboard hiện tại
            }
        });
        cont.add(btnLogOut);

        JPanel leftPanel = createLeftPanel();
        cont.add(leftPanel);

        JPanel rightPanel = createRightPanel();
        cont.add(rightPanel);

        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 600, 720);
        leftPanel.setBackground(new Color(253, 243, 221));
        leftPanel.setLayout(new GridBagLayout());

        // Tạo logo
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src\\images\\logo_ptit.png");

        logoLabel.setIcon(logoIcon);

        // Tạo tiêu đề
        JLabel titleLabel = new JLabel("QUẢN LÝ THƯ VIỆN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Tạo JPanel chứa cả logo và tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(253, 243, 221));
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(logoLabel);
        titlePanel.add(titleLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        leftPanel.add(titlePanel, gbc);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(400, 442));
        ImageIcon imageIcon = new ImageIcon("src\\images\\dashboard.png");
        imageLabel.setIcon(imageIcon);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(imageLabel, gbc);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(680, 100, 450, 570);
        rightPanel.setBackground(new Color(253, 243, 221));
        rightPanel.setLayout(new GridLayout(2, 2, 20, 20));

        btnManageBooks = createButtonWithAction("Quản Lý Sách", new Color(202, 170, 205), "src\\images\\btn_icon.png", e -> openBookManagement());
        btnManageReaders = createButtonWithAction("Quản Lý Độc Giả", new Color(203, 150, 46), "src\\images\\btn_icon.png", e -> openReaderManagement());
        btnLoanReturn = createButtonWithAction("Mượn Trả Sách", new Color(239, 96, 30), "src\\images\\btn_icon.png", e -> openLoanReturn());
        btnStatistics = createButtonWithAction("Thống Kê", new Color(255, 216, 63), "src\\images\\btn_icon.png", e -> openStatistics());

        rightPanel.add(btnManageBooks);
        rightPanel.add(btnManageReaders);
        rightPanel.add(btnLoanReturn);
        rightPanel.add(btnStatistics);

        return rightPanel;
    }

    private JButton createButtonWithAction(String text, Color bgColor, String imagePath, ActionListener actionListener) {
        // Tạo button
        JButton button = new JButton();
        button.setBackground(bgColor);
        button.setFont(new Font("Arial", Font.BOLD, 20));

        // Tạo panel chứa icon và text
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout
        panel.setBackground(bgColor); // Đảm bảo nền của panel tương thích với button

        // Tạo icon
        ImageIcon icon = new ImageIcon(imagePath);
        icon = new ImageIcon(icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        JLabel iconLabel = new JLabel(icon);

        // Tạo text
        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Đặt icon lên trên và text dưới icon
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(iconLabel, gbc); // Icon ở trên

        gbc.gridy = 1;
        panel.add(textLabel, gbc); // Text ở dưới icon

        // Thêm panel vào button
        button.add(panel);

        // Thêm hành động cho nút
        button.addActionListener(actionListener);

        return button;
    }

    private void openBookManagement() {
        new BookManagementUI();
        dispose();
    }

    private void openReaderManagement() {
        new ReaderManagementUI();
        dispose();
    }
    
    private void openStatistics(){
        new StatisticUI();
        dispose();
    }
    
    private void openLoanReturn(){
        new LoanUI();
        dispose();
    }
}