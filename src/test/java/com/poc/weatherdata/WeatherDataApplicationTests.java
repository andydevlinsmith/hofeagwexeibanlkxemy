package com.poc.weatherdata;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@TestPropertySource("/test.properties")
@AutoConfigureTestDatabase
public class WeatherDataApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private String today = LocalDateTime.now().toLocalDate().toString();
	private String yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS).toLocalDate().toString();

	@Test
	public void contextLoads() {
	}


	@Test
	public void postValidDataSucceeds() throws Exception {
		Map<String, Object> body = buildPayload();
		String json = new ObjectMapper().writeValueAsString(body);
		this.mockMvc.perform(post("/data")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.temperature", is(body.get("temperature"))))
				.andExpect(jsonPath("$.humidity", is(body.get("humidity"))))
				.andExpect(jsonPath("$.wind", is(body.get("wind"))))
				.andReturn();
	}

	@Test
	public void postInvalidStationFails() throws Exception {
		Map<String,Object> body = new HashMap<>();
		body.put("stationId", "BAD STATION VAL");
		body.put("temperature", 75.1);
		body.put("humidity", 78.1);
		body.put("wind_speed", 5.5);
		String json = new ObjectMapper().writeValueAsString(body);

		this.mockMvc.perform(post("/data")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andReturn();
	}

	@Test
	public void postInvalidWeatherDataFails() throws Exception {
		Map<String,Object> body = new HashMap<>();
		body.put("stationId", UUID.randomUUID().toString());
		body.put("temperature", "invalid");
		body.put("humidity", 78.1);
		body.put("wind_speed", 5.5);
		String json = new ObjectMapper().writeValueAsString(body);

		this.mockMvc.perform(post("/data")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andReturn();
	}


	@Test
	public void getDataNoStatReturns400() throws Exception {
		UUID testStation = UUID.randomUUID();
		this.mockMvc.perform(
						get("/data")
								.contentType(MediaType.APPLICATION_JSON)
								.param("metrics", "TEMPERATURE")
								.param("stations", testStation.toString())
								.param("startTime", "2023-09-17")
								.param("endTime", "2023-09-20")
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().is4xxClientError())
				.andReturn();
	}

	@Test
	public void getDataNoStationNoTimeSucceeds() throws Exception {
		this.mockMvc.perform(
						get("/data")
								.contentType(MediaType.APPLICATION_JSON)
								.param("metrics", "TEMPERATURE")
								.param("statistic", "MAX")
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void getDataByDateSucceeds() throws Exception {
		Map<String, Object> body = buildPayload();
		body.put("temperature", 1000.0);

		String json = new ObjectMapper().writeValueAsString(body);
		this.mockMvc.perform(post("/data")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.temperature", is(body.get("temperature"))))
				.andExpect(jsonPath("$.humidity", is(body.get("humidity"))))
				.andExpect(jsonPath("$.wind", is(body.get("wind"))))
				.andReturn();

		this.mockMvc.perform(
						get("/data")
								.contentType(MediaType.APPLICATION_JSON)
								.param("metrics", "TEMPERATURE")
								.param("statistic", "MAX")
								.param("startDate", yesterday)
								.param("endDate", today)
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].metric", is("TEMPERATURE")))
				.andExpect(jsonPath("$[0].statistic", is("MAX")))
				.andExpect(jsonPath("$[0].value", is(1000.0)))
				.andExpect(jsonPath("$[0].sensor", is("ALL")))
				.andReturn();

	}
	@Test
	public void GetDataByStationSucceeds() throws Exception {
		Map<String, Object> validBody = new HashMap();
		UUID testStation = UUID.randomUUID();
		double temperature = 1000;
		double humidity = 78.1;
		double wind = 5.5;
		String timestamp = Instant.now().minus(6, ChronoUnit.HOURS).toString();

		validBody.put("stationId", testStation);
		validBody.put("timestamp", timestamp);
		validBody.put("temperature", temperature);
		validBody.put("humidity", humidity);
		validBody.put("wind", wind);

		String json = new ObjectMapper().writeValueAsString(validBody);

		this.mockMvc.perform(post("/data")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.temperature", is(validBody.get("temperature"))))
				.andExpect(jsonPath("$.humidity", is(validBody.get("humidity"))))
				.andExpect(jsonPath("$.wind", is(validBody.get("wind"))))
				.andReturn();

		this.mockMvc.perform(
						get("/data")
								.contentType(MediaType.APPLICATION_JSON)
								.param("metrics", "TEMPERATURE")
								.param("statistic", "MAX")
								.param("stations", testStation.toString())
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].metric", is("TEMPERATURE")))
				.andExpect(jsonPath("$[0].statistic", is("MAX")))
				.andExpect(jsonPath("$[0].value", is(1000.0)))
				.andExpect(jsonPath("$[0].sensor", is(testStation.toString())))
				.andReturn();

	}

	@Test
	public void getDataByStationAndDateSucceeds() throws Exception {
		Map<String, Object> body = buildPayload();
		body.put("temperature", 1000.0);

		String stationId = body.get("stationId").toString();


		String json = new ObjectMapper().writeValueAsString(body);
		this.mockMvc.perform(post("/data")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.temperature", is(body.get("temperature"))))
				.andExpect(jsonPath("$.humidity", is(body.get("humidity"))))
				.andExpect(jsonPath("$.wind", is(body.get("wind"))))
				.andReturn();

		this.mockMvc.perform(
						get("/data")
								.contentType(MediaType.APPLICATION_JSON)
								.param("metrics", "TEMPERATURE")
								.param("statistic", "MAX")
								.param("stations", stationId)
								.param("startDate", yesterday)
								.param("endDate", today)
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].metric", is("TEMPERATURE")))
				.andExpect(jsonPath("$[0].statistic", is("MAX")))
				.andExpect(jsonPath("$[0].value", is(1000.0)))
				.andExpect(jsonPath("$[0].sensor", is(stationId)))
				.andReturn();

	}

	@Test
	public void GetNonexistentDataReturnsEmptyResult() throws Exception {
		Map<String, Object> body = buildPayload();
		String stationId = body.get("stationId").toString();
		this.mockMvc.perform(
						get("/data")
								.contentType(MediaType.APPLICATION_JSON)
								.param("metrics", "TEMPERATURE")
								.param("statistic", "MAX")
								.param("stations", stationId)
								.param("startDate", yesterday)
								.param("endDate", today)
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty())
				.andReturn();
	}


	public Map buildPayload(){
		Map<String, Object> validBody = new HashMap();
		UUID testStation = UUID.randomUUID();
		String timestamp = OffsetDateTime.now().minus(6, ChronoUnit.HOURS).toString();
		double temperature = 72.0;
		double humidity = 78.1;
		double wind = 5.5;

		validBody.put("stationId", testStation);
		validBody.put("timestamp", timestamp);
		validBody.put("temperature", temperature);
		validBody.put("humidity", humidity);
		validBody.put("wind", wind);

		return validBody;
	}

}
