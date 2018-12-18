package cn.chinacloud.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */
public class ResourceClassify {

    private Integer id;
    private String name;
    private Integer typeLevel;
    private List<ResourceClassify> childClassify = new ArrayList<ResourceClassify>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(Integer typeLevel) {
        this.typeLevel = typeLevel;
    }

    public List<ResourceClassify> getChildClassify() {
        return childClassify;
    }

    public void setChildClassify(List<ResourceClassify> childClassify) {
        this.childClassify = childClassify;
    }
}
