package it.unimore.studenti.hackathon.data;

import org.jetbrains.annotations.NotNull;

public final class LineRead {

    // Attributes
    private long linesRead;
    private final long linesToRead;

    public LineRead(final long linesToRead) {
        linesRead = 0;
        this.linesToRead = linesToRead;
    }
    @SuppressWarnings("unused")
    public LineRead(final long linesRead, final long linesToRead) {
        this(linesToRead);
        this.linesRead = linesRead;
    }

    // Methods
    public long getLinesRead() {
        return linesRead;
    }
    public void setLinesRead(long linesRead) {
        this.linesRead = linesRead;
    }
    public void incrementLinesReadBy(long amount) {
        linesRead += amount;
    }
    public boolean isFinish() {
        return linesRead >= linesToRead;
    }
    public long getLinesToRead() {
        return linesToRead;
    }
    public float getPercentageZeroOne() {
        return (float) linesRead / linesToRead;
    }
    @NotNull
    public String getPercentage() {
        return getPercentage(getPercentageZeroOne());
    }
    @NotNull
    public String getPercentage(final float percentageZeroOne) {
        return String.format("%.1f", percentageZeroOne * 100) + "%";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineRead)) return false;

        LineRead lineRead = (LineRead) o;
        return getLinesRead() == lineRead.getLinesRead() && getLinesToRead() == lineRead.getLinesToRead();
    }
    @Override
    public int hashCode() {
        int result = Long.hashCode(getLinesRead());
        result = 31 * result + Long.hashCode(getLinesToRead());
        return result;
    }
    @Override @NotNull
    public String toString() {
        return getPercentage();
    }
}
