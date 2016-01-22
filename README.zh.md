# Scala Connect

一个简单的社交论坛app，提供以下支持

- 用户身份，注册，登录等
- 设定不同的主题
- 发布内容到主题
- 评论发布的内容
- ...


## 使用了

- Bootstrap css framework 3.3.6+
- AngularJS 1.x.x (maybe)
- ReactJs/Flux (maybe)
- Play framework 2.x
- Play Slick 1.x
- Slick 3.x
- MySQL

## 安装

- IDE, 推荐 Ultimate IDEA 带 Scala plugin and Nodejs plugin
- 通过 git clone, 或者直接下载 
- App 位于 /service/web 
- 前端设计等位于 /front 
- 文档位于 /doc 
- 一些跨语言的测试位于 /test
- 运行，在 /service/web 中, ``activator ~run``
- 运行测试, 在 /service/web 中, ``activator`` then ``test`` or ``test-only``
- 数据库的建立和数据初始化代码，位于/service/web/test/migration, 可使用 ``test-only migration.NNNNNN`` 运行
- 所有和 HTTP 有关的代码位于  /service/web/app/process 
- 所有和数据提供有关的代码位于  /service/web/app/service folder
- 一些类位于 /service/web/app/core
- 有问题，发issue


## 如何贡献

请阅读，在做任何贡献之前

这是一个学习的项目，为了学习新的语言，所以学习始终是第一位的，不断的修改以采用更好的设计是首要的

我推荐大家分享自己的知识在wiki里面：

- 最佳实践，Play, Slick, or Scala
- The Play, Slick, Scala, 如何用
- 学习资源，如 Play, Slick and Scala

这样可以补充相互学习

##### 代码版权

- 请不要使用有没有版权的代码
- 你贡献的代码的版权归属你个人


##### 协议

- MIT

##### 风格

- 这是一个学习用的项目
- 没有任务的分派
- 请自己选择最佳的风格
- 多使用英语

##### 编码（待商讨）

- Fork 
- 开启一个新的issue，说明你要做的工作
- 编码测试你的修改
- 请告知测试的方法
- 提交pull 请求
- 在开启的issue里面告诉大家开发的状态，如已完成，如何测试等等

##### Issue的格式建议

```
## 告诉大家你要做什么

To add table NNN, and its model/service as well

## 告诉大家将有何种完成

And it will be

- To add the table/...
- ....
- ....
```

当完成之后，你可以继续说明目前的状态

```

## 比如，我添加了什么什么

Pull request #link of it

## 如何测试

The test case is in ..., ...
```

##### 友善

请用高雅的文字，文字中不要用脏字等，道德优先的原则，而不是自由优先的原则

请友善对待任何人，想象下，你在互联网上交流的对方可能是个小孩，保持友善



