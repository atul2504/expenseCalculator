package com.example.expenses.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalulator.R;
import com.example.expensecalulator.activity.MainActivity;

public class AlltemListAdapter extends BaseAdapter {

	private Context mContext;
	LayoutInflater inflater;
	ArrayList<AllItemModel> mData;
	private final static String AM = " AM";
	private final static String PM = " PM";

	public AlltemListAdapter(Context context, ArrayList<AllItemModel> data) {
		this.mContext = context;
		this.mData = data;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// Returns size of the ArrayList
		((MainActivity) mContext).d("getCont", "" + mData.size());
		return mData.size();
	}

	@Override
	public AllItemModel getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AllItemModel item = getItem(position);
		((MainActivity) mContext).d("getItem", "" + item.getItem());

		if (item.getDescription() != null && item.getDescription().length() > 0) {
			holder.txtCategory.setText(item.getDescription());
		} else {
			holder.txtCategory.setText(item.getCategoryName());
		}

		holder.txtItem.setText(item.getItem());

		holder.txtPrice.setText(item.getFormattedPrice());

		String timeText = item.getHours() + ":" + item.getMinutes()
				+ (Integer.parseInt(item.getHours()) < 12 ? AM : PM);

		holder.txtTime.setText(timeText);

		return convertView;
	}

	private class ViewHolder {
		TextView txtCategory, txtItem, txtPrice, txtTime;

		public ViewHolder(View view) {
			txtCategory = (TextView) view.findViewById(R.id.txtCategory);
			txtItem = (TextView) view.findViewById(R.id.txtItem);
			txtPrice = (TextView) view.findViewById(R.id.txtPrice);
			txtTime = (TextView) view.findViewById(R.id.txtTime);

//			txtCategory.setTypeface(((MainActivity) mContext).getRegularFont());
//			txtItem.setTypeface(((MainActivity) mContext).getRegularFont());
//			txtPrice.setTypeface(((MainActivity) mContext).getMediumFont());
//			txtTime.setTypeface(((MainActivity) mContext).getRegularFont());

		}
	}

}
