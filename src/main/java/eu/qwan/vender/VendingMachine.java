package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VendingMachine {

    private final Map<Can, Drawer> drawers = new HashMap<>();
    private Wallet wallet = new Wallet();

    public void insertCredits(int amount) {
        wallet.insert(amount);
    }

    public void insertChip(Chipknip chipknip) {
        wallet = chipknip;
    }

    public Optional<Can> deliver(Can choice) {
        if (!drawers.containsKey(choice)) return Optional.empty();

        var drawer = drawers.get(choice);
        return drawer.withdraw(wallet);
    }

    public int getChange() {
        return wallet.withdraw();
    }

    public void configure(Can can, int quantity) {
        configure(can, quantity, 0);
    }

    public void configure(Can can, int quantity, int price) {
        drawers.computeIfAbsent(can, c -> new Drawer(c, 0, price))
            .addStock(quantity);
    }
}
