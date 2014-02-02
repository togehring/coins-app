package de.tgehring.coins.app;

import org.json.JSONArray;
import org.json.JSONObject;

import de.tgehring.coins.app.service.RemoteService;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	private final String COUNTRIES = "country";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getCountries();
	}
	
	private void getCountries() {
		Thread loader = new Thread() {
			public void run() {
				final String countriesJsonData = RemoteService.getInstance().getCountries();
				setCountries(countriesJsonData);
			}
		};
		loader.start();
	}
	
	private void setCountries(String countriesJsonData) {
		try {
			JSONObject jsonObject = new JSONObject(countriesJsonData);
			System.out.println(countriesJsonData);
			JSONArray countries = jsonObject.getJSONArray(COUNTRIES);
			// looping through All Contacts
			for (int i = 0; i < countries.length(); i++) {
				JSONObject countryData = countries.getJSONObject(i);
				System.out.println(countryData);
				String id = countryData.getString("id");
				String name = countryData.getString("name");
			}
		} catch (Exception exception) {
			Log.e("EXCN", exception.getMessage());
		}
	}

}
