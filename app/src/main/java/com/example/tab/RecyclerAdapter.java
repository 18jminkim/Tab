package com.example.tab;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> implements Filterable {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Number> listData = new ArrayList<>();
    Context context;
    ArrayList<Number> unFilteredlist = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }


    void addItem(Number data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
        unFilteredlist.add(data);
    }

    public void onClick(){

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                System.out.println(charString);
                if(charString.isEmpty()) {
                    listData = unFilteredlist;
                } else {
                    ArrayList<Number> filteringList = new ArrayList<>();
                    for(Number name : unFilteredlist) {
                        if(name.getName().toLowerCase().contains(charString.toLowerCase())
                        || name.getNum().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteringList.add(name);
                        }
                    }
                    listData = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listData = (ArrayList<Number>)results.values;
                for(int i=0 ;i<listData.size(); i++){
                    System.out.println(listData.get(i).getName());
                }
                notifyDataSetChanged();

            }
        };

    }


    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;
        private Button btn_delete;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
            btn_delete = itemView.findViewById(R.id.delete_btn);

            btn_delete.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View view){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        listData.remove(pos);
                        unFilteredlist.remove(pos);
                        notifyDataSetChanged();
                    }

            }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(view.getContext(), ItemActivity.class);
                        intent.putExtra("name",listData.get(pos).getName());
                        intent.putExtra("number",listData.get(pos).getNum());
                        view.getContext().startActivity(intent);
                    }
                }
            });

        }

        void onBind(Number data) {
            textView1.setText(data.getName());
            textView2.setText(data.getNum());
            imageView.setImageResource(R.drawable.person);
        }
    }
}
