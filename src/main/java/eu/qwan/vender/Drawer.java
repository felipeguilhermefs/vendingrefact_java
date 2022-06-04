package eu.qwan.vender;

public class Drawer {

    private final Can can;
    private final int price;
    private int inStock;

    public Drawer(Can can, int initialStock, int price) {
        this.can = can;
        this.inStock = initialStock;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public boolean isEmpty() {
        return inStock <= 0;
    }

    public Can withdraw() {
        inStock -= 1;
        return can;
    }

    public void addStock(int quantity) {
        this.inStock += quantity;
    }
}
