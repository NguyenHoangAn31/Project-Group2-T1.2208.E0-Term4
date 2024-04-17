package com.example.project.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "roles")
public class Role {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name; // ROLE_ADMIN
	@Column(name = "short_name")
	private String shortName; // ADMIN

	@ManyToMany(mappedBy = "roles")
	private List<User> users;
}
