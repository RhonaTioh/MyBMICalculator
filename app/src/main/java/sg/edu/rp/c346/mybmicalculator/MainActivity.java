package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvCalDate;
    TextView tvLastCalBMI;
    TextView tvOutPut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvCalDate = findViewById(R.id.textViewLastDate);
        tvLastCalBMI = findViewById(R.id.textViewLastBMI);
        tvOutPut = findViewById(R.id.textViewOutPut);


            btnCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Float weight = Float.parseFloat(etWeight.getText().toString());
                    Float height = Float.parseFloat(etHeight.getText().toString());
                    Float BMI = weight / (height * height);

                    Calendar now = Calendar.getInstance();
                    String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
                    tvCalDate.setText("Last calculated Date: " + datetime);
                    tvLastCalBMI.setText("Last calculated BMI: " + String.format("%.3f",BMI));

                    if (BMI<18.5){
                        tvOutPut.setText("You are underweight");
                    }else if (BMI<25){
                        tvOutPut.setText("Your BMI is normal");
                    }else if (BMI<29.9){
                        tvOutPut.setText("You are overweight");
                    }else if (BMI>=30){
                        tvOutPut.setText("You are obese");
                    }

                }
            });

            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    etHeight.setText("");
                    etWeight.setText("");
                    tvCalDate.setText("Last calculated Date:");
                    tvLastCalBMI.setText("Last calculated BMI:");
                    tvOutPut.setText("");
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor prefEdit = prefs.edit();
                    prefEdit.clear();
                    prefEdit.commit();

                }
            });
        }


        @Override
        protected void onPause () {
            super.onPause();
            if (!etWeight.getText().toString().isEmpty()) {
//Input from EditText and store it in a variable
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                Float BMI = (weight / (height * height));
                Calendar now = Calendar.getInstance();String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
//Obtain an instance of the SharedPreferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//Obtain an instance of SharedPreferences Editor for update later
                SharedPreferences.Editor prefEdit = prefs.edit();
//Add the key-value pair
                prefEdit.putFloat("BMI", BMI);
                prefEdit.putString("datetime", datetime);
//Call commit() method to save changes into SharedPreferences
                prefEdit.commit();

            }
        }


        @Override
        protected void onResume () {
            super.onResume();
//Obtain an instance of the SharedPreferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//Retrieve saved data from the SharedPreferences
            float latestBMI = prefs.getFloat("BMI", 0.0f);
            String latestDate = prefs.getString("datetime", "");
//update UI element with the value
            tvCalDate.setText("Last calculated Date: " + latestDate);
            tvLastCalBMI.setText("Last calculated BMI: " + latestBMI);
        }
    }

