package wku.smartplant;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartPlantApplicationTests {

	@Autowired
	private EntityManager em;

	@Test
	void contextLoads() {
	}

	@Test
	void test(){

	}

}
