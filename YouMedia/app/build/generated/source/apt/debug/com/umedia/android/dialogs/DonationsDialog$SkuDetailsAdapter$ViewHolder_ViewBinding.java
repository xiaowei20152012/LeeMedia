// Generated code from Butter Knife. Do not modify!
package com.umedia.android.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.umedia.android.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DonationsDialog$SkuDetailsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private DonationsDialog.SkuDetailsAdapter.ViewHolder target;

  @UiThread
  public DonationsDialog$SkuDetailsAdapter$ViewHolder_ViewBinding(DonationsDialog.SkuDetailsAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
    target.price = Utils.findRequiredViewAsType(source, R.id.price, "field 'price'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DonationsDialog.SkuDetailsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.title = null;
    target.text = null;
    target.price = null;
  }
}
