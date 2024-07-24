package com.example.project.product;

// public class product {

// }
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor // generates a no-argument constructor
@AllArgsConstructor // generates a constructor with all fields
@Table(name = "products")

public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    // private String description;
    private double price;
    public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

    // Getters and Setters
}
