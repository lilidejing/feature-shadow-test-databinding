package com.kye.utils.limiting;

/**
 * 限流器实体类，用于控制和管理限流逻辑中的刷新机制。
 * @author 刘涛13
 * @date 2024/4/3
 */
public class CurrentLimitingBean {
    /**
     * 刷新方式的枚举类型：
     * 0 表示立即刷新，
     * 10 表示防重刷新，
     * 20 表示延迟刷新。
     */
    protected int refreshType;

    /**
     * 默认构造函数，初始化刷新类型为立即刷新。
     */
    public CurrentLimitingBean(){
        refreshType = 0;
    }

    /**
     * 带参数的构造函数，用于指定刷新类型。
     * @param refreshType 刷新类型，0为立即刷新，10为防重刷新，20为延迟刷新。
     */
    public CurrentLimitingBean(int refreshType) {
        this.refreshType = refreshType;
    }

    /**
     * 获取当前设置的刷新类型。
     * @return 返回当前刷新类型的值。
     */
    public int getRefreshType() {
        return refreshType;
    }

    /**
     * 设置刷新类型，并返回当前实例以支持链式调用。
     * @param refreshType 指定的刷新类型值，0为立即刷新，10为防重刷新，20为延迟刷新。
     * @return 返回当前实例。
     */
    public CurrentLimitingBean setRefreshType(int refreshType) {
        this.refreshType = refreshType;
        return this;
    }
}
