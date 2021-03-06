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
	@Column(unique = true)
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

	public void addBicycle(Bicycle bicycle) {
		this.bikes.add(bicycle);
		if (bicycle.getStation() != this) {
			bicycle.setStation(this);
		}
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
			maximumCapacity = Integer.parseInt((String) value);
			break;
		}

	}

	public static String[] getPropertyNames() {
		return new String[] { "n�v", "max", "aktu�lis" };
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

	public Integer getCurrentCapacity() {
		return this.getBikes().size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikes == null) ? 0 : bikes.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((maximumCapacity == null) ? 0 : maximumCapacity.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Station other = (Station) obj;
		if (bikes == null) {
			if (other.bikes != null)
				return false;
		} else if (!bikes.equals(other.bikes))
			return false;
		if (id != other.id)
			return false;
		if (maximumCapacity == null) {
			if (other.maximumCapacity != null)
				return false;
		} else if (!maximumCapacity.equals(other.maximumCapacity))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}
