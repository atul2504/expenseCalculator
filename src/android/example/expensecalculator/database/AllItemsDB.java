package android.example.expensecalculator.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.example.expensecalculator.app.App;
import android.util.Log;

import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalculator.models.CategoryModel;
import com.example.expensecalculator.models.HistoryModel;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;

public class AllItemsDB extends SQLiteOpenHelper {

	public static final String DB_NAME = "MyDatabase";
	public static int VERSION = 1;

	/**
	 * All Item Table name and its fields
	 */
	public static final String TABLE_ALL_ITEM = "AllItems";
	public static final String KEY_ITEM_ID = "_id";
	public static final String KEY_ITEM_NAME = "ItemName";
	public static final String KEY_ITEM_PRICE = "ItemPrice";
	public static final String KEY_ITEM_DESCRIPTION = "ItemDescription";
	public static final String KEY_ITEM_DATE = "Date";
	public static final String KEY_ITEM_DAY = "Day";
	public static final String KEY_ITEM_WOM = "Week_Of_Month";
	public static final String KEY_ITEM_MONTH = "Month";
	public static final String KEY_ITEM_YEAR = "Year";
	public static final String KEY_ITEM_HOURS = "Hours";
	public static final String KEY_ITEM_MINUTES = "Minutes";
	public static final String KEY_ITEM_SECONDS = "Seconds";

	/**
	 * Category Table name and its fields
	 */
	public static final String TABLE_CATEGORY = "Category";
	public static final String KEY_CAT_NAME = "CategoryName";
	public static final String KEY_CAT_ID = "CatID";
	public static final String KEY_CAT_IMAGE = "Item";
	public static final String KEY_CAT_VISISIBILTY = "Visible";

	// SQL statements

	/**
	 * Item Model Table
	 */
	public static final String CREATE_ALL_ITEM_TABLE = "create table "
			+ TABLE_ALL_ITEM + " ( " + KEY_ITEM_ID
			+ " integer primary key autoincrement, " + KEY_ITEM_NAME
			+ " varchar(255), " + KEY_ITEM_PRICE + " real, " + KEY_CAT_ID
			+ " integer, " + KEY_CAT_NAME + " varchar(255), " + KEY_ITEM_DATE
			+ " varchar(20), " + KEY_ITEM_DAY + " varchar(20), " + KEY_ITEM_WOM
			+ " varchar(20), " + KEY_ITEM_MONTH + " varchar(20), "
			+ KEY_ITEM_YEAR + " varchar(20), " + KEY_ITEM_HOURS
			+ " varchar(20), " + KEY_ITEM_MINUTES + " varchar(20), "
			+ KEY_ITEM_SECONDS + " varchar(20), " + KEY_ITEM_DESCRIPTION
			+ " varchar(255), " + "FOREIGN KEY(CatID) REFERENCES CatID(CatID)"
			+ ")";

	/**
	 * Category Model Table
	 */
	public static final String CREATE_CATEGORY_TABLE = "create table "
			+ TABLE_CATEGORY + " ( " + KEY_CAT_ID
			+ " integer primary key autoincrement, " + KEY_CAT_NAME
			+ " varchar(255), " + KEY_CAT_IMAGE + " varchar(255), "
			+ KEY_CAT_VISISIBILTY + " int default 1" + ")";

	public static final String DROP_ALL_ITEM_TABLE = "drop table if exists"
			+ TABLE_ALL_ITEM;
	public static final String DROP_CATEGORY_TABLE = "drop table if exists"
			+ TABLE_CATEGORY;

	public AllItemsDB(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_ALL_ITEM_TABLE);
		db.execSQL(CREATE_CATEGORY_TABLE);

		insertInitialCategories(db);

		Log.d("onCreate", "Table Created and Categories inserted");
	}

	private void insertInitialCategories(SQLiteDatabase db) {
		ArrayList<CategoryModel> arrayCategory = new ArrayList<CategoryModel>();

		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_default), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_auto), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_bill), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_book), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_cloth), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_electronic), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_entertainment), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_food), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_gift), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_medical), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_personal), App.getInstance().getString(
				R.string.img_auto)));
		arrayCategory.add(new CategoryModel(App.getInstance().getString(
				R.string.cat_recharge), App.getInstance().getString(
				R.string.img_auto)));

		/**
		 * Inserts all the default category into the database
		 */
		// for (CategoryModel categoryModel : arrayCategory) {
		// insertCategory(categoryModel);
		// }

		insertAllCategories(arrayCategory, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_ALL_ITEM_TABLE);
		db.execSQL(DROP_CATEGORY_TABLE);
		onCreate(db);
	}

	/**
	 * Category Table
	 */
	public static long insertAllCategories(ArrayList<CategoryModel> array,
			SQLiteDatabase db) {

		long result = 0;
		for (CategoryModel category : array) {

			ContentValues values = new ContentValues();
			values.put(AllItemsDB.KEY_CAT_NAME, category.getCategoryName());
			values.put(AllItemsDB.KEY_CAT_IMAGE, category.getCategoryImage());
			result = result + db.insert(TABLE_CATEGORY, null, values);
		}
		Log.v("Total Categories inserted", "" + result);
		return result;
	}

	public static long insertCategory(CategoryModel category) {
		SQLiteDatabase db = App.getDb().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AllItemsDB.KEY_CAT_NAME, category.getCategoryName());
		values.put(AllItemsDB.KEY_CAT_IMAGE, category.getCategoryImage());
		long result = db.insert(TABLE_CATEGORY, null, values);

		return result;
	}

	public static ArrayList<CategoryModel> getAllCategories() {
		ArrayList<CategoryModel> arrayCategory = new ArrayList<CategoryModel>();

		SQLiteDatabase db = App.getDb().getReadableDatabase();

		Cursor c = db.rawQuery("select * from " + TABLE_CATEGORY, null);
		if (c != null) {
			c.moveToFirst();
			do {

				CategoryModel category = new CategoryModel();

				category.setCategoryId(c.getInt(c.getColumnIndex(KEY_CAT_ID)));

				category.setCategoryName(c.getString(c
						.getColumnIndex(KEY_CAT_NAME)));

				category.setCategoryImage(c.getString(c
						.getColumnIndex(KEY_CAT_IMAGE)));

				category.setCategoryVisibilty(c.getInt(c
						.getColumnIndex(KEY_CAT_VISISIBILTY)));

				arrayCategory.add(category);

			} while (c.moveToNext());
		}

		return arrayCategory;
	}

	public static long updateCategoryName(int id, String catName) {
		SQLiteDatabase db = App.getDb().getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CAT_NAME, catName);
		return db.update(TABLE_CATEGORY, values, KEY_CAT_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * Item Table
	 */

	public static long insertExpenseItem(AllItemModel item) {
		SQLiteDatabase db = App.getDb().getWritableDatabase();
		ContentValues values = new ContentValues();

		insertValaues(values, item);

		Log.d("DB minutes", item.getMinutes());

		long result = db.insert(TABLE_ALL_ITEM, null, values);
		return result;
	}

	public static ArrayList<AllItemModel> getTodaysData(String date,
			String month, String year) {

		String orderBy = KEY_ITEM_HOURS + " DESC, " + KEY_ITEM_MINUTES
				+ " DESC, " + KEY_ITEM_SECONDS + " DESC";

		int id, catId;
		ArrayList<AllItemModel> todaysItem = new ArrayList<AllItemModel>();
		SQLiteDatabase db = App.getDb().getReadableDatabase();
		Cursor c = db.query(TABLE_ALL_ITEM, null, KEY_ITEM_DATE + " =? and "
				+ KEY_ITEM_MONTH + " =? and " + KEY_ITEM_YEAR + " =? ",
				new String[] { date, month, year }, null, null, orderBy);

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					todaysItem.add(getItemFromCursor(c));
				} while (c.moveToNext());

			}
		}

		return todaysItem;
	}

	public static ArrayList<HistoryModel> getHistoryData(int flag) {

		ArrayList<HistoryModel> arrayHistory = new ArrayList<HistoryModel>();

		List<String> headersList = new ArrayList<String>();

		headersList.addAll(getDates(flag));

		for (String date : headersList) {
			HistoryModel history = new HistoryModel();
			history.setHeader(date);
			history.setArrayHistory(getItemFromDate(date, flag));
			history.setFlag(flag);
			arrayHistory.add(history);
		}

		return arrayHistory;
	}

	public static int updateItemWithId(int id, AllItemModel item) {
		SQLiteDatabase db = App.getDb().getReadableDatabase();
		ContentValues values = new ContentValues();

		insertValaues(values, item);

		return db.update(TABLE_ALL_ITEM, values, KEY_ITEM_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public static int deleteItemWithId(int id) {
		SQLiteDatabase db = App.getDb().getReadableDatabase();
		return db.delete(TABLE_ALL_ITEM, KEY_ITEM_ID + " =?",
				new String[] { String.valueOf(id) });
	}

	// Fetches all data
	public static ArrayList<AllItemModel> getAllData() {
		ArrayList<AllItemModel> allItem = new ArrayList<AllItemModel>();
		SQLiteDatabase db = App.getDb().getReadableDatabase();
		// Cursor c = db.rawQuery("select * from " + TABLE_ALL_ITEM +
		// "order by " + , null);
		Cursor c = db.query(TABLE_ALL_ITEM, null, null, null, null, null,
				KEY_ITEM_DATE + " desc");
		if (c != null) {
			if (c.moveToFirst()) {

				do {

					allItem.add(getItemFromCursor(c));

				} while (c.moveToNext());
			}
		}
		return allItem;
	}

	private static AllItemModel getItemFromCursor(Cursor c) {
		AllItemModel item = new AllItemModel();

		item.setId(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
		item.setItem(c.getString(c.getColumnIndex(KEY_ITEM_NAME)));
		item.setPrice(c.getString(c.getColumnIndex(KEY_ITEM_PRICE)));
		item.setCategoryId(c.getInt(c.getColumnIndex(KEY_CAT_ID)));
		item.setCategoryName(c.getString(c.getColumnIndex(KEY_CAT_NAME)));
		item.setDescription(c.getString(c.getColumnIndex(KEY_ITEM_DESCRIPTION)));

		item.setDate(c.getString(c.getColumnIndex(KEY_ITEM_DATE)));
		item.setDay(c.getString(c.getColumnIndex(KEY_ITEM_DAY)));
		item.setWom(c.getString(c.getColumnIndex(KEY_ITEM_WOM)));
		item.setMinutes(c.getString(c.getColumnIndex(KEY_ITEM_MINUTES)));

		item.setMonth(c.getString(c.getColumnIndex(KEY_ITEM_MONTH)));
		item.setYear(c.getString(c.getColumnIndex(KEY_ITEM_YEAR)));
		item.setHours(c.getString(c.getColumnIndex(KEY_ITEM_HOURS)));
		item.setSeconds(c.getString(c.getColumnIndex(KEY_ITEM_SECONDS)));

		return item;
	}

	private static void insertValaues(ContentValues values, AllItemModel item) {
		values.put(AllItemsDB.KEY_ITEM_NAME, item.getItem());
		values.put(AllItemsDB.KEY_ITEM_PRICE, Float.parseFloat(item.getPrice()));
		values.put(AllItemsDB.KEY_CAT_ID, item.getCategoryId());
		values.put(AllItemsDB.KEY_CAT_NAME, item.getCategoryName());
		values.put(AllItemsDB.KEY_ITEM_DATE, item.getDate());
		values.put(AllItemsDB.KEY_ITEM_DAY, item.getDay());
		values.put(AllItemsDB.KEY_ITEM_WOM, item.getWom());
		values.put(AllItemsDB.KEY_ITEM_MONTH, item.getMonth());
		values.put(AllItemsDB.KEY_ITEM_YEAR, item.getYear());
		values.put(AllItemsDB.KEY_ITEM_HOURS, item.getHours());
		values.put(AllItemsDB.KEY_ITEM_MINUTES, item.getMinutes());
		values.put(AllItemsDB.KEY_ITEM_DESCRIPTION, item.getDescription());
		values.put(AllItemsDB.KEY_ITEM_SECONDS, item.getSeconds());
	}

	public static List<String> getDates(int flag) {
		ArrayList<String> dates = new ArrayList<String>();

		SQLiteDatabase db = App.getDb().getReadableDatabase();

		String orderBy = null, groupBy = null;

		Cursor c = null;

		if (flag == Commons.FLAG_WEEK) {

			orderBy = KEY_ITEM_YEAR + " DESC, " + KEY_ITEM_MONTH + " DESC, "
					+ KEY_ITEM_WOM + " DESC";

			groupBy = KEY_ITEM_YEAR + ", " + KEY_ITEM_MONTH + ", "
					+ KEY_ITEM_WOM;

			String columns[] = { KEY_ITEM_WOM };

			c = db.query(TABLE_ALL_ITEM, columns, null, null, groupBy, null,
					orderBy);

			if (c != null) {

				if (c.moveToFirst()) {
					do {
						dates.add(c.getString(c.getColumnIndex(KEY_ITEM_WOM)));
					} while (c.moveToNext());
				}
			}

		} else if (flag == Commons.FLAG_MONTH) {

			Log.d("LOGGG", "Inside MONTH");

			orderBy = KEY_ITEM_YEAR + " DESC, " + KEY_ITEM_MONTH + " DESC";

			groupBy = KEY_ITEM_YEAR + ", " + KEY_ITEM_MONTH;

			String columns[] = { KEY_ITEM_DAY };

			c = db.query(TABLE_ALL_ITEM, columns, null, null, groupBy, null,
					orderBy);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						Log.d("LOGGG", "INSIDE MONTH");
						dates.add(c.getString(c.getColumnIndex(KEY_ITEM_DAY)));
					} while (c.moveToNext());
				} else {
					Log.d("LOGGG", "OUTSIDE MONTH");
				}
			}

		} else if (flag == Commons.FLAG_YEAR) {

			Log.d("LOGGG", "Inside Year");

			orderBy = KEY_ITEM_YEAR + " DESC, " + KEY_ITEM_MONTH + " DESC";

			groupBy = KEY_ITEM_YEAR + ", " + KEY_ITEM_MONTH;

			String columns[] = { KEY_ITEM_YEAR };

			c = db.query(TABLE_ALL_ITEM, columns, null, null, groupBy, null,
					orderBy);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						Log.d("LOGGG", "INSIDE MONTH");
						dates.add(c.getString(c.getColumnIndex(KEY_ITEM_YEAR)));
					} while (c.moveToNext());
				} else {
					Log.d("LOGGG", "OUTSIDE MONTH");
				}
			}
		}

		// Cursor c = db.rawQuery("select " + KEY_ITEM_DATE + " from "
		// + TABLE_ALL_ITEM + " group by " + KEY_ITEM_DATE + " order by "
		// + KEY_ITEM_DATE + " desc", null);

		return dates;
	}

	public static ArrayList<AllItemModel> getItemFromDate(String date, int flag) {
		ArrayList<AllItemModel> items = new ArrayList<AllItemModel>();
		SQLiteDatabase db = App.getDb().getReadableDatabase();

		if (flag == Commons.FLAG_WEEK) {

			String orderBy = KEY_ITEM_DATE + " DESC, " + KEY_ITEM_HOURS
					+ " DESC, " + KEY_ITEM_MINUTES + " DESC, "
					+ KEY_ITEM_SECONDS + " DESC";

			Cursor c = db.rawQuery("select * from " + TABLE_ALL_ITEM
					+ " where " + KEY_ITEM_WOM + " = " + "'" + date + "' "
					+ "ORDER BY " + orderBy, null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						items.add(getItemFromCursor(c));
					} while (c.moveToNext());
				}
			}

		} else if (flag == Commons.FLAG_MONTH) {

			String orderBy = KEY_ITEM_DATE + " DESC, " + KEY_ITEM_HOURS
					+ " DESC, " + KEY_ITEM_MINUTES + " DESC, "
					+ KEY_ITEM_SECONDS + " DESC";

			Cursor c = db.rawQuery("select * from " + TABLE_ALL_ITEM
					+ " where " + KEY_ITEM_DAY + " = " + "'" + date + "' "
					+ "ORDER BY " + orderBy, null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						items.add(getItemFromCursor(c));
					} while (c.moveToNext());
				}
			}

		} else if (flag == Commons.FLAG_YEAR) {

			String orderBy = KEY_ITEM_MONTH + " DESC, " + KEY_ITEM_DATE
					+ " DESC, " + KEY_ITEM_HOURS + " DESC, " + KEY_ITEM_MINUTES
					+ " DESC, " + KEY_ITEM_SECONDS + " DESC";

			Cursor c = db.rawQuery("select * from " + TABLE_ALL_ITEM
					+ " where " + KEY_ITEM_YEAR + " = " + "'" + date + "' "
					+ "ORDER BY " + orderBy, null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						items.add(getItemFromCursor(c));
					} while (c.moveToNext());
				}
			}
		}

		return items;
	}
}
