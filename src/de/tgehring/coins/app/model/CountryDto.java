package de.tgehring.coins.app.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CountryDto {
	public long id;
	public String name;
	
	public CountryDto() {
		
	}
	
	public static CountryDto mapFromJson(JSONObject jsonData) {
		CountryDto result = null;
		try {
			result = new CountryDto();
			result.id = jsonData.getLong("id");
			result.name = jsonData.getString("name");
		} catch (JSONException exception) {
			Log.e("Map(Country)", exception.getMessage());
		}
		
		return result;
	}
}
