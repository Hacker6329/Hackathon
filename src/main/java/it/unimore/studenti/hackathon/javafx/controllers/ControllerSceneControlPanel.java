package it.unimore.studenti.hackathon.javafx.controllers;

import it.unimore.studenti.hackathon.data.ClientData;
import it.unimore.studenti.hackathon.data.TimeInterval;
import it.unimore.studenti.hackathon.javafx.utils.UIElementConfigurator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public final class ControllerSceneControlPanel {

    // Constants
    public static final int DEFAULT_SAMPLE_RATE = 20;

    // Attributes
    private ArrayList<@NotNull ClientData> clientData = null;
    private volatile boolean configurationComplete = false;

    // Attribute Initializers
    public void setClientData(@NotNull final ArrayList<@NotNull ClientData> clientData) {
        this.clientData = clientData;
    }
    public void configurationComplete() {
        configurationComplete = true;
    }

    // Graphic Elements
    @FXML private ComboBox<ClientData> comboBoxClientData;
    @FXML private ComboBox<Short> comboBoxDeviceChannel;
    @FXML private Label labelNoClientChannel;
    @FXML private GridPane gridPaneData;
    @FXML private Label labelMidTimePackets;
    @FXML private Label labelMalfunctioningProbability;
    @FXML private AnchorPane anchorPaneChart;
    @FXML private TextField textFieldDateBegin;
    @FXML private TextField textFieldDateEnd;
    @FXML private Spinner<Integer> spinnerSampleRate;
    @FXML private Button buttonFilter;
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

    // Initialize
    @FXML
    private void initialize() {
        spinnerSampleRate.getEditor().setTextFormatter(UIElementConfigurator.configureNewIntegerTextFormatter());
        spinnerSampleRate.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, DEFAULT_SAMPLE_RATE, 1));
        spinnerSampleRate.setEditable(true);
        lineChart.setLegendVisible(false);
        xAxis.setLabel("Data di Invio");
        yAxis.setLabel("Tempo di Arrivo (minuti)");
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(dataSeries);
        anchorPaneChart.getChildren().add(lineChart);
        AnchorPane.setTopAnchor(lineChart, 0.0);
        AnchorPane.setLeftAnchor(lineChart, 0.0);
        AnchorPane.setBottomAnchor(lineChart, 0.0);
        AnchorPane.setRightAnchor(lineChart, 0.0);
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        //noinspection StatementWithEmptyBody
                        while (!configurationComplete);
                        Platform.runLater(() -> comboBoxClientData.setItems(FXCollections.observableArrayList(clientData)));
                        return null;
                    }
                };
            }
        }.start();
    }

    // Methods
    private void nullClientComponents() {
        hideGridPaneData();
        comboBoxDeviceChannel.setDisable(true);
    }
    private void hideGridPaneData() {
        gridPaneData.setVisible(false);
        labelNoClientChannel.setVisible(true);
    }
    private void showGridPaneData() {
        labelNoClientChannel.setVisible(false);
        gridPaneData.setVisible(true);
    }
    private void updateGridPaneData() {
        ClientData clientData = comboBoxClientData.getSelectionModel().getSelectedItem();
        Short channel = comboBoxDeviceChannel.getSelectionModel().getSelectedItem();
        if (clientData == null || channel == null) return;
        labelMidTimePackets.setText((clientData.getMidTimePacketsMillis(channel) / 60000) + "min");
        labelMalfunctioningProbability.setText(clientData.getMalfunctionProbability(channel));
        writeDataChart(clientData.getDeviceReadings().get(channel));
    }
    private void writeDataChart(@NotNull final ArrayList<@NotNull TimeInterval> intervals) {
        writeDataChart(intervals, DEFAULT_SAMPLE_RATE);
    }
    private void writeDataChart(@NotNull final ArrayList<@NotNull TimeInterval> intervals, int samplingRate) {
        dataSeries.getData().clear();
        if (intervals.isEmpty()) return;
        if (samplingRate <= 0) samplingRate = DEFAULT_SAMPLE_RATE;
        int finalSamplingRate = samplingRate;
        for (int i=0; i<intervals.size(); i+= finalSamplingRate) {
            dataSeries.getData().add(new XYChart.Data<>(TimeInterval.DATE_FORMAT.format(intervals.get(i).getStartTime()), intervals.get(i).timeDifferenceMillis() / 60000.0f));
        }
    }

    // EDT
    @FXML
    private void filterData() {
        buttonFilter.setDisable(true);
        String begin = textFieldDateBegin.getText();
        String end = textFieldDateEnd.getText();
        if (begin.isEmpty()) begin = null;
        if (end.isEmpty()) end = null;
        Date beginDate = null, endDate = null;
        if (begin != null) {
            try {
                beginDate = TimeInterval.DATE_FORMAT.parse(begin);
            } catch (ParseException ignored) {}
        }
        if (end != null) {
            try {
                endDate = TimeInterval.DATE_FORMAT.parse(end);
            } catch (ParseException ignored) {}
        }
        long numBegin, numEnd;
        if (beginDate == null) numBegin = Long.MIN_VALUE;
        else numBegin = beginDate.getTime();
        if (endDate == null) numEnd = Long.MAX_VALUE;
        else numEnd = endDate.getTime();
        ClientData clientData = comboBoxClientData.getSelectionModel().getSelectedItem();
        Short channel = comboBoxDeviceChannel.getSelectionModel().getSelectedItem();
        if (clientData == null || channel == null) return;
        int samplingRate = -1;
        if (!spinnerSampleRate.getEditor().getText().isEmpty()) {
            try {
                samplingRate = Integer.parseInt(spinnerSampleRate.getEditor().getText());
            } catch (NumberFormatException ignored) {}
        }
        if (samplingRate == -1) samplingRate = DEFAULT_SAMPLE_RATE;
        writeDataChart(clientData.getDeviceReadings().get(channel).stream().filter(interval -> interval.getStartTime().getTime() >= numBegin && interval.getStartTime().getTime() <= numEnd).collect(Collectors.toCollection(ArrayList::new)), samplingRate);
        buttonFilter.setDisable(false);
    }
    @FXML
    private void clientChange() {
        comboBoxDeviceChannel.getSelectionModel().clearSelection();
        ClientData clientData = comboBoxClientData.getSelectionModel().getSelectedItem();
        if (clientData == null) {
            nullClientComponents();
            return;
        }
        comboBoxDeviceChannel.setDisable(false);
        comboBoxDeviceChannel.setItems(FXCollections.observableArrayList(clientData.getDevices()));
    }
    @FXML
    private void deviceChannelChange() {
        Short channel = comboBoxDeviceChannel.getSelectionModel().getSelectedItem();
        if (channel == null) {
            hideGridPaneData();
            return;
        }
        updateGridPaneData();
        showGridPaneData();
    }
}
