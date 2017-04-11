package io.ray.hexis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.ray.hexis.util.GoalReader;
import io.ray.hexis.util.GoalWriter;
import io.ray.hexis.util.QuadrantItemReader;
import io.ray.hexis.util.SqlLiteHelper;
import io.ray.hexis.util.sql.GoalsContract;
import io.ray.hexis.util.sql.QuadrantItemsContract;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)

public class SqlLiteHelperTest {
  private SqlLiteHelper sqlHelper;

  @Before
  public void setup() {
    // Setup the activity via Robolectric
    MainActivity activity = Robolectric.setupActivity(MainActivity.class);

    // Initialize the SQL Lite Helper
    sqlHelper = new SqlLiteHelper(activity);
  }

  @Test
  public void sqlInitializeTest() throws Exception {
    GoalsContract.GoalsEntry goalsEntry = new GoalsContract.GoalsEntry();
    QuadrantItemsContract.QuadrantItemsEntry quadrantItemsEntry = new QuadrantItemsContract.QuadrantItemsEntry();
    assertNotNull(sqlHelper);
  }

  @Test
  public void goalReaderWriterTest() throws Exception {
    GoalReader goalReader = new GoalReader(sqlHelper);
    GoalWriter goalWriter = new GoalWriter(sqlHelper);
    assertNotNull(goalReader);
    assertNotNull(goalWriter);
    goalReader.getGoalTitle(0);
    goalReader.doesGoalExist(0);
    goalReader.getGoalTitle(1);
    goalReader.doesGoalExist(1);
    goalReader.doesGoalExist("");
  }

  @Test
  public void quadrantItemRedaerTest() throws Exception {
    QuadrantItemReader quadrantItemReader = new QuadrantItemReader(sqlHelper);
    quadrantItemReader.getItemsTextByQuadrant(1, 1);
  }

}
