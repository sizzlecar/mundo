package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * BaseProcessNode builder.
 *
 * @author carl.che
 */
public final class ProcessElementBuilder<N extends BaseProcessNode> {

    private final String id;

    private String name;

    private String conditionExpression;

    private N source;

    private N target;

    private ProcessElementBuilder(final String id) {
        this.id = id;
    }

    public static <N extends BaseProcessNode> ProcessElementBuilder<N> instance(final String id) {
        return new ProcessElementBuilder<>(id);
    }

    public ProcessElementBuilder<N> name(final String name) {
        this.name = name;
        return this;
    }

    public ProcessElementBuilder<N> conditionExpression(final String conditionExpression) {
        this.conditionExpression = conditionExpression;
        return this;
    }

    public ProcessElementBuilder<N> source(final N source) {
        this.source = source;
        return this;
    }

    public ProcessElementBuilder<N> target(final N target) {
        this.target = target;
        return this;
    }

    public Activity activity() {
        return new Activity(id, name);
    }

    public EndNode endNode() {
        return new EndNode(id, name);
    }

    public ExclusiveGateway exclusiveGateway() {
        return new ExclusiveGateway(id, name);
    }

    public ParallelGateway parallelGateway() {
        return new ParallelGateway(id, name);
    }

    public Link link() {
        return new Link(id, name, source, target, conditionExpression);
    }

    public StartNode startNode() {
        return new StartNode(id, name);
    }

}
