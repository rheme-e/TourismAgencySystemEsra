package com.TourismAgencySystem.View;

import com.TourismAgencySystem.Helper.*;
import com.TourismAgencySystem.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.Collator;
import java.util.*;
import java.util.List;

public class HotelManagerGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTable table_hotelManager;
    private JTextField field_hotelName;
    private JComboBox comboBox_city;
    private JComboBox comboBox_zone;
    private JTextField field_address;
    private JTextField field_email;
    private JTextField field_telephone;
    private JComboBox comboBox_type;
    private JButton button_add;
    private JComboBox comboBox_stars;
    private JTable table_rooms;
    private JComboBox comboBox_roomType;
    private JTextField field_stock;
    private JTextField field_available;
    private JTextField field_capacity;
    private JComboBox comboBox_beds;
    private JButton button_addRoom;
    private JComboBox comboBox_hotelsName;
    private JButton button_logout;
    private JTable table_admin;
    private JTextField field_name;
    private JTextField field_userName;
    private JTextField field_password;
    private JTextField field_password2;
    private JButton button_addAdmin;
    private JTable table_prices;
    private JComboBox comboBox_hotelName;
    private JComboBox comboBox_roomTypess;
    private JComboBox comboBox_periodType;
    private JTextField field_adultPrice;
    private JTextField field_kidPrice;
    private JButton button_addPrice;
    private JTable table_customers;
    private DefaultTableModel model_hotelManager;
    private Object[] row_hotelManager;
    private DefaultTableModel model_roomManager;
    private Object[] row_roomManager;
    private JPopupMenu hotelMenu;

    private JPopupMenu roomMenu;
    private DefaultTableModel model_adminManager;
    private Object[] row_adminManager;
    private DefaultTableModel model_priceManager;
    private Object[] row_priceManager;
    private JPopupMenu priceMenu;
    private DefaultTableModel model_customManager;
    private Object[] row_customManager;




    public HotelManagerGUI() throws SQLException {
        add(wrapper);
        setSize(1200, 1200);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        model_hotelManager = new DefaultTableModel();
        Object[] col_hotelManager = {"ID", "Otel Adı", "İl", "İlçe", "Adres", "E-mail", "Telefon", "Yıldız", "Tesis Tipi"};
        model_hotelManager.setColumnIdentifiers(col_hotelManager);
        row_hotelManager = new Object[col_hotelManager.length];
        table_hotelManager.setModel(model_hotelManager);
        table_hotelManager.getTableHeader().setReorderingAllowed(false);
        loadComboCities();
        loadComboStars();
        loadComboHotelTypes();
        loadComboRoomTypes();
        loadComboNumbersOfBeds();
        loadComboPeriodTypes();
        loadHotelManager();
        loadRoomManager();


        comboBox_city.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadComboZones();
            }
        });

        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (field_hotelName.getText().isEmpty() || field_telephone.getText().isEmpty() || field_address.getText().isEmpty() || field_email.getText().isEmpty()) {
                    Helper.showMessage("fill");
                } else {
                    Item cityItem = (Item) comboBox_city.getSelectedItem();
                    Item zoneItem = (Item) comboBox_zone.getSelectedItem();
                    Item starItem = (Item) comboBox_stars.getSelectedItem();
                    Item typeItem = (Item) comboBox_type.getSelectedItem();
                    try {
                        if (HotelManager.getFetchEmail(field_email.getText()) || HotelManager.getFetchTelephone(field_telephone.getText()) || HotelManager.getFetchHotelName(field_hotelName.getText())) {
                            Helper.showMessage("Bu Otel Kayıtlıdır. Farklı bir telefon numarası ya da mail girdiğinizden emin olun!");
                        }
                        if (!EmailValidator.validate(field_email.getText())) {
                            Helper.showMessage("Geçersiz email!");
                        } else if (!PhoneNumberValidator.validate(field_telephone.getText())) {
                            Helper.showMessage("Geçersiz telefon numarası!");
                        } else if (HotelManager.add(field_hotelName.getText(), cityItem.getValue(), zoneItem.getValue(), field_address.getText(), field_email.getText(), field_telephone.getText(), starItem.getValue(), typeItem.getValue())) {
                            Helper.showMessage("success");
                            loadHotelManager();
                            loadComboHotelsName();
                            field_hotelName.setText(null);
                            field_address.setText(null);
                            field_telephone.setText(null);
                            field_email.setText(null);
                        } else {
                            Helper.showMessage("error");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        hotelMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        hotelMenu.add(updateMenu);
        hotelMenu.add(deleteMenu);


        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selected_id = Integer.parseInt(table_hotelManager.getValueAt(table_hotelManager.getSelectedRow(), 0).toString());
                HotelManager.delete(selected_id);
                try {
                    loadHotelManager();
                    loadComboHotelsName();
                    RoomManager.deleteHotel(selected_id);
                    loadRoomManager();
                    loadComboHotelName();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }

        });
        updateMenu.addActionListener(e -> {
            int selected_id = Integer.parseInt(table_hotelManager.getValueAt(table_hotelManager.getSelectedRow(), 0).toString());
            UpdateHotelManagerGUI updateGUI = new UpdateHotelManagerGUI(HotelManager.getFetch(selected_id));
            HotelManager updatedHotel = HotelManager.getFetch(selected_id);

            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        if (!updatedHotel.getHotelName().equals(updateGUI.getUpdatedHotelName())) {
                            // Otel adı değişti, diğer paneldeki JComboBox'ları güncelle
                            updateComboBoxes(updatedHotel.getHotelName(), updateGUI.getUpdatedHotelName());
                        }
                        loadHotelManager();
                        loadComboHotelsName();
                        loadRoomManager();
                        loadComboHotelName();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            });
        });

        table_hotelManager.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = table_hotelManager.rowAtPoint(point);
                table_hotelManager.setRowSelectionInterval(selectedRow, selectedRow);

                if (SwingUtilities.isRightMouseButton(e)) {
                    hotelMenu.show(table_hotelManager, e.getX(), e.getY());
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        model_roomManager = new DefaultTableModel();
        Object[] col_roomManager = {"ID", "Otel Adı", "Oda Tipi", "Toplam Oda Sayısı", "Müsait Oda Sayısı", "Kişi Sayısı", "Yatak Sayısı"};
        model_roomManager.setColumnIdentifiers(col_roomManager);
        row_roomManager = new Object[col_roomManager.length];
        table_rooms.setModel(model_roomManager);
        table_rooms.getTableHeader().setReorderingAllowed(false);
        loadComboHotelsName();
        loadRoomManager();


        button_addRoom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Item roomTypeItem = (Item) comboBox_roomType.getSelectedItem();
                Item bedItem = (Item) comboBox_beds.getSelectedItem();
                Item hotelNameItem = (Item) comboBox_hotelsName.getSelectedItem();
                int ID = HotelManager.getFetch(hotelNameItem.getValue()).getId();

                int stock = Integer.parseInt(field_stock.getText());
                int available = Integer.parseInt(field_available.getText());
                int capacity = Integer.parseInt(field_capacity.getText());
                if (field_stock.getText().isEmpty() || field_available.getText().isEmpty() || field_capacity.getText().isEmpty()) {
                    Helper.showMessage("fill");
                }else if (RoomManager.add(ID, roomTypeItem.getValue(), stock, available, capacity, bedItem.getValue())) {
                    Helper.showMessage("success");
                    try {
                        loadRoomManager();
                        loadComboHotelName();
                        loadPriceManager();
                        field_stock.setText(null);
                        field_available.setText(null);
                        field_capacity.setText(null);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    Helper.showMessage("error");
                }


            }
        });
        roomMenu = new JPopupMenu();
        JMenuItem updateRoom = new JMenuItem("Güncelle");
        JMenuItem deleteRoom = new JMenuItem("Sil");
        roomMenu.add(updateRoom);
        roomMenu.add(deleteRoom);

        deleteRoom.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selected_id = Integer.parseInt(table_rooms.getValueAt(table_rooms.getSelectedRow(), 0).toString());
                RoomManager.delete(selected_id);
                try {
                    loadRoomManager();
                    loadComboHotelName();
                    loadPriceManager();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }


        });
        updateRoom.addActionListener(e -> {
            int selected_id = Integer.parseInt(table_rooms.getValueAt(table_rooms.getSelectedRow(), 0).toString());
            UpdateRoomManagerGUI updateRoomManagerGUI = new UpdateRoomManagerGUI(RoomManager.getFetch(selected_id));

            updateRoomManagerGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        loadRoomManager();
                        loadComboHotelName();
                        loadPriceManager();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            });

        });
        table_rooms.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = table_rooms.rowAtPoint(point);
                table_rooms.setRowSelectionInterval(selectedRow, selectedRow);

                if (SwingUtilities.isRightMouseButton(e)) {
                    roomMenu.show(table_rooms, e.getX(), e.getY());
                }
            }
        });


        button_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        model_adminManager= new DefaultTableModel();
        Object[] col_adminManager = {"ID", "Adı Soyadı", "Kullanıcı Adı", "Şifre"};
        model_adminManager.setColumnIdentifiers(col_adminManager);
        row_adminManager = new Object[col_adminManager.length];
        table_admin.setModel(model_adminManager);
        table_admin.getTableHeader().setReorderingAllowed(false);
        loadAdminManager();

        button_addAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(field_name.getText().isEmpty() || field_userName.getText().isEmpty() || field_password2.getText().isEmpty() || field_password.getText().isEmpty()){
                    Helper.showMessage("fill");
                }else{
                    if(AdminManager.add(field_name.getText(),field_userName.getText(),field_password.getText(),field_password2.getText())){
                        Helper.showMessage("success");
                        try {
                            loadAdminManager();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }else{
                        Helper.showMessage("error");
                    }
                }
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        model_priceManager= new DefaultTableModel();
        Object[] col_priceManager = {"ID", "Otel Adı", " Oda Tipi", "Dönem", "Yetişkin Fiyat", "Çocuk Fiyat"};
        model_priceManager.setColumnIdentifiers(col_priceManager);
        row_priceManager = new Object[col_priceManager.length];
        table_prices.setModel(model_priceManager);
        table_prices.getTableHeader().setReorderingAllowed(false);
        loadPriceManager();
        loadComboHotelName();




        button_addPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item hotelNameItem = (Item) comboBox_hotelName.getSelectedItem();
                Item roomTypeItem = (Item) comboBox_roomTypess.getSelectedItem();
                Item periodItem = (Item) comboBox_periodType.getSelectedItem();
                int adultPrice = Integer.parseInt(field_adultPrice.getText());
                int kidPrice = Integer.parseInt(field_kidPrice.getText());

                int ID = HotelManager.getFetch(hotelNameItem.getValue()).getId();
                int roomId = RoomManager.getFetchRoomID(HotelManager.getFetch(hotelNameItem.getValue()).getId()).getId();

                if(field_adultPrice.getText().isEmpty() || field_kidPrice.getText().isEmpty()){
                    Helper.showMessage("fill");
                }else if(!PriceManager.control(ID,roomTypeItem.getValue(),periodItem.getValue())){
                    if(PriceManager.add(ID,roomTypeItem.getValue(),periodItem.getValue(),adultPrice,kidPrice)){
                        Helper.showMessage("success");
                        try {
                            loadPriceManager();
                            UserManager.UpdateTable();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }else{
                    Helper.showMessage("Bu odaya fiyat belirlenmiştir!");
                }
            }
        });


        comboBox_hotelName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            loadComboPriceRoomTypes();
            }
        });

        priceMenu = new JPopupMenu();
        JMenuItem updatePriceMenu = new JMenuItem("Güncelle");
        JMenuItem deletePriceMenu = new JMenuItem("Sil");
        priceMenu.add(updatePriceMenu);
        priceMenu.add(deletePriceMenu);


        deletePriceMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selected_id = Integer.parseInt(table_prices.getValueAt(table_prices.getSelectedRow(), 0).toString());
                PriceManager.delete(selected_id);

                try {
                    loadPriceManager();
                    UserManager.UpdateTable();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }


        });
        updatePriceMenu.addActionListener(e -> {
            int selected_id = Integer.parseInt(table_prices.getValueAt(table_prices.getSelectedRow(), 0).toString());
            UpdatePriceManagerGUI updatePriceManagerGUI = new UpdatePriceManagerGUI(PriceManager.getFetch(selected_id));

            updatePriceManagerGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        loadPriceManager();
                        UserManager.UpdateTable();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            });

        });
        table_prices.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = table_prices.rowAtPoint(point);
                table_prices.setRowSelectionInterval(selectedRow, selectedRow);

                if (SwingUtilities.isRightMouseButton(e)) {
                    priceMenu.show(table_prices, e.getX(), e.getY());
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        model_customManager = new DefaultTableModel();
        Object[] col_customManager = {"ID", "Adı Soyadı", "TC Kimlik Numarası", "Telefon Numarası"};
        model_customManager.setColumnIdentifiers(col_customManager);
        row_customManager = new Object[col_roomManager.length];
        table_customers.setModel(model_customManager);
        table_customers.getTableHeader().setReorderingAllowed(false);
        loadCustomManager();

    }



    public void loadComboCities() {
        Map<String, List<String>> cityMap = Cities.CityMap();
        comboBox_city.removeAllItems();

        // Türkçe karakterlere göre sıralama için Collator kullanımı
        Collator collator = Collator.getInstance(new Locale("tr", "TR"));

        List<String> sortedCities = new ArrayList<>(cityMap.keySet());
        sortedCities.sort(collator::compare);

        int k = 0;
        for (String city : sortedCities) {
            comboBox_city.addItem(new Item(k++, city));
        }
    }

    public void loadComboZones() {
        String selectedCity = ((Item) comboBox_city.getSelectedItem()).getValue();

        if (selectedCity != null) {
            Map<String, List<String>> cityMap = Cities.CityMap();
            List<String> zones = cityMap.get(selectedCity);

            comboBox_zone.removeAllItems();
            int k = 0;
            if (zones != null) {
                for (String zone : zones) {
                    comboBox_zone.addItem(new Item(k++, zone));
                }
            }
        }
    }


    public void loadComboStars() {
        ArrayList<String> starlist = new ArrayList<>();
        starlist.add("5 Yıldız");
        starlist.add("4 Yıldız");
        starlist.add("3 Yıldız");
        starlist.add("2 Yıldız");
        starlist.add("1 Yıldız");
        int i = 0;

        for (String stars : starlist) {
            comboBox_stars.addItem(new Item(i++, stars));
        }
    }

    public void loadComboHotelTypes() {
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Her Şey Dahil");
        typelist.add("Tam Pansiyon");
        typelist.add("Yarım Pansiyon");
        int i = 0;


        for (String types : typelist) {
            comboBox_type.addItem(new Item(i++, types));
        }
    }

    public void loadComboRoomTypes() {
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Single");
        typelist.add("Double");
        typelist.add("Suit");
        int i = 0;


        for (String types : typelist) {
            comboBox_roomType.addItem(new Item(i++, types));
        }
    }

    public void loadComboPeriodTypes() {
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Yaz");
        typelist.add("Kış");
        int i = 0;
        for (String types : typelist) {
            comboBox_periodType.addItem(new Item(i++, types));
        }
    }

    public void loadComboNumbersOfBeds() {
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("1 Yataklı");
        typelist.add("2 Yataklı");

        int i = 0;


        for (String types : typelist) {
            comboBox_beds.addItem(new Item(i++, types));
        }
    }

    public void loadComboHotelsName() {
        List<HotelManager> hotelNames = HotelManager.getlist();
        comboBox_hotelsName.removeAllItems();

        int i = 0;
        for (HotelManager name : hotelNames) {
            comboBox_hotelsName.addItem(new Item(i++, name.getHotelName()));
        }
    }
    public void loadComboHotelName() {
        comboBox_hotelName.removeAllItems();
        List<RoomManager> hotelNames = RoomManager.getlist();
        Set<String> uniqueHotelNames = new HashSet<>();
        int i = 0;
        for (RoomManager name : hotelNames) {
            String hotelName = name.getHotel_name();
            if (uniqueHotelNames.add(hotelName)) {
                comboBox_hotelName.addItem(new Item(i++, hotelName));
            }
        }
    }



    public void loadComboPriceRoomTypes(){
        comboBox_roomTypess.removeAllItems();
        Item hotelNameItem = (Item) comboBox_hotelName.getSelectedItem();
        if(hotelNameItem != null){
            int ID = HotelManager.getFetch(hotelNameItem.getValue()).getId();
            int i = 0;
            for(RoomManager types : RoomManager.getFetchType(ID)){
                comboBox_roomTypess.addItem(new Item(i++, types.getType()));
            }
        }
    }



    public void updateComboBoxes(String oldHotelName, String newHotelName) {
        comboBox_hotelsName.removeAllItems();
        comboBox_hotelName.removeAllItems();

        List<HotelManager> hotelNames = HotelManager.getlist();
        int i = 0;
        for (HotelManager name : hotelNames) {
            String hotelName = name.getHotelName();
            if (hotelName.equals(oldHotelName)) {
                comboBox_hotelsName.addItem(new Item(i, newHotelName));
                comboBox_hotelName.addItem(new Item(i, newHotelName));
            } else {
                comboBox_hotelsName.addItem(new Item(i, hotelName));
                comboBox_hotelName.addItem(new Item(i, hotelName));
            }
        }
    }

    public void loadHotelManager() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_hotelManager.getModel();
        clearModel.setRowCount(0);
        for (HotelManager hotelslist : HotelManager.getlist()) {
            row_hotelManager[0] = hotelslist.getId();
            row_hotelManager[1] = hotelslist.getHotelName();
            row_hotelManager[2] = hotelslist.getCityName();
            row_hotelManager[3] = hotelslist.getZoneName();
            row_hotelManager[4] = hotelslist.getAddress();
            row_hotelManager[5] = hotelslist.getEmail();
            row_hotelManager[6] = hotelslist.getTelephone();
            row_hotelManager[7] = hotelslist.getStars();
            row_hotelManager[8] = hotelslist.getTypes();

            model_hotelManager.addRow(row_hotelManager);

        }
    }

    public void loadRoomManager() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_rooms.getModel();
        clearModel.setRowCount(0);

        for (RoomManager rooms : RoomManager.getlist()) {
                row_roomManager[0] = rooms.getId();
                row_roomManager[1] = rooms.getHotel_name();
                row_roomManager[2] = rooms.getType();
                row_roomManager[3] = rooms.getStock();
                row_roomManager[4] = rooms.getAvailable();
                row_roomManager[5] = rooms.getCapacity();
                row_roomManager[6] = rooms.getNumbersOfBeds();
                model_roomManager.addRow(row_roomManager);



        }

    }


    public void loadAdminManager() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_admin.getModel();
        clearModel.setRowCount(0);

        for (AdminManager admins : AdminManager.getList()) {
            row_adminManager[0] = admins.getId();
            row_adminManager[1] = admins.getName();
            row_adminManager[2] = admins.getUserName();
            row_adminManager[3] = admins.getPassword();
            model_adminManager.addRow(row_adminManager);

        }
    }
    public void loadCustomManager() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_customers.getModel();
        clearModel.setRowCount(0);

        for (ReservationManager customers : ReservationManager.getlist()) {
            row_customManager[0] = customers.getId();
            row_customManager[1] = customers.getName();
            row_customManager[2] = customers.getIdNo();
            row_customManager[3] = customers.getTelephone();
            model_customManager.addRow(row_customManager);

        }
    }


    public void loadPriceManager() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_prices.getModel();
        clearModel.setRowCount(0);
        UserManager obj=null;
        int i = 0;
        for (PriceManager prices : PriceManager.getlist()) {
            if (prices.getHotelName() != null) {
                if(RoomManager.getFetchBoolean(prices.getHotelId())){
                    row_priceManager[0] = prices.getId();
                    row_priceManager[1] = prices.getHotelName().getHotelName();
                    row_priceManager[2] = prices.getRoomType();
                    row_priceManager[3] = prices.getPeriodType();
                    row_priceManager[4] = prices.getAdultPrice();
                    row_priceManager[5] = prices.getKidPrice();
                    model_priceManager.addRow(row_priceManager);
                }

            }
        }
    }

    public static ArrayList<UserManager> loadTempManager(){
        ArrayList<UserManager> bestList=new ArrayList<>();
        bestList.clear();
        UserManager obj=null;
        int i = 0;
        for(PriceManager prices : PriceManager.getlist()){
            int hotelID = prices.getHotelId();
            if(HotelManager.getFetch(hotelID) != null ){
                int roomID = RoomManager.getFetchRoomID(hotelID).getId();
                String city = HotelManager.getFetch(prices.getHotelId()).getCityName();
                String zone = HotelManager.getFetch(prices.getHotelId()).getZoneName();
                int capacity = RoomManager.getFetch(roomID).getCapacity();
                System.out.println(capacity);


                obj = new UserManager(i++,hotelID, roomID , prices.getRoomType(), prices.getPeriodType(), prices.getAdultPrice(), city , zone , capacity);
                bestList.add(obj);
            }
        }
        return bestList;
    }




    private void createUIComponents() {
        // JTable bileşeni için model oluşturun
        model_hotelManager = new DefaultTableModel();
        Object[] col_hotelManager = {"ID", "Otel Adı", "İl", "İlçe", "Adres", "E-mail", "Telefon", "Yıldız", "Tesis Tipi"};
        model_hotelManager.setColumnIdentifiers(col_hotelManager);

        // JTable'a modeli bağlayın
        table_hotelManager = new JTable(model_hotelManager);
        table_hotelManager.getTableHeader().setReorderingAllowed(false);

        // Diğer bileşenleri başlatın
        field_hotelName = new JTextField();
        comboBox_city = new JComboBox();
        comboBox_zone = new JComboBox();
        field_address = new JTextField();
        field_email = new JTextField();
        field_telephone = new JTextField();
        comboBox_type = new JComboBox();
        button_add = new JButton();
        comboBox_stars = new JComboBox();
        table_rooms = new JTable();
        comboBox_roomType = new JComboBox();
        field_stock = new JTextField();
        field_available = new JTextField();
        field_capacity = new JTextField();
        comboBox_beds = new JComboBox();
        button_addRoom = new JButton();
        comboBox_hotelsName = new JComboBox();
        button_logout = new JButton();
        table_admin = new JTable();
        field_name = new JTextField();
        field_userName = new JTextField();
        field_password = new JTextField();
        field_password2 = new JTextField();
        button_addAdmin = new JButton();

        // Yeni bileşenleri başlatın
        table_prices = new JTable();
        comboBox_hotelName = new JComboBox();
        comboBox_roomTypess = new JComboBox();
        comboBox_periodType = new JComboBox();
        field_adultPrice = new JTextField();
        field_kidPrice = new JTextField();
        button_addPrice = new JButton();
        model_roomManager = new DefaultTableModel();
        row_roomManager = new Object[col_hotelManager.length];
        hotelMenu = new JPopupMenu();
        roomMenu = new JPopupMenu();
        model_adminManager = new DefaultTableModel();
        row_adminManager = new Object[col_hotelManager.length];
    }


}