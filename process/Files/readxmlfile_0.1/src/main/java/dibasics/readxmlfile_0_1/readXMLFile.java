
package dibasics.readxmlfile_0_1;

import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.DataQuality;
import routines.Relational;
import routines.Mathematical;
import routines.DataQualityDependencies;
import routines.SQLike;
import routines.Numeric;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.DQTechnical;
import routines.MDM;
import routines.TalendDate;
import routines.DataMasking;
import routines.DqStringHandling;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: readXMLFile Purpose: Import product sales<br>
 * Description: Data source is a XML file <br>
 * 
 * @author user@talend.com
 * @version 7.3.1.20200219_1130
 * @status
 */
public class readXMLFile implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "readXMLFile.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(readXMLFile.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "readXMLFile";
	private final String projectName = "DIBASICS";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName, "_dWosEMJ0EeyuzLpJyfGUKQ", "0.1");
	org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					readXMLFile.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(readXMLFile.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputXML_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputXML_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputXML_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputXML_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_DIBASICS_readXMLFile = new byte[0];
		static byte[] commonByteArray_DIBASICS_readXMLFile = new byte[0];

		public String ShopName;

		public String getShopName() {
			return this.ShopName;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public String CustomerName;

		public String getCustomerName() {
			return this.CustomerName;
		}

		public String Product;

		public String getProduct() {
			return this.Product;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DIBASICS_readXMLFile.length) {
					if (length < 1024 && commonByteArray_DIBASICS_readXMLFile.length == 0) {
						commonByteArray_DIBASICS_readXMLFile = new byte[1024];
					} else {
						commonByteArray_DIBASICS_readXMLFile = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DIBASICS_readXMLFile, 0, length);
				strReturn = new String(commonByteArray_DIBASICS_readXMLFile, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DIBASICS_readXMLFile) {

				try {

					int length = 0;

					this.ShopName = readString(dis);

					this.Quantity = readInteger(dis);

					this.CustomerName = readString(dis);

					this.Product = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.ShopName, dos);

				// Integer

				writeInteger(this.Quantity, dos);

				// String

				writeString(this.CustomerName, dos);

				// String

				writeString(this.Product, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("ShopName=" + ShopName);
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",CustomerName=" + CustomerName);
			sb.append(",Product=" + Product);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (ShopName == null) {
				sb.append("<null>");
			} else {
				sb.append(ShopName);
			}

			sb.append("|");

			if (Quantity == null) {
				sb.append("<null>");
			} else {
				sb.append(Quantity);
			}

			sb.append("|");

			if (CustomerName == null) {
				sb.append("<null>");
			} else {
				sb.append(CustomerName);
			}

			sb.append("|");

			if (Product == null) {
				sb.append("<null>");
			} else {
				sb.append(Product);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputXML_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();

				/**
				 * [tLogRow_1 begin ] start
				 */

				ok_Hash.put("tLogRow_1", false);
				start_Hash.put("tLogRow_1", System.currentTimeMillis());

				currentComponent = "tLogRow_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_1 = new StringBuilder();
							log4jParamters_tLogRow_1.append("Parameters:");
							log4jParamters_tLogRow_1.append("BASIC_MODE" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_1 - " + (log4jParamters_tLogRow_1));
						}
					}
					new BytesLimit65535_tLogRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_1", "tLogRow");
					talendJobLogProcess(globalMap);
				}

				///////////////////////

				class Util_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[4];

					public void addRow(String[] row) {

						for (int i = 0; i < 4; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 3 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 3 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|%4$-");
							sbformat.append(colLengths[3]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[2] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[3] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_tLogRow_1 util_tLogRow_1 = new Util_tLogRow_1();
				util_tLogRow_1.setTableName("Console");
				util_tLogRow_1.addRow(new String[] { "ShopName", "Quantity", "CustomerName", "Product", });
				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tFileInputXML_1 begin ] start
				 */

				ok_Hash.put("tFileInputXML_1", false);
				start_Hash.put("tFileInputXML_1", System.currentTimeMillis());

				currentComponent = "tFileInputXML_1";

				int tos_count_tFileInputXML_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputXML_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileInputXML_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileInputXML_1 = new StringBuilder();
							log4jParamters_tFileInputXML_1.append("Parameters:");
							log4jParamters_tFileInputXML_1
									.append("FILENAME" + " = " + "\"C:/StudentFiles/DIBasics/Files/ProductSales.xml\"");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("LOOP_QUERY" + " = " + "\"/root/row\"");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("MAPPING" + " = " + "[{QUERY=" + ("\"ShopName\"")
									+ ", NODECHECK=" + ("false") + ", SCHEMA_COLUMN=" + ("ShopName") + "}, {QUERY="
									+ ("\"Quantity\"") + ", NODECHECK=" + ("false") + ", SCHEMA_COLUMN=" + ("Quantity")
									+ "}, {QUERY=" + ("\"CustomerName\"") + ", NODECHECK=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("CustomerName") + "}, {QUERY=" + ("\"Product\"")
									+ ", NODECHECK=" + ("false") + ", SCHEMA_COLUMN=" + ("Product") + "}]");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("LIMIT" + " = " + "");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("IGNORE_NS" + " = " + "false");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("IGNORE_DTD" + " = " + "false");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("USE_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("GENERATION_MODE" + " = " + "Dom4j");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("CHECK_DATE" + " = " + "false");
							log4jParamters_tFileInputXML_1.append(" | ");
							log4jParamters_tFileInputXML_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileInputXML_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileInputXML_1 - " + (log4jParamters_tFileInputXML_1));
						}
					}
					new BytesLimit65535_tFileInputXML_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileInputXML_1", "tFileInputXML");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tFileInputXML_1 = 0;

				String os_tFileInputXML_1 = System.getProperty("os.name").toLowerCase();
				boolean isWindows_tFileInputXML_1 = false;
				if (os_tFileInputXML_1.indexOf("windows") > -1 || os_tFileInputXML_1.indexOf("nt") > -1) {
					isWindows_tFileInputXML_1 = true;
				}
				class NameSpaceTool_tFileInputXML_1 {

					public java.util.HashMap<String, String> xmlNameSpaceMap = new java.util.HashMap<String, String>();

					private java.util.List<String> defualtNSPath = new java.util.ArrayList<String>();

					public void countNSMap(org.dom4j.Element el) {
						for (org.dom4j.Namespace ns : (java.util.List<org.dom4j.Namespace>) el.declaredNamespaces()) {
							if (ns.getPrefix().trim().length() == 0) {
								xmlNameSpaceMap.put("pre" + defualtNSPath.size(), ns.getURI());
								String path = "";
								org.dom4j.Element elTmp = el;
								while (elTmp != null) {
									if (elTmp.getNamespacePrefix() != null && elTmp.getNamespacePrefix().length() > 0) {
										path = "/" + elTmp.getNamespacePrefix() + ":" + elTmp.getName() + path;
									} else {
										path = "/" + elTmp.getName() + path;
									}
									elTmp = elTmp.getParent();
								}
								defualtNSPath.add(path);
							} else {
								xmlNameSpaceMap.put(ns.getPrefix(), ns.getURI());
							}

						}
						for (org.dom4j.Element e : (java.util.List<org.dom4j.Element>) el.elements()) {
							countNSMap(e);
						}
					}

					private final org.talend.xpath.XPathUtil util = new org.talend.xpath.XPathUtil();

					{
						util.setDefaultNSPath(defualtNSPath);
					}

					public String addDefaultNSPrefix(String path) {
						return util.addDefaultNSPrefix(path);
					}

					public String addDefaultNSPrefix(String relativeXpression, String basePath) {
						return util.addDefaultNSPrefix(relativeXpression, basePath);
					}

				}

				class XML_API_tFileInputXML_1 {
					public boolean isDefNull(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
						if (node != null && node instanceof org.dom4j.Element) {
							org.dom4j.Attribute attri = ((org.dom4j.Element) node).attribute("nil");
							if (attri != null && ("true").equals(attri.getText())) {
								return true;
							}
						}
						return false;
					}

					public boolean isMissing(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
						return node == null ? true : false;
					}

					public boolean isEmpty(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
						if (node != null) {
							return node.getText().length() == 0;
						}
						return false;
					}
				}

				org.dom4j.io.SAXReader reader_tFileInputXML_1 = new org.dom4j.io.SAXReader();
				Object filename_tFileInputXML_1 = null;
				try {
					filename_tFileInputXML_1 = "C:/StudentFiles/DIBasics/Files/ProductSales.xml";
				} catch (java.lang.Exception e) {

					log.error("tFileInputXML_1 - " + e.getMessage());

					System.err.println(e.getMessage());

				}
				if (filename_tFileInputXML_1 != null && filename_tFileInputXML_1 instanceof String
						&& filename_tFileInputXML_1.toString().startsWith("//")) {
					if (!isWindows_tFileInputXML_1) {
						filename_tFileInputXML_1 = filename_tFileInputXML_1.toString().replaceFirst("//", "/");
					}
				}

				boolean isValidFile_tFileInputXML_1 = true;
				org.dom4j.Document doc_tFileInputXML_1 = null;
				java.io.Closeable toClose_tFileInputXML_1 = null;
				try {
					if (filename_tFileInputXML_1 instanceof java.io.InputStream) {
						java.io.InputStream inputStream_tFileInputXML_1 = (java.io.InputStream) filename_tFileInputXML_1;
						toClose_tFileInputXML_1 = inputStream_tFileInputXML_1;
						doc_tFileInputXML_1 = reader_tFileInputXML_1.read(inputStream_tFileInputXML_1);
					} else {
						java.io.Reader unicodeReader_tFileInputXML_1 = new UnicodeReader(
								new java.io.FileInputStream(String.valueOf(filename_tFileInputXML_1)), "ISO-8859-15");
						toClose_tFileInputXML_1 = unicodeReader_tFileInputXML_1;
						org.xml.sax.InputSource in_tFileInputXML_1 = new org.xml.sax.InputSource(
								unicodeReader_tFileInputXML_1);
						doc_tFileInputXML_1 = reader_tFileInputXML_1.read(in_tFileInputXML_1);
					}
				} catch (java.lang.Exception e) {

					log.error("tFileInputXML_1 - " + e.getMessage());

					System.err.println(e.getMessage());
					isValidFile_tFileInputXML_1 = false;
				} finally {
					if (toClose_tFileInputXML_1 != null) {
						toClose_tFileInputXML_1.close();
					}
				}
				if (isValidFile_tFileInputXML_1) {
					NameSpaceTool_tFileInputXML_1 nsTool_tFileInputXML_1 = new NameSpaceTool_tFileInputXML_1();
					nsTool_tFileInputXML_1.countNSMap(doc_tFileInputXML_1.getRootElement());
					java.util.HashMap<String, String> xmlNameSpaceMap_tFileInputXML_1 = nsTool_tFileInputXML_1.xmlNameSpaceMap;

					org.dom4j.XPath x_tFileInputXML_1 = doc_tFileInputXML_1
							.createXPath(nsTool_tFileInputXML_1.addDefaultNSPrefix("/root/row"));
					x_tFileInputXML_1.setNamespaceURIs(xmlNameSpaceMap_tFileInputXML_1);

					java.util.List<org.dom4j.Node> nodeList_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) x_tFileInputXML_1
							.selectNodes(doc_tFileInputXML_1);
					XML_API_tFileInputXML_1 xml_api_tFileInputXML_1 = new XML_API_tFileInputXML_1();
					String str_tFileInputXML_1 = "";
					org.dom4j.Node node_tFileInputXML_1 = null;

//init all mapping xpaths
					java.util.Map<Integer, org.dom4j.XPath> xpaths_tFileInputXML_1 = new java.util.HashMap<Integer, org.dom4j.XPath>();
					class XPathUtil_tFileInputXML_1 {

						public void initXPaths_0(java.util.Map<Integer, org.dom4j.XPath> xpaths,
								NameSpaceTool_tFileInputXML_1 nsTool,
								java.util.HashMap<String, String> xmlNameSpaceMap) {

							org.dom4j.XPath xpath_0 = org.dom4j.DocumentHelper
									.createXPath(nsTool.addDefaultNSPrefix("ShopName", "/root/row"));
							xpath_0.setNamespaceURIs(xmlNameSpaceMap);

							xpaths.put(0, xpath_0);

							org.dom4j.XPath xpath_1 = org.dom4j.DocumentHelper
									.createXPath(nsTool.addDefaultNSPrefix("Quantity", "/root/row"));
							xpath_1.setNamespaceURIs(xmlNameSpaceMap);

							xpaths.put(1, xpath_1);

							org.dom4j.XPath xpath_2 = org.dom4j.DocumentHelper
									.createXPath(nsTool.addDefaultNSPrefix("CustomerName", "/root/row"));
							xpath_2.setNamespaceURIs(xmlNameSpaceMap);

							xpaths.put(2, xpath_2);

							org.dom4j.XPath xpath_3 = org.dom4j.DocumentHelper
									.createXPath(nsTool.addDefaultNSPrefix("Product", "/root/row"));
							xpath_3.setNamespaceURIs(xmlNameSpaceMap);

							xpaths.put(3, xpath_3);

						}

						public void initXPaths(java.util.Map<Integer, org.dom4j.XPath> xpaths,
								NameSpaceTool_tFileInputXML_1 nsTool,
								java.util.HashMap<String, String> xmlNameSpaceMap) {

							initXPaths_0(xpaths, nsTool, xmlNameSpaceMap);

						}
					}
					XPathUtil_tFileInputXML_1 xPathUtil_tFileInputXML_1 = new XPathUtil_tFileInputXML_1();
					xPathUtil_tFileInputXML_1.initXPaths(xpaths_tFileInputXML_1, nsTool_tFileInputXML_1,
							xmlNameSpaceMap_tFileInputXML_1);
					log.debug("tFileInputXML_1 - Retrieving records from the datasource.");

					for (org.dom4j.Node temp_tFileInputXML_1 : nodeList_tFileInputXML_1) {
						nb_line_tFileInputXML_1++;

						row1 = null;
						boolean whetherReject_tFileInputXML_1 = false;
						row1 = new row1Struct();
						try {
							Object obj0_tFileInputXML_1 = xpaths_tFileInputXML_1.get(0).evaluate(temp_tFileInputXML_1);
							if (obj0_tFileInputXML_1 == null) {
								node_tFileInputXML_1 = null;
								str_tFileInputXML_1 = "";

							} else if (obj0_tFileInputXML_1 instanceof org.dom4j.Node) {
								node_tFileInputXML_1 = (org.dom4j.Node) obj0_tFileInputXML_1;
								str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
										org.jaxen.dom4j.DocumentNavigator.getInstance());
							} else if (obj0_tFileInputXML_1 instanceof String
									|| obj0_tFileInputXML_1 instanceof Number) {
								node_tFileInputXML_1 = temp_tFileInputXML_1;
								str_tFileInputXML_1 = String.valueOf(obj0_tFileInputXML_1);
							} else if (obj0_tFileInputXML_1 instanceof java.util.List) {
								java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj0_tFileInputXML_1;
								node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
										: null;
								str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
										: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
												org.jaxen.dom4j.DocumentNavigator.getInstance());
							}
							if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
								row1.ShopName = null;
							} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
								row1.ShopName = "";
							} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
								row1.ShopName = null;
							} else {
								row1.ShopName = str_tFileInputXML_1;
							}
							Object obj1_tFileInputXML_1 = xpaths_tFileInputXML_1.get(1).evaluate(temp_tFileInputXML_1);
							if (obj1_tFileInputXML_1 == null) {
								node_tFileInputXML_1 = null;
								str_tFileInputXML_1 = "";

							} else if (obj1_tFileInputXML_1 instanceof org.dom4j.Node) {
								node_tFileInputXML_1 = (org.dom4j.Node) obj1_tFileInputXML_1;
								str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
										org.jaxen.dom4j.DocumentNavigator.getInstance());
							} else if (obj1_tFileInputXML_1 instanceof String
									|| obj1_tFileInputXML_1 instanceof Number) {
								node_tFileInputXML_1 = temp_tFileInputXML_1;
								str_tFileInputXML_1 = String.valueOf(obj1_tFileInputXML_1);
							} else if (obj1_tFileInputXML_1 instanceof java.util.List) {
								java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj1_tFileInputXML_1;
								node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
										: null;
								str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
										: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
												org.jaxen.dom4j.DocumentNavigator.getInstance());
							}
							if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
								row1.Quantity = null;
							} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)
									|| xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
								row1.Quantity = null;
							} else {
								row1.Quantity = ParserUtils.parseTo_Integer(str_tFileInputXML_1);
							}
							Object obj2_tFileInputXML_1 = xpaths_tFileInputXML_1.get(2).evaluate(temp_tFileInputXML_1);
							if (obj2_tFileInputXML_1 == null) {
								node_tFileInputXML_1 = null;
								str_tFileInputXML_1 = "";

							} else if (obj2_tFileInputXML_1 instanceof org.dom4j.Node) {
								node_tFileInputXML_1 = (org.dom4j.Node) obj2_tFileInputXML_1;
								str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
										org.jaxen.dom4j.DocumentNavigator.getInstance());
							} else if (obj2_tFileInputXML_1 instanceof String
									|| obj2_tFileInputXML_1 instanceof Number) {
								node_tFileInputXML_1 = temp_tFileInputXML_1;
								str_tFileInputXML_1 = String.valueOf(obj2_tFileInputXML_1);
							} else if (obj2_tFileInputXML_1 instanceof java.util.List) {
								java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj2_tFileInputXML_1;
								node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
										: null;
								str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
										: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
												org.jaxen.dom4j.DocumentNavigator.getInstance());
							}
							if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
								row1.CustomerName = null;
							} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
								row1.CustomerName = "";
							} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
								row1.CustomerName = null;
							} else {
								row1.CustomerName = str_tFileInputXML_1;
							}
							Object obj3_tFileInputXML_1 = xpaths_tFileInputXML_1.get(3).evaluate(temp_tFileInputXML_1);
							if (obj3_tFileInputXML_1 == null) {
								node_tFileInputXML_1 = null;
								str_tFileInputXML_1 = "";

							} else if (obj3_tFileInputXML_1 instanceof org.dom4j.Node) {
								node_tFileInputXML_1 = (org.dom4j.Node) obj3_tFileInputXML_1;
								str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
										org.jaxen.dom4j.DocumentNavigator.getInstance());
							} else if (obj3_tFileInputXML_1 instanceof String
									|| obj3_tFileInputXML_1 instanceof Number) {
								node_tFileInputXML_1 = temp_tFileInputXML_1;
								str_tFileInputXML_1 = String.valueOf(obj3_tFileInputXML_1);
							} else if (obj3_tFileInputXML_1 instanceof java.util.List) {
								java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj3_tFileInputXML_1;
								node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
										: null;
								str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
										: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
												org.jaxen.dom4j.DocumentNavigator.getInstance());
							}
							if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
								row1.Product = null;
							} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
								row1.Product = "";
							} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
								row1.Product = null;
							} else {
								row1.Product = str_tFileInputXML_1;
							}
							log.debug("tFileInputXML_1 - Retrieving the record " + (nb_line_tFileInputXML_1) + ".");

						} catch (java.lang.Exception e) {
							whetherReject_tFileInputXML_1 = true;
							log.error("tFileInputXML_1 - " + e.getMessage());

							System.err.println(e.getMessage());
							row1 = null;
						}

						/**
						 * [tFileInputXML_1 begin ] stop
						 */

						/**
						 * [tFileInputXML_1 main ] start
						 */

						currentComponent = "tFileInputXML_1";

						tos_count_tFileInputXML_1++;

						/**
						 * [tFileInputXML_1 main ] stop
						 */

						/**
						 * [tFileInputXML_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputXML_1";

						/**
						 * [tFileInputXML_1 process_data_begin ] stop
						 */
// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tLogRow_1 main ] start
							 */

							currentComponent = "tLogRow_1";

							runStat.updateStatAndLog(execStat, enableLogStash, iterateId, 1, 1, "row1");

							if (log.isTraceEnabled()) {
								log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
							}

///////////////////////		

							String[] row_tLogRow_1 = new String[4];

							if (row1.ShopName != null) { //
								row_tLogRow_1[0] = String.valueOf(row1.ShopName);

							} //

							if (row1.Quantity != null) { //
								row_tLogRow_1[1] = String.valueOf(row1.Quantity);

							} //

							if (row1.CustomerName != null) { //
								row_tLogRow_1[2] = String.valueOf(row1.CustomerName);

							} //

							if (row1.Product != null) { //
								row_tLogRow_1[3] = String.valueOf(row1.Product);

							} //

							util_tLogRow_1.addRow(row_tLogRow_1);
							nb_line_tLogRow_1++;
							log.info("tLogRow_1 - Content of row " + nb_line_tLogRow_1 + ": "
									+ TalendString.unionString("|", row_tLogRow_1));
//////

//////                    

///////////////////////    			

							tos_count_tLogRow_1++;

							/**
							 * [tLogRow_1 main ] stop
							 */

							/**
							 * [tLogRow_1 process_data_begin ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_begin ] stop
							 */

							/**
							 * [tLogRow_1 process_data_end ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_end ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputXML_1 process_data_end ] start
						 */

						currentComponent = "tFileInputXML_1";

						/**
						 * [tFileInputXML_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputXML_1 end ] start
						 */

						currentComponent = "tFileInputXML_1";

					}
				}
				globalMap.put("tFileInputXML_1_NB_LINE", nb_line_tFileInputXML_1);
				log.debug("tFileInputXML_1 - Retrieved records count: " + nb_line_tFileInputXML_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tFileInputXML_1 - " + ("Done."));

				ok_Hash.put("tFileInputXML_1", true);
				end_Hash.put("tFileInputXML_1", System.currentTimeMillis());

				/**
				 * [tFileInputXML_1 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				currentComponent = "tLogRow_1";

//////

				java.io.PrintStream consoleOut_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
				}

				consoleOut_tLogRow_1.println(util_tLogRow_1.format().toString());
				consoleOut_tLogRow_1.flush();
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("tLogRow_1 - " + ("Printed row count: ") + (nb_line_tLogRow_1) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						talendJobLog, "tFileInputXML_1", "tFileInputXML", "tLogRow_1", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Done."));

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputXML_1 finally ] start
				 */

				currentComponent = "tFileInputXML_1";

				/**
				 * [tFileInputXML_1 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				currentComponent = "tLogRow_1";

				/**
				 * [tLogRow_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				ok_Hash.put("talendJobLog", false);
				start_Hash.put("talendJobLog", System.currentTimeMillis());

				currentComponent = "talendJobLog";

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.logging.audit.Context log_context_talendJobLog = null;
					if (jcm.component_name == null) {// job level log
						if (jcm.status == null) {// job start
							log_context_talendJobLog = org.talend.job.audit.JobContextBuilder.create()
									.jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
									.timestamp(jcm.moment).build();
							auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
						} else {// job end
							long timeMS = jcm.end_time - jcm.start_time;
							String duration = String.format(java.util.Locale.US, "%1$.2fs", (timeMS * 1.0) / 1000);

							log_context_talendJobLog = org.talend.job.audit.JobContextBuilder.create()
									.jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
									.timestamp(jcm.moment).duration(duration).status(jcm.status).build();
							auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
						}
					} else if (jcm.current_connector == null) {// component log
						log_context_talendJobLog = org.talend.job.audit.JobContextBuilder.create().jobName(jcm.job_name)
								.jobId(jcm.job_id).jobVersion(jcm.job_version).connectorType(jcm.component_name)
								.connectorId(jcm.component_id).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else {// component connector meter log
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.format(java.util.Locale.US, "%1$.2fs", (timeMS * 1.0) / 1000);

						if (jcm.current_connector_as_input) {// log current component input line
							log_context_talendJobLog = org.talend.job.audit.JobContextBuilder.create()
									.jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
									.connectorType(jcm.component_name).connectorId(jcm.component_id)
									.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
									.rows(jcm.total_row_number).duration(duration).build();
							auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
						} else {// log current component output/reject line
							log_context_talendJobLog = org.talend.job.audit.JobContextBuilder.create()
									.jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
									.connectorType(jcm.component_name).connectorId(jcm.component_id)
									.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
									.rows(jcm.total_row_number).duration(duration).build();
							auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
						}
					}
				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				currentComponent = "talendJobLog";

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				currentComponent = "talendJobLog";

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	private PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final readXMLFile readXMLFileClass = new readXMLFile();

		int exitCode = readXMLFileClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'readXMLFile' - Done.");
		}

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("monitoring"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}
		log.info("TalendJob: 'readXMLFile' - Start.");

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream()
					.filter(it -> it.startsWith("monitoring.audit.logger.properties."))
					.forEach(key -> properties_talendJobLog.setProperty(
							key.substring("monitoring.audit.logger.properties.".length()), System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator.setLevel("audit", org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = readXMLFile.class.getClassLoader()
					.getResourceAsStream("dibasics/readxmlfile_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = readXMLFile.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				// defaultProps is in order to keep the original context value
				if (context != null && context.isEmpty()) {
					defaultProps.load(inContext);
					context = new ContextProperties(defaultProps);
				}

				inContext.close();
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputXML_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputXML_1) {
			globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", -1);

			e_tFileInputXML_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : readXMLFile");
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;
		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--monitoring") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 67849 characters generated by Talend Data Fabric on the April 22, 2022 at
 * 7:57:51 PM GMT
 ************************************************************************************************/