package org.goaler.ballwar.app.activity;

import org.goaler.ballwar.app.BallWarApplication;
import org.goaler.ballwar.app.R;
import org.goaler.ballwar.app.model.AppConstant;
import org.goaler.ballwar.app.util.ToastUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserInfoActivity extends BaseActivity {
	
	private BallWarApplication app;
	
	private EditText editRoleName;
	private Button btn_save;
	private Button btn_cancel;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		init();
	}

	private void init() {
		app = (BallWarApplication) getApplication();
		editRoleName = (EditText) findViewById(R.id.edit_role_name);
		editRoleName.setText(app.getRole().getName());
		
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = editRoleName.getText().toString();
				app.getRole().setName(name);
				app.getSpm().save(AppConstant.SP_KEY_ROLE, app.getRole());
				ToastUtil.showToast(UserInfoActivity.this, "保存成功");
				finish();
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
