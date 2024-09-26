package it.unimore.studenti.hackathon.data.csv;

import it.unimore.studenti.hackathon.exception.CSVParseException;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class CSVData {

    // Constants
    public static final int EXPECTED_COLS = 4;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // Attributes
    @NotNull private final String serialNumber;
    private final short channel;
    @NotNull private final Date dataBoxOne;
    @NotNull private final Date dateTime;

    // Constructors
    public CSVData(@NotNull final String parsableLine) throws CSVParseException {
        String[] data = parsableLine.split(";");
        if (data.length != EXPECTED_COLS) throw new CSVParseException("The data line doesn't have right number of fields.");
        try {
            serialNumber = data[0];
            channel = Short.parseShort(data[1]);
            dataBoxOne = DATE_FORMAT.parse(data[2]);
            dateTime = DATE_FORMAT.parse(data[3]);
        } catch (NumberFormatException | ParseException e) {
            throw new CSVParseException("The data line contains an unexpected data format", e);
        }
    }

    // Methods
    @NotNull
    public String getSerialNumber() {
        return serialNumber;
    }
    public short getChannel() {
        return channel;
    }
    @NotNull
    public Date getDataBoxOne() {
        return dataBoxOne;
    }
    @NotNull
    public Date getDateTime() {
        return dateTime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CSVData)) return false;

        CSVData csvData = (CSVData) o;
        return getChannel() == csvData.getChannel() && getSerialNumber().equals(csvData.getSerialNumber()) && getDataBoxOne().equals(csvData.getDataBoxOne()) && getDateTime().equals(csvData.getDateTime());
    }
    @Override
    public int hashCode() {
        int result = getSerialNumber().hashCode();
        result = 31 * result + getChannel();
        result = 31 * result + getDataBoxOne().hashCode();
        result = 31 * result + getDateTime().hashCode();
        return result;
    }
    @Override @NotNull
    public String toString() {
        return serialNumber + ";" + channel + ";" + dataBoxOne + ";" + dateTime;
    }
}
