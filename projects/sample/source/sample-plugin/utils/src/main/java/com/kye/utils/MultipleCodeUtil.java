package com.kye.utils;

import android.text.TextUtils;

import com.kye.pda.utils.ArithUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * multipleCodeAndMsg格式:code1-msg1#code2-msg2
 * 数据解析
 */
public class MultipleCodeUtil {

    public static List<MultipleCodeBean> parseSourceMsg(String sourceMsg) {
        List<MultipleCodeBean> codeBeans = new ArrayList<>();
        if (TextUtils.isEmpty(sourceMsg)) {
            return codeBeans;
        }
        String[] sourceSplitArr = sourceMsg.split("#");
        for (String str : sourceSplitArr) {
            MultipleCodeBean codeBean = new MultipleCodeBean();
            String[] codeOrMsg = str.split("-");
            String codeStr = codeOrMsg[0];
            if (ArithUtil.mIsInteger(codeStr)) {
                int code = Integer.parseInt(codeStr);
                codeBean.setCode(code);
            }
            if (codeOrMsg.length == 2) {
                codeBean.setMsg(codeOrMsg[1]);
            }
            codeBeans.add(codeBean);
        }
        return codeBeans;
    }

    public static class MultipleCodeBean implements Serializable {
        /**
         * 提示code
         */
        private int code;
        /**
         * 提示语
         */
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
