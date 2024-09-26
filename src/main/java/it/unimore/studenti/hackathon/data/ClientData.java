package it.unimore.studenti.hackathon.data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public final class ClientData {

    // Attributes
    @NotNull private final String clientName;
    @NotNull private final String serialNumber;
    @NotNull private final HashMap<@NotNull Short, ArrayList<@NotNull TimeInterval>> deviceReadings;

    // Constructors
    public ClientData(@NotNull final String clientName, @NotNull final String serialNumber, @NotNull final HashMap<@NotNull Short, ArrayList<@NotNull TimeInterval>> deviceReadings) {
        this.clientName = clientName;
        this.serialNumber = serialNumber;
        this.deviceReadings = deviceReadings;
    }

    // Methods
    public @NotNull String getClientName() {
        return clientName;
    }
    public @NotNull String getSerialNumber() {
        return serialNumber;
    }
    public @NotNull ArrayList<@NotNull Short> getDevices() {
        return new ArrayList<>(deviceReadings.keySet());
    }
    public @NotNull HashMap<@NotNull Short, ArrayList<@NotNull TimeInterval>> getDeviceReadings() {
        return deviceReadings;
    }
    public long getMidTimePacketsMillis(final short channel) {
        ArrayList<@NotNull TimeInterval> timeIntervals = deviceReadings.get(channel);
        double mid = 0;
        for (TimeInterval interval : timeIntervals) {
            mid += (interval.getReceiveTime().getTime() - interval.getStartTime().getTime());
        }
        return Math.round(mid / timeIntervals.size());
    }
    public String getMalfunctionProbability(final short channel) {
        float downtimeIntervals = deviceReadings.get(channel).stream().filter(timeInterval -> (timeInterval.getReceiveTime().getTime() - timeInterval.getStartTime().getTime()) < TimeInterval.DEFINED_DOWNTIME_MILLIS).count();
        return String.format("%.1f", (downtimeIntervals / deviceReadings.get(channel).size()) * 100) + '%';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientData)) return false;

        ClientData clientData = (ClientData) o;
        return getClientName().equals(clientData.getClientName()) && getSerialNumber().equals(clientData.getSerialNumber()) && getDeviceReadings().equals(clientData.getDeviceReadings());
    }
    @Override
    public int hashCode() {
        int result = getClientName().hashCode();
        result = 31 * result + getSerialNumber().hashCode();
        result = 31 * result + getDeviceReadings().hashCode();
        return result;
    }
    @Override
    public String toString() {
        return clientName;
    }
}
