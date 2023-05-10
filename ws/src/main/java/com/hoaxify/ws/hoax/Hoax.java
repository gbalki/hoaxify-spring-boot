package com.hoaxify.ws.hoax;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.hoaxify.ws.file.FileAttachment;
import com.hoaxify.ws.user.User;
import lombok.Data;

@Data
@Entity
@Table(name="hoaxes")
public class Hoax {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 1000)
	private String content;

	private Date timestamp;

	@ManyToOne
	private User user;

	@OneToOne(mappedBy = "hoax" , orphanRemoval = true)
	private FileAttachment fileAttachment;
}
