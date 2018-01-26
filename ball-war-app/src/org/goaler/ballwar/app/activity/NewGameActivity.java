package org.goaler.ballwar.app.activity;

import org.goaler.ballwar.app.BallWarApplication;
import org.goaler.ballwar.app.R;
import org.goaler.ballwar.app.model.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewGameActivity extends BaseActivity {
	private BallWarApplication app;
	
	private EditText edit_room_title;
	private EditText edit_room_description;
	private EditText edit_room_pwd;
	private Button btn_start_game;
	private Button btn_cancel;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
		init();
	}

	private void init() {
		app = (BallWarApplication) getApplication();
		
		//初始化房间信息
		Room room = app.getRoom();
		if (room==null) {
			room = new Room();
			room.setTitle(app.getRole().getName()+"的房间！");
		}
		
		edit_room_title = (EditText) findViewById(R.id.edit_room_title);
		edit_room_title.setText(room.getTitle());
		edit_room_description = (EditText) findViewById(R.id.edit_room_description);
		edit_room_description.setText(room.getDescription());
		edit_room_pwd = (EditText) findViewById(R.id.edit_room_pwd);
		edit_room_pwd.setText(room.getPwd());
		
		//按钮设置监听
		btn_start_game = (Button) findViewById(R.id.btn_start_game);;
		btn_start_game.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	};
}
