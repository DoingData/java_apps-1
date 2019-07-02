package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.ca.jrvs.apps.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {

    private static final String GET_ONE = "SELECT c.first_name AS c_first_name, c.last_name AS c_last_name, c.email AS c_email, " +
            "o.order_id, o.creation_date, o.total_due, o.status, " +
            "s.first_name AS sp_first_name, s.last_name AS sp_last_name, s.email AS sp_email, " +
            "ol.quantity, " +
            "p.code, p.name, p.size, p.variety, p.price " +
            "from orders o " +
            "join customer c on o.customer_id = c.customer_id " +
            "join salesperson s on " +
            "o.salesperson_id=s.salesperson_id " +
            "join order_item ol on ol.order_id=o.order_id " +
            "join product p on ol.product_id = p.product_id " +
            "where o.order_id = ?;";


    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = new Order();
        order.setOrderItems(new LinkedList<>());
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            boolean newOrder = true;
            while (rs.next()) {
                if (newOrder) {
                    order.setCustomer_first_name(rs.getString("c_first_name"));
                    order.setCustomer_last_name(rs.getString("c_last_name"));
                    order.setCustomer_email(rs.getString("c_email"));
                    order.setId(rs.getLong("order_id"));
                    order.setCreation_date((rs.getString("creation_date")));
                    order.setTotal_due(rs.getDouble("total_due"));
                    order.setStatus(rs.getString("status"));
                    order.setSalesperson_first_name(rs.getString("sp_first_name"));
                    order.setSalesperson_last_name(rs.getString("sp_last_name"));
                    order.setSalesperson_email(rs.getString("sp_email"));
                    newOrder = false;
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(rs.getLong("quantity"));
                orderItem.setProduct_code(rs.getString("code"));
                orderItem.setProduct_name(rs.getString("name"));
                orderItem.setProduct_size(rs.getLong("size"));
                orderItem.setProduct_variety(rs.getString("variety"));
                orderItem.setProduct_price(rs.getDouble("price"));
                order.addOrderItem(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order dto) {
        return null;
    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
