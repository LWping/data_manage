package cn.chinacloud.service;

import cn.chinacloud.mapper.ResourceClassifyMapper;
import cn.chinacloud.model.ResourceClassify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/12/17.
 */
@Service
public class ClassifyService {

    @Autowired
    private ResourceClassifyMapper resourceClassifyMapper;

    public List<ResourceClassify> getClassifyInfo(){
        Integer maxClassifyLevel = resourceClassifyMapper.maxClassifyLevel();
        List<ResourceClassify> firstLevelclassifyList=resourceClassifyMapper.findClassifyInfoList(0,1);
        if(maxClassifyLevel>1 && firstLevelclassifyList !=null && firstLevelclassifyList.size()!=0){
            for(ResourceClassify firstLevelclassify:firstLevelclassifyList){
                List<ResourceClassify> secondLevelClassifyList = resourceClassifyMapper.findClassifyInfoList(firstLevelclassify.getId(),2);
                if(maxClassifyLevel>2 && secondLevelClassifyList!=null && secondLevelClassifyList.size()!=0){
                    for(ResourceClassify secondLevelClassify :  secondLevelClassifyList){
                        List<ResourceClassify> thirdLevelClassifyList = resourceClassifyMapper.findClassifyInfoList(secondLevelClassify.getId(),3);
                        if(thirdLevelClassifyList!=null){
                            secondLevelClassify.setChildClassify(thirdLevelClassifyList);
                        }
                    }
                    firstLevelclassify.setChildClassify(secondLevelClassifyList);
                }
            }
        }
        return firstLevelclassifyList;
    }

}
