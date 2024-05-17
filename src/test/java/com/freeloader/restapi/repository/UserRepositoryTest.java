package com.freeloader.restapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;



@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
    private TestEntityManager em;  // Has some additional test-related APIs.

    @Autowired
    private UserRepository repository;


    @BeforeEach
    public void setUp() {
//    	em.persist(new User(-1L, "Herman", "Munster", "user1@email.com", 
//    			new Address(-1L, "1313", "Mockingbird Lane", "AnyTown", "MI", "48230", "USA"), 
//    			List.of(new Phone(-1L, "333-333-3333", "Cell"))));
//    	em.persist(new User(-1L, "Morticia", "Adams", "user2@email.com", 
//    			new Address(-1L, "001", "Cemetery Lane", "AnyTown", "MI", "48230", "USA"), 
//    			List.of(new Phone(-1L, "333-444-3333", "Cell"))));
//    	em.persist(new User(-1L, "Gomez", "Adams", "user2@email.com", 
//    			new Address(-1L, "001", "Cemetery Lane", "AnyTown", "MI", "48230", "USA"), 
//    			List.of(new Phone(-1L, "333-444-3333", "Cell"),
//    					new Phone(-1L, "333-444-3334", "Home"))));
//    	em.persist(new User(-1L, "Marge", "Simpson", "user3@email.com", 
//    			new Address(-1L, "742", "Evergreen Terrace", "Sprinfield", "MI", "48230", "USA"), 
//    			List.of(new Phone(-1L, "333-555-3333", "Cell"))));
    }
    
    
	@Test
	void test() {
//		List<User> byEmailIgnoreCase = repository.findByEmailIgnoreCase("user1@gmail.com");
//		assertEquals(1, byEmailIgnoreCase.size());
//		assertEquals("Herman", byEmailIgnoreCase.get(0).getFirstName());
	}

}
