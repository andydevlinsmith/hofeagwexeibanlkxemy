# Requirements:
* Java 17
* Postgres

# Running the PoC:
* Clone the repo
* Create a postgres database called weather.
```CREATE DATABASE IF NOT EXISTS weather;```
* Run this SQL command to create the columns:

```
CREATE TABLE IF NOT EXISTS weather_data (
weather_data_id UUID PRIMARY KEY,
timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
station_id UUID NOT NULL,
temperature INTEGER NOT NULL,
humidity INTEGER NOT NULL,
wind INTEGER NOT NULL
)
```
Note: If gradle is not set up, you may need to do a gradle init/gradle wrapper

* Start the PoC:

```./gradlew bootRun```


# Roadmap:
* Improve WeatherDataService queries. Consider spring data jpa projections or native queries to simplify.
* Factor unit tests down. Consider TestNG.
* Dockerize
* Create a stations table/interface
* Add caching with Jedis/Redis
* Build out additional resilience features based on [reliable sources](https://www.manning.com/books/spring-microservices-in-action)