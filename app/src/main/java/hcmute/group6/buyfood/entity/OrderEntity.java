package hcmute.group6.buyfood.entity;

import java.io.Serializable;

public class OrderEntity implements Serializable {
    private Integer id;
    private String deliverTo;
    private String paymentMethod;
    private Integer dishId;
    private String email;
    private Float amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(String deliverTo) {
        this.deliverTo = deliverTo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
