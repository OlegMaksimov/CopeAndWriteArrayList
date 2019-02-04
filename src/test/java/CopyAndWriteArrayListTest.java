import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CopyAndWriteArrayListTest {

    @org.junit.jupiter.api.Test
    void add() {
        CopyAndWriteArrayList list = new CopyAndWriteArrayList();
        Assertions.assertTrue(list.add(2));
    }

    @Test
    void length() {
        CopyAndWriteArrayList list = new CopyAndWriteArrayList();
        int size = 15;
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        Assertions.assertEquals(size, list.length());
    }

    @Test
    void equals() {
        CopyAndWriteArrayList list = new CopyAndWriteArrayList();
        CopyAndWriteArrayList list2 = new CopyAndWriteArrayList();
        int size = 15;
        for (int i = 0; i < size; i++) {
            list.add(i);
            list2.add(i);
        }
        Assertions.assertEquals(list, list2);
    }

    @Test
    void racingThread() throws InterruptedException {
        CopyAndWriteArrayList list = new CopyAndWriteArrayList();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            service.submit(()->{
                list.add(random.nextInt());
            });
        }
        service.awaitTermination(1, TimeUnit.SECONDS);
        Assertions.assertEquals(100,list.length());
        System.out.println(list.toString());
    }

}