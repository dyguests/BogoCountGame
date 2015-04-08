package com.fanhl.game.bogocount.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

/**
 * Created by fanhl on 15/4/7.
 */
public class TextDrawable extends Drawable {
    public static final int DEFAULT_TEXT_SIZE = 20;
    public static final int DEFAULT_TEXT_COLOR = Color.rgb(255, 254, 255);
    public static final float WIDTH2TEXT_SIZE = 0.8f;

    protected TextPaint strokePaint;
    protected TextPaint fillPaint;

    protected float offsetY;

    String data = "0";//数字

    public TextDrawable() {
        strokePaint = new TextPaint();
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setTextAlign(Paint.Align.CENTER);
        strokePaint.setStrokeWidth(2f);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(DEFAULT_TEXT_COLOR);
        strokePaint.setTextSize(DEFAULT_TEXT_SIZE);
//        strokePaint.setFakeBoldText(true);

        fillPaint = new TextPaint();
        fillPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setTextAlign(Paint.Align.CENTER);
        strokePaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(DEFAULT_TEXT_COLOR);
        fillPaint.setTextSize(DEFAULT_TEXT_SIZE);


        refreshOffsetY();
    }

    /**
     * 为了使文字垂直居中的垂直偏移量
     */
    protected void refreshOffsetY() {
        offsetY = -(strokePaint.getFontMetrics().top + strokePaint.getFontMetrics().bottom) / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect r = getBounds();

        final int count = canvas.save();
        canvas.translate(r.left, r.top);
        onDraw(canvas, strokePaint, fillPaint);
        canvas.restoreToCount(count);
    }

    private void onDraw(Canvas canvas, TextPaint strokePaint, TextPaint fillPaint) {
        String text = data;

        float x = getWidth() / 2;
        float y = getHeight() / 2 + offsetY;

        canvas.drawText(text, x, y, fillPaint);
        canvas.drawText(text, x, y, strokePaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        strokePaint.setStrokeWidth(((float) (getWidth() * WIDTH2TEXT_SIZE * 0.01)));
        strokePaint.setTextSize(getWidth() * WIDTH2TEXT_SIZE);
        fillPaint.setTextSize(getWidth() * WIDTH2TEXT_SIZE);
        refreshOffsetY();
    }

    float getWidth() {
        Rect rect = getBounds();
        return rect.right - rect.left;
    }

    float getHeight() {
        Rect rect = getBounds();
        return rect.bottom - rect.top;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
