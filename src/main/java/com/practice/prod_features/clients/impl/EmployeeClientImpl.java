package com.practice.prod_features.clients.impl;

import com.practice.prod_features.advices.ApiResponse;
import com.practice.prod_features.clients.EmployeeClient;
import com.practice.prod_features.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Starting getAllEmployees API call");

        try {
            log.debug("Calling endpoint: /employees/getAllEmployees");

            ApiResponse<List<EmployeeDTO>> response = restClient.get()
                    .uri("/employees/getAllEmployees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            log.debug("Received response: {}", response);

            List<EmployeeDTO> employees = response.getData();

            log.info("Successfully fetched {} employees",
                    employees != null ? employees.size() : 0);

            return employees;

        } catch (Exception e) {
            log.error("Error occurred while fetching all employees", e);
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Starting getEmployeeById API call for id={}", id);

        try {
            log.debug("Calling endpoint: /employees/id/{}", id);

            ApiResponse<EmployeeDTO> response = restClient.get()
                    .uri("/employees/id/{id}", id)   // ✅ fixed URL
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        log.error("5xx Server error while calling employee service for id={}", id);
                        throw new RuntimeException("Server Error Occurred");
                    })
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.warn("4xx Client error while calling employee service for id={}", id);
                        throw new RuntimeException("Client Error Occurred");
                    })
                    .body(new ParameterizedTypeReference<>() {});

            log.debug("Received response: {}", response);

            EmployeeDTO employee = response.getData();

            log.info("Successfully fetched employee with id={}", id);

            return employee;

        } catch (Exception e) {
            log.error("Error occurred while fetching employee with id={}", id, e);
            throw new RuntimeException("Failed to fetch employee", e);
        }
    }
}