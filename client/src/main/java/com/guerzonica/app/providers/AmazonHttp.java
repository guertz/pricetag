package com.guerzonica.app.providers;

import com.guerzonica.app.models.amazon.AmazonRequest;

public class AmazonHttp {
    public static void makeRequest() {
        AmazonRequest request = new AmazonRequest();
            request.setItedId("B072K2TQX4");
            request.setResponseGroup("Images,ItemAttributes,OfferFull");

        try {
            System.out.println(request.getRequestUri());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}