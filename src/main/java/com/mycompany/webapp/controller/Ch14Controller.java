package com.mycompany.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.Ch14Board;
import com.mycompany.webapp.dto.Ch14Employee;
import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.dto.Ch14Pager;
import com.mycompany.webapp.service.Ch14BoardService;
import com.mycompany.webapp.service.Ch14EmployeeService;
import com.mycompany.webapp.service.Ch14MemberService;

@Controller
@RequestMapping("/ch14")
public class Ch14Controller {
	private static final Logger logger=
			LoggerFactory.getLogger(Ch14Controller.class);
	
	@GetMapping("/content")
	public String content() {
		return "ch14/content";
	}
	
	@Resource
	private DataSource dataSource;
	
	/*
	이전버전
	@GetMapping("/conntest")
	public String conntest() {
		try {
			//커넥션 풀에서 커넥션 대여
			Connection conn= dataSource.getConnection();
			logger.info("연결성공");
			//대여한 커넥션 반납
			conn.close();
		} catch (SQLException e) {
			logger.info("연결실패");
			e.printStackTrace();
		}
		return "ch14/content";
	}*/
	
	@GetMapping("/conntest")
	public String conntest(Model model) {
		try {
			//커넥션 풀에서 커넥션 대여
			Connection conn= dataSource.getConnection();
			model.addAttribute("result", "연결성공");
			//대여한 커넥션 반납
			conn.close();
		} catch (SQLException e) {
			model.addAttribute("result", "연결실패");
			e.printStackTrace();
		}
		return "ch14/conntest";
	}
	
	@GetMapping("/jasonresponse1")
	public void jasonresponse1(HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		//JSON: JavaScript Object Notations (자바 스크립트 객체 표기법)
		/*pw.println("{");
		pw.println("\"name\":\"홍길동\",");
		pw.println("\"age\":30,");
		pw.println("\"car\":{\"kind\":\"그랜저\", \"color\":\"흰색\"},");
		pw.println("\"hobby\":[\"영화\", \"여행\", \"수영\"]");
		pw.println("}");*/
		
		JSONObject root = new JSONObject();
		root.put("name", "홍길동");
		root.put("age", 30);
		
		JSONObject car = new JSONObject();
		car.put("kind", "그랜저");
		car.put("color", "흰색");
		root.put("car", car);
		
		JSONArray hobby = new JSONArray();
		hobby.put("영화");
		hobby.put("여행");
		hobby.put("수영");
		root.put("hobby", hobby);
		
		String json= root.toString();
		pw.println(json);
		
		pw.flush();
		pw.close();
	}
	
	@GetMapping("/jasonresponse2")
	public void jasonresponse2(HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		/*pw.println("[");
		pw.println("	{\"bno\":1, \"btitle\":\"제목1\", \"bwriter\":\"글쓴이1\"},");
		pw.println("	{\"bno\":2, \"btitle\":\"제목2\", \"bwriter\":\"글쓴이2\"},");
		pw.println("	{\"bno\":3, \"btitle\":\"제목3\", \"bwriter\":\"글쓴이3\"}");
		pw.println("]");*/
		
		JSONArray root= new JSONArray();
		for(int i=1; i<=3 ; i++) {
			JSONObject board = new JSONObject();
			board.put("bno", i);
			board.put("btitle", "제목"+i);
			board.put("bwriter", "글쓴이"+i);
			root.put(board);
		}
		String json = root.toString();
		pw.println(json);
		
		pw.flush();
		pw.close();		
	}
	
	
	
	@Resource
	private Ch14EmployeeService employeeService;
	
	/*@GetMapping("/employee")
	public String employee(int employee_id) {
		Ch14Employee emp = employeeService.getEmployee(employee_id);
		logger.info("employee_id: " +emp.getEmployee_id());
		logger.info("first_name: " +emp.getFirst_name());
		logger.info("last_name: " +emp.getLast_name());		
		return "ch14/content";
	}*/
	
	@GetMapping("/employee")
	public void employee(HttpServletResponse response, int employee_id) throws Exception {
		Ch14Employee emp = employeeService.getEmployee(employee_id);
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
				
		JSONObject root = new JSONObject();
		root.put("employee_id", emp.getEmployee_id());
		root.put("first_name", emp.getFirst_name());
		root.put("last_name", emp.getLast_name());
		
		
		String json= root.toString();
		pw.println(json);
		
		pw.flush();
		pw.close();		
	}
	@Resource
	private Ch14BoardService boardService;
	
	@GetMapping("/boardlist")
	public String boardlist(Model model) {
		List<Ch14Board> list = boardService.getBoardList();
		model.addAttribute("list", list);
		
		return "ch14/boardlist";
	}
	
	@GetMapping("/boardlist2")
	public String boardlist2(
			@RequestParam(defaultValue="1") int pageNo,
			Model model) {
		int totalRows = boardService.getTotalRows();
		Ch14Pager pager = new Ch14Pager(6, 5, totalRows, pageNo);
		List<Ch14Board> list = boardService.getBoardList(pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		
		return "ch14/boardlist";
	}
	
	@GetMapping("/boardsave")
	public String boardsave() {
		/*for(int i=1; i<=100; i++) {
			Ch14Board board = new Ch14Board();  // 일시적으로 강제 데이터 입력을 위해 넣어줌
			board.setBtitle("제목"+i);
			board.setBcontent("내용"+i);
			board.setBwriter("winter");
			boardService.saveBoard(board);
		}*/
		return "redirect:/ch14/boardlist";
		
	}
	
	@GetMapping("/boardwrite")
	public String boardwriteForm() {
		return "ch14/boardwrite";
	}
	
	@PostMapping("/boardwrite")
	public String boardwrite(Ch14Board board, HttpSession session) throws Exception{
		String mid = (String) session.getAttribute("sessionMid");
		board.setBwriter(mid);
		
		MultipartFile mf = board.getBattach();
		if(!mf.isEmpty()) {
			board.setBattachoname(mf.getOriginalFilename());
			String saveName = new Date().getTime() + "-" + mf.getOriginalFilename();
			board.setBattachsname(saveName);
			board.setBattachtype(mf.getContentType());
			//파일저장
			File saveFile = new File("D:/MyWorkspace/uploadfiles/boards/"+saveName);  //properties 파일로 뺄것
			mf.transferTo(saveFile);
		}
		
		
		
		boardService.saveBoard(board);
		return "redirect:/ch14/boardlist2";
	}
	
	@GetMapping("/join")
	public String joinForm() {
		return "ch14/join";
	}
	@Resource
	private Ch14MemberService memberService;
	
	@PostMapping("/join")
	public String join(Ch14Member member) throws Exception{
		//파일 정보 얻기
		MultipartFile mf = member.getMphoto();
		if(!mf.isEmpty()) {
			member.setMphotooname(mf.getOriginalFilename());
			String saveName = new Date().getTime() + "-" + mf.getOriginalFilename();
			member.setMphotosname(saveName);
			member.setMphototype(mf.getContentType());
			//파일저장
			File saveFile = new File("D:/MyWorkspace/uploadfiles/members/"+saveName);
			mf.transferTo(saveFile);
		}
		//DB에 저장		
		memberService.join(member);		
		return "redirect:/ch14/boardlist2";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "ch14/login";
	}

	
	@PostMapping("/login")
	public void login(Ch14Member member,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		//success, wrongMid, wrongPassword
		String result = memberService.login(member);
		if(result.equals("success")) {
			session.setAttribute("sessionMid",member.getMid());
		}
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		//{"result":"success"}
		JSONObject root = new JSONObject();
		root.put("result", result);
		pw.println(root.toString());
		pw.flush();
		pw.close();
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/ch14/boardlist2";
	}
	
	@GetMapping("/mphoto")
	public void mphoto(String mid, HttpSession session, HttpServletResponse response) throws Exception{
		if(mid==null) {
			mid = (String) session.getAttribute("sessionMid");
		}
		Ch14Member member = memberService.getMember(mid);
		String filePath=null;
		if(member.getMphotosname() !=null) {   ///첨부파일이 있을때
			String mphotosname = member.getMphotosname();		
			filePath = "D:/MyWorkspace/uploadfiles/members/" + mphotosname;		
		
			response.setContentType(member.getMphototype()); //받아올 사진타입을 받아오기 위함.
			
			String mphotooname = member.getMphotooname();
			mphotooname=new String(mphotooname.getBytes("UTF-8"), "ISO-8859-1");  // 한글 파일명을 살리기 위함.
			response.setHeader("Content-Disposition", "attachment; filename=\""+mphotooname+"\"");  //원본이름으로 주기위함
			
		} else {							// 첨부파일이 없을때
			filePath = "D:/MyWorkspace/uploadfiles/members/defaultphoto.png";		
			response.setContentType("image/png");			
		}
		OutputStream os= response.getOutputStream();
		InputStream is = new FileInputStream(filePath);
		
		FileCopyUtils.copy(is, os);
		os.flush();
		os.close();
		is.close();
		}
	
	@GetMapping("/boardread")
	public String boardread(int bno, Model model) {
		boardService.addHitcount(bno);
		Ch14Board board= boardService.getboard(bno);
		model.addAttribute("board", board);
		return "ch14/boardread";
	}
	
	@GetMapping("/boardupdate")
	public String boardupdateform(int bno, Model model) {
		Ch14Board board= boardService.getboard(bno);
		model.addAttribute("board", board);
		return "ch14/boardupdate";
	}
	
	@PostMapping("/boardupdate")
	public String boardupdate(Ch14Board board) {
		boardService.updateBoard(board);		
		return "redirect:/ch14/boardlist2";
		
		
		
	}
	
	@GetMapping("/boarddelete")
	public String boarddelete(int bno) {
		boardService.deleteBoard(bno);
		return "redirect:/ch14/boardlist2";
	}
	
	
	
	
	
	}
	


