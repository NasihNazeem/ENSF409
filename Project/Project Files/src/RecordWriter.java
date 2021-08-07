import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Used to construct a new record and write it to the file as a byte buffer. To 
 * accomplish this, the user writes data into a DbByteArrayOutputStream, which 
 * is in turn written to the file. This design protects the file from being 
 * corrupted by the user, since the user is never allowed to write directly to 
 * the file. This speeds things up, since the user can build a record (often 
 * times consuming due to object serialization) without blocking other users 
 * from reading and writing records.
 * 
 * @author Derek Hamner
 * @since 1999 - 01 - 01
 */
public class RecordWriter {
    String key;
    DbByteArrayOutputStream out;
    ObjectOutputStream objOut;
    public RecordWriter(String key) {
        this.key = key;
        out = new DbByteArrayOutputStream();
    }
    public String getKey() {
        return key;
    }
    public OutputStream getOutputStream() {
        return out;
    }
    public ObjectOutputStream getObjectOutputStream() throws IOException {
        if (objOut == null) {
        objOut = new ObjectOutputStream(out);
        }
        return objOut;
    }
    public void writeObject(Object o) throws IOException {
        getObjectOutputStream().writeObject(o);
        getObjectOutputStream().flush();
    }
    /**
     * Returns the number of bytes in the data.
     */
    public int getDataLength() {
        return out.size();
    }
    /**
     *  Writes the data out to the stream without re-allocating the buffer.
     */
    public void writeTo(DataOutput str) throws IOException {
        out.writeTo(str);
    }
  }