package kr.co.swtest.jpa.type;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Customer (JPA example)
 *
 * @author <a href="mailto:davidchoi@nextree.co.kr">�ֿ���</a>
 * @since 2012. 5. 19.
 */
@Entity(name = "Customer")
public class Customer implements Serializable {

    /** UID */
    private static final long serialVersionUID = -9098918044736522968L;

    /** ID */
    @Id
    @Column(name = "CUSTOMER_ID")
    private Integer id;

    /** ���� */
    @Column(nullable = false)
    private String name;

    /** �̸��� */
    @Column(nullable = false)
    private String email;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * ������
     */
    public Customer() {
        // �ƹ��ϵ� ���� ����
    }

    /**
     * ������
     *
     * @param id ID
     * @param name ����
     * @param email �̸���
     */
    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // -------------------------------------------------------------------------
    // Getter and Setter
    // -------------------------------------------------------------------------

    /**
     * @return ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return ����
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name ����
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return �̸���
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email �̸���
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
