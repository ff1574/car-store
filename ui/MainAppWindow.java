package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manufacturer;
import services.ManufacturerService;

public class MainAppWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        TableView<Manufacturer> tableView = new TableView<>();
        TableColumn<Manufacturer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));

        TableColumn<Manufacturer, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerCountry"));

        TableColumn<Manufacturer, String> websiteCol = new TableColumn<>("Website");
        websiteCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerWebsite"));

        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(countryCol);
        tableView.getColumns().add(websiteCol);

        ManufacturerService service = new ManufacturerService();
        tableView.getItems().setAll(service.getAllManufacturers()); // Make sure this line fetches the data

        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
