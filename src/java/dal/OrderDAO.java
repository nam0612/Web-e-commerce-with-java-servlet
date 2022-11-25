/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Cart;
import entity.Item;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Southern Alula
 */
public class OrderDAO extends DBContext {

//    public void addOrder(User u, Cart cart) {
//        LocalDate curDate = java.time.LocalDate.now();
//        String date = curDate.toString();
//        String sql = "INSERT INTO [dbo].[Orders]\n"
//                    + "           ,[user_id]\n"
//                    + "           ,[order_date]\n"
//                    + "           ,[total_money])\n"
//                    + "     VALUES\n"
//                    + "           (?, ?, ?)";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setInt(1, u.getId());
//            st.setString(2, date);
//            st.setDouble(3, cart.getTotalMoney());
//            st.executeUpdate();
//        } catch (Exception e) {
//        }
//    }
    
    
     public void addOrder(User c,Cart cart){
        LocalDate curDate=LocalDate.now();
        String date=curDate.toString();
        try{
            //add order
            String sql="insert into [Orders] ([user_id]\n" +
"           ,[order_date]\n" +
"           ,[total_money]) values(?,?,?)";
            PreparedStatement st=connection.prepareStatement(sql);
            st.setInt(1, c.getId());
            st.setString(2, date);
            st.setDouble(3, cart.getTotalMoney());
            st.executeUpdate();
            //lay id cua order vua add
            String sql1="select top 1 id from [Orders] order by id desc";
            PreparedStatement st1=connection.prepareStatement(sql1);
            ResultSet rs=st1.executeQuery();
            //add bang OrderDetail
            if(rs.next()){
                int oid=rs.getInt("id");
                for(Item i:cart.getItems()){
                    String sql2="insert into [Order_Details] values(?,?,?,?,?)";
                    PreparedStatement st2=connection.prepareStatement(sql2);
                    st2.setInt(1, oid);
                    st2.setInt(2, i.getProduct().getId());
                    st2.setDouble(3, i.getProduct().getPrice()-i.getProduct().getPrice()*i.getProduct().getDiscount()/100);
                    st2.setInt(4, i.getQuantity());
                    st2.setDouble(5, i.getTotal()-i.getTotal()*i.getProduct().getDiscount()/100);
                    st2.executeUpdate();
                }
            }
            //cap nhat lai so luong san pham
            String sql3="update Product set quantity=quantity-? where id=?";
            PreparedStatement st3=connection.prepareStatement(sql3);
            for(Item i:cart.getItems()){
                st3.setInt(1, i.getQuantity());
                st3.setInt(2, i.getProduct().getId());
                st3.executeUpdate();
            }
        }catch(SQLException e){
            
        }
    }
}
