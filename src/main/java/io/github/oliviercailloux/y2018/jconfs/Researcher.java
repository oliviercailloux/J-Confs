package io.github.oliviercailloux.y2018.jconfs;

import java.util.Objects;

import com.google.common.base.MoreObjects;

/**
 * this class allows to stock a researcher
 *
 */

public class Researcher {

	private String surname;
	private String firstName;
	private String function;
	private String phone;
	private String office;
	private String group;
	private String mail;

	/**
	 * this is a getter which return a surname
	 * @return not <code>null</code>
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * this is a setter which allows to set the name 
	 * @param surname
	 * 			not <code>null</code>
	 */
	public void setSurname(String surname) {
		this.surname = Objects.requireNonNull(surname);
	}

	/**
	 * @return not <code>null</code>
	 */
	public String getFirstname() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            not <code>null</code>
	 */
	public void setFirstName(String firstName) {
		this.firstName = Objects.requireNonNull(firstName);
	}

	/**
	 * @return not <code>null</code>
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            not <code>null</code>
	 */
	public void setPhone(String phone) {
		this.phone = Objects.requireNonNull(phone);
	}

	/**
	 * @return not <code>null</code>
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * @param office
	 *            not <code>null</code>
	 */
	public void setOffice(String office) {
		this.office = Objects.requireNonNull(office);
	}

	/**
	 * @return not <code>null</code>
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            not <code>null</code>
	 */
	public void setGroup(String group) {
		this.group = Objects.requireNonNull(group);
	}


	/**
	 * @return not <code>null</code>
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            not <code>null</code>
	 */
	public void setMail(String mail) {
		this.mail = Objects.requireNonNull(mail);
	}

	/**
	 * 
	 * @param function
	 *            not<code>null</code>
	 */
	public void setFunction(String function) {
		this.function = Objects.requireNonNull(function);
	}

	/**
	 * 
	 * This is a constructor which initializes the searcher object
	 * 
	 * @param surname
	 *            not <code>null</code>
	 * @param firstName
	 *            not <code>null</code>
	 */
	public Researcher(String surname, String firstName) {
		this.surname = Objects.requireNonNull(surname);
		this.firstName = Objects.requireNonNull(firstName);
		this.function = "";
		this.phone = "";
		this.office = "";
		this.mail = "";
		this.group = "";
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("surname", surname)
				.add("firstName", firstName)
				.add("function", function)
				.add("phone", phone)
				.add("office", office)
				.add("mail", mail)
				.add("group", group)
				.toString();
	}
}
