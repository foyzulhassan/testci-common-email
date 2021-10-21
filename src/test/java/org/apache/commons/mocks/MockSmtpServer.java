package org.apache.commons.mocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.configs.EmailConfiguration;
import org.junit.After;
import org.junit.Before;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class MockSmtpServer {

	/** The fake Wiser email server */
	private Wiser mockMailServer = null;

	private int mailServerPort;

	/** Where to save email output **/
	private File emailOutputDir;

	public MockSmtpServer() {
		mailServerPort = EmailConfiguration.MAIL_SERVER_PORT;
	}

	public Wiser getMockMailServer() {
		return mockMailServer;
	}

	public MockSmtpServer(int port) {
		this.mailServerPort = port;
	}

	public int getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(int mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	@Before
	public void setUpAbstractEmailTest() {
		emailOutputDir = new File("target/test-emails");
		if (!emailOutputDir.exists()) {
			emailOutputDir.mkdirs();
		}
	}

	@After
	public void tearDownEmailTest() {
		// stop the fake email server (if started)
		if (this.mockMailServer != null && !isMailServerStopped(mockMailServer)) {
			this.mockMailServer.stop();
			assertTrue("Mail server didn't stop", isMailServerStopped(mockMailServer));
		}

		this.mockMailServer = null;
	}

	public void startSmtpMailServer() {
		if (this.mockMailServer == null || isMailServerStopped(mockMailServer)) {

			this.mockMailServer = new Wiser();
			this.mockMailServer.setPort(this.mailServerPort);
			this.mockMailServer.start();

			/// assertFalse("fake mail server didn't start",
			/// isMailServerStopped(mockMailServer));

			Date dtStartWait = new Date();
			while (isMailServerStopped(mockMailServer)) {
				// test for connected
				if (this.mockMailServer != null && !isMailServerStopped(mockMailServer)) {
					break;
				}

				// test for timeout
				if ((dtStartWait.getTime() + EmailConfiguration.TIME_OUT) <= new Date().getTime()) {

				}
			}
		}
	}

	public void stopSmtpMailServer() {

		if (this.mockMailServer != null) {
			if (mockMailServer.getServer().isRunning()) {
				this.mockMailServer.stop();
			}
		}
	}

	protected boolean isMailServerStopped(Wiser mailserver) {
		return !mailserver.getServer().isRunning();
	}

	public String getMailSubject() {
		String subject = "";
		if (this.mockMailServer != null) {
			if (mockMailServer.getServer().isRunning()) {

				if (mockMailServer.getMessages().size() >= 1) {
					WiserMessage emailMessage = mockMailServer.getMessages().get(0);
					try {
						// get the MimeMessage
						MimeMessage mimeMessage = emailMessage.getMimeMessage();

						subject = mimeMessage.getHeader("Subject", null);
					} catch (MessagingException me) {
						IllegalStateException ise = new IllegalStateException(
								"caught MessagingException in validateSend()");
						ise.initCause(me);
						throw ise;
					}

				}

			}
		}
		
		return subject;
	}

}
