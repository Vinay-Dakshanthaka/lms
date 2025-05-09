package com.totfd.lms.service;

public interface EmailService {
    void  sendVerificationEmail(String email, String token);
}
