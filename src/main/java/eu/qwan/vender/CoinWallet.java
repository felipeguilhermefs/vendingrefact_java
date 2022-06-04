package eu.qwan.vender;

public class CoinWallet implements Wallet {

    private int credits;

    @Override
    public boolean hasValue(int amount) {
        return amount <= credits;
    }

    @Override
    public void reduce(int amount) {
        credits -= amount;
    }

    @Override
    public void addCredits(int amount) {
        credits += amount;
    }

    @Override
    public int withdrawCredits() {
        int amount = credits;
        credits = 0;
        return amount;
    }
}
