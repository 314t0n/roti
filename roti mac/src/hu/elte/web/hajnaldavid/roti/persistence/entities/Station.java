package hu.elte.web.hajnaldavid.roti.persistence.entities;

import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Station implements RotiEntity, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique=true)
	private String name;
	private Integer maximumCapacity;

	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Bicycle> bikes = new ArrayList<Bicycle>();

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

	public Integer getMaximumCapacity() {
		return maximumCapacity;
	}

	public void setMaximumCapacity(Integer maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}

	public List<Bicycle> getBikes() {
		return bikes;
	}

	public void setBikes(List<Bicycle> bikes) {
		this.bikes = bikes;
	}

	@Override
	public Object get(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return name;
		case 1:
			return maximumCapacity;
		case 2:
			return bikes.size();
		default:
			return null;
		}
	}

	@Override
	public void set(int columnIndex, Object value) {
		switch (columnIndex) {
		case 0:
			name = (String) value;
			break;
		case 1:
			maximumCapacity =  Integer.parseInt((String)value);
			break;		
		}

	}

	public static String[] getPropertyNames() {
		return new String[] { "név", "max", "aktuális" };
	}

	public Station addBike(Bicycle bike) {
		this.bikes.add(bike);
		return this;
	}

	public Bicycle removeBike(Bicycle bike) throws NoSuchElement {

		int selected = this.bikes.indexOf(bike);

		if (selected < 0) {
			throw new NoSuchElement(bike);
		}
		
		bikes.get(selected).setStation(null);

		return this.bikes.remove(selected);

	}

	public Bicycle removeBikeByIndex(int index) throws NoSuchElement {

		if (index > this.bikes.size() || index < 0) {
			throw new NoSuchElement(index);
		}

		return this.bikes.remove(index);

	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", name=" + name + ", maximumCapacity="
				+ maximumCapacity + ", bikes=" + bikes + "]";
	}

}
