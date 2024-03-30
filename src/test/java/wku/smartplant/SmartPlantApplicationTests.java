package wku.smartplant;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Item;
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
		Item item = new Item();
		item.setName("상추");
		item.setPrice(1000);
		item.setStockQuantity(10);
		itemRepository.save(item);

		Item findItem = itemRepository.findById(item.getId()).get();
//		OrderItem orderItem = OrderItem.builder()
//				.item(itemRepository.findById(1L).get())
//				.count(10)
//				.orderPrice(2000)
//				.build();
//		em.persist(orderItem);
	}

}
