package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.ca.jrvs.apps.jdbc.util.DataTransferObject;

public class Order implements DataTransferObject {

    private String customer_first_name;
    private String customer_last_name;
    private String customer_email;
    private long order_id;
    private String creation_date;
    private double total_due;
    private String status;
    private String salesperson_first_name;
    private String salesperson_last_name;
    private String salesperson_email;
    private OrderItem orderitem;

}
