package com.TourismAgencySystem.Model;

import com.TourismAgencySystem.Helper.DBConnector;
import com.TourismAgencySystem.View.HotelManagerGUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PriceManager {
    private int id;
    private int hotelId;
    private String roomType;
    private String periodType;
    private int adultPrice;
    private int kidPrice;
    private HotelManager hotelName;

    public PriceManager(int id, int hotelId, String roomType, String periodType, int adultPrice, int kidPrice) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.periodType = periodType;
        this.adultPrice = adultPrice;
        this.kidPrice = kidPrice;
        this.hotelName= HotelManager.getFetch(hotelId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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

    public int getKidPrice() {
        return kidPrice;
    }

    public void setKidPrice(int kidPrice) {
        this.kidPrice = kidPrice;
    }

    public HotelManager getHotelName() {
        return hotelName;
    }

    public void setHotelName(HotelManager hotelName) {
        this.hotelName = hotelName;
    }

    public static ArrayList<PriceManager> getlist(){
        ArrayList<PriceManager> prices=new ArrayList<>();
        PriceManager obj;
        String str = "SELECT * FROM prices";
        try(Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(str)) {
            while(rs.next()){
                obj = new PriceManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("room_type"),rs.getString("period_type"),rs.getInt("adult_price"),rs.getInt("kid_price"));
                prices.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }
    public static boolean add(int hotelId, String roomType, String periodType, int adultPrice, int kidPrice){
        String sql = "INSERT INTO prices (hotel_id, room_type, period_type, adult_price, kid_price) VALUES (?, ?, ?, ?, ?);";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, roomType);
            preparedStatement.setString(3, periodType);
            preparedStatement.setInt(4, adultPrice);
            preparedStatement.setInt(5, kidPrice);
            int result = preparedStatement.executeUpdate();

            return result >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean control(int hotelId, String roomType, String periodType){
        String sql = "SELECT * FROM prices WHERE hotel_id = ? AND room_type = ? AND period_type = ? ";
        try(PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql)){
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, roomType);
            preparedStatement.setString(3, periodType);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean update(int id , int hotelId, String roomType, String periodType, int adultPrice, int kidPrice) {
        String str = "UPDATE prices SET hotel_id = ?, room_type = ?, period_type = ?, adult_price = ?, kid_price = ?  WHERE id = ?";
        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1, hotelId);
            pr.setString(2, roomType);
            pr.setString(3, periodType);
            pr.setInt(4, adultPrice);
            pr.setInt(5, kidPrice);
            pr.setInt(6, id);
            int result = pr.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean delete(int id){
        String str = "DELETE FROM prices WHERE id = ?";
        try(PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)){
            pr.setInt(1,id);
            int result = pr.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static PriceManager getFetch(int id){
        ArrayList<PriceManager> prices=new ArrayList<>();
        PriceManager obj=null;
        String str = "SELECT * FROM prices WHERE id = ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new PriceManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("room_type"),rs.getString("period_type"),rs.getInt("adult_price"),rs.getInt("kid_price"));
                prices.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static PriceManager getFetchHotelId(int id){
        ArrayList<PriceManager> prices=new ArrayList<>();
        PriceManager obj=null;
        String str = "SELECT * FROM prices WHERE hotel_id = ?";
        try(PreparedStatement pr= DBConnector.getInstance().prepareStatement(str)) {
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                obj = new PriceManager(rs.getInt("id"),rs.getInt("hotel_id"),rs.getString("room_type"),rs.getString("period_type"),rs.getInt("adult_price"),rs.getInt("kid_price"));
                prices.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static ArrayList<PriceManager> getFetchPeriod(String periodType) {
        ArrayList<PriceManager> prices = new ArrayList<>();
        PriceManager obj = null;
        String str = "SELECT * FROM prices WHERE period_type = ?";
        try (PreparedStatement pr = DBConnector.getInstance().prepareStatement(str)) {
            pr.setString(1, periodType);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                obj = new PriceManager(rs.getInt("id"), rs.getInt("hotel_id"), rs.getString("room_type"), rs.getString("period_type"), rs.getInt("adult_price"), rs.getInt("kid_price"));
                prices.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }

}
