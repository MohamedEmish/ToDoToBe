package com.example.amosh.todotobe.Data;

public class Events {
    private final String mEventTitle;
    private final String mEventDescription;
    private final String mEventLocation;
    private final String mEventDateFrom;
    private final String mEventDateTo;
    private final String mEventTimeFrom;
    private final String mEventTimeTo;
    private final String mEventNotification;
    private final String mEventPeople;
    private final String mEventRepeat;
    private final String mEventImage;

    public Events(String title, String description, String location, String dateFrom,
                  String dateTo, String timeFrom, String timeTo, String notification, String people,
                  String repeat, String image) {
        mEventTitle = title;
        this.mEventDescription = description;
        this.mEventLocation = location;
        this.mEventDateFrom = dateFrom;
        this.mEventDateTo = dateTo;
        this.mEventTimeFrom = timeFrom;
        this.mEventTimeTo = timeTo;
        this.mEventNotification = notification;
        this.mEventPeople = people;
        this.mEventRepeat = repeat;
        this.mEventImage = image;
    }

    public String getEventTitle() {
        return mEventTitle;
    }

    public String getEventDescription() {
        return mEventDescription;
    }

    public String getEventLocation() {
        return mEventLocation;
    }

    public String getEventDateFrom() {
        return mEventDateFrom;
    }

    public String getEventDateTo() {
        return mEventDateTo;
    }

    public String getEventTimeFrom() {
        return mEventTimeFrom;
    }

    public String getEventTimeTo() {
        return mEventTimeTo;
    }

    public String getEventNotification() {
        return mEventNotification;
    }

    public String getEventPeople() {
        return mEventPeople;
    }

    public String getEventRepeat() {
        return mEventRepeat;
    }

    public String getEventImage() {
        return mEventImage;
    }

}
