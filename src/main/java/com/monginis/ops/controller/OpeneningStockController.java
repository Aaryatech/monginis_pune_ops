package com.monginis.ops.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.CategoryListResponse;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.MCategoryList;
import com.monginis.ops.model.PostFrItemStockDetail;;

@Controller
@Scope("session")
public class OpeneningStockController {

	List<PostFrItemStockDetail> detailList = new ArrayList<PostFrItemStockDetail>();

	@RequestMapping(value = "/showFrOpeningStock")
	public ModelAndView showFrOpeningStock(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("stock/fropeningstock");

		RestTemplate restTemplate = new RestTemplate();

		CategoryListResponse itemsWithCategoryResponseList = restTemplate.getForObject(Constant.URL + "showAllCategory",
				CategoryListResponse.class);

		List<MCategoryList> itemsWithCategoriesList = itemsWithCategoryResponseList.getmCategoryList();

		for (int i = 0; i < itemsWithCategoriesList.size(); i++) {

			if (itemsWithCategoriesList.get(i).getCatId() == 5) {

				itemsWithCategoriesList.remove(i);

			}

		}

		for (int i = 0; i < itemsWithCategoriesList.size(); i++) {

			if (itemsWithCategoriesList.get(i).getCatId() == 6) {

				itemsWithCategoriesList.remove(i);

			}

		}

		model.addObject("catList", itemsWithCategoriesList);
		// ---------------------------------4-jan-2019------------------------------------

		return model;
	}

	@RequestMapping(value = "/getItemListById", method = RequestMethod.GET)
	public @ResponseBody List<PostFrItemStockDetail> getItems(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		int catId = Integer.parseInt(request.getParameter("menu_id")); // catId from jsp

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> menuMap = new LinkedMultiValueMap<String, Object>();

		menuMap.add("frId", frDetails.getFrId());
		menuMap.add("catId", catId);

		ParameterizedTypeReference<List<PostFrItemStockDetail>> typeRef = new ParameterizedTypeReference<List<PostFrItemStockDetail>>() {
		};
		ResponseEntity<List<PostFrItemStockDetail>> responseEntity = restTemplate
				.exchange(Constant.URL + "getCurrentOpStock", HttpMethod.POST, new HttpEntity<>(menuMap), typeRef);
		detailList = responseEntity.getBody();

		System.out.println("Item List " + detailList.toString());

		return detailList;
	}

	@RequestMapping(value = "/saveFrOpeningStockProcess", method = RequestMethod.POST)
	public String saveOpeningStock(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("stock/fropeningstock");

		for (int i = 0; i < detailList.size(); i++) {

			String stockQty = request.getParameter("stockQty" + detailList.get(i).getItemId());
			System.out.println("new qty " + stockQty);

			detailList.get(i).setRegOpeningStock(Integer.parseInt(stockQty));

		}

		RestTemplate restTemplate = new RestTemplate();

		List<PostFrItemStockDetail> info = restTemplate.postForObject(Constant.URL + "postFrOpStockDetailList",
				detailList, List.class);

		return "redirect:/showFrOpeningStock";
	}

}
