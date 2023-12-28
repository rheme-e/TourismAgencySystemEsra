package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.Cities;
import com.TourismAgencySystem.Helper.Config;
import com.TourismAgencySystem.Helper.Helper;
import com.TourismAgencySystem.Helper.Item;
import com.TourismAgencySystem.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.Collator;
import java.util.*;

public class UserManagerGUI extends JFrame {
    private JPanel wrapper;
    private JTable table_hotels;
    private JComboBox comboBox_showId;
    private JButton button_show;
    private JComboBox comboBox_City;
    private JComboBox comboBox_Zone;
    private JComboBox comboBox_Adult;

    private JButton button_search;
    private JComboBox comboBox_dayS;
    private JComboBox comboBox_yearS;
    private JComboBox comboBox_monthS;
    private JComboBox comboBox_dayE;
    private JComboBox comboBox_yearE;
    private JComboBox comboBox_monthE;
    private DefaultTableModel model_userManager;
    private Object[] row_userManager;
    private int totalDay;
    private static final String[] MONTHS = {"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran",
            "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"};

    public UserManagerGUI() throws SQLException {
        add(wrapper);
        setSize(1200, 1200);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        loadComboCities();
        loadComboAdult();
        loadComboMonthS();
        loadComboYearS();
        loadComboYearE();
        model_userManager = new DefaultTableModel();
        Object[] col_userManager = {"ID", "Otel Adı", "Oda Tipi", "Fiyat(1 Gece)", "Kişi", "İl", "Dönem"};
        model_userManager.setColumnIdentifiers(col_userManager);
        row_userManager = new Object[col_userManager.length];
        table_hotels.setModel(model_userManager);
        table_hotels.getTableHeader().setReorderingAllowed(false);
        loadUserModel();



        comboBox_City.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadComboZones();
            }
        });
        comboBox_monthS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCombodayS();
                loadComboMonthE();
            }
        });


        comboBox_monthE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCombodayE();
            }
        });


        button_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected_city = comboBox_City.getSelectedItem().toString();
                String selected_zone = comboBox_Zone.getSelectedItem().toString();
                String selected_adult = comboBox_Adult.getSelectedItem().toString();
                String selected_monthE = comboBox_monthE.getSelectedItem().toString();
                String selected_monthS = comboBox_monthS.getSelectedItem().toString();
                int total = Integer.parseInt(selected_adult);

                String periods = period(selected_monthE, selected_monthS);

                try {
                    if (selected_city.equals("Tüm İller")) {
                        System.out.println("Seçilen capacity" + total);
                        String query = UserManager.searchQueryAllCity(periods, total);
                        loadUserModel(UserManager.searchUserListAllCity(query, periods, total));
                    } else {
                        String query = UserManager.searchQuery(selected_city, selected_zone, periods, total);
                        loadUserModel(UserManager.searchUserList(query, selected_city, selected_zone, periods, total));
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        button_show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedID = Integer.parseInt(((Item) comboBox_showId.getSelectedItem()).getValue());
                int hotelID = UserManager.getFetchList(selectedID).getHotelID();
                String selected_dayE = comboBox_dayE.getSelectedItem().toString();
                String selected_monthE = comboBox_monthE.getSelectedItem().toString();
                String selected_yearE = comboBox_yearE.getSelectedItem().toString();
                String selected_dayS = comboBox_dayS.getSelectedItem().toString();
                String selected_monthS = comboBox_monthS.getSelectedItem().toString();
                String selected_yearS = comboBox_yearS.getSelectedItem().toString();
                int dayE = Integer.parseInt(selected_dayE);
                int dayS = Integer.parseInt(selected_dayS);
                int yearE = Integer.parseInt(selected_yearE);
                int yearS = Integer.parseInt(selected_yearS);
                if(selected_monthE.equals(selected_monthS)){
                    totalDay = dayE-dayS;
                }else{
                    Item selectedItemMonthE = (Item) comboBox_monthE.getSelectedItem();
                    int monthE = selectedItemMonthE.getKey();
                    totalDay = monthE*30+dayE-dayS;
                }
                UserGUI userGUI = new UserGUI(selectedID,hotelID, dayE, dayS, yearE, yearS, selected_monthE , selected_monthS , totalDay);
            }
        });

    }

    public void loadComboCities() {
        Map<String, List<String>> cityMap = Cities.CityMap();
        comboBox_City.removeAllItems();
        comboBox_City.addItem(new Item(0, "Tüm İller"));

        Collator collator = Collator.getInstance(new Locale("tr", "TR"));

        List<String> sortedCities = new ArrayList<>(cityMap.keySet());
        sortedCities.sort(collator::compare);

        int k = 0;
        for (String city : sortedCities) {
            comboBox_City.addItem(new Item(k++, city));
        }
    }

    public void loadComboZones() {
        String selectedCity = ((Item) comboBox_City.getSelectedItem()).getValue();

        if (selectedCity != null) {
            Map<String, List<String>> cityMap = Cities.CityMap();
            List<String> zones = cityMap.get(selectedCity);

            comboBox_Zone.removeAllItems();
            comboBox_Zone.addItem("Tüm İlçeler");
            int k = 0;
            if (zones != null) {
                for (String zone : zones) {
                    comboBox_Zone.addItem(new Item(k++, zone));
                }
            }
        }
    }

    public void loadComboAdult() {
        ArrayList<String> starlist = new ArrayList<>();
        starlist.add("1");
        starlist.add("2");
        starlist.add("3");
        starlist.add("4");
        starlist.add("5");
        starlist.add("6");
        starlist.add("7");
        starlist.add("8");
        starlist.add("9");
        starlist.add("10");
        int i = 0;

        for (String stars : starlist) {
            comboBox_Adult.addItem(new Item(i++, stars));
        }
    }
    public void loadComboMonthS() {
        comboBox_monthS.removeAllItems();
        ArrayList<String> starlist = new ArrayList<>();
        starlist.add("Ocak");
        starlist.add("Şubat");
        starlist.add("Mart");
        starlist.add("Nisan");
        starlist.add("Mayıs");
        starlist.add("Haziran");
        starlist.add("Temmuz");
        starlist.add("Ağustos");
        starlist.add("Eylül");
        starlist.add("Ekim");
        starlist.add("Kasım");
        starlist.add("Aralık");

        int i = 0;

        for (String stars : starlist) {
            comboBox_monthS.addItem(new Item(i++, stars));
        }
    }
    public void loadComboMonthE() {
        comboBox_monthE.removeAllItems();
        String selectedMonthS = comboBox_monthS.getSelectedItem().toString();
        List<String> monthsToShow = new ArrayList<>();

        for (String month : MONTHS) {
            if (monthsToShow.size() > 0 || month.equals(selectedMonthS)) {
                monthsToShow.add(month);
            }
        }
        int i = 0;
        for (String month : monthsToShow) {
            comboBox_monthE.addItem(new Item(i++,month));
        }
    }


    public void loadCombodayE() {
        comboBox_dayE.removeAllItems();
        String selectedMonthS = comboBox_monthS.getSelectedItem().toString();
        String selectedYearS = comboBox_yearS.getSelectedItem().toString();
        String selectedDayS = comboBox_dayS.getSelectedItem().toString();

        String selectedMonthE = comboBox_monthE.getSelectedItem().toString();
        String selectedYearE = comboBox_yearE.getSelectedItem().toString();

        if (selectedYearE.equals(selectedYearS) && selectedMonthE.equals(selectedMonthS)) {
            int startDayIndex = Integer.parseInt(selectedDayS);

            for (int i = startDayIndex + 1 ; i <= 31; i++) {
                comboBox_dayE.addItem(new Item(i, String.valueOf(i)));
            }
        } else {
            // Yıl veya ay farklıysa, tüm günleri ekleyin
            for (int i = 1; i <= 31; i++) {
                comboBox_dayE.addItem(new Item(i, String.valueOf(i)));
            }
        }

        // Null check before using selectedItem
        if (comboBox_dayE.getSelectedItem() != null) {
            String selectedDayE = comboBox_dayE.getSelectedItem().toString();
            // Rest of your code...
        } else {
            // Handle the case where no item is selected, for example, by setting a default value.
        }
    }








    public void loadComboYearS() {
        comboBox_yearS.removeAllItems();
        ArrayList<String> starlist = new ArrayList<>();
        starlist.add("2023");
        int i = 0;

        for (String stars : starlist) {
            comboBox_yearS.addItem(new Item(i++, stars));
        }
    }
    public void loadComboYearE() {
        comboBox_yearE.removeAllItems();
        ArrayList<String> starlist = new ArrayList<>();
        starlist.add("2023");
        int i = 0;

        for (String stars : starlist) {
            comboBox_yearE.addItem(new Item(i++, stars));
        }
    }
    public void loadCombodayS() {
        String selectedMonth = comboBox_monthS.getSelectedItem().toString();
        String selectedYear = comboBox_yearS.getSelectedItem().toString();
        comboBox_dayS.removeAllItems();
        ArrayList<String> starlist = new ArrayList<>();

        if (selectedMonth.equals("Ocak") || selectedMonth.equals("Mart") || selectedMonth.equals("Mayıs") ||
                selectedMonth.equals("Temmuz") || selectedMonth.equals("Ağustos") || selectedMonth.equals("Ekim") ||
                selectedMonth.equals("Aralık")) {
            for (int i = 1; i <= 31; i++) {
                String dayS = String.valueOf(i);
                starlist.add(dayS);
            }
        } else if (selectedMonth.equals("Nisan") || selectedMonth.equals("Haziran") ||
                selectedMonth.equals("Eylül") || selectedMonth.equals("Kasım")) {
            for (int i = 1; i <= 30; i++) {
                String dayS = String.valueOf(i);
                starlist.add(dayS);
            }
        } else if (selectedMonth.equals("Şubat")) {
            int maxDays = 28;
            int selectedYearInt = Integer.parseInt(selectedYear);

            if ((selectedYearInt % 4 == 0 && selectedYearInt % 100 != 0) || selectedYearInt % 400 == 0) {
                maxDays = 29; // Artık yıl kontrolü
            }

            for (int i = 1; i <= maxDays; i++) {
                String dayS = String.valueOf(i);
                starlist.add(dayS);
            }
        }

        int k = 0;

        for (String stars : starlist) {
            comboBox_dayS.addItem(new Item(k++, stars));
        }
    }









    public void loadUserModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_hotels.getModel();
        clearModel.setRowCount(0);
        comboBox_showId.removeAllItems();
        int i = 0;
        for (UserManager bestList : UserManager.getlist()) {
            String hotelName = HotelManager.getFetch(bestList.getHotelID()).getHotelName();

            row_userManager[0] = bestList.getId();
            row_userManager[1] = hotelName;
            row_userManager[2] = bestList.getRoomType();
            row_userManager[3] = bestList.getAdultPrice();
            row_userManager[4] = bestList.getCapacity();
            row_userManager[5] = bestList.getCity();
            row_userManager[6] = bestList.getPeriodType();
            model_userManager.addRow(row_userManager);
            Item item = new Item(i++, String.valueOf(bestList.getId()));
            comboBox_showId.addItem(item);

        }

    }

    public void loadUserModel(ArrayList<UserManager> list) throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_hotels.getModel();
        clearModel.setRowCount(0);
        comboBox_showId.removeAllItems();
        int i = 0;
        for (UserManager bestList : list) {
            String hotelName = HotelManager.getFetch(bestList.getHotelID()).getHotelName();

            row_userManager[0] = bestList.getId();
            row_userManager[1] = hotelName;
            row_userManager[2] = bestList.getRoomType();
            row_userManager[3] = bestList.getAdultPrice();
            row_userManager[4] = bestList.getCapacity();
            row_userManager[5] = bestList.getCity();
            row_userManager[6] = bestList.getPeriodType();

            model_userManager.addRow(row_userManager);
            Item item = new Item(i++, String.valueOf(bestList.getId()));
            comboBox_showId.addItem(item);


        }
    }


    public String period(String month1, String month2) {
        String period = null;
        if (month1.equals("Nisan") || month1.equals("Mayıs") || month1.equals("Haziran") || month1.equals("Temmuz") || month1.equals("Ağustos") || month1.equals("Eylül")) {
            if (month2.equals("Nisan") || month2.equals("Mayıs") || month2.equals("Haziran") || month2.equals("Temmuz") || month2.equals("Ağustos") || month2.equals("Eylül")) {
                period = "Yaz";
            }
        } else {
            period = "Kış";
        }
        return period;
    }



}
