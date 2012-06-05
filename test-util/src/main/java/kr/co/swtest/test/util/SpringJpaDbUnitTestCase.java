/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.test.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Spring JPA Test Case with DBunit
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">�ֿ���</a>
 * @since 2012. 5. 19.
 */
@TransactionConfiguration(transactionManager = "jpaTransactionManager", defaultRollback = true)
public abstract class SpringJpaDbUnitTestCase extends SpringDbUnitTestCase {

    /** entityManagerFactory */
    @PersistenceUnit
    protected EntityManagerFactory entityManagerFactory;

    /** entityManager */
    @PersistenceContext
    protected EntityManager entityManager;

}
