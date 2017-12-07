package tm.learning.simplewiki.model.repo;

public interface EntityBaseDao<T> {
	
	void save(T entity);

}
