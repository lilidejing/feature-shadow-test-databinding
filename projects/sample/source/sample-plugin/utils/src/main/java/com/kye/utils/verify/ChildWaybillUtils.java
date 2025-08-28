package com.kye.utils.verify;

import android.text.TextUtils;

import com.kye.utils.verify.bean.ChildWaybillBean;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Desc
 * Source
 *
 * @author boyce on 2020/12/23 11:06
 * Modify by  boyce on 2020/12/23 11:06
 * Version 1.0
 */
public class ChildWaybillUtils {
    private static String regex = "[*-]";
    public static Pattern pattern = Pattern.compile(regex);
    public final static String PICK_TAG = "取货标签";
    public final static String STOWAGE_TAG = "配货标签";

    /**
     * 判断子件在列表中是否存在
     *
     * @param childwaybill
     * @param list
     * @return
     */
    public static boolean containsChildWaybill(String childwaybill, List<String> list) {
        boolean result = false;
        if (TextUtils.isEmpty(childwaybill) || list == null || list.isEmpty()) {
            return false;
        }
        if (list.contains(childwaybill)) {
            return true;
        }
        ChildWaybillBean childWaybillNumber = new ChildWaybillBean(childwaybill, pattern).invoke();
        ChildWaybillBean childWaybillNo = new ChildWaybillBean("", pattern);
        for (String billNo : list) {
            childWaybillNo.setChildWaybill(billNo);
            childWaybillNo.invoke();
            if (childWaybillNumber.getWaybillNumber().equals(childWaybillNo.getWaybillNumber())
                    && childWaybillNumber.getChildNumber() == childWaybillNo.getChildNumber()
                    && childWaybillNumber.getTotalCount() == childWaybillNo.getTotalCount()
                    && childWaybillNumber.getLabelType() == childWaybillNo.getLabelType()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean containsChildWaybill(String childwaybill, ChildWaybillBean childWaybillNumber, ChildWaybillBean childWaybillNo, List<String> list) {
        boolean result = false;
        if (TextUtils.isEmpty(childwaybill) || list == null || list.isEmpty()) {
            return false;
        }
        if (list.contains(childwaybill)) {
            return true;
        }
        for (String billNo : list) {
            childWaybillNo.setChildWaybill(billNo);
            childWaybillNo.invoke();
            if (childWaybillNumber.getWaybillNumber().equals(childWaybillNo.getWaybillNumber())
                    && childWaybillNumber.getChildNumber() == childWaybillNo.getChildNumber()
                    && childWaybillNumber.getTotalCount() == childWaybillNo.getTotalCount()
                    && childWaybillNumber.getLabelType() == childWaybillNo.getLabelType()) {
                result = true;
                break;
            }
        }
        return result;
    }


    /**
     * 从列表中移除子件
     *
     * @param childwaybill
     * @param list
     */
    public static void removeChildWaybillInList(String childwaybill, List<String> list) {
        if (TextUtils.isEmpty(childwaybill) || list == null || list.isEmpty()) {
            return;
        }
        if (list.contains(childwaybill)) {
            list.remove(childwaybill);
        } else {
            ChildWaybillBean childWaybillNumber = new ChildWaybillBean(childwaybill, pattern).invoke();
            ListIterator<String> it = list.listIterator();
            while (it.hasNext()) {
                String billNo = it.next();
                ChildWaybillBean childWaybillNo = new ChildWaybillBean(billNo, pattern).invoke();
                if (childWaybillNumber.getWaybillNumber().equals(childWaybillNo.getWaybillNumber())
                        && childWaybillNumber.getChildNumber() == childWaybillNo.getChildNumber()
                        && childWaybillNumber.getTotalCount() == childWaybillNo.getTotalCount()
                        && childWaybillNumber.getLabelType() == childWaybillNo.getLabelType()) {
                    it.remove();
                }
            }
        }
    }

    public static boolean checkSameLabelType(String scanNumber, List<String> list) {
        if (TextUtils.isEmpty(scanNumber)) {
            return false;
        }
        if (list == null || list.isEmpty()) {
            return true;
        }
        ChildWaybillBean childWaybillNumber = new ChildWaybillBean(scanNumber, pattern).invoke();
        String billNo = list.get(0);
        ChildWaybillBean childWaybillNo = new ChildWaybillBean(billNo, pattern).invoke();
        if (childWaybillNumber.getLabelType() != childWaybillNo.getLabelType()) {
            return false;
        }
        return true;
    }

    /**
     * @param count
     * @param type
     */
    public static List<String> splitChildWaybill(String waybillNumber, int count, String type) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String child = "";
            if (STOWAGE_TAG.equals(type)) {
                child = waybillNumber + "-" + count + "-" + (i + 1);
            } else if (PICK_TAG.equals(type)) {
                child = waybillNumber + "-" + count + "-" + (i + 1) + "-";
            } else {
                child = waybillNumber + "-" + count + "-" + (i + 1) + "-";
            }
            list.add(child);
        }
        return list;
    }

    public static boolean checkSameLabelType(String scanNumber, String scanNumber2) {
        if (TextUtils.isEmpty(scanNumber)) {
            return false;
        }

        ChildWaybillBean childWaybillNumber = new ChildWaybillBean(scanNumber, pattern).invoke();
        ChildWaybillBean childWaybillNo = new ChildWaybillBean(scanNumber2, pattern).invoke();
        if (childWaybillNumber.getLabelType() != childWaybillNo.getLabelType()) {
            return false;
        }
        return true;
    }
}
