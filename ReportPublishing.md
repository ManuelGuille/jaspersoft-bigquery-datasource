# Summary #
**Publish a Report in JasperReports Server**

**1. Publish a Report in JasperReports Server**

# Details #

## **Publish a Report in JasperReports Server** ##

**Option 1: Deploy report using JasperReports Server web interface**

Note: You will need the .jxml file for the report.

1. Login as a admin

2. Right click on the Data Sources under Repository->Reports

3. Select Add Resource->JasperReport

https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/JRS_ReportDeploy.PNG

4. Using the wizard, set up the required and other supported resources for the report.

https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/JRS_ReportDeployment.PNG

5. Set the data source to point BigQuery data connection if you have already created one.If not, create a new BigQuery connection.

https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/JRS_ReportDeployment_DS.PNG

Note:
1. The private key file path property points to a reference location local to the Jaspersoft Server.If running on a distributed setup make sure to have a local copy of the private key file on the Jaspersoft Server location and reference it with an absolute path.

**Option 2: Deploy report to JasperReports Server from iReport**

1. Open JasperReports Server Repository Window

2. Create a new server.

3. Set the configuration properties for the server. Default organization name is organization\_1

Note: You can skip steps 2 and 3 If you have already configured a server.

4.  Include the report to be deployed and click on publish.