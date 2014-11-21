package hu.elte.web.hajnaldavid.roti.persistence.entities;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

	@OneToOne(cascade= CascadeType.PERSIST, fetch=FetchType.EAGER)	
	@JoinColumn(name="bike_id")
	private Bicycle bicycle;	

	public Bicycle getBicycle() {
		return bicycle;
	}
	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}
	public Customer() {	
		this.credit = 0;
	}
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
		String bike = this.bicycle == null ? "" : Long.toString(bicycle.getId());
		return "User [id=" + id + ", name=" + name + ", credit=" + credit + ", bicycle=" + bike + "]";
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
