package com.zerol.crm.controller;

/**
 * Created by gaoqiang on 2/2/2015.
 */

import com.zerol.crm.common.tag.PageTag;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.Map;

public class BaseController {

    //初始化分页相关信息
    protected void initPage(Map<String,Object> map, Integer pageNum, Integer pageSize, Integer totalCount){
        if(null==pageSize || pageSize.equals("")){
            pageSize = 10;
        }
        if(pageSize>50){
            pageSize = 50;
        }
        Integer totalPage = (totalCount+pageSize-1)/pageSize;
        if(null==pageNum){
            pageNum = 1;
        }else if(pageNum>totalPage){
            pageNum = totalPage;
        }
        map.put("startIndex", PageTag.getStartIndex(pageNum, pageSize));
        map.put("pageNum", pageNum);
        map.put("totalPage", totalPage);
        map.put("pageSize", pageSize);
        map.put("totalCount", totalCount);

    }

    //将相关数据放入model, 前后台交互
    protected void initPaginationPage(ModelAndView model, Map<String,Object> map){
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry m = (Map.Entry)it.next();
            model.addObject(m.getKey().toString(), m.getValue());
        }
    }

}