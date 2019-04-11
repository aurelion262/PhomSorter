/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Card
{
    // value : giá trị của quân bài (A=1, 2, 3, ... , J=11, Q=12, K=13)
    // suit : chất của quân bài (1 = bích, 2 = tép, 3 = dô, 4 = cơ)
    private int value,suit;

    public Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }   
    
    @Override
    public String toString()
    {
        return ("[" + getValue() + "," + getSuit() + "]");
    }
}
