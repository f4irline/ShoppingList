package fi.projects.fairline.app;

import javax.persistence.*;

@Entity(name = "Item")
public class Item {
    public int id;
    
    public String item;

    public Integer amount;

    public Item () {
        
    }

    public Item (int id, String item, Integer amount) {
        setId(id);
        setItem(item);
        setAmount(amount);
    }

    public Item (int id, String item) {
        setId(id);
        setItem(item);
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

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    } 

}
