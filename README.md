# 15 puzzle demo project

This 15 puzzle implementation is a demo of online game, built in such a way that every piece of game logic is performed on the back-end side (usualy, this kind of approach used for froad prevention).
Stack:

  - Back-end: Spring boot, Spring data, Spring security, Thymeleaf
  - Database: Neo4j
  - Front-end: vanila html, jquery, jquery-ui

# Online demo

  - https://fifteen-demo.herokuapp.com/

# Run locally:

 - Install and run [Neo4j](https://neo4j.com/) on your machine.
```sh
$ brew install neo4j
$ neo4j start
```
 - Checkout the code from [Github](https://github.com/myteksp/fifteen)
```sh
$ git clone https://github.com/myteksp/fifteen.git
$ cd fifteen/
$ mvn spring-boot:run
```
 - Edit the application.properties file:
comment out these lines
```
spring.data.neo4j.uri=${GRAPHENEDB_BOLT_URL}
spring.data.neo4j.username=${GRAPHENEDB_BOLT_USER}
spring.data.neo4j.password=${GRAPHENEDB_BOLT_PASSWORD}
```
and uncomment these
```
#spring.data.neo4j.username=neo4j
#spring.data.neo4j.password=neo4j
```
then, run the app:
```sh
$ mvn spring-boot:run
```

Now, open your browser on http://localhost:8080, register a user and give it a try.