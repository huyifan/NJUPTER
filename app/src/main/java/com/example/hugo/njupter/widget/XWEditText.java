package com.example.hugo.njupter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class XWEditText extends EditText {
	private XWEditText mthis;

	public XWEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mthis = this;

	}
	public XWEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mthis = this;

	}


	public XWEditText(Context context) {
		super(context);
		mthis = this;

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if(e.getAction()== MotionEvent.ACTION_DOWN){
			

			getParent().requestDisallowInterceptTouchEvent(true);
		}else if(e.getAction()== MotionEvent.ACTION_MOVE){

			getParent().requestDisallowInterceptTouchEvent(true);
		}else if(e.getAction()== MotionEvent.ACTION_UP){
			
//			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.onTouchEvent(e);
	}

}
