import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FileOperationsTest {

    @Test
    public void testReadFile() throws Exception {
        HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
        FileOperations.getInstance().readFromFile(tasks, "C:/Users/monta/IdeaProjects/ToDoManager/ToDo.txt.txt");
        assertEquals(" File Read failed ", 2, tasks.size());
    }

    @Test
    public void testSaveToFile() throws IOException {
        HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
        FileOperations.getInstance().saveToFile(tasks);
        File f = new File("ToDo.txt.txt");
        assertTrue("ToDo.txt.txt file does not exist",f.exists());

    }
}
