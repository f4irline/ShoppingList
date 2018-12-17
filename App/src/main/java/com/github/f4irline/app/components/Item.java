package com.github.f4irline.app.components;

import javax.persistence.*;

/**
 * A single shopping list item object.
 * 
 * <p>
 * Holds values of a single shopping list item.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1216
 */
@Entity(name = "Item")
public class Item {
    public int id;
    
    public String item;

    public Integer amount;

    public Item () {
        
    }

    /**
     * Constructor for the item if a amount is given.
     * 
     * @param id - the key identifier of the item.
     * @param item - the item name.
     * @param amount - the amount of the item.
     */
    public Item (int id, String item, Integer amount) {
        setId(id);
        setItem(item);
        setAmount(amount);
    }

    /**
     * Constructor for the item if amount is not given.
     * 
     * @param id - the key identifier of the item.
     * @param item - the item name.
     */
    public Item (int id, String item) {
        setId(id);
        setItem(item);
    }

    /**
     * Sets the key identifier of the item.
     * 
     * @param id - the key identifier of the item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the key identifier of the item.
     * 
     * @return - the key identifier of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the item name.
     * 
     * @param item - the item name.
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Returns the item name.
     * 
     * @return - the item name.
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the amount of the item.
     * 
     * @param amount - amount of the item.
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    /**
     * Returns the amount of the item.
     * 
     * @return - the amount of the item.
     */
    public Integer getAmount() {
        return amount;
    } 

}
