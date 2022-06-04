package eu.qwan.vender;

public abstract class WalletImpl implements Wallet {

    public int credits;

    @Override
    public boolean deductPayment(int amount) {
        if (amount > credits) return false;

        credits -= amount;
        return true;
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
