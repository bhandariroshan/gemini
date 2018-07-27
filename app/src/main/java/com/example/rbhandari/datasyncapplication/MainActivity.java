package com.example.rbhandari.datasyncapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rbhandari.datasyncapplication.datahandler.FeatureHandler;
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
//        AuditHandler.syncAllUserAuditsFromParse("roshanbhandari"); // correct
//        ZoneHandler.syncAllZonesFromParse("roshanbhandari"); // correct
//        TypeHandler.syncAllTypesFromParse("roshanbhandari"); //correct
        FeatureHandler.syncAllFeaturesFromParse("roshanbhandari");
    }
}
