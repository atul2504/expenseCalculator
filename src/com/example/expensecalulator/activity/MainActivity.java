package com.example.expensecalulator.activity;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.example.expensecalculator.base._Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.expensecalculator.fragments.AddExpenseFragment;
import com.example.expensecalculator.fragments.ExpenseHistoryFragment;
import com.example.expensecalculator.fragments.TodaysExpenseFragment;
import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalculator.utils.Dialogs;
import com.example.expensecalulator.R;
import com.example.expenses.adapters.MyPagerAdapter;
import com.exmple.expenses.interfaces.UpdateUi;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends _Activity implements OnCheckedChangeListener,
		UpdateUi {

	/** The Constant NUM_PAGES. */
	public static final int NUM_PAGES = 3;

	/** The pager. */
	public ViewPager pager;
	// private RadioButton rbtnPage0, rbtnPage1, rbtnPage2;
	/** The rg pages. */
	private RadioGroup rgPages;
	// private List<RadioButton> paginationList;
	/** The adapter. */
	private MyPagerAdapter adapter;

	/** The dialog. */
	private Dialogs dialog;

	/** The manager. */
	private FragmentManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.example.expensecalculator.base._Activity#onCreate(android.os.
	 * Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		manager = getSupportFragmentManager();

		dialog = new Dialogs(this);

		//setRobotoTitle();

		// rgPages = (RadioGroup) findViewById(R.id.rgPages);
		// rbtnPage0 = (RadioButton) findViewById(R.id.rbtnPage0);
		// rbtnPage1 = (RadioButton) findViewById(R.id.rbtnPage1);
		// rbtnPage2 = (RadioButton) findViewById(R.id.rbtnPage2);

		/*
		 * paginationList = new ArrayList<RadioButton>(); if (savedInstanceState
		 * == null) { paginationList.add(rbtnPage0);
		 * paginationList.add(rbtnPage1); paginationList.add(rbtnPage2); }
		 */

		// rgPages.setOnCheckedChangeListener(this);

		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(manager);
		pager.setAdapter(adapter);

		pager.setOnPageChangeListener(mPageChangeListener);
		pager.setCurrentItem(1);

		pager.setPageMargin(20);

		pager.setPageTransformer(false, new PageTransformer() {
			@SuppressLint("NewApi")
			@Override
			public void transformPage(View v, float pos) {
				final float invt = Math.abs(Math.abs(pos) - 1);
				v.setAlpha(invt);
			}
		});

		// setPageSelection(pager.getCurrentItem());

		// hack for force use of action-overflow menu on devices with hardware
		// menu button
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}

	}

	/** The m page change listener. */
	private ViewPager.SimpleOnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

		public void onPageSelected(int position) {
			setPageSelection(position);
			hideSoftKeyBoard();
			if (position == 0) {
				setTitle(Commons.getGroupingSymbol(MainActivity.this)
						+ getString(R.string.app_history));
			} else if (position == 1) {
				setTitle(R.string.app_overview);
			} else if (position == 2) {

				AddExpenseFragment frag = (AddExpenseFragment) pager
						.getAdapter().instantiateItem(pager, 2);

				if (frag.getIsEditing()) {
					setTitle(R.string.title_edit);
				} else {
					setTitle(R.string.app_new);
				}

			}
		};

	};

	/**
	 * Sets the selection of the page indicator depending upon the page
	 * selected.
	 * 
	 * @param currentItem
	 *            the new page selection
	 */
	public void setPageSelection(int currentItem) {

		/*
		 * for (RadioButton page : paginationList) { page.setSelected(false);
		 * }this.d("currentItem", "" + currentItem);
		 * paginationList.get(currentItem).setSelected(true);
		 */

		// ((RadioButton) rgPages.getChildAt(currentItem)).setChecked(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_actions, menu);

		// // Get the root inflator.
		// LayoutInflater baseInflater = (LayoutInflater) getBaseContext()
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//
		// // Inflate your custom view.
		// View myCustomView = baseInflater.inflate(R.layout.layout_action_bar,
		// null);
		// ((TextView) myCustomView.findViewById(R.id.txtText))
		// .setTypeface(getRegularFont());
		//
		// menu.findItem(R.id.action_shopping).setActionView(myCustomView);

		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handles Action Bar menu items click events

		switch (item.getItemId()) {
		case R.id.action_shopping:
			dialog.showShoppingDialog();
			return true;

		case R.id.action_budget:
			dialog.showBudgetDialog();
			return true;

		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Takes user to Main Screen (center tab - 1 Todays expense) if on 0th or
	 * 2nd tab else exit app.
	 */
	@Override
	public void onBackPressed() {
		if (pager.getCurrentItem() == 0 || pager.getCurrentItem() == 2) {
			pager.setCurrentItem(1);
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * hides the soft keyboard when the drawer opens.
	 */
	private void hideSoftKeyBoard() {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(pager.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * Flip pages based on the radio button selected.
	 * 
	 * @param group
	 *            the group
	 * @param checkedId
	 *            the checked id
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// switch (checkedId) {
		// case R.id.rbtnPage0:
		// pager.setCurrentItem(0);
		// break;
		// case R.id.rbtnPage1:
		// pager.setCurrentItem(1);
		// break;
		// case R.id.rbtnPage2:
		// pager.setCurrentItem(2);
		// break;
		// default:
		// break;
		// }
	}

	/**
	 * Restart activity.
	 */
	public void restartActivity() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exmple.expenses.interfaces.UpdateUi#editTodaysExpense(com.example
	 * .expensecalculator.models.AllItemModel)
	 */
	@Override
	public void editTodaysExpense(AllItemModel model) {
		AddExpenseFragment frag = (AddExpenseFragment) pager.getAdapter()
				.instantiateItem(pager, 2);
		frag.editExpense(model);
		pager.setCurrentItem(2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exmple.expenses.interfaces.UpdateUi#updateToday()
	 */
	@Override
	public void updateToday() {
		TodaysExpenseFragment frag = (TodaysExpenseFragment) pager.getAdapter()
				.instantiateItem(pager, 1);
		frag.fetchTodaysExpenses();
		pager.setCurrentItem(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exmple.expenses.interfaces.UpdateUi#notifyEditMode()
	 */
	@Override
	public void notifyEditMode() {
		AddExpenseFragment frag = (AddExpenseFragment) pager.getAdapter()
				.instantiateItem(pager, 2);
		frag.notifyEditPage();

		// pager.setAdapter(adapter);
		pager.setCurrentItem(1);
	}

	@Override
	public void startAddFragment() {
		AddExpenseFragment frag = (AddExpenseFragment) pager.getAdapter()
				.instantiateItem(pager, 2);
		// frag.notifyEditPage();

		// pager.setAdapter(adapter);
		pager.setCurrentItem(2);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exmple.expenses.interfaces.UpdateUi#notifyHistoryUpdate()
	 */
	@Override
	public void notifyHistoryUpdate() {
		ExpenseHistoryFragment frag = (ExpenseHistoryFragment) pager
				.getAdapter().instantiateItem(pager, 0);
		frag.notifyHistoryUpdate();

		pager.setCurrentItem(1);
	}

	@Override
	public void notifyTodayUpdate() {
		TodaysExpenseFragment frag = (TodaysExpenseFragment) pager.getAdapter()
				.instantiateItem(pager, 1);
		frag.update();

	}

}
