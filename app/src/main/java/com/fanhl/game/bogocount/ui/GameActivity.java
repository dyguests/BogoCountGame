package com.fanhl.game.bogocount.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.fanhl.game.bogocount.R;
import com.fanhl.game.bogocount.adapter.BogoAdapter;
import com.fanhl.game.bogocount.logic.Game;
import com.fanhl.game.bogocount.model.Card;
import com.fanhl.game.bogocount.widget.BogoView;
import com.fanhl.game.bogocount.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends ActionBarActivity {
    private BogoView bogoView;
    private ImageView imageView;

    private BogoAdapter bogoAdapter;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        assignViews();
        gameStart();
    }

    private void assignViews() {
        bogoView = (BogoView) findViewById(R.id.bogoView);
        imageView = (ImageView) findViewById(R.id.bogoImgView);

        List<Card> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(new Card(i));
        }
        bogoAdapter = new BogoAdapter(this, list);
        bogoView.setAdapter(bogoAdapter);

        game = new Game(list);
    }

    private void gameStart() {

        //动画 img的动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bogo_show);
//        imageView.setAnimation(animation);
//        imageView.animate();

        //动画  翻成背面
        bogoAdapter.setDirection(Card.TYPE_BACK);


        setClickable();
    }

    private void setClickable() {
        bogoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!(view instanceof CardView)) return;
                CardView cardView = (CardView) view;

                Card data = cardView.getData();

                //动画
                data.setType(Card.TYPE_FORE);
//                cardView.invalidate();

                if (game.validate(data)) {
                    if (game.isWin()) {
                        game.win();
                        Toast.makeText(GameActivity.this, R.string.game_win, Toast.LENGTH_SHORT).show();
                        bogoView.setOnItemClickListener(null);
                    }
                } else {
                    game.gameOver();
                    bogoView.setOnItemClickListener(null);
                    Toast.makeText(GameActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
                }

                view.setClickable(false);
            }
        });
    }

}
