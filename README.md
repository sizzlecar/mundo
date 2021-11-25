# mundo

## mundo是什么？

mundo是一个已保持简单，职责单一为设计原则，轻量级，灵活的，高性能的流程引擎。

## mundo可以做什么？

我们工作生活中有各种各样的流程，比如最常见的请假流程，可能只需要一个人审批。也有特定业务的复杂流程，比如零售行业
中供应商向零售上申报商品的流程，可能需要十几个岗位审批，不同的业务条件审批的流程也是不同的，还可能为了加快审批的速度
还会增加并行审批的节点。以上涉及到的流程都可以交给mundo管理。

## 快速开始

1. 添加mundo依赖，mundo目前还没有发布到maven仓库，请手动下载

```xml
<dependency>
    <groupId>com.bluslee</groupId>
    <artifactId>mundo-process</artifactId>
    <version>last.version</version>
</dependency>
```

2. 定义流程

mundo目前支持XML定义流程，XML的格式示例如下，根标签为mundo，mundo标签可以有多个process标签，一个process就对应一个流程，
process标签有两个属性，id与name，id是流程的唯一标识，不同的流程id不可以重复。一个process标签可以有多个子标签，比如start标签代表流程的开始，
一个完整的流程必须从start开始到end结束，接下来是activity标签代表流程中业务节点，比如示例中的“供应商创建单据”等，当遇到有分支的情况，比如审批
这个时候我们就需要网关标签，代表后续出现分支，比如示例的的审批，只会出现两种通过或者不通过，这个时候我就需要排他网关exclusiveGateway，流程中
只有业务节点还是不完整的，还需要一些连接，来明确节点与节点之间的流转，这就link标签，通过指定link标签的sourceId，targetId来描述业务节点的流转情况
由网关发出的link需要指定一个表达式，mundo会根据表达式以及参数计算出走哪一个link,process中所有的子标签都有id与name属性，同一个process中，不同的
标签id不可以重复。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<mundo xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://bluslee.com/POM/1.0.0" xsi:schemaLocation="http://bluslee.com/POM/1.0.0 http://bluslee.com/xsd/mundo-1.0.0.xsd">
    <process id="process-001" name="简单流程" version="0">
        <start id="START" name="开始"/>
        <activity id="SUP_CREATE" name="供应商创建单据"/>
        <activity id="SUP_SUBMIT" name="供应商提交单据"/>
        <activity id="BUYER_APPROVE" name="采购审批单据"/>
        <activity id="SUP_UPDATE" name="供应商修改单据"/>
        <exclusiveGateway id="BUYER-APPROVE-GATEWAY" name="采购审批"/>
        <link id="START_SUP_CREATE" name="START_SUP_CREATE" sourceId="START" targetId="SUP_CREATE"/>
        <link id="SUP_CREATE_SUP_SUBMIT" name="SUP_CREATE_SUP_SUBMIT" sourceId="SUP_CREATE" targetId="SUP_SUBMIT"/>
        <link id="SUP_SUBMIT_BUYER_APPROVE" name="SUP_SUBMIT_BUYER_APPROVE" sourceId="SUP_SUBMIT" targetId="BUYER_APPROVE"/>
        <link id="BUYER_APPROVE_BUYER-APPROVE-GATEWAY" name="BUYER_APPROVE_BUYER-APPROVE-GATEWAY" sourceId="BUYER_APPROVE" targetId="buyer-approve-gateway"/>
        <link id="BUYER-APPROVE-GATEWAY_SUP_UPDATE" name="BUYER-APPROVE-GATEWAY_SUP_UPDATE" sourceId="BUYER-APPROVE-GATEWAY" targetId="SUP_UPDATE">
            <conditionExpression>#approve == false</conditionExpression>
        </link>
        <link id="SUP_UPDATE_BUYER_APPROVE" name="SUP_UPDATE_BUYER-APPROVE-GATEWAY" sourceId="SUP_UPDATE" targetId="BUYER_APPROVE"/>
        <link id="BUYER-APPROVE-GATEWAY_END" name="BUYER-APPROVE-GATEWAY_END" sourceId="BUYER-APPROVE-GATEWAY" targetId="end">
            <conditionExpression>#approve == true</conditionExpression>
        </link>
        <end id="end" name="审批结束"/>
    </process>
</mundo>
```   

3. 启动流程

```java
package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.RepositoryBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.process.DefaultBootstrap;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;

/**
 * BaseBootstrapTest.
 * @author carl.che
 * @date 2021/11/9
 */
public class BaseBootstrapTest {

    @Test
    public void defaultConfiguratorTest() {
        //1. 设置配置文件的路径，属性名必须是mundo.xml-path
        Configuration configuration = new XmlConfiguration();
        configuration.setProperty("mundo.xml-path", "/mundo.cfg.xml");
        //2. 用Bootstrap的示例，传入配置构建Repository，调用build方法，这个时候配置器会根据配置解析XML，验证，加载定义的流程，返回 XML定义的流程的集合，即Repository
        Repository<BaseProcessNode> repository = Bootstrap.getInstance().build(configuration);
        Assert.assertNotNull(repository);
        //3. 从Repository查找流程,可以获取全部流程，也可以根据id,version进行查询，如果不传version默认返回最新版本的流程
        ProcessEngine<BaseProcessNode> processEngine001 = repository.getProcess("process-001");
        //4. 调用流程流程接口
        //4.1 获取当前流程id
        processEngine001.getId();
        //4.2 获取当前引擎版本号.
        processEngine001.getVersion();
        //4.3 根据id在当前流程中寻找对应的node
        BaseProcessNode node001 = processEngine001.getProcessNode("node-001");
        //4.4 根据当前节点，以及参数找出下一个节点.
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("#approve", true);
        ProcessNodeWrap<BaseProcessNode> nextProcessNodeWrap = processEngine001.getNextProcessNode(node001, paraMap);
        //4.5 获取下一个节点
        if (nextProcessNodeWrap.parallel()) {
            //下一个节点是并行行节可能返回多个节点
            Set<BaseProcessNode> parallelNodes = nextProcessNodeWrap.getParallelNodes();
        }else {
            //非并行节点
            BaseProcessNode nextNode = nextProcessNodeWrap.get();
        }
    }
}
```

## 为什么要选择mundo

目前市面上主流的流程引擎有Flowable，Activiti等，以Flowable为例，Flowable是一个基于BPMN规范，功能强大的框架， Flowable流程引擎可用于
部署BPMN 2.0流程定义（用于定义流程的行业XML标准），创建这些流程定义的流程实例，进行查询，访问运行中或历史的流程实例与相关数据，除此之外
还提供权限，表单，UI，流程版本管理，热部署，统计等功能。Flowable尽可能的提供了你所有可能需要的东西，这带来的并不全是好处，比如过高的学习成本
Flowable的核心是BPMN规范，这个规范本身就很复杂，BPMN2.0完整的介绍是一份500多页的PDF，BPMN规范中的概念对于常见的流程中可能根本用不到，
mundo关于流程的定义参考了BPMN规范，但尽可能的保持简单。Flowable是一个完整的应用，它需要自己的DB，因为它保存了所有有关流程的数据，包括运行时，
当你的业务中启动一个流程示例时，flowable对应也会有一个一对一的实例，这个流程的历史，表单数据，flowable都会保存，这样有些时候会帮你减少很多工作，
但是有些时候也会限制你的灵活性，你需要flowable提供的数据适配成自己业务需要的。mundo的设计原则是保持简单，职责单一，mundo核心的api只有2个，
mundo的思路与Flowable也有所不同，不论业务有多少个流程实例，mundo中一个流程定义只有一个流程实例，业务中流程实例只需要保存当前实例处于流程的位置
当流程节点需要进行下一步时，业务侧提供当前节点，业务参数，mundo根据流程的定义计算出下一步返回给业务侧。总结一下flowable是一个大的全的应用，而mundo
是一个简单高效的流程引擎组件。