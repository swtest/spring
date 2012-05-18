package kr.co.swtest.test.util;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Test Case with DBunit
 *
 * @author <a href="mailto:davidchoi@nextree.co.kr">�ֿ���</a>
 * @since 2012. 5. 19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(propagation = Propagation.REQUIRED)
public abstract class SpringDbUnitTestCase {

	/** �����ͼҽ� */
	@Autowired
	private DataSource dataSource;

	// -------------------------------------------------------------------------
	// Public Method
	// -------------------------------------------------------------------------

	/**
	 * Ʈ������ ������ ������ �۾�
	 *
	 * @throws Exception
	 *             �ʱ� ������ �ε� �� ������ ���� ��� �߻�
	 */
	@BeforeTransaction
	public void beforeTransaction() throws Exception {
		this.createTable();
		this.loadTestData();
	}

	/**
	 * Ʈ������ ������ ������ �۾�
	 *
	 * @throws Exception
	 *             �׽�Ʈ ������ ���� �� ������ ���� ��� �߻�
	 */
	@AfterTransaction
	public void afterTransaction() throws Exception {
		this.cleanTestData();
	}

	// -------------------------------------------------------------------------
	// Protected Method
	// -------------------------------------------------------------------------

	/**
	 * �׽�Ʈ�����͸� �ε��ϱ� ���ؼ��� �� �޼ҵ带 �����ؾ� ��
	 *
	 * @return �׽�Ʈ������ XML ���ҽ� ��ġ. ��)
	 *         kr/co/swtest/customerentity/dao/sqlmap/xxx.xml
	 */
	protected List<String> addDataFiles() {
		return null;
	}

	/**
	 * �׽�Ʈ�����͸� �ε��ϱ� ���ؼ��� �� �޼ҵ带 �����ؾ� ��
	 *
	 * @return �׽�Ʈ������ XML ���ҽ� ��ġ. ��)
	 *         kr/co/swtest/customerentity/dao/sqlmap/xxx.xml
	 */
	protected String addDataFile() {
		return null;
	}

	/**
	 * @return �����ͼҽ�
	 */
	protected DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * @return DbUnit ������Ÿ��
	 */
	protected DbUnitDataType getDbUnitDataType() {
		return DbUnitDataType.DEFAULT;
	}

	/**
	 * @return ��Ű����
	 */
	protected String getSchemaName() {
		return null;
	}

	/**
	 * ���̺� ����
	 */
	protected void createTable() {
		// ���̺� ����
	}

	// -------------------------------------------------------------------------
	// Private Method
	// -------------------------------------------------------------------------

	/**
	 * �ʱ� �׽�Ʈ ������ �ε�
	 */
	private void loadTestData() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}

	/**
	 * �׽�Ʈ ������ ����
	 */
	private void cleanTestData() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
	}

	/**
	 * IDatabaseConnection ����
	 *
	 * @return ����Ŭ�� IDatabaseConnection. <code>not null</code> ����.
	 */
	private IDatabaseConnection getConnection() throws Exception {
		DatabaseConnection connection = new DatabaseConnection(
				this.dataSource.getConnection(), getSchemaName());
		DatabaseConfig config = connection.getConfig();
		setDataTypeFa1ctory(config, getDbUnitDataType());
		return connection;
	}

	/**
	 * IDataSet ����
	 *
	 * @return IDataSet. <code>not null</code> ����.
	 */
	private IDataSet getDataSet() throws Exception {
		List<String> dataFiles = this.getDataFiles();
		if (dataFiles == null) {
			dataFiles = new ArrayList<String>();
		}

		List<IDataSet> dbUnitDataSets = new ArrayList<IDataSet>(
				dataFiles.size());

		for (String file : dataFiles) {
			ReplacementDataSet dataSet = new ReplacementDataSet(
					new FlatXmlDataSetBuilder().build(Thread.currentThread()
							.getContextClassLoader().getResourceAsStream(file)));
			// ���� [NULL] �̸� null ������ ó����.
			dataSet.addReplacementObject("[NULL]", null);
			dbUnitDataSets.add(dataSet);
		}

		return new CompositeDataSet(
				dbUnitDataSets.toArray(new IDataSet[dbUnitDataSets.size()]));
	}

	/**
	 * �׽�Ʈ������ XML ���� ��ġ ���
	 *
	 * @return �׽�Ʈ������ XML ���� ��ġ ���. <code>not null</code> ����.
	 */
	private List<String> getDataFiles() {
		List<String> dataFiles = new ArrayList<String>();

		if (this.addDataFiles() != null) {
			dataFiles.addAll(this.addDataFiles());
		}

		if (this.addDataFile() != null) {
			dataFiles.add(this.addDataFile());
		}

		return dataFiles;
	}

	/**
	 * DataTypeFa1ctory ����
	 *
	 * @param config DatabaseConfig
	 * @param dbUnitDataType DbUnitDataType
	 */
	private void setDataTypeFa1ctory(DatabaseConfig config, DbUnitDataType dbUnitDataType) {
		switch (dbUnitDataType) {
			case DEFAULT:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new DefaultDataTypeFactory());
				break;

			case MYSQL:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
				break;

			case MSSQL:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MsSqlDataTypeFactory());
				break;

			case DB2:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Db2DataTypeFactory());
				break;

			case ORACLE:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
				break;

			case ORACLE10G:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
				break;

			case HSQLDB:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
				break;

			default:
				break;
		}
	}

}
