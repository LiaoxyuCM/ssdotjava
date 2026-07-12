# SSDOT

该文档基于最新版的ssdot

达成成就：用npm去跑一个Java的项目 lol \(见下\)

## 语法

### 注释

```txt
/comment_content/
```

两个`/`之间的内容会被当做注释忽略掉

### 输出字符

```txt
Hello, world!.
```

`.` 用于输出前面的所有字符并换行

### 转义

一共有三种特殊字符\(对于三个语法\)

- `.`
- `\`
- `/`

```txt
example\.com.
```

`\`用于转义下一个字符为普通字符\(包括转义它自己`\\`\)

> [!NOTE]
> 如果下一个字符不是特殊字符, 那转义了跟没转义一样, 浪费

> [!TIP]
> 若在注释内想表达一个`/`, 和注释外面的`/`转义方式不一样
> 使用`//` \(特性: 第一个`/`代表注释的结束, 第二个`/`代表另外一个注释的开始\)

### 示例

```txt
Slash (\/) is a special character, so we need/n't/ to escape it with a backslash (\\) to use it\..The/ first but not the/ second line!.
```

## 参数

### keepResidue和keepStatus

1. `keepResidue`可以把上一次运行结果中还没输出出来的字符先存起来, 等到下一次输出时再一起输出
```bash
java -jar ./ssdot-java.jar --keepResidue
java -jar ./ssdot-java.jar -kr
```
> [!TIP]
> 下文, 若无特殊说明, 还没输出出来的字符就叫做residue

2. `keepStatus`可以把上一次运行结果后的状态先存起来, 下一次运行时默认就是以这个状态开始运行
```bash
java -jar ./ssdot-java.jar --keepStatus
java -jar ./ssdot-java.jar -ks
```

以下是ssdot的状态表

| 状态       | 意义     | 内部使用   |
| ---------- | -------- | ---------- |
| 0 \(默认\) | 程序部分 | NORMAL     |
| 1          | 转义     | BACKSLASH  |
| 2          | 注释     | COMMENT    |

> [!NOTE]
> 如果你退出了这个程序, 状态和待输出文本照样都会丢失

> [!TIP]
> 判断是否启用keepResidue或keepStatus, 可以**逐行**运行下面这段代码\(先确保当前状态为`0`\)
```txt
Residue kept\. /
./Status kept\..
```

### trimEnd

```bash
java -jar ./ssdot-java.jar --trimEnd
java -jar ./ssdot-java.jar -te
```
这个功能可以将输出结果末尾的所有空格去除掉

### verbose

```bash
java -jar ./ssdot-java.jar --verbose
java -jar ./ssdot-java.jar -vb
```
如果你不去启用这个功能, 输入的时机见下

这个功能会在请求输入时额外弹出一个提示框

```txt
[X Y]>
```
其中：
- `X`代表当前residue的长度, 若没有启用`--keepResidue`则会显示`~`
- `Y`代表当前status对应的数字\(见上文status状态表\), 若没有启用`--keepStatus`则会显示`~`

### version

```bash
java -jar ./ssdot-java.jar --version
java -jar ./ssdot-java.jar -v
```
显示版本号

## 输入的时机

只要出现这个
```txt
Press Ctrl/Cmd + C to exit.
```
你就可以开始输入代码了

## 为什么用npm

就一个小项目, 没有任何依赖, 后面也不会经常更新

### 为什么不用Maven或者Gradle

我平日里很少写Java, 但经常写JavaScript, 所以npm对我来说更熟悉一些

Maven和Gradle我完全不会, 说真的

### 为什么不直接用bash或者ps1文件代替npm run

跑`npm run`的时候, 你完全不用担心你现在在哪个目录, 
因为它本来就是跑在项目根目录的, 并且跑完又切回来, 这样就不会有路径问题了
