package eu.qwan.vender;

public class ChipknipWallet implements Wallet {

    public int credits;

    public ChipknipWallet(int initial_value) {
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

    @Override
    public int getCredits() {
        return credits;
    }

    @Override
    public void addCredits(int amount) {
        credits += amount;
    }
}
