package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//import org.apache.commons.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class EmailTest {
	
	private static final String[] TEST_EMAILS = {
			"ab@bc.com",
			"a.b@bc.com",
			"vrejbi3u4r43fg34@fkjh43boi8g9qph0.com.bd",
			"chamdan@umich.edu"
	};
	
	private String[] testValidChars = { " ", "a", "A", "\uc5ec", "0123456789", "\n"};
	
	private String testHostName = "localhost";
	
	private int x = 0;
	
	private Date testDate;
	
	private EmailConcrete email;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
		testDate = new Date();
	}
	
	@After
	public void tearDownEmailTest() throws Exception {
		email = null;
	}
	
	@Test
	public void testAddBcc() throws Exception {
		email.addBcc(TEST_EMAILS);
		
		assertEquals(5, email.getBccAddresses().size());
	}
	
	@Test
	public void testAddCc() throws Exception {
		email.addCc(TEST_EMAILS);
		
		assertEquals(4, email.getCcAddresses().size());
	}

	@Test
	public void testAddHeader() throws Exception {
		email.addHeader("AuthenticationKey", "07606bb6");
		
		// Test will pass if exception is thrown
		exception.expect(IllegalArgumentException.class);
		email.addHeader("AuthenticationKey", "");
	}
	
	@Test
	public void testAddReplyTo() throws Exception {
		// Testing addReplyTo with single parameter will test all overloads
		for (String s : TEST_EMAILS) {
			email.addReplyTo(s);
		}
		
		assertEquals(4, email.getReplyToAddresses().size());
	}
	
	@Test
	public void testGetHostName() throws Exception {
		// Check with no host name
		assertEquals(null, email.getHostName());
		
		// Check with host name
		email.setHostName(testHostName);
		
		assertEquals(testHostName, email.getHostName());
	}
	
	@Test
	public void testGetMailSession() throws Exception {
		email.setHostName(testHostName);
		
		email.getMailSession();
	}
	
	@Test
	public void testGetSentDate() throws Exception {
		email.setSentDate(testDate);
		
		assertEquals(testDate, email.getSentDate());
	}
	
	@Test
	public void testSocketConnectionTimeout() throws Exception {
		email.setSocketTimeout(60000);
		
		assertEquals(60000, email.getSocketConnectionTimeout());
		
		// Set a connection timeout first
	}
	
	@Test
	public void testSetFrom() throws Exception {
		for (String s : TEST_EMAILS) {
			assertEquals(email, email.setFrom(s));
		}
	}
}
