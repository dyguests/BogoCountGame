package com.fanhl.game.bogocount.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.fanhl.game.bogocount.adapter.BogoAdapter;
import com.fanhl.game.bogocount.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by fanhl on 15/4/7.
 */
public class BogoView extends GridView {
    private static final String TAG = BogoView.class.getSimpleName();

    private Random mRandom = new Random();

    int[][] positions;

    boolean initFlag = false;

    public BogoView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BogoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            List<Card> list = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                list.add(new Card(i));
            }
            setAdapter(new BogoAdapter(context, list));
        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.d(TAG, "l:" + left + " t:" + top + " r:" + right + " b" + bottom);

        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "onLayout width:" + width + " height:" + height);

        int childCount = getChildCount();


        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (!initFlag) {
                initFlag = true;
                //加上view的大小,防止view的一部分的container的外面
                int vWidth = width - childWidth - childWidth;
                int vHeight = height - childHeight - childHeight;
                intPositions(childCount, vWidth, vHeight, childWidth, childHeight);
            }


            int x = childWidth + positions[i][0];
            int y = childHeight + positions[i][1];


            int cl = x - child.getMeasuredWidth() / 2;
            int ct = y - child.getMeasuredHeight() / 2;
            int cr = x + child.getMeasuredWidth() / 2;
            int cb = y + child.getMeasuredHeight() / 2;

            child.layout(cl, ct, cr, cb);

            Log.d(TAG, "onLayout child:" + i
                    + " left:" + cl + " top:" + ct + " right:" + cr + " bottom:" + cb);

        }
    }

    private void intPositions(int count, int width, int height, int childWidth, int childHeight) {
        positions = new int[count][2];

        for (int i = 0; i < count; i++) {
            positions[i][0] = mRandom.nextInt(width);
            positions[i][1] = mRandom.nextInt(height);
        }
    }
}
