/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagement;

/**
 *
 * @author ADMIN
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterModify {

    // Phương thức đăng ký người dùng
    public static boolean registerUser(String username, String password, String confirmPassword) {
        // Kiểm tra xem tài khoản đã tồn tại hay chưa
        if (isUsernameExist(username)) {
            return false; // Tài khoản đã tồn tại
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có trùng khớp không
        if (!password.equals(confirmPassword)) {
            return false; // Mật khẩu không khớp
        }

        // Thực hiện lưu thông tin người dùng vào cơ sở dữ liệu
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu đã thêm vào cơ sở dữ liệu thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Đã có lỗi khi thực hiện đăng ký
    }

    // Kiểm tra xem tài khoản đã tồn tại trong cơ sở dữ liệu hay chưa
    private static boolean isUsernameExist(String username) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Nếu có kết quả trả về, tài khoản đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Không có tài khoản trùng tên
    }
}