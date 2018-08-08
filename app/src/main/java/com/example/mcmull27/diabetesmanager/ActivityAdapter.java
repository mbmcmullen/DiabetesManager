package com.example.mcmull27.diabetesmanager;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;


public class ActivityAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Act> items;

    private final int BGL = 0, DIET = 1, EXERCISE = 2, MEDICATION = 3;

    public ActivityAdapter(List<Act> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position).getType().equals(Act.BGL)){
            return BGL;
        }else if(items.get(position).getType().equals(Act.DIET)){
            return DIET;
        }else if(items.get(position).getType().equals(Act.EXERCISE)){
            return EXERCISE;
        }else if(items.get(position).getType().equals(Act.MEDICATION)){
            return MEDICATION;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType) {
            case BGL:
                View v1 = inflater.inflate(R.layout.item_bgl, parent, false);
                viewHolder = new ViewHolderBGL(v1);
                break;
            case DIET:
                View v2 = inflater.inflate(R.layout.item_act, parent, false);
                viewHolder = new ViewHolderAct(v2);
                break;
            case EXERCISE:
                View v3 = inflater.inflate(R.layout.item_act, parent, false);
                viewHolder = new ViewHolderAct(v3);
                break;
            case MEDICATION:
                View v4 = inflater.inflate(R.layout.item_act, parent, false);
                viewHolder = new ViewHolderAct(v4);
                break;
        }

        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        switch(viewHolder.getItemViewType()){
            case DIET:
                ViewHolderAct vh1 = (ViewHolderAct) viewHolder;
                TextView label = vh1.getLabel();
                label.setText(R.string.diet_label);
                vh1.itemView.setBackgroundResource(R.color.colorDietItem);
                break;
            case EXERCISE:
                ViewHolderAct vh2 = (ViewHolderAct) viewHolder;
                TextView label1 = vh2.getLabel();
                label1.setText(R.string.exercise_label);
                vh2.itemView.setBackgroundResource(R.color.colorExerciseItem);
                break;
            case MEDICATION:
                ViewHolderAct vh3 = (ViewHolderAct) viewHolder;
                TextView label2 = vh3.getLabel();
                label2.setText(R.string.medication_label);
                vh3.itemView.setBackgroundResource(R.color.colorMedicationItem);
                break;
        }

    }


    public class ViewHolderBGL extends RecyclerView.ViewHolder{
        private TextView label;
        private EditText date, time;
        private EditText amount;


        public ViewHolderBGL(View v){
            super(v);
            label = (TextView)v.findViewById(R.id.bgl_label);
            date = (EditText) v.findViewById(R.id.item_date);
            time = (EditText) v.findViewById(R.id.item_time);
            amount = (EditText) v.findViewById(R.id.item_amount);

            date.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    items.get(getAdapterPosition()).setTimestamp(editable.toString()+":"+time.getText().toString());
                    Log.d("DATA UPDATE:"+getAdapterPosition()+" date", editable.toString());
                }
            });

            time.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    items.get(getAdapterPosition()).setTimestamp(date.getText().toString()+":"+editable.toString());
                    Log.d("DATA UPDATE:"+getAdapterPosition()+" time", editable.toString());
                }
            });

            amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    items.get(getAdapterPosition()).setAmount(Double.parseDouble(editable.toString()));
                    Log.d("DATA UPDATE:"+getAdapterPosition()+" amount", editable.toString());
                }
            });
        }

        public TextView getLabel() { return label; }

        public EditText getDate() { return date; }

        public EditText getTime() { return time; }

        public EditText getAmount() { return amount; }

        public void setLabel(TextView label){this.label = label;}
    }

    public class ViewHolderAct extends RecyclerView.ViewHolder {
        private TextView label;
        private EditText description, amount, date, time;

        public ViewHolderAct(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.label);
            date = (EditText) v.findViewById(R.id.item_date);
            time = (EditText) v.findViewById(R.id.item_time);
            description = (EditText) v.findViewById(R.id.item_description);
            amount = (EditText) v.findViewById(R.id.item_amount);

            date.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    items.get(getAdapterPosition()).setTimestamp(editable.toString()+":"+time.getText().toString());
                    Log.d("DATA UPDATE:"+getAdapterPosition()+" date", editable.toString());
                }
            });

            time.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    items.get(getAdapterPosition()).setTimestamp(date.getText().toString()+":"+editable.toString());
                    Log.d("DATA UPDATE:"+getAdapterPosition()+" time", editable.toString());
                }
            });

            amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    items.get(getAdapterPosition()).setAmount(Double.parseDouble(editable.toString()));
                    Log.d("DATA UPDATE:"+getAdapterPosition()+" amount", editable.toString());
                }
            });

            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    items.get(getAdapterPosition()).setDescription(editable.toString());
                    Log.d("DATA UPDATE;"+getAdapterPosition()+" desc", editable.toString());
                }
            });
        }

        public TextView getLabel() {
            return label;
        }

        public EditText getAmount() {
            return amount;
        }

        public EditText getDescription() {
            return description;
        }

        public EditText getDate() {
            return date;
        }

        public EditText getTime() {
            return time;
        }

        public void setLabel(TextView label) {
            this.label = label;
        }

    }
}
