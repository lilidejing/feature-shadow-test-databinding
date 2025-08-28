package com.kye.pda.burypoint;



import java.util.HashMap;
import java.util.Map;

/**
 * @author : 703593
 * @description : ClickEventBean
 * @date : 2024/07/05
 */
public class ClickEventBean  {
    public static final int TYPE_BURY = 1;
    public static final int TYPE_PAGE_START = 2;
    public static final int TYPE_PAGE_END = 3;
    public static final int TYPE_EFFICIENCY_BURIED_POINT = 4;
    /**
     * 1 自定义埋点  2 页面start 3 页面end 4 能效埋点
     */
    private int type;
    /**
     * 事件key
     */
    private String key;
    /**
     * 对应的属性map
     */
    private Map<String, Object> eventMap;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, Object> eventMap) {
        this.eventMap = eventMap;
    }

    public static class Builder {
        private final ClickEventBean clickEventBean;

        public Builder() {
            clickEventBean = new ClickEventBean();
        }

        public Builder setType(int type) {
            clickEventBean.type = type;
            return this;
        }

        public Builder setKey(String key) {
            if (clickEventBean != null) {
                clickEventBean.key = key;
            }
            return this;
        }

        public Builder setEventMap(Map<String, Object> eventMap) {
            clickEventBean.eventMap = eventMap;
            return this;
        }

        public Builder putEventMap(String key, Object value) {
            if (clickEventBean.eventMap == null) {
                clickEventBean.eventMap = new HashMap<>();
            }
            clickEventBean.eventMap.put(key, value);
            return this;
        }

        public ClickEventBean build() {
            return clickEventBean;
        }
    }
}
