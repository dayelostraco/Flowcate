# Introduction
Notiflowcate µservice API for tracking Geofence, Beacon an Topic Notifications and storing them in a MySQL database.

## Abstract

This API Key based Notiflowcation µservice API can be used to send notifications via:

* Firebase Cloud Messaing Push Notifications
* Firebase Cloud Messaing Topic Notifications
* AWS Simple Email Service emails
* Twilio SMS Messages
* Twilio MMS Messages

In addition to basic messaging, the API is designed to allow users to create custom Triggers and Notifications based on registered Events.

### Event
A generic and extensible model that represents a time-based campaign. You can also specify how the Event is triggered based on whether the user entered/exited/loitered in a location.
For ex: A 50% of sale that is active between 2016-05-02 through 2016-06-30.

### Trigger
A generic and extensible model that represents a user event based on their location. This model contains a system's userId record, their device, their location, whether they entered/exited/loitered in a location and the UTC server time of their request.

### Notification
A generic and extensible model that represents the Notification that will be sent to a user. This Notification can be a Device specific PushNotification or a Topic based PushNotification. This Model is what will be pushed to a mobile application to be processed.

## Deploying the Notification µservice API

In order to facilitate deployments to AWS ElasticBeanstalk, there is an included .ebextensions folder that configures nginx and a Dockerrun.aws.json file to alert ElasticBeanstalk to configure the exposed Docker port.

1. From the command line, navigate to the project folder.

2. Run mvn clean package

3. Login into the desired AWS Account and navigate to the Elastic Beanstalk application. Select the applicable Docker based application.

4. Click on the "Upload and Deploy" button. Click "Choose File" option and select the ZIP file created on Step 3 that is located in the /target folder.

5. Select the "Fixed" deployment limit option and enter in 1 as the option. This will deploy the latest version of the application to one instance at a time to maintain the uptime of the app.

## Sample API Calls

### Register an Application

POST /api/1/application
```json
{
  "applicationName": "Your Application Name",
  "fcmApiKey": "Your Firebase API Key",
  "fcmSenderId": 100000,
  "sesAccessKey": "Your Amazon SES Access Key",
  "sesSecretKey": "Your Amazon SES Secret Key",
  "twilioAccountSid": "Twilio Account SID",
  "twilioMmsPhone": "Twilio MMS Phone Number",
  "twilioPrimaryToken": "Twilio Primary Token",
  "twilioSecondaryToken": "Twilio Secondary Token",
  "twilioSmsPhone": "Twilio MMS Phone Number",
  "twilioTestAccountSid": "Twilio Test Account SID",
  "twilioTestToken": "Twilio Test Token",
  "twilioVoicePhone": "Twilio Voice Phone Number",
  "active": true
}
```

### Register an Beacon

POST /api/1/beacon
API Key - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH

```json
{
  "active": true,
  "beaconMajor": 1,
  "beaconMinor": 1,
  "beaconUUID": "3E035B8E-EF0F-E877-5799-4D071D9C3E65",
  "campaignId": "Your application campaignId",
  "description": "Description of Beacon",
  "latitude": 0,
  "listingId": "Your application listing id",
  "longitude": 0,
  "name": "Beacon Name"
}
```

### Register an Geofence

POST /api/1/geofence
API Key - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH

```json
{
  "active": true,
  "campaignId": "Your application campaignId",
  "description": "Description",
  "expiration": "2017-05-28T20:51:40.530Z",
  "latitude": 0,
  "listingId": "string",
  "longitude": 0,
  "name": "Geofence Name",
  "radiusInMeters": 150
}
```

### Register an Topic

POST /api/1/topic
API Key - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH

```json
{
  "active": true,
  "topicName": "topic-name"
}
```

### Register a Beacon Based Event

POST /api/1/notificationEvent
API Key - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH

```json
{
  "active": true,
  "endDate": "2016-06-30T01:27:39.748Z",
  "enter": true,
  "eventDescription": "Event Description",
  "eventName": "I'm an awesome event. Please come see me.",
  "eventNotificationText": "Push notification will display me",
  "exit": false,
  "startDate": "2016-06-10T01:27:39.748Z",
  "notificationEventType": "beacon",
  "beaconId": 3
}
```

### Register a Geofence Based Event

POST /api/1/notificationEvent
API Key - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH

```json
{
  "active": true,
  "endDate": "2016-06-30T01:27:39.748Z",
  "enter": true,
  "eventDescription": "Event Description",
  "eventName": "I'm an awesome event. Please come see me.",
  "eventNotificationText": "Push notification will display me",
  "exit": false,
  "dwell": false,
  "startDate": "2016-06-10T01:27:39.748Z",
  "notificationEventType": "geofence",
  "geofenceId": 2
}
```

### Trigger a Beacon

POST /api/1/trigger
API Key - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH

```json
{
  "deviceToken": "dgGIySXHs-I:APA91bG-h3eIRrZ5bRkrMCC1-3bM3cd58Vj9CCtAAm5ZqqxOiq3J21n0UfDC9rNOyJZBv00ZTFULMAZSl-A1E9c-5j5zFDiQgveE7DQb6UtVzDUBUIXEP4W5T4J_BOVkiByojg2ubRI1",
  "userId": "1234",
  "triggerType": "beacon",
  "beaconUUID": "B9407F30-F5F8-466E-AFF9-25556B57FE6D",
  "beaconMajor": 38141,
  "beaconMinor": 50242,
  "onEnter": false,
  "onExit": true
}
```

### Trigger a Geofence

POST /api/1/trigger
API KEY - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH
```json
{
  "deviceToken": "dgGIySXHs-I:APA91bG-h3eIRrZ5bRkrMCC1-3bM3cd58Vj9CCtAAm5ZqqxOiq3J21n0UfDC9rNOyJZBv00ZTFULMAZSl-A1E9c-5j5zFDiQgveE7DQb6UtVzDUBUIXEP4W5T4J_BOVkiByojg2ubRI1",
  "enteredField": "2016-05-28T17:47:01.999Z",
  "userId": "1234",
  "triggerType": "geofence",
  "geofenceId": 1,
  "onEnter": false,
  "onExit": false,
  "onDwell": true
}
```

### Send a Topic Message

POST /api/1/notification
API KEY - ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH
```json
{
  "message": "string",
  "notificationType": "topic",
  "topicName": "testtopic"
}
```