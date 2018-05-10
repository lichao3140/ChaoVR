package com.lichao.chaovr.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lichao.chaovr.R;
import com.lichao.chaovr.model.PanoramaImageModel;

import java.util.List;

/**
 * Created by ChaoLi on 2018/5/10 0010 - 10:31
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class PanoramaImageAdapter extends BaseMultiItemQuickAdapter<PanoramaImageModel, BaseViewHolder> {

    private Context context;

    public PanoramaImageAdapter(Context context, List<PanoramaImageModel> data) {
        super(data);
        this.context = context;
        addItemType(0, R.layout.item_panorana_image);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void convert(BaseViewHolder helper, PanoramaImageModel item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_desc, item.desc);
        helper.setImageResource(R.id.imageView, item.resourceName);
    }
}
