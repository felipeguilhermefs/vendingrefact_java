package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    private final Map<Choice, CanContainer> cans = new HashMap<>();
    private Wallet wallet = new Wallet();

    public void setValue(int amount) {
        wallet.addCredits(amount);
    }

    public void insertChip(ChipknipWallet chipknip) {
        wallet = chipknip;
    }

    public Can deliver(Choice choice) {
        if (!cans.containsKey(choice)) return Can.none;

        var canContainer = cans.get(choice);

        if (canContainer.isEmpty()) return Can.none;

        if (wallet.deductPayment(canContainer.getPrice())) {
            return canContainer.withdraw();
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
        cans.computeIfAbsent(choice, k -> new CanContainer(can, 0, price))
            .addStock(quantity);
    }
}
