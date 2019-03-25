package com.example.android.publiclibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HOME extends AppCompatActivity {

    private Button Login;
    private Button Help;
    private Button Close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Login=(Button) findViewById(R.id.btn_Login);
        Help=(Button) findViewById(R.id.btn_Help);
        Close=(Button) findViewById(R.id.btn_Close);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPg();
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpPg();
            }
        });

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClosePg();
            }
        });
    }

    protected void LoginPg()
    {
        Toast.makeText(getApplicationContext(),"Loading LOGIN", Toast.LENGTH_LONG).show();
        Intent startNewActivity= new Intent(HOME.this, MainActivity.class);
        startActivity(startNewActivity);
    }

    protected void HelpPg()
    {
        Toast.makeText(getApplicationContext(),"Loading HELP", Toast.LENGTH_LONG).show();
        Intent startNewActivity= new Intent(HOME.this, HELP.class);
        startActivity(startNewActivity);
    }

    protected void ClosePg()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(HOME.this);
        builder.setMessage("Do you want to EXIT? ");
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
              finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
