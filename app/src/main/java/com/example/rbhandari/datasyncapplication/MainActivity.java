package com.example.rbhandari.datasyncapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rbhandari.datasyncapplication.datahandler.AuditHandler;
import com.example.rbhandari.datasyncapplication.datahandler.FeatureHandler;
import com.example.rbhandari.datasyncapplication.datahandler.TypeHandler;
import com.example.rbhandari.datasyncapplication.datahandler.UserHandler;
import com.example.rbhandari.datasyncapplication.datahandler.ZoneHandler;
import com.example.rbhandari.datasyncapplication.datamodels.Audit;
import com.example.rbhandari.datasyncapplication.datamodels.Feature;
import com.example.rbhandari.datasyncapplication.datamodels.TypeClass;
import com.example.rbhandari.datasyncapplication.datamodels.User;
import com.example.rbhandari.datasyncapplication.datamodels.Zone;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserHandler userHandler = new UserHandler(
                "roshanbhandari",
                "password1234",
                "brishi98@gmail.com",
                "roshan",
                "bhandari",
                "9808166147"
        );
        userHandler.createLocalUser();
        userHandler.createUserToParse();

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

        SyncHandler syncHandler = new SyncHandler();
        syncHandler.setAction("createbackuptoparse");
        syncHandler.execute();


        //        UserHandler.syncUserFromParse("roshanbhandari");
        SyncHandler syncHandler1 = new SyncHandler();
        syncHandler1.setAction("createbackfromparse");
        syncHandler1.execute();

    }
}
