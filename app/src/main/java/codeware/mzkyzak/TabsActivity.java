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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class TabsActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String home_html = "";
	private double index = 0;
	private String cache_path = "";
	private HashMap<String, Object> mapvar = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> tabs = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ScrollView vscroll1;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear6;
	private ImageView imageview12;
	private TextView textview2;
	private ImageView imageview11;
	private LinearLayout linear3;
	
	private SharedPreferences sp;
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.tabs);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		vscroll1 = findViewById(R.id.vscroll1);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		linear6 = findViewById(R.id.linear6);
		imageview12 = findViewById(R.id.imageview12);
		textview2 = findViewById(R.id.textview2);
		imageview11 = findViewById(R.id.imageview11);
		linear3 = findViewById(R.id.linear3);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				index = 0;
				for(int _repeat11 = 0; _repeat11 < (int)(tabs.size()); _repeat11++) {
					if (tabs.get((int)index).get("tab-index").toString().equals(sp.getString("current-tab", ""))) {
						if (tabs.get((int)index).get("searchFor").toString().equals("cwhome")) {
							intent.setClass(getApplicationContext(), HomeActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						} else {
							intent.setClass(getApplicationContext(), BrowseActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("searchFor", tabs.get((int)index).get("searchFor").toString());
							startActivity(intent);
						}
						break;
					}
					index++;
				}
			}
		});
		
		linear8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_menu();
			}
		});
		
		linear6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				sp.edit().putString("current-tab", String.valueOf((long)(tabs.size() + 1))).commit();
				mapvar = new HashMap<>();
				mapvar.put("tab-index", String.valueOf((long)(tabs.size() + 1)));
				mapvar.put("searchFor", "cwhome");
				tabs.add(mapvar);
				sp.edit().putString("tabs", new Gson().toJson(tabs)).commit();
				intent.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initializeLogic() {
		home_html = "logo";
		tabs = new Gson().fromJson(sp.getString("tabs", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		index = 0;
		for(int _repeat14 = 0; _repeat14 < (int)(tabs.size()); _repeat14++) {
			final int item_index = (int)index;
			final View inflate = getLayoutInflater().inflate(R.layout.tab_item, null);
			final WebView webView = inflate.findViewById(R.id.webview1);
			if (tabs.get((int)index).get("searchFor").toString().equals("cwhome")) {
				webView.loadDataWithBaseURL("file:///android_res/drawable/", "<img src='"+ home_html + "' style='width:100%' />", "text/html", "utf-8", null); 
			} else {
				cache_path = "file:///".concat(FileUtil.getPackageDataDir(getApplicationContext()).concat("/cache/".concat(tabs.get((int)index).get("tab-index").toString().concat(".mhtml"))));
				webView.loadUrl(cache_path);
			}
			webView.setVerticalScrollBarEnabled(false);
			webView.setHorizontalScrollBarEnabled(false);
			LinearLayout parent = inflate.findViewById(R.id.linear1);
			LinearLayout container = inflate.findViewById(R.id.linear2);
			_cardView(parent, container, 20);
			LinearLayout webParent = inflate.findViewById(R.id.linear4);
			LinearLayout webContainer = inflate.findViewById(R.id.linear6);
			_cardView(webParent, webContainer, 20);
			TextView titleText = inflate.findViewById(R.id.textview1);
			titleText.setSingleLine(true);
			titleText.setEllipsize(TextUtils.TruncateAt.END);
			if (tabs.get((int)index).get("searchFor").toString().equals("cwhome")) {
				titleText.setText("tab baru");
			} else {
				try{
					java.net.URL url = new java.net.URL(tabs.get((int)index).get("searchFor").toString());
					titleText.setText(url.getHost());
				}catch (java.net.MalformedURLException e){}
			}
			index++;
			inflate.setId(item_index);
			LinearLayout clicker = inflate.findViewById(R.id.linear8);
			clicker.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					index = inflate.getId();
					if (tabs.get((int)index).get("searchFor").toString().equals("cwhome")) {
						intent.setClass(getApplicationContext(), HomeActivity.class);
						sp.edit().putString("current-tab", tabs.get((int)index).get("tab-index").toString()).commit();
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						intent.setClass(getApplicationContext(), BrowseActivity.class);
						sp.edit().putString("current-tab", tabs.get((int)index).get("tab-index").toString()).commit();
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("searchFor", tabs.get((int)index).get("searchFor").toString());
						startActivity(intent);
					}
				}});
			inflate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					index = inflate.getId();
					if (tabs.get((int)index).get("searchFor").toString().equals("cwhome")) {
						intent.setClass(getApplicationContext(), HomeActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						intent.setClass(getApplicationContext(), BrowseActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("searchFor", tabs.get((int)index).get("searchFor").toString());
						startActivity(intent);
					}
				}});
			linear3.addView(inflate);
		}
		textview2.setText(sp.getString("current-tab", ""));
	}
	
	public void _cardView(final View _parent, final View _view, final double _radius) {
		ViewGroup parent = (ViewGroup)_parent;
		
		androidx.cardview.widget.CardView cardview = new androidx.cardview.widget.CardView(TabsActivity.this);
		
		cardview.setRadius((int)_radius);
		cardview.setCardElevation(0);
		cardview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		
		parent.removeView(_view);
		
		cardview.addView(_view);
		
		parent.addView(cardview);
	}
	
	
	public void _menu() {
		PopupMenu popup = new PopupMenu(TabsActivity.this,linear8);
		Menu menu = popup.getMenu();
		menu.add("hapus tab");
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClick(MenuItem item){
				switch (item.getTitle().toString()){
					case "hapus tab":
					tabs.clear();
					sp.edit().putString("current-tab", "1").commit();
					mapvar = new HashMap<>();
					mapvar.put("tab-index", "1");
					mapvar.put("searchFor", "cwhome");
					tabs.add(mapvar);
					sp.edit().putString("tabs", new Gson().toJson(tabs)).commit();
					intent.setClass(getApplicationContext(), HomeActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;
				}
				return true;
			}
		});
		popup.show();
	}
	
}