package com.freeloader.restapi.loader;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component

public class DataLoader implements SmartInitializingSingleton {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public void afterSingletonsInstantiated() {
		jdbcTemplate.update("insert into ADDRESS (ID, STREET_ADDRESS, CITY, STATE, COUNTRY, ZIP_CODE) values (?, ?, ?, ?, ?, ?)", new Object[]{0, "1313 Mockingbird Lane", "Any Town", "MI", "USA", "48230"});
		
		jdbcTemplate.update("insert into USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, ADDRESS_ID) values (?, ?, ?, ?, ?)", new Object[]{1001, "Herman", "Munster", "herman@gmail.com", 0});
		
		jdbcTemplate.update("insert into PHONE (ID, PHONE_NUMBER, TYPE) values (?, ?, ?)", new Object[]{2001, "313-333-3333", "Mobile"});
		jdbcTemplate.update("insert into PHONE (ID, PHONE_NUMBER, TYPE) values (?, ?, ?)", new Object[]{2002, "313-333-3334", "Work"});
		jdbcTemplate.update("insert into PHONE (ID, PHONE_NUMBER, TYPE) values (?, ?, ?)", new Object[]{2003, "313-333-3344", "Mobile"});
		
		jdbcTemplate.update("insert into USER_PHONE (USER_ID, PHONE_ID) values (?, ?)", new Object[]{1001, 2001});
		jdbcTemplate.update("insert into USER_PHONE (USER_ID, PHONE_ID) values (?, ?)", new Object[]{1001, 2002});
		
	}
}