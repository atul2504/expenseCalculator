package com.example.expensecalculator.fragments;

import android.app.TimePickerDialog;
import android.content.SharedPreferences.Editor;
import android.example.expensecalculator.base._Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.expensecalculator.utils.Commons;
import com.example.expensecalulator.R;
import com.example.expensecalulator.activity.SettingsActivity;

public class SettingsDialog extends DialogFragment implements
		OnCheckedChangeListener, TextWatcher, OnClickListener,
		android.widget.CompoundButton.OnCheckedChangeListener {

	private RadioGroup grpCurrency1, grpCurrency2, grpSorting;
	private RadioButton rbRuppee, rbDollar, rbEuro, rbYen, rbPound, rbYuan,
			rbWeek, rbMonth, rbYear;
	private EditText edtCustomCur;
	private TextView btnCancel, btnOk, btnSave, txtHeaderSetting,
			txtHeaderGroup, txtHeaderAbout;

	private int type;
	private Editor editor;
	private TimePickerDialog timePickerDialog = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		editor = _Activity.myPrefs.edit();

		View view = null;

		type = getArguments().getInt(Commons.FLAG_SETTINGS_TYPE);

		switch (type) {
		case Commons.FLAG_CURRENCY:
			view = inflater.inflate(R.layout.dialog_currency, null);
			intializeCurrency(view);
			break;
		case Commons.FLAG_GROUPING:
			view = inflater.inflate(R.layout.dialog_groups, null);
			intializeGroups(view);
			break;
		case Commons.FLAG_ABOUT:
			view = inflater.inflate(R.layout.dialog_about, null);
			break;

		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		getDialog().getWindow().setLayout(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		if (type == Commons.FLAG_GROUPING) {
			if (Commons.getGroupingFlag(getActivity()) == Commons.FLAG_WEEK) {
				rbWeek.setChecked(true);
			} else if (Commons.getGroupingFlag(getActivity()) == Commons.FLAG_MONTH) {
				rbMonth.setChecked(true);
			} else if (Commons.getGroupingFlag(getActivity()) == Commons.FLAG_YEAR) {
				rbYear.setChecked(true);
			}
		} else if (type == Commons.FLAG_CURRENCY) {
			if (Commons.getCurrencyFlag(getActivity()) == Commons.FLAG_RUPPEE) {
				rbRuppee.setChecked(true);
			} else if (Commons.getCurrencyFlag(getActivity()) == Commons.FLAG_DOLLAR) {
				rbDollar.setChecked(true);
			} else if (Commons.getCurrencyFlag(getActivity()) == Commons.FLAG_EURO) {
				rbEuro.setChecked(true);
			} else if (Commons.getCurrencyFlag(getActivity()) == Commons.FLAG_POUND) {
				rbPound.setChecked(true);
			} else if (Commons.getCurrencyFlag(getActivity()) == Commons.FLAG_YEN) {
				rbYen.setChecked(true);
			} else if (Commons.getCurrencyFlag(getActivity()) == Commons.FLAG_YUAN) {
				rbYuan.setChecked(true);
			} else if (Commons.getCurrencyFlag(getActivity()) == Commons.FLAG_CUSTOM) {
				edtCustomCur.setText(Commons.getCurrencySymbol(getActivity()));
			}

		}

	}

	private void intializeGroups(View view) {
		rbWeek = (RadioButton) view.findViewById(R.id.radioWeek);
		rbMonth = (RadioButton) view.findViewById(R.id.radioMonth);
		rbYear = (RadioButton) view.findViewById(R.id.radioYear);
		btnSave = (TextView) view.findViewById(R.id.txtSave);
		txtHeaderGroup = (TextView) view.findViewById(R.id.txtHeaderGroup);
		grpSorting = (RadioGroup) view.findViewById(R.id.grpGroups);

		grpSorting.setOnCheckedChangeListener(this);
		btnSave.setOnClickListener(this);

//		View[] views = { txtHeaderGroup, btnSave, rbWeek, rbMonth, rbYear };
//		Commons.setCustomTypeFace(
//				((SettingsActivity) getActivity()).getRegularFont(), views);

	}

	private void intializeCurrency(View view) {
		grpCurrency1 = (RadioGroup) view.findViewById(R.id.grpCurrency1);
		grpCurrency2 = (RadioGroup) view.findViewById(R.id.grpCurrency2);
		rbRuppee = (RadioButton) view.findViewById(R.id.radioRupee);
		rbDollar = (RadioButton) view.findViewById(R.id.radioDollar);
		rbEuro = (RadioButton) view.findViewById(R.id.radioEuro);
		rbYen = (RadioButton) view.findViewById(R.id.radioYen);
		rbPound = (RadioButton) view.findViewById(R.id.radioPound);
		rbYuan = (RadioButton) view.findViewById(R.id.radioYuan);
		edtCustomCur = (EditText) view.findViewById(R.id.edtCustom);
		btnCancel = (TextView) view.findViewById(R.id.txtCancel);
		btnOk = (TextView) view.findViewById(R.id.txtOk);
		txtHeaderSetting = (TextView) view.findViewById(R.id.txtHeaderSettings);

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		edtCustomCur.addTextChangedListener(this);
		rbRuppee.setOnCheckedChangeListener(this);
		rbDollar.setOnCheckedChangeListener(this);
		rbEuro.setOnCheckedChangeListener(this);
		rbYen.setOnCheckedChangeListener(this);
		rbPound.setOnCheckedChangeListener(this);
		rbYuan.setOnCheckedChangeListener(this);

//		View[] views = { txtHeaderSetting, btnCancel, btnOk, edtCustomCur,
//				rbRuppee, rbDollar, rbEuro, rbPound, rbYen, rbYuan };
//		Commons.setCustomTypeFace(
//				((SettingsActivity) getActivity()).getRegularFont(), views);

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() > 0) {
			grpCurrency1.clearCheck();
			grpCurrency2.clearCheck();
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.txtCancel:
			break;
		case R.id.txtSave:
			editor.commit();
			Communicator communicator = (SettingsActivity) getActivity();
			communicator.update();
			break;
		case R.id.txtOk:

			if (edtCustomCur.getText().toString().length() > 0) {
				editor.putString(Commons.PREF_CUSTOM, edtCustomCur.getText()
						.toString());
				editor.putInt(Commons.PREF_CURRENCY, Commons.FLAG_CUSTOM);
			}

			editor.commit();
			Communicator communicator1 = (SettingsActivity) getActivity();
			communicator1.update();

			break;
		}

		dismiss();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		editor.putString(Commons.PREF_GROUPING, ((RadioButton) group
				.findViewById(checkedId)).getText().toString());
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {

			if (edtCustomCur.getText().toString().length() > 0) {
				edtCustomCur.setText("");
			}

			Commons.animateBackground(buttonView, getActivity().getResources()
					.getColor(android.R.color.white), getActivity()
					.getResources().getColor(R.color.color_pink_price), 500);

			if (buttonView.getId() == R.id.radioRupee) {
				editor.putInt(Commons.PREF_CURRENCY, Commons.FLAG_RUPPEE);
				grpCurrency2.clearCheck();
			} else if (buttonView.getId() == R.id.radioDollar) {
				editor.putInt(Commons.PREF_CURRENCY, Commons.FLAG_DOLLAR);
				grpCurrency2.clearCheck();
			} else if (buttonView.getId() == R.id.radioEuro) {
				editor.putInt(Commons.PREF_CURRENCY, Commons.FLAG_EURO);
				grpCurrency2.clearCheck();
			} else if (buttonView.getId() == R.id.radioPound) {
				editor.putInt(Commons.PREF_CURRENCY, Commons.FLAG_POUND);
				grpCurrency1.clearCheck();
			} else if (buttonView.getId() == R.id.radioYen) {
				editor.putInt(Commons.PREF_CURRENCY, Commons.FLAG_YEN);
				grpCurrency1.clearCheck();
			} else if (buttonView.getId() == R.id.radioYuan) {
				editor.putInt(Commons.PREF_CURRENCY, Commons.FLAG_YUAN);
				grpCurrency1.clearCheck();
			}

		} else {
			Commons.animateBackground(buttonView, getActivity().getResources()
					.getColor(R.color.color_pink_price), getActivity()
					.getResources().getColor(android.R.color.white), 500);
		}
		// editor.commit();

	}

	public interface Communicator {
		public void update();
	}
}
