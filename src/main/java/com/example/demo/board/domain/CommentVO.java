package com.example.demo.board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {

	private int cno;
	private int bno;
	private String content;
	private String writer;
	private Date reg_date;
	
}
