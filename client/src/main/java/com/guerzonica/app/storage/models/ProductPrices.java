package com.guerzonica.app.storage.models;

/**
 * Model of a Product containing prices
 * 
 * @author Matteo Guerzoni
 */
public class ProductPrices extends Product {

    public ProductPrices() {
        super();
    }

    /** 
     * Create instance from Product dataset 
     * 
     * @param p The Product to add
     */
    public ProductPrices(Product p) {
        setAsin(p.getId());
        setName(p.getName());
        setDescription(p.getDescription());
        setLink(p.getLink());
        setImage(p.getImage());
    }

    public PriceMap prices = new PriceMap();

}