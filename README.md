# May Common Library (MCLib) [![](https://jitpack.io/v/602723113/May-Common-Library.svg)](https://jitpack.io/#602723113/May-Common-Library) [![Build status](https://ci.appveyor.com/api/projects/status/fi528yanx76w25qe?svg=true)](https://ci.appveyor.com/project/602723113/may-common-library) [![Build Status](https://travis-ci.org/602723113/May-Common-Library.svg?branch=dev)](https://travis-ci.org/602723113/May-Common-Library) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/e8b69ada84954b13a415981844a7e376)](https://www.codacy.com/app/602723113/May-Common-Library?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=602723113/May-Common-Library&amp;utm_campaign=Badge_Grade)
> A library based on BukkitAPI development, and give developer some useful feature  

[Wiki](https://github.com/602723113/May-Common-Library/wiki)  
### README Languages
- English
- [Chinese | 中文](https://github.com/602723113/May-Common-Library/blob/dev/README_zh_CN.md)

### Feature
> In _MCLib_ you can enjoy the following features
- Easy to use NMS
- Easy to use NBT
- Easy to use Tellraw
- Easy to use ServerPing
- Easy to use Book
- Easy to use 1.12 new features **Advancement**
- Easy to use Pager
- Easy to use Reflection
- A lot of Utils
- ...

### Use it as a dependency
#### Maven
Step 1. Add the JitPack repository to your build file
```
<repositories>
	<repository>
		  <id>jitpack.io</id>
		  <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Step 2. Add the dependency
```
<dependency>
    <groupId>com.github.602723113</groupId>
	  <artifactId>May-Common-Library</artifactId>
	  <version>1.0.0</version>
</dependency>
```
  
#### Gradle
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
		}
}
```
Step 2. Add the dependency
```
dependencies {
    implementation 'com.github.602723113:May-Common-Library:1.0.0'
}
```
### License
  - This project follows the MIT protocol  
  
### Compile
This project uses Maven for management  
Construction please use command  
```
  mvn clean install package
```
  
**Like this project? Why not put it a star**
