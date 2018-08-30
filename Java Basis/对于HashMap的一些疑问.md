## HashMap的结构
数组的寻址快，但是数据的插入与删除速度不行。 链表的插入与删除速度快，但是寻址速度不行。 那有没有一种两者兼具的数据结构，答案肯定是有的，那就是hash表。 HashMap 就是根据 数组+链表的方式组成了hash表：

## 对于HashMap的一些疑问
一、HashMap的resize过程是什么样的？（rehash）
HashMap在put的时候会先检查当前数组的length,如果插入新的值的时候使得length > 0.75f * size（f 为加载因子，可以在创建hashMap时指定）的话，会将数组进行扩容为当前容量的2倍。 扩容之后必定要将原有hashMap 中的值拷贝到新容量的hashMap 里面，HashMap 默认的容量为16，加载因子为0.75， 也就是说当HashMap 中Entry的个数超过 16 * 0.75 = 12时, 会将容量扩充为 16 * 2 = 32，然后重新计算元素在数组中的位置，这是一个非常耗时的操作，所以我们在使用HashMap的时候如果能预先知道Map中元素的大小，预设其大小能够提升其性能。 resize代码：

这是1.7中的代码，1.8中引入了红黑树的概念，代码会相对复杂一些。

二、HashMap在扩容的时候为什么容量都是原来的2倍，即容量为2^n
HashMap 在计算数组中key的位置时，使用的算法为：
/* * Returns index for hash code h. */
static int indexFor(int h, int length) {
// assert Integer.bitCount(length) == 1 : “length must be a non-zero power of 2”; return h & (length-1); }

即对key的hashcode 与当前数组容量 -1 进行与操作 我们假设有一个容量为分别为15 和 16 的hashMap ，有两个key的hashcode 分别为4和5，进行indexFor操作之后：

H & (length -1) hash & table.length-1 4 & (15 - 1) 0100 & 1110 = 0100 5 & （ 15 -1 ） 0101 & 1110 = 0100
4 & (16 - 1) 0100 & 1111 = 0100 5 & （ 16 -1 ） 0101 & 1111 = 0101

我们能够看到在容量为16时进行indexFor操作之后获得相同结果的几率要比容量为15时的几率要小，这样能够减少出现hash冲突的几率，从而提高查询效率。2 ^ n是一个非常神奇的数字。

三、put时出现相同的hashcode会怎样？
hashMap 里面存储的Entry对象是由数组和链表组成的，当key的hashcode相同时，数组上这个位置存储的结构就是链表，这时会将新的值插入链表的表头。进行取值的时候会先获取到链表，再对链表进行遍历，通过key.equals方法获取到值。（hashcode相同不代表对象相同，不要混淆hashcode和equals方法） 所以声明作final的对象，并且采用合适的equals()和hashCode()方法的话，将会减少碰撞的发生，提高效率。不可变性使得能够缓存不同键的hashcode，这将提高整个获取对象的速度，使用String，Interger这样的wrapper类作为键是非常好的选择。

四、什么是循环链表？
HashMap在遇到多线程的操作中，如果需要重新调整HashMap的大小时，多个线程会同时尝试去调整HashMap的大小，这时处在同一位置的链表的元素的位置会反过来，以为移动到新的bucket的时候，HashMap不会将新的元素放到尾部（为了避免尾部遍历），这时可能会出现A -> B -> A的情况，从而出现死循环，这便是HashMap中的循环链表。 所以HashMap 是不适合用在多线程的情况下的，可以考虑尝试使用HashTable 或是 ConcurrentHashMap

五、如何正确使用HashMap提高性能
在设置HashMap的时候指定其容量的大小，减少其resize的过程。

六、JDK1.8对HashMap进行了哪些优化
jdk1.8在对hash冲突的key时，如果此bucket位置上的元素数量在10以下时，还是和原来一样使用链表来进行存储，这时寻址的时间复杂度为O(n),当元素数量超过10时，使用红黑树进行代替，这时寻址的时间复杂度为O(n)

七、HashMap 与 HashTable、ConcurrentHashMap的区别
1.HashTable的方法是同步的，在方法的前面都有synchronized来同步，HashMap未经同步，所以在多线程场合要手动同步
2.HashTable不允许null值(key和value都不可以) ,HashMap允许null值(key和value都可以)。
3.HashTable有一个contains(Object value)功能和containsValue(Object value)功能一样。
4.HashTable使用Enumeration进行遍历，HashMap使用Iterator进行遍历。
5.HashTable中hash数组默认大小是11，增加的方式是 old*2+1。HashMap中hash数组的默认大小是16，而且一定是2的指数。
6.哈希值的使用不同，HashTable直接使用对象的hashCode，而HashMap重新计算hash值，用与代替求
7.ConcurrentHashMap也是一种线程安全的集合类，他和HashTable也是有区别的，主要区别就是加锁的粒度以及如何加锁，ConcurrentHashMap的加锁粒度要比HashTable更细一点。将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。