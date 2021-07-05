import java.io.IOException;
import java.util.*;

public class Storage {

    private static Storage storage = null;
    private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();

    public static Storage getInstance() {

        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    public void addRecord(Task t) {

        tasks.put(Integer.valueOf(t.getTaskId()),t);
    }

    public void removeRecord(int taskId) {

        tasks.remove(Integer.valueOf(taskId));
    }

    public Task getTask(int taskNo) {

        Task t = tasks.get(Integer.valueOf(taskNo));
        return t;
    }

    public Map getAllTasks() {

        return tasks;
    }


    public void saveToFile() throws IOException {

        FileOperations.getInstance().saveToFile(tasks);
    }

    public void readFromFile(String filePath) throws Exception {

        FileOperations.getInstance().readFromFile(tasks, filePath);
    }

    public int[] getTaskCount() {

        int doneCount = 0;
        int inProgress = 0;
        int counts[] = new int[2];
        Iterator i = tasks.entrySet().iterator();

        while (i.hasNext()){

            Map.Entry me =  (Map.Entry)i.next();

            if (((Task)me.getValue()).getStatus() == "DONE"){
                doneCount++;
            } else {
                inProgress++;
            }
        }
        counts[0] = inProgress;
        counts[1] = doneCount;
        return counts;
    }

    public void sortTaskList(int option) {

        ArrayList<Task> p =this.getTaskAsList();

        if (option == 1) {
            Collections.sort(p, Task.compareDate);
        } else {
            Collections.sort(p, Task.compareProject);
        }

        for (Task g : p){

            System.out.println("---------");
            System.out.println(g);
        }
        System.out.println("---------");
    }

    private ArrayList<Task> getTaskAsList() {

        ArrayList<Task> taskList = new ArrayList<Task>();
        Iterator i = tasks.entrySet().iterator();

        while (i.hasNext()){

            Map.Entry me = (Map.Entry)i.next();
            taskList.add((Task)me.getValue());
        }
        return taskList;
    }
}
