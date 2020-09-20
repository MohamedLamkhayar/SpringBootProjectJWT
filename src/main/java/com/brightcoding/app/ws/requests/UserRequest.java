package com.brightcoding.app.ws.requests;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

	@NotEmpty(message = "Ce champ ne doit pas etre empty!")
	@NotBlank(message = "Ce champ ne doit pas contient de vide meme espace")
	//@Size(min = 3, message = "Ce champ doit avoir 3 caracteres!")
	private String firstName;
	@NotNull(message = "Ce champ ne doit pas etre null!")
	@Size(min = 3, message = "Ce champ doit avoir 3 caracteres!")
	private String lastName;
	@NotNull(message = "Ce champ ne doit pas etre null!")
	@Email(message = "Ce champ doit respecter format Email")
	private String email;
	@NotNull(message = "Ce champ ne doit pas etre null!")
	@Size(min = 8, message = "Ce champ doit avoir minimum 8 caracteres!")
	@Size(max = 12, message = "Ce champ doit avoir maximum 12 caracteres!")
	@Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message="ce mot de passe doit avoir des lettres en Maj et Minsc et numero")
	private String password;
	private Boolean admin;
	private List<AddressRequest> addresses;
	private ContactRequest contact;
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AddressRequest> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressRequest> addresses) {
		this.addresses = addresses;
	}

	public ContactRequest getContact() {
		return contact;
	}

	public void setContact(ContactRequest contact) {
		this.contact = contact;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	
}
