通常情况下我们需要在两个Linux服务器之间拷贝文件，比如定时备份。


以博客为例，网站目录定时打包比如一周或者一个月，远程同步到备份服务器。


博客服务器输入一下命令，然后输入远程主机密码，即可进行同步拷贝：
```
scp -r /mnt/domains/blog.52itstyle.com_20181024.tar.gz  root@115.29.143.135:/home/backups/
```

如果想增量拷贝，我们可以使用rsync命令。


```
rsync -avz  /mnt/domains/blog.52itstyle.com  root@115.29.143.135:/home/backups/
```



