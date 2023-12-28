package com.TourismAgencySystem.Model;

import com.TourismAgencySystem.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminManager {
    private int id;
    private String name;
    private String userName;
    private String password;
    private String passwordAgain;

    public AdminManager(int id, String name, String userName, String password, String passwordAgain) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.passwordAgain = passwordAgain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
    public static ArrayList<AdminManager> getList(){
            ArrayList<AdminManager> admins=new ArrayList<>();
            AdminManager obj;
            String str = "SELECT * FROM admin";
            try(Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery(str)) {
                while(rs.next()){
                    obj = new AdminManager(rs.getInt("id"),rs.getString("name"),rs.getString("user_name"),rs.getString("password"),rs.getString("password_again"));
                    admins.add(obj);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return admins;
        }
    public static boolean add(String name, String userName, String password, String passwordAgain){
        String sql = "INSERT INTO admin (name , user_name, password, password_again) VALUES (?, ?, ?, ?);";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, passwordAgain);

            int result = preparedStatement.executeUpdate();

            return result >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getFetch(String userName,String password){
        String sql = "SELECT * FROM admin WHERE user_name = ? AND password = ?";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
