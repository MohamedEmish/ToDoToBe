package com.example.amosh.todotobe;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddRemainderActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST_EVENT = 0;
    public static final int PICK_IMAGE_REQUEST_PEOPLE = 1;
    public static final int PICK_IMAGE_REQUEST_PEOPLE2 = 2;
    public static final int PICK_IMAGE_REQUEST_PEOPLE3 = 3;


    DatePickerDialog.OnDateSetListener mDateSetListenerFrom;
    TimePickerDialog.OnTimeSetListener mTimeSetListenerFrom;

    DatePickerDialog.OnDateSetListener mDateSetListenerTo;
    TimePickerDialog.OnTimeSetListener mTimeSetListenerTo;


    Dialog dialog;
    EditText nameOfPeople;
    ImageView imageOfPeople;
    Button addPeople;
    Button closePeople;

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
    ImageView people2;
    ImageView people3;

    ImageView imageForEvent;
    ImageView close;

    String eTitle;
    String eDescription;
    String eLocation;

    int eDateFromDay;
    int eDateFromMonth;
    int eDateFromYear;
    int eDateToDay;
    int eDateToMonth;
    int eDateToYear;

    int eTimeFromHour;
    int eTimeFromMinute;
    int eTimeToHour;
    int eTimeToMinute;

    String ePeople = "";
    String ePeopleImageUri = "";
    String ePeopleImageUri2 = "";
    String ePeopleImageUri3 = "";
    String eImageUri = "";

    String eNotification;
    String eRepeat;
    String userName;


    int eState = 0;
    Uri imageUri;
    Uri peopleImageUri;
    Uri peopleImageUri2;
    Uri peopleImageUri3;

    int peopleFlag1 = 0;
    int peopleFlag2 = 0;
    int peopleFlag3 = 0;

    String idString;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remainder);

        // getting signed in user name
        userName = getIntent().getStringExtra("name");

        // open DB
        usersDbHelper = new MyUsersDbHelper(this);

        // defining UI Components
        title = (EditText) findViewById(R.id.add_remainder_title);
        description = (EditText) findViewById(R.id.add_remainder_description);
        location = (EditText) findViewById(R.id.add_remainder_location_text);

        // getting Date & Time to be shown at Activity Start
        final Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        // setting Date & Time Format
        DateFormat dateFormat = new SimpleDateFormat("h:mm aaa");
        String formattedDate = dateFormat.format(date);

        DateFormat dateFormat1 = new SimpleDateFormat("MMMM d,yyyy");
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

        // Defining and reading spinners of Notification & Repeat
        notificationSpinner = (Spinner) findViewById(R.id.add_remainder_notification_spinner);
        setupNotificationSpinner();

        repeatSpinner = (Spinner) findViewById(R.id.add_remainder_repeat_spinner);
        setupRepeatSpinner();

        // Defining and reading Reminder switch
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

        // Adding event Image
        imageForEvent = (ImageView) findViewById(R.id.add_remainder_image);
        imageForEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(PICK_IMAGE_REQUEST_EVENT);
            }
        });

        // Adding new Event
        addFAB = (FloatingActionButton) findViewById(R.id.add_remainder_add_fab);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent();
            }
        });

        people = (ImageView) findViewById(R.id.add_remainder_people_add);
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleFlag1 = 1;
                peopleFlag2 = 0;
                peopleFlag3 = 0;
                showAddPeopleCustomDialog(AddRemainderActivity.this, PICK_IMAGE_REQUEST_PEOPLE);
            }
        });
        people2 = (ImageView) findViewById(R.id.add_remainder_people_add_2);
        people2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleFlag1 = 0;
                peopleFlag2 = 1;
                peopleFlag3 = 0;
                showAddPeopleCustomDialog(AddRemainderActivity.this, PICK_IMAGE_REQUEST_PEOPLE2);
            }
        });
        people3 = (ImageView) findViewById(R.id.add_remainder_people_add_3);
        people3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleFlag1 = 0;
                peopleFlag2 = 0;
                peopleFlag3 = 1;
                showAddPeopleCustomDialog(AddRemainderActivity.this, PICK_IMAGE_REQUEST_PEOPLE3);
            }
        });

        // Close to go back
        close = (ImageView) findViewById(R.id.add_remainder_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

    }

    // Function to check values validate
    private boolean checkIfValueSet(EditText text, String description) {
        if (TextUtils.isEmpty(text.getText())) {
            text.setError("Missing event " + description);
            return false;
        } else {
            text.setError(null);
            return true;
        }
    }

    public void showAddPeopleCustomDialog(final Context context, final int picker) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_add_people_dialoge, null, false);

        /*HERE YOU CAN FIND YOU IDS AND SET TEXTS OR BUTTONS*/
        nameOfPeople = view.findViewById(R.id.dialog_people_name);
        imageOfPeople = view.findViewById(R.id.dialog_add_people_image);

        addPeople = view.findViewById(R.id.dialog_people_add);
        closePeople = view.findViewById(R.id.dialog_people_close);


        imageOfPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(picker);
            }
        });
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picker == PICK_IMAGE_REQUEST_PEOPLE) {
                    ePeople = nameOfPeople.getText().toString().trim();
                }
                if (people2.getVisibility() == View.GONE) {
                    people2.setVisibility(View.VISIBLE);
                } else if (people2.getVisibility() == View.VISIBLE && people3.getVisibility() == View.GONE) {
                    people3.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }
        });
        closePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (peopleFlag1 == 1) {
                    people.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_ppl));
                } else if (peopleFlag2 == 1) {
                    people2.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_ppl));
                } else if (peopleFlag3 == 1) {
                    people3.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_ppl));
                }

                dialog.dismiss();

            }
        });


        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.dialoge_box);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }


    // Function to check values existence
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
            Toast.makeText(AddRemainderActivity.this, "Please Enter necessary data", Toast.LENGTH_SHORT).show();
        }
        if (isAllOk) {
            if (getDataAndInsert()) {
                Toast.makeText(AddRemainderActivity.this, "Insertion Success", Toast.LENGTH_SHORT).show();
                goBack();
            } else if (!getDataAndInsert()) {
                Toast.makeText(AddRemainderActivity.this, "Insertion failed", Toast.LENGTH_SHORT).show();

            }
        }
    }

    // Function to Insert new Event into DB
    private boolean getDataAndInsert() {

        eTitle = title.getText().toString().trim();
        eDescription = description.getText().toString().trim();
        eLocation = location.getText().toString().trim();

        eDateFromDay = getDateDay(dateFrom.getText().toString().trim());
        eDateFromMonth = getDateMonth(dateFrom.getText().toString().trim());
        eDateFromYear = getDateYear(dateFrom.getText().toString().trim());
        eTimeFromHour = getTimeHour(timeFrom.getText().toString().trim());
        eTimeFromMinute = getTimeMinute(timeFrom.getText().toString().trim());

        if (!remainderSwitch.isChecked()) {
            eDateToDay = getDateDay(dateTo.getText().toString().trim());
            eDateToMonth = getDateMonth(dateTo.getText().toString().trim());
            eDateToYear = getDateYear(dateTo.getText().toString().trim());
            eTimeToHour = getTimeHour(timeTo.getText().toString().trim());
            eTimeToMinute = getTimeMinute(timeTo.getText().toString().trim());
        } else if (remainderSwitch.isChecked()) {
            eDateToDay = eDateFromDay;
            eDateToMonth = eDateFromMonth;
            eDateToYear = eDateFromYear;
            eTimeToHour = 23;
            eTimeToMinute = 59;
        }
        eNotification = notificationSpinner.getSelectedItem().toString().trim();
        eRepeat = repeatSpinner.getSelectedItem().toString().trim();

        Events event = new Events();

        event.setTitle(eTitle);
        event.setDescription(eDescription);
        event.setLocation(eLocation);

        event.setDateFromDay(eDateFromDay);
        event.setDateFromMonth(eDateFromMonth);
        event.setDateFromYear(eDateFromYear);
        event.setTimeFromHour(eTimeFromHour);
        event.setTimeFromMinutes(eTimeFromMinute);

        event.setDateToDay(eDateToDay);
        event.setDateToMonth(eDateToMonth);
        event.setDateToYear(eDateToYear);
        event.setTimeToHour(eTimeToHour);
        event.setTimeToMinutes(eTimeToMinute);

        event.setNotification(eNotification);
        event.setRepeat(eRepeat);
        event.setState(eState);
        event.setImage(eImageUri);

        event.setPeople(ePeople);
        event.setPeopleImage(ePeopleImageUri);
        event.setPeopleImage2(ePeopleImageUri2);
        event.setPeopleImage3(ePeopleImageUri3);

        event.setUserName(userName);

        usersDbHelper.insertEvent(event);

        notification(eDateFromDay, eDateFromMonth, eDateFromYear, eTimeFromHour, eTimeFromMinute, eRepeat);

        return true;
    }

    // Function to go back
    private void goBack() {
        Intent back = new Intent(AddRemainderActivity.this, MainScreenActivity.class);
        back.putExtra("name", userName);
        startActivity(back);
    }

    // Function to get Day integer
    private int getDateDay(String date) {

        // Dividing date string into day,month and year
        String[] cutMonthFromRest = date.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

        int dayNumber = Integer.parseInt(dayString.trim());

        return dayNumber;
    }

    // Function to get Month integer
    private int getDateMonth(String date) {

        // Dividing date string into day,month and year
        String[] cutMonthFromRest = date.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

        return getMonthNumber(monthName.trim());
    }

    // Function to get Year integer
    private int getDateYear(String date) {

        // Dividing date string into day,month and year
        String[] cutMonthFromRest = date.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

        int yearNumber = Integer.parseInt(yearString.trim());

        return yearNumber;
    }

    // Function to get Hour integer
    private int getTimeHour(String time) {

        // Dividing date string into hours,minutes and am_pm
        String[] cutHourFromRest = time.split(":");
        String hourString = cutHourFromRest[0];
        String minutesAndXm = cutHourFromRest[1];

        String[] cut2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = cut2ndPhase[0];
        String am_pm = cut2ndPhase[1];

        int hour = Integer.parseInt(hourString.trim());
        int hourNumber = 0;
        if (am_pm.equals("am")) {
            hourNumber = hour;
        }
        if (am_pm.equals("pm")) {
            hourNumber = hour + 12;
        }

        return hourNumber;
    }

    // Function to get minutes integer
    private int getTimeMinute(String time) {

        // Dividing date string into hours,minutes and am_pm
        String[] cutHourFromRest = time.split(":");
        String hourString = cutHourFromRest[0];
        String minutesAndXm = cutHourFromRest[1];

        String[] cut2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = cut2ndPhase[0];
        String am_pm = cut2ndPhase[1];

        return Integer.parseInt(minutesString.trim());
    }

    // Spinners Functions
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
                        eNotification = "via Email";
                    } else if (selection.equals("via Ring")) {
                        eNotification = "via Ring";
                    } else {
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
                        eRepeat = "Daily";
                    } else if (selection.equals("Weekly")) {
                        eRepeat = "Weekly";
                    } else {
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


    // Function to convert month number into month name
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

    // Function to convert month name into month number
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

        dateFrom = (TextView) findViewById(R.id.add_remainder_date_from);

        // getting date that written in textView
        final String dataFromCurrentString = dateFrom.getText().toString();
        String[] cutMonthFromRest = dataFromCurrentString.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

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
                dateTo.setText(date);
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
        String[] cutMonthFromRest = dataToCurrentString.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

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

        timeFrom = (TextView) findViewById(R.id.add_remainder_time_from);

        // reading current time written in text view
        String timeFromCurrentString = timeFrom.getText().toString();
        String[] cutHourFromRest = timeFromCurrentString.split(":");
        String hourString = cutHourFromRest[0];
        String minutesAndXm = cutHourFromRest[1];

        String[] cut2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = cut2ndPhase[0];
        String am_pm = cut2ndPhase[1];


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
                timeTo.setText(strHrsToShow);
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
        String[] cutHourFromRest = timeToCurrentString.split(":");
        String hourString = cutHourFromRest[0];
        String minutesAndXm = cutHourFromRest[1];

        String[] cut2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = cut2ndPhase[0];
        String am_pm = cut2ndPhase[1];


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

    // Function to retrieve image
    private void openGallery(int PICKER) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == PICK_IMAGE_REQUEST_EVENT && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                imageUri = resultData.getData();
                imageForEvent.setImageURI(imageUri);
                imageForEvent.invalidate();
                eImageUri = imageUri.toString();
            }

        }
        if (requestCode == PICK_IMAGE_REQUEST_PEOPLE && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                peopleImageUri = resultData.getData();
                people.setImageURI(peopleImageUri);
                people.invalidate();
                ePeopleImageUri = peopleImageUri.toString();
                imageOfPeople.setImageURI(peopleImageUri);
                imageOfPeople.invalidate();
            }

        }
        if (requestCode == PICK_IMAGE_REQUEST_PEOPLE2 && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                peopleImageUri2 = resultData.getData();
                people2.setImageURI(peopleImageUri2);
                people2.invalidate();
                ePeopleImageUri2 = peopleImageUri2.toString();
                imageOfPeople.setImageURI(peopleImageUri2);
                imageOfPeople.invalidate();


            }

        }
        if (requestCode == PICK_IMAGE_REQUEST_PEOPLE3 && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                peopleImageUri3 = resultData.getData();
                people3.setImageURI(peopleImageUri3);
                people3.invalidate();
                ePeopleImageUri3 = peopleImageUri3.toString();
                imageOfPeople.setImageURI(peopleImageUri3);
                imageOfPeople.invalidate();
            }

        }
    }

    public void notification(int day, int month, int year, int hour, int minute, String repeat) {

        String eTime;
        String pm_am;

        if (eTimeFromHour > 12) {
            eTime = String.valueOf(eTimeFromHour - 12);
            pm_am = "pm";
        } else {
            eTime = String.valueOf(eTimeFromHour);
            pm_am = "am";
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);

        notificationIntent.setAction("my.action.string");
        notificationIntent.putExtra("title", eTitle);
        notificationIntent.putExtra("description", eDescription);
        notificationIntent.putExtra("hour", eTime);
        notificationIntent.putExtra("minutes", String.valueOf(eTimeFromMinute));
        notificationIntent.putExtra("am_pm", pm_am);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        // Define pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // set() schedules an alarm
        long time = calendar.getTimeInMillis();

        switch (repeat) {
            case "Daily":
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, alarmManager.INTERVAL_DAY, pendingIntent);
                break;
            case "Weekly":
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, alarmManager.INTERVAL_DAY * 7, pendingIntent);
                break;
            case "Monthly":
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, alarmManager.INTERVAL_DAY * calendar.getActualMaximum(Calendar.DAY_OF_MONTH), pendingIntent);
                break;
        }

    }

}
