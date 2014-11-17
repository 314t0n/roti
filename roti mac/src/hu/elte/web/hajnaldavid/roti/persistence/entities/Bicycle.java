package hu.elte.web.hajnaldavid.roti.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Bicycle implements RotiEntity, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum BikeType {
		MOUNTAIN, CITY, DOWNHILL, BMX, CAMPING
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private BikeType type;
	private Integer lendingPrice;

	@ManyToOne
	private Station station;
	
	@OneToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
		
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub

	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public Object get(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.id;
		case 1:
			return this.station == null ? "-" : this.station.getName();
		case 2:
			return this.lendingPrice;
		case 3:
			return this.type == null ? "-" : this.type.toString() ;
		}
		return null;
	}

	@Override
	public void set(int columnIndex, Object value) {
		// TODO Auto-generated method stub

	}

	public BikeType getType() {
		return type;
	}

	public void setType(BikeType type) {
		this.type = type;
	}

	public Integer getLendingPrice() {
		return lendingPrice;
	}

	public void setLendingPrice(Integer lendingPrice) {
		this.lendingPrice = lendingPrice;
	}

	@Override
	public String toString() {
		return "Bicycle [id=" + id + ", type=" + type + ", lendingPrice="
				+ lendingPrice + ", station=" + station.getName() + "]";
	}

}
