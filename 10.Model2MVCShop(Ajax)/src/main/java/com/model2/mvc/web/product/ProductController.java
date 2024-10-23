package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	// 이 클래스가 Spring MVC 의 컨트롤러임을 나타낸다. 사용자 요청을 처리하고 응답을 반환함
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
	//@@Autowired : Spring 이 자동으로 productService 객체를 주입
	//사용자 과련 비즈니스 로직을 처리하는 서비스 객체
	
	public ProductController(){
		System.out.println(this.getClass());
	}
	//userController 의 인스턴스가 생성될 때 클래스 이름을 출력한다.
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	//Spring 의 프로퍼티 파일에서 pageUnut 값을 주입받는다. 기본값은 주석처리
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	//page값을 주입받는다.
	
	/*
	 * @RequestMapping("/addProductView.do") public String addProductView() throws
	 * Exception { //@RequestMapping("/addUserView.do") : http 요청 url이
	 * /addUserView.do //일때 이 메서드를 호출 System.out.println("/addProductView.do");
	 * 
	 * return "redirect:/product/addProductView.jsp"; }
	 */
	@RequestMapping(value="addProductView", method=RequestMethod.GET)
	public String addProductView() throws Exception {

		System.out.println("/product/addProduct : GET");
		//Business Logic
	
		
		return "redirect:/product/addProductView.jsp";
	}
	//===================================================
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/product/addProduct : POST");
		//Business Logic
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	//=========================================
	
	//@RequestMapping("/getProduct.do") @RequestParam을 @@ModelAttribute 로 고쳐야하나?
	@RequestMapping(value="getProduct" , method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/product/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	
	@RequestMapping(value="updateProduct", method=RequestMethod.GET )
	public String updateProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/product/updateProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	//=======================================================================
	@RequestMapping( value="updateProduct", method=RequestMethod.POST )
	public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		
		String sessionId=((Product)session.getAttribute("product")).getProdName();
		if(sessionId.equals(product.getProdName())){
			session.setAttribute("product", product);
		}
		
		return "redirect:/getProduct?prodName="+product.getProdName();
	}
	

	@RequestMapping(value="listProduct")
	//public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
	public String listProduct( @RequestParam("menu") String menu, @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("menu", menu);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}