package com.example.demo.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageMakerVO {

	// 시작 페이지
	private int startPage;
	
	// 끝 페이지
	private int endPage;
	
	// 이전 페이지
	private boolean prev;
	
	// 다음 페이지
	private boolean next;
	
	// 전체 게시물 수 
	private int total;
	
	// 현재 페이지, 페이지당 게시물 표시수 정보
	private Criteria cri;
	
	private int lastpage;
	
	
	public PageMakerVO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
		
		this.startPage = this.endPage - 9;
		
		this.lastpage = (int)(Math.ceil(total/10.0));
		
		int realEnd = (int)(Math.ceil(total * 1.0/cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		
		this.next = this.endPage < realEnd;
		
	}
	
	
}
