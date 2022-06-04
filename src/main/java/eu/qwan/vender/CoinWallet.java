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
    public int getCredits() {
        return credits;
    }

    @Override
    public void addCredits(int amount) {
        credits += amount;
    }
}
