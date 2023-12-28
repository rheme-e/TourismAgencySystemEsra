package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.*;
import com.TourismAgencySystem.Model.HotelManager;
import com.TourismAgencySystem.Model.RoomManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UpdateRoomManagerGUI extends JFrame {
    private JPanel wrapper;
    private JComboBox comboBox_updateHotelName;
    private JComboBox comboBox_updateRoomType;
    private JTextField field_stock;
    private JTextField field_available;
    private JTextField field_capacity;
    private JComboBox comboBox_numbersOfBeds;
    private JButton button_UptadeRoom;
    private RoomManager id;

    UpdateRoomManagerGUI(RoomManager id) {
        this.id = id;
        add(wrapper);
        setSize(1200, 1200);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        loadComboHotelName();
        loadComboRoomTypes();
        loadComboNumbersOfBeds();

        button_UptadeRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (field_capacity.getText().isEmpty() || field_stock.getText().isEmpty() ||
                        field_available.getText().isEmpty()) {
                    Helper.showMessage("fill");
                } else {
                    Item hotelNameItem = (Item) comboBox_updateHotelName.getSelectedItem();
                    Item typeItem = (Item) comboBox_updateRoomType.getSelectedItem();
                    Item bedItem = (Item) comboBox_numbersOfBeds.getSelectedItem();
                    int stock = Integer.parseInt(field_stock.getText());
                    int available = Integer.parseInt(field_available.getText());
                    int capacity = Integer.parseInt(field_capacity.getText());
                    int ID = HotelManager.getFetch(hotelNameItem.getValue()).getId();
                    if (RoomManager.update(id.getId(), ID , typeItem.getValue(), stock, available, capacity, bedItem.getValue())) {
                        Helper.showMessage("success");
                        dispose();
                    }else{
                        Helper.showMessage("error");
                    }
                }
            }
        });
    }

    public void loadComboRoomTypes() {
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Single");
        typelist.add("Double");
        typelist.add("Suit");
        int i = 0;


        for (String types : typelist) {
            comboBox_updateRoomType.addItem(new Item(i++, types));
        }
    }

    public void loadComboNumbersOfBeds() {
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("1 Yataklı");
        typelist.add("2 Yataklı");

        int i = 0;


        for (String types : typelist) {
            comboBox_numbersOfBeds.addItem(new Item(i++, types));
        }
    }

    public void loadComboHotelName() {
        List<String> hotelNames = HotelManager.getAllHotelNames();
        int i = 0;
        for (String name : hotelNames) {
            comboBox_updateHotelName.addItem(new Item(i++, name));
        }
    }
}
