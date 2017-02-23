package io.ray.hexis.util;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import io.ray.hexis.util.sql.GoalsContract.GoalsEntry;

public class ReadGoal {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;

    private String queryGoalTitle = "SELECT * FROM "
        + GoalsEntry.TABLE_NAME
        + " WHERE "
        + GoalsEntry.COLUMN_NAME_GOAL_TITLE
        + " = \"";

    private String queryGoalID =
        "SELECT * FROM " + GoalsEntry.TABLE_NAME + " WHERE " + GoalsEntry.COLUMN_NAME_ID + " = ";

    /**
     * @param sqLiteHelper
     */
    public ReadGoal(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    /**
     * @return true if a goal with matching title is found in table
     */
    public boolean doesGoalExist(String goalTitle) {
        // Sanitize goalTitle string before sql query
        goalTitle = DatabaseUtils.sqlEscapeString(goalTitle);
        // Query database for existence of a matching goal title
        Cursor cursor = db.rawQuery(queryGoalTitle + goalTitle + "\"", null);
        // return true if at least one instance of the goal title exists
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * @return true if goal with matching ID is found in table
     */
    public boolean doesGoalExist(int goalID) {
        // Query database for existence of a matching goal ID
        Cursor cursor = db.rawQuery(queryGoalID + goalID, null);
        // return true if at least one instance of the goal title exists
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * @return title of Goal
     */
    public String getGoalTitle(int goalID) {
        // Query database for existence of a matching goal ID
        Cursor cursor = db.rawQuery(queryGoalID + goalID, null);
        // goalID is unique so only one goal should be returned
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            return cursor.getString(
                cursor.getColumnIndexOrThrow(GoalsEntry.COLUMN_NAME_GOAL_TITLE));
        }
        cursor.close();
        return "null";
    }
}
