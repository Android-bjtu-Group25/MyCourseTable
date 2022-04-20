package com.example.mycoursetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class setbase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setbase);

        EditText e1=findViewById(R.id.month);
        EditText e2=findViewById(R.id.day);


        Button b2=findViewById(R.id.sure);
        Button b1=findViewById(R.id.return_to_main);


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(setbase.this, MainActivity.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month=e1.getText().toString();
                String day=e2.getText().toString();
                if(isValidDate(month+"/"+day+"/"+year))//日期真实存在
                {

                    String arrs[]=new String[2];
                    arrs[0]=month+"";
                    arrs[1]=day+"";
                    writefile(arrs);
                }
                //没有直接跳，有了设置完了在跳。
                startActivity(new Intent(setbase.this, MainActivity.class));
            }
        });
    }


    private static boolean isValidDate(String input) {
        String formatString = "MM/dd/yyyy";

        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);

            format.setLenient(false);

            format.parse(input);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;

        }

        return true;

    }

    public void writefile( String[] arrs) {
        String filename="base";
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            String string;
            for(String arr:arrs)
            {
                if(arr==null)
                    arr="";
                string=arr+"\n";
                outputStream.write(string.getBytes());
            }
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}