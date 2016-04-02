package edu.whu.shellshock;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
/*
 * database of user information
 */
public class UserProvider extends ContentProvider{
	
	private static final String AUTHORITY = "shellshock.user";
	private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/");
	private static final String DATABASE_NAME = "user.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "user";
	
	private static final String VOL_ID = "id";
	private static final String VOL_NAME = "name";
	private static final String VOL_EMAIL = "email";
	private static final String VOL_PASSWORD = "password";
	private static final String VOL_LEVEVL = "level";
	private static final String VOL_EXP = "exp";
	
	private DatabaseHelper mDatabaseHelper;
	
	@Override
	public boolean onCreate() {
		mDatabaseHelper = new DatabaseHelper(getContext(), DATABASE_NAME,
				null, DATABASE_VERSION);
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, projection, selection,
				selectionArgs, null, null, sortOrder);
		return cursor;
	}
	@Override
	public String getType(Uri uri) {
		
		return null;
	}
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_NAME, null, values);
		if(rowId > 0){
			uri = ContentUris.withAppendedId(uri, rowId);
			sendNotify(uri);
			return uri;
		}
		throw new SQLException("Failed to insert row into :" + uri);
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		int count = db.delete(TABLE_NAME, selection, selectionArgs);
		if(count > 0){
			sendNotify(uri);
		}
		return count;
	}
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		int count = db.update(TABLE_NAME, values, selection, selectionArgs);
		sendNotify(uri);
		return count;
	}
	
	private void sendNotify(Uri uri){
		getContext().getContentResolver().notifyChange(uri, null);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);	
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS"
					+ TABLE_NAME
					+ "(id INTEGER PRIMARY KEY, name TEXT, email TEXT,"
					+" password TEXT, level INTEGER, exp INTEGER)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
			onCreate(db);
		}		
	}
	
	public static UserInfo getUserFromName(Context context, String username){
		UserInfo user = null;
		Cursor cursor = context.getContentResolver().query(
				CONTENT_URI, 
				new String[] {VOL_ID + "as _id" + VOL_NAME,
				VOL_PASSWORD, VOL_LEVEVL, VOL_EXP, VOL_EMAIL},
				"NAME=?", new String[] { username }, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				user = new UserInfo();
				user.setId(cursor.getInt(cursor.getColumnIndex(VOL_ID)));
				user.setEmail(cursor.getString(cursor.getColumnIndex(VOL_EMAIL)));
				user.setName(cursor.getString(cursor.getColumnIndex(TABLE_NAME)));
				user.setPassword(cursor.getString(cursor.getColumnIndex(VOL_PASSWORD)));
				user.setLevel(cursor.getInt(cursor.getColumnIndex(VOL_LEVEVL)));
				user.setExp(cursor.getInt(cursor.getColumnIndex(VOL_EXP)));			
			}
			cursor.close();
		}
		return user;
	}
	public static void addUser(Context context, String username, String password, String email){
		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(TABLE_NAME, username);
		values.put(VOL_EMAIL, email);
		values.put(VOL_PASSWORD, password);
		cr.insert(CONTENT_URI, values);
		return;
	}
}
