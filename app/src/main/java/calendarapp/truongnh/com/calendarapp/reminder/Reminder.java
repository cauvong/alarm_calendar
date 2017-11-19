package calendarapp.truongnh.com.calendarapp.reminder;

import android.os.Parcel;
import android.os.Parcelable;

// Reminder class
public class Reminder implements Parcelable {
    private int id;
    private String title;
    private String dateFrom;
    private String timeFrom;
    private String repeat;
    private String repeatNo;
    private String repeatType;
    private String active;
    private String dateTo;
    private String timeTo;
    private String typeReminder;
    private String calPerson;
    private String calNote;

    private int month;
    private int year;
    private int dayOfMonth;
    private int hour;
    private int minute;
    private int second;

    public Reminder() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getRepeatNo() {
        return repeatNo;
    }

    public void setRepeatNo(String repeatNo) {
        this.repeatNo = repeatNo;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getTypeReminder() {
        return typeReminder;
    }

    public void setTypeReminder(String typeReminder) {
        this.typeReminder = typeReminder;
    }

    public void setCalPerson(String calPerson) {
        this.calPerson = calPerson;
    }

    public String getCalPerson() {
        return calPerson;
    }

    public void setCalNote(String calNote) {
        this.calNote = calNote;
    }

    public String getCalNote() {
        return calNote;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    protected Reminder(Parcel in) {
        id = in.readInt();
        title = in.readString();
        dateFrom = in.readString();
        timeFrom = in.readString();
        repeat = in.readString();
        repeatNo = in.readString();
        repeatType = in.readString();
        active = in.readString();
        dateTo = in.readString();
        timeTo = in.readString();
        typeReminder = in.readString();
        calPerson = in.readString();
        calNote = in.readString();
        month = in.readInt();
        year = in.readInt();
        dayOfMonth = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        second = in.readInt();
    }

    public Reminder(int ID, String Title, String Date, String Time, String Repeat, String RepeatNo, String RepeatType, String Active, String endDate, String endTime, String typeReminder, String _calPerson, String _calNote){
        id = ID;
        title = Title;
        dateFrom = Date;
        timeFrom = Time;
        repeat = Repeat;
        repeatNo = RepeatNo;
        repeatType = RepeatType;
        active = Active;
        dateTo = endDate;
        timeTo = endTime;
        this.typeReminder = typeReminder;
        this.calPerson = _calPerson;
        this.calNote = _calNote;
    }

    public Reminder(String Title, String Date, String Time, String Repeat, String RepeatNo, String RepeatType, String Active, String endDate, String endTime, String typeReminder, String _calPerson, String _calNote){
        title = Title;
        dateFrom = Date;
        timeFrom = Time;
        repeat = Repeat;
        repeatNo = RepeatNo;
        repeatType = RepeatType;
        active = Active;
        dateTo = endDate;
        timeTo = endTime;
        this.typeReminder = typeReminder;
        this.calPerson = _calPerson;
        this.calNote = _calNote;
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(dateFrom);
        dest.writeString(timeFrom);
        dest.writeString(repeat);
        dest.writeString(repeatNo);
        dest.writeString(repeatType);
        dest.writeString(active);
        dest.writeString(dateTo);
        dest.writeString(timeTo);
        dest.writeString(typeReminder);
        dest.writeString(calPerson);
        dest.writeString(calNote);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeInt(dayOfMonth);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeInt(second);
    }
}
