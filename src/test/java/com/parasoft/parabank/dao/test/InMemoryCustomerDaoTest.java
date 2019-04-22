package com.parasoft.parabank.dao.test;

import com.parasoft.parabank.dao.CustomerDao;
import com.parasoft.parabank.dao.InMemoryCustomerDao;
import com.parasoft.parabank.domain.Customer;
import com.parasoft.parabank.test.util.AbstractParaBankTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @req PAR-25
 *
 */
public class InMemoryCustomerDaoTest extends AbstractParaBankTest {
    private static final int CUSTOMER1_ID = 1;

    private static final String CUSTOMER1_SSN = "123-45-6789";

    private static final String CUSTOMER1_USERNAME = "user1";

    private static final String CUSTOMER1_PASSWORD = "pass1";

    private static final int CUSTOMER2_ID = 2;

    private static final String CUSTOMER2_SSN = "987-65-4321";

    private static final String CUSTOMER2_USERNAME = "user2";

    private static final String CUSTOMER2_PASSWORD = "pass2";

    private CustomerDao customerDao;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        final List<Customer> customers = new ArrayList<Customer>();

        Customer customer = new Customer();
        customer.setId(CUSTOMER1_ID);
        customer.setSsn(CUSTOMER1_SSN);
        customer.setUsername(CUSTOMER1_USERNAME);
        customer.setPassword(CUSTOMER1_PASSWORD);
        customers.add(customer);

        customer = new Customer();
        customer.setId(CUSTOMER2_ID);
        customer.setSsn(CUSTOMER2_SSN);
        customer.setUsername(CUSTOMER2_USERNAME);
        customer.setPassword(CUSTOMER2_PASSWORD);
        customers.add(customer);

        customerDao = new InMemoryCustomerDao(customers);
    }

    @Test
    public void testCreateCustomer() {
        final Customer originalCustomer = new Customer();
        final int id = customerDao.createCustomer(originalCustomer);
        assertEquals(3, id);
        final Customer newCustomer = customerDao.getCustomer(id);
        assertEquals(originalCustomer, newCustomer);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = customerDao.getCustomer(CUSTOMER1_ID);
        assertNotNull(customer);
        assertEquals(CUSTOMER1_ID, customer.getId());
        customer = customerDao.getCustomer(CUSTOMER2_ID);
        assertNotNull(customer);
        assertEquals(CUSTOMER2_ID, customer.getId());
        assertNull(customerDao.getCustomer(-1));

        customer = customerDao.getCustomer(CUSTOMER1_SSN);
        assertNotNull(customer);
        assertEquals(CUSTOMER1_ID, customer.getId());
        customer = customerDao.getCustomer(CUSTOMER2_SSN);
        assertNotNull(customer);
        assertEquals(CUSTOMER2_ID, customer.getId());
        assertNull(customerDao.getCustomer("000-00-0000"));

        customer = customerDao.getCustomer(CUSTOMER1_USERNAME, CUSTOMER1_PASSWORD);
        assertNotNull(customer);
        assertEquals(CUSTOMER1_ID, customer.getId());
        customer = customerDao.getCustomer(CUSTOMER2_USERNAME, CUSTOMER2_PASSWORD);
        assertNotNull(customer);
        assertEquals(CUSTOMER2_ID, customer.getId());
        assertNull(customerDao.getCustomer("bad", "login"));
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = customerDao.getCustomer(CUSTOMER1_ID);
        customer.setFirstName("first");
        customer.setLastName("last");
        customerDao.updateCustomer(customer);
        customer = customerDao.getCustomer(CUSTOMER1_ID);
        assertEquals("first last", customer.getFullName());
    }

    /**
     * Parasoft Jtest UTA: Test for deleteCustomer(int)
     *
     * @author dchacon
     * @see InMemoryCustomerDao#deleteCustomer(int)
     */
    @Test(expected = NullPointerException.class)
    public void testDeleteCustomer() throws Throwable {
        // Given
        List<Customer> customers = new ArrayList<Customer>(); // UTA: default value
        Customer item = mock(Customer.class);
        int getIdResult = 0; // UTA: default value
        when(item.getId()).thenReturn(getIdResult);
        customers.add(item);
        InMemoryCustomerDao underTest = new InMemoryCustomerDao(customers);

        // When
        int id = 0; // UTA: default value
        underTest.deleteCustomer(id);

        // Then - assertion templates for this instance of InMemoryCustomerDao
        ArrayList underTest_customers = null; // UTA: default value
        // assertNotNull(underTest_customers);
        assertEquals(0, underTest_customers.size());

    }

    /**
     * Parasoft Jtest UTA: Test for getCustomer(int)
     *
     * @author dchacon
     * @see InMemoryCustomerDao#getCustomer(int)
     */
    @Test
    public void testGetCustomer2() throws Throwable {
        // Given
        InMemoryCustomerDao underTest = new InMemoryCustomerDao();

        // When
        int id = 0; // UTA: default value
        Customer result = underTest.getCustomer(id);

        // Then
        // assertNotNull(result);
    }

    /**
     * Parasoft Jtest UTA: Test for getCustomer(String, String)
     *
     * @author dchacon
     * @see InMemoryCustomerDao#getCustomer(String, String)
     */
    @Test
    public void testGetCustomer3() throws Throwable {
        // Given
        List<Customer> customers = new ArrayList<Customer>(); // UTA: default value
        Customer item = mock(Customer.class);
        customers.add(item);
        InMemoryCustomerDao underTest = new InMemoryCustomerDao(customers);

        // When
        String username = ""; // UTA: default value
        String password = ""; // UTA: default value
        Customer result = underTest.getCustomer(username, password);

        // Then
        // assertNotNull(result);
    }

    /**
     * Parasoft Jtest UTA: Test for getCustomer(String)
     *
     * @author dchacon
     * @see InMemoryCustomerDao#getCustomer(String)
     */
    @Test
    public void testGetCustomer4() throws Throwable {
        // Given
        List<Customer> customers = new ArrayList<Customer>(); // UTA: default value
        Customer item = mock(Customer.class);
        customers.add(item);
        InMemoryCustomerDao underTest = new InMemoryCustomerDao(customers);

        // When
        String ssn = ""; // UTA: default value
        Customer result = underTest.getCustomer(ssn);

        // Then
        // assertNotNull(result);
    }

    /**
     * Parasoft Jtest UTA: Test for updateCustomer(Customer)
     *
     * @author dchacon
     * @see InMemoryCustomerDao#updateCustomer(Customer)
     */
    @Test
    public void testUpdateCustomer2() throws Throwable {
        // Given
        List<Customer> customers = new ArrayList<Customer>(); // UTA: default value
        Customer item = mock(Customer.class);
        customers.add(item);
        InMemoryCustomerDao underTest = new InMemoryCustomerDao(customers);

        // When
        Customer customer = mockCustomer();
        underTest.updateCustomer(customer);

    }

    /**
     * Parasoft Jtest UTA: Helper method to generate and configure mock of Customer
     */
    private static Customer mockCustomer() throws Throwable {
        Customer customer = mock(Customer.class);
        int getIdResult = 0; // UTA: default value
        when(customer.getId()).thenReturn(getIdResult);
        return customer;
    }
}
