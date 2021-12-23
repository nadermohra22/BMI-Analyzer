package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class foodListAdapter extends RecyclerView.Adapter<foodListAdapter.ViewHilder> {


    Context context;
    List<foodInfo> f;
    private SelectItem selectItem;

    public foodListAdapter(Context context, SelectItem selectItem) {
        this.context = context;
        this.selectItem = selectItem;
    }

    public void setF(List<foodInfo> F) {
        f = F;
        notifyDataSetChanged();
    }

    @Override
    public ViewHilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.custom_food_info, parent, false);
        return new ViewHilder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHilder holder, int position) {
        foodInfo foods = f.get(position);
        holder.food_name.setText(foods.getFoodName());
        holder.food_catagory.setText(foods.getFoodCatagory());
        holder.cal_number.setText(foods.getCal() + "");

       //holder.imv.setImageBitmap(BitmapFactory.decodeStream(is, null, options));

    }

    @Override
    public int getItemCount() {
        if (f == null)
            f = new ArrayList<>();
        return f.size();
    }

    public void addItem(foodInfo food) {
        this.f.add(food);

    }

    public class ViewHilder extends RecyclerView.ViewHolder {


        TextView food_name, food_catagory, cal_number;
        ImageView imv ;
        private Button edit, delete;

        public ViewHilder(@NonNull View itemView) {
            super(itemView);
            imv=(ImageView) itemView.findViewById(R.id.food_img) ;
            food_name = (TextView) itemView.findViewById(R.id.food_name);
            food_catagory = (TextView) itemView.findViewById(R.id.food_catagory);
            cal_number = (TextView) itemView.findViewById(R.id.cal_number);
            edit = itemView.findViewById(R.id.but_edit);
            delete = itemView.findViewById(R.id.but_delete);
            edit.setOnClickListener(v -> {
                selectItem.item(getAdapterPosition(), f.get(getAdapterPosition()));
            });
            delete.setOnClickListener(v -> selectItem.deleteItem(getAdapterPosition()));
        }
    }

    public interface SelectItem {
        void item(int index, foodInfo foodInfo);

        void deleteItem(int index);
    }
}
