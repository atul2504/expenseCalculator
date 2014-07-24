package android.example.expensecalculator.base;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensecalulator.R;

// TODO: Auto-generated Javadoc
/**
 * The Class _Activity.
 */
public class _Activity extends ActionBarActivity {

	/** The text. */
	private TextView text;

	private Typeface thinFont, normalFont, boldFont, mediumFont;

	/** The toast. */
	private Toast toast;

	/** The my prefs. */
	public static SharedPreferences myPrefs;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		myPrefs = getSharedPreferences(getString(R.string.app_name),
				MODE_PRIVATE);

		View layout = getLayoutInflater().inflate(R.layout.layout_toast, null);

		text = (TextView) layout.findViewById(R.id.toastText);

		// Crittercism.initialize(this, Commons.CRITICISM);

		toast = new Toast(getApplicationContext());
		toast.setView(layout);

	}

	public void setRobotoTitle() {
		int actionBarTitle = Resources.getSystem().getIdentifier(
				"action_bar_title", "id", "android");
		TextView actionBarTitleView = (TextView) getWindow().findViewById(
				actionBarTitle);
		Typeface robotoBoldCondensedItalic = Typeface.createFromAsset(
				getAssets(), "fonts/RobotoCondensed-Regular.ttf");
		if (actionBarTitleView != null) {
			actionBarTitleView.setTypeface(robotoBoldCondensedItalic);
			actionBarTitleView.setTextColor(Color.WHITE);
		}
	}

	/**
	 * Toast small.
	 * 
	 * @param msg
	 *            the msg
	 */
	public void toastSmall(String msg) {
		text.setText(msg);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * Toast long.
	 * 
	 * @param msg
	 *            the msg
	 */
	public void toastLong(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * D.
	 * 
	 * @param TAG
	 *            the tag
	 * @param msg
	 *            the msg
	 */
	public void d(String TAG, String msg) {
		Log.d(TAG, msg);
	}

	/**
	 * I.
	 * 
	 * @param TAG
	 *            the tag
	 * @param msg
	 *            the msg
	 */
	public void i(String TAG, String msg) {
		Log.i(TAG, msg);
	}

	/**
	 * E.
	 * 
	 * @param TAG
	 *            the tag
	 * @param msg
	 *            the msg
	 */
	public void e(String TAG, String msg) {
		Log.e(TAG, msg);
	}

	public Typeface getRegularFont() {
		if (normalFont == null) {
			normalFont = Typeface.createFromAsset(getAssets(),
					"fonts/Roboto-Regular.ttf");
		}
		return normalFont;
	}

	public Typeface getThinFont() {
		if (thinFont == null) {
			thinFont = Typeface.createFromAsset(getAssets(),
					"fonts/Roboto-Thin.ttf");
		}
		return thinFont;
	}

	public Typeface getMediumFont() {
		if (mediumFont == null) {
			mediumFont = Typeface.createFromAsset(getAssets(),
					"fonts/Roboto-Medium.ttf");
		}
		return mediumFont;
	}

	public Typeface getBoldFont() {
		if (boldFont == null) {
			boldFont = Typeface.createFromAsset(getAssets(),
					"fonts/Roboto-Bold.ttf");
		}
		return boldFont;
	}

}
