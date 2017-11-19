/*
 * Copyright 2015 Blanyal D'Souza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package calendarapp.truongnh.com.calendarapp.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class ReminderDatabase extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ReminderDatabase";

    // Table name
    private static final String TABLE_REMINDERS = "ReminderTable";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_REPEAT_NO = "repeat_no";
    private static final String KEY_REPEAT_TYPE = "repeat_type";
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_TYPE_REMINDER = "type_remider";
    private static final String KEY_CAL_PERSON = "cal_person";
    private static final String KEY_CAL_NOTE = "cal_note";

    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY_OF_MONTH = "dayofmonth";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_SECOND = "second";
    public ReminderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER,"
                + KEY_REPEAT + " BOOLEAN,"
                + KEY_REPEAT_NO + " INTEGER,"
                + KEY_REPEAT_TYPE + " TEXT,"
                + KEY_ACTIVE + " BOOLEAN,"
                + KEY_END_DATE + " TEXT,"
                + KEY_END_TIME + " TEXT,"
                + KEY_TYPE_REMINDER + " TEXT,"
                + KEY_CAL_PERSON + " TEXT,"
                + KEY_CAL_NOTE + " TEXT,"
                + KEY_YEAR + " INTEGER,"
                + KEY_MONTH + " INTEGER,"
                + KEY_DAY_OF_MONTH + " INTEGER,"
                + KEY_HOUR + " INTEGER,"
                + KEY_MINUTE + " INTEGER,"
                + KEY_SECOND + " INTEGER" + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        // Create tables again
        onCreate(db);
    }

    // Adding new Reminder
    public int addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE , reminder.getTitle());
        values.put(KEY_DATE , reminder.getDateFrom());
        values.put(KEY_TIME , reminder.getTimeFrom());
        values.put(KEY_REPEAT , reminder.getRepeat());
        values.put(KEY_REPEAT_NO , reminder.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(KEY_ACTIVE, reminder.getActive());
        values.put(KEY_END_DATE, reminder.getDateTo());
        values.put(KEY_END_TIME, reminder.getTimeTo());
        values.put(KEY_TYPE_REMINDER, reminder.getTypeReminder());
        values.put(KEY_CAL_PERSON, reminder.getCalPerson());
        values.put(KEY_CAL_NOTE, reminder.getCalNote());
        values.put(KEY_YEAR, reminder.getYear());
        values.put(KEY_MONTH, reminder.getMonth());
        values.put(KEY_DAY_OF_MONTH, reminder.getDayOfMonth());
        values.put(KEY_HOUR, reminder.getHour());
        values.put(KEY_MINUTE, reminder.getMinute());
        values.put(KEY_SECOND, reminder.getSecond());
        // Inserting Row
        long ID = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return (int) ID;
    }

    // Getting single Reminder
    public Reminder getReminder(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_DATE,
                                KEY_TIME,
                                KEY_REPEAT,
                                KEY_REPEAT_NO,
                                KEY_REPEAT_TYPE,
                                KEY_ACTIVE,
                                KEY_END_DATE,
                                KEY_END_TIME,
                                KEY_TYPE_REMINDER,
                                KEY_CAL_PERSON,
                                KEY_CAL_NOTE,
                                KEY_YEAR,
                                KEY_MONTH,
                                KEY_DAY_OF_MONTH,
                                KEY_HOUR,
                                KEY_MINUTE,
                                KEY_SECOND
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

//        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
//                cursor.getString(2), cursor.getString(3), cursor.getString(4),
//                cursor.getString(5), cursor.getString(6), cursor.getString(7),
//                cursor.getString(8), cursor.getString(9), cursor.getString(10),
//                cursor.getString(11), cursor.getString(12));

        Reminder reminder = new Reminder();
        reminder.setId(Integer.parseInt(cursor.getString(0)));
        reminder.setTitle(cursor.getString(1));
        reminder.setDateFrom(cursor.getString(2));
        reminder.setTimeFrom(cursor.getString(3));
        reminder.setRepeat(cursor.getString(4));
        reminder.setRepeatNo(cursor.getString(5));
        reminder.setRepeatType(cursor.getString(6));
        reminder.setActive(cursor.getString(7));
        reminder.setDateTo(cursor.getString(8));
        reminder.setTimeTo(cursor.getString(9));
        reminder.setTypeReminder(cursor.getString(10));
        reminder.setCalPerson(cursor.getString(11));
        reminder.setCalNote(cursor.getString(12));
        reminder.setYear(Integer.parseInt(cursor.getString(13)));
        reminder.setMonth(Integer.parseInt(cursor.getString(14)));
        reminder.setDayOfMonth(Integer.parseInt(cursor.getString(15)));
        reminder.setHour(Integer.parseInt(cursor.getString(16)));
        reminder.setMinute(Integer.parseInt(cursor.getString(17)));
        reminder.setSecond(Integer.parseInt(cursor.getString(18)));

        return reminder;
    }

    // Getting all Reminders
    public List<Reminder> getAllReminders(){
        List<Reminder> reminderList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDateFrom(cursor.getString(2));
                reminder.setTimeFrom(cursor.getString(3));
                reminder.setRepeat(cursor.getString(4));
                reminder.setRepeatNo(cursor.getString(5));
                reminder.setRepeatType(cursor.getString(6));
                reminder.setActive(cursor.getString(7));
                reminder.setDateTo(cursor.getString(8));
                reminder.setTimeTo(cursor.getString(9));
                reminder.setTypeReminder(cursor.getString(10));
                reminder.setCalPerson(cursor.getString(11));
                reminder.setCalNote(cursor.getString(12));
                reminder.setYear(Integer.parseInt(cursor.getString(13)));
                reminder.setMonth(Integer.parseInt(cursor.getString(14)));
                reminder.setDayOfMonth(Integer.parseInt(cursor.getString(15)));
                reminder.setHour(Integer.parseInt(cursor.getString(16)));
                reminder.setMinute(Integer.parseInt(cursor.getString(17)));
                reminder.setSecond(Integer.parseInt(cursor.getString(18)));
                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }

    public List<Reminder> getAllReminders(String date){
        List<Reminder> reminderList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS + " WHERE " + KEY_DATE + "='" + date + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Reminder reminder = new Reminder();
                reminder.setId(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDateFrom(cursor.getString(2));
                reminder.setTimeFrom(cursor.getString(3));
                reminder.setRepeat(cursor.getString(4));
                reminder.setRepeatNo(cursor.getString(5));
                reminder.setRepeatType(cursor.getString(6));
                reminder.setActive(cursor.getString(7));
                reminder.setDateTo(cursor.getString(8));
                reminder.setTimeTo(cursor.getString(9));
                reminder.setTypeReminder(cursor.getString(10));
                reminder.setCalPerson(cursor.getString(11));
                reminder.setCalNote(cursor.getString(12));

                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }

    // Getting Reminders Count
    public int getRemindersCount() {
        String countQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    // Updating single Reminder
    public int updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE , reminder.getTitle());
        values.put(KEY_DATE , reminder.getDateFrom());
        values.put(KEY_TIME , reminder.getTimeFrom());
        values.put(KEY_REPEAT , reminder.getRepeat());
        values.put(KEY_REPEAT_NO , reminder.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(KEY_ACTIVE, reminder.getActive());
        values.put(KEY_END_DATE, reminder.getDateTo());
        values.put(KEY_END_TIME, reminder.getTimeTo());
        values.put(KEY_TYPE_REMINDER, reminder.getTypeReminder());
        values.put(KEY_CAL_PERSON, reminder.getCalPerson());
        values.put(KEY_CAL_NOTE, reminder.getCalNote());
        values.put(KEY_YEAR, reminder.getYear());
        values.put(KEY_MONTH, reminder.getMonth());
        values.put(KEY_DAY_OF_MONTH, reminder.getDayOfMonth());
        values.put(KEY_HOUR, reminder.getHour());
        values.put(KEY_MINUTE, reminder.getMinute());
        values.put(KEY_SECOND, reminder.getSecond());
        // Updating row
        return db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getId())});
    }

    // Deleting single Reminder
    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getId())});
        db.close();
    }
}
