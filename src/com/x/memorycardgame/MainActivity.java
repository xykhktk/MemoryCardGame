package com.x.memorycardgame;

import com.x.memorygame.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	private static TextView highLevel;
	private ImageButton help;
	private ImageButton restart;
	private GameView gameview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		highLevel = (TextView) findViewById(R.id.tv_high_level);
		help = (ImageButton) findViewById(R.id.ib_help);
		restart = (ImageButton) findViewById(R.id.ib_restart);
		gameview = (GameView) findViewById(R.id.gameview);
		restart.setOnClickListener(this);
		help.setOnClickListener(this);
		
		highLevel.setText(SPUtil.getInstance(this).readInt(SPUtil.SpKey_highlevel) + "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.ib_help:
			showinfo();
			break;
		case R.id.ib_restart:
			gameview.startGame(true);
			break;
		}
	}

	
	public static void setHighLevel(String level){
		if(highLevel != null ) highLevel.setText(level);
	}
	
	private void showinfo() {
		// TODO Auto-generated method stub
		AlertDialog.Builder buider = new Builder(this);
		buider.setMessage(getResources().getString(R.string.help));
		buider.setPositiveButton(getResources().getString(R.string.goon), 
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
		buider.show();
	}
}
