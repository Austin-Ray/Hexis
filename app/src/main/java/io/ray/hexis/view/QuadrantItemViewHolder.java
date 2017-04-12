package io.ray.hexis.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.ray.hexis.R;

/**
 * Placeholder ViewHolder for Quadrant items.
 */
public class QuadrantItemViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.quadrant_item_text)
  CheckedTextView textView;

  /**
   * Default constructor.
   *
   * @param itemView View
   */
  public QuadrantItemViewHolder(View itemView) {
    super(itemView);

    // Bind views
    ButterKnife.bind(this, itemView);
  }

  /**
   * Set the text view message.
   *
   * @param message Message
   */
  public void setTextView(String message) {
    textView.setText(message);
  }

  /**
   * Set the check value of the item
   *
   * @param checked 0 if not completed 1 if completed
   */
  public void setCheck(int checked) {

    if(checked == 0)
      textView.setChecked(false);
    else
      textView.setChecked(true);
  }
}
