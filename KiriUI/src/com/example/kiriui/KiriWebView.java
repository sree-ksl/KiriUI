package com.example.kiriui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.SyncStateContract.Constants;
import android.util.AttributeSet;
import android.webkit.WebView;

public class KiriWebView extends WebView{
	
	int elementId = -1;
	
	private Rect mRect;
	private Paint mPaint;
	
	public KiriWebView(Context context, AttributeSet attrs){
		super(context,attrs);
		
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(0x800000FF);
		
		setBackgroundColor(Color.argb(1, 0, 0, 0));
		getSettings().setJavaScriptEnabled(true);
		addJavascriptInterface(this, "JSInterface");
		
		init(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		int count = getHeight();
		Rect r = mRect;
		Paint paint = mPaint;
		paint.setColor(Color.BLACK);
		for(int i=0; i<count; i++)
		{
			int baseline = getBaseline();
			canvas.drawLine(r.left, baseline+1, r.right, baseline+1, paint);
			
		}
		super.onDraw(canvas);
	}
	
	public void init(Context context){
		
		StringBuilder builder = new StringBuilder();
		builder.append(context.getString(R.string.webview_init));
		
		int width = 320;
		int difs = (((width/2)-320)/2) + 15;
		if(difs < 0)
		   difs = 15;
		elementId = elementId + 1;
		builder.append("<div id=div0 style='width:290px;padding-left:"
				+ String.valueOf(difs) + "px;padding-right:"
				+ String.valueOf(difs) + "px;' >"
				+ context.getString(R.string.welcome_message) + "</div>");
		for(int i=1; i < Constants.WEBVIEW_CALL_OUT_SIZE; i++){
			builder.append("<div id=div" + i
					+ " style='width:290px;padding-left:"
					+ String.valueOf(difs) + "px;padding-right:"
					+ String.valueOf(difs) + "px;' ></div>");
		}
		
		builder.append(context.getString(R.string.webview_shortend));
		loadDataWithBaseURL(Constants.BASE_URL, builder.toString(),
				Constants.WEBVIEW_OUTPUT_TYPE, Constants.WEBVIEW_OUTPUT_FORMAT,
				"");
	}
	
	public void addNewCallOut(String message, Boolean ismsgResponse){
		elementId = elementId + 1;
		StringBuilder messageBuilder = new StringBuilder();
		if(!message.contentEquals("")){
			if(!ismsgResponse){
				messageBuilder.append("<table class='bubble-gray' cellspacing='0' cellpadding='0'><tr><td class='head'></td></tr>");
				messageBuilder.append("<tr><td class='mid'><div class='txt shadow'>"
						+ message + "</div></td></tr>");
				messageBuilder.append("<tr><td class='foot'></td></tr></table>");
			}
			else{
				messageBuilder.append("<table class='bubble-blue' cellspacing='0' cellpadding='0'><tr><td class='bhead'></td></tr>");
		        messageBuilder.append("<tr><td class='bmid'><div class='txt shadow'>"
						+ message + "</div></td></tr>");
		        messageBuilder.append("<tr><td class='bfoot'></td></tr></table>");
			}
			
			loadUrl("javascript:document.getElementById(\"div" + elementId
					+ "\").innerHTML=\"" + messageBuilder.toString() + "\";");
			
		}
		StringBuilder jvscr = new StringBuilder();
		if(!ismsgResponse){
			if(elementId != 1){
				if(!ismsgResponse){
					jvscr.append("var elem = document.getElementById('div"
							+ (elementId - 1)
							+ "');     var x = 0;     var y = 0;     while (elem != null) {         x += elem.offsetLeft;         y += elem.offsetTop;         elem = elem.offsetParent;     } ");
					jvscr.append("var endj=500; var i=window.scrollY; for(i=window.scrollY;i<y;i++){ var j=0; var a=0; for(j=0;j<endj;j++) {a=a+1; }  window.scrollTo(x, i); } ");
					loadUrl("javascript:" + jvscr.toString());
				}
			}
		}
	}

}
