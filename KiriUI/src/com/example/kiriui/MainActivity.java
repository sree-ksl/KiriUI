package com.example.kiriui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class MainActivity extends Activity {
	
	KiriWebView webView = null; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(savedInstanceState == null)
		{
			webView = (KiriWebView)findViewById(R.id.webview);
			
			SendMessageTOUI("How can you help me?", true,2000);
			SendMessageTOUI("I can give you info about weather, news and match scores.", false,5000 );
			SendMessageTOUI("What is the weather tomorrow?", true,7000);
			SendMessageTOUI("Sunny -30 Degrees.", false, 9000);
		}
	}
	
	public void SendMessageTOUI(final String message, final Boolean isResponse, int wait){
		final Handler handler = new Handler();
		Timer t = new Timer();
		t.schedule(new TimerTask() { 
            public void run() { 
                handler.post(new Runnable() { 
                        public void run() { 
                        	webView.addNewCallOut(message,isResponse);
                        } 
                }); 
           }
       }, wait);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
