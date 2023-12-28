package com.TourismAgencySystem.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodManager {
    private int id;
    private String startDate;
    private String endDate;

    public PeriodManager(int id, String startDate, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static void addPeriod(Connection connection, String startDate, String endDate) {
        String sql = "INSERT INTO periods (start_date, end_date) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date parsedStartDate = sdf.parse(startDate);
            Date parsedEndDate = sdf.parse(endDate);

            preparedStatement.setDate(1, new java.sql.Date(parsedStartDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(parsedEndDate.getTime()));

            preparedStatement.executeUpdate();
            System.out.println("Dönem başarıyla eklendi.");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
