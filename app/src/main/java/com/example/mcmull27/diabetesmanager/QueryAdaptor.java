package com.example.mcmull27.diabetesmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class QueryAdaptor extends RecyclerView.Adapter<QueryAdaptor.ProductViewHolder>{

    //we are storing all the products in a list
    private List<Act> actList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);

    }

    public void setListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public QueryAdaptor(List<Act> actList) {
        this.actList = actList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_item, parent,false);
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


    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView textViewType, textViewShortDesc, textViewAmount, textViewTimeStamp;
        Button delete;


        public ProductViewHolder(final View itemView) {
            super(itemView);

            textViewType = (TextView) itemView.findViewById(R.id.textViewType);
            textViewShortDesc = (TextView) itemView.findViewById(R.id.textViewShortDesc);
            textViewAmount = (TextView) itemView.findViewById(R.id.textViewAmount);
            textViewTimeStamp = (TextView) itemView.findViewById(R.id.textViewTimeStamp);
            delete = (Button) itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
