package com.example.demo.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.board.domain.BoardVO;
import com.example.demo.board.domain.Criteria;
import com.example.demo.board.domain.FileVO;
import com.example.demo.board.mapper.BoardMapper;

@Service("com.example.demo.board.service.BoardService")
public class BoardService {

	
	@Resource(name = "com.example.demo.board.mapper.BoardMapper")
	BoardMapper mBoardMapper;
	
	@Resource(name = "com.example.demo.board.service.CommentService")
	CommentService mCommentService;
	
	public int getTotal(Criteria cri) throws Exception {
		
		return mBoardMapper.getTotal(cri);
	}
	
	public List<BoardVO> boardListService() throws Exception{
		
		return mBoardMapper.boardList();
	}
	
	public BoardVO boardViewService(int bno) throws Exception{
		
		return mBoardMapper.boardView(bno);
	}
	
	public int boardInsertService(BoardVO board) throws Exception{
		
		return mBoardMapper.boardInsert(board);
	}
	
	public int boardUpdateService(BoardVO board) throws Exception{
		
		return mBoardMapper.boardUpdate(board);
	}
	public String boardDeleteChk(int bno) throws Exception{
		
		return mBoardMapper.boardDeleteChk(bno);
	}
	public int boardDeleteService(int bno) throws Exception{
		
		return mBoardMapper.boardDelete(bno);
	}
	
	public int fileInsertService(FileVO file) throws Exception{
		return mBoardMapper.fileInsert(file);
	}
	
	public List<FileVO> fileViewService(int bno) throws Exception{
		
		return mBoardMapper.fileView(bno);
	}
	
	public FileVO fileViewDetailService(int fno) throws Exception{
		
		return mBoardMapper.fileViewDetail(fno);
	}
	
	public int fileDeleteService(int cno) throws Exception{
	
	return mBoardMapper.fileDelete(cno);
}
	
	public int  boardHitsUpdate(int bno) throws Exception {
		
		return mBoardMapper.boardHitsUpdate(bno);
	}
	
	public List<BoardVO> getListPaging(Criteria cri) throws Exception{
		List<BoardVO> boardVOs = mBoardMapper.getListPaging(cri);
		for(int i=0; i < boardVOs.size(); i++) {
			BoardVO board = boardVOs.get(i);
			board.setHas_file(chkFile(board.getBno()));
			board.setComCount(mCommentService.commentCount(board.getBno()));
		}
		
		return boardVOs;
	}
	public String chkFile(int bno) throws Exception{
		return mBoardMapper.chkFile(bno);
	}
	public int fileDelete4Board(int bno) throws Exception{
		return mBoardMapper.fileDelete4Board(bno);
	}

	

}
