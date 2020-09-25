package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import utils.EMF_Creator;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
        p1 = new Person("David", "Josefsen", "12345678");
        p1.setAddress(new Address("Strandboulevarden", 2100, "København Ø"));
        p2 = new Person("Jens", "Tester", "87654321");
        p2.setAddress(new Address("Strandvejen", 2900, "Hellerup"));
        p3 = new Person("Anders", "Mortensen", "11223344");
        p3.setAddress(new Address("Bernstorffsvej", 2900, "Gentofte"));
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(3, facade.getPersonCount());
    }
    
    @Test
    public void testGetFacadeExample() {
        System.out.println("getPersonFacade");
        EntityManagerFactory _emf = null;
        PersonFacade expResult = null;
        PersonFacade result = PersonFacade.getPersonFacade(_emf);
        assertNotEquals(expResult, result);
    }
    
    @Test
    public void testGetPerson() throws Exception {
        int id = p3.getId();
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        PersonDTO expResult = new PersonDTO(p3);
        PersonDTO result = instance.getPerson(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllPersons() {
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        int expResult = 3;
        PersonsDTO result = instance.getAllPersons();
        assertEquals(expResult, result.getAll().size());
        PersonDTO p1DTO = new PersonDTO(p1);
        PersonDTO p2DTO = new PersonDTO(p2);
        PersonDTO p3DTO = new PersonDTO(p3);
        assertThat(result.getAll(), containsInAnyOrder(p1DTO, p2DTO, p3DTO));
    }
    
     @Test
    public void testAddPerson() throws Exception {
        String fName = "Patip";
        String lName = "Josefsen";
        String phone = "12312312";
        String street = "Rygårds Allé";
        int zip = 2900;
        String city = "Hellerup";
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        PersonDTO result = instance.addPerson(fName, lName, phone, street, zip, city);
        PersonDTO expResult = new PersonDTO(fName, lName, phone, street, zip, city);
        expResult.setId(expResult.getId());
        assertEquals(expResult.getfName(), result.getfName());
        assertEquals(expResult.getlName(), result.getlName());
        assertEquals(expResult.getPhone(), result.getPhone());
    }

}
