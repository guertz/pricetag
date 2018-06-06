package com.guerzonica.app.http.models;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
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

import com.guerzonica.app.env.Env;

/**
 * Easly create an amazon Http Request.
 *  
 * <br>
 * This method takes care of handling all the
 * requested params and then compress them back
 * in an unique request URL (rest API).
 * 
 * @author Matteo Guerzoni
 */
public class AmazonRequest {

    /** URI Param: Http Protocol */
    private static final String protocol     = "http";
    /** URI Param: Host */
    private static final String AWS_Host     = Env.AWSHost;
    /** URI Param: Rest endpoint */
    private static final String AWS_endpoint = "/onca/xml";

    /** API Param: Version */
    private static final String Version        = "2013-08-01";

    /** Request Param: AWS Service ID */
    private String Service       = "AWSECommerceService";
    /** Request Param: Service Operation ID */
    private String Operation     = "ItemLookup";
    /** Request Param: Response group ID */
    private String ResponseGroup = "Small";

    /** Request timestap */
    private String Timestamp;
    /** Request element */
    private String ItemId; // itemType = "ASIN"

    /**
     * Create an AmazonRequest instance taking care of the default params
     * and the correct request timestamp format.
     */
    public AmazonRequest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 2000);
        Instant epoch = timestamp.toInstant();
        StringBuilder parse = new StringBuilder(epoch.toString());
            parse.replace(parse.length() - 5, parse.length() -1, "");

        this.Timestamp = parse.toString();
    }

    /**
     * URL Encoding the provided value.
     * 
     * @throws UnsupportedEncodingException If the encoding charset is not supported.
     * 
     * @param val The string to encode.
     * @return The encoded string.
     */
    private static String e(String val) throws UnsupportedEncodingException {
        return URLEncoder.encode(val, "UTF-8");
    }

    /**
     * Sets - Request Param: AWS Service ID
     * 
     * @param S The provided service value
     */
    public void setService(String S) {
        this.Service = S;
    }

    /**
     * Sets - Request Param: Service Operation ID
     * 
     * @param O The provided operation value
     */
    public void setOperation(String O) {
        this.Operation = O;
    }

    /**
     * Sets - Request element identifier
     * 
     * @param I The provided element identifier value (ASIN)
     */
    public void setItedId(String I) {
        this.ItemId = I;
    }

    /**
     * Sets - Request Param: Response Group ID
     * 
     * @param R The provided response group value
     */
    public void setResponseGroup(String R) {
        this.ResponseGroup = R;
    }

    /**
     * Sets - Request Timestamp
     * 
     * @deprecated
     * @param T The provided timestamp
     */
    @Deprecated
    public void setTimeStamp(String T) {
        this.Timestamp = T;
    }

    /**
     * Method to list the mapped request params in an ArrayList (URLEncoded).
     * 
     * <br/>
     * The params are sorted in the right way to fit amazon request requirements
     *
     * @throws UnsupportedEncodingException If the encoding charset is not supported.
     * @return The list of params in the format key=value
     */
    private ArrayList<String> mapParams() throws UnsupportedEncodingException {
        ArrayList<String> params = new ArrayList<String>();

            params.add(e("Service")        + "=" + e(this.Service));
            params.add(e("AssociateTag")   + "=" + e(Env.AssociateTag));
            params.add(e("Operation")      + "=" + e(this.Operation));
            params.add(e("ItemId")         + "=" + e(this.ItemId));
            params.add(e("ResponseGroup")  + "=" + e(this.ResponseGroup));
            params.add(e("Version")        + "=" + e(Version));
            params.add(e("Timestamp")      + "=" + e(this.Timestamp));

            // lexicographical sorting
            params.sort(new Comparator<String>() {
                @Override
                public int compare(String a, String b) {
                    return a.compareToIgnoreCase(b);
                }
            });

            params.add(0, e("AWSAccessKeyId") + "=" + e(Env.AWSAccessKeyId));

        return params;
    }

    /**
     * Method to generate the request body that will be signed.
     *
     * @throws UnsupportedEncodingException If the encoding charset is not supported.
     * @return The request body including params.
     */
    private String getRequestBody() throws UnsupportedEncodingException {
        return
            "GET"        + "\n" +
            AWS_Host     + "\n" +
            AWS_endpoint + "\n" +
            String.join("&", this.mapParams());
    }

    /**
     * Method to sign the request body.
     *
     * @throws UnsupportedEncodingException If the encoding charset is not supported.
     * @throws InvalidKeyException If the encryption key is invalid.
     * @throws NoSuchAlgorithmException If the requested encryption algorithm is not available
     * 
     * @return The X509 compliant Request Signature (UTF-8 Encoded) 
     */
    private String getSignature() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec sk = new SecretKeySpec(Env.AWSSecretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac sha256_HMAC  = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(sk);

        return Base64.encodeBase64String(
            sha256_HMAC.doFinal(this.getRequestBody().getBytes("UTF-8")));
    }

    /**
     * Retrive the amazon Http Rest Api Request URL.
     * 
     * <br>
     * Takes care of handling all the request params and the possible exceptions.
     * 
     * @throws MalformedURLException Something went wrong during the URL encoding process.
     * 
     * @return The X509 compliant Request Signature (UTF-8 Encoded) 
     */
    public URL getRequestUri() throws MalformedURLException {
        if(this.ItemId == null)
            throw new MalformedURLException("Item cannot be null");

        try {
            ArrayList<String> params = this.mapParams();
                params.add(e("Signature") + "=" + e(this.getSignature()));

            URL request = new URL(
                protocol,
                AWS_Host,
                AWS_endpoint + "?" +
                    String.join("&", params));

            return request;
        }
        catch(MalformedURLException e) { throw e; }
        catch(Exception e) {
            throw new MalformedURLException("Error signing certificate");
        }

    }


}
