package com.lichao.chaovr.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by ChaoLi on 2018/5/10 0010 - 10:20
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class PanoramaImageModel implements MultiItemEntity {

    public int type;
    public String title;
    public String desc;
    public String assetName;
    public int resourceName;

    public PanoramaImageModel(int type, String title, String desc, String assetName, int resourceName) {
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.assetName = assetName;
        this.resourceName = resourceName;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
