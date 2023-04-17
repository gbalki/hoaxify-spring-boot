package com.hoaxify.ws.user.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateVM {

	@NotNull(message = "{hoaxify.constraint.displayName.NotNull.message}")
	@Size(min = 4 , max = 255)
	private String displayName;
	

	private String image;
}
