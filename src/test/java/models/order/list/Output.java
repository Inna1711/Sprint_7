package models.order.list;

import models.order.create.Input;

public class Output {
    private Input[] orders;

    public Input[] getOrders() {
        return orders;
    }

    public void setOrders(Input[] orders) {
        this.orders = orders;
    }
}
