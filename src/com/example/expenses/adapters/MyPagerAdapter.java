package com.example.expenses.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.expensecalculator.fragments.AddExpenseFragment;
import com.example.expensecalculator.fragments.ExpenseHistoryFragment;
import com.example.expensecalculator.fragments.TodaysExpenseFragment;
import com.example.expensecalulator.activity.MainActivity;

public class MyPagerAdapter extends FragmentPagerAdapter {

	FragmentManager fm;

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;

	}

	@Override
	public Fragment getItem(int position) {

		Fragment fragment = null;

		switch (position) {
		case 0:
			fragment = ExpenseHistoryFragment.newInstance(position);

			break;

		case 1:
			fragment = TodaysExpenseFragment.newInstance(position);
			break;

		case 2:
			fragment = AddExpenseFragment.newInstance(position);
			break;
		default:
			fragment = TodaysExpenseFragment.newInstance(position);
			break;
		}

		fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
				android.R.animator.fade_out);

		return fragment;
	}

	@Override
	public int getCount() {
		return MainActivity.NUM_PAGES;
	}

}
