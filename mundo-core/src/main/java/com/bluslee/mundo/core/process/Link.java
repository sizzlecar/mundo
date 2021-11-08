package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseLink;
import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description Link
 */
public class Link extends BaseLink {

    Link(String id, String name, BaseProcessNode source, BaseProcessNode target, String conditionExpression) {
        super(id, name, source, target, conditionExpression);
    }

    Link(BaseProcessNode source, BaseProcessNode target, String conditionExpression) {
        super(source, target, conditionExpression);
    }

    Link() {
    }
}
