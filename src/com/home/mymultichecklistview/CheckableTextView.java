package com.home.mymultichecklistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

public class CheckableTextView extends TextView implements Checkable{

	private boolean isChecked = false;
	public CheckableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CheckableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CheckableTextView(Context context) {
		super(context);
	}

	@Override
	public void setChecked(boolean checked) {
		isChecked = checked;
		setTextColor(checked? this.getContext().getResources().getColor(R.color.chkcolor):this.getContext().getResources().getColor(R.color.fgcolor));
	}

	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return isChecked;
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		
	}

}
