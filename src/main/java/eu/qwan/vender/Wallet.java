package eu.qwan.vender;

public interface Wallet {

    boolean deductPayment(int amount);

    void addCredits(int amount);

    int withdrawCredits();
}
