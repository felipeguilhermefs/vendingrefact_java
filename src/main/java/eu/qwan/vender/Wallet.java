package eu.qwan.vender;

public interface Wallet {

    boolean hasValue(int amount);
    void reduce(int amount);
    int getCredits();
    void addCredits(int amount);
}
