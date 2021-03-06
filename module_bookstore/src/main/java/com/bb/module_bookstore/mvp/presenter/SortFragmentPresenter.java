package com.bb.module_bookstore.mvp.presenter;

import android.util.Log;

import com.bb.module_bookstore.R;
import com.bb.module_bookstore.mvp.modle.SortNovelModel;
import com.bb.module_bookstore.mvp.view.SortFragment;
import com.bb.module_common.base.BasePresenter;
import com.bb.module_novelmanager.entity.NovelInfo;
import com.bb.module_novelmanager.entity.PageData;
import com.bb.module_bookstore.mvp.contract.SortFragmentContract;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_common.utils.ResUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/28
 * Time: 1:07
 */
public class SortFragmentPresenter extends BasePresenter<SortFragment> implements SortFragmentContract.IPresenter {
    String TAG = "SortFragmentPresenter";
    private int mCurrentType = NovelService.NovelType.TYPE_XUANHUAN;
    private final int mFirstPage = 0;
    private int mCurrentPage = mFirstPage;
    private final SortNovelModel mModel;

    public SortFragmentPresenter() {
        mModel = new SortNovelModel(this);
    }

    @Override
    public void process() {
        //获取分类，添加头部（分类选择）
        String[] types = ResUtils.getStringArray(R.array.sort_tabs);
        mView.initTypes(types);

        //在mView.initTypes(types)中添加时指定第一项为selected，然后走到tab点击监听回调方法进行加载，so这里不需要手动加载一次
//        getNovelsBySort(mCurrentType, mFirstPage);
    }

    @Override
    public void getNovelsBySort(int type, int page) {
        mModel.getNovelsBySort(type, page);
    }

    @Override
    public void loadNovelSuccess(PageData pageData, int type, int page, boolean cleanOldData) {
        Log.d(TAG, "loadNovelSuccess() called with: pageData.size = [" + pageData.getNovels().size() + "], type = [" + type + "], page = [" + page + "], cleanOldData = [" + cleanOldData + "]");
        mCurrentType = type;
        mCurrentPage = page;
        List<NovelInfo> novels = pageData.getNovels();
        boolean hasMore = novels != null && novels.size() >= 30;
        mView.addNovels(novels, hasMore, cleanOldData);
    }

    @Override
    public void getNewNovels(int position) {
        mModel.getNovelsBySort(position + 1, mFirstPage,true);
    }

    @Override
    public void loadMore() {
        getNovelsBySort(mCurrentType, (mCurrentPage + 1));
        Log.d(TAG, "loadMore mCurrentType: " + mCurrentType + ",page: " + (mCurrentPage + 1));
    }

    @Override
    public void onError(Throwable throwable) {
        mView.onError(throwable);
    }
}
