package it.unimore.studenti.hackathon.data.csv;

import it.unimore.studenti.hackathon.exception.CSVParseException;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@SuppressWarnings("unused")
public final class CSVDataOld {

    // Constants
    public static final int EXPECTED_COLS = 30;

    // Attributes
    @NotNull private final String serialNumber;
    private final short channel;
    @NotNull private final Date dateTimePeriod;
    @NotNull private final Date dateTime;
    private final int ve;
    private final int v1;
    private final int v2;
    private final int v3;
    private final int ie;
    private final int i1;
    private final int i2;
    private final int i3;
    private final int ce;
    private final int c1;
    private final int c2;
    private final int c3;
    private final int pe;
    private final int p1;
    private final int p2;
    private final int p3;
    private final int pem;
    private final int ee;
    private final int e1;
    private final int e2;
    private final int e3;
    private final int er;
    private final int eeMin;
    private final int eeMax;
    private final int erMin;
    private final int erMax;

    public CSVDataOld(@NotNull final String parsableLine) throws CSVParseException {
        String[] data = parsableLine.split(";");
        if (data.length != EXPECTED_COLS) throw new CSVParseException("The data line doesn't have right number of fields.");
        try {
            serialNumber = data[0];
            channel = Short.parseShort(data[1]);
            dateTimePeriod = DateFormat.getDateTimeInstance().parse(data[2]);
            dateTime = DateFormat.getDateTimeInstance().parse(data[3]);
            ve = Integer.parseInt(data[4]);
            v1 = Integer.parseInt(data[5]);
            v2 = Integer.parseInt(data[6]);
            v3 = Integer.parseInt(data[7]);
            ie = Integer.parseInt(data[8]);
            i1 = Integer.parseInt(data[9]);
            i2 = Integer.parseInt(data[10]);
            i3 = Integer.parseInt(data[11]);
            ce = Integer.parseInt(data[12]);
            c1 = Integer.parseInt(data[13]);
            c2 = Integer.parseInt(data[14]);
            c3 = Integer.parseInt(data[15]);
            pe = Integer.parseInt(data[16]);
            p1 = Integer.parseInt(data[17]);
            p2 = Integer.parseInt(data[18]);
            p3 = Integer.parseInt(data[19]);
            pem = Integer.parseInt(data[20]);
            ee = Integer.parseInt(data[21]);
            e1 = Integer.parseInt(data[22]);
            e2 = Integer.parseInt(data[23]);
            e3 = Integer.parseInt(data[24]);
            er = Integer.parseInt(data[25]);
            eeMin = Integer.parseInt(data[26]);
            eeMax = Integer.parseInt(data[27]);
            erMin = Integer.parseInt(data[28]);
            erMax = Integer.parseInt(data[29]);
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
    public Date getDateTimePeriod() {
        return dateTimePeriod;
    }
    @NotNull
    public Date getDateTime() {
        return dateTime;
    }
    public int getVe() {
        return ve;
    }
    public int getV1() {
        return v1;
    }
    public int getV2() {
        return v2;
    }
    public int getV3() {
        return v3;
    }
    public int getIe() {
        return ie;
    }
    public int getI1() {
        return i1;
    }
    public int getI2() {
        return i2;
    }
    public int getI3() {
        return i3;
    }
    public int getCe() {
        return ce;
    }
    public int getC1() {
        return c1;
    }
    public int getC2() {
        return c2;
    }
    public int getC3() {
        return c3;
    }
    public int getPe() {
        return pe;
    }
    public int getP1() {
        return p1;
    }
    public int getP2() {
        return p2;
    }
    public int getP3() {
        return p3;
    }
    public int getPem() {
        return pem;
    }
    public int getEe() {
        return ee;
    }
    public int getE1() {
        return e1;
    }
    public int getE2() {
        return e2;
    }
    public int getE3() {
        return e3;
    }
    public int getEr() {
        return er;
    }
    public int getEeMin() {
        return eeMin;
    }
    public int getEeMax() {
        return eeMax;
    }
    public int getErMin() {
        return erMin;
    }
    public int getErMax() {
        return erMax;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CSVDataOld)) return false;

        CSVDataOld csvData = (CSVDataOld) o;
        return getChannel() == csvData.getChannel() && getVe() == csvData.getVe() && getV1() == csvData.getV1() && getV2() == csvData.getV2() && getV3() == csvData.getV3() && getIe() == csvData.getIe() && getI1() == csvData.getI1() && getI2() == csvData.getI2() && getI3() == csvData.getI3() && getCe() == csvData.getCe() && getC1() == csvData.getC1() && getC2() == csvData.getC2() && getC3() == csvData.getC3() && getPe() == csvData.getPe() && getP1() == csvData.getP1() && getP2() == csvData.getP2() && getP3() == csvData.getP3() && getPem() == csvData.getPem() && getEe() == csvData.getEe() && getE1() == csvData.getE1() && getE2() == csvData.getE2() && getE3() == csvData.getE3() && getEr() == csvData.getEr() && getEeMin() == csvData.getEeMin() && getEeMax() == csvData.getEeMax() && getErMin() == csvData.getErMin() && getErMax() == csvData.getErMax() && getSerialNumber().equals(csvData.getSerialNumber()) && getDateTimePeriod().equals(csvData.getDateTimePeriod()) && getDateTime().equals(csvData.getDateTime());
    }
    @Override
    public int hashCode() {
        int result = getSerialNumber().hashCode();
        result = 31 * result + getChannel();
        result = 31 * result + getDateTimePeriod().hashCode();
        result = 31 * result + getDateTime().hashCode();
        result = 31 * result + getVe();
        result = 31 * result + getV1();
        result = 31 * result + getV2();
        result = 31 * result + getV3();
        result = 31 * result + getIe();
        result = 31 * result + getI1();
        result = 31 * result + getI2();
        result = 31 * result + getI3();
        result = 31 * result + getCe();
        result = 31 * result + getC1();
        result = 31 * result + getC2();
        result = 31 * result + getC3();
        result = 31 * result + getPe();
        result = 31 * result + getP1();
        result = 31 * result + getP2();
        result = 31 * result + getP3();
        result = 31 * result + getPem();
        result = 31 * result + getEe();
        result = 31 * result + getE1();
        result = 31 * result + getE2();
        result = 31 * result + getE3();
        result = 31 * result + getEr();
        result = 31 * result + getEeMin();
        result = 31 * result + getEeMax();
        result = 31 * result + getErMin();
        result = 31 * result + getErMax();
        return result;
    }

    @Override
    public String toString() {
        return serialNumber + ';' + channel + ';' + dateTimePeriod + ';' + dateTime + ';' + ve + ';' + v1 + ';' + v2 + ';' + v3 + ';' + ie + ';' + i1 + ';' + i2 + ';' + i3 + ';' + ce + ';' + c1 + ';' + c2 + ';' + c3 + ';' + pe + ';' + p1 + ';' + p2 + ';' + p3 + ';' + pem + ';' + ee + ';' + e1 + ';' + e2 + ';' + e3 + ';' + er + ';' + eeMin + ';' + eeMax + ';' + erMin + ';' + erMax + ';';
    }
}
