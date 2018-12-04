package com.example.amosh.todotobe;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddRemainderActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 0;

    DatePickerDialog.OnDateSetListener mDateSetListenerFrom;
    TimePickerDialog.OnTimeSetListener mTimeSetListenerFrom;

    DatePickerDialog.OnDateSetListener mDateSetListenerTo;
    TimePickerDialog.OnTimeSetListener mTimeSetListenerTo;

    Spinner notificationSpinner;
    Spinner repeatSpinner;
    SwitchCompat remainderSwitch;
    FloatingActionButton addFAB;

    TextView dateFrom;
    TextView dateTo;
    TextView timeFrom;
    TextView timeTo;

    EditText title;
    EditText description;
    EditText location;

    MyUsersDbHelper usersDbHelper;

    ImageView people;
    ImageView imageForEvent;

    String eTitle;
    String eDescription;
    String eLocation;

    int eDateFromDay = 0;
    int eDateFromMonth = 0;
    int eDateFromYear = 0;
    int eDateToDay = 0;
    int eDateToMonth = 0;
    int eDateToYear = 0;

    int eTimeFromHour = 0;
    int eTimeFromMinute = 0;
    int eTimeToHour = 0;
    int eTimeToMinute = 0;

    String ePeople = "";
    String eImageUri = "";

    String eNotification;
    String eRepeat;


    int eState = 0;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remainder);

        usersDbHelper = new MyUsersDbHelper(this);
        title = (EditText) findViewById(R.id.add_remainder_title);
        description = (EditText) findViewById(R.id.add_remainder_description);
        location = (EditText) findViewById(R.id.add_remainder_location_text);


        //  getting Date & Time to be shown at Activity Start
        final Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        // setting Date & Time Format
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
        String formattedDate = dateFormat.format(date);

        DateFormat dateFormat1 = new SimpleDateFormat("MMMM dd,yyyy");
        String formattedDate1 = dateFormat1.format(date);

        // defining TextViews that hold Date
        dateFrom = (TextView) findViewById(R.id.add_remainder_date_from);
        dateTo = (TextView) findViewById(R.id.add_remainder_date_to);

        // setting current Date in its TextViews
        dateFrom.setText(formattedDate1);
        dateTo.setText(formattedDate1);

        // defining TextViews that hold Time
        timeFrom = (TextView) findViewById(R.id.add_remainder_time_from);
        timeTo = (TextView) findViewById(R.id.add_remainder_time_to);

        // setting current Time in its TextViews
        timeFrom.setText(formattedDate);
        timeTo.setText(formattedDate);

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
        timeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeFrom();
            }
        });
        timeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeTo();
            }
        });

        notificationSpinner = (Spinner) findViewById(R.id.add_remainder_notification_spinner);
        setupNotificationSpinner();

        repeatSpinner = (Spinner) findViewById(R.id.add_remainder_repeat_spinner);
        setupRepeatSpinner();

        remainderSwitch = (SwitchCompat) findViewById(R.id.add_remainder_switch);
        remainderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
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

                if (!buttonView.isChecked()) {
                    RelativeLayout fromLayout = (RelativeLayout) findViewById(R.id.remainder_from_layout);
                    for (int i = 0; i < fromLayout.getChildCount(); i++) {
                        View child = fromLayout.getChildAt(i);
                        child.setEnabled(true);
                        fromLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    }

                    RelativeLayout toLayout = (RelativeLayout) findViewById(R.id.remainder_to_layout);
                    for (int i = 0; i < toLayout.getChildCount(); i++) {
                        View child = toLayout.getChildAt(i);
                        child.setEnabled(true);
                        toLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    }
                }
            }
        });

        imageForEvent = (ImageView) findViewById(R.id.add_remainder_image);
        imageForEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addFAB = (FloatingActionButton) findViewById(R.id.add_remainder_add_fab);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent();
            }
        });

    }

    private boolean checkIfValueSet(EditText text, String description) {
        if (TextUtils.isEmpty(text.getText())) {
            text.setError("Missing event " + description);
            return false;
        } else {
            text.setError(null);
            return true;
        }
    }


    private void addNewEvent() {

        boolean isAllOk = true;
        if (!checkIfValueSet(title, "title")) {
            isAllOk = false;
        }
        if (!checkIfValueSet(description, "description")) {
            isAllOk = false;
        }
        if (!checkIfValueSet(location, "location")) {
            isAllOk = false;
        }
        if (!isAllOk) {
        }

        eTitle = title.getText().toString().trim();
        eDescription = description.getText().toString().trim();
        eLocation = location.getText().toString().trim();

        Events event = new Events(eTitle, eDescription, eDateFromDay, eDateFromMonth,
                eDateFromYear, eDateToDay, eDateToMonth, eDateToYear,
                eTimeFromHour, eTimeFromMinute, eTimeToHour, eTimeToMinute,
                eLocation, eNotification, ePeople, eRepeat, eImageUri, eState);
        usersDbHelper.insertEvent(event);

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
                        eNotification = "via Email";
                    } else if (selection.equals("via Ring")) {
                        // TODO : set selection
                        eNotification = "via Ring";
                    } else {
                        // TODO : set selection
                        eNotification = "via MSG";
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
                    if (selection.equals("Daily")) {
                        // TODO : set selection
                        eRepeat = "Daily";
                    } else if (selection.equals("Weekly")) {
                        // TODO : set selection
                        eRepeat = "Weekly";
                    } else {
                        // TODO : set selection
                        eRepeat = "Monthly";
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

        final TextView dateFrom = (TextView) findViewById(R.id.add_remainder_date_from);

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

        // start picker with pre-chosen date
        DatePickerDialog dialog = new DatePickerDialog(AddRemainderActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListenerFrom,
                yearNumber, monthNumber - 1, dayNumber);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void dateTo() {

        final TextView dateTo = (TextView) findViewById(R.id.add_remainder_date_to);

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

        mDateSetListenerTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthName = getMonthName(month);
                String date = monthName + " " + day + "," + year;
                dateTo.setText(date);
            }
        };

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


    private void timeFrom() {

        final TextView timeFrom = (TextView) findViewById(R.id.add_remainder_time_from);

        // reading current time written in text view
        String timeFromCurrentString = timeFrom.getText().toString();
        String[] splitedHourFromRest = timeFromCurrentString.split(":");
        String hourString = splitedHourFromRest[0];
        String minutesAndXm = splitedHourFromRest[1];

        String[] splited2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = splited2ndPhase[0];
        String am_pm = splited2ndPhase[1];


        int hourNumber = Integer.parseInt(hourString.trim());
        int minutesNumber = Integer.parseInt(minutesString.trim());

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

                timeFrom.setText(strHrsToShow);
            }
        };

        // start picker with pre chosen time
        TimePickerDialog dialog = new TimePickerDialog(AddRemainderActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mTimeSetListenerFrom,
                hourNumber, minutesNumber, false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void timeTo() {

        final TextView timeTo = (TextView) findViewById(R.id.add_remainder_time_to);

        String timeToCurrentString = timeTo.getText().toString();
        String[] splitedHourFromRest = timeToCurrentString.split(":");
        String hourString = splitedHourFromRest[0];
        String minutesAndXm = splitedHourFromRest[1];

        String[] splited2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = splited2ndPhase[0];
        String am_pm = splited2ndPhase[1];


        int hourNumber = Integer.parseInt(hourString.trim());
        int minutesNumber = Integer.parseInt(minutesString.trim());

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

                timeTo.setText(strHrsToShow);
            }
        };

        TimePickerDialog dialog = new TimePickerDialog(AddRemainderActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mTimeSetListenerTo,
                hourNumber, minutesNumber, false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void openGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                imageUri = resultData.getData();
                imageForEvent.setImageURI(imageUri);
                imageForEvent.invalidate();
                eImageUri = imageUri.toString();
            }

        }
    }

}
