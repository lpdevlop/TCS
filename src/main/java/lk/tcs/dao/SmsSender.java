package lk.tcs.dao;

import lk.tcs.controller.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}
