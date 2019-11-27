# Stress Testing Gatling Training #

## What do we need for this training:
  - Java 8
  - Maven
  - Docker & Docker-Compose
  - Robo3T

## What do you need todo
 - Understand the system and it limitations.(code, running tests, etc)
 - Gather information with discovery team about expected performance.
 - Analyse data and find bottlenecks and issues.
 - Fix it
 - Analyse the new data and compare the results. 
 - We will add some features and stress them.

## User Stories
Here are some user stories that the development team used to deliver this application

- As user I need to list all the registered users.
	- As user I want to be able to search passing a string "X" that will be verified with all User fields. If some field contains the string "X", it must be returned on the listing.
- As user I need to create new users.
	- The user cannot have the same email nor name+lastname of other already registered user.
- As user I want to delete users
- As user I want to register dependents
- As user I want to register Pets
- As user I want to link my pets to my User
- As user I want to list pets per user
	- When I list users I want to get their pets inside the listing.


## Running the services and tests
To run the services just type:
```
cd simple-app/
./buildAndRun.sh
```

To simply run the tests, you can just type
```
mvn -Dgatling.simulation.name=<SIMULATION_NAME> gatling:test
```



## Running as JAR 
Simple example of how to run gatling stress testing as stand alone JAR.
It builds with maven plugin but runs idenpendently.

To build the jar:
```
	mvn clean install
```
Running the tests:
```
	chmod +x target/run.sh
	./run.sh
```
