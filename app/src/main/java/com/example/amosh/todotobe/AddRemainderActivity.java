package com.example.amosh.todotobe;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddRemainderActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenerFrom;
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerFrom;

    private DatePickerDialog.OnDateSetListener mDateSetListenerTo;
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerTo;

    private Spinner notificationSpinner;
    private Spinner repeatSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_remainder);

        //  getting Date & Time to be shwon at Activity Start
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        // setting Date & Time Format
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
        String formattedDate = dateFormat.format(date);

        DateFormat dateFormat1 = new SimpleDateFormat("MMMM dd,yyyy");
        String formattedDate1 = dateFormat1.format(date);

        // defining Textviews that hold Date
        TextView dateFrom = (TextView) findViewById(R.id.remainder_date_from_select);
        TextView dateTo = (TextView) findViewById(R.id.remainder_date_to_select);

        // setting current Date in its textviews
        dateFrom.setText(formattedDate1);
        dateTo.setText(formattedDate1);

        // defining Textviews that hold Time
        TextView clockFrom = (TextView) findViewById(R.id.remainder_clock_from_select);
        TextView clockTo = (TextView) findViewById(R.id.remainder_clock_to_select);

        // setting current Time in its textviews
        clockFrom.setText(formattedDate);
        clockTo.setText(formattedDate);

        // listening to user clicks to set new Date & Time
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFrom();
            }
        });
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTo();
            }
        });
        clockFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockFrom();
            }
        });
        clockTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockTo();
            }
        });

        notificationSpinner = (Spinner) findViewById(R.id.remainder_notification_spinner);
        setupNotificationSpinner();

        repeatSpinner = (Spinner) findViewById(R.id.remainder_repeat_spinner);
        setupRepeatSpinner();

        SwitchCompat remainderSwitch = (SwitchCompat) findViewById(R.id.remainder_switch);
        remainderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked() == true) {
                    RelativeLayout fromLayout = (RelativeLayout) findViewById(R.id.remainder_from_layout);
                    for (int i = 0; i < fromLayout.getChildCount(); i++) {
                        View child = fromLayout.getChildAt(i);
                        child.setEnabled(false);
                        fromLayout.setBackgroundColor(getResources().getColor(R.color.light_gary));
                    }

                    RelativeLayout toLayout = (RelativeLayout) findViewById(R.id.remainder_to_layout);
                    for (int i = 0; i < toLayout.getChildCount(); i++) {
                        View child = toLayout.getChildAt(i);
                        child.setEnabled(false);
                        toLayout.setBackgroundColor(getResources().getColor(R.color.light_gary));
                    }
                }

                if (buttonView.isChecked() == false) {
                    RelativeLayout fromLayout = (RelativeLayout) findViewById(R.id.remainder_from_layout);
                    for (int i = 0; i < fromLayout.getChildCount(); i++) {
                        View child = fromLayout.getChildAt(i);
                        child.setEnabled(true);
                        fromLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    }

                    RelativeLayout toLayout = (RelativeLayout) findViewById(R.id.remainder_to_layout);
                    for (int i = 0; i < toLayout.getChildCount(); i++) {
                        View child = toLayout.getChildAt(i);
                        child.setEnabled(true);
                        toLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });
    }

    private void setupNotificationSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter notificationSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_notification_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        notificationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        notificationSpinner.setAdapter(notificationSpinnerAdapter);

        // Set the integer mSelected to the constant values
        notificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("via Email")) {
                        // TODO : set selection
                    } else if (selection.equals("via Ring")) {
                        // TODO : set selection
                    } else {
                        // TODO : set selection
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupRepeatSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter notificationSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_repeat_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        notificationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        repeatSpinner.setAdapter(notificationSpinnerAdapter);

        // Set the integer mSelected to the constant values
        repeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("via Email")) {
                        // TODO : set selection
                    } else if (selection.equals("via Ring")) {
                        // TODO : set selection
                    } else {
                        // TODO : set selection
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private String getMonthName(int month) {

        String monthName = "";

        if (month == 1) {
            monthName = "January";
        }
        if (month == 2) {
            monthName = "February";
        }
        if (month == 3) {
            monthName = "March";
        }
        if (month == 4) {
            monthName = "April";
        }
        if (month == 5) {
            monthName = "May";
        }
        if (month == 6) {
            monthName = "June";
        }
        if (month == 7) {
            monthName = "July";
        }
        if (month == 8) {
            monthName = "August";
        }
        if (month == 9) {
            monthName = "September";
        }
        if (month == 10) {
            monthName = "October";
        }
        if (month == 11) {
            monthName = "November";
        }
        if (month == 12) {
            monthName = "December";
        }

        return monthName;
    }

    private int getMonthNumber(String monthName) {

        int monthNumber = 0;

        switch (monthName) {
            case "January":
                monthNumber = 1;
                break;
            case ("February"):
                monthNumber = 2;
                break;
            case ("March"):
                monthNumber = 3;
                break;
            case ("April"):
                monthNumber = 4;
                break;
            case ("May"):
                monthNumber = 5;
                break;
            case ("June"):
                monthNumber = 6;
                break;
            case ("July"):
                monthNumber = 7;
                break;
            case ("August"):
                monthNumber = 8;
                break;
            case ("September"):
                monthNumber = 9;
                break;
            case ("October"):
                monthNumber = 10;
                break;
            case ("November"):
                monthNumber = 11;
                break;
            case ("December"):
                monthNumber = 12;
                break;
        }
        return monthNumber;
    }


    private void dateFrom() {

        final TextView dateFrom = (TextView) findViewById(R.id.remainder_date_from_select);

        // getting date that written in textview
        String dataFromCurrentString = dateFrom.getText().toString();
        String[] spiltedMonthFromRest = dataFromCurrentString.split("\\s+");
        String monthName = spiltedMonthFromRest[0];
        String dayAndYearString = spiltedMonthFromRest[1];

        String[] spilted2ndPhase = dayAndYearString.split(",");
        String dayString = spilted2ndPhase[0];
        String yearString = spilted2ndPhase[1];

        int monthNumber = getMonthNumber(monthName.trim());
        int dayNumber = Integer.parseInt(dayString.trim());
        int yearNumber = Integer.parseInt(yearString.trim());

        // start picker with pre-chosen date
        DatePickerDialog dialog = new DatePickerDialog(AddRemainderActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListenerFrom,
                yearNumber, monthNumber - 1, dayNumber);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // picking the new date and set it to the text view
        mDateSetListenerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthName = getMonthName(month);
                String date = monthName + " " + day + "," + year;
                dateFrom.setText(date);
            }
        };

    }

    private void dateTo() {

        final TextView dateTo = (TextView) findViewById(R.id.remainder_date_to_select);

        String dataToCurrentString = dateTo.getText().toString();
        String[] spiltedMonthFromRest = dataToCurrentString.split("\\s+");
        String monthName = spiltedMonthFromRest[0];
        String dayAndYearString = spiltedMonthFromRest[1];

        String[] spilted2ndPhase = dayAndYearString.split(",");
        String dayString = spilted2ndPhase[0];
        String yearString = spilted2ndPhase[1];

        int monthNumber = getMonthNumber(monthName.trim());
        int dayNumber = Integer.parseInt(dayString.trim());
        int yearNumber = Integer.parseInt(yearString.trim());


        DatePickerDialog dialog = new DatePickerDialog(AddRemainderActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListenerTo,
                yearNumber, monthNumber - 1, dayNumber);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListenerTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthName = getMonthName(month);
                String date = monthName + " " + day + "," + year;
                dateTo.setText(date);
            }
        };
    }


    private void clockFrom() {

        final TextView clockFrom = (TextView) findViewById(R.id.remainder_clock_from_select);

        // reading current time written in text view
        String clockFromCurrentString = clockFrom.getText().toString();
        String[] splitedHourFromRest = clockFromCurrentString.split(":");
        String hourString = splitedHourFromRest[0];
        String minutesAndXm = splitedHourFromRest[1];

        String[] splited2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = splited2ndPhase[0];
        String am_pm = splited2ndPhase[1];


        int hourNumber = Integer.parseInt(hourString.trim());
        int minutesNumber = Integer.parseInt(minutesString.trim());

        // start picker with pre chosen time
        TimePickerDialog dialog = new TimePickerDialog(AddRemainderActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mTimeSetListenerFrom,
                hourNumber, minutesNumber, false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // setting the new to time to textview
        mTimeSetListenerFrom = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                String am_pm = "";

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, minute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "am";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "pm";
                String strHrsToShow = datetime.get(Calendar.HOUR) + ":" + datetime.get(Calendar.MINUTE) + " " +
                        am_pm;

                clockFrom.setText(strHrsToShow);
            }
        };

    }

    private void clockTo() {

        final TextView clockTo = (TextView) findViewById(R.id.remainder_clock_to_select);

        String clockToCurrentString = clockTo.getText().toString();
        String[] splitedHourFromRest = clockToCurrentString.split(":");
        String hourString = splitedHourFromRest[0];
        String minutesAndXm = splitedHourFromRest[1];

        String[] splited2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = splited2ndPhase[0];
        String am_pm = splited2ndPhase[1];


        int hourNumber = Integer.parseInt(hourString.trim());
        int minutesNumber = Integer.parseInt(minutesString.trim());


        TimePickerDialog dialog = new TimePickerDialog(AddRemainderActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mTimeSetListenerTo,
                hourNumber, minutesNumber, false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mTimeSetListenerTo = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                String am_pm = "";

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, minute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "am";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "pm";
                String strHrsToShow = datetime.get(Calendar.HOUR) + ":" + datetime.get(Calendar.MINUTE) + " " +
                        am_pm;

                clockTo.setText(strHrsToShow);
            }
        };
    }

}
