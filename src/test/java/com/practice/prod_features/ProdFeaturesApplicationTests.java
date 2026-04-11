package com.practice.prod_features;

import com.practice.prod_features.clients.EmployeeClient;
import com.practice.prod_features.dto.EmployeeDTO;
import com.practice.prod_features.entities.User;
import com.practice.prod_features.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProdFeaturesApplicationTests {

//    @Autowired
//    private EmployeeClient employeeClient;
//
//	@Test
//    void getAllEmployees(){
//        List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
//        System.out.println(employeeDTOList);
//    }
//
//    @Test
//    void getEmployeeById(){
//        EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L);
//        System.out.println(employeeDTO);
//    }

    @Autowired
    private JwtService jwtService;

    @Test
    void testJwt(){
        User user = new User(1L, "Swapnil", "Swapnil123");

        String token = jwtService.generateToken(user);

        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);

        System.out.println(id);
    }

}
