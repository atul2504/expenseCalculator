package com.example.expenses.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalculator.models.HistoryModel;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;
import com.example.expensecalulator.activity.MainActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class AdapterHistory.
 */
public class AdapterHistory extends BaseExpandableListAdapter {

	/** The m context. */
	private Context mContext;

	/** The history list. */
	private ArrayList<HistoryModel> historyList;

	/**
	 * Instantiates a new adapter history.
	 * 
	 * @param context
	 *            the context
	 * @param historyList
	 *            the history list
	 */
	public AdapterHistory(Context context, ArrayList<HistoryModel> historyList) {
		super();
		this.mContext = context;
		this.historyList = historyList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return historyList.get(groupPosition).getArrayHistory()
				.get(childPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ViewHolderChild vChild = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_item, null);
			vChild = new ViewHolderChild(convertView);
			convertView.setTag(vChild);
		} else {
			vChild = (ViewHolderChild) convertView.getTag();
		}

		AllItemModel item = (AllItemModel) getChild(groupPosition,
				childPosition);

		vChild.txtItem.setText(item.getItem());

		if (item.getDescription() != null && item.getDescription().length() > 0) {
			vChild.txtCategory.setText(item.getDescription());
		} else {
			vChild.txtCategory.setText(item.getCategoryName());
		}

		vChild.txtPrice.setText(item.getFormattedPrice());
		vChild.txtTime.setText(item.getDate() + " " + item.getDay() + " "
				+ item.getYear());

		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return historyList.get(groupPosition).getArrayHistory().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public HistoryModel getGroup(int groupPosition) {
		return historyList.get(groupPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		return historyList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ViewHolderParent vParent = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_history_hearder, null);
			vParent = new ViewHolderParent(convertView);
			convertView.setTag(vParent);
		} else {
			vParent = (ViewHolderParent) convertView.getTag();
		}

		HistoryModel history = getGroup(groupPosition);

		Log.d("WEEK", history.getHeader());

		if (history.getFlag() == Commons.FLAG_WEEK) {
			if (Integer.parseInt(history.getHeader()) == Integer
					.parseInt(Commons.getWOM())) {
				vParent.txtDate.setText(R.string.title_week);
			} else {
				vParent.txtDate.setText(Commons.getFirstDayOfWeek(Integer
						.parseInt(history.getHeader())));
			}

		} else if (history.getFlag() == Commons.FLAG_MONTH) {
			vParent.txtDate.setText(history.getHeader());
		} else if (history.getFlag() == Commons.FLAG_YEAR) {
			vParent.txtDate.setText(history.getHeader());
		}

		vParent.txtPrice.setText(Commons.getCurrencySymbol(mContext) + " "
				+ history.getFormattedTotal());

		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/**
	 * The Class ViewHolderParent.
	 */
	private class ViewHolderParent {

		/** The txt price. */
		TextView txtDate, txtPrice;

		/**
		 * Instantiates a new view holder parent.
		 * 
		 * @param v
		 *            the v
		 */
		ViewHolderParent(View v) {
			txtDate = (TextView) v.findViewById(R.id.txtDate);
			txtPrice = (TextView) v.findViewById(R.id.txtPrice);

			txtDate.setTypeface(((MainActivity) mContext).getBoldFont());
			txtPrice.setTypeface(((MainActivity) mContext).getBoldFont());
		}
	}

	/**
	 * The Class ViewHolderChild.
	 */
	private class ViewHolderChild {

		/** The txt time. */
		TextView txtItem, txtCategory, txtPrice, txtTime;

		/**
		 * Instantiates a new view holder child.
		 * 
		 * @param v
		 *            the v
		 */
		ViewHolderChild(View v) {
			txtItem = (TextView) v.findViewById(R.id.txtItem);
			txtCategory = (TextView) v.findViewById(R.id.txtCategory);
			txtPrice = (TextView) v.findViewById(R.id.txtPrice);
			txtTime = (TextView) v.findViewById(R.id.txtTime);

//			txtCategory.setTypeface(((MainActivity) mContext).getRegularFont());
//			txtItem.setTypeface(((MainActivity) mContext).getRegularFont());
//			txtPrice.setTypeface(((MainActivity) mContext).getMediumFont());
//			txtTime.setTypeface(((MainActivity) mContext).getRegularFont());
		}
	}

}