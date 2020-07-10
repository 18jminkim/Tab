package com.example.tab;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.ListFragment;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.w3c.dom.Text;

        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.List;

public class Fragment1 extends Fragment {
    List<String> data = new ArrayList<>();
    ArrayList<Number> number_list;
    ListView listview;
    EditText editText_name, editText_num, search_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        search_name = (EditText)getActivity().findViewById(R.id.insert_name);
        Button btn_search = (Button)getActivity().findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view)
        });

        editText_name = (EditText)getActivity().findViewById(R.id.insert_name);
        editText_num = (EditText)getActivity().findViewById(R.id.insert_num);

       Button btn_1 = (Button) getActivity().findViewById(R.id.send);
        btn_1.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                String name = editText_name.getText().toString();
                String num = editText_num.getText().toString();

            }
        });
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        listview = (ListView) view.findViewById(R.id.list);
        number_list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);
        jsonParsing(getJsonString());
        for(int i=0; i<number_list.size(); i++){
            data.add("이름: " + number_list.get(i).getName()+"\n번호: "+number_list.get(i).getNum());
        }
        System.out.println(data);
        adapter.notifyDataSetChanged();


        return view;
    }
    /*
    private void writeJsonString(String name, String num){
        String json = "";
        JSONObject jsonObject = new JSONObject();
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

    }
     */
    private String Search(String name){
        for(int i=0; i<number_list.size(); i++) {
            if(number_list.get(i).same(name)) return number_list.get(i).getName();
        }
        return "";
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
