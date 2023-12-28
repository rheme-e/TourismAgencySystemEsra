package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.Config;
import com.TourismAgencySystem.Helper.Helper;
import com.TourismAgencySystem.Helper.Item;
import com.TourismAgencySystem.Model.PriceManager;
import com.TourismAgencySystem.Model.RoomManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UpdatePriceManagerGUI extends JFrame{
    private JPanel wrapper;
    private JComboBox comboBox_updatePeriods;
    private JTextField field_updateAdultPrice;
    private JTextField field_updateKidsPrice;
    private JButton button_updatePrice;
    private JLabel label_UpdateHotelName;
    private JLabel label_UpdateRoomType;
    private PriceManager id;
    public UpdatePriceManagerGUI(PriceManager id){
        this.id=id;
        int ID = id.getId();
        add(wrapper);
        setSize(1200, 1200);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        label_UpdateHotelName.setText(PriceManager.getFetch(ID).getHotelName().getHotelName());
        label_UpdateRoomType.setText(PriceManager.getFetch(ID).getRoomType());
        comboBox_updatePeriods.removeAllItems();
        loadComboUpdatePeriods();



        button_updatePrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(field_updateAdultPrice.getText().isEmpty() || field_updateKidsPrice.getText().isEmpty()){
                    Helper.showMessage("fill");
                }else{
                    int hotelID = PriceManager.getFetch(ID).getHotelId();
                    String roomType = PriceManager.getFetch(ID).getRoomType();
                    Item periodType = (Item) comboBox_updatePeriods.getSelectedItem();
                    int adultPrice = Integer.parseInt(field_updateAdultPrice.getText());
                    int kidsPrice = Integer.parseInt(field_updateKidsPrice.getText());
                    if(PriceManager.update(id.getId(),hotelID,roomType,periodType.getValue(),adultPrice,kidsPrice)){
                        Helper.showMessage("success");
                        dispose();
                    }
                }
            }
        });


    }
    public void loadComboUpdatePeriods(){
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Yaz");
        typelist.add("Kış");
        int i = 0;
        for (String types : typelist) {
            comboBox_updatePeriods.addItem(new Item(i++, types));
        }
    }
}
