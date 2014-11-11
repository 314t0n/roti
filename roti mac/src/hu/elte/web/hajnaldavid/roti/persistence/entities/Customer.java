package hu.elte.web.hajnaldavid.roti.persistence.entities;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer implements RotiEntity, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private Integer credit;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", credit=" + credit + "]";
	}
	@Override
	public Object get(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void set(int columnIndex, Object value) {
		// TODO Auto-generated method stub
		
	}
		
	

}