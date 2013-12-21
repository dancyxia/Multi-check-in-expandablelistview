/**
 * 
 */
package com.home.mymultichecklistview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * @author dancy
 *
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
	static class ItemInfo {
		public ItemInfo(int groupPosition, int childPosition, String itemContent) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
			this.itemContent = itemContent;
		}

		int groupPosition;
		int childPosition;
		String itemContent;
	}

	final private String[][] itemData = {
			{"item11", "item12"},
			{"item21", "item22", "item23"},
			{"item31", "item32", "item33","item34"}
	};
	
	private boolean[][] checkedState;
	private String[] groupData;
	private ItemInfo[] checkedItems;
	
	private Context context;
	
	/**
	 * 
	 */
	public MyExpandableListViewAdapter(Context context) {
		this.context = context;
		prepareData();
	}
	
	public List<ItemInfo> getCheckedItems() {
		ArrayList<ItemInfo> checkedItems = new ArrayList<ItemInfo>(10); 
		for (int i=0; i<groupData.length; i++) 
			for (int j=0; j<itemData[i].length; j++) {
				if (checkedState[i][j]) {
					checkedItems.add(new ItemInfo(i, j, itemData[i][j]));
				}
			}
		return checkedItems;
	}
	
	private void prepareData() {
		Log.d("ExpandableListView", "parepare data is called");
		checkedState =new boolean[itemData.length][]; 
		groupData = new String[itemData.length];
		for (int i=0; i<itemData.length; i++) {
			groupData[i] = String.format("Group %d", i);
			checkedState[i] = new boolean[itemData[i].length];
			Arrays.fill(checkedState[i],false);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupData.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return itemData[groupPosition].length;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupData[groupPosition];
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return itemData[groupPosition][childPosition];
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View groupView = convertView;
		if (groupView == null) {
			groupView = new TextView(context);
			((TextView)groupView).setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			groupView.setPadding(50,0,0,0);
		}
		((TextView)groupView).setText(groupData[groupPosition]);
		((TextView)groupView).setTextColor(context.getResources().getColor(R.color.fgcolor));
		
		return groupView;
	}

	private static class ViewHolder {
//		InertCheckBox checkBox;
		TextView item;
		CheckableLinearLayout layout;
	}
	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View itemView = convertView;
		final ViewHolder vh;
		if (itemView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.item_view, null);
			
			vh = new ViewHolder();
			vh.layout = (CheckableLinearLayout)itemView.findViewById(R.id.layout);
			vh.item = (TextView)itemView.findViewById(R.id.item);
			itemView.setTag(vh);
		} else {
			vh = (ViewHolder)itemView.getTag();
		}
		vh.item.setText(itemData[groupPosition][childPosition]);
		final ExpandableListView listView = ((ExpandableListView)((MainActivity)context).findViewById(R.id.list));
		final int position = listView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
		listView.setItemChecked(position, checkedState[groupPosition][childPosition]);
		vh.layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((CheckableLinearLayout)v).toggle();
				checkedState[groupPosition][childPosition] = !checkedState[groupPosition][childPosition]; 
				listView.setItemChecked(position, ((CheckableLinearLayout)v).isChecked());
			}
		});
		return itemView;
	}
	
	public boolean getCheckedState(int groupPosition, int childPosition) {
		return checkedState[groupPosition][childPosition];
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO Auto-generated method stub
		Log.d("ExpandableListView", "onGroupCollapsed is called, groupPositoin: "+groupPosition);
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO Auto-generated method stub
		Log.d("ExpandableListView", "onGroupExpanded is called, groupPositoin: "+groupPosition);
		super.onGroupExpanded(groupPosition);
	}

	
}
