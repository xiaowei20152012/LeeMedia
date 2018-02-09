// Generated code from Butter Knife. Do not modify!
package com.umedia.android.ui.activities;

import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.umedia.android.R;
import com.umedia.android.ui.activities.base.AbsSlidingMusicPanelActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlbumDetailActivity_ViewBinding extends AbsSlidingMusicPanelActivity_ViewBinding {
  private AlbumDetailActivity target;

  @UiThread
  public AlbumDetailActivity_ViewBinding(AlbumDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AlbumDetailActivity_ViewBinding(AlbumDetailActivity target, View source) {
    super(target, source);

    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.list, "field 'recyclerView'", ObservableRecyclerView.class);
    target.albumArtImageView = Utils.findRequiredViewAsType(source, R.id.image, "field 'albumArtImageView'", ImageView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.albumTitleView = Utils.findRequiredViewAsType(source, R.id.title, "field 'albumTitleView'", TextView.class);
    target.songsBackgroundView = Utils.findRequiredView(source, R.id.list_background, "field 'songsBackgroundView'");
  }

  @Override
  public void unbind() {
    AlbumDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.albumArtImageView = null;
    target.toolbar = null;
    target.albumTitleView = null;
    target.songsBackgroundView = null;

    super.unbind();
  }
}
