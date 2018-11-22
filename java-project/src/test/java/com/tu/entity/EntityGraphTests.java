
package com.tu.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EntityGraphTests {

	@SpringBootApplication
	static class Config {
	}

	@Autowired
	EntityManager em;
	@Autowired
	UserRepository repository;

	@Rule
	public TestRule watcher = new TestWatcher() {
		protected void starting(Description description) {
			System.out.println("-------------------------------------");
			System.out.println("Starting test: " + description.getMethodName());
			System.out.println("-------------------------------------");
		}
	};

	@Test
	public void testLazyEagerLoading() {

		// 3.1 - Lazy/Eager Loading
		/*
		 * Here only Domain is loaded eagerly. The rest of the attributes are loaded
		 * lazy. Once the entity is loaded, it is detached from the Manager(session).
		 * So, any lazy loads after detach would give LazyInitializationException
		 */

		User loadedUser = null;
		Optional<User> loadedUserO = repository.findById(1);
		if (loadedUserO.isPresent()) {
			loadedUser = loadedUserO.get();
		}
		// The entity is still under the Manager and loading the team details creates a
		// new query to load the team details.
		assertEquals("BATCH", loadedUser.getTeam().getName());

		em.detach(loadedUser);

		try {
			loadedUser.getDomain().toString(); // This is eager loading and would go through
			loadedUser.getRegion().toString();
			fail("Expected LazyInitializationException to occur when trying to access uninitialized association 'Region'.");
		} catch (Exception expected) {
			System.out.println(expected.getMessage());
		}

		// Even though team has been loaded, the Region inside the team object would be
		// loaded only on request!!
		try {
			assertEquals("BATCH", loadedUser.getTeam().getName()); // The team details would still be present, as it was
																	// loaded earlier
			loadedUser.getTeam().getRegion().toString(); // -> This would give an exception
			fail("Expected LazyInitializationException to occur when trying to access uninitialized association 'Region'.");
		} catch (Exception expected) {
			System.out.println(expected.getMessage());
		}
	}

	// 3.2
	@Test
	public void testFetchGraph() {

		/*
		 * When the javax.persistence.fetchgraph property is used to specify an entity
		 * graph, attributes that are specified by attribute nodes of the entity graph
		 * are treated as FetchType.EAGER and attributes that are not specified are
		 * treated as FetchType.LAZY
		 */

		// Here we use the findOneByUserId that uses a NamedEntityGraph
		User loadedUserWithFetchGraph = repository.findOneByUserId(2); // Here ONLY team should be loaded eagerly. Rest
																		// should be loaded lazy
		em.detach(loadedUserWithFetchGraph);

		try {
			loadedUserWithFetchGraph.getTeam().toString(); // Only this would be available.
			loadedUserWithFetchGraph.getDomain().toString(); // This itself should not be available, but, due to defect
																// https://hibernate.atlassian.net/browse/HHH-8776, this
																// works
			loadedUserWithFetchGraph.getTeam().getRegion().toString(); // This would also be available as this would
																		// have been loaded from the Sub-Graph
			loadedUserWithFetchGraph.getRegion().toString(); // This needs to throw an exception
			fail("Expected LazyInitializationException to occur when trying to access uninitialized association 'Region'.");
		} catch (Exception expected) {
			System.out.println(expected.getMessage());
		}
	}

	@Test
	public void testLoadGraph() {

		/*
		 * When the javax.persistence.loadgraph property is used to specify an entity
		 * graph, attributes that are specified by attribute nodes of the entity graph
		 * are treated as FetchType.EAGER and attributes that are not specified are
		 * treated according to their specified or default FetchType.
		 */

		// Here we use the readOneByUserId that uses a NamedEntityGraph
		User loadedUserWithLoadGraph = repository.readOneByUserId(3); // Here ONLY team and Domain should be loaded
																		// eagerly. region should be loaded lazy.

		em.detach(loadedUserWithLoadGraph);

		try {
			assertNotNull(loadedUserWithLoadGraph.getTeam());
			loadedUserWithLoadGraph.getDomain().toString();
			loadedUserWithLoadGraph.getRegion().toString(); // This data would not be there due to no Session.
			fail("Expected LazyInitializationException to occur when trying to access uninitialized association 'Region'.");
		} catch (Exception expected) {
			System.out.println(expected.getMessage());
		}
	}
}
