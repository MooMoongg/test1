package com.model2.mvc.web.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> 회원관리 RestController
@RestController //RESTful API 요청을 처리함
@RequestMapping("/product/*") //    /product/* 경로의 요청을 이클래스에서 처리함을 정의
public class ProductRestController { //RESTful API 요청을 처리하는 컨트롤러
	//프로덕트 관련 요청을 처리하는 REST 컨트롤러
	///Field
	@Autowired //spring 이 자동으로 주입하도록설정
	@Qualifier("productServiceImpl") //특정구현체를 주입하도록 설정, productService는 product 관련 비즈니스 로직을 처리하는 서비스
	private ProductService productService;
	//setter Method 구현 않음
	 //product 서비스를 자동으로 주입받는다.
	public ProductRestController(){
		System.out.println(this.getClass());//객체가 생성될때 클래스 이름을 출력한다.
	}
	
	//@RequestMapping : 요청 경로 및 Http 메서드를 설정
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET )
	public Product getProduct( @PathVariable int prodNo ) throws Exception{
		//제품번호로 제품을 가져오는 메서드
		//product NO로 product를 가져오는 GET 요청을 처리한다. 
		System.out.println("/product/json/getProduct : GET");
		//호출된 경로를 콘솔에 출력한다.
		//Business Logic
		return productService.getProduct(prodNo);
	}


	}