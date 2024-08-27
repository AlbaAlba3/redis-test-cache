package com.example.test_redis_cache;

import com.example.test_redis_cache.model.User;
import com.example.test_redis_cache.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
public class TestRedisCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestRedisCacheApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(UserRepository userRepository) {
		return args -> {
			// Убедитесь, что база данных пуста перед добавлением данных
			userRepository.deleteAll();

			// Создаем 100 пользователей
			IntStream.range(0, 100).forEach(i -> {
				String name = "User" + i;
				String email = "user" + i + "@example.com";
				User user = new User(name, email);
				userRepository.save(user);
			});

			System.out.println("100 users have been created and saved in the database.");
		};
	}


}
