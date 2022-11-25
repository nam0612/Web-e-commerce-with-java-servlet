package dal;

import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DBContext {

    public User checkAuth(String username, String password) throws SQLException {
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[nickname]\n"
                + "      ,[email]\n"
                + "      ,[phone_number]\n"
                + "      ,[address]\n"
                + "      ,[role_id]\n"
                + "  FROM [dbo].[Users] where [username] = '" + username + "' and [password] = '" + password + "'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setAddress(rs.getString("address"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(password);
                user.setRoleId(rs.getInt("role_id"));

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public User getUser(String username) {
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[nickname]\n"
                + "      ,[email]\n"
                + "      ,[phone_number]\n"
                + "      ,[address]\n"
                + "      ,[role_id]\n"
                + "  FROM [dbo].[Users] where [username] = '" + username + "'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setAddress(rs.getString("address"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRoleId(rs.getInt("role_id"));

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

//    public int insert(String username,String password,String nickname, String email,String phone,String address) {
//        String sql = "INSERT INTO [dbo].[Users]\n"
//                + "           ([username]\n"
//                + "           ,[password]\n"
//                + "           ,[nickname]\n"
//                + "           ,[email]\n"
//                + "           ,[phone_number]\n"
//                + "           ,[address]\n"
//                + "           ,[role_id])\n"
//                + "     VALUES\n"
//                + "           (?, ?, ?, ?, ?, ?, ?)";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setString(1, username);
//            st.setString(2, password);
//            st.setString(3, nickname);
//            st.setString(4, email);
//            st.setString(5, phone);
//            st.setString(6, address);
//            st.setInt(7, 2);
//
//            return st.executeUpdate();
//
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//
//        return 0;
//    }
    
    public int insert(User a) {
        String sql = "INSERT INTO [dbo].[Users]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[nickname]\n"
                + "           ,[email]\n"
                + "           ,[phone_number]\n"
                + "           ,[address]\n"
                + "           ,[role_id])\n"
                + "     VALUES\n"
                + "           (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, a.getUsername());
            st.setString(2, a.getPassword());
            st.setString(3, a.getNickname());
            st.setString(4, a.getEmail());
            st.setString(5, a.getPhone_number());
            st.setString(6, a.getAddress());
            st.setInt(7, 2);

            return st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return 0;
    }
    
    public User getUserById(int id) {
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[nickname]\n"
                + "      ,[email]\n"
                + "      ,[phone_number]\n"
                + "      ,[address]\n"
                + "      ,[role_id]\n"
                + "  FROM [dbo].[Users] where [id] = '" + id + "'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAddress(rs.getString("address"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRoleId(rs.getInt("role_id"));

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }
    
    
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        User a = new User(1, "name", "name", "name", "name", "123132", "hanoi", 2);
        if( dao.insert(a) == 1) {
            System.out.println("ojk");
        } else {
            System.out.println("not");
        }
    }
}
