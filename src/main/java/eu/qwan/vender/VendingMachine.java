package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    private final Map<Choice, CanContainer> cans = new HashMap<Choice, CanContainer>();
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

        if (canContainer.getAmount() <= 0) return Can.none;

        if (!wallet.deductPayment(canContainer.getPrice())) return Can.none;

        Can res = Can.none;
        res = canContainer.getType();
        if (res != Can.none) {
            canContainer.setAmount(canContainer.getAmount() - 1);
        }

        return res;
    }

    public int getChange() {
        return wallet.withdrawCredits();
    }

    public void configure(Choice choice, Can c, int n) {
        configure(choice, c, n, 0);
    }

    public void configure(Choice choice, Can c, int n, int price) {
        if (cans.containsKey(choice)) {
            cans.get(choice).setAmount(cans.get(choice).getAmount() + n);
            return;
        }
        CanContainer can = new CanContainer();
        can.setType(c);
        can.setAmount(n);
        can.setPrice(price);
        cans.put(choice, can);
    }
}
