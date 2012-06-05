/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.test.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * SpringDbUnitTestCase �׽�Ʈ���̽� <br/>
 * �������ҵ� ��.
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">�ֿ���</a>
 * @since 2012. 5. 19.
 */
public class SpringDbUnitTestCaseTest extends SpringDbUnitTestCase {

    @Override
    protected DbUnitDataType getDbUnitDataType() {
        return DbUnitDataType.HSQLDB;
    }

    // -------------------------------------------------------------------------
    // Test Method
    // -------------------------------------------------------------------------

    @Test
    public void test() {
        assertTrue(true);
    }

}
