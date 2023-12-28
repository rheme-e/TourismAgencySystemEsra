package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.Config;
import com.TourismAgencySystem.Helper.Helper;
import com.TourismAgencySystem.Model.AdminManager;
import com.TourismAgencySystem.Model.HotelManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JTextField field_userName;
    private JTextField field_password;
    private JButton button_login;
    private JButton button_conti;
   public LoginGUI(){
        add(wrapper);
        setSize(1200, 1200);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
       button_login.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String username = field_userName.getText();
               String password = field_password.getText();
               if(field_userName.getText().equals(null) ||field_password.getText().equals(null)){
                   Helper.showMessage("fill");
               }else{
                   if(AdminManager.getFetch(username,password)){
                       Helper.showMessage("Başarı ile giriş yapıldı");
                       try {
                           HotelManagerGUI hotelManagerGUI = new HotelManagerGUI();
                       } catch (SQLException ex) {
                           throw new RuntimeException(ex);
                       }
                   }else{
                       Helper.showMessage("error");
                   }
               }
           }
       });
       button_conti.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   UserManagerGUI userManagerGUI = new UserManagerGUI();
               } catch (SQLException ex) {
                   throw new RuntimeException(ex);
               }
           }
       });
   }



}
