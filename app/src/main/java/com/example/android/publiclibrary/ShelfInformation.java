package com.example.android.publiclibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ShelfInformation extends AppCompatActivity {
    private EditText BookID,BookName,Author,Price,ShelfNo;
    private Spinner Category;
    private Button Find;
    private SQLiteDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_information);
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

        BookID   =  (EditText) findViewById(R.id.txt_SI_BookId);
        BookName =  (EditText) findViewById(R.id.txt_SI_BookName);
        Author   =  (EditText) findViewById(R.id.txt_SI_author);
        Price    =  (EditText) findViewById(R.id.txt_SI_price);
        ShelfNo  =  (EditText) findViewById(R.id.txt_SI_shelfNo);
        Category=(Spinner) findViewById(R.id.spinner5);
        Find    = (Button) findViewById(R.id.btn_FIND_shelf);

        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetInfo();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        Category.setAdapter(adapter);
    }

    private void ClearFiled()
    {
        BookID.setText(null);
        BookName.setText(null);
        Author.setText(null);
        Price.setText(null);
        ShelfNo.setText(null);
    }

    private void GetInfo()
    {
        boolean Notvalid = false;
        String SHELD_NO = ShelfNo.getText().toString().trim();
        String CATEGORY=Category.getSelectedItem().toString().trim();
        if(SHELD_NO.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter the ShelfNo to Search Shelf Information",Toast.LENGTH_LONG).show();
            ClearFiled();
        }

        else if (!Notvalid)
        {
            String query = "Select * from LibraryBook_Details where ShelfNo ='" + SHELD_NO + "';";
            Cursor cursorResult = DB.rawQuery(query, new String[]{});
            if(cursorResult.moveToFirst())
            {
                do{
                    String asd = cursorResult.getString(1);
                    BookID.setText(asd);
                    String q = cursorResult.getString(2);
                    BookName.setText(q);
                    String w = cursorResult.getString(3);
                    Category.setSelection(((ArrayAdapter<String>)Category.getAdapter()).getPosition(w));
                    String t = cursorResult.getString(4);
                    Author.setText(t);
                    String y = cursorResult.getString(5);
                    Price.setText(y);

                    Toast.makeText(getApplicationContext(), "Shelf Information Found", Toast.LENGTH_SHORT).show();
                }while (cursorResult.moveToNext());
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please enter the the correct ShelfNo to Search",Toast.LENGTH_LONG).show();
                ClearFiled();
            }
        }
    }

}
