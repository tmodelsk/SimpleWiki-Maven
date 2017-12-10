package tm.learning.simplewiki.model.repo.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
// based on http://websystique.com/springmvc/spring-4-mvc-jpa2-hibernate-many-to-many-example/
public class BaseDao<PK extends Serializable, T> implements EntityBaseDao<T> {

	private final Class<T> persistentClass;
    
	@SuppressWarnings("unchecked")
	public BaseDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
     
    //@PersistenceContext
    //EntityManager entityManager;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    protected SessionFactory getSessionFactory() {
    	return sessionFactory;
    }
    protected Session session() {
    	return getSessionFactory().getCurrentSession();
    }
     
	//protected EntityManager getEntityManager(){
	//    return this.entityManager;
	//}
 
    protected T getByKey(PK key) {
        //return (T) entityManager.find(persistentClass, key);
    	
    	return session().find(persistentClass, key);
    }
    
    public void save(T entity) {
    	persist(entity);
    }
 
    protected void persist(T entity) {
        //entityManager.persist(entity);
    	session().save(entity);
    }
     
    protected void update(T entity) {
        //entityManager.merge(entity);
    	session().update(entity);
    }
 
    protected void delete(T entity) {
        //entityManager.remove(entity);
    	session().remove(entity);
    }
	
}
