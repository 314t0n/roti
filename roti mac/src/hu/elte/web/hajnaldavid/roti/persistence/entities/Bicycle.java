package hu.elte.web.hajnaldavid.roti.persistence.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
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

	@OneToOne(cascade= CascadeType.PERSIST)	
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
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
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
			return this.type == null ? "-" : this.type.toString();
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
		String stationName = "no station";
		if (station != null) {
			stationName = station.getName();
		}
		return "Bicycle [id=" + id + ", type=" + type + ", lendingPrice="
				+ lendingPrice + ", station=" + stationName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lendingPrice == null) ? 0 : lendingPrice.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bicycle other = (Bicycle) obj;
		if (id != other.id)
			return false;
		if (lendingPrice == null) {
			if (other.lendingPrice != null)
				return false;
		} else if (!lendingPrice.equals(other.lendingPrice))
			return false;
		if (type != other.type)
			return false;
		return true;
	}	
	

}
