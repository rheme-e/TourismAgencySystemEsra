package com.TourismAgencySystem.Model;

import com.TourismAgencySystem.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReservationManager {
    private int id;
    private String name;
    private String idNo;
    private String telephone;

    public ReservationManager(int id, String name, String idNo, String telephone) {
        this.id = id;
        this.name = name;
        this.idNo = idNo;
        this.telephone = telephone;
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

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static ArrayList<ReservationManager> getlist(){
        ArrayList<ReservationManager> customers=new ArrayList<>();
        ReservationManager obj;
        String str = "SELECT * FROM reservation";
        try(Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(str)) {
            while(rs.next()){
                obj = new ReservationManager(rs.getInt("id"),rs.getString("name"),rs.getString("id_no"),rs.getString("telephone"));
                customers.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public static boolean add( String name, String idNo, String telephone){
        String sql = "INSERT INTO reservation (name, id_no, telephone) VALUES ( ?, ?, ?);";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, idNo);
            preparedStatement.setString(3, telephone);

            int result = preparedStatement.executeUpdate();

            return result >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
