package com.example.android.publiclibrary;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import android.text.InputType;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private Button Login;
    private TextView UserIDText, PasswordText;
    private CheckBox ShowPassword;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        CreateDatabase();
        Login=(Button) findViewById(R.id.btnLogin);
        UserIDText =(TextView) findViewById(R.id.UserIDTxt);
        PasswordText =(TextView) findViewById(R.id.PasswordTxt);
        ShowPassword = (CheckBox) findViewById(R.id.checkBox_password);

        ShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    PasswordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    PasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginIntoDB();
            }
        });

    }

    protected void  CreateDatabase()
    {
        db=openOrCreateDatabase("Library__System", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS UserLogin(UserID String PRIMARY KEY NOT NULL,Password VARCHAR, UserType VARCHAR);");

        String Query="  INSERT INTO UserLogin values ('A001','admin123','Administrator');";
        db.execSQL(Query);

        String Query1="  INSERT INTO UserLogin values ('M001','member123','Member');";
        db.execSQL(Query1);

    }

    protected void LoginIntoDB()
    {
        String UserID = UserIDText.getText().toString().trim();
        String Password = PasswordText.getText().toString().trim();

        String UserType;
        Boolean valid=true;
        if(UserID.isEmpty()&& Password.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill all the empty Fields", Toast.LENGTH_LONG).show();
            valid=false;
        }

        else if (UserID.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter your UserID", Toast.LENGTH_LONG).show();
            valid=false;
        }

       else if(Password.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter your Password", Toast.LENGTH_LONG).show();
            valid=false;
        }

     if(valid)
        {
            String Query = "Select * from UserLogin where UserID = '" + UserID +"' and Password = '" + Password+ "'; ";
            Cursor cursor = db.rawQuery(Query, new String[]{});

            if (cursor.moveToFirst())
            {
                do
                    {
                        UserType = cursor.getString(2).trim();

                    } while(cursor.moveToNext());

                    if(UserType.equals("Administrator"))
                    {
                        Toast.makeText(getApplicationContext(),"Welcome "+ UserType, Toast.LENGTH_LONG).show();
                        Intent startNewActivityAdmin= new Intent(MainActivity.this, Admin.class);
                        startActivity(startNewActivityAdmin);
                    }
                    else if (UserType.equals("Member"))
                    {
                        Toast.makeText(getApplicationContext(),"Welcome "+ UserType, Toast.LENGTH_LONG).show();
                        Intent startNewActivityAdmin= new Intent(MainActivity.this, User.class);
                        startActivity(startNewActivityAdmin);
                    }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Invalid UserID or Password", Toast.LENGTH_LONG).show();
            }
            UserIDText.setText(null);
            PasswordText.setText(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
