package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.dto.SignInRequest;
import com.utc2.it.Ecommerce.dto.SignUpRequest;
import com.utc2.it.Ecommerce.dto.UserDto;
import com.utc2.it.Ecommerce.entity.Role;
import com.utc2.it.Ecommerce.entity.Token;
import com.utc2.it.Ecommerce.entity.User;
import com.utc2.it.Ecommerce.repository.TokenRepository;
import com.utc2.it.Ecommerce.repository.UserRepository;
import com.utc2.it.Ecommerce.utils.JwtUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository authRepository;
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final UserDetailServiceImpl userDetailService;
    private final UserRepository userRepository;
    public static final String HEADER="Authorization";
    public static final String TOKEN="Bearer ";
    @Override
    public UserDto CreateUser(SignUpRequest signUp) {
        User user= new User();
        user.setEmail(signUp.getEmail());
        user.setFirstName(signUp.getFirstName());
        user.setImage("avatar.jpg");
        user.setLastName(signUp.getLastName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUp.getPassword()));
        user.setRole(Role.User);
        authRepository.save(user);
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }


    @Override
        public Long updateUser(SignUpRequest signUp)throws IOException {
        String username = getCurrentUsername();
        User user=getUser(username);
        user.setPhoneNumber(signUp.getPhoneNumber());
        user.setEmail(signUp.getEmail());
        user.setFirstName(signUp.getFirstName());
        user.setLastName(signUp.getLastName());
        userRepository.save(user);
        return user.getId();
    }
    @Override
    public void saveUserImage(Long userId, String imageName) {
        User user=userRepository.findById(userId).orElseThrow(()->new NotFoundException("Not found product "));
        user.setImage(imageName);
        userRepository.save(user);
    }

    @Override
    public void signin(SignInRequest request, HttpServletResponse httpServletResponse) throws IOException, JSONException {
        UserDetails userDetails=userDetailService.userDetailsService().loadUserByUsername(request.getEmail());
        Optional<User> optionalUser=userRepository.findByEmail(userDetails.getUsername());
        User user=userRepository.findUserByEmail(userDetails.getUsername());
        String jwt=jwtUtils.generateToken(userDetails);
        List<Token>validTokenIsByUser=tokenRepository.findAllTokenByUser(user.getId());
        if(!validTokenIsByUser.isEmpty()){
            validTokenIsByUser.forEach(t->t.setLogout(true));
        }
        tokenRepository.saveAll(validTokenIsByUser);
        saveToken(user, jwt);
        if(optionalUser.isPresent()){
            httpServletResponse.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.get().getId())
                    .put("role",optionalUser.get().getRole())
                    .put("token",jwt)
                    .toString()
            );
            httpServletResponse.setHeader(HEADER,TOKEN+jwt);
        }
    }

    @Override
    public UserDto getCurrentUser() {
      String username=getCurrentUsername();
      User user=getUser(username);
      if(user==null){
          return null;
      }
      UserDto userDto=new UserDto();
      userDto.setId(user.getId());
      userDto.setFirstName(user.getFirstName());
      userDto.setLastName(user.getLastName());
      userDto.setEmail(user.getEmail());
      userDto.setPhoneNumber(user.getPhoneNumber());
      userDto.setRole(user.getRole().name());
      userDto.setImage(user.getImage());
      return userDto;

    }
    private User getUser(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Trả về tên người dùng hiện đang đăng nhập
    }

    private void saveToken(User User, String token) {
        Token token1= new Token();
        token1.setToken(token);
        token1.setLogout(false);
        token1.setUser(User);
        tokenRepository.save(token1);
    }

}