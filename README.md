dn-issue-updating-entity-with-set-in-name
========

[JPA](https://en.wikipedia.org/wiki/Java_Persistence_API) Test case demonstrating an issue I (and a colleague of mine) reported to [DataNucleus JPA Provider](http://datanucleus.org) folks: https://github.com/datanucleus/datanucleus-core/issues/274

In a nutshell, the test case tries to execute a simple JPQL UPDATE Query against a domain class that contains 'Set' in the name.

This test case contains a set of Maven profiles that run the same tests against other JPA providers. The tests pass as expected with EclipseLink and Hibernate, but not with DataNucleus.

- To run the tests against [DataNucleus](http://datanucleus.org) (default profile): `mvn clean compile test` or `mvn clean compile test -P datanucleus`

- To run the tests against [EclipseLink](http://www.eclipse.org/eclipselink/): `mvn clean compile test -P eclipselink`

- To run the tests against [Hibernate](http://hibernate.org): `mvn clean compile test -P hibernate`


