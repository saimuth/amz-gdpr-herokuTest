package com.amazon.gdpr.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazon.gdpr.dao.GdprInputDaoImpl.ImpactTableRowMapper;
import com.amazon.gdpr.dao.GdprInputFetchDaoImpl.CountryRowMapper;
import com.amazon.gdpr.model.gdpr.input.Country;
import com.amazon.gdpr.model.gdpr.input.ImpactTable;

public class GdprInputFetchDaoImplTest {

	@Mock
	JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	GdprInputFetchDaoImpl gdprInputFetchDaoImpl;

	
	@Before
	public void setUp() throws Exception {
	    MockitoAnnotations.initMocks(this);
	    ReflectionTestUtils.setField(gdprInputFetchDaoImpl ,"jdbcTemplate" , 
	    		jdbcTemplate);

	}
	@Test
	public void fetchImpactTableTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(ImpactTableRowMapper.class))).thenReturn(lstImpactTable());
		List<ImpactTable> lstImpactTableRes =gdprInputFetchDaoImpl.fetchImpactTable();
		assertEquals(lstImpactTable().size(), lstImpactTableRes.size());
	}
	@Test
	public void fetchAllCountriesTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(CountryRowMapper.class))).thenReturn(mockCountryDetails());
		List<Country> lstCountryRes =gdprInputFetchDaoImpl.fetchAllCountries();
		assertEquals(mockCountryDetails().size(), lstCountryRes.size());
	}
	@Test
	public void fetchCountryTest() {
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		Mockito.when(jdbcTemplate.query(any(String.class),any(Object[].class) ,any(CountryRowMapper.class))).thenReturn(mockCountryDetails());
		List<Country> res=gdprInputFetchDaoImpl.fetchCountry(selectedCountries);
		assertEquals(mockCountryDetails().size(), res.size());
	}
	
	

	private List<Country> mockCountryDetails() {
		List<Country> country = new ArrayList<Country>();

		Country country2 = new Country("EUR-EU", "AUT");
		Country country3 = new Country("EUR-EU", "BEL");
		/*
		 * Country country4 = new Country("EUR-EU","CHE"); Country country5 = new
		 * Country("EUR-EU","CZE"); Country country6 = new Country("EUR-EU","DEU");
		 * Country country7 = new Country("EUR-EU","DNK"); Country country8 = new
		 * Country("EUR-EU","ESP"); Country country9 = new Country("EUR-EU","FIN");
		 * Country country10 = new Country("EUR-EU","FRA");
		 * 
		 * Country country11 = new Country("EUR-EU","GBR"); Country country12 = new
		 * Country("EUR-EU","HRV"); Country country13 = new Country("EUR-EU","ITA");
		 * Country country14 = new Country("EUR-EU","LUX"); Country country15 = new
		 * Country("EUR-EU","NLD"); Country country16 = new Country("EUR-EU","NOR");
		 * Country country17 = new Country("EUR-EU","POL"); Country country18 = new
		 * Country("EUR-EU","PRT"); Country country19 = new Country("EUR-EU","ROI");
		 * Country country20 = new Country("EUR-EU","ROM"); Country country21 = new
		 * Country("EUR-EU","SVK"); Country country22 = new Country("EUR-EU","SWE");
		 * country.add(country22); country.add(country21); country.add(country20);
		 * country.add(country19); country.add(country18); country.add(country17);
		 * country.add(country16); country.add(country15); country.add(country14);
		 * country.add(country13); country.add(country12); country.add(country11);
		 * 
		 * country.add(country10); country.add(country9); country.add(country8);
		 * country.add(country7); country.add(country6); country.add(country5);
		 * country.add(country4);
		 */
		country.add(country3);
		country.add(country2);

		return country;
	}
	
	private List<ImpactTable> lstImpactTable(){
		List<ImpactTable> lstImpactTable = new ArrayList<ImpactTable>();
		ImpactTable impactTable1 = new ImpactTable(2, "TAG", "APPLICATION__C", "ID", 
				"CHILD", "GDPR", "GDPR_DEPERSONALIZATION", "CANDIDATE__C", 
				"CANDIDATE__C");
		ImpactTable impactTable2 = new ImpactTable(3, "TAG", "INTERVIEW__C", "ID", 
				"CHILD", "GDPR", "GDPR_DEPERSONALIZATION", "CANDIDATE__C", 
				"CANDIDATE__C");
		lstImpactTable.add(impactTable1);
		lstImpactTable.add(impactTable2);
		return lstImpactTable;
	}
}
