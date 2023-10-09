package com.aidaole.easyswiperefreshlayout.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.aidaole.easyswiperefreshlayout.EasySwipeRefreshLayout;
import com.aidaole.easyswiperefreshlayout.FixedHeaderStrategy;
import com.aidaole.easyswiperefreshlayout.R;
import com.airbnb.lottie.LottieAnimationView;

/**
 * 自定义HeaderView实例
 */
public class FixedHeaderRefreshlayout extends EasySwipeRefreshLayout
    implements EasySwipeRefreshLayout.OnScrollStateChangeListener {

  private LottieAnimationView mHeaderAnim;

  public FixedHeaderRefreshlayout(Context context) {
    this(context, null);
  }

  public FixedHeaderRefreshlayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 自定义HeaderView重写该方法
   */
  @Override
  protected void buildHeaderView() {
    mHeaderView = LayoutInflater.from(getContext())
        .inflate(R.layout.earth_header_view, this, false);
    addView(mHeaderView);
    setProcessListener(this);
    mHeaderAnim = mHeaderView.findViewById(R.id.refresh_anim);
    mHeaderAnim.setAnimation("earth.json");
    mHeaderAnim.setRepeatCount(-1);
    mStrategy = new FixedHeaderStrategy(this);
  }


  @Override
  public void onScrollStateChange(int state, int headerHeight, int scrollY) {
    mHeaderAnim.setVisibility(VISIBLE);
    float process = (float) (scrollY * 1.0 / headerHeight);
    mHeaderAnim.setProgress(process);
    if (state == REFRESHING) {
      mHeaderAnim.playAnimation();
    }
  }

  @Override
  public void stopRefreshing() {
    super.stopRefreshing();
    mHeaderAnim.cancelAnimation();
    mHeaderAnim.setVisibility(GONE);
  }

}
