package com.utc2.it.Ecommerce.service;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import com.utc2.it.Ecommerce.dto.SignInRequest;
import com.utc2.it.Ecommerce.dto.SignUpRequest;
import com.utc2.it.Ecommerce.dto.UserDto;

import java.io.IOException;

public interface AuthService {
    UserDto CreateUser(SignUpRequest signUp);
    void signin(SignInRequest request, HttpServletResponse httpServletResponse) throws IOException, JSONException;
    UserDto getCurrentUser();
}
