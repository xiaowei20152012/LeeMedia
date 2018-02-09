// Generated code from Butter Knife. Do not modify!
package com.umedia.android.ui.activities.bugreport;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.umedia.android.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BugReportActivity_ViewBinding implements Unbinder {
  private BugReportActivity target;

  @UiThread
  public BugReportActivity_ViewBinding(BugReportActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BugReportActivity_ViewBinding(BugReportActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.inputLayoutTitle = Utils.findRequiredViewAsType(source, R.id.input_layout_title, "field 'inputLayoutTitle'", TextInputLayout.class);
    target.inputTitle = Utils.findRequiredViewAsType(source, R.id.input_title, "field 'inputTitle'", TextInputEditText.class);
    target.inputLayoutDescription = Utils.findRequiredViewAsType(source, R.id.input_layout_description, "field 'inputLayoutDescription'", TextInputLayout.class);
    target.inputDescription = Utils.findRequiredViewAsType(source, R.id.input_description, "field 'inputDescription'", TextInputEditText.class);
    target.textDeviceInfo = Utils.findRequiredViewAsType(source, R.id.air_textDeviceInfo, "field 'textDeviceInfo'", TextView.class);
    target.inputLayoutUsername = Utils.findRequiredViewAsType(source, R.id.input_layout_username, "field 'inputLayoutUsername'", TextInputLayout.class);
    target.inputUsername = Utils.findRequiredViewAsType(source, R.id.input_username, "field 'inputUsername'", TextInputEditText.class);
    target.inputLayoutPassword = Utils.findRequiredViewAsType(source, R.id.input_layout_password, "field 'inputLayoutPassword'", TextInputLayout.class);
    target.inputPassword = Utils.findRequiredViewAsType(source, R.id.input_password, "field 'inputPassword'", TextInputEditText.class);
    target.optionUseAccount = Utils.findRequiredViewAsType(source, R.id.option_use_account, "field 'optionUseAccount'", RadioButton.class);
    target.optionManual = Utils.findRequiredViewAsType(source, R.id.option_anonymous, "field 'optionManual'", RadioButton.class);
    target.sendFab = Utils.findRequiredViewAsType(source, R.id.button_send, "field 'sendFab'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BugReportActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.inputLayoutTitle = null;
    target.inputTitle = null;
    target.inputLayoutDescription = null;
    target.inputDescription = null;
    target.textDeviceInfo = null;
    target.inputLayoutUsername = null;
    target.inputUsername = null;
    target.inputLayoutPassword = null;
    target.inputPassword = null;
    target.optionUseAccount = null;
    target.optionManual = null;
    target.sendFab = null;
  }
}
