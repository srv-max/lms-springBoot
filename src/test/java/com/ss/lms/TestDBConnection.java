package com.ss.lms;


import java.sql.Connection;
import java.sql.Statement;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ss.lms.service.ConnectionUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDBConnection {
	 @InjectMocks private ConnectionUtil connectionUtil;
	  @Mock private Connection mockConnection;
	  @Mock private Statement mockStatement;
	  
	@BeforeEach
	  public void setUp() {
	    MockitoAnnotations.initMocks(this);
	  }
	 
	  @Test
	  public void testMockDBConnection() throws Exception {
		  Mockito.when(mockConnection.createStatement())
	         .thenReturn(mockStatement);
	      Mockito.when(mockConnection.createStatement()
	         .executeUpdate(Mockito.anyString())).thenReturn(1);
	      boolean database = connectionUtil.connectDatabase().createStatement().execute("select * from tbl_author");
	    
	    		  Assertions.assertEquals(true,database);
	      Mockito.verify(mockConnection.createStatement(),
	         Mockito.times(1));
	  }
	
	
	
}
