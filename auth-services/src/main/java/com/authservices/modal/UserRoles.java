package com.authservices.modal;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "user_role") 
public class UserRoles implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id",insertable = false,updatable = false,unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int roleId;
	
	@Column(name="role_type",length = 235)
	@NotNull(message = "role type required " )
	@NotEmpty(message = "role type required " )
	protected String roleType;
	
	@Column(name="role_name",length = 235)
	@NotNull(message = "roleName required " )
	@NotEmpty(message = " roleName required " )
	protected String roleName;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name = "permission_role", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "role_id")}, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "permission_id") })
	List<Permission> permissions;
}
