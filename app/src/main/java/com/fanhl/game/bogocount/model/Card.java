package com.fanhl.game.bogocount.model;

/**
 * Created by fanhl on 15/4/7.
 */
public class Card {
    public static final int TYPE_FORE = 0;
    public static final int TYPE_BACK = 1;

    int type = TYPE_FORE;
    int value = 0;

    public Card(int value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
