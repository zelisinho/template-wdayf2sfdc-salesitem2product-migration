
# Anypoint Template: Workday to Salesforce Sales Item to Product Migration

<!-- Header (start) -->
Moves a large set of sales items from Workday to Salesforce. You can trigger this manually or programmatically with an HTTP call.

Sales items are upserted so that the migration can be run multiple times without creating duplicate records. This template uses batch to efficiently process many records at a time.

![28ae47e4-292f-4336-b06b-c335c49ab754-image.png](https://exchange2-file-upload-service-kprod.s3.us-east-1.amazonaws.com:443/28ae47e4-292f-4336-b06b-c335c49ab754-image.png)

## Workday Requirements

Install Workday Financials - Revenue Management module which you can find on the [Workday connector page](https://www.mulesoft.com/exchange/com.mulesoft.connectors/mule-workday-connector/).

<!-- Header (end) -->

# License Agreement
This template is subject to the conditions of the <a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>. Review the terms of the license before downloading and using this template. You can use this template for free with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.
# Use Case
<!-- Use Case (start) -->
As a Workday admin I want to migrate Sales Items to Salesforce Products.

This template serves as a foundation for the process of migrating sales items from Workday. As implemented, this template leverages the Mule batch module. The batch job is divided into *Process* and *On Complete* stages. Migration process starts by querying all existing sales items at Workday. In the *Process* stage, the template queries the Salesforce for already existing products based on the product names retrieved from Workday. The products are inserted or updated in the Salesforce system based on the results of these queries. Afterwards, the Salesforce system is queried for a pricebook entry, which belongs to the product. If a pricebook entry is found, it is updated, otherwise the pricebook entry for the product is created. Finally during the *On Complete* stage the template outputs statistics data in the console and sends a notification email with the results of the batch execution.
<!-- Use Case (end) -->

# Considerations
<!-- Default Considerations (start) -->

<!-- Default Considerations (end) -->

<!-- Considerations (start) -->
There are certain pre-requisites that must be considered to run this template. All of them deal with the preparations in both source and destination systems, that must be made for the template to run smoothly. Failing to do so can lead to unexpected behavior of the template.
<!-- Considerations (end) -->

## Salesforce Considerations

- Where can I check that the field configuration for my Salesforce instance is the right one? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=checking_field_accessibility_for_a_particular_field.htm&language=en_US">Salesforce: Checking Field Accessibility for a Particular Field</a>.
- How can I modify the Field Access Settings? See: [Salesforce: Modifying Field Access Settings](https://help.salesforce.com/HTViewHelpDoc?id=modifying_field_access_settings.htm&language=en_US "Salesforce: Modifying Field Access Settings").


### As a Data Destination

There are no considerations with using Salesforce as a data destination.

## Workday Considerations

### As a Data Source

There are no considerations with using Workday as a data origin.

## Workday Financials Considerations

### As a Data Source

There are no considerations with using Workday Financials as a data origin.

# Run it!
Simple steps to get this template running.
<!-- Run it (start) -->

<!-- Run it (end) -->

## Run On Premises
In this section we help you run this template on your computer.
<!-- Running on premise (start) -->

<!-- Running on premise (end) -->

### Download Anypoint Studio and the Mule Runtime
If you are new to Mule, download this software:

- [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
- [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)

**Note:** Anypoint Studio requires JDK 8.
<!-- Where to download (start) -->

<!-- Where to download (end) -->

### Import a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your Anypoint Platform credentials, search for the template, and click Open.
<!-- Importing into Studio (start) -->

<!-- Importing into Studio (end) -->

### Run on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

1. Locate the properties file `mule.dev.properties`, in src/main/resources.
2. Complete all the properties required per the examples in the "Properties to Configure" section.
3. Right click the template project folder.
4. Hover your mouse over `Run as`.
5. Click `Mule Application (configure)`.
6. Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`.
7. Click `Run`.

<!-- Running on Studio (start) -->

<!-- Running on Studio (end) -->

### Run on Mule Standalone
Update the properties in one of the property files, for example in mule.prod.properties, and run your app with a corresponding environment variable. In this example, use `mule.env=prod`.
After this, to trigger the use case, browse to the local HTTP connector with the port you configured in your file. If this is, for instance `9090`, browse to: `http://localhost:9090/migratesalesitems` and this outputs a summary report and sends it in email.

## Run on CloudHub
When creating your application in CloudHub, go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the mule.env value.
<!-- Running on Cloudhub (start) -->
Once your app is all set and started, if you choose as domain name `wdayfsalesitemsmigration` to trigger the use case you just need to browse to `http://wdayfsalesitemsmigration.cloudhub.io/migratesalesitems` and report is sent to the emails configured.
<!-- Running on Cloudhub (end) -->

### Deploy a Template in CloudHub
In Studio, right click your project name in Package Explorer and select Anypoint Platform > Deploy on CloudHub.
<!-- Deploying on Cloudhub (start) -->

<!-- Deploying on Cloudhub (end) -->

## Properties to Configure
To use this template, configure properties such as credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.

<!-- Application Configuration (start) -->
### Application Configuration

- http.port `9090`

### Salesforce Connector Configuration

- sfdc.username `bob.dylan@orga`
- sfdc.password `DylanPassword123`
- sfdc.securityToken `avsfwCUl7apQs56Xq2AKi3X`
- sfdc.integration.pricebook2Id `standard_pricebook2_id`

### Workday Connector Configuration

- wday.user `wdayf_user`
- wday.password `wdayf_password`
- wday.tenant `wday_tenant`
- wday.host `wday_host`
- wday.responseTimeout `20000`

### SMTP Services Configuration

- smtp.host `smtp.gmail.com`
- smtp.port `587`
- smtp.user `gmail_user`
- smtp.password `gmail_password`

### Email Details

- mail.from `batch.migratesalesitems.migration%40mulesoft.com`
- mail.to `username@gmail.com`
- mail.subject `Batch Job Finished Report`
<!-- Application Configuration (end) -->

# API Calls
<!-- API Calls (start) -->
Salesforce imposes limits on the number of API calls that can be made. Therefore calculating this amount may be an important factor to consider. The template calls to the API can be calculated using this formula:

- *** 4 * X  *** -- Where ***X*** is the number of sales items to synchronize on each run.

For instance if 10 records are fetched from origin instance, then 40 API calls are made (4 * 10).
<!-- API Calls (end) -->

# Customize It!
This brief guide provides a high level understanding of how this template is built and how you can change it according to your needs. As Mule applications are based on XML files, this page describes the XML files used with this template. More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

- config.xml
- businessLogic.xml
- endpoints.xml
- errorHandling.xml
<!-- Customize it (start) -->

<!-- Customize it (end) -->

## config.xml
<!-- Default Config XML (start) -->
This file provides the configuration for connectors and configuration properties. Only change this file to make core changes to the connector processing logic. Otherwise, all parameters that can be modified should instead be in a properties file, which is the recommended place to make changes.
<!-- Default Config XML (end) -->

<!-- Config XML (start) -->

<!-- Config XML (end) -->

## businessLogic.xml
<!-- Default Business Logic XML (start) -->
The functional aspect of this template is implemented in this XML file, directed by a flow responsible for executing the logic. For the purpose of this template the *mainFlow* just executes a batch job that handles all the migration logic.
<!-- Default Business Logic XML (end) -->

<!-- Business Logic XML (start) -->

<!-- Business Logic XML (end) -->

## endpoints.xml
<!-- Default Endpoints XML (start) -->
This is the file where you find the inbound side of your integration app. This template has only a HTTP Listener as the way to trigger the use case.

**HTTP Listener Connector** - Start Report Generation

- `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
- The path configured by default is `migratesalesitems` and you are free to change for the one you prefer.
- The host name for all endpoints in your CloudHub configuration should be defined as `0.0.0.0`. CloudHub routes requests from your application domain URL to the endpoint.
- The endpoint is configured as a *request-response* by placing it in the source section of the flow. The response is the total of sales items synced and filtered by the criteria specified.
<!-- Default Endpoints XML (end) -->

<!-- Endpoints XML (start) -->

<!-- Endpoints XML (end) -->

## errorHandling.xml
<!-- Default Error Handling XML (start) -->
This file handles how your integration reacts depending on the different exceptions. This file provides error handling that is referenced by the main flow in the business logic.
<!-- Default Error Handling XML (end) -->

<!-- Error Handling XML (start) -->

<!-- Error Handling XML (end) -->

<!-- Extras (start) -->

<!-- Extras (end) -->
