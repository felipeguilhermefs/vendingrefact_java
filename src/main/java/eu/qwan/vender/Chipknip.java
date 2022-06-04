package eu.qwan.vender;

public class Chipknip implements Wallet {
	public int credits;

	public Chipknip(int initial_value) {
		credits = initial_value;
	}

	@Override
	public boolean hasValue(int amount) {
		return credits >= amount;
	}

	@Override
	public void reduce(int amount) {
		credits -= amount;
	}
}
