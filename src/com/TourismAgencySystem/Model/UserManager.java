package com.TourismAgencySystem.Model;

import com.TourismAgencySystem.Helper.DBConnector;
import com.TourismAgencySystem.View.HotelManagerGUI;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserManager {
    private int id;
    private int hotelID;
    private int roomID;
    private String roomType;
    private String periodType ;
    private int adultPrice ;
    private String city;
    private String zone ;
    private int capacity;

    public UserManager(int id, int hotelID, int roomID, String roomType, String periodType, int adultPrice, String city, String zone, int capacity) {
        this.id = id;
        this.hotelID = hotelID;
        this.roomID = roomID;
        this.roomType = roomType;
        this.periodType = periodType;
        this.adultPrice = adultPrice;
        this.city = city;
        this.zone = zone;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static ArrayList<UserManager> getlist(){
        ArrayList<UserManager> userTable=new ArrayList<>();
        UserManager obj;
        String str = "SELECT * FROM best";
        try(Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(str)) {
            while(rs.next()){
                obj = new UserManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getInt("room_id"),rs.getString("room_type"),rs.getString("period_type"),rs.getInt("adult_price"),rs.getString("city"),rs.getString("zone"),rs.getInt("capacity"));
                userTable.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userTable;
    }

    public static ArrayList<UserManager> searchUserList(String query, String city, String zone,String periodType,int total) {
        ArrayList<UserManager> searchUserList = new ArrayList<>();
        UserManager obj;
        try (PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query)) {
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, zone);
            preparedStatement.setString(3, periodType);
            preparedStatement.setInt(4, total);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                obj = new UserManager(rs.getInt("id"), rs.getInt("hotel_id"), rs.getInt("room_id"), rs.getString("room_type"),
                        rs.getString("period_type"), rs.getInt("adult_price"), rs.getString("city"), rs.getString("zone"), rs.getInt("capacity"));
                searchUserList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Debugging için çıktıları ekrana yazdıralım
        for (UserManager user : searchUserList) {
            System.out.println("User ID: " + user.getId() + ", Hotel ID: " + user.getHotelID() + ", Room ID: " + user.getRoomID() +
                    ", Room Type: " + user.getRoomType() + ", Period Type: " + user.getPeriodType() + ", Adult Price: " + user.getAdultPrice() +
                    ", City: " + user.getCity() + ", Zone: " + user.getZone() + ", Capacity: " + user.getCapacity());
        }

        System.out.println("Result size: " + searchUserList.size()); // Debugging için

        return searchUserList;
    }




    public static String searchQuery(String city,String zone,String periodType,int total) {
        String query = "SELECT * FROM best WHERE city = ?  AND zone = ? AND period_type = ? AND adult_price >= ?";
        return query;
    }
    public static ArrayList<UserManager> searchUserListAllCity(String query,String periodType,int total) {
        ArrayList<UserManager> searchUserList = new ArrayList<>();
        UserManager obj;
        try (PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query)) {

            preparedStatement.setString(1, periodType);
            preparedStatement.setInt(2, total);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                obj = new UserManager(rs.getInt("id"), rs.getInt("hotel_id"), rs.getInt("room_id"), rs.getString("room_type"),
                        rs.getString("period_type"), rs.getInt("adult_price"), rs.getString("city"), rs.getString("zone"), rs.getInt("capacity"));
                searchUserList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (UserManager user : searchUserList) {
            System.out.println("User ID: " + user.getId() + ", Hotel ID: " + user.getHotelID() + ", Room ID: " + user.getRoomID() +
                    ", Room Type: " + user.getRoomType() + ", Period Type: " + user.getPeriodType() + ", Adult Price: " + user.getAdultPrice() +
                    ", City: " + user.getCity() + ", Zone: " + user.getZone() + ", Capacity: " + user.getCapacity());
        }

        System.out.println("Result size: " + searchUserList.size());

        return searchUserList;
    }




    public static String searchQueryAllCity(String periodType,int total) {
        String query = "SELECT * FROM best WHERE period_type = ? AND adult_price >= ?";
        return query;
    }






    public static boolean add(int hotelID, int roomID, String roomType, String periodType, int adultPrice, String city, String zone, int capacity){
        String sql = "INSERT INTO best (hotel_id, room_id, room_type, period_type, adult_price, city, zone, capacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setInt(1, hotelID);
            preparedStatement.setInt(2, roomID);
            preparedStatement.setString(3,roomType);
            preparedStatement.setString(4,periodType);
            preparedStatement.setInt(5, adultPrice);
            preparedStatement.setString(6,city);
            preparedStatement.setString(7,zone);
            preparedStatement.setInt(8, capacity);

            int result = preparedStatement.executeUpdate();

            return result >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean control(int hotelID, int roomID, String roomType, String periodType, int adultPrice, String city, String zone, int capacity){
        String sql = "SELECT * FROM best WHERE hotel_id = ? AND room_id = ? AND room_type = ? AND period_type = ? AND adult_price = ? AND city = ? AND zone = ? AND capacity = ?";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setInt(1, hotelID);
            preparedStatement.setInt(2, roomID);
            preparedStatement.setString(3,roomType);
            preparedStatement.setString(4,periodType);
            preparedStatement.setInt(5, adultPrice);
            preparedStatement.setString(6,city);
            preparedStatement.setString(7,zone);
            preparedStatement.setInt(8, capacity);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean control(int hotelID, String roomType, String periodType){
        String sql = "SELECT * FROM best WHERE hotel_id = ? AND room_type = ? AND period_type = ? ";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setInt(1, hotelID);
            preparedStatement.setString(2,roomType);
            preparedStatement.setString(3,periodType);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void UpdateTable(){
        for(UserManager hotels : HotelManagerGUI.loadTempManager()){
             int hotelID = hotels.getHotelID();
             int roomID = hotels.getRoomID();
             String roomType = hotels.getRoomType();
             String periodType = hotels.getPeriodType();
             int adultPrice= hotels.getAdultPrice();
             String city = hotels.getCity();
             String zone = hotels.getZone();
             int capacity = hotels.getCapacity();
             if(!UserManager.control(hotelID,roomID,roomType,periodType,adultPrice,city,zone,capacity)){
                 String sql = "INSERT INTO best (hotel_id, room_id, room_type, period_type, adult_price, city, zone, capacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                 try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
                     preparedStatement.setInt(1, hotelID);
                     preparedStatement.setInt(2, roomID);
                     preparedStatement.setString(3,roomType);
                     preparedStatement.setString(4,periodType);
                     preparedStatement.setInt(5, adultPrice);
                     preparedStatement.setString(6,city);
                     preparedStatement.setString(7,zone);
                     preparedStatement.setInt(8, capacity);
                     int result = preparedStatement.executeUpdate();

                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }

        }

    }

    public static boolean deleteHotel(int id){
        String str = "DELETE FROM best WHERE hotel_id = ?";
        try(PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)){
            pr.setInt(1,id);
            int result = pr.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static UserManager getFetch(int hotelId){
        ArrayList<UserManager> userList=new ArrayList<>();
        UserManager obj=null;
        String str = "SELECT * FROM best WHERE hotel_id = ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,hotelId);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new UserManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getInt("room_id"),rs.getString("room_type"),rs.getString("period_type"),rs.getInt("adult_price"),rs.getString("city"),rs.getString("zone"),rs.getInt("capacity"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static UserManager getFetchList(int ID){
        ArrayList<UserManager> userList=new ArrayList<>();
        UserManager obj=null;
        String str = "SELECT * FROM best WHERE id = ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,ID);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new UserManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getInt("room_id"),rs.getString("room_type"),rs.getString("period_type"),rs.getInt("adult_price"),rs.getString("city"),rs.getString("zone"),rs.getInt("capacity"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }


}
