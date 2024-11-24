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

public class LoginUI extends JFrame {

    private Container cont;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin, btnRegister;

    public LoginUI() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 720);
        cont = this.getContentPane();
        cont.setLayout(null);

        // Bên trái - Hình ảnh (leftPanel)
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 600, 720);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(new Color(255, 222, 89));

        // Tạo hình ảnh với ImageIcon
        ImageIcon imgIcon = new ImageIcon("src\\images\\login_image.jpg");

        // Điều chỉnh kích thước hình ảnh sao cho vừa với panel mà không bị méo
        Image img = imgIcon.getImage();
        Image scaledImg = img.getScaledInstance(600, 580, Image.SCALE_SMOOTH); // Điều chỉnh chiều cao thành 300 để chiếm phần trên cùng
        imgIcon = new ImageIcon(scaledImg);  // Cập nhật lại ImageIcon với hình ảnh đã được chỉnh lại kích thước

        // Tạo JLabel để hiển thị hình ảnh
        JLabel imgLabel = new JLabel(imgIcon);

        // Thêm JLabel vào phần trên cùng của leftPanel
        leftPanel.add(imgLabel, BorderLayout.NORTH); // Sử dụng BorderLayout.NORTH để đặt hình ảnh lên trên cùng
        
        // Tạo BoxLayout cho phần dưới (văn bản)
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(255, 222, 89));
        textPanel.setBounds(0, 580, 600, 100);

        // Tạo JLabel cho dòng chữ và căn giữa
        JLabel quoteLabel1 = new JLabel("Sách là chất gây nghiện hợp pháp duy nhất!");
        quoteLabel1.setFont(new Font("Arial", Font.ITALIC, 28)); // Phông chữ in nghiêng
        quoteLabel1.setForeground(Color.BLACK);
        quoteLabel1.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa

        JLabel quoteLabel2 = new JLabel("- Andrea Aybar -");
        quoteLabel2.setFont(new Font("Arial", Font.ITALIC, 28)); // Phông chữ in nghiêng
        quoteLabel2.setForeground(Color.BLACK);
        quoteLabel2.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa

        // Thêm các dòng vào textPanel
        textPanel.add(quoteLabel1);
        textPanel.add(Box.createVerticalStrut(10)); // Thêm khoảng cách giữa các dòng
        textPanel.add(quoteLabel2);

        // Thêm textPanel vào leftPanel
        leftPanel.add(textPanel);
        
        cont.add(leftPanel);
        
        // Bên phải - Các thành phần chức năng đăng nhập (rightPanel)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(600, 0, 600, 720);
        rightPanel.setBackground(new Color(255, 189, 89));

        // Nhãn "Đăng nhập"
        JLabel lblLogin = new JLabel("Đăng nhập");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 42));
        lblLogin.setBounds(200, 10, 300, 100);
        rightPanel.add(lblLogin);

        // Tài khoản
        JLabel lblUsername = new JLabel("Tài khoản");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 30));
        lblUsername.setBounds(100, 150, 200, 40);
        rightPanel.add(lblUsername);

        // Tạo TextField cho tài khoản
        tfUsername = new JTextField();
        tfUsername.setFont(new Font("Arial", Font.BOLD, 30));
        tfUsername.setBounds(100, 210, 400, 40);
        rightPanel.add(tfUsername);

        // Mật khẩu
        JLabel lblPassword = new JLabel("Mật khẩu");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 30));
        lblPassword.setBounds(100, 300, 200, 40);
        rightPanel.add(lblPassword);

        // Tạo PasswordField cho mật khẩu
        pfPassword = new JPasswordField();
        pfPassword.setFont(new Font("Arial", Font.BOLD, 30));
        pfPassword.setBounds(100, 360, 400, 40);
        rightPanel.add(pfPassword);

        // Nút "Đăng nhập"
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 42));
        btnLogin.setBackground(new Color(255, 87, 87));
        btnLogin.setBounds(160, 450, 300, 70);
        rightPanel.add(btnLogin);

        // Nút "Chưa có tài khoản? Đăng ký ngay"
        btnRegister = new JButton("Chưa có tài khoản? Đăng ký ngay");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 24));
        btnRegister.setBounds(80, 600, 450, 50);
        btnRegister.setContentAreaFilled(false);
        btnRegister.setBorderPainted(false);
        rightPanel.add(btnRegister);

        // Thêm rightPanel vào container chính
        cont.add(rightPanel);
        
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new RegisterUI().setVisible(true);
                dispose();
            }
	});
        
        // Xử lý sự kiện khi nhấn nút Đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());
                
                // Kiểm tra đăng nhập
                LoginModify loginModify = new LoginModify();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                } else if (!loginModify.isAccountExists(username)) {
                    JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại!");
                } else if (loginModify.validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                    new LibraryDashboard();
                    dispose();  // Đóng cửa sổ đăng nhập
                } else {
                    JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác!");
                }
            }
        });
    }

}