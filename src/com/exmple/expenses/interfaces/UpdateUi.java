package com.exmple.expenses.interfaces;

import com.example.expensecalculator.models.AllItemModel;

public interface UpdateUi {
	public void updateToday();

	void editTodaysExpense(AllItemModel model);

	public void notifyEditMode();

	public void startAddFragment();

	public void notifyHistoryUpdate();

	public void notifyTodayUpdate();

}
