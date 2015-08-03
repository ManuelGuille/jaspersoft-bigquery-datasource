# Installing and configuring the Jaspersoft BigQuery Connector #

## Summary ##

  1. Activate the Google BigQuery Service
  1. Download Jaspersoft BigQuery connector
  1. Install Jaspersoft BigQuery Plugin for iReport
  1. Create a report in iReport
  1. Deploy Jaspersoft BigQuery connector to JasperReports Server
  1. Publish a Report

## Details ##

### 1. Activate the Google BigQuery Service ###
See [Activate Google BigQuery](ActivatingBigQueryService.md) for details.

Existing BigQuery users may skip the section 'Sign up for BigQuery Service' in the above link. But they are advised to refer to the section 'Generate API Access ID' for instructions on the properties needed for establishing a Jaspersoft-BigQuery Datasource connection.

### 2. Download Jaspersoft BigQuery connector ###
Download the following files from the [downloads section](http://code.google.com/p/jaspersoft-bigquery-datasource/downloads/list):

  * Jaspersoft-iReport-BigQueryPlugin-0.0.4.nbm
  * jrs-bigquery-0.0.4.zip

Download the [Jaspersoft Business Intelligence Suite](http://www.jaspersoft.com/thanks-download-30-day). This includes both JasperReports Server and iReport.

### 3. Install the Jaspersoft BigQuery Plugin for iReport ###
By default iReport can connect to relational databases via JDBC. It can connect to XML, JSON, XLS, Hadoop Hive, MongoDB, and many other sources. But it cannot connect to Google BigQuery. (Not yet!) But adding support for BigQuery takes only a few seconds.<br>
<b>See <a href='ConfiguringiReport.md'>Configure iReport</a> for complete details.</b>

<h3>4. Create a report in iReport</h3>
See <a href='ReportCreation.md'>Create a Report</a> for details.<br>
<br>
<h3>5. Deploy the Jaspersoft BigQuery connector to JasperReports Server</h3>
See <a href='ConfiguringJasperReportsServer.md'>Configure JasperReports Server</a> for details.<br>
<br>
<h3>6. Publish a Report</h3>
See <a href='ReportPublishing.md'>Publishing a Report</a> for details.