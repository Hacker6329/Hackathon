package it.unimore.studenti.hackathon.data;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeInterval {

    // Constants
    public static final long DEFINED_DOWNTIME_MILLIS = 600000;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // Attributes
    @NotNull private final Date startTime;
    @NotNull private final Date receiveTime;

    // Constructors
    public TimeInterval(@NotNull final Date startTime, @NotNull final Date receiveTime) {
        this.startTime = startTime;
        this.receiveTime = receiveTime;
    }

    // Methods
    public @NotNull Date getStartTime() {
        return startTime;
    }
    public @NotNull Date getReceiveTime() {
        return receiveTime;
    }
    public long timeDifferenceMillis() {
        long value = receiveTime.getTime() - startTime.getTime();
        return value<0?0:value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeInterval)) return false;

        TimeInterval that = (TimeInterval) o;
        return getStartTime().equals(that.getStartTime()) && getReceiveTime().equals(that.getReceiveTime());
    }
    @Override
    public int hashCode() {
        int result = getStartTime().hashCode();
        result = 31 * result + getReceiveTime().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return receiveTime + ";" + startTime;
    }
}
