package com.example.expensecalulator.activity;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.example.expensecalculator.base._Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.expensecalculator.fragments.SettingsDialog;
import com.example.expensecalculator.fragments.SettingsDialog.Communicator;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;

public class SettingsActivity extends _Activity implements OnClickListener,
		Communicator {

	private TextView txtCurrency, txtGrouping, txtAbout, txtRate, txtFeedback,
			txtHeaderSettings, txtHeaderAbout, txtCurrencyValue,
			txtGroupingValue, txtTime, txtTimeValue, txtHeaderAlarm;
	private LinearLayout linearCurrency, linearGroups, linearTime;
	private CheckedTextView txtReminder;
	private TimePickerDialog timePickerDialog = null;
	private int hour, minute;
	private Editor editor;
	private AlarmManager alramMgr;
	private PendingIntent alarmIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// setRobotoTitle();

		editor = myPrefs.edit();
		alramMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		init();
	}

	private void init() {
		txtAbout = (TextView) findViewById(R.id.txtAboutUs);
		txtRate = (TextView) findViewById(R.id.txtRate);
		txtGrouping = (TextView) findViewById(R.id.txtGrouping);
		txtFeedback = (TextView) findViewById(R.id.txtFeedback);
		txtCurrency = (TextView) findViewById(R.id.txtCurrency);
		txtReminder = (CheckedTextView) findViewById(R.id.txtReminder);
		txtHeaderSettings = (TextView) findViewById(R.id.txtHeaderSettings);
		txtHeaderAbout = (TextView) findViewById(R.id.txtHeaderAbout);
		txtHeaderAlarm = (TextView) findViewById(R.id.txtHeaderAlarm);

		linearCurrency = (LinearLayout) findViewById(R.id.linearCurrency);
		linearGroups = (LinearLayout) findViewById(R.id.linearGroups);
		linearTime = (LinearLayout) findViewById(R.id.linearTime);

		txtTime = (TextView) findViewById(R.id.txtTime);
		txtTimeValue = (TextView) findViewById(R.id.txtTimeValue);
		txtTimeValue.setText(Commons.getFormattedAlarm(this));

		if (Commons.isFirst(this)) {
			txtReminder.setChecked(true);
			editor.putBoolean(Commons.PREF_IS_FIRST, false);
			editor.commit();
			setHourMinutes();
		} else {
			txtReminder.setChecked(Commons.isAlarmON(this));
			toggleTime(Commons.isAlarmON(this));
		}

		txtCurrencyValue = (TextView) findViewById(R.id.txtCurrencyValue);

		txtGroupingValue = (TextView) findViewById(R.id.txtGroupingValue);

//		TextView[] views = { txtAbout, txtRate, txtGrouping, txtFeedback,
//				txtCurrency, txtReminder, txtHeaderSettings, txtHeaderAbout,
//				txtHeaderAlarm, txtTime, txtTimeValue, txtCurrencyValue,
//				txtGroupingValue };

		// Commons.setCustomTypeFace(getRegularFont(), views);

		txtRate.setOnClickListener(this);
		txtAbout.setOnClickListener(this);
		linearTime.setOnClickListener(this);
		txtReminder.setOnClickListener(this);
		// txtCurrency.setOnClickListener(this);
		// txtGrouping.setOnClickListener(this);

		linearCurrency.setOnClickListener(this);
		linearGroups.setOnClickListener(this);

	}

	private void setHourMinutes() {
		String[] time = Commons.getReminderSymbol(this).split(":");
		if (time.length > 0) {
			hour = Integer.parseInt(time[0]);
			minute = Integer.parseInt(time[1]);
			setAlarm(hour, minute);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		txtCurrencyValue.setText(Commons.getCurrencySymbol(this));
		txtGroupingValue.setText(Commons.getGroupingSymbol(this));
	}

	@Override
	public void onClick(View v) {

		Bundle bundle = new Bundle();
		switch (v.getId()) {

		case R.id.linearTime:
			timePickerDialog = new TimePickerDialog(this, timePickerListener,
					Integer.parseInt(Commons.getHours()),
					Integer.parseInt(Commons.getMinutes()), true);

			timePickerDialog.show();
			return;

		case R.id.linearCurrency:
			bundle.putInt(Commons.FLAG_SETTINGS_TYPE, Commons.FLAG_CURRENCY);
			break;

		case R.id.linearGroups:
			bundle.putInt(Commons.FLAG_SETTINGS_TYPE, Commons.FLAG_GROUPING);
			break;

		case R.id.txtAboutUs:
			bundle.putInt(Commons.FLAG_SETTINGS_TYPE, Commons.FLAG_ABOUT);
			break;

		case R.id.txtReminder:
			txtReminder.toggle();
			toggleTime(txtReminder.isChecked());
			if (txtReminder.isChecked()) {
				setHourMinutes();
			} else {

				editor.putBoolean(Commons.PREF_ALARM, false);
				editor.commit();

				Intent intent = new Intent(this, AlarmReceiver.class);
				alarmIntent = PendingIntent.getBroadcast(this, 12345, intent,
						PendingIntent.FLAG_CANCEL_CURRENT);

				alramMgr.cancel(alarmIntent);
			}

			return;

		case R.id.txtRate:
			Intent intent2 = new Intent(Intent.ACTION_VIEW,
					Uri.parse(Commons.APP_URL));
			startActivity(intent2);
			return;

		}

		SettingsDialog dialog = new SettingsDialog();
		dialog.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(dialog, "DIALOG")
				.commit();

	}

	private void toggleTime(boolean toggle) {
		// txtTime.setEnabled(toggle);
		// txtTimeValue.setEnabled(toggle);
		linearTime.setEnabled(toggle);

		if (toggle) {
			txtTime.setTextColor(getResources()
					.getColor(R.color.color_black_87));
			txtTimeValue.setTextColor(getResources().getColor(
					R.color.color_black_87));

		} else {
			txtTime.setTextColor(getResources()
					.getColor(R.color.color_black_26));
			txtTimeValue.setTextColor(getResources().getColor(
					R.color.color_black_26));

		}
	}

	@Override
	public void update() {
		txtCurrencyValue.setText(Commons.getCurrencySymbol(this));
		txtGroupingValue.setText(Commons.getGroupingSymbol(this));
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			setAlarm(hour, minute);

			// set current time into textview
			editor.putString(
					Commons.PREF_REMINDER,
					new StringBuilder()
							.append(Commons.pad(String.valueOf(hour)))
							.append(":")
							.append(Commons.pad(String.valueOf(minute)))
							.toString());
			editor.commit();

			txtTimeValue.setText(Commons
					.getFormattedAlarm(SettingsActivity.this));

		}

	};

	private void setAlarm(int hour, int minute) {

		Calendar now = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);

		long alarmMillis = calendar.getTimeInMillis();

		// if (alarmMillis < now.getTimeInMillis()) {
		// Log.d("ALARM", "CANCELLED");
		// return;
		// }

		if (calendar.before(now) || calendar.equals(now))
			alarmMillis += 86400000L; // Add a day if alarm less than current
										// time

		Intent intent = new Intent(this, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(this, 12345, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		alramMgr.setRepeating(AlarmManager.RTC_WAKEUP, alarmMillis,
				AlarmManager.INTERVAL_DAY, alarmIntent);

		editor.putBoolean(Commons.PREF_ALARM, true);
		editor.commit();

	}
}
