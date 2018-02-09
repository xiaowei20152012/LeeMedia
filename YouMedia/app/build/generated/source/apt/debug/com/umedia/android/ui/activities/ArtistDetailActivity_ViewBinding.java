// Generated code from Butter Knife. Do not modify!
package com.umedia.android.ui.activities;

import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.umedia.android.R;
import com.umedia.android.ui.activities.base.AbsSlidingMusicPanelActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArtistDetailActivity_ViewBinding extends AbsSlidingMusicPanelActivity_ViewBinding {
  private ArtistDetailActivity target;

  @UiThread
  public ArtistDetailActivity_ViewBinding(ArtistDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ArtistDetailActivity_ViewBinding(ArtistDetailActivity target, View source) {
    super(target, source);

    this.target = target;

    target.artistImage = Utils.findRequiredViewAsType(source, R.id.image, "field 'artistImage'", ImageView.class);
    target.songListBackground = Utils.findRequiredView(source, R.id.list_background, "field 'songListBackground'");
    target.songListView = Utils.findRequiredViewAsType(source, R.id.list, "field 'songListView'", ObservableListView.class);
    target.artistName = Utils.findRequiredViewAsType(source, R.id.title, "field 'artistName'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  public void unbind() {
    ArtistDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.artistImage = null;
    target.songListBackground = null;
    target.songListView = null;
    target.artistName = null;
    target.toolbar = null;

    super.unbind();
  }
}
