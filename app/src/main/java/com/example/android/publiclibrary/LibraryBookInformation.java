package com.example.android.publiclibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LibraryBookInformation extends AppCompatActivity {
    private EditText BookID,BookName,Author,Price,ShelfNo;
    private Spinner Category;
    private Button ButtonAdd;
    private Button ButtonView;
    private Button ButtonDelete;
    private Button ButtonUpdate;
    private SQLiteDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_book_information);
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
        BookID   =  (EditText) findViewById(R.id.txt_BOOKID);
        BookName =  (EditText) findViewById(R.id.txt_BOOKNAME);
        Author   =  (EditText) findViewById(R.id.txt_AUTHOR);
        Price    =  (EditText) findViewById(R.id.txt_PRICE);
        ShelfNo  =  (EditText) findViewById(R.id.txt_SHELFNO);
        Category=(Spinner) findViewById(R.id.spinner2);
        ButtonAdd    = (Button) findViewById(R.id.btn_ADD);
        ButtonView   = (Button) findViewById(R.id.btn_SEARCH) ;
        ButtonDelete = (Button) findViewById(R.id.btn_DELETE);
        ButtonUpdate = (Button) findViewById(R.id.btn_UPDATE);

        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDb();
            }
        });

        ButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewData();
            }
        });

        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
            }
        });

        ButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        Category.setAdapter(adapter);
    }


    protected void CreateDatabase()
    {
        DB=openOrCreateDatabase("Library__System", Context.MODE_PRIVATE,null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS LibraryBook_Details(BookID VARCHAR PRIMARY KEY NOT NULL,BookName VARCHAR,Category VARCHAR, Author VARCHAR, Price VARCHAR ,ShelfNo VARCHAR);");
    }

    protected void insertIntoDb()
    {
        String ID=BookID.getText().toString().trim();
        String NAME=BookName.getText().toString().trim();
        String CATEGORY=Category.getSelectedItem().toString().trim();
        String AUTHOR=Author.getText().toString().trim();
        String PRICE=Price.getText().toString().trim();
        String SHELF_NO =ShelfNo.getText().toString().trim();

        if (ID.isEmpty() || NAME.isEmpty() || AUTHOR.isEmpty() || PRICE.isEmpty() || SHELF_NO.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill all the fields above!! ",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String query = "Insert into LibraryBook_Details(BookID,BookName,Category,Author,Price,ShelfNo)values('" + ID + "','" + NAME +"','" + Category.getSelectedItem().toString() +"','" + AUTHOR +"','" + PRICE +"','" + SHELF_NO +"');";
            DB.execSQL(query);
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
            ClearFiled();
        }
    }

    private void ClearFiled()
    {
        BookID.setText(null);
        BookName.setText(null);
        Author.setText(null);
        Price.setText(null);
        ShelfNo.setText(null);
    }

    protected void ViewData()
    {
        boolean Notvalid = false;
        String ID = BookID.getText().toString().trim();
        String CATEGORY=Category.getSelectedItem().toString().trim();
        if(ID.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter the BookID to Search",Toast.LENGTH_LONG).show();
            ClearFiled();
        }

        else if (!Notvalid)
        {
            String query = "Select * from LibraryBook_Details where BookID ='" + ID + "';";
            Cursor cursorResult = DB.rawQuery(query, new String[]{});
            if(cursorResult.moveToFirst())
            {
                do{
                    String add1 = cursorResult.getString(1);
                    BookName.setText(add1);
                    String add2 = cursorResult.getString(2);
                    Category.setSelection(((ArrayAdapter<String>)Category.getAdapter()).getPosition(add2));
                    String add3 = cursorResult.getString(3);
                    Author.setText(add3);
                    String add4 = cursorResult.getString(4);
                    Price.setText(add4);
                    String add5 = cursorResult.getString(5);
                    ShelfNo.setText(add5);

                    Toast.makeText(getApplicationContext(), "Book Information Found", Toast.LENGTH_SHORT).show();
                }while (cursorResult.moveToNext());
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please enter the the correct BookID to Search",Toast.LENGTH_LONG).show();
                ClearFiled();
            }
        }
    }

    protected void DeleteData()
    {
        String ID=BookID.getText().toString().trim();
        if(ID.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter the BookID to Delete the data",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String query = "delete from LibraryBook_Details where BookID = '" + ID + "';";
            DB.execSQL(query);
            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
            ClearFiled();
        }
    }

    protected void UpdateData()
    {
        String ID=BookID.getText().toString().trim();
        String NAME=BookName.getText().toString().trim();
        String CATEGORY=Category.getSelectedItem().toString().trim();
        String AUTHOR=Author.getText().toString().trim();
        String PRICE=Price.getText().toString().trim();
        String SHELF_NO =ShelfNo.getText().toString().trim();

        if (ID.isEmpty() || NAME.isEmpty() || AUTHOR.isEmpty() || PRICE.isEmpty() || SHELF_NO.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill all the fields with the correct ID to UPDATE ",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String query = "Update LibraryBook_Details set BookName = '" + NAME + "', Category = '" + Category.getSelectedItem().toString() + "', Author = '" + AUTHOR + "', Price = '" + PRICE + "', ShelfNo = '" + SHELF_NO + "' where BookID = '" + ID + "';";
            DB.execSQL(query);

            Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
            ClearFiled();
        }
    }


    /*public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sSelected=parent.getItemAtPosition(position).toString();
        Toast.makeText(this,sSelected, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }*/

}

