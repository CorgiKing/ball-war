package org.goaler.ballwar.app.activity;

import org.goaler.ballwar.app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomePageActivity extends BaseActivity {
	
	private ImageButton ibtnUserInfo;
	private Button btnNewGame;
	
	private View.OnClickListener onClick;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		
		init();
	}

	private void init() {
		btnNewGame = (Button) findViewById(R.id.btn_new_game);
		ibtnUserInfo = (ImageButton) findViewById(R.id.ibtn_user_info);
		onClick = new HomePageOnClickListener();
		
		btnNewGame.setOnClickListener(onClick);
		ibtnUserInfo.setOnClickListener(onClick);
	}
	
	private class HomePageOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_new_game) {
				Intent intentNewGame = new Intent(HomePageActivity.this,NewGameActivity.class);
				startActivity(intentNewGame);
			}else if (v.getId() == R.id.ibtn_user_info) {
				Intent intentUserInfo = new Intent(HomePageActivity.this, UserInfoActivity.class);
				startActivity(intentUserInfo);
			}
		}
		
	}
}
