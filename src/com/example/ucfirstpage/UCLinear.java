package com.example.ucfirstpage;

import java.text.Format;

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
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UCLinear extends RelativeLayout{
	private static int CONTENT_ORI_TOP_LOC;
	
	static boolean isInRange = true; 
	public boolean isOrigin = true; //是否是初始状态,用于返回判断
	public boolean isOrigin_left = true; //左滑状态判断
	public static boolean isEdgeDrag = false; //边界拖拽判断
	public boolean isDragEdgeOnRight = true; //viewContent的右边在屏幕的右边：true;在屏幕的左边：false
	
			
	private ViewDragHelper mDragHelper;
	private DragHelperCallback mCallBack;
	private VelocityTracker mVTracker;
	
	public static int mGuideHeight; //导航栏高度
	public static int mSearchHeight; //搜索栏高度
	public static int mWebGuideHeight; //网站导航高度
	public static int mContentHeight; //内容高度
	public static int mTotalHeight;  //总高度
	
	private View mTextViewSearch;   //搜索栏
	
	private View mViewGuide; //导航栏
	private View mViewSearch; //搜索部分
	private View mViewWebGuide; //网站导航
	private View mViewContent; //内容
	private View mViewBottom;//底部导航
	private View mViewSecond; //第二页
	private TextView mTextViewinputSearch; //搜索栏
	
	//bottom中的五个button
	private Button btnPrev,btnNext,btnMore,btnPage,btnToHome;

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
				
				mViewGuide.setTranslationY(-mGuideHeight);
				mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
				UCLinear.this.getViewTreeObserver().removeOnPreDrawListener(this);
				mViewSecond.bringToFront();
				
				return false;
			}
		});
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
				getMeasuredWidth(),
				mViewWebGuide.getMeasuredHeight()+mViewSearch.getMeasuredHeight()+
							getMeasuredHeight()-mViewGuide.getMeasuredHeight()-
							mViewBottom.getMeasuredHeight());
		
		mViewSecond.layout(
				getMeasuredWidth(), 
				mViewGuide.getBottom(), 
				getMeasuredWidth()*2, 
				getMeasuredHeight()-mViewBottom.getMeasuredHeight());
	}
	
	public void initButton(){
		btnPrev = (Button) findViewById(R.id.btnPrev);
		btnNext = (Button) findViewById(R.id.btnNext);
		btnMore = (Button) findViewById(R.id.btnMore);
		btnPage = (Button) findViewById(R.id.btnPage);
		btnToHome = (Button) findViewById(R.id.btnToHome);
	}
	@Override
	protected void onFinishInflate() {
		initButton();
		mViewGuide= findViewById(R.id.uc_guide);
		mViewSearch=findViewById(R.id.uc_search);
		mViewWebGuide=findViewById(R.id.uc_webguide);
		mViewContent=findViewById(R.id.uc_news);
		mViewBottom=findViewById(R.id.uc_bottom);
		mViewSecond=findViewById(R.id.uc_second);
		mTextViewinputSearch = (TextView) findViewById(R.id.input_search);
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
			btnPrev_ta.setDuration(150);
			btnPrev_ta.setFillAfter(true);
			btnPrev.startAnimation(btnPrev_ta);
			btnPrev.setAlpha(1);
			btnPrev_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {				
				}			
				@Override
				public void onAnimationRepeat(Animation animation) {				
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					btnPrev.setTranslationX(0);
					btnPrev.clearAnimation();
					
				}
			});
			
			TranslateAnimation btnNext_ta = new TranslateAnimation(0,-btnNext.getTranslationX(), 0, 0);
			btnNext_ta.setDuration(150);
			btnNext_ta.setFillAfter(true);
			btnNext.startAnimation(btnNext_ta);
			btnNext.setAlpha(1);
			btnNext_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					btnNext.setTranslationX(0);
					btnNext.clearAnimation();
				}
			});
			
			TranslateAnimation btnPage_ta = new TranslateAnimation(0,-btnPage.getTranslationX(),  0, 0);
			btnPage_ta.setDuration(150);
			btnPage_ta.setFillAfter(true);
			btnPage.startAnimation(btnPage_ta);
			btnPage.setAlpha(1);
			btnPage_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					btnPage.setTranslationX(0);
					btnPage.clearAnimation();
				}
			});
			TranslateAnimation btnToHome_ta = new TranslateAnimation(0, -btnToHome.getTranslationX(), 0, 0);
			btnToHome_ta.setDuration(150); 
			btnToHome_ta.setFillAfter(true);
			btnToHome.startAnimation(btnToHome_ta);
			btnToHome_ta.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					btnToHome.setTranslationX(0);
					btnToHome.clearAnimation();
				}
			});			
			btnMore.setAlpha(1);
			
			TranslateAnimation viewGuide_ta = new TranslateAnimation(0,0,0,-mViewGuide.getHeight());
			viewGuide_ta.setDuration(150);
			viewGuide_ta.setFillAfter(true);
			mViewGuide.setAnimation(viewGuide_ta);
			viewGuide_ta.setAnimationListener(new AnimationListener() {
				
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
					mViewGuide.setTranslationY(-mGuideHeight);
					mViewGuide.clearAnimation();
					
				}
			});
			
			mViewContent.setScrollY(-mViewContent.getScrollY());
			TranslateAnimation viewContent_ta = new TranslateAnimation(0,0,mViewGuide.getHeight()-CONTENT_ORI_TOP_LOC,0);
			viewContent_ta.setDuration(150);
			viewContent_ta.setFillAfter(true);
			mViewContent.setAnimation(viewContent_ta);
			viewContent_ta.setAnimationListener(new AnimationListener() {
				
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
					mViewContent.setTranslationY(0);
					mViewContent.clearAnimation();
					
				}
			});
			
			requestLayout();		
		}

	}
	public void setBackToOrigin_Horizontal(){
		mViewGuide.bringToFront();
		mViewContent.bringToFront();
		mViewBottom.bringToFront();
		isOrigin_left = true;
		isEdgeDrag = false;
		isDragEdgeOnRight = true;
		
		TranslateAnimation view_ta = new TranslateAnimation(-mViewSecond.getTranslationX()-mViewSecond.getWidth(), -mViewSecond.getTranslationX(),0,0);
		view_ta.setDuration(250);
		view_ta.setFillAfter(true);
		mViewContent.setAnimation(view_ta);
		mViewWebGuide.setAnimation(view_ta);
		mViewSecond.setAnimation(view_ta);
		view_ta.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mViewContent.setTranslationX(0);
				mViewWebGuide.setTranslationX(0);
				mViewSecond.setTranslationX(0);
				mViewContent.clearAnimation();
				mViewWebGuide.clearAnimation();
				mViewSecond.clearAnimation();
			}
		});
		
		TranslateAnimation viewsearch_ta = new TranslateAnimation(0, 0, 0, -mViewSearch.getTranslationY());
		viewsearch_ta.setDuration(250);
		viewsearch_ta.setFillAfter(true);
		mViewSearch.setAnimation(viewsearch_ta);
		viewsearch_ta.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mViewSearch.setScaleX(1);
				mViewSearch.setScaleY(1);
				mViewSearch.setTranslationY(0);
				mViewSearch.clearAnimation();		
			}
		});
		Animation textview_ta = new TranslateAnimation(0,0,0,-mTextViewinputSearch.getTranslationY());
		textview_ta.setFillAfter(true);
		textview_ta.setDuration(250);
		mTextViewinputSearch.setAnimation(textview_ta);
		mTextViewinputSearch.setScaleX(1.0f);
		mTextViewinputSearch.setScaleY(1.0f);
		textview_ta.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mTextViewinputSearch.setScaleX(1);
				mTextViewinputSearch.setScaleY(1);
				mTextViewinputSearch.setTranslationY(0);	
				mTextViewinputSearch.clearAnimation();
			}
		});
		requestLayout();
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
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEdgeDrag){
			mDragHelper.processTouchEvent(event);
		}
		else {
			float search_len = mSearchHeight-mGuideHeight;
			float searchview_len = mSearchHeight/2-mTextViewinputSearch.getHeight();
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				float dx = event.getX();
				float dy = event.getX()/getWidth();
				float scale_dy = (getWidth()-event.getX())/getWidth();
				
				mViewSecond.bringToFront();
				mViewSearch.bringToFront();
				
				mViewSecond.setTranslationX(dx-getWidth());
				mViewSearch.setTranslationY(dy*search_len-search_len);
				
				mTextViewinputSearch.setTranslationY(searchview_len-dy*searchview_len);
				mTextViewinputSearch.setPivotX(getMeasuredWidth()/2);
				mTextViewinputSearch.setPivotY(mViewGuide.getTop());
				mTextViewinputSearch.setScaleX((1+scale_dy*0.2f));
				mTextViewinputSearch.setScaleY((1+scale_dy*0.1f));
				
				mViewWebGuide.setTranslationX(dx-getWidth());
				mViewContent.setTranslationX(dx-getWidth());
				if (!isDragEdgeOnRight) {
					setBackToOrigin_Horizontal();
				}
			}
			if(event.getAction() == MotionEvent.ACTION_UP) {
				if (getWidth()-event.getX() > getWidth()/3) {
					isOrigin_left = false;
					isDragEdgeOnRight = false;

				    TranslateAnimation textview_ta = new TranslateAnimation(0, 0, 0, searchview_len-mTextViewinputSearch.getTranslationY());
				    textview_ta.setDuration(250);
				    textview_ta.setFillAfter(true);
				    mTextViewinputSearch.startAnimation(textview_ta);
				    TranslateAnimation viewsearch_ta = new TranslateAnimation(0, 0, 0, -search_len-mViewSearch.getTranslationY());
				    viewsearch_ta.setDuration(250);
				    viewsearch_ta.setFillAfter(true);  
				    mViewSearch.startAnimation(viewsearch_ta);
				    requestLayout();
				    
				    TranslateAnimation viewContent_ta = new TranslateAnimation(0, mViewContent.getX(), 0, 0);
				    viewContent_ta.setDuration(350);
				    viewContent_ta.setFillAfter(true);
				    mViewWebGuide.setAnimation(viewContent_ta);
				    mViewContent.setAnimation(viewContent_ta);

					mDragHelper.smoothSlideViewTo(mViewSecond, (int) (getWidth() - mViewSecond.getX()), mViewGuide.getBottom());
					postInvalidate();
					
				}else {
					isOrigin_left = true;
					isDragEdgeOnRight = true;
					
					TranslateAnimation view_ta = new TranslateAnimation(0, -mViewContent.getTranslationX(), 0, 0);
					view_ta.setDuration(200);
					view_ta.setFillAfter(true);
					mViewContent.setAnimation(view_ta);
					mViewWebGuide.setAnimation(view_ta);
					TranslateAnimation viewsearch_ta = new TranslateAnimation(0,0, 0,-mViewSearch.getTranslationY());
					viewsearch_ta.setDuration(200);
					viewsearch_ta.setFillAfter(true);
					mViewSearch.setAnimation(viewsearch_ta);
					viewsearch_ta.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							mViewSearch.setTranslationY(0);
							mViewSearch.clearAnimation();
						}
					});
					
					ScaleAnimation scale = new ScaleAnimation(mViewSearch.getScaleX(), 1, mViewSearch.getScaleY(), 1);
					scale.setDuration(200);
					scale.setFillAfter(true);
					mTextViewinputSearch.setAnimation(scale);
					scale.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							mTextViewinputSearch.clearAnimation();
							
						}
					});
				
					TranslateAnimation viewSecond_ta = new TranslateAnimation(0,-mViewSecond.getTranslationX(),0,0);
					viewSecond_ta.setDuration(200);
					viewSecond_ta.setFillAfter(true);
					mViewSecond.setAnimation(viewSecond_ta);
					view_ta.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							mViewContent.setTranslationX(0);
							mViewWebGuide.setTranslationX(0);
							mViewSearch.setTranslationX(0);
							mViewContent.clearAnimation();
							mViewWebGuide.clearAnimation();
							mViewSearch.clearAnimation();
						}
					});
					viewSecond_ta.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							mViewSecond.setTranslationX(0);
							mViewSecond.clearAnimation();	
						}
					});
					requestLayout();
					
					mDragHelper.smoothSlideViewTo(mViewContent, 0, CONTENT_ORI_TOP_LOC);
					postInvalidate();
					
					isEdgeDrag = false;
					mViewGuide.bringToFront();
					mViewContent.bringToFront();
					mViewBottom.bringToFront();
				}
			}
		}
		return false;
	}

	class DragHelperCallback extends ViewDragHelper.Callback {
		/**
		 * 设置mViewContent可拖拽
		 */
		@Override
		public boolean tryCaptureView(View arg0, int arg1) {
			if (mDragHelper.continueSettling(true)) {
				return false;
			}
			return mViewContent == arg0 && isInRange;
		}
		/**
		 * 垂直拖拽的处理，这里对拖拽过程中mViewContent的移动进行处理
		 */
		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			int topBound = mGuideHeight;
			int bottomBound = mViewWebGuide.getBottom();
			int newTop = Math.min(Math.max(top, topBound), bottomBound);		
			return newTop;
		}
		/**
		 * 水平可拖拽的距离范围
		 */
		@Override
		public int getViewHorizontalDragRange(View child) {
			return 0;
		}
		/**
		 * 垂直可拖拽的距离范围
		 */
		@Override
		public int getViewVerticalDragRange(View child) {
			return (mTotalHeight-mViewBottom.getWidth());
		}
		/**
		 * 监听到View位置的变化，完成其他view动画的处理
		 */
		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			SlideUpHandler(changedView, left, top, dx, dy);		
		}
		/**
		 * 上滑动作处理函数
		 * @param changedView : the same as onViewPositionChanged
		 * @param left ：the same as onViewPositionChanged
		 * @param top ：the same as onViewPositionChanged
		 * @param dx : the same as onViewPositionChanged
		 * @param dy : the same as onViewPositionChanged
		 */
		public void SlideUpHandler(View changedView, int left, int top, int dx, int dy) {
			//获取导航栏相对手指移动的相对距离
			float guidedy = dy / (float)(mSearchHeight+mWebGuideHeight-mGuideHeight) * mGuideHeight;
			mViewGuide.setTranslationY(mViewGuide.getTranslationY() - guidedy);
			
			//设置mViewSearch及mViewWebGuide缩放效果
			float scale = (float) ((mViewWebGuide.getBottom()-mViewContent.getTop()) /
					(float)(mSearchHeight+mWebGuideHeight-mGuideHeight));
			mViewSearch.setPivotY(mViewSearch.getTop());
			mViewSearch.setPivotX(getWidth()/2);
			mViewSearch.setScaleY(1-scale/10);
			mViewSearch.setScaleX(1-scale/10);
			mViewWebGuide.setPivotY(mViewSearch.getTop());
			mViewWebGuide.setPivotX(getWidth()/2);
			mViewWebGuide.setScaleY(1-scale/10);
			mViewWebGuide.setScaleX(1-scale/10);
			
			//设置bottom中各button的效果			 
			float alphady = (mViewWebGuide.getBottom() - mViewContent.getTop())/
					(float)(mSearchHeight+mWebGuideHeight-mGuideHeight);
			btnMore.setAlpha(1-alphady);
			
			float homedy_tran = dy / (float)(mSearchHeight+mWebGuideHeight-mGuideHeight) * 
					(mViewBottom.getWidth()/2-btnToHome.getWidth()/2);
			btnToHome.setTranslationX(btnToHome.getTranslationX() + homedy_tran);
			
			float pagedy_tran = dy/(float)(mSearchHeight+mWebGuideHeight-mGuideHeight) *
					(btnPage.getWidth()/2+btnMore.getWidth()/2);
			btnPage.setTranslationX(btnPage.getTranslationX() + pagedy_tran);
			btnPage.setAlpha(1-alphady);
			
			float prevdy_tran = dy / (float)(mSearchHeight+mWebGuideHeight-mGuideHeight) *
					(mViewBottom.getWidth()/2-btnPrev.getWidth()/2);
			btnPrev.setTranslationX(btnPrev.getTranslationX() - prevdy_tran);
			btnPrev.setAlpha(1-alphady);
			
			float nextdy_tran = dy/(float)(mSearchHeight+mWebGuideHeight-mGuideHeight) *
					(btnNext.getWidth()/2+btnMore.getWidth()/2);
			btnNext.setTranslationX(btnNext.getTranslationX() - nextdy_tran);
			btnNext.setAlpha(1-alphady);
		}
				
		/**
		 * 释放拖拽后执行，根据mViewContent的拖拽距离决定是否上滑或返回原位
		 */
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
				int movelen_Y = CONTENT_ORI_TOP_LOC - mViewContent.getTop();
				if (movelen_Y > mWebGuideHeight){
					isInRange = false;
					isOrigin = false;
					mDragHelper.settleCapturedViewAt(0, mGuideHeight);
					postInvalidate();
				}else {
					mDragHelper.settleCapturedViewAt(0, CONTENT_ORI_TOP_LOC);
					postInvalidate();
				}
		}
		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
			mDragHelper.captureChildView(mViewContent, pointerId);
			isEdgeDrag = true;
		}
		@Override
		public void onEdgeTouched(int edgeFlags, int pointerId) {
			super.onEdgeTouched(edgeFlags, pointerId);
			
		}
	}
}
