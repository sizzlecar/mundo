package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseLink;
import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * 流程Link类型.
 *
 * @author carl.che
 */
public class Link extends BaseLink {

    Link(final String id, final String name, final BaseProcessNode source, final BaseProcessNode target, final String conditionExpression) {
        super(id, name, source, target, conditionExpression);
    }

    Link(final BaseProcessNode source, final BaseProcessNode target, final String conditionExpression) {
        super(source, target, conditionExpression);
    }

    Link() {
    }
}
