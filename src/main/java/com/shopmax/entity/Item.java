package com.shopmax.entity;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.dto.ItemFormDto;
import com.shopmax.exception.OutOfStockException;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티 클래스로 정의
@Table(name="item") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
	
	@Id
	@Column(name="item_id") // 테이블 생성될때 컬럼 이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) // 키본키를 자동으로 생성해주는 전략 사용(시퀀스)
	private Long id; // 상품코드
	
	@Column(nullable = false, length = 50) // not null, 컬럼 크기지정
	private String itemNm; // 상품명 -> item_nm
	
	@Column(nullable = false)
	private int price; // 가격
	
	@Column(nullable = false)
	private int stockNumber; // 재고수량 -> stock_number
	
	@Lob // clob과 같은 큰타입으로 컬럼을 만든다
	@Column(nullable = false, columnDefinition = "longtext")
	private String itemDetail; // 상품상세설명 -> item_detail
	
	@Enumerated(EnumType.STRING) // enum의 이름을 DB의 저장
	private ItemSellStatus itemSellStatus; // 판매상태(SELL, SOLD_OUT) -> item_sell_status
	
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();	
	}
	
	// 재고를 감소시키는 메소드
	public void removeStock(int stockNumber) {
		int restStock = this.stockNumber - stockNumber; // 남은 재고 수량
		
		if(restStock < 0) {
			throw new OutOfStockException("상품의 재고가 부족합니다. 현재 재고 수량 : " + this.stockNumber);
		}
		
		this.stockNumber = restStock;
		
	}
}
