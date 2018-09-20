## MySql 小技巧

#### 1）修改默认时区

select now(); 查看 MySql 系统时间。和当前时间做对比

set global time_zone = '+8:00';设置时区更改为东八区

flush privileges; 刷新权限

#### 2）批量删除以字段开头的表

```
# 先查询
SELECT GROUP_CONCAT(table_name) FROM information_schema.tables WHERE table_schema='itstyle' AND table_name LIKE 'add%'
# 拷贝出来
DROP TABLE  add_student,add_teacher
```
