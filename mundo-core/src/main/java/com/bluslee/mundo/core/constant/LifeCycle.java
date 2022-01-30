package com.bluslee.mundo.core.constant;

/**
 * 加载配置的生命周期.
 * @author carl.che
 */
public enum LifeCycle {

    /**
     * 根据配置加载对应的资源.
     */
    LOAD,

    /**
     * 根据配置，以及加载后的资源，进行解析.
     */
    PARSE,

    /**
     * 根据配置，以及解析后的资源，build出对应的资源.
     */
    BUILD

}
