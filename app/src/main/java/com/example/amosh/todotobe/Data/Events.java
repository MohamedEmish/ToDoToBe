package com.example.amosh.todotobe.Data;

public class Events {

    private final String mTitle;
    private final String mDescription;
    private final int mDateFromDay;
    private final int mDateFromMonth;
    private final int mDateFromYear;
    private final int mDateToDay;
    private final int mDateToMonth;
    private final int mDateToYear;
    private final int mTimeFromHour;
    private final int mTimeFromMinutes;
    private final int mTimeToHour;
    private final int mTimeToMinutes;
    private final String mLocation;
    private final String mNotification;
    private final String mPeople;
    private final String mRepeat;
    private final String mImage;


    public Events(String title, String description, int dateFromDay, int dateFromMonth, int dateFromYear, int dateToDay, int dateToMonth, int dateToYear,
                  int timeFromHour, int timeFromMinute, int timeToHour, int timeToMinute, String location, String notification, String people,
                  String repeat, String image) {
        mTitle = title;
        this.mDescription = description;
        this.mDateFromDay = dateFromDay;
        this.mDateFromMonth = dateFromMonth;
        this.mDateFromYear = dateFromYear;
        this.mDateToDay = dateToDay;
        this.mDateToMonth = dateToMonth;
        this.mDateToYear = dateToYear;
        this.mTimeFromHour = timeFromHour;
        this.mTimeFromMinutes = timeFromMinute;
        this.mTimeToHour = timeToHour;
        this.mTimeToMinutes = timeToMinute;
        this.mLocation = location;
        this.mNotification = notification;
        this.mPeople = people;
        this.mRepeat = repeat;
        this.mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getNotification() {
        return mNotification;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getPeople() {
        return mPeople;
    }

    public String getRepeat() {
        return mRepeat;
    }

    public String getImage() {
        return mImage;
    }

    public int getDateFromDay() {
        return mDateFromDay;
    }

    public int getDateFromMonth() {
        return mDateFromMonth;
    }

    public int getDateFromYear() {
        return mDateFromYear;
    }

    public int getDateToDay() {
        return mDateToDay;
    }

    public int getDateToMonth() {
        return mDateToMonth;
    }

    public int getDateToYear() {
        return mDateToYear;
    }

    public int getTimeFromHour() {
        return mTimeFromHour;
    }

    public int getTimeFromMinutes() {
        return mTimeFromMinutes;
    }

    public int getTimeToHour() {
        return mTimeToHour;
    }

    public int getTimeToMinutes() {
        return mTimeToMinutes;
    }

}
