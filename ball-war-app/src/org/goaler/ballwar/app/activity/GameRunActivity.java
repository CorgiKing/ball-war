package org.goaler.ballwar.app.activity;

import org.goaler.ballwar.app.BallWarApplication;
import org.goaler.ballwar.app.R;
import org.goaler.ballwar.app.view.CombatView;
import org.goaler.ballwar.common.msg.Msg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GameRunActivity extends BaseActivity {
	private BallWarApplication app;
	private CombatView combatView;
	private ImageButton imgBtn_split;
	private ImageButton imgBtn_vomit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_run);

		init();
	}

	private void init() {
		app = (BallWarApplication) getApplication();
		this.combatView = (CombatView) findViewById(R.id.combatView);
		this.imgBtn_split = (ImageButton) findViewById(R.id.imgBtn_split);
		this.imgBtn_vomit = (ImageButton) findViewById(R.id.imgBtn_vomit);
		
		PlayListener listener = new PlayListener();
        this.imgBtn_split.setOnClickListener(listener);
        this.imgBtn_vomit.setOnClickListener(listener);
	}

	class PlayListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.imgBtn_split) {
				Msg msg = new Msg();
				msg.setCmd("split");
				app.getMsgManager().output(msg);
			} else if (v.getId() == R.id.imgBtn_vomit) {
				Msg msg = new Msg();
				msg.setCmd("vomit");
				app.getMsgManager().output(msg);
			}
		}
	}
}
