package com.sunjays.sqlcipher;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName, etPages;
    private Button btnInsert, btnQuery;
    private TextView tv;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase.loadLibs(this);
        setViews();

        MyDatabaseHelper helper = new MyDatabaseHelper(getApplicationContext(), "demo.db", null, 1);
        database = helper.getWritableDatabase("password");

        btnInsert.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    private void setViews() {
        etName = (EditText) findViewById(R.id.et_name);
        etPages = (EditText) findViewById(R.id.et_pages);
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnQuery = (Button) findViewById(R.id.btn_query);
        tv = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insertData();
                break;
            case R.id.btn_query:
                queryData();
                break;
        }
    }

    private void queryData() {
        Cursor cursor = database.query("Book", null, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                Log.d("TAG", name+pages);
                tv.append("name:"+name+","+pages);
            }
        }
        cursor.close();
    }

    private void insertData() {
        ContentValues values = new ContentValues();
        values.put("name", "第一行代码");
        values.put("pages", 666);
        database.insert("Book", null, values);
    }
}
