package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VendingMachine {

    private final Map<Choice, Drawer> drawers = new HashMap<>();
    private Wallet wallet = new Wallet();

    public void insertCredits(int amount) {
        wallet.insert(amount);
    }

    public void insertChip(Chipknip chipknip) {
        wallet = chipknip;
    }

    public Optional<Can> deliver(Choice choice) {
        return Optional.ofNullable(drawers.get(choice))
            .filter(drawer -> !drawer.isEmpty())
            .filter(drawer -> wallet.pay(drawer.getPrice()))
            .map(Drawer::withdraw);
    }

    public int getChange() {
        return wallet.withdraw();
    }

    public void configure(Choice choice, Can can, int quantity) {
        configure(choice, can, quantity, 0);
    }

    public void configure(Choice choice, Can can, int quantity, int price) {
        drawers.computeIfAbsent(choice, k -> new Drawer(can, 0, price))
            .addStock(quantity);
    }
}
