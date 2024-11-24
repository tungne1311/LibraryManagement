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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI extends JFrame {

    private Container cont;
    private JTextField tfUsername;
    private JPasswordField pfPassword, pfConfirmPassword;
    private JButton btnRegister, btnLogin;

    public RegisterUI() {
        setTitle("Đăng ký");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 720);
        cont = this.getContentPane();
        cont.setLayout(null);

        // Bên phải - Hình ảnh (rightPanel)
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(600, 0, 600, 720);
        rightPanel.setLayout(new BorderLayout());

        // Tạo hình ảnh với ImageIcon
        ImageIcon imgIcon = new ImageIcon("src\\images\\register_img.jpg");

        // Điều chỉnh kích thước hình ảnh sao cho vừa với panel mà không bị méo
        Image img = imgIcon.getImage();
        Image scaledImg = img.getScaledInstance(600, 720, Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(scaledImg);  // Cập nhật lại ImageIcon với hình ảnh đã được chỉnh lại kích thước

        // Tạo JLabel để hiển thị hình ảnh
        JLabel imgLabel = new JLabel(imgIcon);

        // Thêm JLabel vào phần trên cùng của rightPanel
        rightPanel.add(imgLabel);

        // Thêm rightPanel vào container chính
        cont.add(rightPanel);

        // Bên trái - Các thành phần chức năng đăng ký (leftPanel)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 600, 720);
        leftPanel.setBackground(new Color(191, 239, 255));

        // Nhãn "Đăng ký"
        JLabel lblRegister = new JLabel("Đăng ký");
        lblRegister.setFont(new Font("Arial", Font.BOLD, 42));
        lblRegister.setBounds(230, 10, 300, 100);
        leftPanel.add(lblRegister);

        // Tài khoản
        JLabel lblUsername = new JLabel("Tài khoản");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 30));
        lblUsername.setBounds(100, 100, 200, 40);
        leftPanel.add(lblUsername);

        // Tạo TextField cho tài khoản
        tfUsername = new JTextField();
        tfUsername.setFont(new Font("Arial", Font.BOLD, 30));
        tfUsername.setBounds(100, 160, 400, 40);
        leftPanel.add(tfUsername);

        // Mật khẩu
        JLabel lblPassword = new JLabel("Mật khẩu");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 30));
        lblPassword.setBounds(100, 250, 200, 40);
        leftPanel.add(lblPassword);

        // Tạo PasswordField cho mật khẩu
        pfPassword = new JPasswordField();
        pfPassword.setFont(new Font("Arial", Font.BOLD, 30));
        pfPassword.setBounds(100, 310, 400, 40);
        leftPanel.add(pfPassword);

        // Nhập lại mật khẩu
        JLabel lblConfirmPassword = new JLabel("Nhập lại mật khẩu");
        lblConfirmPassword.setFont(new Font("Arial", Font.BOLD, 30));
        lblConfirmPassword.setBounds(100, 400, 300, 40);
        leftPanel.add(lblConfirmPassword);

        // Tạo PasswordField cho nhập lại mật khẩu
        pfConfirmPassword = new JPasswordField();
        pfConfirmPassword.setFont(new Font("Arial", Font.BOLD, 30));
        pfConfirmPassword.setBounds(100, 460, 400, 40);
        leftPanel.add(pfConfirmPassword);

        // Nút "Đăng ký"
        btnRegister = new JButton("Đăng ký");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 42));
        btnRegister.setBackground(new Color(0, 205, 205));
        btnRegister.setBounds(160, 520, 300, 70);
        leftPanel.add(btnRegister);

        // Nút "Đã có tài khoản? Đăng nhập"
        btnLogin = new JButton("Đã có tài khoản? Đăng nhập ngay");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 24));
        btnLogin.setForeground(new Color(0, 139, 69));
        btnLogin.setBounds(80, 610, 450, 50);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        leftPanel.add(btnLogin);

        // Thêm leftPanel vào container chính
        cont.add(leftPanel);
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new LoginUI().setVisible(true);
                dispose();
            }
	});
        
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());
                String confirmPassword = new String(pfConfirmPassword.getPassword());

                // Kiểm tra thông tin nhập vào
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterUI.this, 
                        "Hãy nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(RegisterUI.this, 
                        "Vui lòng kiểm tra lại mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Gọi backend để đăng ký
                boolean isRegistered = RegisterModify.registerUser(username, password, confirmPassword);

                if (isRegistered) {
                    JOptionPane.showMessageDialog(RegisterUI.this, 
                        "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    new LoginUI().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterUI.this, 
                        "Tài khoản đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    
}