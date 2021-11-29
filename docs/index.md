# [mundo - 轻量级，可扩展的流程引擎](http://www.bluslee.com)

**官方网站: http://www.bluslee.com**

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/sizzlecar/mundo/Java%20CI)
![License](https://img.shields.io/github/license/sizzlecar/mundo)
![GitHub stars](https://img.shields.io/github/stars/sizzlecar/mundo)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/sizzlecar/mundo)
![GitHub last commit](https://img.shields.io/github/last-commit/sizzlecar/mundo)

## mundo是什么？

mundo是一个轻量级，可扩展的流程引擎。

## mundo可以做什么？

我们工作生活中有各种各样的流程，比如公司的请假流程，银行的贷款流程，特定的业务流程。
1. 需要根据业务条件动态跳过某些流程中的节点
2. 同一个流程有不同的版本，同时存在
3. 涉及到复杂的串行与并行同时存在的流程
4. 需要对流程进行热加载(规划中)

以上涉及到的流程场景，全都可以交给mundo管理

## 快速开始

1. 添加mundo依赖，mundo目前还没有发布到maven仓库，请手动下载

```xml
<dependencys>
    <!--非spring环境-->
    <dependency>
        <groupId>com.bluslee</groupId>
        <artifactId>mundo-process</artifactId>
        <version>last.version</version>
    </dependency>

    <!--springboot-->
    <dependency>
        <groupId>com.bluslee</groupId>
        <artifactId>spring-boot-starter-mundo</artifactId>
        <version>last.version</version>
    </dependency>
</dependencys>
```

2. 定义流程

mundo目前支持XML定义流程,XML中允许出现的标签见下面的表格

|  标签   | 描述  | 属性  |  属性描述  |
|  ----  | ----  |  ----  | ----  |
|  mundo | XML配置文件的根标签，一个XML文件有且只能有一个 |   |    |
| process  | 流程标签，一个process标签就对应一个业务流程定义，一个mundo标签下，可以有多个process标签| id  |  流程的id与version构成流程的唯一键，一个XML文件内 process的id与version不能重复，否则会被覆盖 |
|   | | name  |  流程的名称 |
|   | | version  |  流程的版本号， 流程的id与version构成流程的唯一键，一个XML文件内 process标签的id与version不能重复，否则会被覆盖|
|  start | 开始标签，代表流程的开始 | id  | 标签的唯一标识，一个process标签内不能出现id重复的标签，一个process标签下可以出现多个 |
|   |  | name  | 标签的名称 |
|  activity | 活动标签，代表流程中活动，比如某人的审批，某人的修改等 | id  | 标签的唯一标识，一个process标签内不能出现id重复的标签，一个process标签下可以出现多个 |
|   |  | name  | 标签的名称 |
|  exclusiveGateway | 排他网关标签，代表流程下一步出现分支，根据条件只会进入其中一个分支 | id  | 标签的唯一标识，一个process标签内不能出现id重复的标签，一个process标签下可以出现多个 |
|   |  | name  | 标签的名称 |
|  parallelGateway | 并行网关标签，代表流程下一步出现分支，流程可以同时到达下一步的多个分子 | id  | 标签的唯一标识，一个process标签内不能出现id重复的标签，一个process标签下可以出现多个 |
|   |  | name  | 标签的名称 |
|  link | link标签，代表流程中各个节点的流向，流程中的每个节点都至少有一条link | id  | 标签的唯一标识，一个process标签内不能出现id重复的标签，一个process标签下可以出现多个 |
|   |  | name  | 标签的名称 |
|   |  | sourceId  | link的出发节点id |
|   |  | targetId  | link的目的节点id |
|  conditionExpression | 表达式标签，代表由出发节点到目的节点需要满足的条件，使用OGNL解析表达式 |   |  |
|  end | 结束标签，代表流程的终点 | id  | 标签的唯一标识，一个process标签内不能出现id重复的标签，一个process标签下可以出现多个 |
|   |  | name  | 标签的名称 |

如下是一个简单的完整的XML配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<mundo xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://bluslee.com/POM/1.0.0" xsi:schemaLocation="http://bluslee.com/POM/1.0.0 http://bluslee.com/xsd/mundo-1.0.0.xsd">
    <process id="process-001" name="简单流程" version="0">
        <start id="START" name="开始"/>
        <activity id="SUP_CREATE" name="供应商创建单据"/>
        <activity id="BUYER_APPROVE" name="采购审批单据"/>
        <activity id="SUP_UPDATE" name="供应商修改单据"/>
        <exclusiveGateway id="BUYER-APPROVE-GATEWAY" name="采购审批"/>
        <link id="START_SUP_CREATE" name="START_SUP_CREATE" sourceId="START" targetId="SUP_CREATE"/>
        <link id="SUP_CREATE_BUYER_APPROVE" name="SUP_CREATE_BUYER_APPROVE" sourceId="SUP_CREATE" targetId="BUYER_APPROVE"/>
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

3. 示例代码

- 非spring环境

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
    public void bootstrapTest() {
        //1. 设置配置文件的路径，属性名必须是mundo.xml-path
        Configuration configuration = new XmlConfiguration();
        configuration.setProperty(XmlConstants.ConfigKey.XML_PATH_CONFIG_NAME, "/mundo.cfg.xml");
        //2. 用Bootstrap的示例，传入配置构建Repository，调用build方法，这个时候配置器会根据配置解析XML，验证，加载定义的流程，返回 XML定义的流程的集合，即Repository
        Repository<BaseProcessNode> repository = Bootstrap.getInstance().build(configuration);
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
        //XML中表达式中出现的参数，使用OGNL解析
        paraMap.put("#approve", true);
        //4.5 获取下一个节点
        ProcessNodeWrap<BaseProcessNode> nextProcessNodeWrap = processEngine001.getNextProcessNode(node001, paraMap);
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

- springboot

```yaml
mundo:
  enabled: true
  xml:
    xml-path: /mundo.cfg.xml
```

```java
package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MundoStarterTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MundoStarterTest.class)
@SpringBootApplication
public class MundoStarterTest {

    @Autowired
    private Repository<? extends BaseProcessNode> repository;

    @Test
    public void test() {
        ProcessEngine<BaseProcessNode> processEngine001 = repository.getProcess("process-001");
        //1. 调用流程流程接口
        //1.1 获取当前流程id
        processEngine001.getId();
        //1.2 获取当前引擎版本号.
        processEngine001.getVersion();
        //1.3 根据id在当前流程中寻找对应的node
        BaseProcessNode node001 = processEngine001.getProcessNode("node-001");
        //1.4 根据当前节点，以及参数找出下一个节点.
        Map<String, Object> paraMap = new HashMap<>();
        //XML中表达式中出现的参数，使用OGNL解析
        paraMap.put("#approve", true);
        //1.5 获取下一个节点
        ProcessNodeWrap<BaseProcessNode> nextProcessNodeWrap = processEngine001.getNextProcessNode(node001, paraMap);
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

## 项目地址

[GitHub](https://github.com/sizzlecar/mundo)
[gitee](https://gitee.com/sizzle_carl/mundo)
