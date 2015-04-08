package com.fanhl.game.bogocount.logic;

import com.fanhl.game.bogocount.model.Card;

import java.util.List;

/**
 * Created by fanhl on 15/4/7.
 */
public class Game {
    private final List<Card> list;

    int currentIndex = 0;

    public Game(List<Card> list) {
        this.list = list;
    }

    public void start() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setType(Card.TYPE_BACK);
        }
        currentIndex = 0;
    }

    public void restart() {
        currentIndex = 0;
    }

    public boolean validate(Card data) {
        int i = list.indexOf(data);
        if (i == currentIndex) {
            currentIndex++;
            return true;
        }

        return false;
    }

    public boolean isWin() {
        return currentIndex == list.size();
    }

    public void win() {

    }

    public void gameOver() {

    }
}
