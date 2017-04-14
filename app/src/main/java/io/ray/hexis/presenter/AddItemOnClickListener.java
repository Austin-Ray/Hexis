package io.ray.hexis.presenter;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;
import io.ray.hexis.presenter.abs.ModifyItemListener;
import io.ray.hexis.util.QuadrantItemWriter;
import io.ray.hexis.util.SqlLiteHelper;
import io.ray.hexis.view.AddItemDialogFragment;
import io.ray.hexis.view.abs.IQuadrantFragment;

/**
 * Listener for the FloatingActionButton, and the DialogFragment is spawns.
 */
public class AddItemOnClickListener implements FloatingActionButton.OnClickListener,
    ModifyItemListener {

  private final ViewPager pager;
  private final SqlLiteHelper sqlLiteHelper;

  /**
   * Parameterized constructor that passed a ViewPager object in order to operate.
   * @param pager     ViewPager
   */
  public AddItemOnClickListener(ViewPager pager) {
    this.pager = pager;
    this.sqlLiteHelper = new SqlLiteHelper(pager.getContext());
    //pager.setOffscreenPageLimit(this.pager.getAdapter().getCount());
  }

  /**
   * On clicking the FloatingActionButton.
   * @param v     View being clicked
   */
  @Override
  public void onClick(View v) {
    // Get the current fragment being displayed
    IQuadrantFragment currentFragment = getCurrentFragment();

    // Get a new instance of the AddItemDialogFragment
    DialogFragment dialog = AddItemDialogFragment.newInstance(this,
        ((Fragment) currentFragment).getString(R.string.add_item));

    // Show the dialog fragment
    dialog.show(((Fragment)currentFragment).getFragmentManager(), "Add Item");
  }

  /**
   * Adds a new QuadrantItem to the current QuadrantFragment.
   * @param message   Message that will be used to construct a QuadrantItem
   */
  @Override
  public void addItem(String message) {
    // Get the current fragment
    IQuadrantFragment currentFragment = getCurrentFragment();

    // Add a new QuadrantItem to the fragment
    currentFragment.getPresenter().addItem(message);
  }

  /**
   * Add an item to a quadrant with specified message.
   * @param message   Message that will be used to construct a QuadrantItem
   * @param quadrantId the quadrant id
   */
  @Override
  public void addItem(String message, int quadrantId) {
    IQuadrantFragment specificFragment = getFragment(quadrantId);

    // Initialize QuadrantItemWriter to access database
    QuadrantItemWriter writeQuadrantItems = new QuadrantItemWriter(sqlLiteHelper);

    // Insert new item into QuadrantItems table
    long itemUid = writeQuadrantItems.insertNewItem(0, quadrantId, message);

    // The item through the presenter.
    specificFragment.getPresenter().addItem(message, itemUid);
  }

  /**
   * Get the current quadrant id.
   * @return quadrant id of current quadrant
   */
  @Override
  public int getQuadrant() {
    return pager.getCurrentItem();
  }

  /**
   * Get the currently display fragment in the ViewPager.
   * @return  Fragment being displayed
   */
  private IQuadrantFragment getCurrentFragment() {
    return getFragment(pager.getCurrentItem());
  }

  /**
   * Return a QuadrantFragment based on an ID.
   * @param quadrantId The requested quadrant fragment
   * @return Fragment that holds the requested quadrant
   */
  private IQuadrantFragment getFragment(int quadrantId) {
    // Get the adapter used by the ViewPager
    QuadrantFragmentPagerAdapter adapter = (QuadrantFragmentPagerAdapter) pager.getAdapter();

    // Return the item.
    return (IQuadrantFragment)adapter.getItem(quadrantId);
  }

  public void updateItem(QuadrantItem item, int quadrant) {
    QuadrantItemWriter writer = new QuadrantItemWriter(sqlLiteHelper);
    writer.updateItem(item, quadrant, -1);

    if (quadrant != -1) {
      IQuadrantFragment currentFragment = getCurrentFragment();
      currentFragment.getPresenter().removeItemFromModel(item);
      currentFragment.getPresenter().updateFragment();
    }

    IQuadrantFragment specificFragment = quadrant == -1 ? getCurrentFragment() :
        getFragment(quadrant);
    specificFragment.getPresenter().updateFragment();

    IQuadrantPresenter presenter = specificFragment.getPresenter();
    presenter.modifyItemInModel(item);
    presenter.updateFragment();
  }

  @Override
  public void updateItem(QuadrantItem item) {
    updateItem(item, -1);
  }

  @Override
  public void removeItem(QuadrantItem item) {
    QuadrantItemWriter writer = new QuadrantItemWriter(sqlLiteHelper);
    writer.removeItem(item);

    IQuadrantPresenter presenter = getCurrentFragment().getPresenter();
    presenter.removeItemFromModel(item);
    presenter.updateFragment();
  }
}
