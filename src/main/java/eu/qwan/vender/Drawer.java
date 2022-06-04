package eu.qwan.vender;

public class Drawer {
	private final Can type;
	private final int price;
	private int amount;

	public Drawer(Can can, int quantity, int price) {
		this.type = can;
		this.amount = quantity;
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public boolean isEmpty() {
		return amount <= 0;
	}

	public Can withdraw() {
		amount -= 1;
		return type;
	}

	public void addStock(int quantity) {
		this.amount += quantity;
	}
}
