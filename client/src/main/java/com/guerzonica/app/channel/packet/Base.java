package com.guerzonica.app.channel.packet;

import com.google.gson.annotations.SerializedName;

public class Base {

    @SerializedName(value="uri")
    private String uri;

    @SerializedName(value="rid")
    private String rid; // request identifier

    public Base(String u, String r) {
        this.uri = u;
        this.rid = r;
    }
    
    public String getRid() {
        return this.rid;
    }

    public String getUri() {
        return this.uri;
    }
    
}