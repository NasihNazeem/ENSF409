import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Extends BaseRecordsFile to provide an implementation for the abstract 
 * methods.
 * @author Derek Hamner
 * @since 1999 - 01 - 01
 */
public class RecordsFile extends BaseRecordsFile {
    /**
     * Hashtable which holds the in-memory index. For efficiency, the entire index 
     * is cached in memory. The hashtable maps a key of type String to a RecordHeader.
     */
    protected HashMap<String, RecordHeader> memIndex;    
    /**
     * Creates a new database file.  The initialSize parameter determines the 
     * amount of space which is allocated for the index.  The index can grow 
     * dynamically, but the parameter is provide to increase 
     * efficiency. 
     */
    public RecordsFile(String dbPath, int initialSize) throws IOException, RecordsFileException {
        super(dbPath, initialSize);
        memIndex = new HashMap<>(initialSize);
    }
    /**
     * Opens an existing database and initializes the in-memory index. 
     */
    public RecordsFile(String dbPath, String accessFlags) throws IOException, RecordsFileException {
        super(dbPath, accessFlags);
        int numRecords = readNumRecordsHeader();
        memIndex = new HashMap<>(numRecords);
        for (int i = 0; i < numRecords; i++) {
            String key = readKeyFromIndex(i);
            RecordHeader header = readRecordHeaderFromIndex(i);
            header.setIndexPosition(i);
            memIndex.put(key, header);
        }
    }
    /**
     * Returns an enumeration of all the keys in the database.
     */
    public synchronized Iterator<String> enumerateKeys() {
        return memIndex.keySet().iterator();
    }
    /**
     * Returns the current number of records in the database. 
     */
    public synchronized int getNumRecords() {
        return memIndex.size();
    }
    /**
     * Checks if there is a record belonging to the given key. 
     */
    public synchronized boolean recordExists(String key) {
        return memIndex.containsKey(key);
    }
    /**
     * Maps a key to a record header by looking it up in the in-memory index.
     */
    protected RecordHeader keyToRecordHeader(String key) throws RecordsFileException {
        RecordHeader h = (RecordHeader)memIndex.get(key);
        if (h==null) {
        throw new RecordsFileException("Key not found: " + key);
        } 
        return h;
    }
    /**
     * This method searches the file for free space and then returns a RecordHeader 
     * which uses the space. (O(n) memory accesses)
     */
    protected RecordHeader allocateRecord(String key, int dataLength) throws RecordsFileException, IOException {
        // search for empty space
        RecordHeader newRecord = null;
        for (RecordHeader header : memIndex.values()) {
            if (dataLength <= header.getFreeSpace()) {
                newRecord = header.split();
                writeRecordHeaderToIndex(header);
                break;
            }
        }
        if (newRecord == null) {
            // append record to end of file - grows file to allocate space
            long fp = getFileLength();
            setFileLength(fp + dataLength);
            newRecord = new RecordHeader(fp, dataLength);
        } 
        return newRecord;
    }
    /**
     * Returns the record to which the target file pointer belongs - meaning the specified location
     * in the file is part of the record data of the RecordHeader which is returned.  Returns null if 
     * the location is not part of a record. (O(n) mem accesses)
     */
    protected RecordHeader getRecordAt(long targetFp) throws RecordsFileException {
        for (RecordHeader recordHeader : memIndex.values()) {
            if (targetFp >= recordHeader.getDataPointer() &&
                    targetFp <= recordHeader.getDataPointer() + recordHeader.getDataCapacity()) {
				return recordHeader;
            }
        }
        return null;
    }
    /**
     * Closes the database. 
     */
    public synchronized void close() throws IOException, RecordsFileException {
        try {
        super.close();
        } finally {
        memIndex.clear();
        memIndex = null;
        }
    }
    /**
     * Adds the new record to the in-memory index and calls the super class add
     * the index entry to the file. 
     */
    protected void addEntryToIndex(String key, RecordHeader newRecord, int currentNumRecords) throws IOException, RecordsFileException {
        super.addEntryToIndex(key, newRecord, currentNumRecords);
        memIndex.put(key, newRecord);   
    }
    /**
     * Removes the record from the index. Replaces the target with the entry at the 
     * end of the index. 
     */
    protected void deleteEntryFromIndex(String key, RecordHeader header, int currentNumRecords) throws IOException, RecordsFileException {
        super.deleteEntryFromIndex(key, header, currentNumRecords);
        RecordHeader deleted = (RecordHeader)memIndex.remove(key);
    }
}