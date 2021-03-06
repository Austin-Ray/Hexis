package io.ray.hexis.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.AddItemOnClickListener;
import io.ray.hexis.presenter.QuadrantViewAdapter;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;
import io.ray.hexis.view.abs.IQuadrantFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Viewer class that displays one quadrant of a time matrix.
 */
public class QuadrantFragment extends Fragment implements IQuadrantFragment,
    QuadrantViewAdapter.Listener {

  @BindView(R.id.quadrant_list)
  RecyclerView quadRecView;

  private QuadrantViewAdapter quadrantViewAdapter;
  private IQuadrantPresenter presenter;

  /**
   * Create a new instance of a QuadrantFragment.
   *
   * @return New instance of a Quadrant Fragment
   */
  public static IQuadrantFragment newInstance() {
    return new QuadrantFragment();
  }

  @Override
  public void onStart() {
    super.onStart();

    if (presenter != null) {
      presenter.updateFragment();
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    // Create the root view by inflating the fragment_quadrant layout
    View root = inflater.inflate(R.layout.fragment_quadrant, container, false);

    // Inject the views
    ButterKnife.bind(this, root);

    // Set the layout manager
    quadRecView.setLayoutManager(new LinearLayoutManager(getContext()));

    // Initialize the ViewAdapter based on if there's a data set or not
    if (savedInstanceState == null) {
      quadrantViewAdapter = QuadrantViewAdapter.newInstance(this);
    } else {
      List<QuadrantItem> items = savedInstanceState.getParcelableArrayList("data");
      quadrantViewAdapter = QuadrantViewAdapter.newInstance(this, items);
    }

    quadRecView.setAdapter(quadrantViewAdapter);

    return root;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    // Put the data set into the OutState
    outState.putParcelableArrayList("data",
        new ArrayList<Parcelable>(quadrantViewAdapter.getData()));
  }

  /**
   * Set the data for the ViewAdapter.
   *
   * @param data Data
   */
  @Override
  public void setData(List<QuadrantItem> data) {
    quadrantViewAdapter.setData(data);
  }

  /**
   * Return a fragment reference of this object.
   *
   * @return This, but every time its called, it's a fragment
   */
  @Override
  public Fragment toFragment() {
    return this;
  }

  @Override
  public IQuadrantPresenter getPresenter() {
    return presenter;
  }

  /**
   * Set the Presenter object for this class.
   *
   * @param presenter Presenter reference
   */
  @Override
  public void setPresenter(IQuadrantPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void onItemLongClick(QuadrantItem item) {
    FragmentManager manager = getActivity().getSupportFragmentManager();

    // Get a new instance of the AddItemDialogFragment
    DialogFragment dialog =
        EditItemDialogFragment.newInstance(item,
            new AddItemOnClickListener(presenter.getMatrixPresenter().getPager()),
            getString(R.string.edit_item));

    // Show the dialog and set its tag.
    dialog.show(manager, "Edit Item");
  }

  @Override
  public void onItemClick(QuadrantItem item, QuadrantItemViewHolder vh) {
    // Completion status set to 0 for incomplete and 1 for completed
    vh.clickCheck();
    item.setCompletion(vh.isChecked());
    presenter.modifyItemInModel(item);
  }
}
