package de.tgehring.coins.app.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CoinDto {
	public long id;
	public double value;
	public int year;
	public CountryDto country;
	public char letter;
	public boolean commemorative;
	
	public CoinDto() {
		
	}
	
	public static CoinDto mapFromJson(JSONObject jsonData) {
		CoinDto result = null;
		try {
			result = new CoinDto();
			result.id = jsonData.getLong("id");
			result.value = jsonData.getDouble("value");
			result.year = jsonData.getInt("year");
			result.country = CountryDto.mapFromJson(jsonData.getJSONObject("country"));
			result.letter = jsonData.getString("letter").charAt(0);
			result.commemorative = jsonData.getBoolean("commemorative");
		} catch (JSONException exception) {
			Log.e("Map(Country)", exception.getMessage());
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("[id:%s] ", id));
		sb.append(String.format("%s € ", value));
		sb.append(String.format("von %s ", year));
		sb.append(String.format("aus %s ", country.name));
		sb.append(String.format("[%s]", letter));
		
		return sb.toString();
	}
}