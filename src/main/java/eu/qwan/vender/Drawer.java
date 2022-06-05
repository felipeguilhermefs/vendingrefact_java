package eu.qwan.vender;

import java.util.Optional;

public class Drawer {

    private final Can can;
    private final int price;
    private int inStock;

    public Drawer(Can can, int initialStock, int price) {
        this.can = can;
        this.inStock = initialStock;
        this.price = price;
    }

    public Optional<Can> withdraw(Wallet wallet) {
        if (inStock <= 0) return Optional.empty();
        if (!wallet.pay(price)) return Optional.empty();
        inStock -= 1;
        return Optional.of(can);
    }

    public void addStock(int quantity) {
        this.inStock += quantity;
    }
}
