package wku.smartplant;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Item;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.MemberPlatform;
import wku.smartplant.domain.OrderItem;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.repository.ItemRepository;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class SmartPlantApplicationTests {

	@Autowired
	private EntityManager em;
	@Autowired
	private ItemRepository itemRepository;



	@Test
	void contextLoads() {
	}

	@Test
	void test(){
		//given
		Member member = new Member("koala","playgm1@naver.com","00000000",null, MemberPlatform.LOCAL,true);
	}

}
