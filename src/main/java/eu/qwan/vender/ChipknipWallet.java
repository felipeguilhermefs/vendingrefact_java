package eu.qwan.vender;

public class ChipknipWallet extends WalletImpl {

    public int credits;

    public ChipknipWallet(int initial_value) {
        credits = initial_value;
    }


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
