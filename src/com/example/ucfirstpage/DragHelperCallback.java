package com.example.ucfirstpage;

import android.support.v4.widget.ViewDragHelper;
import android.view.View;

public class DragHelperCallback extends ViewDragHelper.Callback{

	@Override
	public int clampViewPositionHorizontal(View child, int left, int dx) {
		// TODO Auto-generated method stub
		return super.clampViewPositionHorizontal(child, left, dx);
	}

	@Override
	public int clampViewPositionVertical(View child, int top, int dy) {
		// TODO Auto-generated method stub
		return super.clampViewPositionVertical(child, top, dy);
	}

	//当前view是否允许拖动
	@Override
	public boolean tryCaptureView(View arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	

}
