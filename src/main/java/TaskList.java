import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

public class TaskList {
  private Storage storage;
  private ArrayList<Task> taskList;

  public TaskList(Storage storage) {
    this.taskList = new ArrayList<>();
    this.storage = storage;
    storage.loadList(taskList);
  }

  public void addTask(Task task) throws IOException {
    taskList.add(task);
    storage.saveList(taskList);
  }

  public int getTaskListSize() {
    return taskList.size();
  }

  public Task getTask(int i) {
    return taskList.get(i);
  }

  public void removeTask(int i) throws IOException {
    taskList.remove(i);
    storage.saveList(taskList);
  }

  public void markTask(int i) throws IOException {
    taskList.get(i).setStatus(true);
    storage.saveList(taskList);
  }

  public void unmarkTask(int i) throws IOException {
    taskList.get(i).setStatus(false);
    storage.saveList(taskList);
  }

  public void clearList() throws IOException {
    taskList = new ArrayList<>();
    storage.saveList(taskList);
  }

  public String displayList() {
    String output = "";
    if (taskList.size() == 0) {
      output = "LUMU: Your list is empty!";
    } else {
      for (int i = 0; i < taskList.size(); i++) {
        Task currTask = taskList.get(i);
        output = output + String.valueOf(i + 1) + ". " + currTask.toString() + "\n";
      }
    }
    return output.trim();
  }
}
