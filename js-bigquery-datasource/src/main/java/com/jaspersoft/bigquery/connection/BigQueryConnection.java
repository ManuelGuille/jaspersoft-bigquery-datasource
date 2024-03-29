/*
 * Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.bigquery.connection;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.log4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Datasets.List;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.DatasetList;
import com.google.api.services.bigquery.model.DatasetList.Datasets;

/**
 * 
 * @author Eric Diaz
 * @modified Matthew Dahlman 2012-07-06
 * 
 */
public class BigQueryConnection implements Connection {
    private HttpTransport transport;

    private JsonFactory jsonFactory;

    private String serviceAccountId;

    private String privateKeyFilePath;

    private String projectId;

    private static final String APPLICATION_NAME = "Jaspersoft BigQuery Connector 0.0.5";

    private final static String NTP_SERVER = "0.pool.ntp.org";

    private final Logger logger = Logger.getLogger(BigQueryConnection.class);

    private Bigquery bigquery;

    public BigQueryConnection(String serviceAccountId, String privateKeyFilePath, String projectId) throws Throwable {
        transport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();
        this.serviceAccountId = serviceAccountId;
        this.privateKeyFilePath = privateKeyFilePath;
        this.projectId = projectId;
        if (logger.isDebugEnabled()) {
            logger.debug("serviceAccountId: " + serviceAccountId);
            logger.debug("privateKeyFilePath: " + privateKeyFilePath);
        }
        create();
    }

    private void create() throws Throwable {
        File privateKeyFile = new File(privateKeyFilePath);
        if (!privateKeyFile.exists()) {
            logger.info("File \"" + privateKeyFilePath + "\" doesn't seem to be a full path. Searching the classpath.");
            URL resource = getClass().getClassLoader().getResource(privateKeyFilePath);
            if (resource == null) {
                throw new JRException("The file \"" + privateKeyFilePath + "\" doesn't exist");
            }
            privateKeyFile = new File(resource.getPath());
        }
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(transport)
                .setJsonFactory(jsonFactory).setServiceAccountId(serviceAccountId)
                .setServiceAccountScopes(BigqueryScopes.BIGQUERY)
                .setServiceAccountPrivateKeyFromP12File(privateKeyFile).build();

        bigquery = Bigquery.builder(transport, jsonFactory).setApplicationName(APPLICATION_NAME)
                .setHttpRequestInitializer(credential).build();
    }

    public String getProjectId() {
        return projectId;
    }

    public Bigquery getBigquery() {
        return bigquery;
    }

    public String getPrivateKeyFilePath() {
        return privateKeyFilePath;
    }

    public String getServiceAccountId() {
        return serviceAccountId;
    }

    @Override
    public void close() {
        if (transport != null) {
            try {
                transport.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
            transport = null;
        }
        jsonFactory = null;
        bigquery = null;
        logger.info("BigQuery connection closed");
    }

    @Override
    public boolean isClosed() throws SQLException {
        return transport == null;
    }

    public String test() throws JRException {
        try {
            if (transport != null && jsonFactory != null && bigquery != null) {
                List datasetRequest = bigquery.datasets().list("publicdata");
                DatasetList datasetList = datasetRequest.execute();
                StringBuilder builder = new StringBuilder();
                builder.append("Available datasets: ");
                if (datasetList != null) {
                    java.util.List<Datasets> datasets = datasetList.getDatasets();
                    for (Datasets dataset : datasets) {
                        builder.append(dataset.getId());
                        builder.append(",");
                    }
                }
                if (builder.charAt(builder.length() - 1) == ',') {
                    builder.setCharAt(builder.length() - 1, '.');
                }
                return builder.toString();
            }
        } catch (Throwable e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("unauthorized")) {
                logger.error(e);
                NTPUDPClient ntpClient = new NTPUDPClient();
                TimeInfo timeInfo = null;
                try {
                    timeInfo = ntpClient.getTime(InetAddress.getByName(NTP_SERVER));
                } catch (Throwable e1) {
                    e1.printStackTrace();
                } finally {
                    ntpClient.close();
                }
                StringBuilder timeMessage = new StringBuilder();
                timeMessage.append("Ensure your system clock is synchronized with an NTP server.\n");
                if (timeInfo != null) {
                  timeInfo.computeDetails();
                  long offset = (timeInfo.getOffset() != null) ? timeInfo.getOffset() : 0l;
                  SimpleDateFormat df    = new SimpleDateFormat("EEE yyyy MMM d HH:mm:ss SSS z");
                  String localDateString = df.format( new java.util.Date(System.currentTimeMillis()) );
                  String ntpDateString   = df.format( new java.util.Date(System.currentTimeMillis() + offset) );
                  timeMessage.append("Current offset against \"");
                  timeMessage.append(NTP_SERVER);
                  timeMessage.append("\" is: ");
                  timeMessage.append(offset);
                  timeMessage.append(" milliseconds.\n");
                  timeMessage.append("Current time on NTP   server: " + ntpDateString + "\n");
                  timeMessage.append("Current time on local server: " + localDateString );
                }
                throw new JRException(
                        "Unauthorized exception. Please review your serviceAccountId and privateKeyFilePath.\n"
                                + timeMessage.toString());
            }
            throw new JRException(e);
        }
        throw new JRException("Could not connect to BigQuery");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public void commit() throws SQLException {
    }

    @Override
    public void rollback() throws SQLException {
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }
}