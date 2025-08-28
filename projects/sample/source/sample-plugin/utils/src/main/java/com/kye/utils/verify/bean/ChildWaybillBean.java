package com.kye.utils.verify.bean;

import android.text.TextUtils;

import com.kye.pda.utilcode.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Desc
 * Source
 *
 * @author boyce on 2020/12/23 10:48
 * Modify by  boyce on 2020/12/23 10:48
 * Version 1.0
 */
public class ChildWaybillBean {

    private String childWaybill;

    private String waybillNumber;
    private int childNumber;
    private int totalCount;
    private String regex = "[*-]";
    private Pattern pattern;
    private LabelType labelType = LabelType.NONE;

    public ChildWaybillBean(String childWaybill, Pattern pattern) {
        this.childWaybill = childWaybill;
        this.pattern = pattern;
    }

    public ChildWaybillBean invoke() {
        if (TextUtils.isEmpty(childWaybill)) {
            return this;
        }
        if (childWaybill.contains("*") || StringUtils.getSameCharCount("-", childWaybill) == 3) {
            labelType = LabelType.TAKE_GOODS;
        } else if (StringUtils.getSameCharCount("-", childWaybill) == 2) {
            labelType = LabelType.ALLOCATE_CARGO;
        } else {
            labelType = LabelType.NONE;
        }
        String[] split;
        if (pattern != null) {
            split = pattern.split(childWaybill);
        } else {
            split = childWaybill.split(regex);
        }
        if (split.length > 2) {
            waybillNumber = split[0];
            try {
                int i = Integer.parseInt(split[1]);
                int j = Integer.parseInt(split[2]);
                if (i > j) {
                    childNumber = j;
                    totalCount = i;
                } else {
                    childNumber = i;
                    totalCount = j;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            waybillNumber = childWaybill;
        }
        return this;
    }

    public String getChildWaybill() {
        return childWaybill;
    }

    public void setChildWaybill(String childWaybill) {
        this.childWaybill = childWaybill;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public int getChildNumber() {
        return childNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public LabelType getLabelType() {
        return labelType;
    }

    enum LabelType {
        /**
         * @ NONE,取货标签,配货标签
         */
        NONE, TAKE_GOODS, ALLOCATE_CARGO
    }
}
