package com.guerzonica.app.storage.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class ProductPrices extends TreeMap<Date,Offer> {

    private static final long serialVersionUID = 1L;

    private Product product = new Product();
    
    public ProductPrices(Product product) {
        this.product = product;
    }

    public ProductPrices(Offer offer) throws ParseException {
        this.product = offer.getProduct();
        add(offer);
    }

    public Offer add(Offer value) throws ParseException {
        SimpleDateFormat stamp = 
            new SimpleDateFormat("dd/MM/yyyy");

        Date k = stamp.parse(value.getDate());
        return super.put(k, value);
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}