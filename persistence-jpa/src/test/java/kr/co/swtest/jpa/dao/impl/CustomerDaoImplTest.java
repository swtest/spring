package kr.co.swtest.jpa.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import kr.co.swtest.jpa.dao.CustomerDao;
import kr.co.swtest.jpa.type.Customer;
import kr.co.swtest.test.util.SpringJpaDbUnitTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * CustomerLogicTest (JPA test example)
 *
 * @author <a href="mailto:davidchoi@nextree.co.kr">�ֿ���</a>
 * @since 2012. 5. 19.
 */
public class CustomerDaoImplTest extends SpringJpaDbUnitTestCase {

    /** ��DAO �������̽� */
    @Autowired
    private CustomerDao customerDao;

    @Override
    protected List<String> addDataFiles() {
        return super.addDataFiles();
    }

    @Override
    protected String addDataFile() {
        return "kr/co/swtest/jpa/dao/impl/CustomerDaoImplTestDataSet.xml";
    }

    // -------------------------------------------------------------------------
    // Test Method
    // -------------------------------------------------------------------------

    /** Customer ��� �׽�Ʈ */
    @Test
    public void testCreateCustomer() {
        int customerId = 3;

        // 1. ��� �� Ȯ��
        Customer customer = this.customerDao.readCustomerById(customerId);
        assertNull(customer);

        // 2. ���
        customer = new Customer(customerId, "�ֿ���3", "davidchoi3@nextree.co.kr");
        this.customerDao.createCustomer(customer);

        // 3. ����
        Customer result = this.customerDao.readCustomerById(customerId);
        assertCustomer(customer, result);
    }

    /** Customer ��ȸ Test */
    @Test
    public void testReadCustomerById() {
        int customerId = 1;
        Customer result = this.customerDao.readCustomerById(customerId);

        assertCustomer(new Customer(customerId, "�ֿ���", "davidchoi@nextree.co.kr"), result);
    }

    /** Customer ������ȸ Test */
    @Test
    public void testReadCustomersByCondition() {
        Customer condition = new Customer();
        condition.setName("��");

        List<Customer> customers = this.customerDao.readCustomersByCondition(condition);
        assertEquals(2, customers.size());
        assertCustomer(new Customer(1, "�ֿ���", "davidchoi@nextree.co.kr"), customers.get(0));
        assertCustomer(new Customer(2, "�ֿ���2", "davidchoi2@nextree.co.kr"), customers.get(1));
    }

    /** Customer ���� Test */
    @Test
    public void testUpdateCustomer() {
        // 1. ���� �� Ȯ��
        int customerId = 2;
        Customer customer = this.customerDao.readCustomerById(customerId);
        assertCustomer(new Customer(customerId, "�ֿ���2", "davidchoi2@nextree.co.kr"), customer);

        // 2. ����
        customer.setName("newName");
        customer.setEmail("newEmail@nextree.co.kr");
        this.customerDao.updateCustomer(customer);

        // 3. ����
        Customer result = this.customerDao.readCustomerById(customerId);
        assertEquals("newName", result.getName());
        assertEquals("newEmail@nextree.co.kr", result.getEmail());
    }

    /** Customer ���� Test */
    @Test
    public void testDeleteCustomer() {
        // 1. ���� �� Ȯ��
        int customerId = 1;
        Customer customer = this.customerDao.readCustomerById(customerId);
        assertNotNull(customer);

        // 2. ����
        this.customerDao.deleteCustomerById(customerId);

        // 3. ���� �� Ȯ��
        assertNull(this.customerDao.readCustomerById(customerId));
    }

    // -------------------------------------------------------------------------
    // Private Method
    // -------------------------------------------------------------------------

    /**
     * �� ���� <br/>
     * ������� : ID, ����, �̸���
     *
     * @param expected ����ϴ� �� ����
     * @param actual ���� �� ����
     */
    private void assertCustomer(Customer expected, Customer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

}
