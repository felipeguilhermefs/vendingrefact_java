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
        var drawer = Optional.ofNullable(drawers.get(choice))
            .filter(d -> !d.isEmpty())
            .filter(d -> wallet.deductPayment(d.getPrice()))
            .orElse(null);

        if (drawer == null) return Optional.empty();

        return Optional.of(drawer.withdraw());
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
