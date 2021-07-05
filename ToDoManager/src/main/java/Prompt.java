import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Prompt {

    private int option;
    private int count = 0;
    private static Prompt prompt = null;

    public static Prompt getInstance() {

        if (prompt == null) {
            prompt = new Prompt();
        }
        return prompt;
    }

    public void displayOption() {

        System.out.println("----------------------------------------------------");
        System.out.println(" Welcome to ToDo.txt application!");

        while (true) {
            try {
                this.displayOptions();
                option = scanInput();

                switch (option) {
                    case 1:
                        this.taskList();
                        break;
                    case 2:
                        this.addTask();
                        System.out.println("Task added successfully!");
                        break;
                    case 3:
                        this.editTask();
                        break;
                    case 4:
                        this.readFromFile();
                        break;
                    case 5:
                        this.saveExitTask();
                        break;
                    default:
                        System.out.println("Please enter correct option");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numbers only");
                continue;
            }
        }
    }

    public int scanInput() {
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        return i;
    }

    public String scanString() throws Exception {
        String line = null;
        try {
            Scanner sc = new Scanner(System.in);
            line = sc.nextLine();
            validateString(line);
        } catch (NumberFormatException e) {
            System.out.println("Please enter text only");
        }
        return line;
    }

    private void validateString(String s) throws Exception {
        try {
            Integer.parseInt(s);
            throw new Exception();
        } catch (Exception ignored) {

        }
    }

    void displayOptions() {

        int[] counts = Storage.getInstance().getTaskCount();
        System.out.println("You have "+counts[0]+" tasks ToDo.txt and "+counts[1]+" tasks are done! ");
        System.out.println("----------------------------------------------------");
        System.out.println("Pick an option: ");
        System.out.println("1 - Show Task List ");
        System.out.println("2 - Add New Task ");
        System.out.println("3 - Edit Task (Update, mark as Done, Remove)");
        System.out.println("4 - Load tasks from existing file");
        System.out.println("5 - Save and Quit ");
        System.out.println(" ");
        System.out.println("----------------------------------------------------");
        System.out.println("Enter your option: ");
    }

    private void addTask() throws Exception {

        try {
            System.out.println("Enter the Task Name: ");
            String tname = scanString();
            System.out.println("Enter the Project Name: ");
            String tpname = scanString();
            System.out.println("Enter the Status of the task: ");
            System.out.println("1 - ON-GOING");
            System.out.println("2 - DONE");
            String tstatus = null;
            try {
                int tstatusOption = scanInput();
                if(tstatusOption == 1) {
                    tstatus = "ON-GOING";
                } else if(tstatusOption == 2) {
                    tstatus = "DONE";
                } else {
                    System.out.println("Please enter valid Input");
                    this.addTask();
                }
            } catch (Exception e) {
                System.out.println("Invalid Input. Please enter the correct details");
                this.addTask();
            }
            System.out.println("Enter the TaskDate(dd-mm-yyyy) : ");
            String tdate = scanString();
            Date inputDate = this.validateDate(tdate);
            Task s = new Task(tname, tpname, tstatus, inputDate);
            Storage.getInstance().addRecord(s);
        } catch (Exception e) {
            System.out.println("Invalid Input. Please try again");
            count++;

            if (count == 3){
                System.out.println("Invalid Input for 3 times. EXIT!");
                this.displayOption();
            } else {
                this.addTask();
            }
        }
    }

    private void editTask() throws Exception {

        System.out.println("Enter the taskId you want to edit");
        int taskNo = scanInput();
        System.out.println("Choose an option you want to perform");
        System.out.println("1 - Update Task");
        System.out.println("2 - Mark Task as done");
        System.out.println("3 - Remove Task");
        option = scanInput();

        switch(option) {

            case 1:
                this.updateTask(taskNo);
                System.out.println("Task updated successfully");
                break;
            case 2:
                this.markTaskDone(taskNo);
                System.out.println("Task marked as DONE successfully");
                break;
            case 3:
                this.removeTask(taskNo);
                System.out.println("Task deleted successfully");
                break;
            default:
                System.out.println("Please enter correct option");
                this.editTask();
        }
    }

    private void updateTask(int taskNo) throws Exception {

        Task t = Storage.getInstance().getTask(taskNo);
        System.out.println(t);
        System.out.println("Select which field you want to update");
        System.out.println("1 - Task Name");
        System.out.println("2 - Project Name");
        System.out.println("3 - Status");
        System.out.println("4 - Date");
        int option = scanInput();

        switch (option) {

            case 1:
                System.out.println("Enter the new Task Name");
                String tname = scanString();
                t.setTaskName(tname);
                break;
            case 2:
                System.out.println("Enter the new Project Name");
                String tpname = scanString();
                t.setProjectName(tpname);
                break;
            case 3:
                System.out.println("Enter the new Status of the task");
                String tstatus = scanString();
                t.setStatus(tstatus);
                break;
            case 4:
                System.out.println("Enter the new Date");
                String tdate = scanString();
                Date date = this.validateDate(tdate);
                t.setTaskDate(date);
                break;
            default:
                System.out.println("Please enter correct option");
                this.editTask();
        }
    }
    private void markTaskDone(int taskNo) {

        Task t = Storage.getInstance().getTask(taskNo);
        t.setStatus("DONE");
    }

    private void removeTask(int taskNo) {

        Storage.getInstance().removeRecord(taskNo);
    }

    private void saveExitTask() throws IOException {

        Storage.getInstance().saveToFile();
        System.out.println("Successfully saved the tasks into the file ToDo.txt");
        // Exiting the program
        System.exit(0);
    }

    private void taskList() {

        System.out.println("Please Enter your choice");
        System.out.println("1.Sort based on Date");
        System.out.println("2.Sort based on Title");
        int option = this.scanInput();
        if (option == 1){
            Storage.getInstance().sortTaskList(option);
        } else if(option == 2){
            Storage.getInstance().sortTaskList(option);
        } else {
            System.out.println("Invalid input! Please try again.");
        }
    }

    public void readFromFile() throws Exception {

        System.out.println("Please enter the path of the file to load");
        String filePath = this.scanString();
        Storage.getInstance().readFromFile(filePath);
        System.out.println("Successfully loaded tasks from the file: "+filePath);
    }

    private Date validateDate(String date) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date inputDate ;
        // Get current date
        try {
            inputDate = formatter.parse(date);
            Date today = formatter.parse(formatter.format(new Date()));
            if (inputDate.compareTo(today) < 0) {
                throw new Exception();
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            throw e;
        } catch (Exception e) {
            System.out.println("Date Entered should be greater than today");
            throw e;
        }
        return inputDate;
    }
}

