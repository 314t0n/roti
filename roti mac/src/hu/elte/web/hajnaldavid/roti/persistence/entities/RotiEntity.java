package hu.elte.web.hajnaldavid.roti.persistence.entities;

public interface RotiEntity {
	
	 public long getId();
	 public void setId(long id);
	 public Object get(int columnIndex);
	 public void set(int columnIndex, Object value);

}
