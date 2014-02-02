package de.tgehring.coins.app;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.tgehring.coins.app.model.CoinDto;
import de.tgehring.coins.app.model.CountryDto;
import de.tgehring.coins.app.model.cache.CoinList;
import de.tgehring.coins.app.model.filter.CoinFilterChain;
import de.tgehring.coins.app.model.filter.CountryFilter;
import de.tgehring.coins.app.model.filter.ValueFilter;
import de.tgehring.coins.app.model.filter.YearFilter;
import de.tgehring.coins.app.service.RemoteService;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private final String COUNTRIES = "country";
	private final String COINS = "coin";
	private List<String> countries = new LinkedList<String>();
	private List<CoinDto> coins = new LinkedList<CoinDto>();
	private EditText valueEdit;
	private EditText yearEdit;
	private Spinner countrySpinner;
	private Switch commemorativeSwitch;
	private Button searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.valueEdit = (EditText) findViewById(R.id.valueEdit);
		this.yearEdit = (EditText) findViewById(R.id.yearEdit);
		this.countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
		this.commemorativeSwitch = (Switch) findViewById(R.id.commemorativeSwitch);
		this.searchButton = (Button) findViewById(R.id.searchButton);
		this.searchButton.setOnClickListener(this);
		
		recieveCountries();
		//recieveCoins();
	}
	
	private void recieveCountries() {
		Thread loader = new Thread() {
			public void run() {
				final String countriesJsonData = RemoteService.getInstance().getCountries();
				MainActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mapCountries(countriesJsonData);
					}
					
				});
			}
		};
		loader.start();
	}
	
	private void mapCountries(String countriesJsonData) {
		try {
			JSONObject jsonObject = new JSONObject(countriesJsonData);
			JSONArray countryData = jsonObject.getJSONArray(COUNTRIES);
			for (int i = 0; i < countryData.length(); i++) {
				CountryDto country = CountryDto.mapFromJson(countryData.getJSONObject(i));
				countries.add(country.name);
			}
		} catch (JSONException exception) {
			Log.e("Recieving(Country)", exception.getMessage());
		}
		if (this.countries.size() > 0) {
			setCountries();
		}
	}
	
	private void setCountries() {
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, this.countries);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		countrySpinner.setAdapter(dataAdapter);
		
		recieveCoins();
	}
	
	private void recieveCoins() {
		Thread loader = new Thread() {
			public void run() {
				final String coinsJsonData = RemoteService.getInstance().getCoins();
				MainActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mapCoins(coinsJsonData);
					}
					
				});
			}
		};
		if (CoinList.getInstance().getSize() > 0) {
			this.coins = CoinList.getInstance().getCoinList();
		} else {
			loader.start();
		}
		
	}
	
	private void mapCoins(String coinsJsonData) {
		try {
			JSONObject jsonObject = new JSONObject(coinsJsonData);
			JSONArray countryData = jsonObject.getJSONArray(COINS);
			for (int i = 0; i < countryData.length(); i++) {
				CoinDto coin = CoinDto.mapFromJson(countryData.getJSONObject(i));
				coins.add(coin);
			}
		} catch (JSONException exception) {
			Log.e("Recieving(Coin)", exception.getMessage());
		}
		if (this.coins.size() > 0) {
			CoinList coinList = CoinList.getInstance();
			coinList.setCoinList(coins);
		}
	}

	@Override
	public void onClick(View v) {
		CoinFilterChain filterChain = new CoinFilterChain();
		if (valueEdit.getText().toString().length() > 0) {
			double value = Double.valueOf(valueEdit.getText().toString());
			filterChain.addFilter(new ValueFilter(value));
		}
		if (yearEdit.getText().toString().length() > 0) {
			int year = Integer.valueOf(yearEdit.getText().toString());
			filterChain.addFilter(new YearFilter(year));
		}
		String countryName = countrySpinner.getSelectedItem().toString();
		filterChain.addFilter(new CountryFilter(countryName));
		
		List<CoinDto> filteredCoins = filterChain.applyFilter(coins);
		Toast.makeText(this, "found: " + filteredCoins.size() + " of " + coins.size(), Toast.LENGTH_SHORT).show();
	}

}
