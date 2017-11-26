package eu.qms.qms;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    // Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "defozo@gmail.com";
    static final String FROMNAME = "Queue Management System";

    // Replace smtp_username with your Amazon SES SMTP user name.
    static final String SMTP_USERNAME = "AKIAJFJO5NJZXQU7TIXA";

    // Replace smtp_password with your Amazon SES SMTP password.
    static final String SMTP_PASSWORD = "AgG2zJqBkwlcY7Ahd0+sJixE6HiapvrC9aQVJEjWBcv/";

    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    //static final String CONFIGSET = "ConfigSet";

    // Amazon SES SMTP host name. This example uses the US West (Oregon) Region.
    static final String HOST = "email-smtp.eu-west-1.amazonaws.com";

    // The port you will connect to on the Amazon SES SMTP endpoint.
    static final int PORT = 587;

    static final String SUBJECT = "Queue Management System confirmation e-mail";

    public static void send(String emailAddress, Integer studentId, String reservationToken) throws Exception {
        String TO = emailAddress;

        String BODY = String.join(
                System.getProperty("line.separator"),
                "<h1>Queue Management System</h1>",
                "<p>To complete reservation request please ",
                "<a href='https://77c36733.ngrok.io/reservation/confirm/",
                studentId.toString(),
                "/",
                reservationToken,
                "'>click here</a>."
        );

        /*String BODY = "<html>\n" +
                "<head>\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
                "\n" +
                "    <!-- Latest compiled and minified CSS -->\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"\n" +
                "          integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "    <!-- Optional theme -->\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\"\n" +
                "          integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "    <!-- Latest compiled and minified JavaScript -->\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"\n" +
                "            integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\"\n" +
                "            crossorigin=\"anonymous\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h2 class=\"lead\">Queue Management System</h2>\n" +
                "    <p class=\"lead\">Witaj,</p>\n" +
                "    <p class=\"text-justify\">Zarejestrowałeś się w kolejce do prodziekana.</p>\n" +
                "    <p class=\"text-justify\">Proszę o potwierdzenie przybycia klikając w przycisk:</p>\n" +
                "    <br/>\n" +
                "    <a href=\"https://77c36733.ngrok.io/reservation/" + studentId + "/" + reservationToken + "\" class=\"btn btn-lg btn-success\">Potwierdź</a>\n" +
                "    <hr/>\n" +
                "    <address>\n" +
                "        <strong>Nieistniejący dziekanat</strong><br/>\n" +
                "        ul. Gdzieś 3<br/>\n" +
                "        11-111 Świat<br/>\n" +
                "        Tel.: 12 123 12 23<br/>\n" +
                "    </address>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";*/

        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");

        // Add a configuration set header. Comment or delete the
        // next line if you are not using a configuration set
       // msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            System.out.println("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
