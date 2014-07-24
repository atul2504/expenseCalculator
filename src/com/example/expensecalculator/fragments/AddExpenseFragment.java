package com.example.expensecalculator.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.example.expensecalculator.base._Fragment;
import android.example.expensecalculator.database.AllItemsDB;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.expensecalculator.models.AllItemModel;
import com.example.expensecalculator.models.CategoryModel;
import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;
import com.example.expensecalulator.activity.MainActivity;
import com.example.expenses.adapters.MySpinnerAdapter;
import com.exmple.expenses.interfaces.UpdateUi;

public class AddExpenseFragment extends _Fragment implements OnClickListener,
		OnItemSelectedListener, OnLongClickListener, OnEditorActionListener {

	private EditText edtItem, edtPrice, edtDescription;
	private Button btnSave, btnReset;
	private Spinner mSpinCategory;
	private MySpinnerAdapter mSpinnerAdapter;
	private ArrayList<CategoryModel> mACategoryModels;
	private UpdateUi mUpdateUi;

	public static boolean isEditing = false;
	private int ID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Creates the View for the Add Expense Fragment
		View view = inflater.inflate(R.layout.fragment_add_item_expense, null);

		// Initialize views
		initComponents(view);

		return view;
	}

	@Override
	public void onClick(View buttonView) {
		// Handle Click events for Save and Reset button

		switch (buttonView.getId()) {

		// If save button was clicked
		case R.id.btnSave:

			if (isEditing) {
				updateEditedItemData(ID);
			} else {
				addItemData();
			}

			break;

		// If reset button was clicked
		case R.id.btnReset:
			resetViewData();
			if (isEditing) {
				isEditing = false;
				// txtTitle.setText(getString(R.string.title_add));
				btnReset.setText(getString(R.string.btn_reset));
				((MainActivity) getActivity()).pager.setCurrentItem(1);
				this.toastSmall("Edit Cancelled!");
			}
			break;

		default:
			break;
		}

	}

	public boolean getIsEditing() {
		return isEditing;
	}

	private void updateEditedItemData(int id) {
		AllItemModel itemModel = new AllItemModel();
		itemModel.setItem(edtItem.getText().toString());
		itemModel.setPrice(edtPrice.getText().toString());
		itemModel.setDescription(edtDescription.getText().toString());
		itemModel.setDate(Commons.getDate());
		itemModel.setDay(Commons.getDay());
		itemModel.setMonth(Commons.getMonth());
		itemModel.setYear(Commons.getYear());
		itemModel.setWom(Commons.getWOM());
		itemModel.setHours(Commons.getHours());
		itemModel.setMinutes(Commons.getMinutes());
		itemModel.setSeconds(Commons.getSeconds());

		CategoryModel category = getCategoryObject(mSpinCategory
				.getSelectedItemPosition());
		itemModel.setCategoryId(category.getCategoryId());
		itemModel.setCategoryName(category.getCategoryName());
		long result = AllItemsDB.updateItemWithId(id, itemModel);
		if (result > 0) {
			this.toastSmall("Edited successfully" + "\n ID = " + id);
			mUpdateUi.updateToday();
		} else {
			this.toastSmall("Error occurred while editing. Try Again.");
		}
		isEditing = false;
		notifyEditPage();
	}

	private void addItemData() {
		if (verifyData()) {
			addExpenseItemToData();
		}
	}

	public void editExpense(AllItemModel model) {

		isEditing = true;

		ID = model.getId();

		if (model.getItem() != null) {
			edtItem.setText(model.getItem());
		} else {
			edtItem.setText("");
		}

		if (model.getPrice() != null) {
			edtPrice.setText(model.getPrice());
		} else {
			edtPrice.setText("");
		}

		if (model.getDescription() != null) {
			edtDescription.setText(model.getDescription());
		} else {
			edtDescription.setText("");
		}

		if (model.getCategoryId() > 0) {
			mSpinCategory.setSelection(model.getCategoryId() - 1);
			Log.d("Cat ID EDIt", "" + model.getCategoryId());
		} else {
			mSpinCategory.setSelection(0);
		}

		// txtTitle.setText(getString(R.string.title_edit));

		btnReset.setText(getString(R.string.btn_cancel));
	}

	public static AddExpenseFragment newInstance(int index) {
		AddExpenseFragment f = new AddExpenseFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		args.putString("title", "PAge #" + index);
		f.setArguments(args);
		return f;
	}

	public void initComponents(View view) {
		// Initializes all the views for this fragment

		mUpdateUi = (UpdateUi) getActivity();

		mACategoryModels = new ArrayList<CategoryModel>();

		edtItem = (EditText) view.findViewById(R.id.edtItem);
		edtPrice = (EditText) view.findViewById(R.id.edtPrice);

		edtDescription = (EditText) view.findViewById(R.id.edtDescription);
		edtPrice.setOnEditorActionListener(this);
		// edtDescription.setOnEditorActionListener(this);

		mSpinCategory = (Spinner) view.findViewById(R.id.spnCategory);
		mSpinnerAdapter = new MySpinnerAdapter(getActivity(),
				R.layout.spinner_layout, mACategoryModels);
		mSpinCategory.setAdapter(mSpinnerAdapter);
		mSpinCategory.setOnItemSelectedListener(this);
		mSpinCategory.setOnLongClickListener(this);

		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnReset = (Button) view.findViewById(R.id.btnReset);

//		View[] views = { edtItem, edtPrice, edtDescription, btnSave, btnReset };
//		Commons.setCustomTypeFace(getRegularFont(), views);

		btnSave.setOnClickListener(this);
		btnReset.setOnClickListener(this);
	}

	public boolean verifyData() {
		if (Commons.isBlank(edtItem)) {
			toastSmall(getString(R.string.no_item));
			return false;
		} else if (Commons.isBlank(edtPrice)) {
			toastSmall(getString(R.string.no_price));
			return false;
			// } else if (Commons.isBlank(edtDescription)) {
			// AddExpenseFragment.this
			// .toastSmall(getString(R.string.no_description));
			// return false;
		} else if (Commons.isCatrgorySelected(mSpinCategory)) {
			toastSmall(getString(R.string.no_category));
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();

		int identifier = getActivity().getResources().getIdentifier(
				"ic_launcher", "drawable", getActivity().getPackageName());
		Log.d("identifier", getActivity().getPackageName() + " - " + identifier);

		refreshCategories();
	}

	public void refreshCategories() {
		// Fetches all the Categories from the database
		mACategoryModels.clear();
		mACategoryModels.addAll(AllItemsDB.getAllCategories());
		// this.toastSmall("Category count = " + mACategoryModels.size()
		// + " - ID - " + mACategoryModels.get(0).getCategoryId());
		mSpinnerAdapter.notifyDataSetChanged();
	}

	private void resetViewData() {
		// Resets all the views data
		this.d("Reset", "I got reset");
		edtItem.setText("");
		edtPrice.setText("");
		edtDescription.setText("");
		mSpinCategory.setSelection(0);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// this.toastSmall("" + mACategoryModels.get(position).getCategoryId());
		// if (!(edtDescription.getText().toString().length() > 0)) {
		// showSoftKeyBoard(edtDescription);
		// }

		if (position > 0) {
			edtDescription.requestFocus();
		}

		// showSoftKeyBoard(edtDescription);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		this.toastSmall("Not selected");

	}

	@Override
	public boolean onLongClick(View view) {

		Spinner spin = (Spinner) view;
		int pos = spin.getSelectedItemPosition();
		if (pos > 0) {
			CategoryModel categoryToEdit = getCategoryObject(pos);
			showEditCategoryDialog(categoryToEdit.getCategoryId(),
					categoryToEdit.getCategoryName());
		}
		// this.toastSmall("" + pos);
		return true;
	}

	private CategoryModel getCategoryObject(int pos) {
		return mSpinnerAdapter.getItem(pos);
	}

	public void showEditCategoryDialog(int id, String catName) {
		final int ID = id;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getString(R.string.category_title));

		final EditText editCategory = new EditText(getActivity());

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		editCategory.setLayoutParams(lp);
		editCategory.setTextSize(25);
		editCategory.setText(catName);
		editCategory.setHint(getActivity().getString(R.string.category_hint));
		builder.setView(editCategory);

		builder.setPositiveButton(
				getActivity().getString(R.string.category_positive),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Call update Category method of category table
						if (AllItemsDB.updateCategoryName(ID, editCategory
								.getText().toString()) > 0) {
							((MainActivity) getActivity())
									.toastSmall("Updated successfully!");
						} else {
							((MainActivity) getActivity())
									.toastSmall("Error Updating!");
						}
						dialog.dismiss();
						refreshCategories();

					}
				});

		builder.setNegativeButton(
				getActivity().getString(R.string.category_negative),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Cancels the dialog
						dialog.dismiss();
					}
				});
		builder.create();
		builder.show();
	}

	public void addExpenseItemToData() {
		AllItemModel itemModel = new AllItemModel();

		itemModel.setItem(edtItem.getText().toString());
		itemModel.setPrice(edtPrice.getText().toString());
		itemModel.setDescription(edtDescription.getText().toString());
		itemModel.setDate(Commons.getDate());
		itemModel.setDay(Commons.getDay());
		itemModel.setMonth(Commons.getMonth());
		itemModel.setWom(Commons.getWOM());
		itemModel.setYear(Commons.getYear());
		itemModel.setMinutes(Commons.getMinutes());
		itemModel.setHours(Commons.getHours());
		itemModel.setSeconds(Commons.getSeconds());

		CategoryModel category = getCategoryObject(mSpinCategory
				.getSelectedItemPosition());
		itemModel.setCategoryId(category.getCategoryId());
		Log.d("Cat ID", "" + category.getCategoryId());
		itemModel.setCategoryName(category.getCategoryName());

		long result = AllItemsDB.insertExpenseItem(itemModel);
		if (result > 0) {
			this.toastSmall("Row inserted successfully" + "\n ID = " + result);
			resetViewData();
			mUpdateUi.updateToday();
		} else {
			this.toastSmall("Error occurred while adding row. Try Again.");
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		boolean handled = false;
		switch (v.getId()) {
		case R.id.edtPrice:
			if (actionId == EditorInfo.IME_ACTION_NEXT) {
				hideSoftKeyBoard(v);
				mSpinCategory.requestFocus();
				// mSpinCategory.performClick();
			}
			break;

		case R.id.edtDescription:
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				if (isEditing) {
					updateEditedItemData(ID);
				} else {
					addItemData();
				}
			}
			break;
		}
		return handled;
	}

	public void notifyEditPage() {
		// TODO Auto-generated method stub
		isEditing = false;

		// txtTitle.setText(getString(R.string.title_add));
		btnReset.setText(getString(R.string.btn_reset));
		resetViewData();
	}
}
