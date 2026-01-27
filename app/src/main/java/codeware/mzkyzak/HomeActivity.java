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
import android.widget.EditText;
import android.widget.ImageView;
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

public class HomeActivity extends AppCompatActivity {
	
	private HashMap<String, Object> mapvar = new HashMap<>();
	private String url = "";
	
	private ArrayList<HashMap<String, Object>> tabs = new ArrayList<>();
	
	private ScrollView vscroll2;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear19;
	private LinearLayout linear20;
	private TextView textview2;
	private ImageView imageview11;
	private ImageView imageview1;
	private AnalogClock analogclock1;
	private TextView textview3;
	private LinearLayout linear4;
	private LinearLayout linear6;
	private TextView textview1;
	private LinearLayout linear18;
	private LinearLayout linear5;
	private EditText edittext1;
	private ImageView imageview2;
	private LinearLayout linear7;
	private LinearLayout linear13;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private ImageView imageview3;
	private TextView textview5;
	private ImageView imageview4;
	private TextView textview4;
	private ImageView imageview5;
	private TextView textview7;
	private ImageView imageview6;
	private TextView textview6;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private LinearLayout linear16;
	private LinearLayout linear17;
	private ImageView imageview7;
	private TextView textview8;
	private ImageView imageview8;
	private TextView textview9;
	private ImageView imageview9;
	private TextView textview10;
	private ImageView imageview10;
	private TextView textview11;
	
	private RequestNetwork rn;
	private RequestNetwork.RequestListener _rn_request_listener;
	private Intent intent = new Intent();
	private SharedPreferences sp;
	private ProgressDialog onPositiveButtonClick;
	private DatePickerDialog dialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll2 = findViewById(R.id.vscroll2);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear19 = findViewById(R.id.linear19);
		linear20 = findViewById(R.id.linear20);
		textview2 = findViewById(R.id.textview2);
		imageview11 = findViewById(R.id.imageview11);
		imageview1 = findViewById(R.id.imageview1);
		analogclock1 = findViewById(R.id.analogclock1);
		textview3 = findViewById(R.id.textview3);
		linear4 = findViewById(R.id.linear4);
		linear6 = findViewById(R.id.linear6);
		textview1 = findViewById(R.id.textview1);
		linear18 = findViewById(R.id.linear18);
		linear5 = findViewById(R.id.linear5);
		edittext1 = findViewById(R.id.edittext1);
		imageview2 = findViewById(R.id.imageview2);
		linear7 = findViewById(R.id.linear7);
		linear13 = findViewById(R.id.linear13);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		linear11 = findViewById(R.id.linear11);
		linear12 = findViewById(R.id.linear12);
		imageview3 = findViewById(R.id.imageview3);
		textview5 = findViewById(R.id.textview5);
		imageview4 = findViewById(R.id.imageview4);
		textview4 = findViewById(R.id.textview4);
		imageview5 = findViewById(R.id.imageview5);
		textview7 = findViewById(R.id.textview7);
		imageview6 = findViewById(R.id.imageview6);
		textview6 = findViewById(R.id.textview6);
		linear14 = findViewById(R.id.linear14);
		linear15 = findViewById(R.id.linear15);
		linear16 = findViewById(R.id.linear16);
		linear17 = findViewById(R.id.linear17);
		imageview7 = findViewById(R.id.imageview7);
		textview8 = findViewById(R.id.textview8);
		imageview8 = findViewById(R.id.imageview8);
		textview9 = findViewById(R.id.textview9);
		imageview9 = findViewById(R.id.imageview9);
		textview10 = findViewById(R.id.textview10);
		imageview10 = findViewById(R.id.imageview10);
		textview11 = findViewById(R.id.textview11);
		rn = new RequestNetwork(this);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
		dialog = new DatePickerDialog(this);
		
		linear19.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), TabsActivity.class);
				startActivity(intent);
			}
		});
		
		linear20.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_menu();
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), BrowseActivity.class);
				intent.putExtra("searchFor", edittext1.getText().toString());
				startActivity(intent);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.linkedin.com/in/taufiq-ikhsan-muzaky-42ab26388?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"));
				startActivity(intent);
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setData(Uri.parse("https://www.facebook.com/share/184A7T5Ags/"));
				intent.setAction(Intent.ACTION_VIEW);
				startActivity(intent);
			}
		});
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://youtube.com/@muzaky_2023?si=eY0pnhB4ECWaJXWl"));
				startActivity(intent);
			}
		});
		
		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://t.me/mzkyzak"));
				startActivity(intent);
			}
		});
		
		imageview7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.tiktok.com/@mzky896?_r=1&_t=ZS-93FziFQgRa1"));
				startActivity(intent);
			}
		});
		
		imageview8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.instagram.com/mzky_zak?igsh=eWN2cjlzeXhuMmR0"));
				startActivity(intent);
			}
		});
		
		imageview9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://github.com/mzkyzak"));
				startActivity(intent);
			}
		});
		
		imageview10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://wa.link/b2vq5s"));
				startActivity(intent);
			}
		});
		
		_rn_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					org.json.JSONObject jsonObj = new org.json.JSONObject(_response);
					final org.json.JSONArray articles = jsonObj.getJSONArray("articles");
					for (int i = 0; i < articles.length(); i++) {
						org.json.JSONObject item = articles.getJSONObject(i);
						url = item.getString("url").toString().trim();
						final View inflate;
						if (SketchwareUtil.getRandom((int)(0), (int)(1)) == 0) {
							inflate = getLayoutInflater().inflate(R.layout.article_small, null);
							TextView title = inflate.findViewById(R.id.textview1);
							TextView description = inflate.findViewById(R.id.textview2);
							ImageView image = inflate.findViewById(R.id.imageview1);
							title.setText(item.getString("title").toString().trim());
							title.setEllipsize(TextUtils.TruncateAt.END);
							title.setMaxLines(3);
							description.setText(item.getString("content").toString().trim());
							description.setEllipsize(TextUtils.TruncateAt.END);
							description.setMaxLines(3);
							Glide.with(getApplicationContext()).load(item.getString("urlToImage").toString()).into(image);
							LinearLayout parent = inflate.findViewById(R.id.linear1);
							LinearLayout view = inflate.findViewById(R.id.linear2);
							androidx.cardview.widget.CardView cardview = new androidx.cardview.widget.CardView(HomeActivity.this);
							
							cardview.setRadius(20);
							cardview.setCardElevation(0);
							cardview.setBackgroundResource(R.drawable.card_view_border);
							
							parent.removeView(view);
							
							cardview.addView(view);
							
							parent.addView(cardview);
							LinearLayout imgParent = inflate.findViewById(R.id.linear3);
							androidx.cardview.widget.CardView cardview1 = new androidx.cardview.widget.CardView(HomeActivity.this);
							
							cardview1.setRadius(15);
							cardview1.setCardElevation(0);
							
							imgParent.removeView(image);
							
							cardview1.addView(image);
							
							imgParent.addView(cardview1);
							cardview1.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
						} else {
							inflate = getLayoutInflater().inflate(R.layout.article_large, null);
							TextView title = inflate.findViewById(R.id.textview1);
							TextView description = inflate.findViewById(R.id.textview2);
							ImageView image = inflate.findViewById(R.id.imageview1);
							title.setText(item.getString("title").toString().trim());
							title.setEllipsize(TextUtils.TruncateAt.END);
							title.setMaxLines(3);
							description.setText(item.getString("content").toString().trim());
							description.setEllipsize(TextUtils.TruncateAt.END);
							description.setMaxLines(3);
							Glide.with(getApplicationContext()).load(item.getString("urlToImage").toString()).into(image);
							LinearLayout parent = inflate.findViewById(R.id.linear1);
							LinearLayout view = inflate.findViewById(R.id.linear2);
							androidx.cardview.widget.CardView cardview = new androidx.cardview.widget.CardView(HomeActivity.this);
							
							cardview.setRadius(20);
							cardview.setCardElevation(0);
							cardview.setBackgroundResource(R.drawable.card_view_border);
							
							parent.removeView(view);
							
							cardview.addView(view);
							
							parent.addView(cardview);
						}
						inflate.setTag(url);
						inflate.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								url = view.getTag().toString();
								intent.setClass(getApplicationContext(), BrowseActivity.class);
								intent.putExtra("searchFor", url);
								startActivity(intent);
							}});
						linear18.addView(inflate);
					}
				} catch (final org.json.JSONException e) {
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
		androidx.cardview.widget.CardView cardview1 = new androidx.cardview.widget.CardView(HomeActivity.this);
		cardview1.setRadius(180);
		cardview1.setCardElevation(0);
		
		linear4.removeView(linear5);
		
		cardview1.addView(linear5);
		
		linear4.addView(cardview1);
		cardview1.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
		rn.startRequestNetwork(RequestNetworkController.GET, "https://newsapi.org/v2/everything?q=hacker OR hacking OR cybersecurity OR keamanan%20siber OR peretasan&language=id&sortBy=publishedAt&pageSize=50&apiKey=de5c55929d1c4d4fb2e8a5d3c68c8956", "A", _rn_request_listener);
		_cardView(linear9, imageview3, 30);
		_cardView(linear10, imageview4, 30);
		_cardView(linear11, imageview5, 30);
		_cardView(linear12, imageview6, 30);
		_cardView(linear14, imageview7, 30);
		_cardView(linear15, imageview8, 30);
		_cardView(linear16, imageview9, 30);
		_cardView(linear17, imageview10, 30);
		edittext1.setSingleLine(true);
		if (sp.getString("current-tab", "").equals("")) {
			sp.edit().putString("current-tab", "1").commit();
			mapvar = new HashMap<>();
			mapvar.put("tab-index", "1");
			mapvar.put("searchFor", "cwhome");
			tabs.add(mapvar);
			sp.edit().putString("tabs", new Gson().toJson(tabs)).commit();
			textview2.setText("1");
		} else {
			if (!sp.getString("tabs", "").equals("")) {
				tabs = new Gson().fromJson(sp.getString("tabs", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			}
			textview2.setText(sp.getString("current-tab", ""));
		}
		edittext1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
					intent.setClass(getApplicationContext(), BrowseActivity.class);
					intent.putExtra("searchFor", edittext1.getText().toString());
					startActivity(intent);
					return true;
				}
				return false;
			}});
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
		SketchwareUtil.showMessage(getApplicationContext(), "makasih telah memakai apk ini☺️");
	}
	public void _cardView(final View _parent, final View _view, final double _radius) {
		ViewGroup parent = (ViewGroup)_parent;
		
		androidx.cardview.widget.CardView cardview = new androidx.cardview.widget.CardView(HomeActivity.this);
		
		cardview.setRadius((int)_radius);
		cardview.setCardElevation(0);
		cardview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		
		parent.removeView(_view);
		
		cardview.addView(_view);
		
		parent.addView(cardview);
	}
	
	
	public void _menu() {
		PopupMenu popup = new PopupMenu(HomeActivity.this,linear20);
		Menu menu = popup.getMenu();
		menu.add("tab baru" );
		menu.add("History");
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClick(MenuItem item){
				switch (item.getTitle().toString()){
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
	
	
	public void _CodeWare() {
		intent.setClass(getApplicationContext(), BrowseActivity.class);
		intent.putExtra("searchFor", "https://codewarestudios.blogspot.com/2020/10/blog-post.html");
		startActivity(intent);
	}
	
}