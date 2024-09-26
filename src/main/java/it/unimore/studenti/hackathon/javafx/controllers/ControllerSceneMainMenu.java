package it.unimore.studenti.hackathon.javafx.controllers;

import it.italiandudes.idl.common.Logger;
import it.unimore.studenti.hackathon.data.ClientData;
import it.unimore.studenti.hackathon.data.LineRead;
import it.unimore.studenti.hackathon.exception.CSVParseException;
import it.unimore.studenti.hackathon.javafx.Client;
import it.unimore.studenti.hackathon.javafx.alerts.ErrorAlert;
import it.unimore.studenti.hackathon.javafx.scenes.SceneControlPanel;
import it.unimore.studenti.hackathon.utils.CSVReader;
import it.unimore.studenti.hackathon.utils.Defs;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public final class ControllerSceneMainMenu {

    // Attributes
    @Nullable private ArrayList<@NotNull ClientData> clientData = null;

    // Graphic Elements
    @FXML private TextArea textAreaOps;
    @FXML private ProgressBar progressBar;
    @FXML private Label labelProgress;
    @FXML private Button continueButton;

    // Initialize
    @FXML
    private void initialize() {
        addLine("Inizio lettura dataset...");
        startReadingDataset();
    }

    // Methods
    private void addLine(@NotNull final String line) {
        Logger.log(line);
        textAreaOps.appendText(line + '\n');
    }

    // EDT
    @FXML
    private void gotoControlPanel() {
        if (clientData == null) {
            new ErrorAlert("ERRORE", "Errore Interno", "Il set di dati e' nullo, impossibile accedere al pannello di controllo.");
            Client.exit();
            return;
        }
        Client.setScene(SceneControlPanel.getScene(clientData));
    }
    private void startReadingDataset() {
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {

                        long startTime = System.currentTimeMillis();

                        File csvFile = new File(Defs.CSV_FILE);
                        if (!csvFile.exists() || !csvFile.isFile()) {
                            Platform.runLater(() -> {
                                new ErrorAlert("ERRORE", "ERRORE DI I/O", "File al percorso \"" + Defs.CSV_FILE + "\" non trovato.");
                                Client.exit();
                            });
                            return null;
                        }

                        addLine("Raccolta dimensione dataset...");

                        long linesToRead;
                        try {
                            linesToRead = CSVReader.getFileLineCount(csvFile);
                        } catch (IOException e) {
                            Logger.log(e);
                            Platform.runLater(() -> {
                                new ErrorAlert("ERRORE", "ERRORE DI I/O", "Si e' verificato un errore nella lettura del file dati.");
                                Client.exit();
                            });
                            return null;
                        }

                        addLine("Dimensione dataset: " + linesToRead + " righe");

                        LineRead lineRead = new LineRead(linesToRead);

                        new Thread(() -> {
                            while (!lineRead.isFinish()) {
                                try {
                                    //noinspection BusyWait
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                Platform.runLater(() -> {
                                    float percZeroOne = lineRead.getPercentageZeroOne();
                                    addLine("Righe lette: " + lineRead.getLinesRead() + "/" + linesToRead);
                                    progressBar.setProgress(percZeroOne);
                                    labelProgress.setText(lineRead.getPercentage(percZeroOne));
                                    if (percZeroOne == 1) {
                                        addLine("Lettura file completata in " + (System.currentTimeMillis() - startTime) + "ms!");
                                        if (clientData != null) addLine("Clienti letti: " + clientData.size());
                                    }
                                });
                            }
                        }).start();

                        try {
                            clientData = CSVReader.readCSV(csvFile, lineRead);
                        } catch (FileNotFoundException e) {
                            lineRead.setLinesRead(lineRead.getLinesToRead());
                            Logger.log(e);
                            Platform.runLater(() -> {
                                new ErrorAlert("ERRORE", "ERRORE DI I/O", "File al percorso \"" + Defs.CSV_FILE + "\" non trovato.");
                                Client.exit();
                            });
                            return null;
                        } catch (CSVParseException e) {
                            lineRead.setLinesRead(lineRead.getLinesToRead());
                            addLine("Errore di parsing!");
                            Logger.log(e);
                            Platform.runLater(() -> {
                                new ErrorAlert("ERRORE", "ERRORE DI PARSING", "Si e' verificato un errore di parsing del file, verifica i log per l'errore.");
                                Client.exit();
                            });
                            return null;
                        }

                        Platform.runLater(() -> {
                            progressBar.setVisible(false);
                            labelProgress.setVisible(false);
                            continueButton.setDisable(false);
                            continueButton.setVisible(true);
                        });

                        return null;
                    }
                };
            }
        }.start();
    }
}