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

## Running the services and tests
To run the services just type:
```
cd simple-app/
./buildAndRunEnv.sh
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
