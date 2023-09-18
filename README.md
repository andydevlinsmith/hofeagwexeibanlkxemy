TO DO:

Clean up this file
How are we going to scaffold postgres?

Need to create weather_data table
with these 
weather_data_id-uuid
station_id-uuid
temperature-double
humidity- double
wind_speed- double




probably need to dockerize this thing.




Get something basic working.


To do:
    Get endpoint
    Test cleanup- quit adding to the DB each time.
        @DataJpaTest?
        Do we want to use h2 for tests?
    Tests- get the GET endpoints going.

    Need to create a station table too.
    Create a test to ensure a valid station
    Create an endpoint to manage stations

    So you need a table for stations
    Post data to nonexistent station

hamcrest- could do this with gson or something

Discussion points:

Going to use MockMVC so we're not actually starting the server to run the unit tests. that's not necessary IMO.

Did this with junit as a first pass. My last project used TestNG, but - something basic.
Dates are relative to the locale