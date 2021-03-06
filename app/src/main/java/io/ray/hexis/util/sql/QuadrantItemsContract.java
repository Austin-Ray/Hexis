package io.ray.hexis.util.sql;

import android.provider.BaseColumns;

public final class QuadrantItemsContract {

  // Prevent instantiation
  private QuadrantItemsContract() { }

  public static class QuadrantItemsEntry implements BaseColumns {
    public static final String TABLE_NAME = "QuadrantItems";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_GOAL_ID = "goal_id";
    public static final String COLUMN_NAME_ITEM_TEXT = "item_text";
    public static final String COLUMN_NAME_QUADRANT = "quadrant";
    public static final String COLUMN_NAME_COMPLETION_STATUS = "completion_status";
  }
}
