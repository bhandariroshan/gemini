# Gemini Data Sync Application #

This app helps sync data to and from Android Local Database to Parser Server.

## Architecture ##
- datamodels: Defines data models using sugarorm
  - audit: model for audit table
  - feature: model for feature table
  - typeclass: model for typeclass table
  - user: model for user
  - zone: model for zone

 - datahandler: handler for models, has different logics on top of corresponding data models
   - audithandler: handler for audit model
   - featurehandler: handler for audit model
   - typeclasshandler: handler for typeclass model
   - userhandler: handler for user model
   - zonehandler: handler for zone model

- requesthandler
   - audithandler: handler for audit model

## Using the App ##

We can directly use Synchandler class to sync data to and fro between android device and parse server.
Following methods are available in the SyncHandler class:


- createBackupToParse: This method saves all the newly created data in feature, type, audit and zone model to the parse server

  from com.example.rbhandari.datasyncapplication import SyncHandler
  
  SyncHandler.createBackupToParse()

- loadBackupFromParse: This method can be used to created data in feature, type, audit and zone model from the data in parse server

  from com.example.rbhandari.datasyncapplication import SyncHandler
  
  SyncHandler.loadBackupFromParse()


- saveAllUpdatesToParse: This method can be used to save changes in feature, type, audit and zone model from the android database to the data in parse server

  from com.example.rbhandari.datasyncapplication import SyncHandler
  
  SyncHandler.saveAllUpdatesToParse()


## Prerequisites to use the SyncHandler Class ##
- Every audits, features, zones and types are linked to user, so please make sure user is created and associated with audit model
- After every update, please make sure the isUpdated flag is turned on, so that updates can be synced to the server

