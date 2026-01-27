package codeware.mzkyzak;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double index = 0;
	private String searchFor = "";
	private String current_tab = "";
	
	private ArrayList<HashMap<String, Object>> tabs = new ArrayList<>();
	
	private LinearLayout linear1;
	private TextView textview1;
	private ImageView imageview1;
	
	private TimerTask t;
	private Intent intent = new Intent();
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		imageview1 = findViewById(R.id.imageview1);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (!sp.getString("current-tab", "").equals("")) {
							if (!sp.getString("tabs", "").equals("")) {
								tabs = new Gson().fromJson(sp.getString("tabs", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
								current_tab = sp.getString("current-tab", "");
								index = 0;
								for(int _repeat26 = 0; _repeat26 < (int)(tabs.size()); _repeat26++) {
									if (tabs.get((int)index).get("tab-index").toString().equals(current_tab)) {
										searchFor = tabs.get((int)index).get("searchFor").toString();
										break;
									}
									index++;
								}
								if (searchFor.equals("cwhome")) {
									intent.setClass(getApplicationContext(), HomeActivity.class);
									startActivity(intent);
								} else {
									intent.setClass(getApplicationContext(), BrowseActivity.class);
									intent.putExtra("searchFor", searchFor);
									startActivity(intent);
								}
							} else {
								intent.setClass(getApplicationContext(), HomeActivity.class);
								startActivity(intent);
							}
						} else {
							intent.setClass(getApplicationContext(), HomeActivity.class);
							startActivity(intent);
						}
					}
				});
			}
		};
		_timer.schedule(t, (int)(2000));
	}
	
	@Override
	public void onBackPressed() {
		
	}
}