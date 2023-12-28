package com.TourismAgencySystem.Helper;

import javax.imageio.stream.ImageInputStreamImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQL {
    public static void createTableHotel(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE reservation (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    name VARCHAR(255),\n" +
                "    id_no VARCHAR(255),\n" +
                "    telephone VARCHAR(255)\n" +
                ");\n";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
        }
    }





}
