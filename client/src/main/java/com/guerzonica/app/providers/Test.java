package com.guerzonica.app.providers;

public class Test {
    public static void run() {
        try {
            Amazon request = new Amazon();
                request.setItedId("B072K2TQX4");
                
            System.out.println(request.getRequestUri().toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}