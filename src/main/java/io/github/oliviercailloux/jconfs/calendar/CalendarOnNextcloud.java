package io.github.oliviercailloux.jconfs.calendar;

public class CalendarOnNextcloud implements CalendarOnCloudInterface {
	String url;
	String userName;
	String password;

	public static CalendarOnNextcloud newInstance() {
		return new CalendarOnNextcloud();
	}
	
	private CalendarOnNextcloud() {
		this.url = "https://us.cloudamo.com/remote.php/dav/principals/users/";
		this.userName = "sebastien.bourg@dauphine.eu";
		this.password = "600bec84476fb1";
	}
	
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setCredentials() {
		TODO();
		
	}

}
