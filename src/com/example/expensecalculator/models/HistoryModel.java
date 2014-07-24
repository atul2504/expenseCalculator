package com.example.expensecalculator.models;

import java.util.ArrayList;

import com.example.expensecalculator.utils.Commons;

public class HistoryModel {

	private String total;
	private ArrayList<AllItemModel> arrayHistory;
	private String header;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	private int flag;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getFormattedTotal() {
		return Commons.updateTextExpense(Float.parseFloat(total));
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public ArrayList<AllItemModel> getArrayHistory() {
		return arrayHistory;
	}

	public void setArrayHistory(ArrayList<AllItemModel> arrayHistory) {
		this.arrayHistory = arrayHistory;

		float price = 0;

		for (AllItemModel item : arrayHistory) {
			price = price + Float.parseFloat(item.getPrice());
		}

		setTotal(String.valueOf(price));
	}

	public HistoryModel() {

	}

}
