package org.goaler.ballwar.app.activity;

import org.goaler.ballwar.app.R;
import org.goaler.ballwar.app.view.CombatView;

import android.os.Bundle;
import android.widget.ImageButton;

public class GameRunActivity extends BaseActivity {
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
		this.combatView = (CombatView)findViewById(R.id.combatView);
		this.imgBtn_split = (ImageButton)findViewById(R.id.imgBtn_split);
		this.imgBtn_vomit = (ImageButton)findViewById(R.id.imgBtn_vomit);
	}
}
