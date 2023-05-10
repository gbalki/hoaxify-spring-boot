package com.hoaxify.ws.auth;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hoaxify.ws.user.User;
import lombok.Data;

@Entity
@Data
@Table(name="tokens")
public class Token {

	@Id
	private String token;
	
	@ManyToOne
	private User user;
}
