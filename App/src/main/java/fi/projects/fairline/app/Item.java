package fi.projects.fairline.app;

import javax.persistence.*;

@Entity(name = "Item")
public class Item {
    public int id;
    
    public String item;

    public int amount;

    public Item (int id, String item, int amount) {
        setId(id);
        setItem(item);
        setAmount(amount);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setItem(String item) {
        this.item = item;
    }
    public String getItem() {
        return item;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    } 

}
