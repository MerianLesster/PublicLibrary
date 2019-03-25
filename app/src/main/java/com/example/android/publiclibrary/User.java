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

public class User extends AppCompatActivity {

    private Button LibruaryADD, FindBook, Logout, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
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

        LibruaryADD =(Button) findViewById(R.id.btn_AddBorrowerInfo);
        FindBook = (Button) findViewById(R.id.btn_F_Book);
        Logout = (Button) findViewById(R.id.btn_LOGout);
        Exit = (Button) findViewById(R.id.btn_EXit);

        LibruaryADD.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           Toast.makeText(getApplicationContext(),"Book Borrowed Information", Toast.LENGTH_LONG).show();
           Intent abc= new Intent(User.this, BookBorrowedInfomation.class);
           startActivity(abc);
           }
         });

        FindBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Book Borrowed Information", Toast.LENGTH_LONG).show();
                Intent xyz = new Intent(User.this, FindBookInformation.class);
                startActivity(xyz);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Successfully Logged-out", Toast.LENGTH_LONG).show();
                Intent asd = new Intent(User.this, MainActivity.class);
                startActivity(asd);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
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
