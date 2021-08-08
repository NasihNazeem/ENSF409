/**
 * A simple exception class that indicates that an error has occurred while 
 * performing an operation on the records file.
 * 
 * @author Derek Hamner
 * @since 1999 - 01 - 01
 */
public class RecordsFileException extends Exception {
    public RecordsFileException (String msg) {
        super(msg);
    }
}