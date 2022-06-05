package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VendingMachine {

    private final Map<Choice, Drawer> drawers = new HashMap<>();
    private Wallet wallet = new Wallet();

    public void setValue(int amount) {
        wallet.addCredits(amount);
    }

    public void insertChip(ChipknipWallet chipknip) {
        wallet = chipknip;
    }

    public Optional<Can> deliver(Choice choice) {
        if (!drawers.containsKey(choice)) return Optional.empty();

        var drawer = drawers.get(choice);

        if (drawer.isEmpty()) return Optional.empty();

        if (wallet.deductPayment(drawer.getPrice())) {
            return Optional.of(drawer.withdraw());
        }

        return Optional.empty();
    }

    public int getChange() {
        return wallet.withdrawCredits();
    }

    public void configure(Choice choice, Can can, int quantity) {
        configure(choice, can, quantity, 0);
    }

    public void configure(Choice choice, Can can, int quantity, int price) {
        drawers.computeIfAbsent(choice, k -> new Drawer(can, 0, price))
            .addStock(quantity);
    }
}
