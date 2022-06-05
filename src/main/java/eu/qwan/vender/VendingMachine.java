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

    public Optional<Can> deliverCan(Choice choice) {
        var can = deliver(choice);
        return Optional.ofNullable(can == Can.none ? null : can);
    }

    public Can deliver(Choice choice) {
        if (!drawers.containsKey(choice)) return Can.none;

        var drawer = drawers.get(choice);

        if (drawer.isEmpty()) return Can.none;

        if (wallet.deductPayment(drawer.getPrice())) {
            return drawer.withdraw();
        }

        return Can.none;
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
