package com.TourismAgencySystem.Model;

import com.TourismAgencySystem.Helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private int Id;
    private int hotelId;
    private String type;
    private int stock;
    private int available;
    private int capacity;
    private String numbersOfBeds;
    private String hotel_name;

    public RoomManager(int id, int hotelId, String type, int stock, int available, int capacity, String numbersOfBeds) {
        Id = id;
        this.hotelId = hotelId;
        this.type = type;
        this.capacity = capacity;
        this.numbersOfBeds = numbersOfBeds;
        HotelManager hotel = HotelManager.getFetch(hotelId);
        if (hotel != null) {
            this.hotel_name = HotelManager.getFetch(hotelId).getHotelName();            // ...
        } else {
            // Hotel null döndü, burada bir hata durumuyla başa çıkmalısınız.
            // ...
        }


        if (available > stock) {
            this.available = stock;
        } else {
            this.available = available;
        }

        this.stock = stock;
    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getNumbersOfBeds() {
        return numbersOfBeds;
    }

    public void setNumbersOfBeds(String numbersOfBeds) {
        this.numbersOfBeds = numbersOfBeds;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public static ArrayList<RoomManager> getlist(){
        ArrayList<RoomManager> rooms=new ArrayList<>();
        RoomManager obj;
        String str = "SELECT * FROM room";
        try(Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(str)) {
            while(rs.next()){
                obj = new RoomManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("type"),rs.getInt("stock"),rs.getInt("available"),rs.getInt("capacity"),rs.getString("numbers_of_beds"));
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }
    public static boolean add(int hotelId, String type, int stock, int available, int capacity, String numbersOfBeds){
        String sql = "INSERT INTO room (hotel_id, type, stock, available, capacity, numbers_of_beds) VALUES (?, ?, ?, ?, ?, ?);";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, stock);
            preparedStatement.setInt(4, available);
            preparedStatement.setInt(5, capacity);
            preparedStatement.setString(6, numbersOfBeds);

            int result = preparedStatement.executeUpdate();

            return result >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(int id, int hotelId, String type, int stock, int available, int capacity, String numbersOfBeds) {
        String str = "UPDATE room SET hotel_id = ?, type = ?, stock = ?, available = ?, capacity = ?, numbers_of_beds = ?  WHERE id = ?";
        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1, hotelId);
            pr.setString(2, type);
            pr.setInt(3, stock);
            pr.setInt(4, available);
            pr.setInt(5, capacity);
            pr.setString(6, numbersOfBeds);
            pr.setInt(7, id);
            int result = pr.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean delete(int id){
        String str = "DELETE FROM room WHERE id = ?";
        try(PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)){
            pr.setInt(1,id);
            int result = pr.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteHotel(int id){
        String str = "DELETE FROM room WHERE hotel_id = ?";
        try(PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)){
            pr.setInt(1,id);
            int result = pr.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public static RoomManager getFetch(int id){
        ArrayList<RoomManager> rooms=new ArrayList<>();
        RoomManager obj=null;
        String str = "SELECT * FROM room WHERE id = ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new RoomManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("type"),rs.getInt("stock"),rs.getInt("available"),rs.getInt("capacity"),rs.getString("numbers_of_beds"));
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static RoomManager getFetchForCapacity(int hotelId , int roomId){
        ArrayList<RoomManager> rooms=new ArrayList<>();
        RoomManager obj=null;
        String str = "SELECT * FROM room WHERE id = ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,hotelId);
            pr.setInt(2,roomId);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new RoomManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("type"),rs.getInt("stock"),rs.getInt("available"),rs.getInt("capacity"),rs.getString("numbers_of_beds"));
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static RoomManager getFetchRoomID(int hotelId){
        ArrayList<RoomManager> rooms=new ArrayList<>();
        RoomManager obj=null;
        String str = "SELECT * FROM room WHERE hotel_id = ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,hotelId);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new RoomManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("type"),rs.getInt("stock"),rs.getInt("available"),rs.getInt("capacity"),rs.getString("numbers_of_beds"));
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static boolean getFetchBoolean(int hotelID){
        String sql = "SELECT * FROM room WHERE hotel_id = ?";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setInt(1, hotelID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static ArrayList<RoomManager> getFetchType(int hotelId) {
        ArrayList<RoomManager> roomTypes = new ArrayList<>();

        String query = "SELECT * FROM room WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    RoomManager room = new RoomManager(
                            resultSet.getInt("id"),
                            resultSet.getInt("hotel_id"),
                            resultSet.getString("type"),
                            resultSet.getInt("stock"),
                            resultSet.getInt("available"),
                            resultSet.getInt("capacity"),
                            resultSet.getString("numbers_of_beds")
                    );

                    roomTypes.add(room);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roomTypes;
    }
    public static ArrayList<RoomManager> getFetchRoomCapacity(int total){
        ArrayList<RoomManager> rooms=new ArrayList<>();
        RoomManager obj=null;
        String str = "SELECT * FROM room WHERE capacity <= ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,total);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new RoomManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("type"),rs.getInt("stock"),rs.getInt("available"),rs.getInt("capacity"),rs.getString("numbers_of_beds"));
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public static List<RoomManager> getFetchRoomHotelId(int hotelId) throws SQLException {
        List<RoomManager> rooms = new ArrayList<>();
        RoomManager obj = null;
        String query = "SELECT * FROM rooms WHERE hotel_id = ?";

        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(query)) {
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                obj = new RoomManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("type"),rs.getInt("stock"),rs.getInt("available"),rs.getInt("capacity"),rs.getString("numbers_of_beds"));
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rooms;
    }






}
