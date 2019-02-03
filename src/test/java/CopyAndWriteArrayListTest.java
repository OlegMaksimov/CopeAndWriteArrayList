import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}