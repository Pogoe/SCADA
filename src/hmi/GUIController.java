package hmi;

import batchserver.IBatchExporter;
import controller.SCADAController;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import server.Server;

public class GUIController implements Observer, Initializable
{
    @FXML
    private SplitPane splitPane;
    @FXML
    private ListView<IBatchExporter> listGreenhouse;
    @FXML
    private TextField chosenHouseField;
    @FXML
    private TextField tempField;
    @FXML
    private TextField moistField;
    @FXML
    private TextField CO2Field;
    @FXML
    private TextField waterField;
    @FXML
    private TextField dayField;
    @FXML
    private ImageView moistImg;
    @FXML
    private ImageView sunImg;
    @FXML
    private ImageView co2Img;
    @FXML
    private ImageView tempImg;
    @FXML
    private ImageView waterImg;
    @FXML
    private ImageView fanImg;
    @FXML
    private LineChart<?, ?> graph;
    @FXML
    private BarChart<?, ?> barGraph;
    
    private SCADAController controller;
    private ObservableList<IBatchExporter> batches = FXCollections.observableArrayList(SCADAController.clients);

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        controller = new SCADAController();
        controller.addObserver(this);
        Server.connect();
    }

    @Override
    public void update(Observable o, Object o1)
    {
        tempField.setText(String.valueOf(controller.getTemp()));
        moistField.setText(String.valueOf(controller.getMoist()));
        waterField.setText(String.valueOf(controller.getWaterLevel()));
    }
}
