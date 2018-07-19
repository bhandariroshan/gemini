package com.example.rbhandari.datasyncapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rbhandari.datasyncapplication.datahandler.TypeHandler;
import com.example.rbhandari.datasyncapplication.datahandler.ZoneHandler;
import com.example.rbhandari.datasyncapplication.datamodels.User;
import com.example.rbhandari.datasyncapplication.datahandler.AuditHandler;
import com.example.rbhandari.datasyncapplication.datahandler.UserHandler;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserHandler userHandler = new UserHandler("roshan", "123", "brishi98@gmail.com",
                "Roshan", "Bhandari", "");
        userHandler.createLocalUser();
        //        userHandler.createUserParse();
        //        userHandler.getUserFromParse("mPZ2OZ8gMb");

        //        UserHandler.createAllLocalUsersToParse();
        try{
            /*User user = (User) UserHandler.getUserByUsername("roshan").get(0);
            Date date = new Date();
            Long data = new Long(0);
            //            AuditHandler auditHandler = new AuditHandler(user, data, "", "Audit 1",date, date);
            //            auditHandler.createLocalAudit();
            AuditHandler.createAllLocalAuditToParse();*/

            //            ZoneHandler zoneHandler = new ZoneHandler("1","","Zone 1","", new Long(1), new Date(), new Date());
            //            zoneHandler.createLocalZone();
            //            zoneHandler.createZoneParse();
            //            ZoneHandler.saveAllLocalZonesToParse();

            TypeHandler typeHandler = new TypeHandler(
                    "",
                    "",
                    Long.valueOf(1),
                    Long.valueOf(1),
                    "Type 1",
                    "HVAC",
                    new Date(),
                    new Date()
            );
            typeHandler.createLocalType();
            //            typeHandler.createTypeParse();
            TypeHandler.saveAllLocalTypesToParse();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
