package be.ordina.wcd.model;

public class Beer {

	private String brand;
    private double alcohol;
    private double price;

	public Beer() {
		super();
	}
	
	public Beer(String brand, double alcohol, double price) {
		this.brand = brand;
		this.alcohol = alcohol;
		this.price = price;
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
