import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class CopyAndWriteArrayList {
    private final Logger log = Logger.getLogger(this.getClass());

    private ReentrantLock lock = new ReentrantLock();
    private int DEFAULT_CAPACITY = 10;
    private volatile int[] innerArray = new int[DEFAULT_CAPACITY];
    private volatile int size = DEFAULT_CAPACITY;
    private AtomicInteger cursor = new AtomicInteger();


    /**
     * Add new element in Array
     * @return Boolean
     */
    public  boolean add (Integer e){
        int[] tempArray = innerArray.clone();
        if (cursor.get() == 0 && size >0){
            tempArray[cursor.get()] = e;
            lock.lock();
            innerArray = tempArray.clone();
            cursor.incrementAndGet();
            lock.unlock();
            return true;
        }
        if (cursor.get() < size) {
            tempArray[cursor.get()] = e;
            lock.lock();
            if (checkArrays(tempArray)) {
                innerArray = tempArray.clone();
                cursor.incrementAndGet();
            } else {
                cursor.decrementAndGet();
            }
            lock.unlock();
            return true;
        } else {
            lock.lock();
            size = size * 2;
            innerArray = Arrays.copyOf(innerArray,size);
            tempArray = innerArray.clone();
            tempArray[cursor.get()] = e;

            if (checkArrays(tempArray)) {
                innerArray = tempArray.clone();
                cursor.incrementAndGet();
            } else {
                cursor.decrementAndGet();
            }
            lock.unlock();
            return true;
        }
    }

    /**
     * Check Array before write new value
     * @param newArray  ...
     * @return boolean
     */
    private  boolean checkArrays(int[] newArray){
        if (newArray.length == 0){
            return true;
        }
        int[] tempArray =  new int[newArray.length-1];
        System.arraycopy(newArray,0,tempArray,0,newArray.length-1);
        return innerArray.equals(tempArray);
    }


    /**
     * return length of Array
     * @return int
     */
    public int length() {
        return cursor.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CopyAndWriteArrayList list = (CopyAndWriteArrayList) o;
        return Arrays.equals(innerArray, list.innerArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(innerArray);
    }
}
