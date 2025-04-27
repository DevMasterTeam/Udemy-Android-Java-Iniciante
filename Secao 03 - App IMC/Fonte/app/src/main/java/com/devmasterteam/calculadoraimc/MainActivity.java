package com.devmasterteam.calculadoraimc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        EditText editTextWeight = findViewById(R.id.edittext_weight);
        SeekBar seekBarHeight = findViewById(R.id.seek_height);
        TextView textHeightValue = findViewById(R.id.text_height_value);
        TextView textResult = findViewById(R.id.text_result);
        Button buttonCalculate = findViewById(R.id.button_calculate);
        Button buttonClear = findViewById(R.id.button_clear);

        // Atualizar valor da altura ao mover o SeekBar
        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textHeightValue.setText(progress + " cm");
                textHeightValue.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Calcular IMC ao clicar no botão
        buttonCalculate.setOnClickListener(v -> {
            try {
                String weightText = editTextWeight.getText().toString();
                double height = (double) seekBarHeight.getProgress() / 100; // Converter cm para metros
                double weight = Double.parseDouble(weightText);

                if (height == 0) {
                    Toast.makeText(getApplicationContext(), R.string.msg_select_valid_height, Toast.LENGTH_SHORT).show();
                } else {
                    double imc = weight / (height * height);
                    textResult.setText(String.format(Locale.getDefault(), "IMC: %.2f", imc));
                    textResult.setVisibility(TextView.VISIBLE);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), R.string.msg_select_valid_weight, Toast.LENGTH_SHORT).show();
            }
        });

        // Limpar os campos ao clicar no botão
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextWeight.setText("");
                seekBarHeight.setProgress(0);
                textResult.setText("");

                textHeightValue.setVisibility(TextView.GONE);
                textResult.setVisibility(TextView.GONE);
            }
        });
    }
}