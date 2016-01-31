package com.x.memorycardgame;

import java.util.ArrayList;

import com.x.memorygame.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements Callback,OnTouchListener,Runnable{

	private ArrayList<Card> cards;
	private int[][] points;
	private int cardNumEachLine = 5; 
	private int currentCardNum = 5;
	private int cardWidth = 0,cardHeight = 0;
	private final int margin = 8;
	private Context context;
	private SurfaceHolder holder;
	private int index = 1;
	private boolean isGameStart = false;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GameView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		this.context = context;
		holder = getHolder();
		holder.addCallback(this);
		cards = new ArrayList<>();
		points = new int[cardNumEachLine][cardNumEachLine];
		setOnTouchListener(this);
	}

	/**
	 * @param autoRestart 是否需要重置游戏。
	 */
	public void startGame(boolean reset){
		for(int i = 0;i < cardNumEachLine;i++){
			for(int j = 0;j < cardNumEachLine; j++){
				points[i][j] = 0;
			}
		}
		cards.clear();
		index = 1;
		isGameStart = false;
		if(reset) currentCardNum = 5;
		
		int x,y;
		int count = 1;
		for(int i = 0;i < currentCardNum ;i++){
			x = (int) (Math.random() * (cardNumEachLine));
			y = (int) (Math.random() * (cardNumEachLine));
			if(points[x][y] == 1){
				i--;
				continue;
			}
			points[x][y] = 1;
			Card c = new Card(getContext(), x * (cardWidth + margin), y * (cardHeight + margin), cardWidth, cardHeight, count + "");
			count ++;
			cards.add(c);
		}
		draw();
		new Thread(this).start();
	}
	
	public void draw(){
		// TODO Auto-generated method stub
		Canvas canvas = holder.lockCanvas();
		if(canvas != null){
			canvas.drawColor(context.getResources().getColor(R.color.white));
			for(Card c: cards){
				c.DrawMe(canvas);
			}
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		cardWidth = getWidth() / cardNumEachLine - margin;
		//cardHeight = getHeight() / cardNum - margin;
		cardHeight = cardWidth;
		Log.i("xxx", getClass().getName() +  " getWidth()"+  getWidth() + " getHeight()" + getHeight()+ " cardHeight" + cardHeight + "  cardWidth:" + cardWidth);
		startGame(false);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(isGameStart){
			float x = event.getX();
			float y = event.getY();
			Card cardClick = null;
			for(Card c : cards){
				if(c.isClickInMe(x, y))	cardClick = c;
			}
			
			if(cardClick == null ||cardClick.getVisible()) return true;
			if(cardClick.getText().equals(String.valueOf(index))){
				cardClick.setVisible(true);
				index++;
				draw();
			}else{
				isGameStart = false;
				currentCardNum = 5;
				showDialog(context.getResources().getString(R.string.youlose),
						context.getResources().getString(R.string.again));
			}
			
			if(isWin() && currentCardNum == 25) {
				saveHighLevel(currentCardNum);
				currentCardNum = 5;
				showDialog(context.getResources().getString(R.string.you_remeber_25_cards),
						context.getResources().getString(R.string.again));
			}else if(isWin()){
				saveHighLevel(currentCardNum);
				currentCardNum++;
				showDialog(context.getResources().getString(R.string.youwin),
						context.getResources().getString(R.string.goon));
			}
		}
		
		return true;
	}

	
	
	private void showDialog(String message,String btnText) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton(btnText, 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startGame(false);
					}
				});
		builder.setCancelable(false);
		builder.show();
	}

	
	private boolean isWin() {
		for(Card c:cards){
			if(c.getVisible() == false) return false;
		}
		return true;
	}
	
	private void saveHighLevel(int current){
		int high = SPUtil.getInstance(getContext()).readInt(SPUtil.SpKey_highlevel);
		if (current > high){
			SPUtil.getInstance(getContext()).saveInt(SPUtil.SpKey_highlevel, current);
			MainActivity.setHighLevel(current + "");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int count = 1;
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			if(count > 3) break;
		}
		
		for(Card c:cards){
			c.setVisible(false);
		}
		draw();
		isGameStart = true;
	}

}
