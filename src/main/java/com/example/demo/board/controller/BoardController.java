package com.example.demo.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.domain.AjaxVO;
import com.example.demo.board.domain.BoardVO;
import com.example.demo.board.domain.Criteria;
import com.example.demo.board.domain.FileVO;
import com.example.demo.board.domain.PageMakerVO;
import com.example.demo.board.service.BoardService;
import com.example.demo.board.service.CommentService;

@Controller
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Resource(name = "com.example.demo.board.service.BoardService")
	BoardService mBoardService;
	
	@Resource(name = "com.example.demo.board.service.CommentService")
	CommentService mCommentService;
	
	
	/*
	  @RequestMapping("/") 
	  private String boardList(Model model) throws Exception{
	  
	  model.addAttribute("list", mBoardService.boardListService());
	  
	  return "list"; 
	  }
	 */
	
	
	// 리스트(페이징, 검색)
	@RequestMapping("/list")
	private String boardList(Model model, Criteria cri, HttpSession session)throws Exception {
		model.addAttribute("list", mBoardService.getListPaging(cri));
		logger.info(mBoardService.getListPaging(cri).toString());
		int total = mBoardService.getTotal(cri);
		
		PageMakerVO pageMake = new PageMakerVO(cri, total);
		logger.info(cri.toString());
		model.addAttribute("cri", cri);
		model.addAttribute("pageMaker", pageMake);
		session.setAttribute("kwd", cri.getKeyword());
		session.setAttribute("tp", cri.getType());
		return "list";
	}
	
	// 게시판 조회
	@GetMapping("/view" )
	private String boardView(@RequestParam("bno") Integer bno,
							@RequestParam("pageNum")Integer pageNum,	Model model, Criteria cri, HttpSession session) throws Exception{
		model.addAttribute("keyword", session.getAttribute("kwd"));
		model.addAttribute("type", session.getAttribute("tp"));
		session.setAttribute("pageNum", pageNum);
		BoardVO boardVO = mBoardService.boardViewService(bno);
		logger.info(boardVO.toString());
		int total = mBoardService.getTotal(cri);
		
		PageMakerVO pageMake = new PageMakerVO(cri, total);
		logger.info(session.getAttribute(bno.toString())+" 게시글 세션");
		if(boardVO.getIs_private().equals("Y")) {
			try {
				if(session.getAttribute(bno.toString()).equals("chk")) {
					int comCount = mCommentService.commentCount(bno);
					model.addAttribute("view", boardVO);
					model.addAttribute("files", mBoardService.fileViewService(bno));
					model.addAttribute("hit", mBoardService.boardHitsUpdate(bno));
					model.addAttribute("cri", cri);
					
					
				}
			} catch (Exception e) {
				model.addAttribute("state", "비밀글입니다");
				model.addAttribute("pageMaker", pageMake);
				model.addAttribute("view", boardVO);
				model.addAttribute("bno", bno);
				return "chkpwd";
			}
			return "view";

		}
		else {
			int comCount = mCommentService.commentCount(bno);
			model.addAttribute("view", boardVO);
			model.addAttribute("files", mBoardService.fileViewService(bno));
			model.addAttribute("hit", mBoardService.boardHitsUpdate(bno));
			model.addAttribute("cri", cri);
			
			return "view";
		}
		

	}
	@RequestMapping("/chkpwds/{bno}")
	@ResponseBody
	private String chkPrivate(AjaxVO vo,
		Model model, Criteria cri, HttpSession session) throws Exception{
		
		logger.info(vo.toString());
		String state = "-1";
		
		if(vo.getPassword().equals(mBoardService.boardDeleteChk(vo.getBno()))) {
			BoardVO boardVO = mBoardService.boardViewService(vo.getBno());
			session.setAttribute(  String.valueOf(vo.getBno()) , "chk");
			
			model.addAttribute("view", boardVO);
			model.addAttribute("files", mBoardService.fileViewService(vo.getBno()));
			model.addAttribute("hit", mBoardService.boardHitsUpdate(vo.getBno()));
			model.addAttribute("cri", cri);
			state = "200";
			return state;
		}
		else {
			return state;
		}
		
		
		

		
		
	}
	

	// 등록창 
	@RequestMapping("/insert")
	private String boardInsertForm() {
		
		return "insert";
	}
	
	// 등록문(게시글, 파일)
	@RequestMapping("/insertProc")
	private String boardInsertProc(HttpServletRequest request, @RequestPart List<MultipartFile> files) throws Exception{
		
		BoardVO board = new BoardVO();
		FileVO fileVO = new FileVO();
		board.setPassword(request.getParameter("password"));
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setWriter(request.getParameter("writer"));
		board.setPassword(request.getParameter("password"));
		try {
			if(request.getParameter("secret").equals("on")) {
				board.setIs_private("Y");
			}
			else {
				board.setIs_private("N");
			}
		} catch(Exception e) {
			board.setIs_private("N");
		}
		
		

		if(files.get(0).getOriginalFilename().equals("")) {
			board.setHas_file("N");
			logger.info(board.toString());
			mBoardService.boardInsertService(board);
		}else {
			board.setHas_file("Y");
			logger.info(board.toString());
			mBoardService.boardInsertService(board);
			for (int i =0; i<files.size(); i++) {
				MultipartFile file = files.get(i);
			String fileName = file.getOriginalFilename();
			String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
			File destinationFile;
			String destinationFileName;
			String fileUrl = "C:/eclipse/workspace/demo/src/main/webapp/WEB-INF/uploadFiles/";
			
			do {
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
				destinationFile = new File(fileUrl + destinationFileName);			
			} while (destinationFile.exists());
			
			destinationFile.getParentFile().mkdirs();
			file.transferTo(destinationFile);
			
			
			fileVO.setBno(board.getBno());
			fileVO.setFileName(destinationFileName);
			fileVO.setFileOriName(fileName);
			fileVO.setFileUrl(fileUrl);

			mBoardService.fileInsertService(fileVO);
			
		}
		}
		return "redirect:/list";
	}
	
	//  창
	@RequestMapping("/update/{bno}")
	private String boardUpdateForm(@PathVariable("bno") Integer bno, Criteria cri, Model model) throws Exception {
		
		model.addAttribute("view", mBoardService.boardViewService(bno));
		
		model.addAttribute("cri", cri);
		
		model.addAttribute("files", mBoardService.fileViewService(bno));
		model.addAttribute("fno", mBoardService.fileViewService(bno).get(0));
		logger.info( mBoardService.fileViewService(bno).toString());
		
		return "update";
	}
	
	// 수정문 
	@RequestMapping("/updateProc")
	private String boardUpdateProc(HttpServletRequest request,  @RequestPart MultipartFile files, Model model, HttpSession session) throws Exception{
		int bno = Integer.parseInt(request.getParameter("bno"));

		

		BoardVO board = new BoardVO();
		FileVO file = new FileVO();
		Criteria cri = new Criteria();
		
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setBno(Integer.parseInt(request.getParameter("bno")));
		
		if(files.getOriginalFilename().equals("")) {
			board.setHas_file("N");
			logger.info(board.toString());
			mBoardService.boardUpdateService(board);
		}else {
			
			String fileName = files.getOriginalFilename();
			String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
			File destinationFile;
			String destinationFileName;
			String fileUrl = "C:/eclipse/workspace/demo/src/main/webapp/WEB-INF/uploadFiles/";
			
			do {
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
				destinationFile = new File(fileUrl + destinationFileName);			
			} while (destinationFile.exists());
			
			destinationFile.getParentFile().mkdirs();
			files.transferTo(destinationFile);
			board.setHas_file("Y");
			mBoardService.boardUpdateService(board);
			
			file.setBno(board.getBno());
			file.setFileName(destinationFileName);
			file.setFileOriName(fileName);
			file.setFileUrl(fileUrl);
			
			mBoardService.fileInsertService(file);
		}
		
		mBoardService.boardUpdateService(board);
		
		return "redirect:/view?bno=" + request.getParameter("bno") + "&pageNum="+session.getAttribute("pageNum");

	}
	

	// 삭제 창
	@RequestMapping("/deletes/{bno}")
	private String boardDeleteViewForm(@PathVariable int bno, Model model, Criteria cri) throws Exception {
		
		model.addAttribute("view", mBoardService.boardViewService(bno));
		
		model.addAttribute("cri", cri);
		
		 
		return "deletes";
	}
	
	
	
	// 게시글 삭제
	@RequestMapping("/delete/{bno}")
	@ResponseBody
	private String boardDeleteProc(AjaxVO vo) throws Exception{
		logger.info(vo.toString());
		String state = "-1";
		
		if(vo.getPassword().equals(mBoardService.boardDeleteChk(vo.getBno()))) {
			mBoardService.boardDeleteService(vo.getBno());
			state = "200";
			return state;
		}
		else {
			return state;
		}

	}
	
	@RequestMapping("/updateChk")
	@ResponseBody
	private String updateChk(AjaxVO vo) throws Exception{
		logger.info(vo.toString());
		String state = "-1";
		
		if(vo.getPassword().equals(mBoardService.boardDeleteChk(vo.getBno()))) {
			if(vo.getFileDeleteYN().equals("Y")) {
				mBoardService.fileDelete4Board(vo.getBno());
				mBoardService.fileDeleteService(vo.getFno());
				state = "200del";
			}
			else {
				state = "200";
			}
			
			return state;
		}
		else {
			return state;
		}

	}
	
	// 파일삭제
	@RequestMapping("/fdelete/{fno}")
	private void fileDelete(@PathVariable int fno, Integer bno) throws Exception{
		logger.info("파일삭제!!!!!!!!!!!!!!!!");
		mBoardService.fileDelete4Board(bno);
		mBoardService.fileDeleteService(fno);
		
	}
	
	// 파일 다운로드
	@RequestMapping("/fileDown/{fno}")
		private void fileDown(@PathVariable int fno, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		FileVO fileVO = mBoardService.fileViewDetailService(fno);
		
		try {
			String fileUrl = fileVO.getFileUrl();
			fileUrl += "/";
			String savePath = fileUrl;
			String fileName = fileVO.getFileName();
			
			String oriFileName = fileVO.getFileOriName();
			InputStream in = null;
			OutputStream os = null;
			File file = null;
			boolean skip = false;
			String client = "";
			
			try {
				file = new File(savePath, fileName);
				in = new FileInputStream(file);
			}catch (FileNotFoundException fe) {
				skip = true;
			}
			
			client = request.getHeader("User-Agent");
			
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "Jsp Generated Data");
			
			if (!skip) {
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				} else if (client.indexOf("Trident") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\"" 
							+ java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				} else {
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;
				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html;charset=UTF-8");
				System.out.println("<script language='javascript'> alert('파일을 찾을 수 없습니다');history.back(); </script>");
			}
			
			in.close();
			os.close();
		}catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

}
