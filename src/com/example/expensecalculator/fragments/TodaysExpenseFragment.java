package com.example.expensecalculator.fragments;

import java.util.ArrayList;

import android.example.expensecalculator.base._Fragment;
import android.example.expensecalculator.database.AllItemsDB;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;
import com.example.expenses.adapters.AlltemListAdapter;
import com.exmple.expenses.interfaces.UpdateUi;

public class TodaysExpenseFragment extends _Fragment implements OnClickListener {

	private TextView txtExpense, txtNoExpense;
	private ListView lstTodaysExpenses;
	private ArrayList<AllItemModel> mArrItemModels;
	private AlltemListAdapter adapter;
	private float expense = 0.00f;
	private UpdateUi mUpdateUi;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Creates view for this fragment
		View view = inflater.inflate(R.layout.fragment_todays_expense, null);
		mArrItemModels = new ArrayList<AllItemModel>();
		txtExpense = (TextView) view.findViewById(R.id.txtExpense);
		txtExpense.setText(Commons.updateTextExpense(expense));
		txtExpense.setTypeface(getBoldFont());
		txtNoExpense = (TextView) view.findViewById(R.id.txtNoExpense);
		// txtNoExpense.setTypeface(getRegularFont());
		txtNoExpense.setOnClickListener(this);
		lstTodaysExpenses = (ListView) view.findViewById(R.id.listTodays);

		mUpdateUi = (UpdateUi) getActivity();

		// Adding header to the list
		// View headerView = inflater.inflate(R.layout.list_hearder, null);
		// headerView.setClickable(false);
		// headerView.setFocusable(false);
		// lstTodaysExpenses.addHeaderView(headerView);

		/*
		 * if(lstTodaysExpenses.getChildAt(0) == headerView){
		 * lstTodaysExpenses.setDivider(new
		 * ColorDrawable(getActivity().getResources
		 * ().getColor(R.color.color_dark_blue)));
		 * lstTodaysExpenses.setDividerHeight(1); }
		 */

		adapter = new AlltemListAdapter(getActivity(), mArrItemModels);
		lstTodaysExpenses.setAdapter(adapter);

		registerForContextMenu(lstTodaysExpenses);

		return view;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_context, menu);

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int position = (int) info.id;

		AllItemModel itemModel = mArrItemModels.get(position);
		if (itemModel != null) {
			menu.setHeaderTitle(itemModel.getItem());
		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (getUserVisibleHint()) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			int position = (int) info.id;

			if (position < 0) {
				return false;
			}

			Log.d("Position - ", "" + position);

			AllItemModel itemModel = mArrItemModels.get(position);

			switch (item.getItemId()) {
			case R.id.contextEditItem:

				mUpdateUi.editTodaysExpense(itemModel);
				// this.toastSmall(itemModel.getItem());
				break;

			case R.id.contextDeleteItem:
				if (AllItemsDB.deleteItemWithId(itemModel.getId()) > 0) {

					fetchTodaysExpenses();
					if (AddExpenseFragment.isEditing) {
						this.toastSmall("Deleted and Reset Edit page.");
						mUpdateUi.notifyEditMode();
					} else {
						this.toastSmall("Deleted Successfully!");
					}

					mUpdateUi.notifyHistoryUpdate();

				} else {
					this.toastSmall("Error while deleting. Try Again!");
				}
				break;
			}
			return true;
		}
		return super.onContextItemSelected(item);

	}

	@Override
	public void onPause() {
		super.onPause();
		// unregisterForContextMenu(lstTodaysExpenses);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fetchTodaysExpenses();
	}

	public void fetchTodaysExpenses() {
		// Gets todays expenses
		mArrItemModels.clear();
		mArrItemModels.addAll(AllItemsDB.getTodaysData(Commons.getDate(),
				Commons.getMonth(), Commons.getYear()));
		this.d("mArrItemModels", "" + mArrItemModels.size());
		if (mArrItemModels.size() > 0) {
			// this.d("mArrItemModels", "I am inside");
			// this.d("mArrItemModels", "I am outside" );
			showList(true);

		} else {
			// this.d("mArrItemModels", "I am else" );
			// lstTodaysExpenses.setVisibility(View.GONE);
			// txtNoExpense.setVisibility(View.VISIBLE);
			// Animation blink = Commons.getBlinkAnimation();
			// txtNoExpense.startAnimation(blink);

			showList(false);

		}
		adapter.notifyDataSetChanged();
		txtExpense.setText(Commons.getCurrencySymbol(getActivity()) + " "
				+ Commons.calculateTotalExpense(mArrItemModels));
	}

	private void showList(boolean show) {
		if (show) {
			lstTodaysExpenses.setVisibility(View.VISIBLE);
			txtNoExpense.setVisibility(View.GONE);
		} else {
			lstTodaysExpenses.setVisibility(View.GONE);
			txtNoExpense.setVisibility(View.VISIBLE);

			// SpannableString extra = new SpannableString(
			// getString(R.string.no_expense) + "\n\n"
			// + Html.fromHtml("<b>Add Expense</b>"));
			// ClickableSpan clickableSpan = new ClickableSpan() {
			//
			// @Override
			// public void onClick(View widget) {
			// Log.d("Inside", "Clickable");
			// mUpdateUi.startAddFragment();
			// }
			// };
			//
			// extra.setSpan(clickableSpan, 25, extra.length(),
			// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			//
			// txtNoExpense.setText(extra);
			// txtNoExpense.setMovementMethod(LinkMovementMethod.getInstance());

		}
	}

	public void update() {
		fetchTodaysExpenses();
		mUpdateUi.notifyEditMode();
	}

	public static TodaysExpenseFragment newInstance(int index) {
		TodaysExpenseFragment f = new TodaysExpenseFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		args.putString("title", "PAge #" + index);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.txtNoExpense) {
			v.clearAnimation();

		}
	}
}
