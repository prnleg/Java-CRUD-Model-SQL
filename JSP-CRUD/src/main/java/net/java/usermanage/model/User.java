package net.java.usermanage.model;

public class User {
	
	private int id;
	private String name;
	private String birth;
	private String email;
	private String work;
	
	
	public User(int id, String name, String birth, String email, String work) {
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.email = email;
		this.work = work;
	}
	
	
	public User(String name, String birth, String email, String work) {
		this.name = name;
		this.birth = birth;
		this.email = email;
		this.work = work;
	}

	public void invertDate() {
		this.setBirth(this.getBirth().replace("-", "/"));
		String birth = this.getBirth();
		birth = birth.substring(8, 10) + birth.substring(7, 8) + birth.substring(5, 7)
			+ birth.substring(4, 5) + birth.substring(0, 4);
		this.setBirth(birth);
	}
	
	public int getId() { return id; }
	
	public void setId(int id) { this.id = id; }
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	public String getBirth() { return birth; }
	
	public void setBirth(String birth) { this.birth = birth; }
	
	public String getEmail() { return email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getWork() { return work; }
	
	public void setWork(String work) { this.work = work; }
	
}
