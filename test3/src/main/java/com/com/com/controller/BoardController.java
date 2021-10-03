package com.com.com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.com.com.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService service;
	private final String CURR_IMAGE_REPO_PATH = "/Users/choejuyeong/Desktop/upload/";

	@RequestMapping("list")
	public String list(@RequestParam Map<String, Object> map, Model model) {

		if (map.isEmpty()) {
			map.put("pageNo", 1);
			map.put("listSize", 10);
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = service.list(map);

		Map<String, Object> pageMap = service.pagination(map);

		model.addAttribute("list", list);
		model.addAttribute("map", map);
		model.addAttribute("pageMap", pageMap);

		return "board/list";
	}

	@RequestMapping("writer")
	public String writer() {
		return "board/write";
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(@RequestParam Map<String, Object> map, MultipartHttpServletRequest mr) throws Exception {

		mr.setCharacterEncoding("utf-8");
		Iterator<String> itr = mr.getFileNames();
		int seq = service.maxSeq();

		while (itr.hasNext()) {
			MultipartFile mfile = mr.getFile(itr.next());
			if (mfile.getSize() > 0) {
				UUID ui = UUID.randomUUID();
				String realFile = mfile.getOriginalFilename();
				String saveFile = ui + "_" + mfile.getOriginalFilename();
				mfile.transferTo(new File(CURR_IMAGE_REPO_PATH + saveFile));

				Map<String, Object> fileMap = new HashMap<String, Object>();
				fileMap.put("REAL_NAME", realFile);
				fileMap.put("SAVE_NAME", saveFile);
				fileMap.put("SAVE_PATH", CURR_IMAGE_REPO_PATH);
				fileMap.put("LIST_SEQ", seq);

				int fileInsert = service.fileInsert(fileMap);

			}
		}

		map.put("seq", seq);

		int insert = service.insert(map);

		if (insert == 0) {

		} else {

		}

		return "redirect:list";
	}

	@RequestMapping("detail")
	public String detail(@RequestParam int seq, Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		Map<String, Object> map = service.detail(seq);
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		files = service.files(seq);
		 Cookie[] cookies = request.getCookies();
	        
	        // 비교하기 위해 새로운 쿠키
	        Cookie viewCookie = null;
	        // 쿠키가 있을 경우 
	        if (cookies != null && cookies.length > 0) 
	        {
	            for (int i = 0; i < cookies.length; i++)
	            {
	                // Cookie의 name이 cookie + reviewNo와 일치하는 쿠키를 viewCookie에 넣어줌 
	                if (cookies[i].getName().equals("cookie"+seq))
	                { 
	                    System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
	                    viewCookie = cookies[i];
	                }
	            }
	        }
	        
	            
	 
	            // 만일 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리함.
	            if (viewCookie == null) {    
	                System.out.println("cookie 없음");
	                
	                // 쿠키 생성(이름, 값)
	                Cookie newCookie = new Cookie("cookie"+seq, "|" + seq + "|");
	                                
	                // 쿠키 추가
	                response.addCookie(newCookie);
	 
	                // 쿠키를 추가 시키고 조회수 증가시킴
	                int result = service.count(seq);
	                
	                if(result>0) {
	                    System.out.println("조회수 증가");
	                }else {
	                    System.out.println("조회수 증가 에러");
	                }
	            }
	            // viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
	         
	        

		
		model.addAttribute("map", map);
		model.addAttribute("files", files);

		return "board/write";
	}

	@RequestMapping("update")
	public String update(@RequestParam Map<String, Object> map) {

		int update = service.update(map);

		return "redirect:list";
	}

	@RequestMapping("delete")
	public String delete(Integer[] chk, @RequestParam Map<String, Object> map) {

		List<Integer> list = Arrays.asList(chk);

		int delete = service.delete(list);
		int filedelete = service.filedelete(list);
		
		return "redirect:list";
	}

	@RequestMapping("searchlist")
	public String searchlist(@RequestParam Map<String, Object> map, Model model) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = service.list(map);

		Map<String, Object> pageMap = service.pagination(map);

		model.addAttribute("list", list);
		model.addAttribute("pageMap", pageMap);

		return "board/searchlist";
	}

	@RequestMapping("filedown")
	public void down(@RequestParam String saveFile, @RequestParam String realFile, HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		String saveDir = CURR_IMAGE_REPO_PATH;
		String fileName = saveFile;
		File file = new File(saveDir + "/" + fileName);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ServletOutputStream sos = null;

		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			String reFilename = "";

			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE") != -1
					|| request.getHeader("user-agent").indexOf("Trident") != -1;
			if (isMSIE) {
				reFilename = URLEncoder.encode(realFile, "utf-8");
				reFilename = realFile.replaceAll("\\+", "%20");
			} else {
				reFilename = new String(realFile.getBytes("utf-8"), "ISO-8859-1");
			}

			response.setContentType("application/octet-stream;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + reFilename + "\"");
			response.setContentLength((int) file.length());
			int read = 0;
			while ((read = bis.read()) != -1) {
				sos.write(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	

}
