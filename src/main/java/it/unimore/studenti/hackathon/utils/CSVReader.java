package it.unimore.studenti.hackathon.utils;

import it.unimore.studenti.hackathon.data.ClientData;
import it.unimore.studenti.hackathon.data.TimeInterval;
import it.unimore.studenti.hackathon.data.csv.CSVData;
import it.unimore.studenti.hackathon.data.LineRead;
import it.unimore.studenti.hackathon.exception.CSVParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CSVReader {

    // Constants
    @SuppressWarnings("unused") public static final String OLD_EXPECTED_FIRST_LINE = "Serialnum;Canale;DataOraPeriodo;DataOra;VE;V1;V2;V3;IE;I1;I2;I3;CE;C1;C2;C3;PE;P1;P2;P3;PEM;EE;E1;E2;E3;ER;EEMINIMO;EEMASSIMO;ERMINIMO;ERMASSIMO";
    public static final String EXPECTED_FIRST_LINE = "SerialNum;Canale;DataBoxOne;DataOra";

    // Methods
    @NotNull
    public static ArrayList<@NotNull ClientData> readCSV(@NotNull final File csvFile, @Nullable LineRead lineRead) throws FileNotFoundException, CSVParseException {
        Scanner fileReader = new Scanner(csvFile, Defs.DEFAULT_CHARSET);
        String firstLine = fileReader.nextLine();
        if (!firstLine.equals(EXPECTED_FIRST_LINE)) {
            fileReader.close();
            throw new CSVParseException("The expected column names are not correct");
        }
        if (lineRead != null) lineRead.incrementLinesReadBy(1);
        ArrayList<@NotNull ClientData> dataList = new ArrayList<>();
        while (fileReader.hasNext()) {
            String line = fileReader.nextLine();
            if (line.replace(" ", "").replace("\t", "").replace("\n", "").isEmpty()) continue;
            CSVData data = new CSVData(line);
            List<ClientData> compatibleClientData = dataList.stream().filter(clientData -> clientData.getSerialNumber().equals(data.getSerialNumber())).collect(Collectors.toList());
            if (compatibleClientData.isEmpty()) {
                String newClientName = Defs.getRandomName();
                if (newClientName == null) newClientName = data.getSerialNumber();
                ClientData clientData = new ClientData(newClientName, data.getSerialNumber(), new HashMap<>());
                clientData.getDeviceReadings().put(data.getChannel(), new ArrayList<>());
                clientData.getDeviceReadings().get(data.getChannel()).add(new TimeInterval(data.getDateTime(), data.getDataBoxOne()));
                dataList.add(clientData);
            } else {
                ClientData clientData = compatibleClientData.get(0);
                if (clientData.getDeviceReadings().containsKey(data.getChannel())) {
                    clientData.getDeviceReadings().get(data.getChannel()).add(new TimeInterval(data.getDateTime(), data.getDataBoxOne()));
                } else {
                    clientData.getDeviceReadings().put(data.getChannel(), new ArrayList<>());
                    clientData.getDeviceReadings().get(data.getChannel()).add(new TimeInterval(data.getDateTime(), data.getDataBoxOne()));
                }
            }
            if (lineRead != null) lineRead.incrementLinesReadBy(1);
        }
        fileReader.close();
        return dataList;
    }
    public static long getFileLineCount(@NotNull final File file) throws IOException {
        try (Stream<String> fileStream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            return fileStream.count();
        }
    }
}
