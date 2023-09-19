package com.example.phone_ordering_webapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Receipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "Name", nullable = false)
	private String name;
	@Column(name = "Email", nullable = false)
	private String email;
	@Column(name = "Price", nullable = false)
	private int price;
	@Column(name = "PhoneModel", nullable = false)
	public String option;
	@Column(name = "PhoneColor", nullable = false)
	private String color;
	
	public Integer getID() {
		return id;
	}
	public void setID(Integer id) {
		this.id = id;
	}
	
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public Receipt() {}
	public Receipt (Integer id, String name, String email, int price, String option, String color) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.option = option;
		this.color = color;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Receipt [name=" + name + ", email=" + email + ", price=" + price + ", option=" + option + ", color="
				+ color + "]";
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
