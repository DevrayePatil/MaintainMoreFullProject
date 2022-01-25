package com.example.maintainmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;


public class ServiceDetailsActivity extends AppCompatActivity {

    Button buttonCancel, buttonBook;

    TextView displayTotalItems, displayTotalPrice;

    int numberOfItems = 0;
    int servicePrice = 256;
    int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);

        setContentView(R.layout.activity_service_details);

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonBook = findViewById(R.id.buttonBook);

        displayTotalItems = findViewById(R.id.displayTotalItems);
        displayTotalPrice = findViewById(R.id.displayTotalPrice);

        buttonCancel.setOnClickListener(view -> finish());
        buttonBook.setOnClickListener(view -> TimePickerForService());
        ItemPicker();
    }

    private void TimePickerForService() {

        MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder();
        builder.setTitleText("Choose Time");

        MaterialTimePicker timePicker = builder.build();

        timePicker.show(getSupportFragmentManager(), "TIME_PICKER");

        timePicker.addOnPositiveButtonClickListener(view ->
                Toast.makeText(getApplicationContext(), "time" + timePicker.getHour() + ":"
                        + timePicker.getMinute() + " " + timePicker, Toast.LENGTH_SHORT).show());
    }

    private void DatePickerForService() {

        long today = MaterialDatePicker.todayInUtcMilliseconds();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Choose Date");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintBuilder.build());
        MaterialDatePicker materialDatePicker = builder.build();

        materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKET");

        materialDatePicker.addOnPositiveButtonClickListener(selection ->
                Toast.makeText(getApplicationContext(), "Selected Date " + materialDatePicker.getHeaderText(), Toast.LENGTH_SHORT).show());
    }

    private void BookService() {

        if (numberOfItems <= 0){
            Toast.makeText(getApplicationContext(), "Please Select at least 1 Item", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Selected Items" + numberOfItems, Toast.LENGTH_SHORT).show();

        }
    }

    private void ItemPicker(){
        TextView numberPicker;
        ImageView buttonDecrement, buttonIncrement;

        numberPicker = findViewById(R.id.numberPicker);

        buttonDecrement = findViewById(R.id.buttonDecrement);
        buttonIncrement = findViewById(R.id.buttonIncrement);

        buttonDecrement.setOnClickListener(view -> {
            if (numberOfItems > 0) {
                numberOfItems--;
                numberPicker.setText(String.valueOf(numberOfItems));
                displayTotalItems.setText(String.valueOf(numberOfItems));

                totalPrice = servicePrice * numberOfItems;
                displayTotalPrice.setText(String.valueOf(totalPrice));
            }
        });

        buttonIncrement.setOnClickListener(view -> {
            numberOfItems++;
            numberPicker.setText(String.valueOf(numberOfItems));
            displayTotalItems.setText(String.valueOf(numberOfItems));

            totalPrice = servicePrice * numberOfItems;
            displayTotalPrice.setText(String.valueOf(totalPrice));
        });


    }
}