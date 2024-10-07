package models.OrderList;

import models.OrderCreate.Input;

public class Output {
    private Input[] orders;

    public Input[] getOrders() {
        return orders;
    }

    public void setOrders(Input[] orders) {
        this.orders = orders;
    }
}
