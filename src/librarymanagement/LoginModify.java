/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagement;

/**
 *
 * @author ADMIN
 */
import java.sql.*;

public class LoginModify implements DatabaseInfo {

    // Phương thức kiểm tra thông tin đăng nhập
    public boolean validateLogin(String username, String password) {
        Connection conn = ConnectDB.getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Gán giá trị vào các tham số
            stmt.setString(1, username);
            stmt.setString(2, password); // Lưu ý: Nên mã hóa mật khẩu trước khi lưu và kiểm tra
            
            // Thực thi truy vấn
            ResultSet rs = stmt.executeQuery();
            
            // Nếu có kết quả thì tài khoản và mật khẩu hợp lệ
            if (rs.next()) {
                return true; // Đăng nhập thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Đăng nhập thất bại
    }
    
    // Phương thức kiểm tra xem tài khoản có tồn tại không
    public boolean isAccountExists(String username) {
        Connection conn = ConnectDB.getConnection();
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Tài khoản tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Tài khoản không tồn tại
    }
}