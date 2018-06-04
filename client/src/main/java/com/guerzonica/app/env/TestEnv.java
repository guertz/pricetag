package com.guerzonica.app.env;

public class TestEnv {
    public static final String AWSSecretKey   = "";
    public static final String AWSAccessKeyId = "";
    public static final String AssociateTag   = "";
    public static final String AWSHost        = "";

    public static final String WSLocator      = "ws://localhost:8001/"; 

    public static final String DataPath       = ":memory:";

    private static String argPath;

    private static String getPathOrDefault() {
        if(argPath != null)
            return argPath;

        return DataPath;
    }

    public static void setPath(String path) {
        if(argPath != null)
            System.err.println("Path was: " + argPath);
            
        argPath = path;
    }

    public static String getPath() {
        System.out.println("Current path is: " + getPathOrDefault());

        return getPathOrDefault();
    }

}