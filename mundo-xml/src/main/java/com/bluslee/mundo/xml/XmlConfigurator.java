package com.bluslee.mundo.xml;

import com.thoughtworks.xstream.XStream;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description XmlConfigurator
 * @copyright COPYRIGHT © 2014 - 2021 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 */
public class XmlConfigurator{

    /**
     * 使用XStream解析xml
     */
    private final XStream X_STREAM = new XStream();

    /**
     * 配置文件默认路径
     */
    private final String DEFAULT_CONFIG_FILE_PATH = "/mundo.cfg.xml";


}
