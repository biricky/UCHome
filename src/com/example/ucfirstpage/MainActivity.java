package com.example.ucfirstpage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	public UCLinear ucView;
	public Button btnHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ucView = (UCLinear)findViewById(R.id.uc_liner);
		btnHome = (Button) findViewById(R.id.btnToHome);
		btnHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!ucView.isOrigin) {
					ucView.setBackToOrigin();
				}else {	
				}
			}
		});
	}
	@Override
	public void onBackPressed() {
		if (!ucView.isOrigin) {
			ucView.setBackToOrigin();
		}else {
			super.onBackPressed();
		}
	}		
}
