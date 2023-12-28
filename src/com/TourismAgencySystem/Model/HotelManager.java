package com.TourismAgencySystem.Model;

import com.TourismAgencySystem.Helper.DBConnector;
import com.TourismAgencySystem.View.HotelManagerGUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HotelManager {
    private int id;
    private String hotelName;
    private String cityName;
    private String zoneName;
    private String address;
    private String email;
    private String telephone;
    private String stars;
    private String types;

    public HotelManager(int id, String hotelName, String cityName, String zoneName, String address, String email, String telephone, String stars, String types) {
        this.id = id;
        this.hotelName = hotelName;
        this.cityName = cityName;
        this.zoneName = zoneName;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.stars = stars;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {return hotelName;}

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public static ArrayList<HotelManager> getlist(){
        ArrayList<HotelManager> hotels=new ArrayList<>();
        HotelManager obj;
        String str = "SELECT * FROM hotels";
        try(Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(str)) {
            while(rs.next()){
                obj = new HotelManager(rs.getInt("id"),rs.getString("hotel_name"),rs.getString("city_name"),rs.getString("city_zone"),rs.getString("address"),rs.getString("email"),rs.getString("telephone"),rs.getString("stars"),rs.getString("type"));
                hotels.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }
    public static boolean add(String hotelName, String cityName, String zoneName, String address, String email, String telephone, String stars, String types){
        String sql = "INSERT INTO hotels (hotel_name, city_name, city_zone, address, email, telephone, stars, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setString(1, hotelName);
            preparedStatement.setString(2, cityName);
            preparedStatement.setString(3, zoneName);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, telephone);
            preparedStatement.setString(7, stars);
            preparedStatement.setString(8, types);
            int result = preparedStatement.executeUpdate();

            return result >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean getFetchEmail(String email){
        String sql = " SELECT * FROM hotels WHERE email = ? ;";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean getFetchTelephone(String telephone){
        String sql = " SELECT * FROM hotels WHERE telephone = ? ;";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setString(1, telephone);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean getFetchHotelName(String hotelName){
        String sql = " SELECT * FROM hotels WHERE hotel_name = ? ;";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setString(1, hotelName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static HotelManager getFetch(int id){
        ArrayList<HotelManager> hotels=new ArrayList<>();
        HotelManager obj=null;
        String str = "SELECT * FROM hotels WHERE id = ?;";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new HotelManager(rs.getInt("id"),rs.getString("hotel_name"),rs.getString("city_name"),rs.getString("city_zone"),rs.getString("address"),rs.getString("email"),rs.getString("telephone"),rs.getString("stars"),rs.getString("type"));
                hotels.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static HotelManager getFetch(String hotelName){
        ArrayList<HotelManager> hotels=new ArrayList<>();
        HotelManager obj=null;
        String str = "SELECT * FROM hotels WHERE hotel_name = ?;";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setString(1,hotelName);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new HotelManager(rs.getInt("id"),rs.getString("hotel_name"),rs.getString("city_name"),rs.getString("city_zone"),rs.getString("address"),rs.getString("email"),rs.getString("telephone"),rs.getString("stars"),rs.getString("type"));
                hotels.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static ArrayList<HotelManager> getFetch(String cityName, String zoneName) {
        ArrayList<HotelManager> hotels = new ArrayList<>();
        HotelManager obj = null;
        String str = "SELECT * FROM hotels WHERE city_name = ? AND city_zone = ?";
        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)) {
            pr.setString(1, cityName);
            pr.setString(2, zoneName);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                obj = new HotelManager(
                        rs.getInt("id"),
                        rs.getString("hotel_name"),
                        rs.getString("city_name"),
                        rs.getString("city_zone"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("stars"),
                        rs.getString("type")
                );
                hotels.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    public static List<String> getAllHotelNames() {
        List<String> hotelNames = new ArrayList<>();
        String query = "SELECT hotel_name FROM hotels;";
        try (PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String hotelName = resultSet.getString("hotel_name");
                hotelNames.add(hotelName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelNames;
    }


    public static boolean delete(int id){
        String str = "DELETE FROM hotels WHERE id = ?";
        try(PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)){
            pr.setInt(1,id);
            int result = pr.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean update(int id, String name, String city, String zone, String address, String email, String telephone, String stars, String type) {
        String str = "UPDATE hotels SET hotel_name = ?, city_name = ?, city_zone = ?, address = ?, email = ?, telephone = ?, stars = ?, type = ? WHERE id = ?";
        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)) {
            pr.setString(1, name);
            pr.setString(2, city);
            pr.setString(3, zone);
            pr.setString(4, address);
            pr.setString(5, email);
            pr.setString(6, telephone);
            pr.setString(7, stars);
            pr.setString(8, type);
            pr.setInt(9, id);

            int result = pr.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
