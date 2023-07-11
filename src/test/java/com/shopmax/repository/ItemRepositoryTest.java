package com.shopmax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopmax.constant.ItemSellStatus;
import com.shopmax.entity.Item;
import com.shopmax.entity.QItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {
	@Autowired
	ItemRepository itemRepository;
	
	@PersistenceContext // 영속성 컨텍스트
	EntityManager em; // 엔티티 매니저(엔티티 관리)
	
	
	@Disabled
	@Test
	@DisplayName("상품 저장 테스트")
	public void createItemTest() {
		Item item  = new Item();
		item.setItemNm("테스드 상품");
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now());
		item.setUpdateTime(LocalDateTime.now());
		
		// insert
		Item savedItem = itemRepository.save(item);
		System.out.println(savedItem.toString());
	}

	// 여러개 저장
	public void createItemList() {
		for(int i=1;i<=10;i++) {		
			Item item  = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			// insert
			Item savedItem = itemRepository.save(item);
		}
	}
	
	// 여러개 저장2
	public void createItemList2() {
		for(int i=1;i<=5;i++) {		
			Item item  = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			// insert
			Item savedItem = itemRepository.save(item);
		}
		
		for(int i=6;i<=10;i++) {		
			Item item  = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
			item.setStockNumber(0);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			// insert
			Item savedItem = itemRepository.save(item);
		}
	}
	
	@Disabled
	@Test
	@DisplayName("상품 조회 테스트")
	public void findByItemNmTest() {
		createItemList(); // 데이터 10개 생성
		List<Item> itemList = itemRepository.findByItemNm("테스드 상품1");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Disabled
	@Test
	@DisplayName("퀴즈 1-1")
	public void quiz1() {
		createItemList();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스드 상품1", ItemSellStatus.SELL);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 1-2")
	public void quiz2() {
		createItemList();
		List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	@Disabled
	@Test
	@DisplayName("퀴즈 1-3")
	public void quiz3() throws Exception{
		createItemList();

		List<Item> itemList = itemRepository.findByRegTimeAfter(LocalDateTime.of(2023, 01, 01, 12, 12, 44));
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Disabled
	@Test
	@DisplayName("퀴즈 1-4")
	public void quiz4() {
		List<Item> itemList = itemRepository.findByItemSellStatusNotNull();
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 1-5")
	public void quiz5() {
		List<Item> itemList = itemRepository.findByItemDetailEndingWith("설명1");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 1-6")
	public void quiz6() {
		createItemList();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 1-7")
	public void quiz7() {
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPrice(10005);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("@Query를 이용한 상품 조회 테스트")
	public void findByItemDetailTest() {
		createItemList();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("@Query(native 쿼리)를 이용한 상품 조회 테스트")
	public void findByItemDetailNativeTest() {
		createItemList();
		List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 2-1")
	public void quiz2_1() {
		createItemList();
		List<Item> itemList = itemRepository.findByPrice(10005);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 2-2")
	public void quiz2_2() {
		createItemList();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus2("테스트 상품1", ItemSellStatus.SELL);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("querydsl 조회 테스트")
	public void queryDslTest() {
		createItemList();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		// 쿼리문을 실행했을때 결과 값을 담을 타입을 제네릭에 선언
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
								 .where(qItem.itemDetail.like("%테스트 상품 상세%"))
								 .orderBy(qItem.price.desc());
		
		List<Item> itemList = query.fetch(); // 쿼리문 실행
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("querydsl 조회 테스트2")
	public void queryDslTest2() {
		createItemList2();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		// 0부터 페이지 번호가 시작된다.
		Pageable page = PageRequest.of(0, 3); // of(조회할 페이지의 번호, 한 페이지당 조회할 데이터의 갯수)
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
								 .where(qItem.itemDetail.like("%테스트 상품 상세%"))
//								 .where(qItem.price.gt(10003))
								 .offset(page.getOffset())
								 .limit(page.getPageSize());
		
		List<Item> itemList = query.fetch(); // 쿼리문 실행
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 3-1")
	public void quzie3_1() {
		createItemList2();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemNm.eq("테스트 상품1"))
								 .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
		
		List<Item> itemList = query.fetch(); // 쿼리문 실행
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 3-2")
	public void quzie3_2() {
		createItemList2();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.price.between(10004, 10008));
		
		List<Item> itemList = query.fetch(); // 쿼리문 실행
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 3-3")
	public void quzie3_3() {
		createItemList2();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.regTime.after(LocalDateTime.of(2023, 01, 01, 12, 12, 44)));
		
		List<Item> itemList = query.fetch(); // 쿼리문 실행
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@Disabled
	@Test
	@DisplayName("퀴즈 3-4")
	public void quzie3_4() {
		createItemList2();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemSellStatus.isNotNull());
		
		List<Item> itemList = query.fetch(); // 쿼리문 실행
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	@DisplayName("퀴즈 3-5")
	public void quzie3_5() {
		createItemList2();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemDetail.like("%1"));
		
		List<Item> itemList = query.fetch(); // 쿼리문 실행
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
}
