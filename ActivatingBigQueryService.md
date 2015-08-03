# Activate BigQuery #

## Summary ##
  1. BigQuery Service Activation
  1. Generate an API Access ID

## Details ##
### 1. BigQuery Service Activation ###
  1. Log in to [Google APIs Console](https://code.google.com/apis/console).
  1. Create a new Google APIs Console project or use an existing project.
  1. Navigate to API Services table.
  1. Click on Services on the left-hand sidebar.
  1. Turn on BigQuery.
  1. If prompted, review and agree to Terms of Service.

![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/Activate_BigQuery.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/Activate_BigQuery.png)

That should be enough to get started. Refer to the [official sign up documentation](https://developers.google.com/bigquery/docs/getting-started) for comprehensive details.

### 2. Generating API Access ID ###
Google APIs use the OAuth 2.0 protocol for authentication and authorization. There are many possible scenarios for using OAuth including Login, Web Server Applications, Client-side Applications, Installed Applications, Devices, and Service Accounts. Refer to the [official OAuth2 documentation](https://developers.google.com/accounts/docs/OAuth2) for complete details.

The Jaspersoft-BigQuery connector uses the **Service Account** scenario. Therefore you must obtain a Client ID and a private key. The connector uses this information to authenticate with BigQuery. The steps are the following:

  1. Navigate to the API Access on the left-hand sidebar in the Google APIs Console.
  1. Create an OAuth Client ID.
  1. Download the private key.
  1. You need the following to use these credentials:
    * Private key
    * Email address
    * Project ID

![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/OAuth_ClientID_Create.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/OAuth_ClientID_Create.png)

You can find project ID in the url of the Google APIs Console:
![https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/OAuth_ClientID_Info.png](https://jaspersoft-bigquery-datasource.googlecode.com/svn/wiki/Resources/OAuth_ClientID_Info.png)

Refer to the [official sign up documentation](https://developers.google.com/bigquery/docs/getting-started) for comprehensive details.