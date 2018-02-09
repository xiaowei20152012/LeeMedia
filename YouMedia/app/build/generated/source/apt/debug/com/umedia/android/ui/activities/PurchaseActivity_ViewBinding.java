// Generated code from Butter Knife. Do not modify!
package com.umedia.android.ui.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.umedia.android.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PurchaseActivity_ViewBinding implements Unbinder {
  private PurchaseActivity target;

  @UiThread
  public PurchaseActivity_ViewBinding(PurchaseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PurchaseActivity_ViewBinding(PurchaseActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.restoreButton = Utils.findRequiredViewAsType(source, R.id.restore_button, "field 'restoreButton'", Button.class);
    target.purchaseButton = Utils.findRequiredViewAsType(source, R.id.purchase_button, "field 'purchaseButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PurchaseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.restoreButton = null;
    target.purchaseButton = null;
  }
}
