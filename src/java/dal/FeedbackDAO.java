/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Feedback;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Southern Alula
 */
public class FeedbackDAO extends DBContext {

    public List<Feedback> getFeedbackById(int id) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback\n"
                + "where product_id = " + id;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setId(rs.getInt("id"));
                f.setRating(rs.getInt("rating"));
                f.setNote(rs.getString("note"));
                f.setDate(rs.getString("date"));
                UserDAO ud = new UserDAO();
                User u = ud.getUserById(rs.getInt("user_id"));
                f.setUser(u);
                f.setProduct_id(rs.getInt("product_id"));
                list.add(f);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;

    }

    public void insertFeedback(User u, String note, int rating, int product_id) {
        LocalDateTime curDate = java.time.LocalDateTime.now();
        String date = curDate.toString();
        String sql = "insert into Feedback values (?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, product_id);
            st.setString(2, date);
            st.setInt(3, rating);
            st.setString(4, note);
            st.setInt(5, u.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int getAvgRating(int id) {
        String sql = "select avg(cast (rating as float)) as star from Feedback where product_id = ?";
        int result = -1;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getInt("star");
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static void main(String[] args) {
        FeedbackDAO fd = new FeedbackDAO();
        List<Feedback> list = fd.getFeedbackById(25);
        System.out.println(fd.getAvgRating(1));
    }

}
