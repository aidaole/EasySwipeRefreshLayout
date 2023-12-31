package com.aidaole.easyswiperefreshlayout.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.aidaole.easyswiperefreshlayout.EasySwipeRefreshLayout;
import com.aidaole.easyswiperefreshlayout.MoveHeaderStrategy;
import com.aidaole.easyswiperefreshlayout.R;
import com.airbnb.lottie.LottieAnimationView;


/**
 * 自定义HeaderView示例
 */
public class MoveHeaderRefreshLayout extends EasySwipeRefreshLayout implements
        EasySwipeRefreshLayout.OnScrollStateChangeListener {

  private LottieAnimationView mPulldownAnim;
  private LottieAnimationView mRefreshAnim;

  public MoveHeaderRefreshLayout(Context context) {
    this(context, null);
  }

  public MoveHeaderRefreshLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 如果想自己设置HeaderView，重写这个方法，设置header view和scroll listener
   */
  @Override
  protected void buildHeaderView() {
    mHeaderView = LayoutInflater.from(getContext()).inflate(
        R.layout.rocket_header_view, this, false
    );
    addView(mHeaderView);
    setProcessListener(this);
    mPulldownAnim = findViewById(R.id.pulldown_anim);
    mPulldownAnim.setAnimation("fly.json");

    mRefreshAnim = findViewById(R.id.refresh_anim);
    mRefreshAnim.setAnimation("circle.json");
    mRefreshAnim.setRepeatCount(ValueAnimator.INFINITE);
    mStrategy = new MoveHeaderStrategy(this);
  }

  @Override
  public void stopRefreshing() {
    super.stopRefreshing();
    mRefreshAnim.setProgress(0);
    mPulldownAnim.setProgress(0);
    mRefreshAnim.setVisibility(GONE);
    mPulldownAnim.setVisibility(GONE);
  }

  @Override
  public void onScrollStateChange(int state, int headerHeight, int scrollY) {
    float process = (float) (scrollY * 1.0 / headerHeight);
    process = Math.min(0.5f, process);
    mRefreshAnim.setVisibility(GONE);
    mPulldownAnim.setVisibility(VISIBLE);
    mPulldownAnim.setProgress(process);

    if (state == REFRESHING) {
      mPulldownAnim.resumeAnimation();
      mPulldownAnim.addAnimatorListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          mPulldownAnim.setVisibility(GONE);
          mRefreshAnim.setVisibility(VISIBLE);
          mRefreshAnim.playAnimation();
        }
      });
    }
  }
}
