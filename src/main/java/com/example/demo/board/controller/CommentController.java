package com.example.demo.board.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.board.domain.CommentVO;
import com.example.demo.board.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Resource(name = "com.example.demo.board.service.CommentService")
	CommentService mCommentService;
	
	// 댓글 목록
	@RequestMapping("/list")
	private List<CommentVO> mCommentServiceList(Model model,int bno) throws Exception{
		
		return mCommentService.commentListService(bno);
	}
	
	// 댓글 등록
	@RequestMapping("/insert")
	private int mCommentServiceInsert(@RequestParam int bno, @RequestParam String content) throws Exception{
		
		CommentVO comment = new CommentVO();
		comment.setBno(bno);
		comment.setContent(content);
		comment.setWriter("test");
		
		return mCommentService.commentInsertService(comment);
	}
	
	// 댓글 수정
	@RequestMapping("/update")
	private int mCommentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception{
		
		CommentVO comment = new CommentVO();
		comment.setCno(cno);
		comment.setContent(content);
		
		return mCommentService.commentUpdateService(comment);
		
	}
	// 댓글 삭제
	@RequestMapping("/delete/{cno}")
	private int mCommentServiceDelete(@PathVariable int cno) throws Exception{
		return mCommentService.commentDeleteService(cno);
	}
	
	
}
