package com.example.mcmull27.diabetesmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class QueryAdaptor extends RecyclerView.Adapter<QueryAdaptor.ProductViewHolder>{


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Act> actList;

    //getting the context and product list with constructor
    public QueryAdaptor(Context mCtx, List<Act> aList) {
        this.mCtx = mCtx;
        this.actList = aList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_table, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Act act = actList.get(position);

        //binding the data with the viewholder views
        holder.textViewType.setText(act.getType());
        holder.textViewShortDesc.setText(act.getDescription());
        holder.textViewAmount.setText(String.valueOf(act.getAmount()));
        holder.textViewTimeStamp.setText(String.valueOf(act.getTimestamp()));


    }


    @Override
    public int getItemCount() {
        return actList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewType, textViewShortDesc, textViewAmount, textViewTimeStamp;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewType = (TextView) itemView.findViewById(R.id.textViewType);
            textViewShortDesc = (TextView) itemView.findViewById(R.id.textViewShortDesc);
            textViewAmount = (TextView) itemView.findViewById(R.id.textViewAmount);
            textViewTimeStamp = (TextView) itemView.findViewById(R.id.textViewTimeStamp);
        }
    }
}
