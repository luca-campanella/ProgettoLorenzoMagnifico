package it.polimi.ingsw.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * This is the logger used to write messages on a file, used concurrently with the class {@link Debug}
 */
public class MessageLogger {

    static private FileHandler fileTxt;

    static public void setup(boolean isServer, Level level) throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(level);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        // suppress the logging output to the console
        /*Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        if(isServer) {
            fileTxt = new FileHandler(getClass().getResource() + "/Logs/server" + dateFormat.format(date) +".txt");
        }
        else {
            fileTxt = new FileHandler("/Logs/client" + dateFormat.format(date) + ".txt");
        }
        logger.addHandler(fileTxt);*/
    }
}
