package com.example.demo.board.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.board.domain.BoardVO;
import com.example.demo.board.domain.Criteria;
import com.example.demo.board.domain.FileVO;

@Repository("com.example.demo.board.mapper.BoardMapper")
public interface BoardMapper {

	// 게시글 개수	
	public int getTotal(Criteria cri) throws Exception;
	
	// 게시글 목록
	public List<BoardVO> boardList() throws Exception;
	
	// 게시글 조회
	public BoardVO boardView(int bno) throws Exception;
	
	// 게시글 작성
	public int boardInsert(BoardVO board) throws Exception;
	
	// 게시글 수정
	public int boardUpdate(BoardVO board) throws Exception;
	
	
	// 게시글 삭제 비번 chk
	public String boardDeleteChk(int bno) throws Exception;
	// 게시글 삭제
	public int boardDelete(int bno) throws Exception;
	
	// 파일업로드
	public int fileInsert(FileVO file) throws Exception;
	
	// 파일 상세
	public List<FileVO> fileView(int bno) throws Exception;
	
	public FileVO fileViewDetail(int fno) throws Exception;
	
	// 파일 삭제
	public int fileDelete(int cno) throws Exception;
	
	// 조회수
	public int boardHitsUpdate(int bno) throws Exception;
	
	// 게시판 목록(페이징)
	public List<BoardVO> getListPaging(Criteria cri) throws Exception;
	
	// VO에 파일 여부 리턴
	public String chkFile(int bno) throws Exception;
	
	// 파일 삭제
	public int fileDelete4Board(int bno) throws Exception;
	
	
}
