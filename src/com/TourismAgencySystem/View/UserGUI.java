package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.Config;
import com.TourismAgencySystem.Helper.Helper;
import com.TourismAgencySystem.Model.HotelManager;
import com.TourismAgencySystem.Model.RoomManager;
import com.TourismAgencySystem.Model.UserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGUI extends JFrame{
    private JPanel wrapper;
    private JButton button_rez;
    private JLabel label_hotelName;
    private JPanel label_stars;
    private JLabel label_address;
    private JLabel label_telephone;
    private JLabel label_hotelservise;
    private JLabel label_roomservise;
    private JLabel label_startDate;
    private JLabel label_endDate;
    private JLabel label_totalDay;
    private JLabel label_price;
    private JLabel label_star;
    private int id;
    private int dayE;
    private int yearE;

    private int dayS;
    private int yearS;
    private String monthE;
    private String monthS;
    private int selectedID;
    private int totalDay;

    private RoomManager roomId;


    public UserGUI(int selectedID,int id,int dayE, int dayS , int yearE , int yearS, String monthE , String monthS , int totalDay){
        this.id=id;
        this.dayE=dayE;
        this.dayS=dayS;
        this.yearE=yearE;
        this.yearS=yearS;
        this.monthE=monthE;
        this.monthS=monthS;
        this.selectedID=selectedID;
        this.totalDay=totalDay;



        add(wrapper);
        setSize(1200, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        String hotelName= HotelManager.getFetch(id).getHotelName();
        String address= HotelManager.getFetch(id).getAddress();
        String telephone= HotelManager.getFetch(id).getTelephone();
        String hotelType= HotelManager.getFetch(id).getTypes();
        String stars= HotelManager.getFetch(id).getStars();
        String roomType = UserManager.getFetchList(selectedID).getRoomType();
        label_hotelName.setText(hotelName);
        label_address.setText(address);
        label_telephone.setText(telephone);
        if(hotelType.equals("Her Şey Dahil")){
            label_hotelservise.setText("Oda Kahvaltı,\n" +
                    "Ücretsiz Otopark,\n" +
                    "Ücretsiz WiFi,\n" +
                    "Yüzme Havuzu,\n" +
                    "Fitness Center,\n" +
                    "Hotel Concierge,\n" +
                    "SPA,\n" +
                    "7/24 Oda Servisi");
        }else if(hotelType.equals("Tam Pansiyon")){
            label_hotelservise.setText("Oda Kahvaltı,\n" +
                    "Ücretsiz Otopark,\n" +
                    "Ücretsiz WiFi,\n" +
                    "Fitness Center,\n" +
                    "7/24 Oda Servisi");
        }else{
            label_hotelservise.setText("Oda Kahvaltı,\n" +
                    "Ücretsiz Otopark,\n" +
                    "Ücretsiz WiFi\n");
        }

        label_star.setText(stars);
        if(roomType.equals("Suit")){
            label_roomservise.setText("Televizyon (Var),\n" +
                    "Minibar (Var),\n" +
                    "Oyun Konsolu (Var),\n" +
                    "Metrekare ( 40 m2),\n" +
                    "Kasa (Var),\n" +
                    "Projeksiyon (Var)");
        }else if(roomType.equals("Double")){
            label_roomservise.setText("Televizyon (Var),\n" +
                    "Minibar (Var),\n" +
                    "Metrekare ( 30 m2 )");
        }else{
            label_roomservise.setText("Televizyon (Var)\n" +
                    "Minibar (Var)\n" +
                    "Metrekare ( 20 m2)");
        }

        label_endDate.setText(dayE+"/"+monthS+"/"+yearE);
        label_startDate.setText(dayS+"/"+monthE+"/"+yearS);
        label_totalDay.setText(String.valueOf(totalDay+1)+" gün " + String.valueOf(totalDay) +" gece için fiyat:");
        int unitPrice = UserManager.getFetchList(selectedID).getAdultPrice();
        int price = unitPrice*(totalDay+1);
        label_price.setText(String.valueOf(price));


        button_rez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationGUI reservationGUI = new ReservationGUI(selectedID,id);
                dispose();
            }
        });
    }




}
