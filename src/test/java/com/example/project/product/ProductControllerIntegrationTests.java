// package com.example.project.product;

// // public class ProductControllerIntegrationTests {

// // }
// import com.example.project.product.Product;
// import com.example.project.product.ProductRepository;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.SpringBootMockMvcBuilderCustomizer;
// import org.springframework.boot.test.context.SpringBootTest;
// // import org.springframework.boot.web.server.LocalServerPort;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import java.util.List;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertFalse;

// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.annotation.DirtiesContext;


// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
// @ActiveProfiles("test")
// // @RunWith(SpringRunner.class)
// // @SpringBootTest
// public class ProductControllerIntegrationTests {

//     // @LocalServerPort
//     // private int port;

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Autowired
//     private ProductRepository productRepository;

//     @Test
//     @DirtiesContext
//     public void testGetAllProducts() throws Exception {
//         // Insert test data
//         Product product1 = new Product("Product 1", 10.0);
//         Product product2 = new Product("Product 2", 20.0);
//         productRepository.saveAll(List.of(product1, product2));

//         // Perform GET request
//         mockMvc.perform(MockMvcRequestBuilders.get("/products")
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].name").value("Product 1"))
//                 .andExpect(jsonPath("$[1].name").value("Product 2"));
//     }

//     @Test
//     @DirtiesContext
//     public void testGetProductById() throws Exception {
//         // Insert test data
//         Product product = new Product("Product 1", 10.0);
//         product = productRepository.save(product);

//         // Perform GET request
//         mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", product.getId())
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("Product 1"));
//     }

//     @Test
//     @DirtiesContext
//     public void testSaveProduct() throws Exception {
//         // Create test data
//         Product product = new Product("New Product", 15.0);

//         // Perform POST request
//         mockMvc.perform(MockMvcRequestBuilders.post("/products")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(product)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("New Product"));

//         // Verify the product is saved in repository
//         List<Product> products = productRepository.findAll();
//         // for(Product pp :products){
//         //     System.out.println(pp.getId());
//         // }
//         // assertEquals(1, products.size());
//         assertEquals("New Product", products.get(0).getName());
//     }

//     @Test
//     @DirtiesContext
//     public void testDeleteProduct() throws Exception {
//         // Insert test data
//         Product product = new Product("Product to delete", 25.0);
//         product = productRepository.save(product);

//         // Perform DELETE request
//         mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", product.getId())
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isNoContent());

//         // Verify the product is deleted from repository
//         assertFalse(productRepository.existsById(product.getId()));
//     }
// }
