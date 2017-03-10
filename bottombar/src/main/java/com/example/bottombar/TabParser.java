package com.example.bottombar;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.annotation.XmlRes;
import android.support.v4.content.ContextCompat;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 2017/1/29.
 */

public class TabParser  {
    private final XmlResourceParser parser;
    private final Context context;
    private ArrayList<BottomBarTab> tabs;
    private BottomBarTab workingTab;//当前正在添加的tab

    TabParser(Context context,@XmlRes int tabsXmlResId) {
        this.context = context;
        parser = context.getResources().getXml(tabsXmlResId);
        tabs = new ArrayList<>();
        parse();
    }

    private void parse() {
        try {
            parser.next();
            int eventType = parser.getEventType();
            while(eventType!= XmlResourceParser.END_DOCUMENT){
                if(eventType==XmlResourceParser.START_TAG){
                    parseNewTab(parser);
                }else if(eventType==XmlResourceParser.END_TAG){
                    if (parser.getName().equals("tab")) { //？什么意思
                        if (workingTab != null) {
                            tabs.add(workingTab);
                            workingTab = null;
                        }
                    }

                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseNewTab(XmlResourceParser parser) {
        if(workingTab==null){
            workingTab=new BottomBarTab(context);
        }
        for(int i=0;i<parser.getAttributeCount();i++){
            String attrName = parser.getAttributeName(i);
            switch (attrName){
                case "id":
                    workingTab.setId(parser.getIdAttributeResourceValue(i));
                    break;
                case "icon":
                    workingTab.setIconResId(parser.getAttributeResourceValue(i, 0));
                    break;
                case "title":
                    workingTab.setTabString(getTitleValue(i, parser));
                    break;

                case "inActiveColor":
                    Integer inActiveColor = getColorValue(i, parser);

                    if (inActiveColor != null) {
                        workingTab.setInActiveColor(inActiveColor);
                    }
                    break;
                case "activeColor":
                    Integer activeColor = getColorValue(i, parser);

                    if (activeColor != null) {
                        workingTab.setActiveColor(activeColor);
                    }
                    break;
                case "barColorWhenSelected":
                Integer barColorWhenSelected = getColorValue(i, parser);

                if (barColorWhenSelected != null) {
                    workingTab.setBarColorWhenSelected(barColorWhenSelected);
                }
                break;
            }
        }
    }

    private Integer getColorValue(int attrIndex, XmlResourceParser parser) {
        int colorResource = parser.getAttributeResourceValue(attrIndex, 0);

        if (colorResource != 0) {
            return ContextCompat.getColor(context, colorResource);
        }

        try {
            return Color.parseColor(parser.getAttributeValue(attrIndex));
        } catch (Exception ignored) {
            return null;
        }
    }

    private String getTitleValue(int attrIndex, XmlResourceParser parser) {
        int titleResource = parser.getAttributeResourceValue(attrIndex, 0);

        if (titleResource != 0) {
            return context.getString(titleResource);
        }

        return parser.getAttributeValue(attrIndex);
    }

    List<BottomBarTab> getTabs() {
        return tabs;
    }
}
