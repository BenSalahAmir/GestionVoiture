package tn.esprit.gestionuser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private Context context;
    private static final String DATABASE_NAME = "souha.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_SERVICE = "Services";



    //hotel
    public static final String _ID_SERVICE = "_id_service";
    public static final String COLUMN_SERVICE_NAME = "serviceName";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_SERVICE_PRICE = "price";

    // Creating table query
    public boolean deleteOffer(Long offerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SERVICE, "_id_service=?", new String[]{String.valueOf(offerId)}) > 0;
    }
    // UserDatabaseHelper.java


    public boolean updateOffer(int id, String name, String location, float price, String details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SERVICE_NAME, name);
        cv.put(COLUMN_SERVICE_PRICE, price);
        cv.put(COLUMN_NOTE, details);

        int result = db.update(TABLE_SERVICE, cv, "_id_service=?", new String[]{String.valueOf(id)});
        return result > 0; // true if updated successfully
    }



    public Cursor getAllOffers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SERVICE, null);
    }

    public ArrayList<Service> getAllOffers2() {
        ArrayList<Service> services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                _ID_SERVICE,
                COLUMN_SERVICE_NAME,
                COLUMN_NOTE,
                COLUMN_SERVICE_PRICE
        };

        Cursor cursor = db.query(
                TABLE_SERVICE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long offerIdColumnIndex = cursor.getColumnIndex(_ID_SERVICE);
                long offerId = offerIdColumnIndex != -1 ? cursor.getInt((int) offerIdColumnIndex) : -1L;

                int nameColumnIndex = cursor.getColumnIndex(COLUMN_SERVICE_NAME);
                String name = nameColumnIndex != -1 ? cursor.getString(nameColumnIndex) : "";


                int detailsColumnIndex = cursor.getColumnIndex(COLUMN_NOTE);
                String details = detailsColumnIndex != -1 ? cursor.getString(detailsColumnIndex) : "";

                int priceColumnIndex = cursor.getColumnIndex(COLUMN_SERVICE_PRICE);
                float price = priceColumnIndex != -1 ? cursor.getFloat(priceColumnIndex) : -1;

                Service service = new Service(offerId, name, details, price);
                services.add(service);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return services;
    }

    public Cursor getOfferById(long offerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SERVICE, null, _ID_SERVICE + "=?", new String[]{String.valueOf(offerId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    void addOffer(String name, String details, String location, Float price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SERVICE_NAME, name);
        cv.put(COLUMN_NOTE, details);
        cv.put(COLUMN_SERVICE_PRICE, price);
        long result = db.insert(TABLE_SERVICE,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean addOffer2(String name, String details, float price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_NAME, name);
        values.put(COLUMN_NOTE, details);
        values.put(COLUMN_SERVICE_PRICE, price);

        // Inserting Row
        long rowId = db.insert(TABLE_SERVICE, null, values);
        db.close(); // Closing database connection

        return rowId != -1; // Return true if insert is successful
    }

    private static final String CREATE_TABLE_OFFERS = "CREATE TABLE " + TABLE_SERVICE + "(" +
            _ID_SERVICE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SERVICE_NAME + " TEXT NOT NULL, " +
            COLUMN_NOTE + " TEXT NOT NULL, " +
            COLUMN_SERVICE_PRICE + " REAL NOT NULL);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // Set the context member variable
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OFFERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);
        onCreate(db);
    }
















}
