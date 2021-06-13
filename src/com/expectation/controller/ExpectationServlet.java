package com.expectation.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.expectation.model.*;
import com.movie.model.*;
import com.rating.model.RatingVO;

public class ExpectationServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str1 = req.getParameter("memberno");
				if (str1 == null || (str1.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				
				String str2 = req.getParameter("movieno");
				if (str2 == null || (str2.trim()).length() == 0) {
					errorMsgs.add("請輸入電影編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer memberno = null;
				try {
					memberno = new Integer(str1);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				
				Integer movieno = null;
				try {
					movieno = new Integer(str2);
				} catch (Exception e) {
					errorMsgs.add("電影編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ExpectationService expectationSvc = new ExpectationService();
				ExpectationVO expectationVO = expectationSvc.getOneExpectation(memberno,movieno);
				if (expectationVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("expectationVO", expectationVO); // 資料庫取出的expectationVO物件,存入req
				String url = "/front-end/expectation/listOneExpectation.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneExpectation.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/index.jsp");
				failureView.forward(req, res);
			}
		}
		
		
//		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp 的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】		
//			
//			try {
//				/***************************1.接收請求參數****************************************/
//				Integer memberno = new Integer(req.getParameter("memberno"));
//				Integer movieno = new Integer(req.getParameter("movieno"));
//				
//				/***************************2.開始查詢資料****************************************/
//				ExpectationService expectationSvc = new ExpectationService();
//				ExpectationVO expectationVO = expectationSvc.getOneExpectation(memberno,movieno);
//								
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				req.setAttribute("expectationVO", expectationVO); // 資料庫取出的expectationVO物件,存入req
//				String url = "/front-end/expectation/update_expectation_input.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_expectation_input.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
//		}
//		
//		
//		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
//		
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				Integer memberno = new Integer(req.getParameter("memberno").trim());
//				
//				Integer movieno = new Integer(req.getParameter("movieno").trim());
//				
//				Double expectation = new Double(req.getParameter("expectation").trim());
//
//				ExpectationVO expectationVO = new ExpectationVO();
//				expectationVO.setMemberno(memberno);
//				expectationVO.setMovieno(movieno);
//				expectationVO.setExpectation(expectation);				
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("expectationVO", expectationVO); // 含有輸入格式錯誤的expectationVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/front-end/expectation/update_expectation_input.jsp");
//					failureView.forward(req, res);
//					return; //程式中斷
//				}
//				
//				/***************************2.開始修改資料*****************************************/
//				ExpectationService expectationSvc = new ExpectationService();
//				expectationVO = expectationSvc.updateExpectationAndUpdateMovieExpectation(memberno, movieno, expectation);
//				
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/				
////				DeptService deptSvc = new DeptService();
////				if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
////					req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(deptno)); // 資料庫取出的list物件,存入request
//
//                String url = requestURL;
//				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/expectation/update_expectation_input.jsp");
//				failureView.forward(req, res);
//			}
//		}

        if ("insertOrUpdate".equals(action)) { // 來自addEmp.jsp的請求  
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			
			String requestURL = req.getParameter("requestURL");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				Integer memberno = new Integer(req.getParameter("memberno").trim());
				
				Integer movieno = new Integer(req.getParameter("movieno").trim());
				
				Double expectation = new Double(req.getParameter("expectation").trim());
				
//				ExpectationVO expectationVO = new ExpectationVO();
//				expectationVO.setMemberno(memberno);
//				expectationVO.setMovieno(movieno);
//				expectationVO.setExpectation(expectation);	

				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("expectationVO", expectationVO); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/front-end/expectation/addExpectation.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				
				/***************************2.開始新增資料***************************************/
				ExpectationService expectationSvc = new ExpectationService();
//				expectationVO = expectationSvc.insertOrUpdateExpectationAndUpdateMovieExpectation(memberno , movieno , expectation);
				expectationSvc.insertOrUpdateExpectationAndUpdateMovieExpectation(memberno , movieno , expectation);
				ExpectationVO expectationVO = expectationSvc.getThisMovieToatalExpectation(movieno);
				
				//將原本的movievo塞回去
				MovieService movieSvc = new MovieService();
				MovieVO movieVO = movieSvc.getOneMovie(movieno);
				if (movieVO == null) {
					errorMsgs.add("查無資料");
				}
				req.setAttribute("movieVO", movieVO);
				
				//將最新的期待度丟回去
				double newExpectation=movieVO.getExpectation();
				double countExpectation = expectationVO.getExpectation();
				JSONObject jsonobj = new JSONObject();
				try {
					jsonobj.put("newExpectation", newExpectation);
					jsonobj.put("countExpectation", countExpectation);
					out.print(jsonobj.toString());
					return;
				}catch(JSONException e) {
					e.printStackTrace();
				}finally {
					out.flush();
					out.close();
				}

				/***************************3.新增完成,準備轉交(Send the Success view)***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/expectation/addExpectation.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/***************************1.接收請求參數***************************************/
				Integer memberno = new Integer(req.getParameter("memberno").trim());
				
				Integer movieno = new Integer(req.getParameter("movieno").trim());
				
				/***************************2.開始刪除資料***************************************/
				ExpectationService expectationSvc = new ExpectationService();
//				ExpectationVO expectationVO = expectationSvc.getOneExpectation(memberno,movieno);
				expectationSvc.deleteExpectation(memberno,movieno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
//				DeptService deptSvc = new DeptService();
//				if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
//					req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno())); // 資料庫取出的list物件,存入request
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}
