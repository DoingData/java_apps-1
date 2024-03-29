package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport",
                "postgres", "password");
        try{
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer.setFirstName("George");
            customer.setLastName("Washington");
            customer.setEmail("george.washington@wh.gov");
            customer.setPhone("(555) 555-6543");
            customer.setAddress("1234 Main St");
            customer.setCity("Mount Vernon");
            customer.setState("VA");
            customer.setZipCode("22121");
            customerDAO.create(customer);
            customer = customerDAO.findById(1000);
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
            customer.setEmail("gwashington@wh.gov");
            customer = customerDAO.update(customer);
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " "
                    + customer.getEmail());
            customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Adams");
            customer.setEmail("jadams@wh.gov");
            customer.setPhone("(555) 555-9845");
            customer.setAddress("1234 Main St");
            customer.setCity("Arlington");
            customer.setState("VA");
            customer.setZipCode("01234");
            Customer dbcustomer = customerDAO.create(customer);
            System.out.println(dbcustomer);
            dbcustomer = customerDAO.findById(dbcustomer.getId());
            System.out.println(dbcustomer);
            dbcustomer.setEmail("john.adams@wh.gov");
            dbcustomer = customerDAO.update(dbcustomer);
            System.out.println(dbcustomer);
            customerDAO.delete(dbcustomer.getId());
            OrderDAO orderDao = new OrderDAO(connection);
            Order test = orderDao.findById(1000);
            System.out.println(test);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
