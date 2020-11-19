package com.authservices.modal;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "user")  
public class User implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id",unique = true,updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO) 
	protected int UserId;
	
	@Column(name="user_nm",length = 235)
	@NotEmpty(message = "username required")
	@NotNull(message = "username required") 
	protected String userName;
	
	@Column(name="password",length = 235)
	@NotEmpty(message = "password required")
	@NotNull(message = "password required")
	protected String password;
	
	
	@Column(name="status",length = 100)
	protected String status;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
	@JoinColumn(name = "user_id")
    protected Set<UserRoles> userRoles;
   
	
	
}
