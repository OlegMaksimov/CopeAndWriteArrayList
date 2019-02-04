import org.apache.log4j.Logger;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class CopyOnWriteArrayList extends AbstractList<Integer> {
    private final Logger log = Logger.getLogger(this.getClass());

    private final ReentrantLock lock = new ReentrantLock();
    private int DEFAULT_CAPACITY = 1;
    private volatile int[] innerArray = new int[DEFAULT_CAPACITY];
    private volatile AtomicInteger cursor = new AtomicInteger();

    public CopyOnWriteArrayList() {
    }

    @Override
    public int size() {
        return innerArray.length;
    }

    /**
     *
     * @param size count element оf array
     */
    public CopyOnWriteArrayList(int size) {
        innerArray = new int[size];
    }

    /**
     * Add new element in Array
     * @return Boolean
     */
    public boolean add (Integer e){
        Lock lock = this.lock;
        lock.lock();
        try {
            int length = innerArray.length;
            int[] copy = new int[length + 1];
            System.arraycopy(innerArray, 0, copy, 0, length - 1);
            copy[length] = e;

            innerArray = copy;
            return true;
        } finally {
          lock.unlock();
        }
    }

    @Override
    public void add(int index, Integer element) {
        if (index > innerArray.length || index < 0) {
            throw new IndexOutOfBoundsException("This index is not valid.");
        }
        Lock lock = this.lock;
        lock.lock();
        try{
            int length = innerArray.length;
            int[] copy = new int[length + 1];
            System.arraycopy(innerArray, 0, copy, 0, index-1);
            copy[index] = element;
            System.arraycopy(innerArray, index+1, copy, index, index-1);
            innerArray = copy;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public Integer get(int index) {
        return innerArray[index];
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
        CopyOnWriteArrayList tempArray =  new CopyOnWriteArrayList(cursor.get());
        CopyOnWriteArrayList tempInnerArray =  new CopyOnWriteArrayList(cursor.get());
        for (int i = 0; i < cursor.get(); i++) {
            tempArray.add(newArray[i]);
            tempInnerArray.add(innerArray[i]);
        }
        return tempArray.equals(tempInnerArray);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CopyOnWriteArrayList list = (CopyOnWriteArrayList) o;
        return Arrays.equals(innerArray, list.innerArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(innerArray);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < cursor.get(); i++) {
            buffer.append(innerArray[i]+",");
        }
        return buffer.toString();
    }
}
