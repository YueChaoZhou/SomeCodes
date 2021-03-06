package com.bb.module_novelmanager.entity;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;
import me.ghui.fruit.reflect.TypeToken;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 21:46
 * 小说详情页面bean、收藏小说bean
 */
@Pick("div#wrapper")
@Entity
public class NovelDetails {
    @Id
    public String novelId;
    @Pick(value = "div#fmimg > img", attr = Attrs.SRC)
    public String coverUrl;
    @Pick("div#info > h1")
    public String name;
    @Pick("div#info > p")
    @Convert(converter = InfoConverter.class, columnType = String.class)
    private List<String> infos;
    @Transient
    @Pick(value = "div#intro > p", attr = Attrs.OWN_TEXT)
    List<String> introductions;
    public String introduction;
    @Pick("div#list > dl > dd")
    @Transient
    public List<Chapter> chapterList;
    public boolean hasNewChapter;

    @Generated(hash = 95855330)
    public NovelDetails() {
    }

    @Generated(hash = 1644369618)
    public NovelDetails(String novelId, String coverUrl, String name, List<String> infos,
            String introduction, boolean hasNewChapter) {
        this.novelId = novelId;
        this.coverUrl = coverUrl;
        this.name = name;
        this.infos = infos;
        this.introduction = introduction;
        this.hasNewChapter = hasNewChapter;
    }

    public String getAuthor() {
        return infos != null ? infos.get(0) : null;
    }

    public String getType() {
        return infos != null ? infos.get(1) : null;
    }

    public String getLastUpdateTime() {
        return infos != null ? infos.get(2) : null;
    }

    public String getNewestChapter() {
        return infos != null ? infos.get(3) : null;
    }

    public String getNovelId() {
        return this.novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInfos() {
        return this.infos;
    }

    public void setInfos(List<String> infos) {
        this.infos = infos;
    }

    public String getIntroduction() {
        if (TextUtils.isEmpty(introduction) && introductions != null && !introductions.isEmpty()) {
            introduction = introductions.get(introductions.size() - 1);
        }
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "NovelDetails{" +
                "novelId='" + novelId + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", name='" + name + '\'' +
                ", infos=" + infos +
                ", introduction='" + introduction + '\'' +
                ", chapterList=" + chapterList +
                '}';
    }

    public boolean getHasNewChapter() {
        return this.hasNewChapter;
    }

    public void setHasNewChapter(boolean hasNewChapter) {
        this.hasNewChapter = hasNewChapter;
    }

    public static class Chapter {
        @Pick("a")
        public String name;
        @Pick(value = "a", attr = Attrs.HREF)
        public String chapterUrl;
    }

    public static class InfoConverter implements PropertyConverter<List<String>, String> {
        static String TAG = "InfoConverter";

        @Override
        public List<String> convertToEntityProperty(String databaseValue) {
            Gson gson = new Gson();
            List<String> infos = gson.fromJson(databaseValue, new TypeToken<List<String>>() {
            }.getType());
            return infos;
        }

        @Override
        public String convertToDatabaseValue(List<String> entityProperty) {
            String json = new Gson().toJson(entityProperty);
            return json;
        }
    }
}
