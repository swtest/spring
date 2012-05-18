package kr.co.swtest.jpa.dao;

import java.util.List;

import kr.co.swtest.jpa.type.Customer;

/**
 * CustomerDao interface (JPA example)
 *
 * @author <a href="mailto:davidchoi@nextree.co.kr">�ֿ���</a>
 * @since 2012. 5. 19.
 */
public interface CustomerDao {

    /**
     * �� ���
     *
     * @param customer ��
     */
    public void createCustomer(Customer customer);

    /**
     * �� ��ȸ
     *
     * @param customerId ��ID
     * @return ��. ���� ��� <code>null</code>�� �����Ѵ�.
     */
    public Customer readCustomerById(int customerId);

    /**
     * ����� ��ȸ : ���ǰ˻� <br/>
     * �˻����� : ����(like), �̸���(like) <br/>
     * �������� : ����, ��ID
     *
     * @param customer �˻�����
     * @return ���ǿ� �ش��ϴ� �����. ���� ��� ����(Empty List)�� �����Ѵ�.
     */
    public List<Customer> readCustomersByCondition(Customer customer);

    /**
     * �� ����
     *
     * @param customer ������ ��
     */
    public void updateCustomer(Customer customer);

    /**
     * �� ����
     *
     * @param customerId ��ID
     */
    public void deleteCustomerById(int customerId);

}
