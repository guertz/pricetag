package com.guerzonica.app.models.amazon;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.guerzonica.app.providers.env.Env;

public class AmazonRequest {

    // uri params
    private static final String protocol     = "http";
    private static final String AWS_Host     = Env.AWSHost;
    private static final String AWS_endpoing = "/onca/xml";

    // api params
    private static final String Version        = "2013-08-01";

    // request params
    private String Service       = "AWSECommerceService";
    private String Operation     = "ItemLookup";
    private String ResponseGroup = "Small";

    // item params
    private String Timestamp;
    private String ItemId;
    // private String ItemType  = "ASIN";

    public AmazonRequest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Instant epoch = timestamp.toInstant();
        StringBuilder parse = new StringBuilder(epoch.toString());
            parse.replace(parse.length() - 5, parse.length() -1, "");

        this.Timestamp = parse.toString();
    }

    private static String e(String val) throws UnsupportedEncodingException {
        return URLEncoder.encode(val, "UTF-8");
    }

    public void setService(String S) {
        this.Service = S;
    }

    public void setOperation(String O) {
        this.Operation = O;
    }

    public void setItedId(String I) {
        this.ItemId = I;
    }

    public void setResponseGroup(String R) {
        this.ResponseGroup = R;
    }

    @Deprecated
    public void setTimeStamp(String T) {
        this.Timestamp = T;
    }

    // mapping params/value can be better
    private ArrayList<String> mapParams() throws UnsupportedEncodingException {
        ArrayList<String> params = new ArrayList<String>();

            params.add(e("Service")        + "=" + e(this.Service));
            params.add(e("AssociateTag")   + "=" + e(Env.AssociateTag));
            params.add(e("Operation")      + "=" + e(this.Operation));
            params.add(e("ItemId")         + "=" + e(this.ItemId));
            params.add(e("ResponseGroup")  + "=" + e(this.ResponseGroup));
            params.add(e("Version")        + "=" + e(Version));
            params.add(e("Timestamp")      + "=" + e(this.Timestamp));

            params.sort(new Comparator<String>() {
                @Override
                public int compare(String a, String b) {
                    return a.compareToIgnoreCase(b);
                }
            });

            params.add(0, e("AWSAccessKeyId") + "=" + e(Env.AWSAccessKeyId));

        return params;
    }

    private String getRequestBody() throws UnsupportedEncodingException {
        return
            "GET"        + "\n" +
            AWS_Host     + "\n" +
            AWS_endpoing + "\n" +
            String.join("&", this.mapParams());
    }

    private String getSignature() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec sk = new SecretKeySpec(Env.AWSSecretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac sha256_HMAC  = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(sk);

        return Base64.encodeBase64String(
            sha256_HMAC.doFinal(this.getRequestBody().getBytes("UTF-8")));
    }

    // handle exception, use URI instead of url
    public URL getRequestUri() throws Exception {
        if(this.ItemId == null)
            throw new Exception("Product Identifier (ASIN) can't be null");

        ArrayList<String> params = this.mapParams();
            params.add(e("Signature") + "=" + e(this.getSignature()));

        URL request = new URL(
                protocol,
                AWS_Host,
                AWS_endpoing + "?" +
                    String.join("&", params));

        return request;
    }


}
