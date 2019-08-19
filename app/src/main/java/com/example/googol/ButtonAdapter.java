package com.example.googol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    public static int suggestedMax;
    public static int currentPosition;

    private Context context;
    private ArrayList<ButtonModel> buttonList;

    private int maxNumber = 0;

    public ButtonAdapter(Context context, ArrayList<ButtonModel> buttonList) {
        this.context = context;
        this.buttonList = buttonList;
        currentPosition = 0;
        suggestedMax = 0;
    }


    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.googol_button, viewGroup, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ButtonViewHolder buttonViewHolder, int position) {
        final ButtonModel buttonModel = buttonList.get(position);
        String value = buttonModel.getValue();
        final int hiddenValue = buttonModel.getHiddenValue();
        buttonViewHolder.button.setText(value);
        buttonViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == buttonViewHolder.getAdapterPosition()) {
                    suggestedMax = hiddenValue;
                    buttonViewHolder.button.setEnabled(false);
                    buttonViewHolder.button.setText("" + hiddenValue);
                    if (hiddenValue > maxNumber) {
                        maxNumber = hiddenValue;
                        MainActivity.setText(hiddenValue);
                    }
                    currentPosition++;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    class ButtonViewHolder extends RecyclerView.ViewHolder{

        Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }
}
