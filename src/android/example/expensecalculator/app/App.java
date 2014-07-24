package android.example.expensecalculator.app;

import java.lang.reflect.Field;

import android.app.Application;
import android.example.expensecalculator.database.AllItemsDB;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

public class App extends Application {

	private static App app;
	private static AllItemsDB db;
	private static String TAG = "APP-";

	public static App getInstance() {
		return app;
	}

	public static AllItemsDB getDb() {

		if (db == null) {
			db = new AllItemsDB(app);
		}

		return db;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		app = this;
		replaceFont();
	}

	private void replaceFont() {
		try {
			Typeface typeface;
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
				typeface = Typefaces.get(this, "fonts/Roboto-Regular.ttf");
			else
				typeface = Typeface.createFromAsset(getAssets(),
						"fonts/Roboto-Regular.ttf");

			final Field defaultField = Typeface.class
					.getDeclaredField("MONOSPACE");
			defaultField.setAccessible(true);
			defaultField.set(null, typeface);
		} catch (NoSuchFieldException e) {
			Log.e(TAG, e.toString());
		} catch (IllegalAccessException e) {
			Log.e(TAG, e.toString());
		}
	}

}
