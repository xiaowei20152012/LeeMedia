// Generated code from Butter Knife. Do not modify!
package com.umedia.android.ui.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.umedia.android.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutActivity_ViewBinding implements Unbinder {
  private AboutActivity target;

  @UiThread
  public AboutActivity_ViewBinding(AboutActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutActivity_ViewBinding(AboutActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.appVersion = Utils.findRequiredViewAsType(source, R.id.app_version, "field 'appVersion'", TextView.class);
    target.changelog = Utils.findRequiredViewAsType(source, R.id.changelog, "field 'changelog'", LinearLayout.class);
    target.intro = Utils.findRequiredViewAsType(source, R.id.intro, "field 'intro'", LinearLayout.class);
    target.licenses = Utils.findRequiredViewAsType(source, R.id.licenses, "field 'licenses'", LinearLayout.class);
    target.writeAnEmail = Utils.findRequiredViewAsType(source, R.id.write_an_email, "field 'writeAnEmail'", LinearLayout.class);
    target.addToGooglePlusCircles = Utils.findRequiredViewAsType(source, R.id.add_to_google_plus_circles, "field 'addToGooglePlusCircles'", LinearLayout.class);
    target.followOnTwitter = Utils.findRequiredViewAsType(source, R.id.follow_on_twitter, "field 'followOnTwitter'", LinearLayout.class);
    target.forkOnGitHub = Utils.findRequiredViewAsType(source, R.id.fork_on_github, "field 'forkOnGitHub'", LinearLayout.class);
    target.visitWebsite = Utils.findRequiredViewAsType(source, R.id.visit_website, "field 'visitWebsite'", LinearLayout.class);
    target.reportBugs = Utils.findRequiredViewAsType(source, R.id.report_bugs, "field 'reportBugs'", LinearLayout.class);
    target.joinGooglePlusCommunity = Utils.findRequiredViewAsType(source, R.id.join_google_plus_community, "field 'joinGooglePlusCommunity'", LinearLayout.class);
    target.translate = Utils.findRequiredViewAsType(source, R.id.translate, "field 'translate'", LinearLayout.class);
    target.donate = Utils.findRequiredViewAsType(source, R.id.donate, "field 'donate'", LinearLayout.class);
    target.rateOnGooglePlay = Utils.findRequiredViewAsType(source, R.id.rate_on_google_play, "field 'rateOnGooglePlay'", LinearLayout.class);
    target.aidanFollestadGooglePlus = Utils.findRequiredViewAsType(source, R.id.aidan_follestad_google_plus, "field 'aidanFollestadGooglePlus'", AppCompatButton.class);
    target.aidanFollestadGitHub = Utils.findRequiredViewAsType(source, R.id.aidan_follestad_git_hub, "field 'aidanFollestadGitHub'", AppCompatButton.class);
    target.michaelCookGooglePlus = Utils.findRequiredViewAsType(source, R.id.michael_cook_google_plus, "field 'michaelCookGooglePlus'", AppCompatButton.class);
    target.michaelCookWebsite = Utils.findRequiredViewAsType(source, R.id.michael_cook_website, "field 'michaelCookWebsite'", AppCompatButton.class);
    target.maartenCorpelGooglePlus = Utils.findRequiredViewAsType(source, R.id.maarten_corpel_google_plus, "field 'maartenCorpelGooglePlus'", AppCompatButton.class);
    target.aleksandarTesicGooglePlus = Utils.findRequiredViewAsType(source, R.id.aleksandar_tesic_google_plus, "field 'aleksandarTesicGooglePlus'", AppCompatButton.class);
    target.eugeneCheungGitHub = Utils.findRequiredViewAsType(source, R.id.eugene_cheung_git_hub, "field 'eugeneCheungGitHub'", AppCompatButton.class);
    target.eugeneCheungWebsite = Utils.findRequiredViewAsType(source, R.id.eugene_cheung_website, "field 'eugeneCheungWebsite'", AppCompatButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.appVersion = null;
    target.changelog = null;
    target.intro = null;
    target.licenses = null;
    target.writeAnEmail = null;
    target.addToGooglePlusCircles = null;
    target.followOnTwitter = null;
    target.forkOnGitHub = null;
    target.visitWebsite = null;
    target.reportBugs = null;
    target.joinGooglePlusCommunity = null;
    target.translate = null;
    target.donate = null;
    target.rateOnGooglePlay = null;
    target.aidanFollestadGooglePlus = null;
    target.aidanFollestadGitHub = null;
    target.michaelCookGooglePlus = null;
    target.michaelCookWebsite = null;
    target.maartenCorpelGooglePlus = null;
    target.aleksandarTesicGooglePlus = null;
    target.eugeneCheungGitHub = null;
    target.eugeneCheungWebsite = null;
  }
}
