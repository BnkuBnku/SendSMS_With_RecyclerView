package com.example.sendsms_with_recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    EditText numberPhone;
    EditText textMsg;
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recyclerView);
        numberPhone = findViewById(R.id.editTextPhone);
        textMsg = findViewById(R.id.editTextTextMultiLine);
        buttonSend = findViewById(R.id.button);



        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, 2);
                }
                else{
                    Toast.makeText(MainActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();
                    textMsg.setText("");
                    sendMessage();
                }
            }
        });

        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_CONTACTS}, 1);
        }
        else{
            getNumber();
        }

    }

    private void sendMessage() {
        String num = numberPhone.getText().toString();

        SmsManager smManager = SmsManager.getDefault();
        smManager.sendTextMessage(num, null, textMsg.getText().toString(), null, null);
    }

    private void getNumber() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        ArrayList<String> nums = new ArrayList<>();
        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int numIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            if(nameIndex >= 0 && numIndex >=0){
                String name = cursor.getString(nameIndex);
                String num = cursor.getString(numIndex);
                nums.add(name + " - " + num);
            }
        }

        //Store the Value and Pass the Value to Adapter
        String[] conNum = new String[nums.size()];
        for(int i = 0; i < nums.size(); i++){
            conNum[i] = nums.get(i);
        }

        LinearLayoutManager layout = new LinearLayoutManager(this);
        recycler.setLayoutManager(layout);
        recycler.setAdapter(new ContactAdapter(this, conNum));
        //
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getNumber();
        }
        else if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendMessage();
        }
    }
}