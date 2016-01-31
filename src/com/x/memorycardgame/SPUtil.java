package com.x.memorycardgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtil {

	private static Context context;
	private static SPUtil sputil;
	
	private static SharedPreferences sp;
	private static final String SpName = "memorycard";
	public static final String SpKey_highlevel = "highlevel";
	
	private SPUtil(Context context){
		this.context = context;
	}
	
	public static SPUtil getInstance(Context context){
		if(sputil == null){
			sputil = new SPUtil(context);
		}
		return sputil;
	}
	
	public void saveInt(String key,int value){
		if(sp == null) sp = context.getSharedPreferences(SpName, Context.MODE_PRIVATE);
		Editor e = sp.edit();
		e.putInt(key, value);
		e.commit();
	}
	
	public int readInt(String key){
		if(sp == null) sp = context.getSharedPreferences(SpName, Context.MODE_PRIVATE);
		return sp.getInt(key, 0);
	}
	
}
