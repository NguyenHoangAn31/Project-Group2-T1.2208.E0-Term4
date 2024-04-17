package com.example.project.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String code;
	@Column(name = "expired_at", columnDefinition = "DATETIME")
	private LocalDateTime expiredAt;
	private Boolean available;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public RefreshToken(User user, int daysToExpired) {
		this.user = user;
		this.expiredAt = LocalDateTime.now().plusDays(daysToExpired);
		code = UUID.randomUUID().toString();
	}

	public boolean isExpired() {
		return expiredAt.isBefore(LocalDateTime.now());
	}
}
