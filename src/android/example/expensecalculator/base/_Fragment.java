package android.example.expensecalculator.base;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class _Fragment extends Fragment {

	private Typeface thinFont, normalFont, boldFont, mediumFont;

	public void toastSmall(String msg) {
		((_Activity) getActivity()).toastSmall(msg);
	}

	public void toastLong(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	public void d(String TAG, String msg) {
		Log.d(TAG, msg);
	}

	public void i(String TAG, String msg) {
		Log.i(TAG, msg);
	}

	public void e(String TAG, String msg) {
		Log.e(TAG, msg);
	}

	/**
	 * hides the soft keyboard
	 */
	public void hideSoftKeyBoard(View view) {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * show the soft keyboard
	 */
	public void showSoftKeyBoard(View view) {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	public Typeface getRegularFont() {
		if (normalFont == null) {
			normalFont = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/Roboto-Regular.ttf");
		}
		return normalFont;
	}

	public Typeface getThinFont() {
		if (boldFont == null) {
			boldFont = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/Roboto-Thin.ttf");
		}
		return boldFont;
	}

	public Typeface getBoldFont() {
		if (boldFont == null) {
			boldFont = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/Roboto-Bold.ttf");
		}
		return boldFont;
	}
}
