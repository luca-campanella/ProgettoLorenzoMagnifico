package it.polimi.ingsw.utils;

/**
 * This class is useful to print debug messages. It is a singleton-like (the method setLevel changes the instance) and should be initialized before being used.
 * It contains three levels of debugging: normal (lv 2), only errors (lv 1), nothing (lv 0). Every message with a level lower is printed.
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
	 * Everything is printed (debug messages and errors)
	 */
	public static final int LEVEL_NORMAL = 2;
	
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
		if(debLevel < LEVEL_NOTHING || debLevel > LEVEL_NORMAL)
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
		if(debLevel < LEVEL_NOTHING || debLevel > LEVEL_NORMAL)
		{
			throw new IllegalArgumentException("Level not recognised");
		}
		internalInstance = new Debug(debLevel);
	}
	
	/**
	 * prints the desired debug message if the level is set to LEVEL_NORMAL
	 * @param text the message to be printed
	 */
	public static void printDebug(String text)
	{
		if(getInstance().level >= LEVEL_NORMAL)
			System.out.println("--- DEBUG: " + text);
	}
	
    /**
     * prints the message and stack trace of the exception
     * @param e the exception that should be visualized
     */
    public static void printDebug(Exception e)
    {
        if(getInstance().level >= LEVEL_NORMAL) {
            System.err.println("--- DEBUG-exception: ");
            System.err.println("--- Exception Message: " + e.getMessage());
            System.err.println("--- Stack Trace: ");
            e.printStackTrace();
        }
    }
    
    /**
     * prints the message and stack trace of the exception, plus a message chosen by the user of the function
     * @param e the exception that should be visualized
     */
    public static void printDebug(String text, Exception e)
    {
        if(getInstance().level >= LEVEL_NORMAL) {
            System.err.println("--- DEBUG-exception: " + text);
            System.err.println("--- Exception Message: " + e.getMessage());
            System.err.println("--- Stack Trace: ");
            e.printStackTrace();
        }
    }
    
	/**
	 * prints the desired debug message if the level is set to LEVEL_ERRORS
	 * @param text the message to be printed
	 */
	public static void printError(String text)
	{
		if(getInstance().level >= LEVEL_ERRORS)
			System.err.println("--- ERROR: " + text);
	}
	
	/**
	 * prints the message and stack trace of the exception
	 * @param e the exception that should be visualized
	 */
	public static void printError(Exception e)
	{
		if(getInstance().level >= LEVEL_ERRORS) {
			System.err.println("--- ERROR-exception: ");
			System.err.println("--- Message: " + e.getMessage());
			System.err.println("--- Stack Trace: ");
			e.printStackTrace();
		}
	}
    
    /**
     * prints the message and stack trace of the exception, plus a message chosen by the user of the function
     * @param e the exception that should be visualized
     */
    public static void printError(String text, Exception e)
    {
        if(getInstance().level >= LEVEL_ERRORS) {
            System.err.println("--- ERROR-exception: " + text);
            System.err.println("--- Message: " + e.getMessage());
            System.err.println("--- Stack Trace: ");
            e.printStackTrace();
        }
    }
}
