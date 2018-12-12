package fi.projects.fairline.app;

import javax.persistence.*;

@Entity(name = "Item")
public class Item {
    private int id;
    
    private String name;

    private int amount;

    public Item (int id, String name, int amount) {
        setId(id);
        setName(name);
        setAmount(amount);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    } 

}
