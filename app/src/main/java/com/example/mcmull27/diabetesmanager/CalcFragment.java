package com.example.mcmull27.diabetesmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CalcFragment extends Fragment {
    TextView sumResult, devResult, meanResult;
    int count;
    double sum, mean, stdDev;


    public CalcFragment(){
        count = 0;
        sum = 0;
        mean = 0;
        stdDev = 0;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragment", "inside onCreateView");
        return inflater.inflate(R.layout.fragment_layout,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("fragment", "inside onStart");
        sumResult = (TextView) getView().findViewById(R.id.result_total);
        meanResult = (TextView) getView().findViewById(R.id.result_mean);
        devResult = (TextView) getView().findViewById(R.id.result_stdDev);
        setTextViews(sum,mean,stdDev);
    }

    public void calculate(List<Act> displayList){
        Log.d("fragment", "inside calculate");

        //calculate mean
        for (int i = 0; i < displayList.size(); i++) {
            count++;
            sum += displayList.get(i).getAmount();
            Log.e("JKERR", "inside_mean_loop_amount: " + displayList.get(i).getAmount() + " \n\t\t\tsum: " + sum);
        }


        mean = sum / count;
        if(count == 0) mean = 0;

        Log.e("JKERR", "Mean: " + mean);

        //calculate standard deviance
        for (int i = 0; i < displayList.size(); i++) {
            stdDev += Math.pow(displayList.get(i).getAmount() - mean, 2);
        }
        Log.e("JKERR", "STD: " + stdDev);
        Log.d("fragment", "exiting calculate");
    }

    public void setTextViews(double sum, double mean, double stdDev){
        Log.d("fragment", "inside setTextViews");
        sumResult.setText(Double.toString(sum));
        meanResult.setText(Double.toString(mean));
        devResult.setText(Double.toString(stdDev));
        Log.d("fragment", "exiting setTextViews");
    }

}
