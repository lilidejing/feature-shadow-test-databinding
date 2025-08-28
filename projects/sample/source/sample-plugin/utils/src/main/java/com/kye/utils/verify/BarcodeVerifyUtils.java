package com.kye.utils.verify;

import android.text.TextUtils;

import com.kye.pda.utilcode.util.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Routing
 * Desc 单号相关验证工具类
 * Source
 * Created by lixi on 2018/10/18 10:07
 * Modify by lixi on 2018/10/18 10:07
 * Version 1.0
 */
public class BarcodeVerifyUtils {
    /**
     * 资产编码正则
     * 1、"KYE-"开头
     * 2、“KY”+“4位数字”
     * 3、14位纯数字
     */
    public static final String ASSET_CODE_REGEX = "^KYE-\\d+$|^KY\\d{4}$|^\\d{14}$";

    /**
     * 资产编码正则
     * 1、"KYE-"开头
     * 2、“KY”+“8位数字”
     */
    public static final String ASSET_CODE_REGEX2 = "^KYE-\\d+$|^KY\\d{8}$";
    /**
     * 运单正则
     * 11位纯数字
     * 12位纯数字
     * 2个字母 + 13位数字
     * 3个字母 + 12位数字
     */
    public static final String waybillBarcodeRegex = "^\\d{11}$|^\\d{12}$|^[A-Za-z]{2}\\d{13}$|^[A-Za-z]{3}\\d{12}$";

    /**
     * 联邦运单正则
     */
    public static final String fedexWaybillBarcodeRegex = "^\\d{12}$|^\\d{16}$|^\\d{28}$|^\\d{34}$";

    /**
     * 国内航班号正则
     */
    public static final String FLIGHT_NUMBER_REGEX = "^\\d[a-zA-Z]\\d{4}$|^[A-Za-z]{2}\\d{4}$|^[A-Za-z]{3}\\d{3}$|^[A-Za-z]\\d{5}$|^[A-Za-z]{2}\\d{3}[A-Za-z]$";

    /**
     * 网点代码正则
     */
    public static final String BRANCH_POINT_REGEX = "^\\d{3}\\w{2,3}$|^\\d{4}\\w{2,3}$|^(YP|YL|YC|YT|YZ|XH)\\d.+$";

    /**
     * 签回联正则
     */
    public static final String signback = "^\\d{9}$";

    /**
     * 封签号正则
     */
    public static final String sealNumberRegex = "^\\d{1,10}$";

    /**
     * 油管运单号正则
     */
    public static final String postWaybillBarcodeRegex = "^\\d{11}$|^\\d{12}$|^\\d{14}$|^[A-Za-z]{2}\\d{13}$|^[A-Za-z]{3}\\d{12}$";

    /**
     * 子件正则
     */
    public static final String CODE_CHILD = "^(KY\\d{13}|\\d{11}|KY[A-Za-z]\\d{12})[*-]\\d+[*-]\\d+-?$";

    /**
     * 取货标签
     */
    public static final String CODE_TAKE_GOODS_CHILD = "^(\\d{7}|\\d{11}|\\d{12}|[A-Za-z]{2}\\d{13}|[A-Za-z]{3}\\d{12})([*]\\d+[*]\\d+|[-]\\d+[-]\\d+[-])$";

    /**
     * 取货标签 总数为1
     */
    public static final String CODE_TAKE_GOODS_CHILD_ONE_COUNT = "^(\\d{7}|\\d{11})\\*001\\*\\d+$";
    private static final Pattern VIP_PATTERN = Pattern.compile("^[A-Za-z0-9]{10}-[A-Za-z0-9]{3,4}$");
    /**
     * 配货标签 总数为1
     */
    public static final String CODE_MATCH_GOODS_CHILD_ONE_COUNT = "^(\\d{7}|\\d{11})-001-\\d+$";

    /**
     * 配货标签
     */
    public static final String CODE_MATCH_GOODS_CHILD = "^(\\d{7}|\\d{11}|\\d{12}|[A-Za-z]{2}\\d{13}|[A-Za-z]{3}\\d{12})[-]\\d+[-]\\d+$";
    /**
     * 母单正则
     */
    public static final String CODE_MD = "^MD\\d+[-]\\d+$";
    /**
     * 笼号正则
     */
    public static final String CODE_LH = "^LH\\d{8}$";
    /**
     * 干线任务编码正则
     * 例如：G2208110000136
     */
    public static final String CODE_TRUNKLINE = "^G\\d{13}$";
    /**
     * 回单号正则
     */
    public static final String receiptBarcodeRegex = "^\\d{9}$";
    /**
     * 唯品标签规范
     */
    public static final String CODE_WP = "^\\d+[-]\\d+$";

    /**
     * 完整的国家车牌正则
     */
    public static final String CAR_NUMBER_COMPLETENESS =
            "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领跨A-Z]{1}[A-Z]{1" +
                    "}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4,6}[A-Z0-9挂学警港澳厢]{1}$";

    /**
     * 网点代码,员工工号正则
     */
    public static final String REGULAR_EXPRESSION_ALPHABET_CODE_NAME = "^[0-9a-zA-Z]+\\-{0,1}[0-9a-zA-Z]+$";
    /**
     * 员工工号正则（包含临时工号）
     */
    public static final String USER_NUMBER = "^[0-9L]{1}[0-9]+$";


    /**
     * 唯品入库单号（唯品编码）
     * 1.10位纯数字或字母 - 三位纯数字或字母(混合)
     * 2.10位纯数字或字母 - 四位纯数字或字母(混合)
     */
    public static final String VIP_STORAGE_NO = "^[A-Za-z0-9]{10}-[A-Za-z0-9]{3,4}$";
    /**
     * 箱号正则
     */
    public static final String RE_BOX_NUMBER = "^[A-Za-z0-9]{20}$";
    /**
     * 叉车编码正则
     * “前4位子母”+“-”+“两位数字或子母”+“-”+“任意位数字”
     * 例如：KYPY-F1-032
     */
    public static final String FORKLIFT_CODE_REGEX = "^[A-Za-z]{4}-[A-Za-z0-9]{2}-\\d.+$";
    /**
     * 下单编码
     */
    public static final String ORDER_NUMBER = "^XD(\\d{4}|\\d{6})-\\d{7,10}$";

    /**
     * 交接单号
     */
    public static final String deliveryNumber = "^BT\\d{15}$";

    /**
     * 退货箱号
     */
    public static final String containerNo = "^WP\\d{16}$";

    /**
     * 退供单号
     */
    public static final String returnNo = "^\\d{6}RTV\\d{5}KTNH$";

    /**
     * 格位号正则
     */
    public static final String CASE_BAECODE = "^GW+[-]\\d{3}$";
    /**
     * 工号正则
     */
    public static final String USER_NO = "^[0-9a-zA-Z]{1}[0-9]{5}$";
    /**
     * 预配编码正则TSYP-201905080216
     */
    public static final String PRE_MATCHING_BAECODE = "^TSYP+[-]\\d{12}$";
    private static final Pattern STOREINPATTERN = Pattern.compile("^[0-9]{10}-[0-9]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{3}-\\d{8}|\\d{4}-\\d{7,8}");
    /**
     * 唯品单号
     */
    public static final String VIP_CODE = "^WP.*";
    /**
     * 校验中国内陆座机电话
     */
    public final static String TELE_PHONE_REGEX = "\\d{3}-\\d{7,8}|\\d{4}-\\d{7,8}";
    /**
     * 校验中国内陆区号正则，不包括港澳台
     */
    public final static String TELE_PHONE_AREA_REGEX = "(^[0][1-2]\\d)|([0][3-9]\\d{2})";
    public static final String VIP_CODE_LOW = "^wp.*";
    /**
     * 回单封装单号格式,“F开头10位纯数字”
     */
    public static final String RECEIPT_BARCODE_PACKAGE_REGEX = "^F\\d{10}$";

    /**
     * KA运单号正则
     */
    public static final String KA_WAYBILL_NUMBER_1 = "^KY\\d{13}$";
    public static final String KA_WAYBILL_NUMBER_2 = "^KY[A-Z]\\d{12}$";

    /**
     * 托盘条码
     */
    public static final String TRAY_NUMBER = "^TP-\\d{12}[a-zA-Z]$";
    /**
     * 卡位号 纯数字1-4位
     */
    public static final String CARD_POSITION = "^\\d{1,4}$";

    /**
     * 卡位号 纯数字1-4位 - 纯数字1-3位
     */
    public static final String CARD_POSITION2 = "^\\d{1,4}[-]\\d{1,3}$";

    /**
     * 商超退货单号
     */
    public static final String SUPE_RMARKET = "^SC\\d{12}$";

    /**
     * 商超退货单号子件正则
     */
    public static final String SUPE_RMARKET_CHILD = "^(SC\\d{12})[*-]\\d+[*-]\\d+[-]?$";
    public static final String CAR_NUMBER_COMPLETENESS_NEW =
            "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领跨A-Z]{1}[A-Z]{1" +
                    "}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4,6}[A-Z0-9挂学警港澳厢]{1}$";


    /**
     * 回单封装号
     */
    public static final String RECEIPT_PACKAGE_NUM = "^F\\d{10}$";
    /**
     * 拒收运单正则
     */
    public static final String JS_WAYBILL_REG = "^JS\\d{12}$";
    /**
     * 拒收运单子件单号正则
     */
    public static final String JS_WAYBILL_CHILD_REG = "^(JS\\d{12})[*-]\\d+[*-]\\d+[-]?$";

    /**
     * 9-15位数（英文+数字 或 纯数字）单号
     */
    public static final String DIGITAL_ENGLISH_REG = "^(?:(?:[A-Za-z0-9]{9,15})|(?:\\d{9,15}))$";

    /**
     * 伙伴密码正则
     */
    public static final String PARTNER_PASSWORD_REG = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{8,20}$";

    /**
     * 是否是KA运单号
     *
     * @param waybillNumber
     * @return
     */
    public static boolean isKAWaybillNumber(@NotNull String waybillNumber) {
        return waybillNumber.matches(KA_WAYBILL_NUMBER_1) || waybillNumber.matches(KA_WAYBILL_NUMBER_2);
    }


    /**
     * 判断传入但是是否为运单号
     *
     * @param barcode 用来判断的单号
     */
    public static boolean isWayBillBarcode(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.matches(waybillBarcodeRegex);
    }

    /**
     * 判断传入但是是否为联邦运单号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isFedexWayBillBarcode(String barcode) {
        return barcode.matches(fedexWaybillBarcodeRegex);
    }

    /**
     * 判断传入是干线任务编码正则
     *
     * @param barcode 用来判断的单号
     */
    public static boolean isTrunkLinecode(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.matches(CODE_TRUNKLINE);
    }

    /**
     * 判断传入但是是否为封签号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isSealNumberRegex(String barcode) {
        return barcode.matches(sealNumberRegex);
    }

    /**
     * 是否为交接单号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isDeliveryCode(String barcode) {
        return barcode.matches(deliveryNumber);
    }

    /**
     * 是否为退货箱号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isContainerCode(String barcode) {
        return barcode.matches(containerNo);
    }

    /**
     * 是否为退供单号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isReturnCode(String barcode) {
        return barcode.matches(returnNo);
    }


    /**
     * 是否为签回联单号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isSignBackCode(String barcode) {
        return barcode.matches(signback);
    }

    /**
     * 油管判断传入但是是否为运单号，他们相比正常的多了个14位的运单
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isPostWayBillBarcode(String barcode) {
        return barcode.matches(postWaybillBarcodeRegex);
    }

    /**
     * 检查是否回单封装单号格式
     *
     * @param barcode 需验证标签
     * @return true 是，false 不是
     */
    public static boolean isReceiptBarcodePackage(String barcode) {
        if (!TextUtils.isEmpty(barcode))
            return barcode.matches(RECEIPT_BARCODE_PACKAGE_REGEX);
        return false;
    }

    /**
     * 判断传入单号是否为回单号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isReciptBarcode(String barcode) {
        return barcode.matches(receiptBarcodeRegex);
    }

    /**
     * 判断传入单号是否为母单
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isMDNUmber(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.matches(CODE_MD);
    }

    /**
     * 检查标签是否为唯品标签
     *
     * @param barcode 需验证标签
     * @return true 是，false 不是
     */
    public static boolean isWPBarcode(String barcode) {
        if (!TextUtils.isEmpty(barcode))
            return barcode.matches(CODE_WP);
        return false;
    }

    /**
     * 判断是否是子件
     *
     * @param barcode
     * @return
     */
    public static boolean isChildBarcode(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.matches(CODE_CHILD);
    }

    /**
     * 判断是否是车牌号
     *
     * @param barcode
     * @return
     */
    public static boolean isCarNoBarcode(String barcode) {
        return barcode.matches(CAR_NUMBER_COMPLETENESS);
    }

    /**
     * 判断是否是出车卡号，查车辆信息其实扫的是出车卡号
     *
     * @param str
     * @return
     */
    public static boolean isOutCarNo(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("^[a-zA-Z][a-zA-Z0-9]{5,9}");
    }

    /**
     * 判断是否是运单，子件，母单
     *
     * @param waybill
     * @return
     */
    public static boolean isLegalChildWaybill(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && (waybill.matches(waybillBarcodeRegex)
                || waybill.matches(CODE_CHILD)
                || waybill.matches(CODE_MD))) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是取货标签
     *
     * @param waybill
     * @return
     */
    public static boolean isPickGoodsChildWaybill(String waybill) {
        if (!TextUtils.isEmpty(waybill) && waybill.matches(CODE_TAKE_GOODS_CHILD)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是运单，取货标签
     *
     * @param waybill
     * @return
     */
    public static boolean isTakeGoodsChildWaybill(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && (waybill.matches(waybillBarcodeRegex)
                || waybill.matches(CODE_TAKE_GOODS_CHILD))) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否是配货标签
     *
     * @param waybill
     * @return
     */
    public static boolean isMatchGoodsChildWaybill(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && waybill.matches(CODE_MATCH_GOODS_CHILD)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否一票一件的子件
     * matches方法性能不如contains
     *
     * @param waybill
     * @return
     */
    public static boolean isOneTicketOneCount(String waybill) {
        String regex = "[*-]";
        String[] split = waybill.split(regex);
        if (split.length > 2) {
            try {
                int i = Integer.parseInt(split[1]);
                int j = Integer.parseInt(split[2]);
                if (i == 1 && j == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 判断是否一票一件的子件
     * matches方法性能不如contains
     *
     * @param waybill
     * @return
     */
    public static boolean isOneTicketOneCountNewTakeGoods(String waybill) {
        return waybill.contains("-1-1-");
    }

    /**
     * 判断是否是运单，子件，母单, 笼号
     *
     * @param waybill
     * @return
     */
    public static boolean isAllWaybill(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && (waybill.matches(waybillBarcodeRegex)
                || waybill.matches(CODE_CHILD)
                || waybill.matches(CODE_MD)
                || waybill.matches(CODE_LH))
                || isSupeRmarket(waybill)) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否是9-15位数（英文+数字 或 纯数字）单号
     *
     * @param waybill
     */
    public static boolean isDigitalEnglish(String waybill) {
        return !TextUtils.isEmpty(waybill)
                && (waybill.matches(DIGITAL_ENGLISH_REG));
    }


    /**
     * 判断是否是运单，子件
     *
     * @param waybill
     * @return
     */
    public static boolean isLegalChildWaybillNotMD(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && (waybill.matches(waybillBarcodeRegex)
                || waybill.matches(CODE_CHILD))) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是运单号、取货标签、配货标签
     *
     * @param waybill
     * @return
     */
    public static boolean isWaybillOrChildBill(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && (waybill.matches(waybillBarcodeRegex)
                || waybill.matches(CODE_TAKE_GOODS_CHILD)
                || waybill.matches(CODE_MATCH_GOODS_CHILD))) {
            return true;
        }
        return false;
    }

    /**
     * 当扫描为运单号需要截取的时候调用,截取子件标签前边运单号并返回
     *
     * @param barcode 需要截取的运单号
     * @return 返回运单号
     */
    public static String getWaybillNumber(String barcode) {
        if (barcode.startsWith("MD") || barcode.startsWith("LH")) {
            return barcode;
        }
        int index = barcode.indexOf("*");
        if (index == -1) {
            index = barcode.indexOf("-");
        }
        if (index != -1) {
            barcode = barcode.substring(0, index);
        }
        return barcode;
    }


    /**
     * 获取子件的子件编号
     *
     * @param barcode
     * @return barcode 是子件单号时截取最后的子件编号，非子件单号时直接返回""
     */
    public static String getChildNumber(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return "";
        }
        if (!isChildBarcode(barcode)) {
            return barcode;
        }
        StringBuilder childNumber = new StringBuilder();
        String regex = "[*-]";
        String[] split = barcode.split(regex);
        if (split.length > 2) {
            try {
                int a = Integer.parseInt(split[1]);
                int b = Integer.parseInt(split[2]);
                if (a < b) {
                    childNumber.append(a);
                } else {
                    childNumber.append(b);
                }
                if (StringUtils.getSameCharCount("-", barcode) == 3 || barcode.contains("*")) {
                    //新取货标签3个-
                    //新旧标签统一显示新格式
                    childNumber.append("-");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return barcode;
            }
        } else {
            return barcode;
        }
        return childNumber.toString();
    }

    /**
     * 截取运单号
     */
    public static int getTotalCountByWaybill(String barcode) {

        int totalCount = 0;
        String regex = "[*-]";
        String[] split = barcode.split(regex);
        if (split.length > 2) {
            try {
                int i = Integer.parseInt(split[1]);
                int j = Integer.parseInt(split[2]);
                if (i > j) {
                    totalCount = i;
                } else {
                    totalCount = j;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return totalCount;
    }

    /**
     * 判断是否是员工号
     *
     * @param employeeNumber
     * @return
     */
    public static boolean isEmployeeNumber(String employeeNumber) {
        return employeeNumber.matches(USER_NUMBER);
    }

    /**
     * 判断是否为航班号
     *
     * @param flightNumber
     * @return
     */
    public static boolean isFlightNumber(String flightNumber) {
        if (TextUtils.isEmpty(flightNumber)) {
            return false;
        }
        if (flightNumber.contains("-")) {
            String[] flight = flightNumber.split("-");
            if (flight != null && flight.length > 0) {
                return flight[0].matches(FLIGHT_NUMBER_REGEX);
            }
        }
        return flightNumber.matches(FLIGHT_NUMBER_REGEX);
    }

    /**
     * 判断是否是网点代码
     *
     * @param branchPoint
     * @return
     */
    public static boolean isBranchPoint2(String branchPoint) {
        if (TextUtils.isEmpty(branchPoint)) {
            return false;
        }
        if (branchPoint.startsWith("DBDM") || branchPoint.startsWith("BMDM")) {
            return true;
        }
        return branchPoint.matches(BRANCH_POINT_REGEX);
    }

    /**
     * 判断是否是网点代码,员工工号正则
     *
     * @param code
     * @return
     */
    public static boolean isBranchPoint(String code) {
        if (code.matches(REGULAR_EXPRESSION_ALPHABET_CODE_NAME)) {
            return true;
        }
        return false;
    }

    /**
     * 截取为运单号
     * 如果是取货或配货标签，截取为运单号（注提单号可能有"-"）
     *
     * @return 单号
     */
    public static String resolveIntoWaybillNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return number;
        }
        if (number.matches(CODE_CHILD)) {
            //取货或配货标签
            int end = number.length();
            if (number.contains("*")) {
                end = number.indexOf("*");
            } else if (number.contains("-")) {
                end = number.indexOf("-");
            }
            number = number.substring(0, end);
        }
        return number;
    }


    public static boolean isCard(String number) {
        String regex = "^[0-9]+$";
        Matcher m = Pattern.compile(regex).matcher(number);
        if (m.matches() && number.length() <= 4) {
            //纯数字且小于等于4位
            return true;
        }
        //或者数字-数字，这种林进森要求不限制位数
//        regex = "^[0-9]+(-)[0-9]+$";
        //20180519林进森要求，修改正则表达式[字母][数字]-[数字]
        regex = "^[A-Za-z]*[0-9]+-[0-9]+$";
        m = Pattern.compile(regex).matcher(number);
        return m.matches();
    }

    /**
     * 是否是笼号
     *
     * @param cageNo
     * @return
     */
    public static boolean isCageNumber(String cageNo) {

        if (TextUtils.isEmpty(cageNo)) {
            return false;
        }
        return cageNo.matches(CODE_LH);
    }

    /**
     * 是否是装卸车位
     */
    public static boolean isZXCode(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.startsWith("ZX");
    }

    /**
     * 数字和字母
     *
     * @param str
     * @return
     */
    public static boolean isNumberAndLetter(String str) {
        String regx = "^[0-9a-zA-Z]{1,}$";
        if (!TextUtils.isEmpty(str) && str.matches(regx)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMobilePhoneNumber(String number) {
        String regx = "^(1[3-9])\\d{9}$";
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        return number.matches(regx);
    }

    /**
     * 首位非0小数验证（最多保留两位小数）
     *
     * @param content 内容
     * @return true 验证通过
     */
    public static boolean isDecimal(String content) {
        return content.matches("^((([^0][0-9]+|0)\\.([0-9]{1,2}))$)|^(([1-9]+)\\.([0-9]{1,2})$)");
    }

    /**
     * 验证是否为下单编码
     *
     * @param number 号码
     * @return true 是
     */
    public static boolean isOrderNumber(String number) {
        if (!TextUtils.isEmpty(number)) {
            return number.matches(ORDER_NUMBER);
        }
        return false;
    }

    /**
     * 非中文
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    public static boolean isNumeric(String str) {
        // 使用正则表达式匹配一个或多个数字字符
        String regex = "\\d+";
        return str.matches(regex);
    }

    public static boolean isChineseOrNumeric(String str) {
        // 使用正则表达式匹配一个或多个数字字符
        String regex = "^[\u4e00-\u9fa50-9]+$";
        return str.matches(regex);
    }

    /**
     * 中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        String regex = "^[\\u4e00-\\u9fa5]+$";
        return str.matches(regex);
    }

    /**
     * 模糊匹配
     *
     * @param str
     * @return
     */
    public static boolean fuzzyQuery(String str) {
        String regex = "^.*[\\u4e00-\\u9fa5]+.*$";
        return str.matches(regex);
    }

    /**
     * 汉字括号数字
     *
     * @param content
     * @return
     */
    public static boolean isChineseNumber(String content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        String regex = "[\\u4e00-\\u9fa5_0-9]+[\\（]?[\\）]?[\\(]?[\\)]?";
        return content.matches(regex);
    }

    /**
     * 是否是交接码
     *
     * @param content
     * @return
     */
    public static boolean isTaskCode(String content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        return (content.startsWith("YC") || content.startsWith("ZC")) && content.contains("-");
    }

    /**
     * 判断传入是否是格位号
     *
     * @param barcode 用来判断的格位号
     * @return
     */
    public static boolean isCaseBarcode(String barcode) {
        return barcode.matches(CASE_BAECODE);
    }

    /**
     * 判断传入是否是卡位号
     *
     * @param barcode 用来判断的卡位号
     * @return
     */
    public static boolean isKwBarcode(String barcode) {
        return barcode.startsWith("KW-");
    }

    /**
     * 判断传入是否是预配编码
     *
     * @param preMatchingEncode 用来判断的格位号
     * @return
     */
    public static boolean isPreMatchingEncode(String preMatchingEncode) {
        return preMatchingEncode.matches(PRE_MATCHING_BAECODE);
    }

    /**
     * 是否唯品单号
     *
     * @param code
     * @return
     */
    public static boolean isVipCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(VIP_CODE) || code.matches(VIP_CODE_LOW);
    }

    /**
     * 是否是编织袋
     *
     * @param code
     * @return
     */
    public static boolean isBianZhiDaiCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.startsWith("BZD");
    }

    /*判断是否手选员工车辆（达达外请也属于员工车辆）*/
    public static boolean isEmployeeCar(String car) {
        if (!TextUtils.isEmpty(car) && (car.contains("闪送外请") || car.contains("达达外请"))) {
            return true;
        }
        return false;
    }


    /**
     * 判断运单号是否是单票多件
     *
     * @param waybillNumber
     * @return
     */
    public static boolean waybillNumberIsMulti(String waybillNumber) {
        //是否是合法运单
        boolean mIsLegalWaybill = isLegalChildWaybillNotMD(waybillNumber);
        if (mIsLegalWaybill) {
            //是否是合法子件
            boolean mIsLegalChildWaybill = waybillNumber.matches(CODE_CHILD);
            if (mIsLegalChildWaybill) {
                String[] numberStr = null;
                if (waybillNumber.contains("*")) {
                    waybillNumber = waybillNumber.replace("*", "-");
                }
                if (waybillNumber.contains("-")) {
                    numberStr = waybillNumber.split("-");
                }
                if (numberStr != null) {
                    boolean condition = numberStr.length >= 3;
                    if (condition) {
                        int number0 = Integer.parseInt(numberStr[1]);
                        int number1 = Integer.parseInt(numberStr[2]);
                        return number0 != number1 || number0 != 1;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 校验卡位号 纯1~4位数字 或 纯1~4位数字 - 纯1~3位数字
     *
     * @param number 内容
     * @return
     */
    public static boolean isCard3(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        return number.matches(CARD_POSITION) || number.matches(CARD_POSITION2);
    }


    /*托盘交接条码校验*/
    public static boolean isTrayManagerNumber(String number, String tray_regular) {
        if (!TextUtils.isEmpty(number)) {
            String regex = TRAY_NUMBER;
            if (!TextUtils.isEmpty(tray_regular)) {
                regex = tray_regular;
            }
            if (number.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否商超退货单号 包含子件单号
     *
     * @param code
     * @return
     */
    public static boolean isSupeRmarket(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(SUPE_RMARKET) || code.matches(SUPE_RMARKET_CHILD);
    }

    /**
     * 是否商超退货子件单号
     *
     * @param code
     * @return
     */
    public static boolean isSupeRmarketChildNumber(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(SUPE_RMARKET_CHILD);
    }

    /**
     * 判断是否叉车编码
     *
     * @param code
     * @return
     */
    public static boolean isForkliftCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(FORKLIFT_CODE_REGEX);
    }

    /**
     * 商超退货单号,截取前面的运单号
     *
     * @param barcode 需要截取的运单号
     * @return 返回运单号
     */
    public static String getSupeRmarketNumber(String barcode) {
        int index = barcode.indexOf("-");
        if (index != -1) {
            barcode = barcode.substring(0, index);
        }
        return barcode;
    }

    /**
     * 商超退货单号,截取子件单号
     */
    public static String getSupeRmarketChildNumber(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return "";
        }
        if (!isSupeRmarketChildNumber(barcode)) {
            return barcode;
        }
        String childNumber = "";
        String regex = "[*-]";
        String[] split = barcode.split(regex);
        if (split.length > 2) {
            try {
                int a = Integer.parseInt(split[1]);
                int b = Integer.parseInt(split[2]);
                if (a < b) {
                    childNumber += a;
                } else {
                    childNumber += b;
                }
                //新取货标签3个-
                if (StringUtils.getSameCharCount("-", barcode) == 3 || barcode.contains("*")) {
                    //新旧标签统一显示新格式
                    childNumber = childNumber + "-";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                childNumber = barcode;
            }
        } else {
            childNumber = barcode;
        }
        return childNumber;
    }

    /**
     * 是否无头件
     *
     * @param code
     */
    public static boolean isNoHeader(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        if (StringUtils.isAllNumbers(code) && code.length() == 12) {
            return true;
        }
        return false;
    }

    /**
     * 拒收运单号正则
     */
    public static final String JS_WAYBILL_NUMBER = "^JS\\d{12}$";


    /**
     * 拒收运单号子件正则
     */
    public static final String JS_WAYBILL_NUMBER_CHILD = "^(JS\\d{12})[*-]\\d+[*-]\\d+[-]?$";

    /**
     * 是否为交接单号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isGoodsRejectCode(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.matches(JS_WAYBILL_NUMBER);
    }

    /**
     * 是否商超退货子件单号
     *
     * @param code
     * @return
     */
    public static boolean isGoodsRejectChildNumber(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(JS_WAYBILL_NUMBER_CHILD);
    }

    /**
     * 商超退货单号,截取子件单号
     */
    public static String getGoodsRejectCodeChild(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return "";
        }
        if (isGoodsRejectChildNumber(barcode)) {
            if (barcode.length() > 14) {
                barcode = barcode.substring(0, 14);
            }
        }
        return barcode;
    }

    /**
     * 判断是否是拒收运单或拒收运单子件
     *
     * @param jsWaybill
     * @return
     */
    public static boolean isJSWaybill(String jsWaybill) {
        if (TextUtils.isEmpty(jsWaybill)) {
            return false;
        }
        return jsWaybill.matches(JS_WAYBILL_REG) || jsWaybill.matches(JS_WAYBILL_CHILD_REG);
    }

    /**
     * 判断是否是拒收运单子件
     *
     * @param jsWaybill
     * @return
     */
    public static boolean isJSChildWaybill(String jsWaybill) {
        if (TextUtils.isEmpty(jsWaybill)) {
            return false;
        }
        return jsWaybill.matches(JS_WAYBILL_CHILD_REG);
    }

    /**
     * 拒收运单单号,截取子件单号
     */
    public static String getJsWaybillChildNumber(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return "";
        }
        if (!isJSChildWaybill(barcode)) {
            return barcode;
        }
        String childNumber = "";
        String regex = "[*-]";
        String[] split = barcode.split(regex);
        if (split.length > 2) {
            try {
                int a = Integer.parseInt(split[1]);
                int b = Integer.parseInt(split[2]);
                if (a < b) {
                    childNumber += a;
                } else {
                    childNumber += b;
                }
                //新取货标签3个-
                if (StringUtils.getSameCharCount("-", barcode) == 3 || barcode.contains("*")) {
                    //新旧标签统一显示新格式
                    childNumber = childNumber + "-";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                childNumber = barcode;
            }
        } else {
            childNumber = barcode;
        }
        return childNumber;
    }


    /**
     * 拒收运单单号截取，获取不带子件的拒收运单号
     *
     * @param barcode
     */
    public static String getJsWaybillMainNumber(String barcode) {
        if (!isJSWaybill(barcode)) {
            return "";
        }
        if (isJSChildWaybill(barcode)) {
            if (barcode.contains("*")) {
                int xinIndex = barcode.indexOf("*");
                return barcode.substring(0, xinIndex);
            }
            if (barcode.contains("-")) {
                int index = barcode.indexOf("-");
                return barcode.substring(0, index);
            }
        }
        return barcode;
    }

    /**
     * 从子件单号中获取总数量
     */
    public static int getTotalCountFromChildBillNo(String childBillNo) {
        //不是子件单号，返回1，表示只有一件
        if (!isChildBarcode(childBillNo)) {
            return 1;
        }
        String totalCountStr;
        if (childBillNo.contains("*")) {
            totalCountStr = childBillNo.split("\\*")[1];
        } else {
            totalCountStr = childBillNo.split("-")[1];
        }
        return Integer.parseInt(totalCountStr);
    }

    /**
     * 是否是胶垫板资产或木垫板资产
     *
     * @param value
     * @return
     */
    public static boolean isJDBLiquidAsset(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        } else if (AssetsUtilKt.isBottomBoard(value)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是卡板资产
     *
     * @param value
     * @return
     */
    public static boolean isKBLiquidAsset(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        } else if (AssetsUtilKt.isCardBoard(value)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是周转箱资产
     *
     * @param value
     * @return
     */
    public static boolean isCircleBoxLiquidAsset(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        } else if (AssetsUtilKt.isCircleBox(value)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是流动资产
     *
     * @param result
     * @return
     */
    public static boolean isLiquidAsset(String result) {
        return BarcodeVerifyUtils.isJDBLiquidAsset(result) ||
                BarcodeVerifyUtils.isKBLiquidAsset(result) ||
                BarcodeVerifyUtils.isCircleBoxLiquidAsset(result);
    }

    /**
     * 判断是否是运单 母单, 笼号
     *
     * @param waybill
     * @return
     */
    public static boolean isAllWaybillNew(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && (waybill.matches(waybillBarcodeRegex)
                || waybill.matches(CODE_MD)
                || waybill.matches(CODE_LH))) {
            return true;
        }
        return false;
    }

    /**
     * 获取子件的纯粹子件编号
     *
     * @param barcode
     * @return barcode 是子件单号时截取最后的纯粹子件编号，不带-，非子件单号时直接返回""
     */
    public static String getChildNumber2(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return "";
        }
        if (!isChildBarcode(barcode)) {
            return barcode;
        }
        String childNumber = "";
        String regex = "[*-]";
        String[] split = barcode.split(regex);
        if (split.length > 2) {
            try {
                int a = Integer.parseInt(split[1]);
                int b = Integer.parseInt(split[2]);
                if (a < b) {
                    childNumber += a;
                } else {
                    childNumber += b;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                childNumber = barcode;
            }
        } else {
            childNumber = barcode;
        }
        return childNumber;
    }

    /**
     * 判断是否叉车编码
     *
     * @param code
     * @return
     */
    public static boolean isAssetCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(ASSET_CODE_REGEX);
    }

    /**
     * 判断是资产编码，针对分拣建笼
     * "KYE-" + 至少一位数字。
     * "KY" + 恰好8位数字。
     *
     * @param code
     * @return
     */
    public static boolean isAssetCodeForSorting(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(ASSET_CODE_REGEX2);
    }

    /**
     * 是否是笼车 围板箱等流动资产条码
     */
    public static boolean isCageBarCode(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.startsWith("KYE-") || text.startsWith("KY-");
    }

    /**
     * 校验卡位号 纯4位数字
     */
    public static boolean isCard2(String number) {
        String regex = "^[0-9]+$";
        Matcher m = Pattern.compile(regex).matcher(number);
        if (m.matches() && number.length() <= 4) {
            //纯数字且小于等于4位
            return true;
        }
        return false;
    }

    /**
     * 获取子件标签中的后四位运单号带后面的子件标签
     *
     * @param childBarcode
     * @return
     */
    public static String getLastFourWaybillNumberWithChild(String childBarcode) {
        if (TextUtils.isEmpty(childBarcode)) {
            return "";
        }

        if (!isChildBarcode(childBarcode)) {
            return childBarcode;
        }

        String waybillNumber = getWaybillNumber(childBarcode);
        if (TextUtils.isEmpty(waybillNumber)) {
            return "";
        }
        int length = waybillNumber.length();
        if (length > 4) {
            String lastFourWaybill = waybillNumber.substring(length - 4);
            int lastFourPosition = childBarcode.indexOf(lastFourWaybill);
            String lastFourValue = childBarcode.substring(lastFourPosition);
            return lastFourValue;
        }
        return "";
    }

    /**
     * 判断唯品入库运单
     * <p>
     * 1.10位纯数字或字母 - 三位纯数字或字母(混合)
     * 2.10位纯数字或字母 - 四位纯数字或字母(混合)
     */
    public static boolean isVipInStorageNo(String no) {
        Matcher matcher = VIP_PATTERN.matcher(no);
        return matcher.matches();
    }

    /**
     * 获取子件的纯粹子件编号
     *
     * @param barcode
     * @return barcode 是子件单号时截取最后的纯粹子件编号，不带-，非子件单号时直接返回""
     */
    public static String getChildNumberOnlyNumber(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return "";
        }
        if (!isChildBarcode(barcode)) {
            return barcode;
        }
        String childNumber = "";
        String regex = "[*-]";
        String[] split = barcode.split(regex);
        if (split.length > 2) {
            try {
                int a = Integer.parseInt(split[1]);
                int b = Integer.parseInt(split[2]);
                if (a < b) {
                    childNumber += a;
                } else {
                    childNumber += b;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                childNumber = barcode;
            }
        } else {
            childNumber = barcode;
        }
        return childNumber;
    }

    /**
     * 是否是箱号
     *
     * @param barCode
     * @return
     */
    public static boolean isBoxNumber(String barCode) {
        return barCode.matches(RE_BOX_NUMBER);
    }

    /**
     * 是否是唯品入库单号
     *
     * @param code
     * @return
     */
    public static boolean isVipInputCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(VIP_STORAGE_NO);
    }

    /**
     * 判断传入单号是否为回单封装号
     *
     * @param barcode 用来判断的单号
     * @return
     */
    public static boolean isReceiptPackageBarcode(String barcode) {
        return barcode.matches(RECEIPT_PACKAGE_NUM);
    }

    /**
     * 判断是否是车牌号
     *
     * @param barcode
     * @return
     */
    public static boolean isCarNoBarcodeKY(String barcode) {
        return !TextUtils.isEmpty(barcode) && barcode.matches(CAR_NUMBER_COMPLETENESS_NEW);
    }

    /**
     * 获取固定电话号码，非固定电话返回空字符串
     *
     * @param number 电话号码
     * @return 非固定电话返回空字符串
     */
    public static String obtainFixedPhone(String number) {
        Matcher matcher = PHONE_PATTERN.matcher(number);
        if (matcher.matches()) {
            // 座机直接返回
            return number;
        } else if (matcher.lookingAt()) {
            // 以座机开头的是带分机号的例如：0755-2657471-001
            String[] split = number.split("-");
            if (split.length == 3) {
                return split[0] + "-" + split[1] + "-" + split[2];
            }
        }
        return "";
    }

    /**
     * 获取子件总数
     *
     * @param barcode
     * @return barcode 是子件单号时截取最后的纯粹子件编号，不带-，非子件单号时直接返回""
     */
    public static String getChildNumberCount(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return "";
        }
        if (!isChildBarcode(barcode)) {
            return barcode;
        }
        String childNumber = "";
        String regex = "[*-]";
        String[] split = barcode.split(regex);
        if (split.length > 2) {
            try {
                int a = Integer.parseInt(split[1]);
                int b = Integer.parseInt(split[2]);
                if (a > b) {
                    childNumber += a;
                } else {
                    childNumber += b;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                childNumber = barcode;
            }
        } else {
            childNumber = barcode;
        }
        return childNumber;
    }

    public static boolean containsEnglish(String str) {
        String regex = ".*[a-zA-Z]+.*";
        return str.matches(regex);
    }

    /**
     * 是否包含中文
     *
     * @return
     */
    public static boolean containsChinese(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String regex = ".*[\\u4e00-\\u9fa5]+.*";
        return str.matches(regex);
    }

    /**
     * 是否是入库单号
     *
     * @param no
     * @return
     */
    public static boolean isInputNo(String no) {
        Matcher matcher = STOREINPATTERN.matcher(no);
        return matcher.matches();
    }

    /**
     * 验证是否有效工号
     *
     * @param userNo 员工工号
     * @return true 有效；false 无效
     */
    public static boolean isUserNo(String userNo) {
        return !TextUtils.isEmpty(userNo) && userNo.matches(USER_NO);
    }

    /**
     * 是否是周转箱条码
     *
     * @param barcode 周转箱条码
     * @return 是：true，不是：false
     */
    public static boolean isTurnOverBoxBarcode(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.startsWith("ZZX");
    }

    /**
     * 是否是 AE运单
     *
     * @param barcode AE运单
     * @return 是：true，不是：false
     */
    public static boolean isAECheckBarcode(String barcode) {
        if (TextUtils.isEmpty(barcode)) {
            return false;
        }
        return barcode.startsWith("LBX");
    }

    public static boolean isPassword(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        return code.matches(PARTNER_PASSWORD_REG);
    }

    /**
     * 是否是外部订单号
     *
     * @return
     */
    public static boolean isEcsOrderNo(String orderNo) {
        if (TextUtils.isEmpty(orderNo)) {
            return false;
        }
        String regex = "^[0-9]+$";
        Matcher m = Pattern.compile(regex).matcher(orderNo);
        if (m.matches() && orderNo.length() == 18) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是运单，子件，配货标签
     *
     * @param waybill
     * @return
     */
    public static boolean isChildMatchGoodsWaybill(String waybill) {
        if (!TextUtils.isEmpty(waybill)
                && (waybill.matches(waybillBarcodeRegex)
                || waybill.matches(CODE_CHILD)
                || waybill.matches(CODE_MATCH_GOODS_CHILD))) {
            return true;
        }
        return false;
    }
}
