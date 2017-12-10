package tm.learning.simplewiki.model.repo.base;

public interface EntityBaseDao<T> {
	
	void save(T entity);

}
