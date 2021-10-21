package org.apache.commons.configs;


import org.apache.commons.mail.EmailConstants;


public final class EmailConfiguration
{
    public static final boolean MAIL_FORCE_SEND                 = false;
    public static final boolean MAIL_DEBUG                      = false;
    public static final String  MAIL_CHARSET                    = EmailConstants.UTF_8;
    public static final String  MAIL_SERVER                     = "localhost";
    public static final int     MAIL_SERVER_PORT                = 25;
    public static final String  TEST_FROM                       = "test_from@abc.com";
    public static final String  TEST_TO                         = "test_to@abc.com";
    public static final String  TEST_USER                       = "user";
    public static final String  TEST_PASSWD                     = "password";

    public static final boolean MAIL_USE_SSL                    = false;
    public static final boolean MAIL_SSL_CHECKSERVERIDENTITY    = false;
    public static final boolean MAIL_USE_STARTTLS               = true;
    public static final boolean MAIL_STARTTLS_REQUIRED          = true;
    public static final int     MOCK_SMTP_PORT			        = 2500;  
    public static final int 	TIME_OUT = 500;
}
