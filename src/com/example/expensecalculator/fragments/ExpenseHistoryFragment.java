package com.example.expensecalculator.fragments;

import java.util.ArrayList;

import android.example.expensecalculator.base._Fragment;
import android.example.expensecalculator.database.AllItemsDB;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalculator.models.HistoryModel;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;
import com.example.expenses.adapters.AdapterHistory;
import com.exmple.expenses.interfaces.UpdateUi;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpenseHistoryFragment.
 */
public class ExpenseHistoryFragment extends _Fragment {

	/** The expandable listview. */
	private ExpandableListView lstHistory;

	/** The history model arraylist. */
	private ArrayList<HistoryModel> historyList;

	/** The adapter. */
	private AdapterHistory adapter;

	/** The no history txt. */
	private TextView txtNoHistory;

	private View divider;
	private UpdateUi mUpdateUi;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		historyList = new ArrayList<HistoryModel>();

		View v = inflater.inflate(R.layout.fragment_history, null);

		txtNoHistory = (TextView) v.findViewById(R.id.txtNoHistory);
//		txtNoHistory.setTypeface(getRegularFont());
		divider = (View) v.findViewById(R.id.divider);

		mUpdateUi = (UpdateUi) getActivity();

		lstHistory = (ExpandableListView) v.findViewById(R.id.listHistory);
		// lstHistory.setChildDivider(new
		// ColorDrawable(R.color.color_black_12));
		// lstHistory.setDividerHeight(1);
		adapter = new AdapterHistory(getActivity(), historyList);
		lstHistory.setAdapter(adapter);

		registerForContextMenu(lstHistory);

		return v;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_context, menu);

		MenuItem menuItem = menu.findItem(R.id.contextEditItem);
		menuItem.setVisible(false);

		ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition);
		int groupPosition = ExpandableListView
				.getPackedPositionGroup(info.packedPosition);
		int childPosition = ExpandableListView
				.getPackedPositionChild(info.packedPosition);

		// Show context menu for groups
		if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			menu.clear();
			// Show context menu for children
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

			AllItemModel item = historyList.get(groupPosition)
					.getArrayHistory().get(childPosition);

			String header = null;
			if (historyList.get(groupPosition).getFlag() == Commons.FLAG_WEEK) {
				if (Integer
						.parseInt(historyList.get(groupPosition).getHeader()) == Integer
						.parseInt(Commons.getWOM())) {
					header = getString(R.string.title_week);
				}
			}

			if (header == null) {
				header = historyList.get(groupPosition).getHeader();
			}

			if (item != null) {
				menu.setHeaderTitle(header + " - " + item.getItem());
			}

		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (getUserVisibleHint()) {
			ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item
					.getMenuInfo();

			int type = ExpandableListView
					.getPackedPositionType(info.packedPosition);
			int groupPosition = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			int childPosition = ExpandableListView
					.getPackedPositionChild(info.packedPosition);

			if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
				Toast.makeText(
						getActivity(),
						"Group - " + historyList.get(groupPosition).getHeader(),
						Toast.LENGTH_SHORT).show();

			} else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

				AllItemModel itemModel = historyList.get(groupPosition)
						.getArrayHistory().get(childPosition);
				if (AllItemsDB.deleteItemWithId(itemModel.getId()) > 0) {

					loadHistory();

					if (AddExpenseFragment.isEditing) {
						this.toastSmall("Deleted and Reset Edit page.");
						mUpdateUi.notifyEditMode();
					} else {
						this.toastSmall("Deleted Successfully!");
					}

					mUpdateUi.notifyTodayUpdate();

				} else {
					this.toastSmall("Error while deleting. Try Again!");
				}
			}
			return true;
		}

		return super.onContextItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		// unregisterForContextMenu(lstHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		loadHistory();
	}

	/**
	 * Load history data.
	 */
	private void loadHistory() {
		historyList.clear();
		historyList.addAll(AllItemsDB.getHistoryData(Commons
				.getGroupingFlag(getActivity())));
		if (historyList.size() > 0) {
			if (historyList.size() == 1)
				lstHistory.expandGroup(0);

			showList();
		} else {
			hideList();
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * Display History if data is present
	 */
	private void showList() {
		lstHistory.setVisibility(View.VISIBLE);
		divider.setVisibility(View.VISIBLE);
		txtNoHistory.setVisibility(View.GONE);
	}

	/**
	 * Hide History if data is absent
	 */
	private void hideList() {
		lstHistory.setVisibility(View.GONE);
		divider.setVisibility(View.GONE);
		txtNoHistory.setVisibility(View.VISIBLE);
	}

	/**
	 * New instance.
	 * 
	 * @param index
	 *            the index
	 * @return the expense history fragment
	 */
	public static ExpenseHistoryFragment newInstance(int index) {
		ExpenseHistoryFragment f = new ExpenseHistoryFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		args.putString("title", "Page #" + index);
		f.setArguments(args);
		return f;
	}

	/**
	 * Notify history update.
	 */
	public void notifyHistoryUpdate() {
		loadHistory();
	}

}
