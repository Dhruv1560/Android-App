package com.example.dhruvpatel.login;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OTP extends AsyncTask<String, String, String> {
    Context context;
    Properties pro;
    Session session;
    MimeMessage mMsg;
    String ToEmail, Tosubject, Tomessage;

    public OTP(Context context, String s_signup_email, String s, String s1) {
        this.context = context;
        this.ToEmail = s_signup_email;
        this.Tosubject = s;
        this.Tomessage = s1;
    }

    @Override
    protected String doInBackground(String... strings) {
        pro = new Properties();
        pro.put(Constant.Smtphostkey, Constant.SmatphostValue);
        pro.put(Constant.Sslsocketportkey, Constant.SslsocketportValue);
        pro.put(Constant.Sslclassfactorykey, Constant.SslclassfactoryValue);
        pro.put(Constant.Sslauthnticationkey, Constant.SslauthnticationValue);
        pro.put(Constant.Sslsmtpportkey, Constant.SslsmtpportValue);
        session =Session.getDefaultInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constant.ADMINEmail,Constant.ADMINPassword);
            }
        });
        try {
            mMsg = new MimeMessage(session);
            mMsg.setFrom(new InternetAddress(Constant.ADMINEmail));
            mMsg.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(ToEmail)));
            mMsg.setSubject(Tosubject);
            mMsg.setText(Tomessage);
            Transport.send(mMsg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
