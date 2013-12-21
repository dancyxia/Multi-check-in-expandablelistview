package com.home.mymultichecklistview;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

	private ArrayList<Checkable> checkableViews; 
	boolean isChecked;
	
	public CheckableLinearLayout(Context context) {
		super(context);
		initialize();
	}

	public CheckableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	@Override
	public void setChecked(boolean checked) {
		this.isChecked = checked;
		for (Checkable view : checkableViews) {
			view.setChecked(checked);
		}
	}
	
	private void initialize() {
		isChecked = false;
		checkableViews = new ArrayList<Checkable>();
	}

	@Override
	public boolean isChecked() {
		return this.isChecked;
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		for (Checkable view : checkableViews) {
			Log.d("ExpandableListView", "view is toggled");
			view.toggle();
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		for (int i=0; i<this.getChildCount(); i++) {
			findCheckableChild(this.getChildAt(i));
		}
	}

	private void findCheckableChild(View child) {
		if (child instanceof Checkable) {
			checkableViews.add((Checkable)child);
		}
		
		if (child instanceof ViewGroup) {
			for (int i=0; i<((ViewGroup)child).getChildCount(); i++) {
				findCheckableChild(((ViewGroup) child).getChildAt(i));
			}
		}
	}
}
