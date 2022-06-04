package eu.qwan.vender;

public abstract class Wallet {

    protected int credits;

    public boolean deductPayment(int amount) {
        if (amount > credits) return false;

        credits -= amount;
        return true;
    }

    public void addCredits(int amount) {
        credits += amount;
    }

    public int withdrawCredits() {
        int amount = credits;
        credits = 0;
        return amount;
    }
}
