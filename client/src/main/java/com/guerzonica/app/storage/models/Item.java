package com.guerzonica.app.storage.models;

import java.sql.SQLException;

import com.google.gson.annotations.SerializedName;

import com.guerzonica.app.storage.exceptions.AlreadyExistException;
import com.guerzonica.app.storage.exceptions.NotFoundException;

/**
 * Model of an abstract database Item (record).
 *  
 * <br>
 * This class primarly represent the minimal methods to
 * implement a CRUD logic for a database item and handles
 * the element identifier (primary key) through a parametrized
 * type.
 * 
 * @author Matteo Guerzoni
 */
public abstract class Item<T> {

    @SerializedName(value="id")
    private T id;

    public Item() {
        this.id = null;
    }

    public Item(T i) {
        this.id = i;
    }

    protected void setId(T i) {
        this.id = i;
    }

    public T getId() {
        return this.id;
    }

    /**
     * Determinates if the instance actually exists
     * 
     * @return Boolean value that indicates if the instance exists
     */
    public Boolean isRecord() {
        return this.id != null;
    }

    /**
     * Method to inizitialize/generate table schema on the database.
     * 
     * @throws SQLException If an exception occurs or either the element doesn't support schema creation.
     */
    public void SCHEMA() throws SQLException {
        throw new SQLException("Undefined schema");
    }
    

    /**
     * Create the desired element on the database (CRUD - Create).
     * 
     * The implemented method needs to take care of the "after created" logic,
     * which means it needs to set the element ID with the generated ID (if auto).
     * 
     * @throws AlreadyExistException If the primary key constraint fails.
     * @throws SQLException If an SQL exception occours during the query execution.
     */
    public abstract void CREATE() throws AlreadyExistException, SQLException;

    /**
     * Read the desired element on the database (CRUD - Read).
     * 
     * The implemented method needs to take care of the "pre read" logic,
     * which means it needs to set the element ID to read before reading it.
     * 
     * @throws NotFoundException If the element hasn't been found.
     * @throws SQLException If an SQL exception occours during the query execution.
     */
    public abstract void READ()   throws NotFoundException, SQLException;

    /**
     * Update the desired element on the database (CRUD - Update).
     * 
     * The implemented method needs to take care of the "pre update" logic,
     * which means it needs to set the element content before updating it.
     * 
     * @throws NotFoundException If the element hasn't been found.
     * @throws SQLException If an SQL exception occours during the query execution.
     */
    public abstract void UPDATE() throws NotFoundException, SQLException;

    /**
     * Delete the desired element on the database (CRUD - Delete).
     * 
     * The implemented method needs to take care of the "pre delete" logic,
     * which means it needs to set the element ID before deleting it.
     * 
     * @throws NotFoundException If the element hasn't been found.
     * @throws SQLException If an SQL exception occours during the query execution.
     */
    public abstract void DELETE() throws NotFoundException, SQLException;

}
