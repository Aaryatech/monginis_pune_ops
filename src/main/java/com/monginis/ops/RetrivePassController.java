package com.monginis.ops;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.Info;

@Controller
public class RetrivePassController {
	
	Instant start = null;
	
	@RequestMapping("/forgetPwd")
	public ModelAndView forgetPwd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("forgotpassword");
		return mav;
	}
	
	
	@RequestMapping(value = "/getFranchiseeInfo", method = RequestMethod.POST)
	public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		ModelAndView model = null;
		
	try{
		
		RestTemplate rest = new RestTemplate();
		String frCode = request.getParameter("username");
		System.out.println("frCode--------------------------"+frCode);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("frCode", frCode);
		
		Franchisee franchisee = rest.postForObject(Constant.URL + "getFranchiseeByFrCode", map, Franchisee.class);
		System.err.println("Franchisee-----------"+franchisee);
		if(franchisee!=null) {
			model = new ModelAndView("verifyOTP");
			model.addObject("frCode", frCode);
			info.setError(false);
			info.setMessage("User Found");
			System.err.println(info);
			
			start = Instant.now();

			;
		}else {
			info.setError(true);
			model = new ModelAndView("forgotpassword");
			info.setMessage("User Not Found");
			System.err.println(info);
		}
	}catch (Exception e) {
		e.printStackTrace();		
	}
		
		return model;
		
	}
	
	
	
	@RequestMapping(value = "/OpsOTPVerification", method = RequestMethod.POST)
	public ModelAndView OTPVerificationByContact(HttpServletRequest request, HttpServletResponse response) {

		System.err.println("Hiii  OpsOTPVerification  ");
		Info info = new Info();
		ModelAndView model = null;

		try {
			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			String otp = request.getParameter("otp");

			map.add("otp", otp);

			Franchisee franchisee = rest.postForObject(Constant.URL + "verifyOPSOTP", map, Franchisee.class);	
			System.err.println("OTP franchisee--------------"+franchisee);

			if (franchisee.getFrId() == 0) {
				model = new ModelAndView("forgotpassword");
				model.addObject("msg", "Incorrect OTP");

			} else {				
				System.err.println("Franchisee----------------" + franchisee);
				model = new ModelAndView("changePassword");
				model.addObject("frId", franchisee.getFrId());
				
			}

		} catch (Exception e) {
			System.err.println("Exce in OTPVerification  " + e.getMessage());
			e.printStackTrace();
		}

		return model;

	}
	
	
	@RequestMapping(value = "/updateNewPassword", method = RequestMethod.POST)
	public String changeToNewPassword(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		ModelAndView model = null;
		HttpSession session = request.getSession();
		try {

			RestTemplate rest = new RestTemplate();
			int frId = Integer.parseInt(request.getParameter("frId"));
			String newPass = request.getParameter("newPass");
			//System.out.println("Passs--------------------------" + userId + " " + newPass);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("newPass", newPass);

			Info inf = rest.postForObject(Constant.URL + "updateToNewOPSPassword", map, Info.class);

			if (inf.isError()) {
				model = new ModelAndView("login");
				session.setAttribute("changePasswordFail", "Password Not Change");
				info.setError(true);
				info.setMessage("User Not Found");
				System.err.println(info);
			} else {
				model = new ModelAndView("login");			
				session.setAttribute("changePassword", "Password Change Sucessfully");
				info.setError(false);
				info.setMessage("User Found");
				System.err.println(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/";

	}
	
	@RequestMapping(value = "/reGenOPSOtp", method = RequestMethod.POST)
	public ModelAndView reGenOtp1(HttpServletRequest request, HttpServletResponse response) {
		String c = null;
		System.err.println("Hiii  checkValue  ");
		Info info = new Info();
		ModelAndView model = null;

		try {
			
			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			String frCode = request.getParameter("username");
			System.err.println("frCode for regeneration mob***  " + frCode);

			map.add("frCode", frCode);

			Franchisee franchisee = rest.postForObject(Constant.URL + "getFranchiseeByFrCode", map, Franchisee.class);
			System.err.println("Info Response  " + franchisee.toString());

			if (franchisee.getFrId()==0) {
				model = new ModelAndView("forgotpassword");
				model.addObject("msg", "Invalid User Name");

			} else {
				model = new ModelAndView("verifyOTP");
				model.addObject("frCode", frCode);
				model.addObject("msg", "OTP Resent Please check");
				start = Instant.now();
			}

		} catch (Exception e) {
			System.err.println("Exce in reGenOPSOtp  " + e.getMessage());
			e.printStackTrace();
		}

		return model;

	}

}
