package org.example.services.EmailSend;

import com.aspose.email.*;

import java.nio.charset.Charset;

public class EmailSend {
    private String addresFrom="taskhub.sup@outlook.com";
    final String password = "taskhub123";

    public void sendEmail(String addresTo,String subject,String htmlBody){
        MailMessage message = new MailMessage();
     // Set subject of the message, body and sender information
        message.setSubject(subject);
        message.setHtmlBody(htmlBody);

        message.setFrom(new MailAddress(addresFrom, "Task Hub", false));
     // Specify encoding
        message.setBodyEncoding(Charset.forName("US-ASCII"));
    // Add To recipients and CC recipients
        message.getTo().addItem(new MailAddress(addresTo, addresTo, false));
     // Create an instance of SmtpClient Class
        SmtpClient client = new SmtpClient();
      // Specify your mailing host server, Username, Password, Port
        client.setHost("smtp-mail.outlook.com");
        client.setUsername(addresFrom);
        client.setPassword(password);
        client.setPort(587);
        try {
            client.send(message);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void sendEmailWithImage2(String addresTo,String subject,String htmlBody,String imagePath){
        MailMessage message = new MailMessage();
        // Set subject of the message, body and sender information
        message.setSubject(subject);
        message.setHtmlBody(htmlBody);
        String dataDir = "";
        LinkedResource res = new LinkedResource(dataDir + imagePath, MediaTypeNames.Image.JPEG);
        res.setContentId("companylogo");
        message.getLinkedResources().addItem(res);
        message.setFrom(new MailAddress(addresFrom, "Task Hub", false));
        // Specify encoding
        message.setBodyEncoding(Charset.forName("US-ASCII"));
        message.getTo().addItem(new MailAddress(addresTo, addresTo, false));
        SmtpClient client = new SmtpClient();
        client.setHost("smtp-mail.outlook.com");
        client.setUsername(addresFrom);
        client.setPassword(password);
        client.setPort(587);
        try {
            client.send(message);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}

