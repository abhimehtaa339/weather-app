package com.example.calculator.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class adapter extends RecyclerView.Adapter<adapter.viewHolder> {

    private Context context;
    private ArrayList<weather_modal> list ;

    public adapter(Context context, ArrayList<weather_modal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item, parent ,false);
        viewHolder v = new viewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.temprature.setText(list.get(position).getTemprature()+".c");
        Picasso.get().load("https:".concat(list.get(position).getIcon())).into(holder.condition);
        holder.windspeed.setText(list.get(position).getWindspeed()+"Km/h");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");


//        holder.Contact.setText(arr.get(position).name);
//        holder.number.setText(arr.get(position).number);
//        holder.img.setImageResource(arr.get(position).img);

        try{
            Date t = input.parse(list.get(position).getTime());
            holder.time.setText(output.format(t));
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {

        return list.size();

    }

    public  class viewHolder extends RecyclerView.ViewHolder{
        private TextView time , temprature , windspeed;
        private ImageView condition;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_time);
            temprature = itemView.findViewById(R.id.tv_temprature);
            windspeed = itemView.findViewById(R.id.windspeed);
            condition = itemView.findViewById(R.id.weather_conditon);
        }
    }
}
