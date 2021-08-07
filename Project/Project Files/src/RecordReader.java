import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;

/**
 * Used to read data from the record. It is a utility class that makes record 
 * data more accessible than it is in its raw byte buffer form.
 * 
 * @author Derek Hamner
 * @since 1999 - 01 - 01
 */
public class RecordReader {
    String key;
    byte[] data;
    ByteArrayInputStream in;
    ObjectInputStream objIn;
    public RecordReader(String key, byte[] data) {
        this.key = key;
        this.data = data;
        in = new ByteArrayInputStream(data);
    }
    public String getKey() {
        return key;
    }
    public byte[] getData() {
        return data;
    }
    public InputStream getInputStream() throws IOException {
        return in;
    }
    public ObjectInputStream getObjectInputStream() throws IOException {
        if (objIn == null) {
        objIn = new ObjectInputStream(in);
        }
        return objIn;
    }
    /**
     * Reads the next object in the record using an ObjectInputStream.
     */
    public Object readObject() throws IOException, OptionalDataException, ClassNotFoundException {
        return getObjectInputStream().readObject();
    }
}