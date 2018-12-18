package cn.chinacloud.mapper;

import cn.chinacloud.model.ResourceClassify;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */
@Transactional
public interface ResourceClassifyMapper {

    @Select("select id AS id,name AS name,parent_id AS parentId,type_level AS typeLevel from resource_classify where parent_id = #{parentId} and type_level =#{typeLevel}")
    public List<ResourceClassify> findClassifyInfoList(@Param("parentId") Integer parentId, @Param("typeLevel") Integer typeLevel);

    @Select("select max(type_level) from resource_classify")
    public Integer maxClassifyLevel();
}
