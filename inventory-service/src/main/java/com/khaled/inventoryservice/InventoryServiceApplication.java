package com.khaled.inventoryservice;

import com.khaled.inventoryservice.model.Inventory;
import com.khaled.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadDate(InventoryRepository inventoryRepository){
		return args->{
			Inventory inventory= new Inventory();
			inventory.setSkuCode("");
			inventory.setQuantity(100);

			Inventory inventory1= new Inventory();
			inventory1.setQuantity(0);
			inventory1.setSkuCode("iphone_13_red");

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}
}
