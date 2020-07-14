package com.example.tab;

import android.app.Activity;
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

        add_btn = findViewById(R.id.add_btn);
        final Intent intent = getIntent(); /*데이터 수신*/
        extra = new Bundle();
        final Intent result = new Intent();


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                //((Fragment1)((MainActivity)MainActivity.context).getSupportFragmentManager().findFragmentByTag("Fragment1")).add_item(data);
                //((Fragment1)Fragment1.context).add_item(data);
                //                ((Fragment1)getSupportFragmentManager().findFragmentByTag("Fragment1")).add_item(data);
            }
        });
    }

    public void finish(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        EditText tx1 = findViewById(R.id.textView1);
        EditText tx2 = findViewById(R.id.textView2);
        String name = tx1.getText().toString();
        String number = tx2.getText().toString();
        if(!(name.isEmpty())){
            bundle.putString("name",name);
            bundle.putString("number",number);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
        }
        else{
            setResult(Activity.RESULT_CANCELED,intent);
        }
        super.finish();
    }

}