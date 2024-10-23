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


//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	// �� Ŭ������ Spring MVC �� ��Ʈ�ѷ����� ��Ÿ����. ����� ��û�� ó���ϰ� ������ ��ȯ��
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
	//@@Autowired : Spring �� �ڵ����� productService ��ü�� ����
	//����� ���� ����Ͻ� ������ ó���ϴ� ���� ��ü
	
	public ProductController(){
		System.out.println(this.getClass());
	}
	//userController �� �ν��Ͻ��� ������ �� Ŭ���� �̸��� ����Ѵ�.
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	//Spring �� ������Ƽ ���Ͽ��� pageUnut ���� ���Թ޴´�. �⺻���� �ּ�ó��
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	//page���� ���Թ޴´�.
	
	/*
	 * @RequestMapping("/addProductView.do") public String addProductView() throws
	 * Exception { //@RequestMapping("/addUserView.do") : http ��û url��
	 * /addUserView.do //�϶� �� �޼��带 ȣ�� System.out.println("/addProductView.do");
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
	
	//@RequestMapping("/getProduct.do") @RequestParam�� @@ModelAttribute �� ���ľ��ϳ�?
	@RequestMapping(value="getProduct" , method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/product/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	
	@RequestMapping(value="updateProduct", method=RequestMethod.GET )
	public String updateProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/product/updateProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
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
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("menu", menu);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}