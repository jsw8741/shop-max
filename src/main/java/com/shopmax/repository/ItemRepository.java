package com.shopmax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.entity.Item;


public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{ // <해당 repository에서 사용할 Entity, Entity 클래스의 기본키 타입>
	
	// select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);
	
	// 퀴즈 1-1
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus ItemSellStatus);
	// 퀴즈 1-2
	List<Item> findByPriceBetween(int s, int e);
	// 퀴즈 1-3
	List<Item> findByRegTimeAfter(LocalDateTime regTime);
	// 퀴즈 1-4
	List<Item> findByItemSellStatusNotNull();
	// 퀴즈 1-5
	List<Item> findByItemDetailEndingWith(String itemDetail);
	// 퀴즈 1-6
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	// 퀴즈 1-7
	List<Item> findByPriceLessThanOrderByPrice(int e);

	// JPQL(non native 쿼리) -> 컬럼명, 테이블명은 entity 클래스 기준으로 작성
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	
	// JPQL(native 쿼리) -> h2 데이터베이스를 기준으로 작성
	@Query(value = "select * from item where item_detail like %:itemDetail% order by price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	// 퀴즈 2-1
	@Query("select i from Item i where i.price >= :price")
	List<Item> findByPrice(@Param("price") int price);

	// 퀴즈 2-2
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :itemSellStatus")
	List<Item> findByItemNmAndItemSellStatus2(@Param("itemNm") String itemNm, @Param("itemSellStatus") ItemSellStatus itemSellStatus);
	
	
}
