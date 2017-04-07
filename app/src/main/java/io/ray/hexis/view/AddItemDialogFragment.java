package io.ray.hexis.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ray.hexis.R;
import io.ray.hexis.presenter.AddItemOnClickListener;

/**
 * DialogFragment that appears when a user adds an item to a QuadrantFragment.
 */
public class AddItemDialogFragment extends DialogFragment implements View.OnClickListener {

  @BindView(R.id.add_item) EditText newItem;

  @BindView(R.id.btn_QI) ToggleButton quadOne;
  @BindView(R.id.btn_QII) ToggleButton quadTwo;
  @BindView(R.id.btn_QIII) ToggleButton quadThree;
  @BindView(R.id.btn_QIV) ToggleButton quadFour;

  private int selectedQuadrant = 0;
  private Listener listener;

  /**
   * Listener for the AddItemDialogFragment.
   */
  public interface Listener {

    /**
     * Add a QuadrantItem to the current fragment.
     *
     * @param message Message that will be used to construct a QuadrantItem
     */
    void addItem(String message);

    /**
     * Add a QuadrantItem to the current fragment.
     *
     * @param message    Message that will be used to construct a QuadrantItem
     * @param quadrantId the quadrant id
     */
    void addItem(String message, int quadrantId);

    /**
     * Return id of current quadrant.
     * @return id of current quadrant
     */
    int getQuadrant();
  }

  /**
   * Factory method for creating the DialogFragment with a listener.
   *
   * @param listener Listener for passing back the information to create a QuadrantItem
   * @return New AddItemDialogFragment instance
   */
  public static DialogFragment newInstance(Listener listener) {
    DialogFragment dialog = new AddItemDialogFragment();

    // Set the listener
    ((AddItemDialogFragment) dialog).setListener(listener);

    // Return the dialog
    return dialog;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View v = inflater.inflate(R.layout.fragment_add_item_dialog, null);

    ButterKnife.bind(this, v);

    // Get the current quadrant
    selectedQuadrant = listener.getQuadrant();

    // Select the Quadrant
    selectQuadrant(selectedQuadrant);

    // Set the click listeners for all the ToggleButtons
    quadOne.setOnClickListener(e -> selectQuadrant(0));
    quadTwo.setOnClickListener(e -> selectQuadrant(1));
    quadThree.setOnClickListener(e -> selectQuadrant(2));
    quadFour.setOnClickListener(e -> selectQuadrant(3));

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(v)
        // Add action buttons
        .setPositiveButton("Add Item", null)
        .setNegativeButton("Cancel", (dialog, id) -> AddItemDialogFragment.this.getDialog()
            .cancel());

    Dialog dialog = builder.create();

    dialog.setOnShowListener(dialog1 -> {
      Button button = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
      button.setOnClickListener(AddItemDialogFragment.this);
    });

    return dialog;
  }

  /**
   * Select a Quadrant, and update the ToggleButtons to reflect user choice.
   *
   * @param n Selected Quadrant
   */
  private void selectQuadrant(int n) {
    // Set selectedQuadrant to what the user chose
    selectedQuadrant = n;

    // Create an array of the ToggleButtons for easy updating
    ToggleButton[] arr = {quadOne, quadTwo, quadThree, quadFour};

    // Update the ToggleButtons based on if they were selected or not
    for (int i = 0; i < arr.length; i++) {
      if (i == selectedQuadrant) {
        arr[i].setChecked(true);
      } else {
        arr[i].setChecked(false);
      }
    }
  }

  /**
   * Set the listener for the Dialog.
   *
   * @param listener Listener reference for the DialogFragment
   */
  public void setListener(Listener listener) {
    this.listener = listener;
  }

  /**
   * Get the Listener used by the AddItemDialogFragment.
   *
   * @return Listener
   */
  public Listener getListener() {
    return listener;
  }

  @Override
  public void onClick(View v) {
    if (!newItem.getText().toString().isEmpty()) {
      listener.addItem(newItem.getText().toString());
      dismiss();
    }
  }
}
