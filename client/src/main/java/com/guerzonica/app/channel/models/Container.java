package com.guerzonica.app.channel.models;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Container {

    protected static Gson _ser = new Gson();

    public static Container fromJson(String blob) {
        return _ser.fromJson(blob, Container.class);
    }

    public static String toJson(Container c) {
        return _ser.toJson(c, Container.class);
    }

    @SerializedName(value="uri")
    private String uri;

    @SerializedName(value="rid")
    private String rid; // request identifier


    public Container(String u, String r) {
        this.uri = u;
        this.rid = r;
    }

    public Container(String u) {
        this(u, UUID.randomUUID().toString());
    }

    public String getRid() {
        return this.rid;
    }

    public String getUri() {
        return this.uri;
    }

}
