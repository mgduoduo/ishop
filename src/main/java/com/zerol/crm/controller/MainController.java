package com.zerol.crm.controller;

import com.zerol.crm.common.SearchCriteria;
import com.zerol.crm.common.constant.CommonConstant;
import com.zerol.crm.common.util.DateUtil;
import com.zerol.crm.entry.OrderInfo;
import com.zerol.crm.entry.Product;
import com.zerol.crm.entry.TxnRel;
import com.zerol.crm.entry.common.MyCode;
import com.zerol.crm.service.web.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/iShop")
public class MainController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ProdService prodService;

    @Autowired
    OrderService orderService;

    @Autowired
    TxnService txnService;

    @Autowired
    UserService userService;

    @Autowired
    CodeService codeService;

    public void setProdService(ProdService prodService) {
        this.prodService = prodService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTxnService(TxnService txnService) {
        this.txnService = txnService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * @param request
     * @param response
     * @return 查询
     */

    @RequestMapping("/preSearch.do")
    public ModelAndView initSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        //1, initParam
        this.initParams(mv);// load the code param.
        mv.addObject("menuID", "menu_2"); // mandatory
        //2, put in model view : setAttribute
        mv.setViewName("search");
        return mv;
    }

    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute SearchCriteria searchCriteria,
                               @RequestParam(required = false) Integer pageNum,
                               @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();

        List orderInfoList = null;
        Map<String, Object> pageMap = new HashMap<String, Object>();
        // search all
        if (searchCriteria == null) {
            orderInfoList = orderService.getAllOrderList();
            Integer totalCount = orderInfoList == null ? 0 : orderInfoList.size();
            this.initPage(pageMap, pageNum, pageSize, totalCount);

            //search by criteria
        } else {
            Integer totalCount = orderService.getCountOfOrderListBySearchCriteria(searchCriteria);
            this.initPage(pageMap, pageNum, pageSize, totalCount);
            orderInfoList = orderService.getOrderListBySearchCriteria(searchCriteria, pageMap);
        }
        this.initPaginationPage(mv, pageMap);
        mv.addObject("orderInfoList", orderInfoList);
        mv.addObject("isSearch", CommonConstant.COMMON_BOOLEAN_Y);// search indicator : Yes
        this.initParams(mv);// load the code param.
        mv.setViewName("orderList");
        return mv;
    }

    @RequestMapping("/login.do")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("errInd", CommonConstant.COMMON_BOOLEAN_Y);
        mv.setViewName("/sys/login");
        return mv;
    }

    /**
     * @param request
     * @param response
     * @return 订单信息, 管理
     */

    @RequestMapping("/orderManage.do")
    public ModelAndView orderManage(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        mv.clear();

        // search all
        Map<String, Object> pageMap = new HashMap<String, Object>();

        //Step 1, calculate total count
        List allResult = orderService.getAllOrderList(pageMap);
        Integer totalCount = allResult == null ? 0 : allResult.size();

        //Step 2, initial pagination parameters total count
        this.initPage(pageMap, pageNum, pageSize, totalCount);

        //Step 3, search by pagination
        List orderInfoList = orderService.getAllOrderList(pageMap);
        this.initPaginationPage(mv, pageMap);

        mv.addObject("isSearch", CommonConstant.COMMON_BOOLEAN_N);//search indicator : No a search operation
        mv.addObject("menuID", "menu_1"); // mandatory
        mv.addObject("orderInfoList", orderInfoList);
        this.initParams(mv);// load the code param.
        mv.setViewName("orderManage");
        return mv;
    }

    @RequestMapping("/orderList.do")
    public ModelAndView orderList(@ModelAttribute SearchCriteria searchCriteria,
                                  @RequestParam(required = false) String pageNum,
                                  @RequestParam(required = false) String pageSize) {
        ModelAndView mv = new ModelAndView();

        // use pagination instead.
        List orderInfoList = null;
        Map<String, Object> pageMap = new HashMap<String, Object>();

        if (searchCriteria == null) {// search all
            orderInfoList = orderService.getAllOrderList();
            Integer totalCount = orderInfoList == null ? 0 : orderInfoList.size();
            this.initPage(pageMap, Integer.valueOf(pageNum), Integer.valueOf(pageSize), totalCount);


        } else { //search by criteria

            Integer totalCount = orderService.getCountOfOrderListBySearchCriteria(searchCriteria);
            this.initPage(pageMap, Integer.valueOf(pageNum), Integer.valueOf(pageSize), totalCount);
            orderInfoList = orderService.getOrderListBySearchCriteria(searchCriteria, pageMap);
        }
        this.initPaginationPage(mv, pageMap);
        this.initParams(mv);// load the code param.
        mv.addObject("orderInfoList", orderInfoList);
        mv.setViewName("orderList");
        return mv;
    }

    @RequestMapping(value = "/orderInfo.do", method = RequestMethod.POST)
    public ModelAndView orderInfo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String expressNO = (String) request.getParameter("expressNO");
        String operateType = (String) request.getParameter("operateType");
        OrderInfo orderInfo = orderService.getOrderInfoByExpressNO(expressNO);
        if (orderInfo == null) {
            return null;
        }
        mv.addObject("orderInfo", orderInfo);
        this.initParams(mv);// load the code param.
        if ("1".equals(operateType)) { // view
            mv.setViewName("orderInfo");
        } else if ("2".equals(operateType)) {//edit
            mv.setViewName("orderInfoForUpdate");
        }
        return mv;
    }

    private void initParams(ModelAndView mv) {

        List<Product> productCodeList = prodService.getAllProductList();
        List<MyCode> contactTypeCodeList = codeService.findCodeListByCodeTypID(CommonConstant.CODE_CONTACT_TP);
        List<MyCode> expressCompanyCodeList = codeService.findCodeListByCodeTypID(CommonConstant.CODE_EXPRESS_COMPANY);
        List<MyCode> expressStatusCodeList = codeService.findCodeListByCodeTypID(CommonConstant.CODE_EXPRESS_STATUS);
        List<MyCode> payTypeCodeList = codeService.findCodeListByCodeTypID(CommonConstant.CODE_PAY_TP);
        List<MyCode> commonYesNOList = codeService.findCodeListByCodeTypID(CommonConstant.CODE_CMN_YES_NO);

        mv.addObject("productCodeList", productCodeList);
        mv.addObject("expressStatusCodeList", expressStatusCodeList);
        mv.addObject("expressCompanyCodeList", expressCompanyCodeList);
        mv.addObject("contactTypeCodeList", contactTypeCodeList);
        mv.addObject("payTypeCodeList", payTypeCodeList);
        mv.addObject("commonYesNOList", commonYesNOList);
    }

    @RequestMapping(value = "/updateOrderInfo.do", method = RequestMethod.POST)
    public ModelAndView updateOrderInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ModelAndView mv = new ModelAndView();
        long tradeID = Long.valueOf(request.getParameter("tradeID"));
        OrderInfo orderInfo = orderService.getOrderInfoByID(tradeID);
//        OrderInfo orderInfo = orderService.getOrderInfoByExpressNO(expressNO);
        if (orderInfo == null) {
            orderInfo = new OrderInfo();
        }

        orderInfo.setPayType(request.getParameter("payType"));
        orderInfo.setShopingDate(DateUtil.strToSqlDate(DateUtil.DEFAULT_DATE_FORMAT, request.getParameter("shopingDate")));
        orderInfo.setTotalPrice(Integer.valueOf(request.getParameter("totalPrice")));
        orderInfo.setCustomerQQ(request.getParameter("customerQQ"));
        orderInfo.setCustomerWW(request.getParameter("customerWW"));
        orderInfo.setCustomerName(request.getParameter("customerName"));
        orderInfo.setReceiveAdd(request.getParameter("receiveAdd"));
        orderInfo.setCustomerPhoneNO(request.getParameter("customerPhoneNO"));
        orderInfo.setExpressCompany(request.getParameter("expressCompany"));
        orderInfo.setExpressStatus(request.getParameter("expressStatus"));
        orderInfo.setRemark(request.getParameter("remark"));

        String[] paramArr = request.getParameterValues("refProdNO");
        if (paramArr != null && paramArr.length > 0) {
            int paramLength = paramArr.length;
            List<TxnRel> txnFromUIList = new ArrayList<TxnRel>();
            for (int i = 0; i < paramLength; i++) {
                TxnRel txn = new TxnRel();
                txn.setRefExpressNO(orderInfo.getExpressNO());
                txn.setRefProdNO(request.getParameterValues("refProdNO")[i]);
                txn.setPurchaseCount(Integer.valueOf(request.getParameterValues("purchaseCount")[i]));
                txn.setDeleteIndicator(request.getParameterValues("deleteIndicator")[i]);
                txnFromUIList.add(txn);
            }

            orderInfo.setTxnList(txnFromUIList);
        } else {
            orderInfo.setTxnList(null);
        }

        orderService.update(orderInfo);
        return null;
    }

    /**
     * @param request
     * @param response
     * @return 到付信息
     */
    @RequestMapping("/deliveryList.do")
    public ModelAndView deliveryList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        List<Product> prodList = prodService.getAllProductList();
        mv.addObject("prodList", prodList);
        mv.addObject("menuID", "menu_3"); // mandatory
        mv.setViewName("deliveryList");
        return mv;
    }

    /**
     * @param request
     * @param response
     * @return 存货信息管理
     */
    @RequestMapping("/backupProductManage.do")
    public ModelAndView backupList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        List<Product> prodList = prodService.getAllProductList();
        mv.addObject("prodList", prodList);
        mv.addObject("menuID", "menu_4"); // mandatory
        mv.setViewName("backupProduct");
        return mv;
    }

    /**
     * @param request
     * @param response
     * @return 系统管理
     */
    @RequestMapping("/sysManage.do")
    public ModelAndView sysMgt(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        List<Product> prodList = prodService.getAllProductList();
        mv.addObject("prodList", prodList);
        mv.addObject("menuID", "menu_5"); // mandatory
        mv.setViewName("/sys/sysManage");
        return mv;
    }

    /**
     * @param request
     * @param response
     * @return 上传, 导入
     */
    @RequestMapping("/uploadAttachment.do")
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        //ajax
        return null;
    }

    /**
     * @param request
     * @param response
     * @return 导出, 下载
     */
    @RequestMapping("/download.do")
    public ModelAndView download(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        //ajax
        return null;
    }
}
