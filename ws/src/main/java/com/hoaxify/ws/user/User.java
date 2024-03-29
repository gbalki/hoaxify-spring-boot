package com.hoaxify.ws.user;

import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import com.hoaxify.ws.auth.Token;
import com.hoaxify.ws.hoax.Hoax;
import lombok.Data;

@Data // this annotation comes from Lombok for use Getter and Setter
@Entity
@Table(name="users")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8421768845853099274L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "{hoaxify.constraint.username.NotNull.message}")
	@Size(min = 4, max = 255)
	@UniqueUsername(message = "{hoaxify.constraint.uniqueUsername.NotNull.message}")
	private String username;

	@NotNull(message = "{hoaxify.constraint.displayName.NotNull.message}")
	@Size(min = 4, max = 255)
	private String displayName;

	@NotNull
	@Size(min = 4, max = 255)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constrain.password.Pattern.message}")
	private String password;

	private String image;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Hoax> hoaxes;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Token> tokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
