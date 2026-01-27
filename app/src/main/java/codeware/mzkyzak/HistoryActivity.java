package codeware.mzkyzak;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class HistoryActivity extends AppCompatActivity {
	
	private double index = 0;
	
	private ArrayList<HashMap<String, Object>> history = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear1;
	private TextView textview1;
	
	private SharedPreferences sp;
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.history);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		if (!sp.getString("history", "").equals("")) {
			history = new Gson().fromJson(sp.getString("history", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			index = history.size() - 1;
			for(int _repeat13 = 0; _repeat13 < (int)(history.size()); _repeat13++) {
				int id = (int) index;
				View inflate = getLayoutInflater().inflate(R.layout.history_item, null);
				ImageView logo = inflate.findViewById(R.id.imageview1);
				String logoUrl = "https://www.google.com/s2/favicons?sz=64&domain_url=" + history.get((int)index).get("domain").toString();
				Glide.with(getApplicationContext()).load(logoUrl).into(logo);
				TextView url = inflate.findViewById(R.id.textview1);
				url.setSingleLine(true);
				url.setEllipsize(TextUtils.TruncateAt.END);
				url.setText(history.get((int)index).get("url").toString());
				TextView domain = inflate.findViewById(R.id.textview2);
				domain.setText(history.get((int)index).get("domain").toString());
				linear1.addView(inflate);
				inflate.setId(id);
				inflate.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						index = view.getId();
						intent.setClass(getApplicationContext(), BrowseActivity.class);
						intent.putExtra("searchFor", history.get((int)index).get("url").toString());
						startActivity(intent);
					}});
				index--;
			}
		}
	}
	
}