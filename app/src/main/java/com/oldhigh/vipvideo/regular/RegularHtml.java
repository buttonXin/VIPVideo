package com.oldhigh.vipvideo.regular;

import com.oldhigh.vipvideo.bean.EachVideoBean;
import com.oldhigh.vipvideo.bean.VIPVideoBean;
import com.oldhigh.vipvideo.constant.Constant;
import com.oldhigh.vipvideo.util.L;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularHtml {

    private static Pattern patternSearch = Pattern.compile("<li><span class=.+?</span></li>", Pattern.DOTALL);
    private static Pattern patternUrl = Pattern.compile("/?m=vod-detail-id.+?html");
    private static Pattern patternName = Pattern.compile("target=\"_blank\">.+?</a></span>", Pattern.DOTALL);
    private static Pattern patternType = Pattern.compile("<span class=\"xing_vb5\">.+?</span>", Pattern.DOTALL);

    /**
     * 解析搜索的内容，并封装成对象
     *
     * @param headSearchHtml
     */
    public static List<VIPVideoBean> regularSearch(String headSearchHtml) {

        List<VIPVideoBean> titleBeanList = new ArrayList<>();
        VIPVideoBean videoBean;

        Matcher matcher = patternSearch.matcher(headSearchHtml);

        int count = 0;
        while (matcher.find()) {
            if (count == 0) {
                count++;
                continue;
            }
            count++;
            if (count > 25) {
                break;
            }

            videoBean = new VIPVideoBean();

            String group = matcher.group();
            L.e("匹配到 ", group);
            Matcher url = patternUrl.matcher(group);
            Matcher name = patternName.matcher(group);
            Matcher type = patternType.matcher(group);

            if (url.find()) {
                String groupUrl = url.group();
                videoBean.setAddress(Constant.BASE_URL + "?" + groupUrl);
                L.e("---------------------->二级匹配-url", groupUrl);
            }
            if (name.find()) {
                String groupName = name.group();
                groupName = groupName.substring(groupName.indexOf(">") + 1, groupName.indexOf("<"));
                videoBean.setName(groupName);
                L.e("---------------------->二级匹配-name", groupName);
            }
            if (type.find()) {
                String groupType = type.group();
                groupType = groupType.substring(groupType.indexOf(">") + 1, groupType.lastIndexOf("<"));
                videoBean.setType(groupType);
                L.e("---------------------->二级匹配-type", groupType);
            }

            L.e();

            titleBeanList.add(videoBean);
        }

        return titleBeanList;
    }

    private static Pattern patternEach = Pattern.compile("<h3>来源：131m3u8</h3>.+?播放来源结束-->", Pattern.DOTALL);
    private static Pattern patternEachUrl = Pattern.compile("<li><input type=\"checkbox.+?index.m3u8", Pattern.DOTALL);
    private static Pattern patternEachGrade = Pattern.compile("第.+?集", Pattern.DOTALL);
    private static Pattern patternEachLastUrl = Pattern.compile("https.+?m3u8", Pattern.DOTALL);


    public static List<EachVideoBean> regularEach(String eachHtml) {


        EachVideoBean eachVideoBean;
        List<EachVideoBean> beanList = new ArrayList<>();

        Matcher interceptContent = patternEach.matcher(eachHtml);
        if (interceptContent.find()) {
            eachHtml = interceptContent.group();

            Matcher matcher = patternEachUrl.matcher(eachHtml);

            Matcher gradeNameMatcher = patternEachGrade.matcher(eachHtml);


            while (matcher.find()) {
                eachVideoBean = new EachVideoBean();
                String groupUrl = matcher.group();
                Matcher matcherUrl = patternEachLastUrl.matcher(groupUrl);
                if (matcherUrl.find()) {
                    String address = matcherUrl.group();
                    eachVideoBean.setAddress(address);
                    L.e("----> url=", address);
                }

                if (gradeNameMatcher.find()) {
                    String groupName = gradeNameMatcher.group();
                    eachVideoBean.setGrade(groupName);
                    L.e("----> 集数", groupName);
                }

                L.e();
                beanList.add(eachVideoBean);
            }
        }

        return beanList;

    }


}
