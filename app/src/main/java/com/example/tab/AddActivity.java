package com.example.tab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class AddActivity extends AppCompatActivity {
    ImageButton add_btn;
    String number, name;
    Bundle extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_insert);

        final EditText tx1 = findViewById(R.id.textView1);
        final EditText tx2 = findViewById(R.id.textView2);
        add_btn = findViewById(R.id.add_btn);
        final Intent intent = getIntent(); /*데이터 수신*/
        extra = new Bundle();
        final Intent result = new Intent();


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number data = new Number(tx1.getText().toString(),tx2.getText().toString());
                result.putExtra("name",tx1.getText().toString());
                result.putExtra("number",tx2.getText().toString());
                setResult(RESULT_OK, result);


                //((Fragment1)((MainActivity)MainActivity.context).getSupportFragmentManager().findFragmentByTag("Fragment1")).add_item(data);
                //((Fragment1)Fragment1.context).add_item(data);
        //                ((Fragment1)getSupportFragmentManager().findFragmentByTag("Fragment1")).add_item(data);
            }
        });



    }

}
