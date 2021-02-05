package com.amazon.gdpr.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class TagInputDaoImplTest {
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	TagInputDaoImpl tagInputDaoImpl;

	
	@Before
	public void setUp() throws Exception {
	    MockitoAnnotations.initMocks(this);
	    ReflectionTestUtils.setField(tagInputDaoImpl ,"jdbcTemplate" , 
	    		jdbcTemplate);

	}
	
	@Test
	public void batchInsertTagTablesTest() {
		Object arg="";
		tagInputDaoImpl.batchInsertTagTables(arg);
	}
}
