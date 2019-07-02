package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;

import java.util.LinkedList;

public class Order implements DataTransferObject {

    private String customer_first_name;
    private String customer_last_name;
    private String customer_email;
    private long id;
    private String creation_date;
    private double total_due;
    private String status;
    private String salesperson_first_name;
    private String salesperson_last_name;
    private String salesperson_email;
    private LinkedList<OrderItem> orderItems;

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public double getTotal_due() {
        return total_due;
    }

    public void setTotal_due(double total_due) {
        this.total_due = total_due;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesperson_first_name() {
        return salesperson_first_name;
    }

    public void setSalesperson_first_name(String salesperson_first_name) {
        this.salesperson_first_name = salesperson_first_name;
    }

    public String getSalesperson_last_name() {
        return salesperson_last_name;
    }

    public void setSalesperson_last_name(String salesperson_last_name) {
        this.salesperson_last_name = salesperson_last_name;
    }

    public String getSalesperson_email() {
        return salesperson_email;
    }

    public void setSalesperson_email(String salesperson_email) {
        this.salesperson_email = salesperson_email;
    }

    public LinkedList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(LinkedList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer_first_name='" + customer_first_name + '\'' +
                ", customer_last_name='" + customer_last_name + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", id=" + id +
                ", creation_date='" + creation_date + '\'' +
                ", total_due=" + total_due +
                ", status='" + status + '\'' +
                ", salesperson_first_name='" + salesperson_first_name + '\'' +
                ", salesperson_last_name='" + salesperson_last_name + '\'' +
                ", salesperson_email='" + salesperson_email + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
