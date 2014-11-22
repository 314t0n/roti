package hu.elte.web.hajnaldavid.roti.persistence.connection;

import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;

import java.util.List;

public interface CrudService<T extends RotiEntity> {

	List<T> readAll();
	T read(Integer id);
	T create(T t);
	T update(T t);
	void delete(T t);
	int rowCount();
	
}
