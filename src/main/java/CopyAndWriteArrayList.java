import org.apache.log4j.Logger;

/**
 *
 */
public class CopyAndWriteArrayList {
    private final Logger log = Logger.getLogger(this.getClass());
    private int DEFAULT_CAPACITY = 10;
    private volatile int[] innerArray = new int[DEFAULT_CAPACITY];

    /**
     * Add new element in Array
     * @return
     */
    public synchronized boolean  add (){
        boolean result = false;
        return result;
    }

}
