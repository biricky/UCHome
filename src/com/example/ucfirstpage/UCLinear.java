package com.example.ucfirstpage;

import android.R.bool;
import android.R.integer;
import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UCLinear extends RelativeLayout{
	private static int CONTENT_ORI_TOP_LOC;
	
	private boolean isInRange = true;
	public boolean isOrigin = true;
	
	private ViewDragHelper mDragHelper;
	private DragHelperCallback mCallBack;
	private VelocityTracker mVTracker;
	
	private int mGuideHeight; //导航栏高度
	private int mSearchHeight; //搜索栏高度
	private int mWebGuideHeight; //网站导航高度
	private int mContentHeight; //内容高度
	private int mTotalHeight;  //总高度
	
	
	private View mViewGuide; //导航栏
	private View mViewSearch; //搜索部分
	private View mViewWebGuide; //网站导航
	private View mViewContent; //内容
	private View mViewBottom;//底部导航
	
	private TextView tv_in_Search ;
	
	//bottom中的五个button
	private Button btnPrev,btnNext,btnMore,btnPage,btnToHome;
	private float btnPrev_dis,btnNext_dis,btnPage_dis,btnToHome_dis;

	public UCLinear(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mCallBack = new DragHelperCallback();
		mDragHelper = ViewDragHelper.create(this, mCallBack);
		this.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {	
			@Override
			public boolean onPreDraw() {
				CONTENT_ORI_TOP_LOC = mViewContent.getTop();
				mGuideHeight = mViewGuide.getHeight();
				mSearchHeight = mViewSearch.getHeight();
				mWebGuideHeight = mViewWebGuide.getHeight();
				mTotalHeight = UCLinear.this.getHeight();
				mViewGuide.bringToFront();
				mViewGuide.setTranslationY(-mGuideHeight);
				UCLinear.this.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});
	}
	
	public void initButton(){
		btnPrev = (Button) findViewById(R.id.btnPrev);
		btnNext = (Button) findViewById(R.id.btnNext);
		btnMore = (Button) findViewById(R.id.btnMore);
		btnPage = (Button) findViewById(R.id.btnPage);
		btnToHome = (Button) findViewById(R.id.btnToHome);
	}
	public void setBackToOrigin(){
		if (!isOrigin){
			isInRange = true;
			isOrigin = true; 
			
			mViewSearch.setScaleX(1);
			mViewSearch.setScaleY(1);
			
			mViewWebGuide.setScaleX(1);
			mViewWebGuide.setScaleY(1);
			
			TranslateAnimation btnPrev_ta = new TranslateAnimation(0,-btnPrev.getTranslationX(), 0, 0);
			btnPrev_ta.setDuration(100);
			btnPrev_ta.setFillAfter(true);
			btnPrev.startAnimation(btnPrev_ta);
			btnPrev.setAlpha(1);
			btnPrev_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub					
				}			
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub				
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					btnPrev.setTranslationX(0);
					btnPrev.clearAnimation();
					
				}
			});
			
			TranslateAnimation btnNext_ta = new TranslateAnimation(0,-btnNext.getTranslationX(), 0, 0);
			btnNext_ta.setDuration(100);
			btnNext_ta.setFillAfter(true);
			btnNext.startAnimation(btnNext_ta);
			btnNext.setAlpha(1);
			btnNext_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					btnNext.setTranslationX(0);
					btnNext.clearAnimation();
				}
			});
			
			TranslateAnimation btnPage_ta = new TranslateAnimation(0,-btnPage.getTranslationX(),  0, 0);
			btnPage_ta.setDuration(100);
			btnPage_ta.setFillAfter(true);
			btnPage.startAnimation(btnPage_ta);
			btnPage.setAlpha(1);
			btnPage_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					btnPage.setTranslationX(0);
					btnPage.clearAnimation();
				}
			});
			TranslateAnimation btnToHome_ta = new TranslateAnimation(0, -btnToHome.getTranslationX(), 0, 0);
			btnToHome_ta.setDuration(100); 
			btnToHome_ta.setFillAfter(true);
			btnToHome.startAnimation(btnToHome_ta);
			btnToHome_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					btnToHome.setTranslationX(0);
					btnToHome.clearAnimation();
				}
			});			
			btnMore.setAlpha(1);
			
			mViewGuide.setTranslationY(-mGuideHeight);
			mViewContent.setScrollY(-mViewContent.getScrollY());
			requestLayout();		
		}
	}
	//计算拖动速度
	@Override
	public void computeScroll() {
		if(mDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mVTracker == null) {
			mVTracker = VelocityTracker.obtain();
		}
		mVTracker.addMovement(ev);
		final VelocityTracker vt = mVTracker;
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			vt.computeCurrentVelocity(1000);
			if ( vt.getYVelocity() < -1500 && isInRange) {			
				mDragHelper.smoothSlideViewTo(mViewContent, 0, mGuideHeight);
				this.postInvalidate();
				
				isInRange = false;
				isOrigin = false;
				
				return false;			
			}
			else if (vt.getYVelocity() > 500 && mViewContent.getScrollY() == 0) {
				this.setBackToOrigin();
				return true;
			}
		}
		return mDragHelper.shouldInterceptTouchEvent(ev) && isInRange;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDragHelper.processTouchEvent(event);
		return false;
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mViewGuide.layout(
				0, 
				0, 
				mViewGuide.getMeasuredWidth(), 
				mViewGuide.getMeasuredHeight());
		mViewSearch.layout(
				0, 
				0, 
				mViewSearch.getMeasuredWidth(), 
				mViewSearch.getMeasuredHeight());
		mViewWebGuide.layout(
				0, 
				mViewSearch.getMeasuredHeight(),
				mViewWebGuide.getMeasuredWidth(), 
				mViewSearch.getMeasuredHeight()+mViewWebGuide.getMeasuredHeight());
		mViewBottom.layout(
				0, 
				getMeasuredHeight()-mViewBottom.getMeasuredHeight(), 
				mViewBottom.getMeasuredWidth(),
				getMeasuredHeight());
		mViewContent.layout(
				0, 
				mViewWebGuide.getMeasuredHeight()+mViewSearch.getMeasuredHeight(), 
				mViewContent.getMeasuredWidth(),
				mViewWebGuide.getMeasuredHeight()+mViewSearch.getMeasuredHeight()+getMeasuredHeight()-mViewGuide.getMeasuredHeight()-mViewBottom.getMeasuredHeight());
	}
	@Override
	protected void onFinishInflate() {
		initButton();
		mViewGuide= findViewById(R.id.uc_guide);
		mViewSearch=findViewById(R.id.uc_search);
		mViewWebGuide=findViewById(R.id.uc_webguide);
		mViewContent=findViewById(R.id.uc_news);
		mViewBottom=findViewById(R.id.uc_bottom);
	}
	
	
	
	class DragHelperCallback extends ViewDragHelper.Callback {

		@Override
		public boolean tryCaptureView(View arg0, int arg1) {
			if (mDragHelper.continueSettling(true))
				return false;
			return mViewContent == arg0 && isInRange;
		}
		/**
		 * 重新定义垂直移动的位置
		 */
		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			int topBound = mGuideHeight;
			int bottomBound = mViewWebGuide.getBottom();
			int newTop = Math.min(Math.max(top, topBound), bottomBound);		
			return newTop;
		}
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// TODO Auto-generated method stub
			return super.clampViewPositionHorizontal(child, left, dx);
		}
		@Override
		public int getViewHorizontalDragRange(View child) {
			// TODO Auto-generated method stub
			return (mViewWebGuide.getWidth() | mViewContent.getWidth());
		}
		@Override
		public int getViewVerticalDragRange(View child) {
			// TODO Auto-generated method stub
			return mTotalHeight;
		}
		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
						
			//获取导航栏相对手指移动的相对距离
			float guidedy = dy / (float)(mSearchHeight+mWebGuideHeight-mGuideHeight) * mGuideHeight;
			mViewGuide.setTranslationY(mViewGuide.getTranslationY() - guidedy);
			
			float scale = (float) ((mViewWebGuide.getBottom()-mViewContent.getTop()) /(float)(mSearchHeight+mWebGuideHeight-mGuideHeight));
			mViewSearch.setPivotY(mViewSearch.getTop());
			mViewSearch.setPivotX(getWidth()/2);
			mViewSearch.setScaleY(1-scale/10);
			mViewSearch.setScaleX(1-scale/10);
			mViewWebGuide.setPivotY(mViewSearch.getTop());
			mViewWebGuide.setPivotX(getWidth()/2);
			mViewWebGuide.setScaleY(1-scale/10);
			mViewWebGuide.setScaleX(1-scale/10);
			/**
			 * 设置bottom中各button的效果			
			 */
			float alphady = (mViewWebGuide.getBottom() - mViewContent.getTop())/(float)(mSearchHeight+mWebGuideHeight-mGuideHeight);
			btnMore.setAlpha(1-alphady);
			
			float homedy_tran = dy / (float)(mSearchHeight+mWebGuideHeight-mGuideHeight) * (mViewBottom.getWidth()/2-btnToHome.getWidth()/2);
			btnToHome.setTranslationX(btnToHome.getTranslationX() + homedy_tran);
			
			float pagedy_tran = dy/(float)(mSearchHeight+mWebGuideHeight-mGuideHeight) *(btnPage.getWidth()/2+btnMore.getWidth()/2);
			btnPage.setTranslationX(btnPage.getTranslationX() + pagedy_tran);
			btnPage.setAlpha(1-alphady);
			
			float prevdy_tran = dy / (float)(mSearchHeight+mWebGuideHeight-mGuideHeight) * (mViewBottom.getWidth()/2-btnPrev.getWidth()/2);
			btnPrev.setTranslationX(btnPrev.getTranslationX() - prevdy_tran);
			btnPrev.setAlpha(1-alphady);
			
			float nextdy_tran = dy/(float)(mSearchHeight+mWebGuideHeight-mGuideHeight) *(btnNext.getWidth()/2+btnMore.getWidth()/2);
			btnNext.setTranslationX(btnNext.getTranslationX() - nextdy_tran);
			btnNext.setAlpha(1-alphady);
		}
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			int movelen = CONTENT_ORI_TOP_LOC - mViewContent.getTop();
			if (movelen > mWebGuideHeight){
				isInRange = false;
				isOrigin = false;
				mDragHelper.settleCapturedViewAt(0, mGuideHeight);
				postInvalidate();
			}else {
				mDragHelper.settleCapturedViewAt(0, CONTENT_ORI_TOP_LOC);
				postInvalidate();
			}
		}
	}
}
