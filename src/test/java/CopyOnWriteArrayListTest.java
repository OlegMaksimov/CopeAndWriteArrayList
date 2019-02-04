import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CopyOnWriteArrayListTest {

    @org.junit.jupiter.api.Test
    void add() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        Assertions.assertTrue(list.add(2));
    }

    @Test
    void get(){
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        Assertions.assertTrue(list.add(2));
        Assertions.assertEquals(2,list.get(10));
    }

    @Test
    void length() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        int size = 15;
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        Assertions.assertEquals(size, list.size());
    }

    @Test
    void equals() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        CopyOnWriteArrayList list2 = new CopyOnWriteArrayList();
        int size = 15;
        for (int i = 0; i < size; i++) {
            list.add(i);
            list2.add(i);
        }
        Assertions.assertEquals(list, list2);
    }

    @Test
    void racingThread() throws InterruptedException {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            final Random random = new Random();
            service.submit(()->{
                list.add(random.nextInt());
            });
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        Assertions.assertEquals(110, list.size());
        System.out.println(list.toString());
    }

    @Test
    void getIndexOutofBounds(){
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        Executable executable = ()->list.add(100,1);
        Assertions.assertThrows(IndexOutOfBoundsException.class, executable);
    }


    @Test
    void addNewEllementInCurrentIndex(){
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        list.add(1,5);
        Assertions.assertEquals(5,list.get(1));
    }

}