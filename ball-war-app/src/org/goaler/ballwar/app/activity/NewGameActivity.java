package org.goaler.ballwar.app.activity;

import java.io.IOException;
import java.net.Socket;

import org.goaler.ballwar.app.BallWarApplication;
import org.goaler.ballwar.app.R;
import org.goaler.ballwar.app.util.ConnectServerUtil;
import org.goaler.ballwar.app.util.GLog;
import org.goaler.ballwar.app.util.ToastUtil;
import org.goaler.ballwar.common.io.DataTransfer;
import org.goaler.ballwar.common.io.bio.BioTcpSerialDataTransfer;
import org.goaler.ballwar.common.model.RoomInfo;
import org.goaler.ballwar.common.msg.MsgManager;

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

		// 初始化房间信息
		initRoomInfo();

		// 按钮设置监听
		btn_start_game = (Button) findViewById(R.id.btn_start_game);
		btn_start_game.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				saveRoomInfo();
				startNewGame();
			}
		});
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initRoomInfo() {
		RoomInfo room = app.getRoom();
		if (room == null) {
			room = new RoomInfo();
			room.setTitle(app.getRole().getName() + "的房间！");
		}

		edit_room_title = (EditText) findViewById(R.id.edit_room_title);
		edit_room_title.setText(room.getTitle());
		edit_room_description = (EditText) findViewById(R.id.edit_room_description);
		edit_room_description.setText(room.getDescription());
		edit_room_pwd = (EditText) findViewById(R.id.edit_room_pwd);
		edit_room_pwd.setText(room.getPwd());
	};

	private void saveRoomInfo() {
		RoomInfo room = new RoomInfo();
		room.setTitle(edit_room_title.getText().toString());
		room.setDescription(edit_room_description.getText().toString());
		room.setPwd(edit_room_pwd.getText().toString());
		app.setRoom(room);
		app.cacheRoom();
	}

	private void startNewGame() {
		connectServer("192.168.1.109");
	}

	public void connectServer(final String ip) {
		if (ip != null) {
			ConnectServerUtil.connect(ip, new ConnectServerUtil.ConCallback() {
				@Override
				public void call(Socket socket) {
					if (socket == null) {
						return;
					}
					DataTransfer dataTransfer = null;
					try {
						dataTransfer = new BioTcpSerialDataTransfer(socket);
					} catch (IOException e) {
						GLog.error("goaler", "创建TcpDataTransfer失败！ip-{}", ip);
						return;
					}
					app.setMsgManager(new MsgManager(dataTransfer));
					ToastUtil.showToast(NewGameActivity.this, "成功连接" + ip);
				}
			});
		}

	}
}
