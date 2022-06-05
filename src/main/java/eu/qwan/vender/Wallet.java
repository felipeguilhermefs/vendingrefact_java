package eu.qwan.vender;

public class Wallet {

    protected int credits;

    public boolean pay(int amount) {
        if (amount > credits) return false;

        credits -= amount;
        return true;
    }

    public void insert(int amount) {
        credits += amount;
    }

    public int withdraw() {
        int amount = credits;
        credits = 0;
        return amount;
    }
}
