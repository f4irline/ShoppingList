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
    
    public int red;

    public int green;

    public int blue;

    public Integer amount;

    public Boolean checked;

    public Item () {
        
    }

    /**
     * Constructor for the item if a amount is given.
     * 
     * @param id - the key identifier of the item.
     * @param item - the item name.
     * @param rgb - array containing values for red, green and blue color.
     * @param amount - the amount of the item.
     * @param checked - the checked state of the item.
     */
    public Item (int id, String item, int[] rgb, Integer amount, boolean checked) {
        setId(id);
        setItem(item);
        setRed(rgb[0]);
        setGreen(rgb[1]);
        setBlue(rgb[2]);
        setAmount(amount);
        setChecked(checked);
    }

    /**
     * Constructor for the item if amount is not given.
     * 
     * @param id - the key identifier of the item.
     * @param item - the item name.
     * @param rgb - array containing values for red, green and blue color.
     * @param checked - the checked state of the item.
     */
    public Item (int id, String item, int[] rgb, boolean checked) {
        setId(id);
        setItem(item);
        setRed(rgb[0]);
        setGreen(rgb[1]);
        setBlue(rgb[2]);
        setChecked(checked);
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
     * Sets the amount of red in the color.
     * 
     * @param red - the red color value.
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Sets the amount of green in the color.
     * 
     * @param green - the green color value.
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Sets the amount of blue in the color.
     * 
     * @param blue - the blue color value.
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * Returns the amount of red in the color.
     * 
     * @return - the amount of red in the color.
     */
    public int getRed() {
        return red;
    }

    /**
     * Returns the amount of red in the color.
     * 
     * @return - the amount of red in the color.
     */
    public int getGreen() {
        return green;
    }

    /**
     * Returns the amount of red in the color.
     * 
     * @return - the amount of red in the color.
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Returns the RGB value as a string for styling purposes.
     * 
     * @return - RGB value in a string.
     */
    public String getRgbString() {
        return "rgb("+red+","+green+","+blue+")";
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

    /**
     * Sets the checked state of the item.
     * 
     * @param checked - true or false depending if the item is checked or not.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * Changes the checked state of the item.
     */
    public void changeChecked() {
        checked = !checked;
    }

    /**
     * Returns the checked state of the item.
     * 
     * @return - the checked state of the item.
     */
    public boolean getChecked() {
        return checked;
    }

}
