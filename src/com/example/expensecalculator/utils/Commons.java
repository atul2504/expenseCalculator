package com.example.expensecalculator.utils;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.example.expensecalculator.app.App;
import android.example.expensecalculator.base._Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalulator.R;

public class Commons {

	/**
	 * 
	 * Constants
	 */

	public final static String APP_URL = "https://play.google.com/store/apps/details?id=at.markushi.expensemanager&hl=en";

	public final static String CRITICISM = "53ce6f2507229a4134000003";

	public final static String FLAG_SETTINGS_TYPE = "settingsType";
	public static final String PREF_CURRENCY = "pref_curreny";
	public static final String PREF_GROUPING = "pref_grouping";
	public static final String PREF_CUSTOM = "pref_custom";
	public static final String PREF_REMINDER = "pref_reminder";
	public static final String PREF_ALARM = "pref_alarm";
	public static final String PREF_IS_FIRST = "pref_first";

	public static final int FLAG_CURRENCY = 100;
	public static final int FLAG_GROUPING = 101;
	public static final int FLAG_REMINDER = 102;
	public static final int FLAG_ABOUT = 103;

	public static final int FLAG_WEEK = 200;
	public static final int FLAG_MONTH = 201;
	public static final int FLAG_YEAR = 202;
	public static final int FLAG_RUPPEE = 203;
	public static final int FLAG_DOLLAR = 204;
	public static final int FLAG_EURO = 205;
	public static final int FLAG_YEN = 206;
	public static final int FLAG_YUAN = 207;
	public static final int FLAG_POUND = 208;
	public static final int FLAG_CUSTOM = 209;

	public static String stringFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	public static Bitmap bitmapFromString(String image) {
		byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
				encodeByte.length);
		return bitmap;
	}

	public static boolean isBlank(EditText txt) {

		if (txt.getText().toString() != null
				&& txt.getText().toString().length() > 0) {
			return false;
		}

		return true;
	}

	public static boolean isCatrgorySelected(Spinner spin) {

		if (spin.getSelectedItemPosition() != 0) {
			return false;
		}
		return true;
	}

	public static int getDrawableIntId(String drawable) {

		int identifier = App
				.getInstance()
				.getResources()
				.getIdentifier(drawable, "drawable",
						App.getInstance().getPackageName());
		return identifier;
	}

	public static Calendar getCalendarInstance() {
		Calendar calendar = GregorianCalendar.getInstance();
		return calendar;
	}

	public static String getDate() {

		return String.valueOf(getCalendarInstance().get(Calendar.DAY_OF_MONTH));
	}

	public static String getDay() {
		return new SimpleDateFormat("MMMM").format(getCalendarInstance()
				.getTime());
	}

	public static String getWOM() {
		return String.valueOf(getCalendarInstance().get(Calendar.WEEK_OF_YEAR));
	}

	public static String getMonth() {
		return String.valueOf(getCalendarInstance().get(Calendar.MONTH));
	}

	public static String getYear() {
		return String.valueOf(getCalendarInstance().get(Calendar.YEAR));
	}

	public static String getHours() {
		return pad(String.valueOf(getCalendarInstance().get(
				Calendar.HOUR_OF_DAY)));
	}

	public static String getMinutes() {
		return pad(String.valueOf(getCalendarInstance().get(Calendar.MINUTE)));
	}

	public static String getSeconds() {
		return pad(String.valueOf(getCalendarInstance().get(Calendar.SECOND)));
	}

	public static String calculateTotalExpense(
			ArrayList<AllItemModel> mArrItemModels) {
		float expense = 0.0f;
		for (AllItemModel itemModel : mArrItemModels) {
			expense = expense + Float.parseFloat(itemModel.getPrice());
		}

		return updateTextExpense(expense);

	}

	public static String getFirstDayOfWeek(int dayOfWeek) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_YEAR, dayOfWeek);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.YEAR, 2014);

		return sdf.format(calendar.getTime());
	}

	public static String updateTextExpense(Float expense) {
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		return df.format(expense);
	}

	public static String pad(String str) {
		int c = Integer.parseInt(str);
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@SuppressLint("NewApi")
	public static void animateBackground(CompoundButton buttonView,
			int beginColor, int endColor, int duration) {

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			ObjectAnimator objectAnimator = ObjectAnimator.ofObject(buttonView,
					"backgroundColor", new ArgbEvaluator(), beginColor,
					endColor);
			objectAnimator.setDuration(duration);
			objectAnimator.start();
		} else {
			buttonView.setBackgroundColor(endColor);
		}
	}

	public static void setCustomTypeFace(Typeface typeface, View[] view) {
//		for (int i = 0; i < view.length; i++) {
//			if (view[i] instanceof TextView) {
//				((TextView) view[i]).setTypeface(typeface);
//			} else if (view[i] instanceof EditText) {
//				((EditText) view[i]).setTypeface(typeface);
//			} else if (view[i] instanceof Button) {
//				((Button) view[i]).setTypeface(typeface);
//			}
//		}
	}

	public static String getCurrencySymbol(Context context) {
		String currency = context.getString(R.string.cur_ruppee);

		if (getCurrencyFlag(context) == FLAG_RUPPEE) {
			currency = context.getString(R.string.cur_ruppee);
		} else if (getCurrencyFlag(context) == FLAG_DOLLAR) {
			currency = context.getString(R.string.cur_dollar);
		} else if (getCurrencyFlag(context) == FLAG_EURO) {
			currency = context.getString(R.string.cur_euro);
		} else if (getCurrencyFlag(context) == FLAG_YEN) {
			currency = context.getString(R.string.cur_yen);
		} else if (getCurrencyFlag(context) == FLAG_YUAN) {
			currency = context.getString(R.string.cur_yuan);
		} else if (getCurrencyFlag(context) == FLAG_POUND) {
			currency = context.getString(R.string.cur_pound);
		} else if (getCurrencyFlag(context) == FLAG_CUSTOM) {
			currency = _Activity.myPrefs.getString(PREF_CUSTOM,
					context.getString(R.string.cur_ruppee));
		}
		return currency;
	}

	public static int getCurrencyFlag(Context context) {
		return _Activity.myPrefs.getInt(PREF_CURRENCY, FLAG_RUPPEE);
	}

	public static String getGroupingSymbol(Context context) {
		return _Activity.myPrefs.getString(PREF_GROUPING,
				context.getString(R.string.group_week))
				+ " ";
	}

	public static String getReminderSymbol(Context context) {
		return _Activity.myPrefs.getString(PREF_REMINDER,
				context.getString(R.string.alarm_default));
	}

	public static String getFormattedAlarm(Context context) {
		String[] time = _Activity.myPrefs.getString(PREF_REMINDER,
				context.getString(R.string.alarm_default)).split(":");
		if (time.length > 0 && Integer.parseInt(time[0]) > 11) {
			return _Activity.myPrefs.getString(PREF_REMINDER,
					context.getString(R.string.alarm_default))
					+ " PM";
		} else {
			return _Activity.myPrefs.getString(PREF_REMINDER,
					context.getString(R.string.alarm_default))
					+ " AM";
		}

	}

	public static boolean isAlarmON(Context context) {
		return _Activity.myPrefs.getBoolean(PREF_ALARM, false);
	}

	public static boolean isFirst(Context context) {
		return _Activity.myPrefs.getBoolean(PREF_IS_FIRST, true);
	}

	public static int getGroupingFlag(Context context) {
		int flag = FLAG_WEEK;

		if (getGroupingSymbol(context).trim().equals(
				context.getString(R.string.group_month))) {
			flag = FLAG_MONTH;
		} else if (getGroupingSymbol(context).trim().equals(
				context.getString(R.string.group_year))) {
			flag = FLAG_YEAR;
		}

		return flag;
	}

	public static Animation getBlinkAnimation() {
		final Animation animation = new AlphaAnimation(1, 0);
		animation.setDuration(500);
		animation.setInterpolator(new LinearInterpolator());
		animation.setRepeatCount(Animation.INFINITE);
		animation.setRepeatMode(Animation.REVERSE);
		return animation;
	}

	public static TextView getHeaderView(Context context, String title,
			Typeface typeface) {
		TextView textView = new TextView(context);
		textView.setText(title);
		if (typeface != null) {
			textView.setTypeface(typeface);
		}
		return textView;
	}
}
