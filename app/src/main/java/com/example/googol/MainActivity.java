package com.example.googol;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<ButtonModel> buttonList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter buttonAdapter;
    static TextView textView;
    Button finishButton;
    Button refreshButton;
    Random random;
    ProgressBar spinner;

    private int[] randomNumberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonList = new ArrayList<>();
        random = new Random();
        randomNumberList = new int[50];
        textView = findViewById(R.id.largestNumber);
        finishButton = findViewById(R.id.finishButton);
        refreshButton = findViewById(R.id.nextButton);
        recyclerView = findViewById(R.id.recycler_view);
        buttonAdapter = new ButtonAdapter(this, buttonList);
        layoutManager = new GridLayoutManager(this, 4);
        spinner = findViewById(R.id.progressBar1);

        spinner.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(buttonAdapter);


        generateRandomList(50);
        createButtons();

        spinner.setVisibility(View.GONE);


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actualMax = getMax(randomNumberList);
                if (ButtonAdapter.suggestedMax == actualMax) {
                    Toast.makeText(MainActivity.this, "Winner", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Looser", Toast.LENGTH_LONG).show();
                }
                openRemainingBoxes();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshActivity();
            }
        });
    }

    private void createButtons() {
        for (int i=1; i<=44; i++) {
            buttonList.add(new ButtonModel("" + i, randomNumberList[i]));
        }
    }

    private void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    void generateRandomList(int size) {
        int bound = generateBound(100, 10000);
        for (int i = 0; i < size; i++) {
            randomNumberList[i] = random.nextInt(bound);

            for (int j = 0; j < i; j++) {
                if (randomNumberList[i] == randomNumberList[j]) {
                    i--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                    break;
                }
            }
        }
    }

    int generateBound(int lowerLimit, int upperLimit) {
        int rand = random.nextInt(upperLimit);
        if(rand < lowerLimit) {
            rand += lowerLimit;
        }
        return rand;
    }

    int getMax(int []array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    void openRemainingBoxes() {
        int max = getMax(randomNumberList);
        int suggestedMax = ButtonAdapter.suggestedMax;
        Button button;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            button = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.button);

            revealHiddenValue(button, buttonList.get(i), max, suggestedMax);
        }
    }

    void revealHiddenValue(Button button, ButtonModel buttonModel, int max, int suggestedMax) {

        button.setEnabled(false);
        int hiddenValue = buttonModel.getHiddenValue();
        button.setText("" + hiddenValue);

        if (suggestedMax == hiddenValue) {
            button.setTextColor(Color.RED);
        }

        if (max == hiddenValue) {
            button.setTextColor(Color.GREEN);
        }
    }

    public static void setText(int val) {
        textView.setText("" + val);
    }

    public static String getText() {
        return textView.getText().toString();
    }
}
