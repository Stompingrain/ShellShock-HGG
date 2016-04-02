package edu.whu.shellshock;

public class UserInfo {
	private String name;
	private String password;
	private String email;
	private int level;
	private int exp;
	private int id;
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public int getLevel(){
		return level;
	}
	public void setExp(int exp){
		this.exp = exp;
	}
	public int getExp(){
		return exp;
	}
	public String toString(){
		return "UserInfo [name= " + name + ", password=" + password +
				", email=" + email + ", id=" + id + ", level=" +
				level + ", exp=" + exp + "]";
	}

}
