package eu.qwan.vender;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    private final Map<Choice, CanContainer> cans = new HashMap<Choice, CanContainer>();
    private PaymentMethod paymentMethod = PaymentMethod.COIN;
    private Chipknip chipknip;
    private int credits = -1;

    public void set_value(int v) {
        paymentMethod = PaymentMethod.COIN;
        if (credits != -1) {
            credits += v;
        } else {
            credits = v;
        }
    }

    public void insert_chip(Chipknip chipknip) {
        // TODO
        // can't pay with chip in brittain
        paymentMethod = PaymentMethod.CHIPKNIP;
        this.chipknip = chipknip;
    }

    // delivers the can if all ok {
    public Can deliver(Choice choice) {
        Can res = Can.none;
        //
        // step 1: check if choice exists {
        //
        if (!cans.containsKey(choice)) return Can.none;

        //
        // step2 : check price
        //
        if (cans.get(choice).price == 0) {
            res = cans.get(choice).getType();
            // or price matches
        } else {

            switch (paymentMethod) {
                case COIN:
                    if (credits != -1 && cans.get(choice).price <= credits) {
                        res = cans.get(choice).getType();
                        credits -= cans.get(choice).price;
                    }
                    break;
                case CHIPKNIP:
                    // TODO: if this machine is in belgium this must be an error
                    // {
                    if (chipknip.HasValue(cans.get(choice).price)) {
                        chipknip.Reduce(cans.get(choice).price);
                        res = cans.get(choice).getType();
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

        //
        // step 3: check stock
        //
        if (res != Can.none) {
            if (cans.get(choice).getAmount() <= 0) {
                res = Can.none;
            } else {
                cans.get(choice).setAmount(cans.get(choice).getAmount() - 1);
            }
        }

        return res;
    }

    public int get_change() {
        int to_return = 0;
        if (credits > 0) {
            to_return = credits;
            credits = 0;
        }
        return to_return;
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
