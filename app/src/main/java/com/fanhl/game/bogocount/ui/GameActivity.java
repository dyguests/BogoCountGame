package com.fanhl.game.bogocount.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.fanhl.game.bogocount.R;
import com.fanhl.game.bogocount.adapter.BogoAdapter;
import com.fanhl.game.bogocount.logic.Game;
import com.fanhl.game.bogocount.model.Card;
import com.fanhl.game.bogocount.widget.BogoView;
import com.fanhl.game.bogocount.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
        //初始化bogoView
        bogoView.initFlag = false;
        bogoView.setVisibility(View.INVISIBLE);
        bogoView.setVisibility(View.VISIBLE);

        //一个长时间的暂停

        //动画 img的动画
        startBogoImgAnimate();
    }

    private void startBogoImgAnimate() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 1000, 0, 0, 0, 0, 0, 1000);
        animator.setStartDelay(3000);
        animator.setDuration(2000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(View.GONE);
                //动画  翻成背面
                startCardsTurnBack();
            }
        });
        animator.start();

    }

    private void startCardsTurnBack() {
        bogoAdapter.setDirection(Card.TYPE_BACK);
        bogoAdapter.notifyDataSetChanged();

        startClickable();
    }

    private void startClickable() {
        //可以开始操作了
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
                cardView.notifyDirectionChanged();

                if (game.validate(data)) {
                    if (game.isWin()) {
                        game.win();
                        bogoView.setOnItemClickListener(null);
                        showGameWinDialog();
                    }
                } else {
                    game.gameOver();

                    bogoAdapter.setDirection(Card.TYPE_FORE);
                    bogoAdapter.notifyDataSetChanged();


                    bogoView.setOnItemClickListener(null);
                    showGameOverDialog();
                }

                view.setClickable(false);
            }
        });
    }

    private void showGameWinDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(gStr(R.string.game_over))
                .setCancelText(gStr(R.string.back))
                .setConfirmText(gStr(R.string.again))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        game.restart();
                        gameStart();
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    private void showGameOverDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(gStr(R.string.game_over))
                .setCancelText(gStr(R.string.back))
                .setConfirmText(gStr(R.string.again))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        game.restart();
                        gameStart();
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    private String gStr(int id) {
        return getResources().getString(id);
    }

}
