package com.aidaole.easyswiperefreshlayout;

public interface IStyleStrategy {

  void onLayout();

  void onStopNestedScroll();

  void onNestedPreScroll(int dy);

  void onNestedScroll(int dy);

  void smoothScrollToHeader();

  void smoothScrollToReset();

  void computeScrollState(boolean isReleased);

  void setOnRefreshListener(EasySwipeRefreshLayout.OnRefreshListener listener);
}
