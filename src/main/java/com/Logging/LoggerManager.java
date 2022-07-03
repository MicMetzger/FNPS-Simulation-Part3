package main.java.com.Logging;

import java.io.*;
import java.text.*;
import java.util.*;
import main.java.com.events.task.*;
import main.java.com.individuals.*;

/* 
public class LogManager {
  private static final LogManager manager;
  
  
} */



public class LoggerManager implements MessageReceiver {
  // Eager Instantiation of LoggerManager
  private static volatile LoggerManager       loggerManager = new LoggerManager();
  private static          String              LOGGERNAME;
  public static final     String              LOGFOLD       = "LOGS/";
  public static final     String              LOGFILE       = "LoggerManager-";
  public static           String              LOGINPUTPATH  = "";
  public static           SimpleDateFormat    DATEFORMAT    = new SimpleDateFormat("yyyy-MM-dd");
  // private static          List<Log>           LOGS          = new ArrayList<>();
  private static          int                 LOG_COUNT     = 0;
  protected static        int                 ID_TAG        = 0;
  protected static        Map<String, Logger> listeners;


  private LoggerManager() {
    // LOGS          = new ArrayList<Log>();
    listeners     = new HashMap<String, Logger>();
    loggerManager = this;
    LOGGERNAME    = "GenericLogger";
  }


  private LoggerManager(String name) {
    // LOGS          = new ArrayList<Log>();
    listeners     = new HashMap<String, Logger>();
    loggerManager = this;
    LOGGERNAME    = name;
  }


  public static LoggerManager getInstance(/* Class<?> clazz */ /* String File */) {
    if (loggerManager == null) {
      synchronized (LoggerManager.class) {
        if (loggerManager == null) {
          loggerManager = new LoggerManager(/* File */);
        }
      }
    }
    return loggerManager;
  }


  public Logger getLogger(Object obj) {
    if (listeners.containsKey(obj.getClass().getCanonicalName())) {
      return listeners.get(obj.getClass().getCanonicalName());
    } else {
      Logger listener = new Logger(obj.getClass().getCanonicalName());
      listeners.put(obj.getClass().getCanonicalName(), listener);
      return listener;
    }
  }


  public Logger getLogger(String name) {
    if (listeners.containsKey(name)) {
      return listeners.get(name);
    } else {
      Logger listener = new Logger(name);
      listeners.put(name, listener);
      return listener;
    }
  }


  public interface ILogger {
    void info(String msg);
    void info(EventLog event);
  }



  public final class Logger implements ILogger {
    private String    name;
    private List<Log> logs;


    public Logger(String name) {
      this.name = name;
      this.logs = new ArrayList<Log>();
    }


    public void info(String msg) {
      System.out.println("[@ " + name + "]: " + msg);

      Log log = new Log(msg);
      logs.add(log);
    }


    public void info(EventLog event) {
      System.out.println("[@ " + name + "]: " + event.toString());

      Log log = new Log(event.toString());
      logs.add(log);
    }

    
    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      for (Log log : logs) {
        builder.append(log.toString());
      }
      return builder.toString();
    }
  }


  /**
   * Save.
   *
   * @throws IOException the io exception
   */
  public static void SAVE() throws IOException {
    File file = new File("");
    if (!new File(LOGFOLD).exists()) {
      var e = new File(LOGFOLD).mkdir();
    }
    File LOGPATH = new File(LOGFOLD + DATEFORMAT.format(new Date()) + "/");
    if (!LOGPATH.exists()) {
      var e = LOGPATH.mkdir();
    }

    if (LOGINPUTPATH.equals("")) {
      for (int i = 0; i < 101; i++) {
        if (i == 101) {
          System.out.println("LoggerManager: Error: Unable to save logs.");
          return;
        }
        file = new File(LOGFOLD + DATEFORMAT.format(new Date()) + "/" + LOGFILE + i + ".txt");

        if (!file.exists()) {
          var e = file.createNewFile();
          break;
        }
      }
    } else {
      file = new File(LOGINPUTPATH);
    }

    try (FileWriter output = new FileWriter(file, true /* StandardCharsets.UTF_8 */)) {
      for (Logger listener : listeners.values()) {
        try {
          output.write(listener.toString());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      };
      // output.write(data);
    } catch (IOException ignored) {
      System.out.println("Logger: Error: Unable to save logs.");
    }
  }



/*   public static void filePathALT(String path) {
    LOGINPUTPATH = path;
  } */


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MessageReceiver Overrides ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  @Override
  public void sendMessage(String message) {
    // log.write(message + "\n");
  }


  @Override
  public void update(String watched, Object event) {
    if (event instanceof EmployeeTask) {
      State e = (State) event;
      // log("Event: " + e.getName() + " " + e.getStatus().getName());
    }
  }


  @Override
  public ReceiverType getType() {
    return null;
  }


  @Override
  public void setType(ReceiverType type) {

  }

}

