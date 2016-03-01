package com.zerol.crm.repository;

import com.zerol.crm.common.constant.CommonConstant;
import com.zerol.crm.common.util.DateUtil;
import com.zerol.crm.common.util.MD5Util;
import com.zerol.crm.entry.OrderInfo;
import com.zerol.crm.entry.Product;
import com.zerol.crm.entry.TxnRel;
import com.zerol.crm.entry.common.MyCode;
import com.zerol.crm.entry.common.User;
import com.zerol.crm.service.web.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.util.List;
import java.util.Random;

public class TestCustomerRepository {

	static ApplicationContext context;
	private ProdService prodService ;
	private TxnService txnService ;
	private OrderService orderService;
	private UserService userService;
	private CodeService codeService;
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("spring-test.xml");
		prodService = context.getBean(ProdService.class);
		txnService = context.getBean(TxnService.class);
		orderService = context.getBean(OrderService.class);
		userService = context.getBean(UserService.class);
		codeService = context.getBean(CodeService.class);
	}


	public void testCode(){
		MyCode code = new MyCode();
		code.setCodeID("code_01");
		code.setCodeTypeID("codeType_01");
		code.setCodeDesc("this is the code 01");
		codeService.saveCode(code);

		MyCode myCode = codeService.findCodeByCodeTypIDAndCodeID("codeType_01", "code_01");
		if(myCode != null){
			System.out.println("~~"+myCode.getCodeDesc());
		}
	}


	public void testUser(){
		User user = new User();
		user.setUsername("abc");
		user.setPassword(MD5Util.parseStrToMd5L32(MD5Util.parseStrToMd5L32("abc")));
		userService.saveUser(user);
		System.out.println("pp="+user.getPassword());
	}

	@Test
	public void testQuery(){

//		List<Product> productList = prodService.getAllProductList();
//		System.out.println("productList~~"+(productList!=null?productList.size():0));
//
//		List<TxnRel> txnRelList = txnService.getAllTxnListByExpressNO("Express01");
//		System.out.println("txnRelList~~"+(txnRelList==null?0:txnRelList.size()));

		List<OrderInfo> orderInfoList = orderService.getAllOrderList();
		System.out.println("orderInfoList~~"+(orderInfoList!=null?orderInfoList.size():0));
		if(orderInfoList!=null)
		System.out.println("orderInfoList.getTxnList~~"+(orderInfoList.get(0).getTxnList()!=null?orderInfoList.get(0).getTxnList().size():0));

		if(orderInfoList!=null && orderInfoList.get(0).getTxnList()!=null){
			System.out.println("orderInfoList.getTxnList.getProduct~~"+(orderInfoList.get(0).getTxnList().get(0).getProduct()!=null?orderInfoList.get(0).getTxnList().get(0).getProduct().getProdName():0));
		}

	}


	public void testInsertRecord(){
		for(int i=0; i< 50; i++){

			String expressNO = "Express0"+i;
			String productNO = "Product0"+i;
			// 1 insert test record
			Product product = new Product();
			product.setProdNo(productNO);
			product.setProdName("Prod name");

			Product product1 = prodService.getProductByProdNO(productNO);
			if(product1 == null){
				product1 = prodService.save(product);
			}

			System.out.println(product1==null?"==== 1 ==== "+product1.getProdID()+","+product1.getProdName()+","+product1.getProdNo():"no product!");


			TxnRel txnRel = new TxnRel();
			txnRel.setRefExpressNO(expressNO);
			List<TxnRel> txnRelList = txnService.getTxnListByExpressNO(expressNO);
			if(txnRelList==null || txnRelList.isEmpty()){
				txnRel.setRefProdNO(productNO);
				txnRel.setPurchaseCount(new Random().nextInt(10));
				txnRel.setDeleteIndicator(CommonConstant.COMMON_BOOLEAN_N);
				txnRel.setProduct(product);

				txnService.save(txnRel);
			}


			txnRelList = txnService.getTxnListByExpressNO(expressNO);
			if(txnRelList!=null && !txnRelList.isEmpty()){
				TxnRel txnRel1 = txnRelList.get(0);
				//System.out.println(txnRel1==null?"==== 2 ==== "+txnRel1.getTxnID()+","+txnRel1.getRefExpressNO()+","+txnRel1.getRefProdNO():"no txnRel1!");
			}


			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setExpressNO(expressNO);
			orderInfo.setTotalPrice(new Random().nextInt(1000));
			orderInfo.setPayType(i % 3 == 1 ? "C" : (i % 3 == 2 ? "A" : "B"));
			orderInfo.setExpressCompany("kk");
			orderInfo.setReceiveAdd("address receive");
			orderInfo.setCustomerName("customer name");
			orderInfo.setCustomerPhoneNO("phone no");
			orderInfo.setRefundIndicator(CommonConstant.COMMON_BOOLEAN_N);
			orderInfo.setExpressStatus(i%3==1?"C":(i%3==2?"A":"B"));
			orderInfo.setCustomerQQ("QQ"+new Random().nextInt(5));
			orderInfo.setCustomerWW("WW"+new Random().nextInt(5));
			try {
				orderInfo.setShopingDate(DateUtil.strToSqlDate("yyyyMMdd", "20121224"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			orderInfo.setTxnList(txnRelList);

			OrderInfo orderInfo2 = orderService.getOrderInfoByExpressNO(expressNO);
			if(orderInfo2==null){

				orderInfo2 = orderService.save(orderInfo);
			}

			//System.out.println(orderInfo2==null?"==== 0 ==== "+orderInfo2.getExpressNO()+","+orderInfo2.getCustomerPhoneNO()+","+orderInfo2.getCustomerName():"no orderInfo2!");


			OrderInfo orderInfo1 = orderService.getOrderInfoByExpressNO(expressNO);
			//System.out.println(orderInfo1==null?"==== 3 ==== "+orderInfo1.getTradeID()+","+orderInfo1.getExpressNO()+","+orderInfo1.getCustomerName():"no orderInfo1!");
		}

	}

}
