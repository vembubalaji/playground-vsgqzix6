// { autofold
  
package com.tu.entity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, Integer> {

	// { autofold
	  
	@EntityGraph(value = "user-with-team" , type=EntityGraphType.FETCH)
	User findOneByUserId(Integer id);
	
	@EntityGraph(value = "user-with-team" , type=EntityGraphType.LOAD)
	User readOneByUserId(Integer id);

}
