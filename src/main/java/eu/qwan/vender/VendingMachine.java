package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine implements Wallet {

    private final Map<Choice, CanContainer> cans = new HashMap<Choice, CanContainer>();
    private Wallet wallet = this;
    private int credits = 0;

    public void setValue(int v) {
        wallet = this;
        credits += v;
    }

    public void insertChip(Chipknip chipknip) {
        // TODO
        // can't pay with chip in brittain
        wallet = chipknip;
    }

    // delivers the can if all ok {
    public Can deliver(Choice choice) {
        if (!cans.containsKey(choice)) return Can.none;

        var can = cans.get(choice);

        if (can.getAmount() <= 0) {
            return Can.none;
        }

        Can res = Can.none;
        if (can.price == 0) {
            res = can.getType();
            // or price matches
        } else {
            if (wallet.hasValue(can.price)) {
                wallet.reduce(can.price);
                res = can.getType();
            }
        }

        if (res != Can.none) {
            can.setAmount(can.getAmount() - 1);
        }

        return res;
    }

    public int getChange() {
        var change = credits;
        credits = 0;
        return change;
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

    @Override
    public boolean hasValue(int amount) {
        return amount <= credits;
    }

    @Override
    public void reduce(int amount) {
        credits -= amount;
    }
}
