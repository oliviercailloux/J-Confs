package io.github.oliviercailloux.jconfs.researcher;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/**
 * this class allows to stock a researcher
 *
 */
public class Researcher {

	private String lastName;
	private String firstName;
	private String function;
	private String phone;
	private String office;
	private String group;
	private String mail;

	/**
	 * This is a constructor which initializes the searcher object
	 * 
	 * @param surname   not {@code null}.
	 * @param firstName not {@code null}.
	 * @param function
	 * @param phone
	 * @param office
	 * @param mail
	 * @param group
	 */
	public Researcher(String lastName, String firstName, String function, String phone, String office, String mail,
			String group) {
		this.lastName = Objects.requireNonNull(lastName);
		this.firstName = Objects.requireNonNull(firstName);
		this.function = function;
		this.phone = phone;
		this.office = office;
		this.mail = mail;
		this.group = group;
	}

	/**
	 * this is a getter which return a lastName
	 * 
	 * @return not {@code null}
	 */
	public String getLastname() {
		return lastName;
	}

	/**
	 * this is a setter which allows to set the name
	 * 
	 * @param lastName not {@code null}
	 */
	public void setLastname(String lastName) {
		this.lastName = Objects.requireNonNull(lastName);
	}

	/**
	 * @return not {@code null}
	 */
	public String getFirstname() {
		return firstName;
	}

	/**
	 * @param firstName not {@code null}
	 */
	public void setFirstName(String firstName) {
		this.firstName = Objects.requireNonNull(firstName);
	}

	/**
	 * @return not {@code null}
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone not {@code null}
	 */
	public void setPhone(String phone) {
		this.phone = Objects.requireNonNull(phone);
	}

	/**
	 * @return not {@code null}
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * @param office not {@code null}
	 */
	public void setOffice(String office) {
		this.office = Objects.requireNonNull(office);
	}

	/**
	 * @return not {@code null}
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group not {@code null}
	 */
	public void setGroup(String group) {
		this.group = Objects.requireNonNull(group);
	}

	/**
	 * @return not {@code null}
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail not {@code null}
	 */
	public void setMail(String mail) {
		this.mail = Objects.requireNonNull(mail);
	}

	/**
	 * 
	 * @param function not{@code null}
	 */
	public void setFunction(String function) {
		this.function = Objects.requireNonNull(function);
	}

	/**
	 * 
	 * This is a constructor which initializes the searcher object
	 * 
	 * @param lastName  not {@code null}
	 * @param firstName not {@code null}
	 */
	public Researcher(String lastName, String firstName) {
		this.lastName = Objects.requireNonNull(lastName);
		this.firstName = Objects.requireNonNull(firstName);
		this.function = "";
		this.phone = "";
		this.office = "";
		this.mail = "";
		this.group = "";
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("lastName", lastName).add("firstName", firstName)
				.add("function", function).add("phone", phone).add("office", office).add("mail", mail)
				.add("group", group).toString();
	}
}
