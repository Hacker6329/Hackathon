package it.unimore.studenti.hackathon.exception;

public class CSVParseException extends RuntimeException {
    public CSVParseException(String message) {
        super(message);
    }
  public CSVParseException(String message, Throwable e) {
    super(message, e);
  }
}
