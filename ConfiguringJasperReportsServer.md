# Summary #
Configuring JasperReports Server

1. Deploy the Big Query connector to JasperReports Server

2. Define the Data Source

3. Deploy a report to JasperReports Server

# Details #

## **Configuring JasperReports Server** ##

## **1. Deploy the Big Query connector to JasperReports Server** ##
a) Unzip the file jrs-bigquery-x.y.z.zip. It will have a structure similar to this:

```
WEB-INF/applicationContext-BigQueryDataSource.xml
WEB-INF/bundles/BigQueryDataSource.properties
WEB-INF/classes/yourPrivateKeyGoesInThisFolder.p12
WEB-INF/lib/google-api-client-1.8.0-beta.jar
WEB-INF/lib/google-api-services-bigquery-v2-rev5-1.5.0-beta.jar
WEB-INF/lib/google-http-client-1.8.3-beta.jar
WEB-INF/lib/google-oauth-client-1.8.0-beta.jar
WEB-INF/lib/guava-11.0.1.jar
WEB-INF/lib/js-bigquery-datasource-0.0.4.jar
WEB-INF/lib/jsr305-1.3.9.jar
WEB-INF/lib/protobuf-java-2.3.0.jar
WEB-INF/lib/xpp3-1.1.4c.jar
```

Copy these files into your JasperReports Server instance. Be sure to keep the folder structure when copying the files. Add your private key (.p12 file) to the classes directory.

b) Start or restart JasperReports Server.

## **2. Define the Data Source** ##

a) Define a Big Query data source in JasperReports Server

b) Right-click on a folder and select 'Add Resource' to add Data Source

![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/JRS_AddResource.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/JRS_AddResource.png)

c) Set the Data Source properties.

![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/JRS_BigQueryDataSource.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/JRS_BigQueryDataSource.png)

## **3. Deploy a report to JasperReports Server** ##

Deploy the report from iReport using the built-in connection to browse the JasperReports Server Repository. Alternatively, you can use the web interface in JasperReports Server to deploy a report. In most cases it's quicker to deploy from iReport.