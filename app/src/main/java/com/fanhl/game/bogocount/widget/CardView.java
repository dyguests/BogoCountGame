package com.fanhl.game.bogocount.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.fanhl.game.bogocount.R;
import com.fanhl.game.bogocount.drawable.TextDrawable;
import com.fanhl.game.bogocount.model.Card;

/**
 * 显示卡牌的view
 * Created by fanhl on 15/4/7.
 */
public class CardView extends View {
    private static final String TAG = CardView.class.getSimpleName();

    TextDrawable textDrawable;

    private int width;
    private int height;
    private int mWidthMeasureSpec;
    private int mHeightMeasureSpec;

    Card data;

    public CardView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            data = new Card(99);
        }

        width = (int) context.getResources().getDimension(R.dimen.view_card_width);
        height = (int) context.getResources().getDimension(R.dimen.view_card_height);
        mWidthMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(width, android.view.View.MeasureSpec.EXACTLY);
        mHeightMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(height, android.view.View.MeasureSpec.EXACTLY);

        //for API 14
        //setBackground(getResources().getDrawable(R.drawable.card_background));
        setBackgroundDrawable(getResources().getDrawable(R.drawable.card_background));

        textDrawable = new TextDrawable();
//        textDrawable.setData(String.valueOf(data.getValue()));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(mWidthMeasureSpec, mHeightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data != null && data.getType() == Card.TYPE_FORE) {
            textDrawable.setBounds(0, 0, width, height);
            textDrawable.draw(canvas);
        }
    }

    public Card getData() {
        return data;
    }

    public void setData(Card data) {
        this.data = data;
        textDrawable.setData(String.valueOf(data.getValue()));
    }

    public void notifyDirectionChanged() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotationY", 0, 360, 720).setDuration(400);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
               invalidate();
            }
        });

        animator.start();
    }
}
