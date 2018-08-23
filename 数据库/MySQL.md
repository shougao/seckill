## 注意事项

-  在创建表的时候如果在业务中能保证非null的字段，建议明确标示not null 因为mysql中对null需要特殊的标示。使用not null 字段更节省空间。对接下来的索引构建也有好处。

-  count(*) 和count(id) id 代表某个字段。在mysql中count(*)会把null统计进去、而count(id) 不会。如果统计的字段中含有null，这个两个统计的结果是不同的。

-  在sql语句等号左边用函数，会使该查询在该字段无法使用索引。如LENGTH(str) 函数。

-  索引也是需要存储到物理空间的，经常增删的表不适合建太多的索引，因为索引的维护会很耗时间。一张表最多建立15个索引。索引的长度越小越好，索引是有序的。如果查询Max（）之类用索引的话，连表都不用查询了，快得飞起。

-   mysql中null不参与比较运算，name <>'小米' 得出的结果中不包含 name=null的情况。在业务不能保证某字段是否为null的情况，写代码的时候需要注意null的坑。保证取得的数据是对而全，然后再考虑查询速率问题。

-  MySQL InnoDB默认行级锁。行级锁都是基于索引的，如果一条SQL语句用不到索引是不会使用行级锁的，会使用表级锁把整张表锁住，这点需要注意。

## 教程

### 文字教程

[MySQL 教程（菜鸟教程）](http://www.runoob.com/mysql/mysql-tutorial.html)

[MySQL教程（易百教程）](https://www.yiibai.com/mysql/)

###  视频教程

 [MySQL开发技巧（一）](https://www.imooc.com/learn/398)　　
 [MySQL开发技巧（二）](https://www.imooc.com/learn/427)　　
 [MySQL开发技巧（三）](https://www.imooc.com/learn/449)

 [MySQL5.7版本新特性](https://www.imooc.com/learn/533)　　
 [性能优化之MySQL优化](https://www.imooc.com/learn/194)

[MySQL集群（PXC）入门](https://www.imooc.com/learn/993)　　
[MyCAT入门及应用](https://www.imooc.com/learn/951)
