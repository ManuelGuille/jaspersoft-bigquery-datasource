# Summary #
**Configuring iReport**

**1. Installation**

**2. Define a Connection**

# Details #
## **Configuring iReport** ##
## **1. Installation** ##
1. Download the BigQuery connector. This includes the plugin (BigQuery-xxx.nbm).

2. In iReport choose the menu Tools → Plugins.

3. On the tab 'Downloaded' browse to the .nbm file then click 'Install'.

![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/BigQueryPlugin.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/BigQueryPlugin.png)

4. Restart iReport and the connector will complete its installation.

## **2. Define a Connection** ##

1. Click the button "Report Datasources" to define a new connection to BigQuery.

https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/ReportDatasources.PNG

2. Add a new datasource of type "BigQuery Connection"

3. Fill in the following details pertaining to your Google APIs Console project:
  * account email id
  * project id
  * private key

![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/BigQueryConnection.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/BigQueryConnection.png)

4. Test the connection.

![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/BigQueryConnection_test.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/BigQueryConnection_test.png)