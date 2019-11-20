package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
    Button calculateBill;
    Spinner location, roomType;
    TextView checkInDate, checkOutDate, showCheckInDate, showCheckOutDate, showRoomType, showLocation,
    showTotalStay, showUnitPrice, showTax, showServiceCharge, showTotalAmount, heading, thankYou;
    EditText adults, children, roomNumber;
    String selectedcheckInDate = "2019/05/05", selectedcheckOutDate = "";
    Double taxAmount, serviceCharge, unitPrice, grandTotal;
    Long totalStay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeVariables();

        final String [] places = {"Kathmandu", "Pokhara", "Butwal"};
        final String [] rooms = {"Deluxe", "Gold", "Platinium"};

        ArrayAdapter placesAdaptar = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                places
        );
        location.setAdapter(placesAdaptar);
        ArrayAdapter roomAdaptar = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                rooms
        );
        location.setAdapter(placesAdaptar);
        roomType.setAdapter(roomAdaptar);
        checkInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDatePicker();
            }
        });
        checkOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadcheckOutDatePicker();
            }
        });
        calculateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validateDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(totalStay < 0) {
                    Toast.makeText(MainActivity.this,"Check Out Date Must not be Smaller than Check In",Toast.LENGTH_LONG).show();
                } else {
                    int locationPostion = location.getSelectedItemPosition();
                    String location = places[locationPostion];

                    int roomTypePositon = roomType.getSelectedItemPosition();
                    String roomType = rooms[roomTypePositon];
                    int price;

                    int rooms = Integer.parseInt(roomNumber.getText().toString());
                    getPrice(roomType, rooms);
                    thankYou.setText("Thank You!");
                    heading.setText("Your Bill For Stay");
                    showCheckInDate.setText("Check In Date :" + selectedcheckInDate);
                    showCheckOutDate.setText("Check In Date :" + selectedcheckOutDate);
                    showTotalStay.setText("Total Stay :" + totalStay + " Days");
                    showRoomType.setText("Room Type :"+ roomType);
                    showServiceCharge.setText("Service Charge : " + serviceCharge);
                    showTax.setText("Total Tax: " + taxAmount);
                    showTotalAmount.setText("Total Amount to Pay: " + grandTotal);
                    showUnitPrice.setText("Price for Room: " + unitPrice);
                }
            }
        });

    }

    private void loadDatePicker () {
       final Calendar c = Calendar.getInstance();
       int year = c.get(Calendar.YEAR);
       int month = c.get(Calendar.MONTH);
       int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedcheckInDate = month + "/" + day + "/" + year;
                checkInDate.setText(selectedcheckInDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    };
    private void loadcheckOutDatePicker () {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedcheckOutDate = month + "/" + day + "/" + year;
                checkOutDate.setText(selectedcheckOutDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    };

    public void validateDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

            Date first = sdf.parse(selectedcheckInDate);
            Date second = sdf.parse(selectedcheckOutDate);
            totalStay = (second.getTime()- first.getTime()) / (24 * 60 * 60 * 1000) ;
    }

    public void InitializeVariables () {
        calculateBill = findViewById(R.id.calculateBill);
        location = findViewById(R.id.location);
        roomType = findViewById(R.id.roomType);
        checkInDate = findViewById(R.id.checkInDate);
        checkOutDate = findViewById(R.id.checkOutDate);
        showCheckInDate = findViewById(R.id.showCheckInDate);
        showCheckOutDate = findViewById(R.id.showCheckOutDate);
        showRoomType = findViewById(R.id.showRoomType);
        showTotalStay = findViewById(R.id.showTotalStay);
        showTax = findViewById(R.id.showTax);
        showUnitPrice = findViewById(R.id.showUnitPrice);
        showServiceCharge = findViewById(R.id.showServiceCharge);
        showTotalAmount = findViewById(R.id.showTotalAmount);
        adults = findViewById(R.id.nadults);
        heading = findViewById(R.id.billHeading);
        thankYou = findViewById(R.id.thankYou);
        children = findViewById(R.id.nchildren);
        roomNumber = findViewById(R.id.nRooms);
    };

    public void getPrice (String type, int num) {
        Double totalexAddition;
        if(type.equals("Deluxe")) {
            unitPrice = 1000.0;
            totalexAddition = unitPrice * num * totalStay;
            taxAmount = (totalexAddition * (0.13));
            serviceCharge = (totalexAddition) * (0.1);
            grandTotal = (totalexAddition + taxAmount + serviceCharge);
        } else if (type.equals("Gold")){
            unitPrice = 2000.0;
            totalexAddition = unitPrice * num * totalStay;
            taxAmount = (totalexAddition * (0.13));
            serviceCharge = (totalexAddition) * (0.1);
            grandTotal = (totalexAddition + taxAmount + serviceCharge);
        } else {
            unitPrice = 3000.0;
            totalexAddition = unitPrice * num * totalStay;
            taxAmount = (totalexAddition * (0.13));
            serviceCharge = (totalexAddition) * (0.1);
            grandTotal = (totalexAddition + taxAmount + serviceCharge);
        }
    }
}
