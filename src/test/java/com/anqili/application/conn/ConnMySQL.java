package com.anqili.application.conn;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnMySQL {
	@Autowired
	DataSource dataSource;
	
	@Test
	public void test() {
			try {
				
				if(dataSource.getConnection().isValid(1000))
					System.out.println("MySQL connected.");
				else
					System.out.println("MySQL not connected.");
			} catch (SQLException e) {
				System.out.println(e.getCause());
			}

	}
}
