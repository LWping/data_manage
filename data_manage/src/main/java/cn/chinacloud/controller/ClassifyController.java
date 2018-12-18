package cn.chinacloud.controller;

import cn.chinacloud.model.ResourceClassify;
import cn.chinacloud.service.ClassifyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2018/12/17.
 */

@Controller
public class ClassifyController {
    private Logger logger = Logger.getLogger(CatalogController.class);

    @Autowired
    private ClassifyService classifyService;

    @RequestMapping("/getClassifyInfo")
    @ResponseBody
    public List<ResourceClassify> getClassifyInfo(){

        List<ResourceClassify> classifyList = classifyService.getClassifyInfo();
        if(classifyList!=null){
            ResourceClassify classify =  classifyList.get(0);
            System.out.println("resouce.getName():"+classify.getName());
            logger.info("resouce.getName():"+classify.getName());
        }
        return classifyList;
    }

}
