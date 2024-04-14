package com.utc2.it.Ecommerce;

import com.utc2.it.Ecommerce.entity.Role;
import com.utc2.it.Ecommerce.entity.User;
import com.utc2.it.Ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RequiredArgsConstructor
@SpringBootApplication
public class EcommerceApplication {
	private final UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	public void run(String... args){
		User adminCreate = userRepository.findByRole(Role.Admin);
		if(adminCreate==null){
			User admin= new User();
			admin.setFirstName("Nguyen Phuoc");
			admin.setLastName("Hung");
			admin.setEmail("admin@gmail.com");
			admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			admin.setPhoneNumber("0399333643");
			admin.setRole(Role.Admin);
			userRepository.save(admin);
		}
	}

}
