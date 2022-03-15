package model;

public class Billboard {
	private int height;
	private int width;
	private boolean isInUse;
	private String promotedBrand;
	private int area;
	
	public Billboard(int width, int height, boolean isInUse, String promotedBrand) {
		this.height = height;
		this.width = width;
		this.isInUse = isInUse;
		this.promotedBrand = promotedBrand;
		this.area = height*width;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public boolean isInUse() {
		return isInUse;
	}
	public void setInUse(boolean isInUse) {
		this.isInUse = isInUse;
	}
	public String getPromotedBrand() {
		return promotedBrand;
	}
	public void setPromotedBrand(String promotedBrand) {
		this.promotedBrand = promotedBrand;
	}

	public int getArea() {
		return area;
	}
}
