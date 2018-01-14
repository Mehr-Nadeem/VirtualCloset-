package com.example.mehr.virtualcloset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class SavePhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_photo);

        Toolbar saveToolbar = (Toolbar) findViewById(R.id.save_toolbar);
        setSupportActionBar(saveToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_actions,menu);
        return true;

    }


}
