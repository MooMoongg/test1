package com.model2.mvc.web.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> ȸ������ RestController
@RestController //RESTful API ��û�� ó����
@RequestMapping("/product/*") //    /product/* ����� ��û�� ��Ŭ�������� ó������ ����
public class ProductRestController { //RESTful API ��û�� ó���ϴ� ��Ʈ�ѷ�
	//���δ�Ʈ ���� ��û�� ó���ϴ� REST ��Ʈ�ѷ�
	///Field
	@Autowired //spring �� �ڵ����� �����ϵ��ϼ���
	@Qualifier("productServiceImpl") //Ư������ü�� �����ϵ��� ����, productService�� product ���� ����Ͻ� ������ ó���ϴ� ����
	private ProductService productService;
	//setter Method ���� ����
	 //product ���񽺸� �ڵ����� ���Թ޴´�.
	public ProductRestController(){
		System.out.println(this.getClass());//��ü�� �����ɶ� Ŭ���� �̸��� ����Ѵ�.
	}
	
	//@RequestMapping : ��û ��� �� Http �޼��带 ����
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET )
	public Product getProduct( @PathVariable int prodNo ) throws Exception{
		//��ǰ��ȣ�� ��ǰ�� �������� �޼���
		//product NO�� product�� �������� GET ��û�� ó���Ѵ�. 
		System.out.println("/product/json/getProduct : GET");
		//ȣ��� ��θ� �ֿܼ� ����Ѵ�.
		//Business Logic
		return productService.getProduct(prodNo);
	}


	}