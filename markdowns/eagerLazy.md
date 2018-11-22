# Eager and Lazy loading

@[A quick sample. Check out the JAVA class and SQL file]({"stubs": ["src/main/java/com/tu/entity/User.java","src/test/resources/schema.sql"], "command": "com.tu.entity.EntityGraphTests#testLazyEagerLoading"})


*	When we load the above entity
*	As Domain is to be loaded eagerly, we see the query built with both User (the requested entity) and domain, with an inner join on the domain_id (Check out Query fired in the output logs)
*	Now, when team object of the user entity is requested, a separate query is fired to get the team details based on the team_id – query as below (Check out Query fired in the output logs)

## Advantages and disadvantages of Eager and Lazy loading

**1**	Lazy Loading
Advantages:
*	Initial load time much smaller than in the other approach
*	Less memory consumption than in the other approach
Disadvantages:
*	Delayed initialization might impact performance during unwanted moments
*	In some cases you need to handle lazily-initialized objects with a special care or you might end up with an exception(LazyInitializationException)
**1**		Eager Loading:
Advantages:
*	No delayed initialization related performance impacts
Disadvantages:
*	Long initial loading time
*	Loading too much unnecessary data might impact performance
