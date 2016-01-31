package com.x.memorycardgame;

import java.util.ArrayList;

import com.x.memorygame.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Card {

	private float x=0,y=0;
	private float width=0,height=0;
	private boolean visible = true;
	
	private String text;
	private int textSize = 96;
	private int bgColor = R.color.bg_color;
	private Paint textPaint;
	private Paint bgPaint;
	private Rect rect;
	private float textLength = 0;
	
	/**
	 * 
	 * @param context
	 * @param x x坐标
	 * @param y y坐标
	 * @param width
	 * @param height
	 * @param text
	 */
	public Card(Context context,float x,float y,float width,float height,String text) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.text = text;
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(context.getResources().getColor(R.color.white));
		textPaint.setTextSize(96);
		//textPaint.setTextAlign(Paint.Align.CENTER);
		bgPaint = new Paint();
		bgPaint.setColor(context.getResources().getColor(bgColor));
				
		rect = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), rect);
		textLength = textPaint.measureText(text);
		// TODO Auto-generated constructor stub
	}
	
	public void DrawMe(Canvas canvas){
		canvas.save();
		canvas.translate(x, y);
		canvas.drawRect(0 , 0 , width, height, bgPaint);
		if (visible){
			canvas.drawText(text, 0  + width/2 - textLength /2,0 + rect.height()/2 + height/2, textPaint);	//嗯
		}
		canvas.restore();
	}
	
	public boolean isClickInMe(float x,float y){
		if((x > this.x &&  x < (this.x + width)) && (y > this.y && y <(this.y + height))){
			return true;
		}
		
		return false;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public boolean getVisible(){
		return this.visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
	
	public int getBgColor() {
		return bgColor;
	}
}
