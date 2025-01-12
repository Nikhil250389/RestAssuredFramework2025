package api.Payloads;

public class location {

	// String email;
	// String password;
	String job;
	int accuracy;
	String language;
	String phone_number;
	String website;
	GooglePlace location;
	String name;

	String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public GooglePlace getLocation() {
		return location;
	}

	public void setLocation(GooglePlace location) {
		this.location = location;
	}

	// public String getTypes() {
	// return types;
	// }

//	public void setTypes(String types) {
	// this.types = types;
}
