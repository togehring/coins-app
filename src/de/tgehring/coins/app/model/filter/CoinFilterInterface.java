package de.tgehring.coins.app.model.filter;

import java.util.List;

import de.tgehring.coins.app.model.CoinDto;

public interface CoinFilterInterface {
	public List<CoinDto> applyFilter(List<CoinDto> coins);
}