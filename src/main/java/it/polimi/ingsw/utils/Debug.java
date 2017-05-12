package it.polimi.ingsw.utils;

import java.io.PrintStream;

/**
 * This class is useful to print debug messages. It is a singleton-like (the method setLevel changes the instance) and should be initialized before being used.
 * It contains four levels of debugging: verbose (lv 3), normal (lv 2), only errors (lv 1), nothing (lv 0). Every message with a level lower is printed.
 * This class should not be used to print normal messages to the user, just debug and error messages.
 */
public class Debug {
	
	/**
	 * Nothing is printed
	 */
	public static final int LEVEL_NOTHING = 0;
	
	/**
	 * Only errors are printed
	 */
	public static final int LEVEL_ERRORS = 1;
	
	/**
	 * Debug messages and errors are printed
	 */
	public static final int LEVEL_NORMAL = 2;

	/**
	 * Everything is printed
	 */
	public static final int LEVEL_VERBOSE = 3;
	
	/**
	 * Singleton instance
	 */
	private static Debug internalInstance = null;
	
	/**
	 * current level, by default set to nothing
	 */
	private final int level;
	
	/**
	 * private constructor to create the singleton instance
	 * @param debLevel the desired level
	 */
	private Debug(int debLevel)
	{
		this.level = debLevel;
	}
	
	/**
	 * public method to create the instance of the singleton
	 * @param debLevel the desired level
	 * @throws IllegalArgumentException if the level is not one of the decided ones
	 * @return the instance
	 */
	public static Debug instance(int debLevel) throws IllegalArgumentException
	{
		if(debLevel < LEVEL_NOTHING || debLevel > LEVEL_VERBOSE)
		{
			throw new IllegalArgumentException("Level not recognised");
		}
		
		if(internalInstance == null)
			internalInstance = new Debug(debLevel);

		return internalInstance;
	}
	
	/**
	 * Method to get the singleton instance, if not already initialized it's created with the default level LEVEL_NOTHING
	 * @return the singleton instance
	 */
	public static Debug getInstance()
	{
		if(internalInstance == null)
			internalInstance = new Debug(LEVEL_NOTHING);

		return internalInstance;
	}
	
	/**
	 * setter to change debug level, may be useful to have a certain level of debug in one part of code and a different one in a different part
	 * @param debLevel the new desired level
	 * @throws IllegalArgumentException if the level is not one of the decided ones
	 */
	public static void setLevel(int debLevel) throws IllegalArgumentException
	{
		if(debLevel < LEVEL_NOTHING || debLevel > LEVEL_VERBOSE)
		{
			throw new IllegalArgumentException("Level not recognised");
		}
		internalInstance = new Debug(debLevel);
	}


	/**
	 * internal private function to decorate the text of a message and print it or not in relation with the level
	 * @param text
	 * @param header
	 * @param printStream
	 * @param desiredLv
	 */
	private static void printText(String text, String header, PrintStream printStream, int desiredLv)
	{
		if(getInstance().level >= desiredLv)
			printStream.println("---" + header + ": " + text);
	}

	/**
	 * * internal private function to decorate the exception and print it or not in relation with the level
	 * @param e
	 * @param header
	 * @param printStream
	 * @param desiredLv
	 */
	private static void printException(Exception e, String header, PrintStream printStream, int desiredLv)
	{
		if(getInstance().level >= desiredLv) {
			printStream.println("---" + header + "exception: ");
			printStream.println("--- Exception Message: " + e.getMessage());
			printStream.println("--- Stack Trace: ");
		}
	}

	/**
	 * internal private function to decorate the exception and the relative text and print it or not in relation with the level
	 * @param e
	 * @param text
	 * @param header
	 * @param printStream
	 * @param desiredLv
	 */
	private static void printException(Exception e, String text, String header, PrintStream printStream, int desiredLv)
	{
		if(getInstance().level >= desiredLv) {
			printStream.println("---" + header + "exception: ");
			printStream.println("--- Added message: " + text);
			printStream.println("--- Exception Message: " + e.getMessage());
			printStream.println("--- Stack Trace: ");
		}
	}

	/**
	 * prints the desired debug message if the level is set to LEVEL_NORMAL
	 * @param text the message to be printed
	 */
	public static void printVerbose(String text)
	{
		printText(text, "DEBUG-VERB", System.out, LEVEL_VERBOSE);
	}

	/**
	 * prints the message and stack trace of the exception
	 * @param e the exception that should be visualized
	 */
	public static void printVerbose(Exception e)
	{
		printException(e, "DEBUG-VERB", System.out, LEVEL_VERBOSE);
	}

	/**
	 * prints the message and stack trace of the exception, plus a message chosen by the user of the function
	 * @param e the exception that should be visualized
	 */
	public static void printVerbose(String text, Exception e)
	{
		printException(e, text, "DEBUG-VERB", System.out, LEVEL_VERBOSE);
	}

	/**
	 * prints the desired debug message if the level is set to LEVEL_NORMAL
	 * @param text the message to be printed
	 */
	public static void printDebug(String text)
	{
		printText(text, "DEBUG", System.out, LEVEL_NORMAL);
	}
	
    /**
     * prints the message and stack trace of the exception
     * @param e the exception that should be visualized
     */
    public static void printDebug(Exception e)
    {
        printException(e, "DEBUG", System.out, LEVEL_NORMAL);
    }
    
    /**
     * prints the message and stack trace of the exception, plus a message chosen by the user of the function
     * @param e the exception that should be visualized
     */
    public static void printDebug(String text, Exception e)
    {
		printException(e, text, "DEBUG", System.out, LEVEL_NORMAL);
    }
    
	/**
	 * prints the desired debug message if the level is set to LEVEL_ERRORS
	 * @param text the message to be printed
	 */
	public static void printError(String text)
	{
		printText(text, "ERROR", System.err, LEVEL_ERRORS);
	}
	
	/**
	 * prints the message and stack trace of the exception
	 * @param e the exception that should be visualized
	 */
	public static void printError(Exception e)
	{
		printException(e, "ERROR", System.err, LEVEL_ERRORS);
	}
    
    /**
     * prints the message and stack trace of the exception, plus a message chosen by the user of the function
     * @param e the exception that should be visualized
     */
    public static void printError(String text, Exception e)
    {
		printException(e, text, "ERROR", System.err, LEVEL_ERRORS);
    }
}
