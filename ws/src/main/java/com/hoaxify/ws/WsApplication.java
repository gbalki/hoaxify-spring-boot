package com.hoaxify.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.hoaxify.ws.hoax.HoaxService;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;
import com.hoaxify.ws.hoax.Hoax;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}
	
	@Bean
	CommandLineRunner createInitialUsers(UserService userService , HoaxService hoaxService) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				for(int i=1; i<=25;i++) {
					User user =new User();
					user.setUsername("user"+i);
					user.setDisplayName("display"+i);
					user.setPassword("password");
					userService.save(user);
					for(int j=1; j<=20;j++ ) {
						Hoax hoax = new Hoax();
						hoax.setContent("hoax ("+j+") from user("+i+")");
						hoaxService.save(hoax,user);
					}
				}
			}
			
		};
	}
	
}
