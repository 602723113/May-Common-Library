# May Common Library (MCLib) [![](https://jitpack.io/v/602723113/May-Common-Library.svg)](https://jitpack.io/#602723113/May-Common-Library) [![Build status](https://ci.appveyor.com/api/projects/status/fi528yanx76w25qe?svg=true)](https://ci.appveyor.com/project/602723113/may-common-library) [![Build Status](https://travis-ci.org/602723113/May-Common-Library.svg?branch=dev)](https://travis-ci.org/602723113/May-Common-Library) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/e8b69ada84954b13a415981844a7e376)](https://www.codacy.com/app/602723113/May-Common-Library?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=602723113/May-Common-Library&amp;utm_campaign=Badge_Grade)
> 一个基于BukkitAPI开发的类库  

[Wiki](https://github.com/602723113/May-Common-Library/wiki)  
### 特性
> 在 _MCLib_ 中你可以享受以下功能
- 简易使用NMS
- 简易使用NBT
- 简易的使用Tellraw
- 简易的使用ServerPing
- 简易的使用Book
- 简易的使用1.12的新特性Advancement
- 简易的使用分页(Pager)
- 简易的使用反射工具类
- 封装了许许多多的Util
- ...

### 构建工具的配置
#### Maven
Step 1. 添加repository
```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Step 2. 添加dependency
```
<dependency>
    <groupId>com.github.602723113</groupId>
    <artifactId>May-Common-Library</artifactId>
    <version>1.0.0</version>
</dependency>
```
  
#### Gradle
Step 1. 添加repositories
```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
Step 2. 添加dependency
```
dependencies {
    implementation 'com.github.602723113:May-Common-Library:1.0.0'
}
```

### 协议  
  - 本项目遵循 MIT 协议  
  
### 编译
本项目使用 maven 进行管理  
构建请使用
```
  mvn clean install package
```
  
**喜欢这个项目?不妨给它留个star?**
