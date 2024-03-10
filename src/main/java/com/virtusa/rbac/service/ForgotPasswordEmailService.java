package com.virtusa.rbac.service;

import com.virtusa.rbac.dto.ForgotPasswordEmailDto;

public interface ForgotPasswordEmailService {

    String sendForgotPasswordEmail(ForgotPasswordEmailDto emailDto);
}
