import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

// Βαλλιανάτος Βασίλειος icsd22010
// Κοκκινάκης Γεώργιος icsd24093

public class HotelFXApp extends Application {

    private Hotel hotel;

    
    private TextArea output;

    
    private TextField tfCustomer;
    private TextField tfRentableId;
    private DatePicker dpStart;
    private DatePicker dpEnd;

    
    private TextField tfSearchId;
    private TextField tfSearchCustomer;
    private DatePicker dpSearchDate;

    
    private TextField tfDeleteId;

    
    private DatePicker dpStatsDate;
    private TextField tfRevenueMonth; 

    public static void main(String[] args) {
        launch(args);
    }

    
    public void start(Stage stage) {
        hotel = new Hotel("HotelProject", "Patras", 4);
        seedRentables();

        TabPane tabs = new TabPane();
        tabs.getTabs().add(new Tab("New Reservation", buildNewReservationPane()));
        tabs.getTabs().add(new Tab("Search / Delete", buildSearchDeletePane()));
        tabs.getTabs().add(new Tab("Stats", buildStatsPane()));
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        output = new TextArea();
        output.setEditable(false);
        output.setPrefRowCount(10);

        VBox root = new VBox(10, tabs, new Label("Output:"), output);
        root.setPadding(new Insets(12));

        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Hotel Booking System (JavaFX)");
        stage.setScene(scene);
        stage.show();

        log("System ready. Seed rentables added. Try a reservation!");
        log("Example rentable ids: 101, 102, L201, CAR1234, ATV7777, SCO1111, HALL-A");
    }

    private void seedRentables() {
        
        hotel.addRentable(new CommonRoom("101", Bedtype.SINGLE, false));
        hotel.addRentable(new CommonRoom("102", Bedtype.DOUBLE, true));
        hotel.addRentable(new LuxuryRoom("L201", true));

        
        hotel.addRentable(new Car("CAR1234"));
        hotel.addRentable(new ATV("ATV7777"));
        hotel.addRentable(new Scooter("SCO1111"));

        
        hotel.addRentable(new Eventhall("HALL-A"));
    }

    private Pane buildNewReservationPane() {
        tfCustomer = new TextField();
        tfRentableId = new TextField();
        dpStart = new DatePicker();
        dpEnd = new DatePicker();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        grid.add(new Label("Customer Name:"), 0, 0);
        grid.add(tfCustomer, 1, 0);

        grid.add(new Label("Rentable ID:"), 0, 1);
        grid.add(tfRentableId, 1, 1);

        grid.add(new Label("Start Date:"), 0, 2);
        grid.add(dpStart, 1, 2);

        grid.add(new Label("End Date:"), 0, 3);
        grid.add(dpEnd, 1, 3);

        Button btnBook = new Button("Create Reservation");
        btnBook.setOnAction(e -> handleCreateReservation());

        Button btnListRentables = new Button("List Rentables");
        btnListRentables.setOnAction(e -> handleListRentables());

        HBox buttons = new HBox(10, btnBook, btnListRentables);

        VBox box = new VBox(10, grid, buttons);
        box.setPadding(new Insets(10));
        return box;
    }

    private Pane buildSearchDeletePane() {
        tfSearchId = new TextField();
        tfSearchCustomer = new TextField();
        dpSearchDate = new DatePicker();

        GridPane searchGrid = new GridPane();
        searchGrid.setHgap(10);
        searchGrid.setVgap(10);
        searchGrid.setPadding(new Insets(12));

        searchGrid.add(new Label("Search by Reservation ID:"), 0, 0);
        searchGrid.add(tfSearchId, 1, 0);

        Button btnSearchId = new Button("Search ID");
        btnSearchId.setOnAction(e -> handleSearchById());
        searchGrid.add(btnSearchId, 2, 0);

        searchGrid.add(new Label("Search by Customer Name:"), 0, 1);
        searchGrid.add(tfSearchCustomer, 1, 1);

        Button btnSearchCustomer = new Button("Search Customer");
        btnSearchCustomer.setOnAction(e -> handleSearchByCustomer());
        searchGrid.add(btnSearchCustomer, 2, 1);

        searchGrid.add(new Label("Search Active on Date:"), 0, 2);
        searchGrid.add(dpSearchDate, 1, 2);

        Button btnSearchDate = new Button("Search Date");
        btnSearchDate.setOnAction(e -> handleSearchByDate());
        searchGrid.add(btnSearchDate, 2, 2);

        tfDeleteId = new TextField();

        GridPane deleteGrid = new GridPane();
        deleteGrid.setHgap(10);
        deleteGrid.setVgap(10);
        deleteGrid.setPadding(new Insets(12));

        deleteGrid.add(new Label("Delete Reservation ID:"), 0, 0);
        deleteGrid.add(tfDeleteId, 1, 0);

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> handleDeleteReservation());
        deleteGrid.add(btnDelete, 2, 0);

        VBox box = new VBox(15,
                new Label("Search"),
                searchGrid,
                new Separator(),
                new Label("Delete"),
                deleteGrid
        );
        box.setPadding(new Insets(10));
        return box;
    }

    private Pane buildStatsPane() {
        dpStatsDate = new DatePicker();
        tfRevenueMonth = new TextField();
        tfRevenueMonth.setPromptText("YYYY-MM (e.g. 2026-01)");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        grid.add(new Label("Date for Occupancy Stats:"), 0, 0);
        grid.add(dpStatsDate, 1, 0);

        Button btnTypes = new Button("Occupied Types");
        btnTypes.setOnAction(e -> handleOccupiedTypes());

        Button btnRoomCounts = new Button("Room Counts (Common/Luxury)");
        btnRoomCounts.setOnAction(e -> handleRoomCounts());

        HBox occButtons = new HBox(10, btnTypes, btnRoomCounts);
        grid.add(occButtons, 1, 1);

        grid.add(new Separator(), 0, 2, 3, 1);

        grid.add(new Label("Monthly Revenue (start/end in month):"), 0, 3);
        grid.add(tfRevenueMonth, 1, 3);

        Button btnRevenue = new Button("Calculate Revenue");
        btnRevenue.setOnAction(e -> handleMonthlyRevenue());
        grid.add(btnRevenue, 2, 3);

        VBox box = new VBox(10, grid);
        box.setPadding(new Insets(10));
        return box;
    }

    private void handleCreateReservation() {
        try {
            String customer = tfCustomer.getText();
            String rentableId = tfRentableId.getText();
            LocalDate start = dpStart.getValue();
            LocalDate end = dpEnd.getValue();

            Reservation r = hotel.createReservation(customer, start, end, rentableId.trim());
            log("CREATED: " + r);
            showInfo("Reservation created", "Total cost: " + String.format("%.2f", r.getTotalCost()) + "€");
        } catch (Exception ex) {
            showError("Cannot create reservation", ex.getMessage());
        }
    }

    private void handleListRentables() {
        List<Rentable> rentables = hotel.getAllRentables();
        log("---- RENTABLES ----");
        for (Rentable r : rentables) {
            log(r.getTypeName() + " | id=" + r.getId() + " | daily=" + String.format("%.2f", r.getDailyCost()) + "€");
        }
    }

    private void handleSearchById() {
        try {
            int id = Integer.parseInt(tfSearchId.getText().trim());
            Reservation r = hotel.findReservationById(id);
            if (r == null) log("Search ID: No reservation found for id=" + id);
            else log("FOUND: " + r);
        } catch (Exception ex) {
            showError("Search by ID failed", "Give a valid integer ID.");
        }
    }

    private void handleSearchByCustomer() {
        String name = tfSearchCustomer.getText();
        List<Reservation> list = hotel.findReservationsByCustomer(name);
        log("Search Customer: " + name);
        if (list.isEmpty()) log("No reservations for this customer.");
        for (Reservation r : list) log(r.toString());
    }

    private void handleSearchByDate() {
        LocalDate d = dpSearchDate.getValue();
        List<Reservation> list = hotel.findActiveReservationsOn(d);
        log("Active on " + d + ":");
        if (list.isEmpty()) log("No active reservations.");
        for (Reservation r : list) log(r.toString());
    }

    private void handleDeleteReservation() {
        try {
            int id = Integer.parseInt(tfDeleteId.getText().trim());
            boolean ok = hotel.deleteReservation(id);
            if (ok) {
                log("DELETED reservation id=" + id);
                showInfo("Deleted", "Reservation " + id + " deleted.");
            } else {
                showError("Delete failed", "No reservation with id=" + id);
            }
        } catch (Exception ex) {
            showError("Delete failed", "Give a valid integer ID.");
        }
    }

    private void handleOccupiedTypes() {
        try {
            LocalDate d = dpStatsDate.getValue();
            Set<String> types = hotel.getOccupiedRentableTypesOn(d);
            log("Occupied types on " + d + ": " + types);
        } catch (Exception ex) {
            showError("Stats failed", "Choose a valid date.");
        }
    }

    private void handleRoomCounts() {
        try {
            LocalDate d = dpStatsDate.getValue();
            Roomstats stats = hotel.getOccupiedRoomStatsOn(d);
            log("Room counts on " + d + " => " + stats);
        } catch (Exception ex) {
            showError("Stats failed", "Choose a valid date.");
        }
    }

    private void handleMonthlyRevenue() {
        try {
            String text = tfRevenueMonth.getText().trim();
            YearMonth ym = YearMonth.parse(text);
            double revenue = hotel.getMonthlyRevenue(ym);
            log("Monthly revenue for " + ym + ": " + String.format("%.2f", revenue) + "€");
        } catch (Exception ex) {
            showError("Revenue failed", "Give month like YYYY-MM (e.g. 2026-01).");
        }
    }

    private void log(String msg) {
        output.appendText(msg + "\n");
    }

    private void showInfo(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    private void showError(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(message);
        a.showAndWait();
    }
}
