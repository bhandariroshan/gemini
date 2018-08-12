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

- requesthandler: communicates to and from from android to parse server
   - requesthandler: creates get, put, post, delete request to provided url
   - requesthandler : creates data and url for required behaviour and triggers requesthandler for required action
   - responsehandler: all response from parse server is landed here, we can control required behavior of response here and do necessary actions such as update local database based on response of server

## Using the App ##

We can directly use Synchandler class to sync data to and fro between android device and parse server.
Following methods are available in the SyncHandler class:

- Creating User using UserHandler in Android

  ``` 

  import com.example.rbhandari.datasyncapplication.datahandler.UserHandler
  UserHandler userHandler = new UserHandler(
      "roshanbhandari",
      "password1234",
      "brishi98@gmail.com",
      "roshan",
      "bhandari",
      "9808166147"
  );
  userHandler.createLocalUser();

  ```
 
- Saving Locally created user to Parse

  ``` 

    import com.example.rbhandari.datasyncapplication.datahandler.UserHandler
    userHandler.createUserToParse();

  ``` 

- Creating Audit Locally
 
  ``` 
    import com.example.rbhandari.datasyncapplication.datahandler.AuditHandler
    AuditHandler auditHandler = new AuditHandler(
      "roshanbhandari",
      Long.valueOf(1),
      "",
      "Audit ****",
      new Date(),
      new Date(),
      false
    );
    auditHandler.createLocalAudit();

  ```
  
- Creating Zone Locally
  
  ``` 
    import com.example.rbhandari.datasyncapplication.datahandler.ZoneHandler
    ZoneHandler zoneHandler = new ZoneHandler(
          "",
          "roshanbhandari",
          "",
          "zone 1",
          "",
          Long.valueOf(1),
          new Date(),
          new Date(),
          false
    );
    zoneHandler.createLocalZone();

  ```
 
- Creating Type Locally
  
  ``` 
    import com.example.rbhandari.datasyncapplication.datahandler.TypeHandler
    TypeHandler typeHandler = new TypeHandler(
            "roshanbhandari",
            "",
            "",
            Long.valueOf(1),
            Long.valueOf(1),
            "name",
            "subtype",
            new Date(),
            new Date(),
            false
    );
    typeHandler.createTypeAtLocal();

  ```
 
- Creating Feature Locally
  
  ```
  
    import com.example.rbhandari.datasyncapplication.datahandler.FeatureHandler
    FeatureHandler featureHandler = new FeatureHandler(
        "roshanbhandari",
        "",
        "",
        Long.valueOf(1),
        Long.valueOf(1),
        Long.valueOf(1),
        Long.valueOf(1),
        "roshan",
        "Integer",
        "Price",
        "",
        5,
        Double.valueOf("1"),
        new Date(),
        new Date(),
        false
    );
    featureHandler.createFeatureAtLocal();
    
  ```
 
- createBackupToParse: This method saves all the newly created data in feature, type, audit and zone model to the parse server

  ``` 

    import com.example.rbhandari.datasyncapplication.SyncHandler
    SyncHandler syncHandler = new SyncHandler();
    syncHandler.setAction("createbackuptoparse");
    syncHandler.execute();

  ``` 

- loadBackupFromParse: This method can be used to created data in feature, type, audit and zone model from the data in parse server

  ``` 
    import com.example.rbhandari.datasyncapplication.SyncHandler
    SyncHandler syncHandler = new SyncHandler();
    syncHandler.setAction("createbackupfromparse");
    syncHandler.execute();

  ```


- saveAllUpdatesToParse: This method can be used to save changes in feature, type, audit and zone model from the android database to the data in parse server

  ``` 

    import com.example.rbhandari.datasyncapplication.SyncHandler
    SyncHandler syncHandler = new SyncHandler();
    syncHandler.setAction("backupallupdatestoparse");
    syncHandler.execute();

  ```

## Prerequisites to use the SyncHandler Class ##
- Every audits, features, zones and types are linked to user, so please make sure user is created and associated with audit model
- After every update, please make sure the isUpdated flag is turned on, so that updates can be synced to the server.

