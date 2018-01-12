dn-issue-updating-entity-with-set-in-name
========

JPA Test case demonstrating an issue I (and a colleague of mine) reported to DataNucleus folks: https://github.com/datanucleus/datanucleus-core/issues/274

In a nutshell, the test case tries to execute a simple JPQL UPDATE Query against a domain class that contains 'Set' in the name.

This test case contains a set of Maven profiles that run the same tests against other JPA providers. The tests pass as expected with EclipseLink and Hibernate, but not with DataNucleus.

- To run the tests against DataNucleus (default profile): `mvn clean compile test` or `mvn clean compile test -P datanucleus`

- To run the tests against EclipseLink: `mvn clean compile test -P eclipselink`

- To run the tests against Hibernate: `mvn clean compile test -P hibernate`


