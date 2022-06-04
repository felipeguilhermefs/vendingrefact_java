package eu.qwan.vender;

public class CanContainer {
	private final Can type;
	private final int price;
	private int amount;

	public CanContainer(Can can, int quantity, int price) {
		this.type = can;
		this.amount = quantity;
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public int getAmount() {
		return amount;
	}

	public boolean isEmpty() {
		return amount <= 0;
	}

	public Can withdraw() {
		amount -= 1;
		return type;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
