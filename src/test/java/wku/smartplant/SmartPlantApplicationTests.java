package wku.smartplant;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import wku.smartplant.dto.ResponseDTO;

@SpringBootTest
class SmartPlantApplicationTests {

	@Autowired
	private EntityManager em;


	@Test
	void contextLoads() {
	}

	@Test
	void test(){
		ResponseDTO reponseDTO = ResponseDTO.builder()
				.statusCode(HttpStatus.ACCEPTED)
				.message("성공").build();
	}

}
