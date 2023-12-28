package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.*;
import com.TourismAgencySystem.Model.HotelManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UpdateHotelManagerGUI extends JFrame {

    private JPanel wrapper;
    private JTextField field_updateHotelName;
    private JComboBox comboBox_updateCity;
    private JComboBox comboBox_updateZone;
    private JTextField field_updateAddress;
    private JTextField field_updateEmail;
    private JTextField field_updateTelephone;
    private JComboBox comboBox_updateStars;
    private JComboBox comboBox_updateType;
    private JButton button_updateAdd;
    private HotelManager id;
    public UpdateHotelManagerGUI(HotelManager id){
        this.id=id;
        add(wrapper);
        setSize(1200, 1200);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        loadComboUpHotelTypes();
        loadComboUpStars();
        loadComboUpCities();
        loadComboUpZones();
        button_updateAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (field_updateHotelName.getText().isEmpty() || field_updateTelephone.getText().isEmpty() ||
                        field_updateAddress.getText().isEmpty() || field_updateEmail.getText().isEmpty()) {
                    Helper.showMessage("fill");
                } else {
                    Item cityItem = (Item) comboBox_updateCity.getSelectedItem();
                    Item zoneItem = (Item) comboBox_updateZone.getSelectedItem();
                    Item starItem = (Item) comboBox_updateStars.getSelectedItem();
                    Item typeItem = (Item) comboBox_updateType.getSelectedItem();

                    if (!EmailValidator.validate(field_updateEmail.getText())) {
                        Helper.showMessage("Geçersiz email!");
                    } else if (!PhoneNumberValidator.validate(field_updateTelephone.getText())) {
                        Helper.showMessage("Geçersiz telefon numarası!");
                    } else {
                        if (HotelManager.update(id.getId(), field_updateHotelName.getText(), cityItem.getValue(),
                                zoneItem.getValue(), field_updateAddress.getText(), field_updateEmail.getText(),
                                field_updateTelephone.getText(), starItem.getValue(), typeItem.getValue())) {
                            Helper.showMessage("success");
                            dispose();
                        } else {
                            Helper.showMessage("error");
                        }
                    }
                }
            }
        });

    }

    public String getUpdatedHotelName() {
        return field_updateHotelName.getText();
    }


    public void loadComboUpCities() {
        Map<String, List<String>> cityMap = Cities.CityMap();
        comboBox_updateCity.removeAllItems();

        List<String> sortedCities = new ArrayList<>(cityMap.keySet());
        Collections.sort(sortedCities);
        int k=0;
        for (String city : sortedCities) {
            comboBox_updateCity.addItem(new Item(k++, city));
        }

    }

    public void loadComboUpZones() {
        String selectedCity = ((Item) comboBox_updateCity.getSelectedItem()).getValue();

        if (selectedCity != null) {
            Map<String, List<String>> cityMap = Cities.CityMap();
            List<String> zones = cityMap.get(selectedCity);

            comboBox_updateZone.removeAllItems();
            int k=0;
            if (zones != null) {
                for (String zone : zones) {
                    comboBox_updateZone.addItem(new Item(k++,zone));
                }
            }
        }
    }


    public void loadComboUpStars(){
        ArrayList<String> starlist = new ArrayList<>();
        starlist.add("5 Yıldız");
        starlist.add("4 Yıldız");
        starlist.add("3 Yıldız");
        starlist.add("2 Yıldız");
        starlist.add("1 Yıldız");
        int i=0;

        for(String stars : starlist){
            comboBox_updateStars.addItem(new Item(i++,stars));
        }
    }
    public void loadComboUpHotelTypes(){
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Her Şey Dahil");
        typelist.add("Tam Pansiyon");
        typelist.add("Yarım Pansiyon");
        int i=0;


        for(String types : typelist){
            comboBox_updateType.addItem(new Item(i++,types));
        }
    }

}
