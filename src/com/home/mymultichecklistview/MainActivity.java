package com.home.mymultichecklistview;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.home.mymultichecklistview.MyExpandableListViewAdapter.ItemInfo;

public class MainActivity extends Activity {
	private ExpandableListView listView;
	private MyExpandableListViewAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ExpandableListView)findViewById(R.id.list);
		listView.setGroupIndicator(this.getResources().getDrawable(R.drawable.group_indicator));
		listView.setIndicatorBounds(5, 25);
		listView.setDivider(null);
		adapter = new MyExpandableListViewAdapter(this);
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(true);		
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	public void onButtonClick(View v) {
		StringBuilder sb = new StringBuilder();
		List<ItemInfo> checkedItems =adapter.getCheckedItems();
		for (ItemInfo item: checkedItems) {
			sb.append("group[").append(item.groupPosition).append("][").append(item.childPosition).append("]-").append(item.itemContent).append("\n");			
		}

//		SparseBooleanArray array = listView.getCheckedItemPositions();
//		for (int i=0; i<array.size(); i++) {
//			Log.d("ExpandableListView", "array key: "+array.keyAt(i));
//			if (array.valueAt(i)) {
//				long packedPosition = listView.getExpandableListPosition(array.keyAt(i));
//				int groupPos = ExpandableListView.getPackedPositionGroup(packedPosition);
//				int childPos = ExpandableListView.getPackedPositionChild(packedPosition);
//				sb.append("packedPosition: ").append(packedPosition).append(",group[").append(groupPos).append("][").append(childPos).append("],");
//			}
//		}
		sb.append(" are checked");
		Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
