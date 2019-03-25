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

public class Admin extends AppCompatActivity {

    private Button LibruaryADD, FindBook, CategoryInfo, ShelfInfo, Logout, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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

        LibruaryADD =(Button) findViewById(R.id.btn_Add);
        FindBook =(Button) findViewById(R.id.btn_FindBook);
        CategoryInfo =(Button) findViewById(R.id.btn_CategoryInfo);
        ShelfInfo =(Button) findViewById(R.id.btn_ShelfInfo);
        Logout =(Button) findViewById(R.id.btn_logout);
        Exit =(Button) findViewById(R.id.btn_exit);

        LibruaryADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(getApplicationContext(),"Library Book Information", Toast.LENGTH_LONG).show();
              Intent a = new Intent(Admin.this, LibraryBookInformation.class);
                startActivity(a);
            }
         });

        FindBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Find Book", Toast.LENGTH_LONG).show();
                Intent b = new Intent(Admin.this, FindBookInformation.class);
                startActivity(b);
            }
        });

        CategoryInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Category Information", Toast.LENGTH_LONG).show();
                Intent c = new Intent(Admin.this, CategoryInformation.class);
                startActivity(c);
            }
        });

        ShelfInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Shelf Information", Toast.LENGTH_LONG).show();
                Intent d = new Intent(Admin.this, ShelfInformation.class);
                startActivity(d);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Successfully Logged-out", Toast.LENGTH_LONG).show();
                Intent e = new Intent(Admin.this, MainActivity.class);
                startActivity(e);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClosePg();
            }
        });
    }

    private void ClosePg()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
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
