package com.gestao.mimoso;

import ch.qos.logback.core.net.SocketConnector;
import com.gestao.mimoso.model.Role;
import com.gestao.mimoso.model.User;
import com.gestao.mimoso.model.Venda;
import com.gestao.mimoso.repository.UserRepo;
import com.gestao.mimoso.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.gestao.mimoso.config.DateConfig.DATETIME_FORMAT;
import static com.gestao.mimoso.config.DateConfig.LOCAL_DATETIME_SERIALIZER;


@Controller
@SpringBootApplication
public class MimosoApplication {

	private final UserRepo userRepo;

	public MimosoApplication(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(MimosoApplication.class, args);
	}

	@GetMapping("/")
	public String claudio (){
		return "Claudio Caldeira";
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args->{

			userService.saveRole(new Role(null, "ROLE_USER",  LocalDate.now()));
			userService.saveRole(new Role(null, "ROLE_MANAGER", LocalDate.now()));
			userService.saveRole(new Role(null, "ROLE_ADMIN", LocalDate.now()));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN", LocalDate.now()));
			userService.saveRole(new Role(null, "ROLE_COUNTER", LocalDate.now()));
			userService.saveRole(new Role(null, "ROLE_PROGRAMMER", LocalDate.now()));

			userService.saveUser(new User(null, "Claudio","claudio", "1234", LocalDate.now(), new ArrayList<Role>()));
			userService.saveUser(new User(null, "Caldeira","caldeira", "1234",LocalDate.now(), new ArrayList<Role>()));
			userService.saveUser(new User(null, "Antonio","antonio", "1234",LocalDate.now(), new ArrayList<Role>()));
			userService.saveUser(new User(null, "Sergio","sergio", "1234",LocalDate.now(), new ArrayList<Role>()));

		};

	}

}
