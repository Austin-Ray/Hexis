package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import io.ray.hexis.util.sql.QuadrantItemsContract;

/**
 * Created by Andrew on 2/22/2017.
 */

public class WriteGoalItems {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;
    private ContentValues values;

    /**
     * @param sqLiteHelper
     */
    public WriteGoalItems(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    public long insertNewItem(int goalID, int quadrant, String itemText){
        values = new ContentValues();
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS, 0);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID, goalID);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, itemText);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT, quadrant);
        return db.insert(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, null, values);
    }
}
