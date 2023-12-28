package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.Config;
import com.TourismAgencySystem.Helper.Helper;
import com.TourismAgencySystem.Helper.PhoneNumberValidator;
import com.TourismAgencySystem.Model.HotelManager;
import com.TourismAgencySystem.Model.ReservationManager;
import com.TourismAgencySystem.Model.UserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationGUI extends JFrame {
    private JPanel wrapper;
    private JButton button_ok;
    private JTextField field_name;
    private JTextField field_id;
    private JTextField field_telephone;
    private JLabel label_roomType;
    private JLabel label_roomServise;
    private JLabel label_hotelServise;
    private int id;
    private int dayE;
    private int yearE;

    private int dayS;
    private int yearS;
    private String monthE;
    private String monthS;
    private int selectedID;
    private int totalDay;

    public ReservationGUI(int selectedID, int id){
        this.id=id;
        this.selectedID=selectedID;
        add(wrapper);
        setSize(1200, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        String hotelType= HotelManager.getFetch(id).getTypes();
        String roomType = UserManager.getFetchList(selectedID).getRoomType();

        if(hotelType.equals("Her Şey Dahil")){
            label_hotelServise.setText("Oda Kahvaltı,\n" +
                    "Ücretsiz Otopark,\n" +
                    "Ücretsiz WiFi,\n" +
                    "Yüzme Havuzu,\n" +
                    "Fitness Center,\n" +
                    "Hotel Concierge,\n" +
                    "SPA,\n" +
                    "7/24 Oda Servisi");
        }else if(hotelType.equals("Tam Pansiyon")){
            label_hotelServise.setText("Oda Kahvaltı,\n" +
                    "Ücretsiz Otopark,\n" +
                    "Ücretsiz WiFi,\n" +
                    "Fitness Center,\n" +
                    "7/24 Oda Servisi");
        }else{
            label_hotelServise.setText("Oda Kahvaltı,\n" +
                    "Ücretsiz Otopark,\n" +
                    "Ücretsiz WiFi\n");
        }


        if(roomType.equals("Suit")){
            label_roomServise.setText("Televizyon (Var),\n" +
                    "Minibar (Var),\n" +
                    "Oyun Konsolu (Var),\n" +
                    "Metrekare ( 40 m2),\n" +
                    "Kasa (Var),\n" +
                    "Projeksiyon (Var)");
        }else if(roomType.equals("Double")){
            label_roomServise.setText("Televizyon (Var),\n" +
                    "Minibar (Var),\n" +
                    "Metrekare ( 30 m2 )");
        }else{
            label_roomServise.setText("Televizyon (Var)\n" +
                    "Minibar (Var)\n" +
                    "Metrekare ( 20 m2)");
        }
        label_roomType.setText(roomType);
        button_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=field_name.getText();
                String idNo=field_id.getText();
                String telephone=field_telephone.getText();
                if(field_telephone.getText().equals(null) || field_name.getText().equals(null) || field_id.getText().equals(null)){
                    Helper.showMessage("fill");
                }else{
                    if (!PhoneNumberValidator.validate(field_telephone.getText())) {
                        Helper.showMessage("Geçersiz telefon numarası!");
                    }else if (!PhoneNumberValidator.validate(field_id.getText())) {
                        Helper.showMessage("Geçersiz Tc kimlik numarası!");
                    }else if(ReservationManager.add(name,idNo,telephone)){
                        Helper.showMessage("Rezervasyon başarı ile oluşturuldu.");
                        dispose();
                    }else{
                        Helper.showMessage("error");
                        dispose();
                    }
                }

            }
        });
    }
}
