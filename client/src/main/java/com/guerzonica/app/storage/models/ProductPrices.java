package com.guerzonica.app.storage.models;

public class ProductPrices extends Product {

    public ProductPrices() {
        super();
    }

    public ProductPrices(Product p) {
        setAsin(p.getId());
        setName(p.getName());
        setDescription(p.getDescription());
        setLink(p.getLink());
        setImage(p.getImage());
    }

    public PriceMap prices = new PriceMap();

}