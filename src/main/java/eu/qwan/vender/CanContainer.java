package eu.qwan.vender;

public class CanContainer {
	private Can type;
	private int price;

	public Can getType() {
		return type;
	}

	public void setType(Can type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	private int amount;

	public int getAmount() {
		return amount;
	}

	public boolean isEmpty() {
		return amount <= 0;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
