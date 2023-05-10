package com.hoaxify.ws.file;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hoaxify.ws.hoax.Hoax;

import lombok.Data;

@Entity
@Data
@Table(name="fileAttachments")
public class FileAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private Date date;
	
	@OneToOne
	private Hoax hoax;
}
