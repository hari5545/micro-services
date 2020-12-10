package com.authservices.modal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="OAUTH_CLIENT_DETAILS")
public class OauthClientDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="client_id",unique = true,nullable = false)
	protected String clientId;

	@Column(name="client_secret")
	protected String cleientSecret;

	@Column(name="resources_id")
	protected String resourcesId;

	@Column(name="scopes")
	protected String scopes;

	@Column(name="grant_type",length = 255)
	protected String grantType;

	@Column(name="redirect-url")
	protected String redirectUrl;

	@Column(name="authorities")
	protected String authorities;

	@Column(name="access_token")
	protected String accessToken;

	@Column(name="refresh_token")
	protected String refreshToken;

	@Column(name="information")
	protected String information;

	@Column(name="auto_approval")
	protected String autoApproval;
}
