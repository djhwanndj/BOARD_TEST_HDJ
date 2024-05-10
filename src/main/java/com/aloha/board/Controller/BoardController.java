package com.aloha.board.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.aloha.board.dto.Board;
import com.aloha.board.service.BoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	// 게시글 목록 [GET]
	@GetMapping("/list")
	public String list(Model model) throws Exception {
		// 데이터 불러오기
		List<Board> boardList = boardService.list();
		// 모델에 등록
		model.addAttribute("boardList", boardList);
		// 리다이렉트
		return "/board/list";
	}
	
	// 게시글 조회 [GET]
	@GetMapping("/read")
	public String select(@RequestParam("no") int no, Model model) throws Exception {
		// 데이터 불러오기
		Board board = boardService.select(no);
		// 모델에 등록
		model.addAttribute("board", board);
		return "/read";
	}
	
	// 게시글 입력 화면 [GET]
	@GetMapping("/insert")
	public String insert() {
		return "/board/insert";
	}
	
	// 게시글 입력 처리 [POST]
	@PostMapping("/insert")
	public String insertPro(Board board) throws Exception {
		int result = boardService.insert(board);
		if (result > 0 ) {
			return "redirect:/board/list";
		}
		return "redirect:/board/insert";
	}
	
	// 게시글 수정 화면 [GET]
	@GetMapping("/update")
	public String update(@RequestParam("no") int no, Model model) throws Exception {
		Board board = boardService.select(no);
		model.addAttribute(board);
		return "/board/update?no=" + no;
	}
	
	@PostMapping("/update")
	public String updatePro(Board board, @RequestParam("no") int no) throws Exception {
		// 데이터 요청
		int result = boardService.update(board);
		if (result > 0 ) {
			return "redirect:/board/list";
		}
		return  "redirect:/update?no = " + no + "&error";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("no") int no) throws Exception {
		int result = boardService.delete(no);
		if (result > 0 ) {
			return "redirect:/board/list";
		}
		return  "redirect:/update?no = " + no + "&error";
	}
	
	
}
