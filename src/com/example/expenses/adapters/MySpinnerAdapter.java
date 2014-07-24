package com.example.expenses.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensecalculator.models.CategoryModel;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;

public class MySpinnerAdapter extends ArrayAdapter {

	private Context context;
	private ArrayList<CategoryModel> mACategoryModels;

	public MySpinnerAdapter(Context ctx, int txtViewResourceId,
			ArrayList<CategoryModel> mACategoryModels) {
		super(ctx, txtViewResourceId);
		this.context = ctx;
		this.mACategoryModels = mACategoryModels;
	}

	@Override
	public int getCount() {
		return mACategoryModels.size();
	}

	@Override
	public CategoryModel getItem(int position) {
		return mACategoryModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getDropDownView(int position, View cnvtView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View mySpinner = inflater.inflate(R.layout.spinner_layout, parent,
				false);
		TextView main_text = (TextView) mySpinner.findViewById(R.id.spnTitle);
		ImageView left_icon = (ImageView) mySpinner.findViewById(R.id.spnIcon);

		Log.d("Category Name " + position, mACategoryModels.get(position)
				.getCategoryName()
				+ " - "
				+ mACategoryModels.get(position).getCategoryImage());

		main_text.setText(mACategoryModels.get(position).getCategoryName());
		//main_text.setTypeface(((MainActivity) context).getRegularFont());

		left_icon.setImageResource(Commons.getDrawableIntId(mACategoryModels
				.get(position).getCategoryImage()));

		if (position == 0) {
			main_text.setTextColor(context.getResources().getColor(
					R.color.color_black_26));
			left_icon.setVisibility(View.INVISIBLE);
		} else {
			left_icon.setImageResource(R.drawable.entertainment);
		}

		/*
		 * if (position == 0) { main_text.setVisibility(View.GONE); } else if
		 * (position > 0) {
		 * 
		 * }
		 */

		mySpinner.setBackgroundColor(context.getResources().getColor(
				R.color.color_white));
		return mySpinner;
	}

	@Override
	public View getView(int position, View cnvtView, ViewGroup prnt) {

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View mySpinner = inflater.inflate(R.layout.spinner_layout, prnt, false);

		TextView main_text = (TextView) mySpinner.findViewById(R.id.spnTitle);
		//main_text.setTypeface(((MainActivity) context).getRegularFont());
		main_text.setText(mACategoryModels.get(position).getCategoryName());

		ImageView left_icon = (ImageView) mySpinner.findViewById(R.id.spnIcon);
		if (position == 0) {
			main_text.setTextColor(context.getResources().getColor(
					R.color.color_black_54));
			left_icon.setImageResource(R.drawable.spinner_down);
			left_icon.setVisibility(View.GONE);
		} else {
			left_icon.setVisibility(View.GONE);
		}

		// mySpinner.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.my_custom_edittext_bg));
		return mySpinner;
	}

}
