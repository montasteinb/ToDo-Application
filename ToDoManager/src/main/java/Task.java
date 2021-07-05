import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Task implements Serializable {
    private int taskId;
    private String taskName;
    private String projectName;
    private String status;
    private Date taskDate;
    private static int count = 0;

    public Task(String taskName, String projectName, String status, Date taskDate) {
        this.taskName = taskName;
        this.projectName = projectName;
        this.status = status;
        this.taskDate = taskDate;
        this.taskId = getCount();
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getStatus() {
        return status;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public static int getCount() {
        return ++ count;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public static Comparator<Task> compareProject = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            String projectName1 = t1.getProjectName();
            String projectName2 = t2.getProjectName();

            return projectName1.compareTo(projectName2);
        }
    };

    public static Comparator<Task> compareDate = new Comparator<Task>() {
        @Override
        public int compare(Task d1, Task d2) {
            Date date1 = d1.getTaskDate();
            Date date2 = d2.getTaskDate();

            return date1.compareTo(date2);
        }
    };

    public String toString() {
        return  "Task id:           " + this.taskId
                +"\nTask Name:      " + this.taskName
                +"\nProject Name:  " + this.projectName
                +"\nStatus:         " + this.status
                +"\nDate:           " + this.taskDate;
    }

}

