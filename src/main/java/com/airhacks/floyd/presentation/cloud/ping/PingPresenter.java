package com.airhacks.floyd.presentation.cloud.ping;

import com.airhacks.floyd.business.monitor.boundary.PingService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javax.inject.Inject;

/**
 *
 * @author airhacks.com
 */
public class PingPresenter implements Initializable {

    @Inject
    String uri;

    @FXML
    Label errorSink;

    @Inject
    PingService service;

    @FXML
    NumberAxis memoryNumberAxis;

    @FXML
    NumberAxis loadNumberAxis;

    @FXML
    BarChart loadAverageChart;

    @FXML
    BarChart memoryChart;

    @FXML
    Label upTime;

    @FXML
    Label cores;

    private XYChart.Series<String, Number> memorySeries;
    private XYChart.Series<String, Number> loadSeries;
    private long counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.memorySeries = new XYChart.Series<>();
        this.loadSeries = new XYChart.Series<>();
        memoryChart.getData().add(memorySeries);
        loadAverageChart.getData().add(loadSeries);
        refresh();
    }

    public void refresh() {
        this.refreshMemory();
        this.refreshOsInfo();
        this.refreshUpTime();
        counter++;
    }

    public void refreshOsInfo() {
        XYChart.Data<String, Number> point = new XYChart.Data<>();
        point.setXValue(String.valueOf(counter));
        IntegerProperty upTimeProperty = new SimpleIntegerProperty();

        Runnable doneListener = () -> {
            Platform.runLater(() -> {
                loadSeries.getData().add(point);
                cores.setText(String.valueOf(upTimeProperty.get()));
            });
        };
        service.askForOSInfo("http://" + uri, point::setYValue, upTimeProperty::set, errorSink::setText, doneListener);

    }

    public void refreshMemory() {
        XYChart.Data<String, Number> point = new XYChart.Data<>();
        point.setXValue(String.valueOf(counter));
        Runnable doneListener = () -> {
            Platform.runLater(() -> memorySeries.getData().add(point));
        };
        service.askForMemory("http://" + uri, memoryNumberAxis.upperBoundProperty()::set, point::setYValue, errorSink::setText, doneListener);
    }

    public void refreshUpTime() {
        StringProperty upTimeProperty = new SimpleStringProperty();
        Runnable doneListener = () -> {
            Platform.runLater(() -> upTime.setText(upTimeProperty.get()));
        };
        service.askForUptime("http://" + uri, upTimeProperty::set, errorSink::setText, doneListener);

    }

}
