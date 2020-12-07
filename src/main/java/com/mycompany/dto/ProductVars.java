package com.mycompany.dto;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ProductVars implements Serializable {

    public ProductVars() {
    }

    private int numCheese;
    private int numWine;
    private int numApples;

    public int getNumCheese() {
        return numCheese;
    }

    public void setNumCheese(int numCheese) {
        this.numCheese = numCheese;
    }

    public int getNumWine() {
        return numWine;
    }

    public void setNumWine(int numWine) {
        this.numWine = numWine;
    }

    public int getNumApples() {
        return numApples;
    }

    public void setNumApples(int numApples) {
        this.numApples = numApples;
    }

}
