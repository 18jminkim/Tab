package com.example.tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Fragment1 extends Fragment implements TextWatcher {
    ArrayList<Number> number_list = new ArrayList<Number>();
    RecyclerAdapter adapter = new RecyclerAdapter();
    ArrayList<Number> add_list = new ArrayList<>();
    FloatingActionButton btn_add;
    EditText editText;
    int flag = 0;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);

        // MenuInflater inflater

        //adapter contact list
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);

        if(flag == 0) {
            jsonParsing(getJsonString());
            for(int i=0; i<number_list.size(); i++){
                //add_list.add(number_list.get(i));
                adapter.addItem(number_list.get(i));
            }
            adapter.notifyDataSetChanged();
            flag = 1;
        }


        editText = (EditText)view.findViewById(R.id.search_name);
        editText.addTextChangedListener(this);
        btn_add = view.findViewById(R.id.add_btn);
        btn_add.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivityForResult(intent,3000);
            }
        });



        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("get result");
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 3000:
                    add_item(new Number(data.getStringExtra("name"),data.getStringExtra("number")));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void add_item(Number data){
        //number_list.add(data);
        add_list.add(data);
        adapter.addItem(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private String getJsonString(){
        String json = "";
        try {
            InputStream is = getActivity().getAssets().open("test.json");
            int fileSize = is.available();
            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }
    private void jsonParsing(String json)
    {
        number_list = new ArrayList();
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray numberarray = jsonObject.getJSONArray("number_list");
            for(int i=0; i<numberarray.length(); i++)
            {
                JSONObject number_listJSONObjectObject = numberarray.getJSONObject(i);
                Number number = new Number();
                number.setName(number_listJSONObjectObject.getString("name"));
                number.setNum(number_listJSONObjectObject.getString("num"));
                number_list.add(number);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
