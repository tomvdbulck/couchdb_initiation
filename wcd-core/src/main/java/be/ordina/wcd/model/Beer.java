package be.ordina.wcd.model;

import com.google.gson.annotations.SerializedName;

public class Beer {

	@SerializedName("_id")
	private String id;
	@SerializedName("_rev")
	private String revision;
	
	private String brand;
    private double alcohol;
    private double price;

	public Beer() {
		super();
	}
	
	public Beer(String id, String brand, double alcohol, double price) {
		this.id = id;
		this.brand = brand;
		this.alcohol = alcohol;
		this.price = price;
	}

	public String getId() {
		return id;
	}
	
	public String getRevision() {
		return revision;
	}
	
	public String getBrand() {
		return brand;
	}

	public double getAlcohol() {
		return alcohol;
	}

	public double getPrice() {
		return price;
	}

}
