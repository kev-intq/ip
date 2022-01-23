import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Storage {
  public static Storage INSTANCE;
  private String directoryPath = System.getProperty("user.dir") + "/data/";
  private String listFile = "listData.txt";

  public Storage() {
  }

  public static Storage getInstance() {
    INSTANCE = (INSTANCE == null) ? new Storage() : INSTANCE;
    return INSTANCE;
  }

  public boolean saveList(ArrayList<Task> taskList) throws IOException {
    boolean success = false;
    File file = new File(this.directoryPath + this.listFile);
    file.getParentFile().mkdirs();
    file.createNewFile();

    FileWriter writer = new FileWriter(this.directoryPath + this.listFile);
    BufferedWriter bufferedWriter = new BufferedWriter(writer);

    for (Task t : taskList) {
      bufferedWriter.write(t.getSaveDescription());
      bufferedWriter.newLine();
    }

    bufferedWriter.close();
    success = true;
    return success;
  }



  public boolean loadList(ArrayList<Task> taskList) {
    boolean success = false;
    try {
      File file = new File(this.directoryPath + this.listFile);
      file.getParentFile().mkdirs();
      file.createNewFile();

      FileReader reader = new FileReader(this.directoryPath + this.listFile);
      BufferedReader bufferedReader = new BufferedReader(reader);
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        String[] task = line.split(" \\| ");
        switch (task[0]) {
          case "Todo":
            Todo todoTask = new Todo(task[2]);
            todoTask.setStatus((task[1].equals("1") ? true : false));
            taskList.add(todoTask);
            break;
          case "Deadline":
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(task[3], dateTimeFormatter);
            Deadline deadlineTask = new Deadline(task[2], dateTime);
            deadlineTask.setStatus((task[1].equals("1") ? true : false));
            taskList.add(deadlineTask);
            break;
          case "Event":
            Event eventTask = new Event(task[2], task[3]);
            eventTask.setStatus((task[1].equals("1") ? true : false));
            taskList.add(eventTask);
            break;
        }
      }

      bufferedReader.close();
    } catch (DateTimeParseException e) {
      System.out.println("Invalid save file format. Save file may have been edited or corrupted.");
    } catch (IOException e) {
      System.out.println("Unable to load list." +
          "Please check if you have permission to read from files in the following directory: " +
          directoryPath);
    }
    return success;
  }

  public String getDirectoryPath() {
    return directoryPath;
  }
}
