package com.practice.prod_features;

import com.practice.prod_features.entities.UserEntity;
import com.practice.prod_features.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        UserEntity userEntity = new UserEntity(1L, "Swapnil@example.com", "Swapnil123", "Swapnil");

        String token = jwtService.generateToken(userEntity);

        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);

        System.out.println(id);
    }

}
