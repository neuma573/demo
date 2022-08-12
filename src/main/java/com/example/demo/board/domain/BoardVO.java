package com.example.demo.board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {

	private int bno;
	private String title;
	private String content;
	private String writer;
	private Date reg_date;
	private Date update_time;
	private int hit_cnt;
	private int comCount;
	private String password;
	private String type;
	private String keyword;
	private String has_file;
	private String is_private;
}
