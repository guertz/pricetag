package com.guerzonica.app.env;

/**
 * The TestEnvironment with all the defaults value content
 * 
 * @author Matteo Guerzoni
 */
public class TestEnv {

    /** AWS Secret key - for Amazon Products API */
    public static final String AWSSecretKey   = "";
    /** AWS Access key - for Amazon Products API */
    public static final String AWSAccessKeyId = "";
    /** Associate Tag - for Amazon Products API */
    public static final String AssociateTag   = "";
    /** AWS Host - for Amazon Products API */
    public static final String AWSHost        = "";

    /** WebSocket Server URI - for Peers communication */
    public static final String WSLocator      = "ws://localhost:8001/"; 

    /** Database storage folder path (default volatile) - SQLite */
    public static final String DataPath       = ":memory:";

    /** The database path as a file (consistant) - SQLite */
    private static String argPath;

    /** 
     * Retrive the database path.
     * Takes care of the default case as well
     * 
     * @return The SQLite Database File path
     */
    private static String getPathOrDefault() {
        if(argPath != null)
            return argPath;

        return DataPath;
    }

    /** 
     * Specify a different database path.
     * 
     * @param path The SQLite Database File path
     */
    public static void setPath(String path) {
        if(argPath != null)
            System.err.println("Path was: " + argPath);
            
        argPath = path;
    }

    /** 
     * Retrive the database path and log the result.
     * 
     * @return The SQLite Database File path
     */
    public static String getPath() {
        System.out.println("Current path is: " + getPathOrDefault());

        return getPathOrDefault();
    }

}