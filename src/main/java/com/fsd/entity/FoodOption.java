package com.fsd.entity;
import jakarta.persistence.*;

//After entering the event, we have to enter food options based on event id.
//and based on event id food options will be displayed.



@Entity
public class FoodOption {
   

	public FoodOption(Long id, String name, double price, String description, Long eventId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.eventId = eventId;
	}

	public FoodOption() {
		super();
	}

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;
    
    private Long eventId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Override
	public String toString() {
		return "FoodOption [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", eventId=" + eventId + "]";
	}
    
    
    
    }
