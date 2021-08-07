/**
 * A simple exception class that indicates that an error has occurred while 
 * performing an operation on the records file.
 */
public class RecordsFileException extends Exception {
    public RecordsFileException (String msg) {
        super(msg);
    }
}