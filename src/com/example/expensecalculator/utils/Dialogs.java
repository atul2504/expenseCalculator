package com.example.expensecalculator.utils;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.expensecalulator.R;

public class Dialogs {

	private Context context;
	private AlertDialog.Builder builder;

	public Dialogs(Context context) {
		super();
		this.context = context;
		builder = new AlertDialog.Builder(context);
	}

	public void showShoppingDialog() {

		builder.setTitle(context.getString(R.string.shoping_title));
		builder.setMessage(context.getString(R.string.shoping_msg));
		builder.setPositiveButton(context.getString(R.string.shoping_positive),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						Intent intent = new Intent(Intent.ACTION_EDIT);
						intent.setType("vnd.android.cursor.item/event");
						intent.putExtra("beginTime", cal.getTimeInMillis());
						intent.putExtra("allDay", true);
						context.startActivity(intent);
					}
				});

		builder.setNegativeButton(context.getString(R.string.shoping_negative),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		builder.create();
		builder.show();
	}

	public void showBudgetDialog() {
		builder.setTitle(context.getString(R.string.shoping_title));
		builder.setMessage(context.getString(R.string.shoping_msg));
		builder.setPositiveButton(context.getString(R.string.shoping_positive),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						Intent intent = new Intent(Intent.ACTION_EDIT);
						intent.setType("vnd.android.cursor.item/event");
						intent.putExtra("beginTime", cal.getTimeInMillis());
						intent.putExtra("allDay", true);
						context.startActivity(intent);
					}
				});

		builder.setNegativeButton(context.getString(R.string.shoping_negative),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		builder.create();
		builder.show();
	}

	public void showSettingsDialog() {
		builder.setTitle(context.getString(R.string.shoping_title));
		builder.setMessage(context.getString(R.string.shoping_msg));
		builder.setPositiveButton(context.getString(R.string.shoping_positive),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						Intent intent = new Intent(Intent.ACTION_EDIT);
						intent.setType("vnd.android.cursor.item/event");
						intent.putExtra("beginTime", cal.getTimeInMillis());
						intent.putExtra("allDay", true);
						context.startActivity(intent);
					}
				});

		builder.setNegativeButton(context.getString(R.string.shoping_negative),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		builder.create();
		builder.show();
	}

	public void showEditExpenseDialog() {

		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_edit_expense, null);

		// builder.setTitle(context.getString(R.string.shoping_title));

		builder.setView(view);

		builder.create();
		builder.show();
	}

}
