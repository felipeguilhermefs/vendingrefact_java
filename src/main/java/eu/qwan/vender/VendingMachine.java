package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine implements Wallet {

    private final Map<Choice, CanContainer> cans = new HashMap<Choice, CanContainer>();
    private PaymentMethod paymentMethod = PaymentMethod.COIN;
    private Chipknip chipknip;
    private int credits = 0;

    public void setValue(int v) {
        paymentMethod = PaymentMethod.COIN;
        credits += v;
    }

    public void insertChip(Chipknip chipknip) {
        // TODO
        // can't pay with chip in brittain
        paymentMethod = PaymentMethod.CHIPKNIP;
        this.chipknip = chipknip;
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

            switch (paymentMethod) {
                case COIN:
                    if (this.hasValue(can.price)) {
                        res = can.getType();
                        this.reduce(can.price);
                    }
                    break;
                case CHIPKNIP:
                    // TODO: if this machine is in belgium this must be an error
                    // {
                    if (chipknip.HasValue(can.price)) {
                        chipknip.Reduce(can.price);
                        res = can.getType();
                    }
                    break;
                default:
                    // TODO: Is this a valid situation?:
                    // larry forgot the } else { clause
                    // i added it, but i am acutally not sure as to wether this
                    // is a problem
                    // unknown payment
                    break;
                // i think(i) nobody inserted anything
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
