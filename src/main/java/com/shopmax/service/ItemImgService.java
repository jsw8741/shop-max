package com.shopmax.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shopmax.entity.ItemImg;
import com.shopmax.repository.ItemImgRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
//	private String itemImgLocation = "D:/java/Spring/shop-max/shop/item";
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	private final ItemImgRepository itemImgRepository;
	private final FileService fileService;
	
	/* 이미지 저장
	1. 파일을 itemImgLocationdp에 저장
	2. item_img 테이블에 저장
	*/
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
		String oriImgName = itemImgFile.getOriginalFilename(); // 파일이름 -> 이미지1.jpg
		String imgName = "";
		String imgUrl = "";
		
		// 1. 파일을 itemImg Location에 저장
		if(!StringUtils.isEmpty(oriImgName)) {
			// oriImgName이 빈문자열이 아니라면 이미지 파일 업로드
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		// 2. item_img 테이블에 저장 
		
		// (이미지1.jpg, ERASJUKFHEASIGF4.jpg, "")로 entity 값을 update
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		itemImgRepository.save(itemImg); // db에 insert
		
	}
	
	// 이미지 업데이트 메소드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
		if(!itemImgFile.isEmpty()) { // 첨부한 이미지 파일이 있으면
			// 해당 이미지를 가져오고
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
													.orElseThrow(EntityNotFoundException::new);
			// 기존 이미지 파일 C:/shop/item 폴더에서 삭제
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
				fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
			}
			
			// 수정된 이미지 파일 C:/shop/item에 업로드
			String oriImgName = itemImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			
			String imgUrl = "/images/item/" + imgName;
			
			// update 쿼리문 실형
			// 한번 insert를 진행하면 entity가 영속성 컨택스트에 저장되므로 그 이후에는 변경감지 기능이
			// 동작하기 때문에 개발자는 update 쿼리문을 쓰지않고 엔티티 데이터만 변경해주면 된다.
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
			
		}
	}
	
	
}
