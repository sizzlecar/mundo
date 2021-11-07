package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseLink;
import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description Link
 * @copyright COPYRIGHT © 2014 - 2021 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 */
public class Link extends BaseLink {

    public Link(String id, String name, BaseProcessNode source, BaseProcessNode target, String conditionExpression) {
        super(id, name, source, target, conditionExpression);
    }

    public Link(BaseProcessNode source, BaseProcessNode target, String conditionExpression) {
        super(source, target, conditionExpression);
    }

    public Link() {
    }
}
