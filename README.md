# CSPR-Kotlin-SDK

Kotlin SDK library for interacting with a CSPR node.

## What is CSPR-Kotlin-SDK?

SDK  to streamline the 3rd party Kotlin client integration processes. Such 3rd parties include exchanges & app developers. 

## System requirement
The SDK is built with Kotlin version 1.5
JVM version 1.8
## Build and test

The package can be built and tested from IntelliJ IDEA and from command line

### Build and test in IntelliJ IDEA

Download the latest IntelliJ IDEA from https://www.jetbrains.com/idea/

The SDK is developed with IntelliJ IDEA 2021.3.3 (Community Edition). Then a Community Edition is good enough to test the SDK.

Download or clone the code from github,  then open it with IntelliJ IDEA.

To build the project, in IntelliJ IDEA, hit "Build->Build Project" as in the image below

<img width="1440" alt="Screen Shot 2022-04-18 at 20 31 31" src="https://user-images.githubusercontent.com/94465107/163815577-87bddf54-9489-4cb0-b627-e8a95680f3cf.png">

### Build and test in command line

In order to build and test the sdk, you need to install Java JDK and Maven.

#### Install Java JDK

This step include the work of installing JDK and setting up the path in local system environment variable.

The detail process of this stepp can be done with the manual in this address https://www.geeksforgeeks.org/setting-environment-java/

Here is a brief instruction: (For Windows)
1) Download the JDK at this address: https://www.oracle.com/java/technologies/downloads/, depends on which operating system you are using, choose the corresponding download (Windows, Mac, Linux)
2) Run the already downloaded JDK .exe file (for example "jdk-18_windows-x64_bin.exe" to setup the JDK.
3) Open the "Environment Variables" setting

<img width="300" alt="step1" src="https://user-images.githubusercontent.com/94465107/163914481-89014e5c-2164-41d3-99ce-ecf8c5df73ce.png">

4) Double click on the "Path" line or Click on "Path" line and then hit the "Edit" button

<img width="438" alt="step2" src="https://user-images.githubusercontent.com/94465107/163925113-acd82c87-333c-404f-90e6-513a760ce523.png">

5) Click New and enter the path for the jdk bin folder, in this example "C:\Program Files\Java\jdk-18\bin" (in general the default location will be this value, but if your folder is different, please enter the bin folder address for the sdk)

<img width="377" alt="step3" src="https://user-images.githubusercontent.com/94465107/163926358-1d145c73-02ff-4dd5-9130-7c545abb0c50.png">


Click OK several times to close the "Environment Variables" setting window, then open the "Command prompt" progame in Windows, type in this command:

"javac -version". You will see that java is running on your machine, somehow the result will be "javac 18" if you install JDK 18

#### Install Maven
Follow these step in this address to install Maven

https://maven.apache.org/install.html

Once you have maven install successfully in your machine, type this in the command prompt to check for it
mvn -v

To build the SDK with Maven

Enter the SDK root folder, then run the following command

```Kotlin
mvn clean package
```

Then 

```Kotlin
mvn -B package --file pom.xml
```

### Build and test using Github script

You can build and test the package with Github script in the "Action" section. Hit the "Action" section of this SDK and choose the latest Work Flow, for example with the below image, hit the green line "Update CONTRIBUTING.md"

<img width="1440" alt="Screen Shot 2022-04-18 at 21 37 33" src="https://user-images.githubusercontent.com/94465107/163824425-510fd77a-9c34-42c3-b3e1-82f676e1983f.png">

Then hit the "Build" button in the next screen

<img width="1440" alt="Screen Shot 2022-04-18 at 21 37 52" src="https://user-images.githubusercontent.com/94465107/163824912-c92d47d3-b38c-4edf-8c72-64e45150ebcc.png">

In the next screen, hit the line "Build with Maven" and scroll down to see the full list of the test

<img width="1440" alt="Screen Shot 2022-04-18 at 21 38 18" src="https://user-images.githubusercontent.com/94465107/163824987-13178c63-a4e4-4f00-965a-5b4797064dc7.png">

# Documentation for classes and methods

* [List of classes and methods](./Docs/Help.md#list-of-rpc-methods)

  -  [Get State Root Hash](/Docs/Help.md#i-get-state-root-hash)

  -  [Get Peer List](./Docs/Help.md#ii-get-peers-list)

  -  [Get Deploy](./Docs/Help.md#iii-get-deploy)
  
  -  [Get Status](./Docs/Help.md#iv-get-status)
  
  -  [Get Block Transfers](./Docs/Help.md#v-get-block-transfers)
  
  -  [Get Block](./Docs/Help.md#vi-get-block)
  
  -  [Get Era Info By Switch Block](./Docs/Help.md#vii-get-era-info-by-switch-block)
  
  -  [Get Item](./Docs/Help.md#vii-get-item)
  
  -  [Get Dictionary Item](./Docs/Help.md#ix-get-dictionaray-item)
  
  -  [Get Balance](./Docs/Help.md#x-get-balance)
  
  -  [Get Auction Info](./Docs/Help.md#xi-get-auction-info)
 
 # Coding standard
 
 The code in the SDK follow the coding convention at this address
 
 https://kotlinlang.org/docs/coding-conventions.html#immutability
