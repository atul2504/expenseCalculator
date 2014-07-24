package com.example.expensecalculator.models;

public class CategoryModel {
	private int categoryId;
	private String categoryName;
	private String categoryImage;
	private int categoryVisibilty;

	public CategoryModel() {
		// Default Constructor
	}

	public CategoryModel(String categoryName) {
		this.categoryName = categoryName;
		this.categoryImage = "R.drawable.ic_launcher";
	}

	public CategoryModel(String categoryName, String categoryImage) {
		this.categoryName = categoryName;
		this.categoryImage = categoryImage;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public void setCategoryVisibilty(boolean categoryVisibilty) {

		this.categoryVisibilty = (categoryVisibilty ? 1 : 0);
	}

	public void setCategoryVisibilty(int categoryVisibilty) {
		this.categoryVisibilty = categoryVisibilty;
	}

	public boolean isCategoryVisible() {
		return (categoryVisibilty == 1) ? true : false;
	}

}
