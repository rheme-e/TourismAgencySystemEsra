package com.TourismAgencySystem.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public  static int screenCenterPoint(String eksen , Dimension size){
        int point;
        switch (eksen){
            case "x":
                point=(Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point=(Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point=0;
        }
        return point;
    }

    public static void showMessage(String str){
        optionPaneTR();
        String msg ;
        String title ;
        switch (str) {
            case  "fill" :
                msg = "Tüm alanları doldurunuz!";
                title = "Hata!";
                break;
            case "success" :
                msg = "İşlem başarı ile gerçekleşti!";
                title = "Mesaj";
                break;
            case "error" :
                msg = "İşlem gerçekleşemedi";
                title = "Hata";
                break;
            default:
                msg = str;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean confirm(String str){
        optionPaneTR();
        String msg ;
        switch (str) {
            case "sure" :
                msg = "Bu işlemi yapmaya emin misiniz?";
                break;
            default:
                msg = str;

        }
        return JOptionPane.showConfirmDialog(null,msg,"",JOptionPane.YES_NO_OPTION) == 0;
    }



    public static boolean isFieldEmpty(JTextField field){
        return field.getText().isEmpty();
    }

    public static void optionPaneTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");

    }
    public static String cleanCourseName(String inputName) {
        return inputName.trim().replaceAll("\\s", "");
    }
}
