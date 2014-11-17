package hu.elte.web.hajnaldavid.roti.persistence.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Lending implements RotiEntity, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Integer revenue;
	
	@OneToOne(cascade = CascadeType.REFRESH)
	private Bicycle bike;

	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getRevenue() {
		return revenue;
	}

	public void setRevenue(Integer revenue) {
		this.revenue = revenue;
	}

	public Bicycle getBike() {
		return bike;
	}

	public void setBike(Bicycle bike) {
		this.bike = bike;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
