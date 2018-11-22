# Introduction to Entity graph

Now, both Lazy loading and Eager Loading have their own set of advantages and disadvantages. To mitigate both loading’s cons, JPA 2.1 introduced entity graphs.
The definition of an entity graph is independent of the query and defines which attributes to fetch from the database. 
Use an entity graph as a fetch or a load graph. 
*	If a fetch graph is used, only the attributes specified by the entity graph are loaded eager. All other attributes will be lazy. 
*	If a load graph is used, all attributes that not specified by the entity graph will keep their default fetch type.


##	Fetch graph and Load graph

#### How to define Entity graphs:

*	To define a named entity graph use the @NamedEntityGraph annotation at the entity. 
*	It defines a unique name and a list of attributes (the attributeNodes) that have be loaded. 
*	The usage of @NamedSubgraph annotation defines an entity graph for sub-entities within the given entity and their own attributeNodes to define the loading technique for the sub-entity.
*	Sample as below

@[A quick sample of Fetch Graph. Check out the JAVA class and SQL file]({"stubs": ["src/main/java/com/tu/entity/User.java","src/main/java/com/tu/entity/UserRepository.java","src/test/resources/schema.sql"], "command": "com.tu.entity.EntityGraphTests#testFetchGraph"})

*	In this example, we have defined a namedEntiyGraph with attribute as Team. We have also defined a region subgraph, which denotes the region entity within the Team entity would be eligible for the entityGraph.
*	Now, to define fetch or load graph, we specify the name of the entity graph used (here it’s user-with-team) with the @entity
*	Now, when we load the User entity with the findOneByUserId method(click on RUN button), only Team entity, region entity within the Team would be loaded eagerly. All other attributes would be loaded lazily. (Check out Query fired in the output logs)

@[A quick sample of Load Graph. Check out the JAVA class and SQL file]({"stubs": ["src/main/java/com/tu/entity/User.java","src/main/java/com/tu/entity/UserRepository.java","src/test/resources/schema.sql"], "command": "com.tu.entity.EntityGraphTests#testLoadGraph"})

*	Now, when we load the User entity with the readOneByUserId method, only Team entity, region entity within the Team would be loaded eagerly. All other attributes would be loaded lazily
*	In the case of a fetch map, the queries would look as below. If you notice, an outer join is performed between, the team and region and the requesting User entity due to entity graph and subgraph.
*	In the case of a load map, the queries would look as below. This would be similar to the fetch map, except that Domain would be loaded along. 

