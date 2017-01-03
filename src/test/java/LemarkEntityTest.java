
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Table;
import org.junit.Assert;
import org.junit.Test;
import ru.dskonasov.lemark.dao.LemarkDAO;
import ru.dskonasov.lemark.entity.LemarkEntity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitriy Konasov
 */
public class LemarkEntityTest {
    
    @Entity
    @Table(name="lemark_test_entity")
    public static class LemarkTestEntity extends LemarkEntity{
        @Column(name = "name", length = 64)
        private String name;
        
        public LemarkTestEntity(){}
        
        public LemarkTestEntity(String name){
            this.name = name;
        }
        
        public void setName(String name){
            this.name = name;
        }
        
        public String getName(){
            return this.name;
        }
    }
    
    private EntityManager em;
    public LemarkEntityTest(){
        this.em = Persistence.createEntityManagerFactory("LEMARK").createEntityManager();
    }
    
    @Test
    public void save(){
        LemarkTestEntity testEntity = new LemarkTestEntity("foo");
        LemarkDAO<LemarkTestEntity> lemarkTestEntityDAO = new LemarkDAO(LemarkTestEntity.class, em);
        long id = lemarkTestEntityDAO.save(testEntity);
        Assert.assertTrue(id > 0);
    }
    
    @Test
    public void getById(){
        LemarkTestEntity testEntity = new LemarkTestEntity("foo");
        LemarkDAO<LemarkTestEntity> lemarkTestEntityDAO = new LemarkDAO(LemarkTestEntity.class, em);
        long id = lemarkTestEntityDAO.save(testEntity);
        LemarkTestEntity entityFromDB = lemarkTestEntityDAO.getById(id);
        Assert.assertEquals(entityFromDB.getName(), "foo");
    }
    
    @Test
    public void update(){
        LemarkTestEntity testEntity = new LemarkTestEntity("foo");
        LemarkDAO<LemarkTestEntity> lemarkTestEntityDAO = new LemarkDAO(LemarkTestEntity.class, em);
        long id = lemarkTestEntityDAO.save(testEntity);
        LemarkTestEntity entityFromDB = lemarkTestEntityDAO.getById(id);
        entityFromDB.setName("bar");
        lemarkTestEntityDAO.save(entityFromDB);
        LemarkTestEntity newEntityFromDB = lemarkTestEntityDAO.getById(id);
        Assert.assertEquals(newEntityFromDB.getName(), "bar");
    }
    
    @Test
    public void delete(){
        LemarkTestEntity testEntity = new LemarkTestEntity("foo");
        LemarkDAO<LemarkTestEntity> lemarkTestEntityDAO = new LemarkDAO(LemarkTestEntity.class, em);
        long id = lemarkTestEntityDAO.save(testEntity);
        LemarkTestEntity entityFromDB = lemarkTestEntityDAO.getById(id);
        lemarkTestEntityDAO.delete(entityFromDB);
        Assert.assertNull(lemarkTestEntityDAO.getById(id));
    }
    
    @Test
    public void getAll(){
        LemarkTestEntity testEntity = new LemarkTestEntity("foo");
        LemarkDAO<LemarkTestEntity> lemarkTestEntityDAO = new LemarkDAO(LemarkTestEntity.class, em);
        lemarkTestEntityDAO.save(testEntity);
        List<LemarkTestEntity> result = lemarkTestEntityDAO.getAll();
        Assert.assertTrue(result.size() > 0);
    }
    
    @Test
    public void get(){
        LemarkTestEntity testEntity = new LemarkTestEntity("foobar");
        LemarkDAO<LemarkTestEntity> lemarkTestEntityDAO = new LemarkDAO(LemarkTestEntity.class, em);
        lemarkTestEntityDAO.save(testEntity);
        testEntity = new LemarkTestEntity("foobaz");
        lemarkTestEntityDAO.save(testEntity);
        Map<String, String> query = new HashMap();
        query.put("name", "foobar");
        List<LemarkTestEntity> result = lemarkTestEntityDAO.get(query);
        Assert.assertEquals(result.size(), 1);
    }
}
