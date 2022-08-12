package com.example.demo.board.domain;

import lombok.Data;

@Data
public class FileVO {

	// 파일번호
	private int fno;
	
	// 게시물 번호
	private int bno;
	
	// 저장 파일 명
	private String fileName;
	
	// 파일 원본 명 
	private String fileOriName;
	
	// URL
	private String fileUrl;
	
}
