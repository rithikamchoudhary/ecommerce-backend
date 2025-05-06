package com.ecommerce.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner init(ProductRepository repo) {
		return args -> {
			// if (repo.count() == 0) {
			repo.save(new Product("Smart Watch", "Tracks health and time.", 3499,
					"https://picsum.photos/200?random=2"));
			repo.save(new Product("Bluetooth Speaker", "Loud and portable.", 1499,
					"https://picsum.photos/200?random=3"));
			repo.save(new Product("Wireless Earbuds", "Noise-cancelling.", 2499,
					"https://picsum.photos/200?random=4"));
			repo.save(new Product("Smartphone", "Latest model with 5G.", 59999,
					"https://picsum.photos/200?random=5"));
			repo.save(new Product("Laptop", "High performance for gaming.", 89999,
					"https://picsum.photos/200?random=6"));
			repo.save(new Product("Tablet", "Portable and powerful.", 29999,
					"https://picsum.photos/200?random=7"));
			repo.save(new Product("Smart TV", "4K Ultra HD.", 49999,
					"https://picsum.photos/200?random=8"));
			repo.save(new Product("Gaming Console", "Next-gen gaming.", 39999,
					"https://picsum.photos/200?random=9"));
			repo.save(new Product("Headphones", "Over-ear with deep bass.", 1999,
					"https://picsum.photos/200?random=10"));
			repo.save(new Product("Smart Home Assistant", "Voice-controlled.", 4999,
					"https://picsum.photos/200?random=11"));
			repo.save(new Product("Fitness Tracker", "Monitors heart rate.", 2499,
					"https://picsum.photos/200?random=12"));

			// }
		};
	}

	private void addProductIfNotExists(ProductRepository repo, String name, String description, double price,
			String image) {
		boolean exists = repo.findByName(name).isPresent();
		if (!exists) {
			Product product = new Product(name, description, price, image);
			repo.save(product);
		}
	}

}
