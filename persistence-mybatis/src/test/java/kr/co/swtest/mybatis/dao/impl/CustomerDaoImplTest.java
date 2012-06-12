/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.mybatis.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import kr.co.swtest.mybatis.dao.CustomerDao;
import kr.co.swtest.mybatis.dto.CustomerDto;
import kr.co.swtest.test.util.SpringDbUnitTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

/**
 * CustomerDaoImplTest (MyBatis test example)
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">�ֿ���</a>
 * @since 2012. 6. 10.
 */
@ContextConfiguration(locations = { "classpath:context-mybatis.xml" })
@SuppressWarnings("deprecation")
public class CustomerDaoImplTest extends SpringDbUnitTestCase {

    /** ��DAO �������̽� : �׽�Ʈ���(SUT) */
    @Autowired
    private CustomerDao customerDao;

    @Override
    protected List<String> addDataFiles() {
        return super.addDataFiles();
    }

    @Override
    protected String addDataFile() {
        return "kr/co/swtest/mybatis/dao/impl/CustomerDaoImplTestDataSet.xml";
    }

    /**
     * Oracleó�� ��Ű������ �ʿ��� ��� �������̵��ؼ� ����Ѵ�.
     *
     * <pre>
     * <code>
     * protected String getSchemaName() {
     *     return "test";
     * }
     * </code>
     * </pre>
     */
    @Override
    protected String getSchemaName() {
        return super.getSchemaName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createTable() {
        SimpleJdbcTemplate template = new SimpleJdbcTemplate(getDataSource());

        Resource resource = new ClassPathResource("/script/test-script.sql");
        SimpleJdbcTestUtils.executeSqlScript(template, resource, true);
    }

    // -------------------------------------------------------------------------
    // Test Method
    // -------------------------------------------------------------------------

    /** Customer ��� �׽�Ʈ */
    @Test
    public void testCreateCustomer() {
        int customerId = 3;

        // 1. ��� �� Ȯ��
        CustomerDto customer = this.customerDao.readCustomerById(customerId);
        assertNull(customer);

        // 2. ���
        customer = new CustomerDto(customerId, "�ֿ���3", "scroogy3@swtest.co.kr");
        this.customerDao.createCustomer(customer);

        // 3. ����
        CustomerDto result = this.customerDao.readCustomerById(customerId);
        assertCustomer(customer, result);
    }

    /** Customer ��ȸ Test */
    @Test
    public void testReadCustomerById() {
        int customerId = 1;
        CustomerDto result = this.customerDao.readCustomerById(customerId);

        assertCustomer(new CustomerDto(customerId, "�ֿ���", "scroogy@swtest.co.kr"), result);
    }

    /** Customer ������ȸ Test */
    @Test
    public void testReadCustomersByCondition() {
        CustomerDto condition = new CustomerDto();
        condition.setName("��%");

        List<CustomerDto> customers = this.customerDao.readCustomersByCondition(condition);
        assertEquals(2, customers.size());
        assertCustomer(new CustomerDto(1, "�ֿ���", "scroogy@swtest.co.kr"), customers.get(0));
        assertCustomer(new CustomerDto(2, "�ֿ���2", "scroogy2@swtest.co.kr"), customers.get(1));
    }

    /** Customer ���� Test */
    @Test
    public void testUpdateCustomer() {
        // 1. ���� �� Ȯ��
        int customerId = 2;
        CustomerDto customer = this.customerDao.readCustomerById(customerId);
        assertCustomer(new CustomerDto(customerId, "�ֿ���2", "scroogy2@swtest.co.kr"), customer);

        // 2. ����
        customer.setName("newName");
        customer.setEmail("newEmail@swtest.co.kr");
        this.customerDao.updateCustomer(customer);

        // 3. ����
        CustomerDto result = this.customerDao.readCustomerById(customerId);
        assertEquals("newName", result.getName());
        assertEquals("newEmail@swtest.co.kr", result.getEmail());
    }

    /** Customer ���� Test */
    @Test
    public void testDeleteCustomer() {
        // 1. ���� �� Ȯ��
        int customerId = 1;
        CustomerDto customer = this.customerDao.readCustomerById(customerId);
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
    private void assertCustomer(CustomerDto expected, CustomerDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

}
