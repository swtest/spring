package kr.co.swtest.test.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * SpringJpaDbUnitTestCase �׽�Ʈ���̽� <br/>
 * �������ҵ� ��.
 *
 * @author <a href="mailto:davidchoi@nextree.co.kr">�ֿ���</a>
 * @since 2012. 5. 19.
 */
public class SpringJpaDbUnitTestCaseTest extends SpringJpaDbUnitTestCase {

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
