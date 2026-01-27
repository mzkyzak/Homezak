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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
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

public class BrowseActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String logoUrl = "";
	private String themeColor = "";
	private String title = "";
	private String description = "";
	private String domain = "";
	private String savePath = "";
	private String searchFor = "";
	private String cardColor = "";
	private String lastDomain = "";
	private double index = 0;
	private String current_tab = "";
	private double tab_position = 0;
	private HashMap<String, Object> mapvar = new HashMap<>();
	private String current_url = "";
	
	private ArrayList<HashMap<String, Object>> tabs = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> history = new ArrayList<>();
	
	private LinearLayout linear1;
	private WebView webview1;
	
	private RequestNetwork rn;
	private RequestNetwork.RequestListener _rn_request_listener;
	private TimerTask timer;
	private SharedPreferences sp;
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.browse);
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
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		rn = new RequestNetwork(this);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				current_url = _url;
				tabs.get((int)tab_position).put("searchFor", _url);
				sp.edit().putString("tabs", new Gson().toJson(tabs)).commit();
				titleText.setText(_url);
				progressBar.setVisibility(View.VISIBLE);
				try{
					java.net.URL url = new java.net.URL(_url);
					domain = url.getHost();
				}catch (java.net.MalformedURLException e){}
				if (lastDomain.equals("")) {
					lastDomain = domain;
					rn.startRequestNetwork(RequestNetworkController.GET, "http://url-metadata.herokuapp.com/api/metadata?url=http://".concat(lastDomain), "A", _rn_request_listener);
					logoUrl = "https://www.google.com/s2/favicons?sz=64&domain_url=".concat(lastDomain);
					Glide.with(getApplicationContext()).load(logoUrl).into(logoImage);
				} else {
					if (!domain.equals(lastDomain)) {
						lastDomain = domain;
						rn.startRequestNetwork(RequestNetworkController.GET, "http://url-metadata.herokuapp.com/api/metadata?url=http://".concat(lastDomain), "A", _rn_request_listener);
						logoUrl = "https://www.google.com/s2/favicons?sz=64&domain_url=".concat(lastDomain);
						Glide.with(getApplicationContext()).load(logoUrl).into(logoImage);
					}
				}
				mapvar = new HashMap<>();
				mapvar.put("url", _url);
				mapvar.put("domain", domain);
				history.add(mapvar);
				sp.edit().putString("history", new Gson().toJson(history)).commit();
				savePath = FileUtil.getPackageDataDir(getApplicationContext()).concat("/cache/".concat(tabs.get((int)tab_position).get("tab-index").toString().concat(".mhtml")));
				FileUtil.deleteFile(savePath);
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				progressBar.setVisibility(View.INVISIBLE);
				savePath = FileUtil.getPackageDataDir(getApplicationContext()).concat("/cache/".concat(tabs.get((int)tab_position).get("tab-index").toString().concat(".mhtml")));
				webview1.saveWebArchive(savePath);
				super.onPageFinished(_param1, _param2);
			}
		});
		
		_rn_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				themeColor = "null";
				title = "null";
				description = "null";
				try {
					org.json.JSONObject jsonObj = new org.json.JSONObject(_response);
					org.json.JSONObject meta_data = jsonObj.getJSONObject("data");
					title = meta_data.getString("title");
					description = meta_data.getString("description");
					themeColor = meta_data.getString("themeColor");
				} catch (final org.json.JSONException e) {
				}
				if (!themeColor.equals("null")) {
					if (themeColor.contains("#")) {
						if(isDark(Color.parseColor(themeColor))){
							int flags = BrowseActivity.this.getWindow().getDecorView().getSystemUiVisibility();
							flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;	BrowseActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
							titleText.setTextColor(Color.parseColor("#FFFFFF"));
							option.setImageResource(R.drawable.ic_more_vert_white);
							tabText.setBackgroundResource(R.drawable.ic_crop_din_white);
							tabText.setTextColor(Color.parseColor("#FFFFFF"));
						} else {
							int flags = BrowseActivity.this.getWindow().getDecorView().getSystemUiVisibility();
							flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; BrowseActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
							titleText.setTextColor(Color.parseColor("#000000"));
							option.setImageResource(R.drawable.ic_more_vert_grey);
							tabText.setBackgroundResource(R.drawable.ic_crop_din_grey);
							tabText.setTextColor(Color.parseColor("#757575"));
						}
						getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(themeColor)));
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
							Window w = BrowseActivity.this.getWindow();
							w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
							w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor(themeColor));
						}
						cardColor = themeColor;
						cardview.setCardBackgroundColor(manipulateColor(Color.parseColor(cardColor),0.9f));
					} else {
						getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
						cardview.setCardBackgroundColor(Color.parseColor("#F1F3F4"));
						getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
							Window w = BrowseActivity.this.getWindow();
							w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
							w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFFFFFFFF);
						}
						titleText.setTextColor(Color.parseColor("#000000"));
						option.setImageResource(R.drawable.ic_more_vert_grey);
						tabText.setBackgroundResource(R.drawable.ic_crop_din_grey);
						tabText.setTextColor(Color.parseColor("#757575"));
					}
				} else {
					getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
					cardview.setCardBackgroundColor(Color.parseColor("#F1F3F4"));
					getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
						Window w = BrowseActivity.this.getWindow();
						w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
						w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFFFFFFFF);
					}
					titleText.setTextColor(Color.parseColor("#000000"));
					option.setImageResource(R.drawable.ic_more_vert_grey);
					tabText.setBackgroundResource(R.drawable.ic_crop_din_grey);
					tabText.setTextColor(Color.parseColor("#757575"));
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		tabs = new Gson().fromJson(sp.getString("tabs", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		current_tab = sp.getString("current-tab", "");
		index = 0;
		for(int _repeat67 = 0; _repeat67 < (int)(tabs.size()); _repeat67++) {
			if (tabs.get((int)index).get("tab-index").toString().equals(current_tab)) {
				tab_position = index;
				break;
			}
			index++;
		}
		searchFor = getIntent().getStringExtra("searchFor");
		themeColor = "#FFFFFF";
		if(URLUtil.isValidUrl(searchFor)){
			webview1.loadUrl(searchFor);
		} else {
			webview1.loadUrl("https://www.google.com/search?q=".concat(searchFor));
		}
		webview1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				progressBar.setProgress(progress);
			}});
		webview1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview1.getSettings().setDomStorageEnabled(true);
		_setUpToolBar();
		if (!sp.getString("history", "").equals("")) {
			history = new Gson().fromJson(sp.getString("history", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
	}
	
	@Override
	public void onBackPressed() {
		if (webview1.canGoBack()) {
			webview1.goBack();
		} else {
			tabs.get((int)tab_position).put("searchFor", "cwhome");
			sp.edit().putString("tabs", new Gson().toJson(tabs)).commit();
			intent.setClass(getApplicationContext(), HomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
	public void _library() {
	}
	ProgressBar progressBar;
	ImageView logoImage,option;
	TextView titleText, tabText;
	androidx.cardview.widget.CardView cardview;
	{
	}
	boolean isDark(int color) {
		return androidx.core.graphics.ColorUtils.calculateLuminance(color) < 0.5;
	}
	{
	}
	public static int manipulateColor(int color, float factor) {
		int a = Color.alpha(color);
		int r = Math.round(Color.red(color) * factor);
		int g = Math.round(Color.green(color) * factor);
		int b = Math.round(Color.blue(color) * factor);
		return Color.argb(a,
		Math.min(r,255),
		Math.min(g,255),
		Math.min(b,255));
	}
	{
	}
	
	
	public void _setUpToolBar() {
		androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		View customView = getLayoutInflater().inflate(R.layout.search_bar, null);
		customView.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
		progressBar = customView.findViewById(R.id.progressbar1);
		logoImage = customView.findViewById(R.id.imageview1);
		option = customView.findViewById(R.id.imageview3);
		titleText = customView.findViewById(R.id.textview1);
		tabText = customView.findViewById(R.id.textview3);
		tabText.setText(current_tab);
		titleText.setSingleLine(true);
		titleText.setEllipsize(TextUtils.TruncateAt.END);
		final LinearLayout logoParent = customView.findViewById(R.id.linear3);
		androidx.cardview.widget.CardView cardview1 = new androidx.cardview.widget.CardView(BrowseActivity.this);
		cardview1.setRadius(180);
		cardview1.setCardElevation(0);
		
		logoParent.removeView(logoImage);
		
		cardview1.addView(logoImage);
		
		logoParent.addView(cardview1);
		actionBar.setCustomView(customView);
		Toolbar parent =(Toolbar) customView.getParent();
		parent.setPadding(0,0,0,0);
		parent.setContentInsetsAbsolute(0,0);
		final LinearLayout urlContainer = customView.findViewById(R.id.linear5);
		ViewGroup urlParent = (ViewGroup)urlContainer.getParent();
		urlParent.removeView(urlContainer);
		cardview = new androidx.cardview.widget.CardView(BrowseActivity.this);
		cardview.setRadius(180);
		cardview.setCardElevation(0);
		
		cardview.addView(urlContainer);
		cardview.setCardBackgroundColor(Color.parseColor("#F1F3F4"));
		
		urlParent.addView(cardview);
		cardview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
		urlContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_searchDialog();
			}});
		final LinearLayout tabParent = customView.findViewById(R.id.linear6);
		tabParent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_go_to_tabs();
			}});
		final LinearLayout menu = customView.findViewById(R.id.linear7);
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_menu(menu);
			}});
	}
	
	
	public void _cardView(final View _parent, final View _view, final double _radius) {
		ViewGroup parent = (ViewGroup)_parent;
		
		androidx.cardview.widget.CardView cardview = new androidx.cardview.widget.CardView(BrowseActivity.this);
		
		cardview.setRadius((int)_radius);
		cardview.setCardElevation(0);
		cardview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		
		parent.removeView(_view);
		
		cardview.addView(_view);
		
		parent.addView(cardview);
	}
	
	
	public void _searchDialog() {
		final Dialog searchDialog = new Dialog(this, R.style.FullScreenDialog);
		searchDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		View inflate = getLayoutInflater().inflate(R.layout.search_dialog, null);
		inflate.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
		searchDialog.setContentView(inflate);
		searchDialog.show();
		final LinearLayout urlParent = inflate.findViewById(R.id.linear4);
		final LinearLayout urlContainer = inflate.findViewById(R.id.linear5);
		androidx.cardview.widget.CardView cardview = new androidx.cardview.widget.CardView(BrowseActivity.this);
		cardview.setRadius(180);
		cardview.setCardElevation(0);
		urlParent.removeView(urlContainer);
		cardview.addView(urlContainer);
		cardview.setCardBackgroundColor(Color.parseColor("#F1F3F4"));
		
		urlParent.addView(cardview);
	}
	
	
	public void _go_to_tabs() {
		intent.setClass(getApplicationContext(), TabsActivity.class);
		startActivity(intent);
	}
	
	
	public void _menu(final View _view) {
		PopupMenu popup = new PopupMenu(BrowseActivity.this,_view);
		Menu menu = popup.getMenu();
		menu.add("Ulang");
		menu.add("tab baru");
		menu.add("History");
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClick(MenuItem item){
				switch (item.getTitle().toString()){
					case "Reload":
					webview1.loadUrl(current_url);
					break;
					case "tab baru":
					sp.edit().putString("current-tab", String.valueOf((long)(tabs.size() + 1))).commit();
					mapvar = new HashMap<>();
					mapvar.put("tab-index", String.valueOf((long)(tabs.size() + 1)));
					mapvar.put("searchFor", "cwhome");
					tabs.add(mapvar);
					sp.edit().putString("tabs", new Gson().toJson(tabs)).commit();
					intent.setClass(getApplicationContext(), HomeActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;
					case "History":
					intent.setClass(getApplicationContext(), HistoryActivity.class);
					startActivity(intent);
					break;
				}
				return true;
			}
		});
		popup.show();
	}
	
}