# Kotlin Casper SDK usage

To call for SDK RPC methods, the SDK is exported to "jar" file and can be imported from other Kotlin projects.

## Export SDK to "jar" file

To export the SDK hit "File->Project Structure ..."

<img width="1440" alt="Screen Shot 2022-06-28 at 15 55 19" src="https://user-images.githubusercontent.com/94465107/176137964-36e77e2e-7689-4a37-bb7a-19a65b5f0aeb.png">

Click the "Artifacts" row. Then give the jar a name. Choose the folder for the "jar" to store in the "Output directory" section. In this example the out put "jar" file will be in the "out/artifacts/CasperKotlinSDK_jar" folder under the SDK folder. You can change the folder to any folder you like. Remember that folder so that you can find the exported "jar" file later.

Click "Apply" and "OK"

<img width="1440" alt="Screen Shot 2022-06-28 at 15 51 37" src="https://user-images.githubusercontent.com/94465107/176138266-997b2cc6-4945-4290-bed0-895d6941bc21.png">

Click "Build->Build Artifacts..."

<img width="1440" alt="Screen Shot 2022-06-28 at 15 59 49" src="https://user-images.githubusercontent.com/94465107/176138957-7fe262de-702d-4842-a08d-6f83b631e9e2.png">

Then click the "Build" row.

<img width="1440" alt="Screen Shot 2022-06-28 at 16 00 37" src="https://user-images.githubusercontent.com/94465107/176139248-26a6a5d4-6f6a-46e1-a147-fd2ab187da26.png">

You can see the "jar" file is exported succcessfully

<img width="1212" alt="Screen Shot 2022-06-28 at 16 16 24" src="https://user-images.githubusercontent.com/94465107/176142387-b28e7ab8-1dd9-4005-be8a-d4b57dc03009.png">

Open "Terminal", go to the folder of the "CasperKotlinSDK.jar" file and type this command 

```Kotlin
zip -d CasperKotlinSDK.jar 'META-INF/.SF' 'META-INF/.RSA' 'META-INF/*SF'
```

You will see some result generated like this

<img width="633" alt="Screen Shot 2022-06-28 at 17 50 51" src="https://user-images.githubusercontent.com/94465107/176162004-fc3795d1-ff16-4d49-8b5f-b2b690625e47.png">

This is to make sure that some encryption being made in the "CasperKotlinSDK.jar" file is removed so that the SDK can be access without any restriction.

With this "CasperKotlinSDK.jar" file, you can import to other project to call for Casper RPC methods.

## Call RPC method from the exported "jar" file from the SDK.

Create a new project in IntelliJ IDEA. Make sure that "Kotlin" and "Maven" is selected, as the image below. Then click "Create" button.

<img width="804" alt="Screen Shot 2022-06-28 at 16 20 33" src="https://user-images.githubusercontent.com/94465107/176144564-6637df74-8991-4d12-b868-fbf40e716047.png">

Wait for a while for the project structure to fully loaded. Then right click on "kotlin" folder, choose "New-> Kotlin Class/File"

<img width="1440" alt="Screen Shot 2022-06-28 at 16 25 19" src="https://user-images.githubusercontent.com/94465107/176144643-46ddbe3b-4321-4bdd-b9b2-df02576aa045.png">

Give the file a name, for example "Main"

<img width="1440" alt="Screen Shot 2022-06-28 at 16 26 46" src="https://user-images.githubusercontent.com/94465107/176145189-2878af37-3b18-47f3-a757-fe4f9cad54fb.png">

Replace the content of the file to this

```Kotlin
fun  main(args:Array<String>) {
    println("Hello Casper Kotlin SDK")
}
```

Right click anywhere in the "Main.kt" file and choose "Run MainKt"

<img width="1440" alt="Screen Shot 2022-06-28 at 16 43 59" src="https://user-images.githubusercontent.com/94465107/176148284-ca261587-5366-4f95-b7c7-46b9fd97116b.png">

There will be line of "Hello Casper Kotlin SDK" printed out. The next step is to import the "CasperKotlinSDK.jar" file.

<img width="1440" alt="Screen Shot 2022-06-28 at 16 45 00" src="https://user-images.githubusercontent.com/94465107/176148609-6fa3f45e-d5a1-42ef-a9d0-01b74973a643.png">

### Import "CasperKotlinSDK.jar" file.

The first step is to import "net.jemzart" library for Json handle.

Open "pom.xml" file and paste the following content to the <dependencies> section

```Kotlin
<dependency>
    <groupId>net.jemzart</groupId>
    <artifactId>jsonkraken</artifactId>
    <version>1.2.0</version>
</dependency>
```

<img width="1440" alt="Screen Shot 2022-06-28 at 16 42 19" src="https://user-images.githubusercontent.com/94465107/176149123-50e929e0-be6c-4dab-92bf-c6587117452d.png">
 
Click "File-> Invalidate Caches ..."
    
    <img width="1440" alt="Screen Shot 2022-06-28 at 16 48 25" src="https://user-images.githubusercontent.com/94465107/176149304-be6a4170-6032-4039-abe2-f1bb3b7516cf.png">
    
 Check all the check boxes and click "Invalidate and Restart"

<img width="465" alt="Screen Shot 2022-06-28 at 16 35 08" src="https://user-images.githubusercontent.com/94465107/176149404-e7ea76e0-483d-4a46-a68e-a8a03aacd354.png">

Go to file "Main.kt" and type import net.jemzart, if you can see the hint appears, then you are import the net.jemzart successfully.
    
 <img width="1440" alt="Screen Shot 2022-06-28 at 16 52 19" src="https://user-images.githubusercontent.com/94465107/176150390-84dbbaf8-85a0-413b-a29d-1e2ec7754001.png">

Click File->Project Structure ...
    <img width="1440" alt="Screen Shot 2022-06-28 at 17 16 53" src="https://user-images.githubusercontent.com/94465107/176155836-8eb4ebcd-f495-4401-8693-6cd104f93fc4.png">

Click "Libraries" and add the "CasperKotlinSDK.jar" as in the image below
 
<img width="1440" alt="Screen Shot 2022-06-28 at 17 20 33" src="https://user-images.githubusercontent.com/94465107/176157536-fa291f48-c729-4d45-bb0c-d79698777bae.png">

Click the "+" button and browse to the CasperKotlinSDK.jar" file
    
<img width="1440" alt="Screen Shot 2022-06-28 at 17 24 14" src="https://user-images.githubusercontent.com/94465107/176157699-e34d26c1-c041-4145-a6cd-4034e3c04cb5.png">

Click "Open", you will then see the "CasperKotlinSDK.jar" file being imported. Click "Apply" then "OK"
    
    <img width="1440" alt="Screen Shot 2022-06-28 at 17 34 55" src="https://user-images.githubusercontent.com/94465107/176158874-719d682c-ce55-42cb-b7fc-c10a103f9f82.png">


Again click "File->Invalidate caches ..."
    
<img width="1440" alt="Screen Shot 2022-06-28 at 16 48 25" src="https://user-images.githubusercontent.com/94465107/176159042-c2df64b3-0e8e-45af-90d4-b0d2536a486f.png">
    
Check all the check boxes and click "Invalidate and Restart"
    
<img width="465" alt="Screen Shot 2022-06-28 at 16 35 08" src="https://user-images.githubusercontent.com/94465107/176159153-a0c7e07a-d30b-4e56-a4e0-a8f3941b2e1a.png">

Wait for a while for the project to load again.

Open the file "Main.kt" again, you can now import Casper Kotlin SDK classes, as shown in the image below.

<img width="1440" alt="Screen Shot 2022-06-28 at 17 40 27" src="https://user-images.githubusercontent.com/94465107/176159850-6416b943-ff10-4fc7-8fea-c6d7f3a75cee.png">

Copy the following code to do the sample work of "chain_get_state_root_hash" RPC call to the "Main.kt" file so that the content of the "Main.kt" file is like this:
    
```Kotlin   
import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import com.casper.sdk.getstateroothash.GetStateRootHashRPC

fun  main(args:Array<String>) {
    println("Hello Casper Kotlin SDK")
    getStateRootHash()
}

fun getStateRootHash() {
    val getStateRootHashTest = GetStateRootHashRPC()
    //Call 1:  Get state root hash with non parameter
    val bi = BlockIdentifier()
    bi.blockType = BlockIdentifierType.NONE
    val str: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
    try {
        val stateRootHash1 = getStateRootHashTest.getStateRootHash(str)
        println("stateRootHash1" + stateRootHash1)
    } catch (e: IllegalArgumentException) {}
    //Call 2:  Get state root hash with BlockIdentifier of type Block Hash with correct Block Hash
    bi.blockType = BlockIdentifierType.HASH
    bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
    val str2: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
    try {
        val stateRootHash2 = getStateRootHashTest.getStateRootHash(str2)
        println("stateRootHash2" + stateRootHash2)
    } catch (e: IllegalArgumentException){}
    //Call3:  Get state root hash with BlockIdentifier of type Block Height with correct Block Height
    bi.blockType = BlockIdentifierType.HEIGHT
    bi.blockHeight = 673033u
    val str3: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
    try {
        val stateRootHash3 = getStateRootHashTest.getStateRootHash(str3)
        println("stateRootHash3:" + stateRootHash3)
    } catch (e: IllegalArgumentException){}

    //Negative test

    //Call 4:  Get state root hash with BlockIdentifier of type Block Hash with incorrect Block Hash.
    // Expected result:  latest state root hash
    bi.blockType = BlockIdentifierType.HASH
    bi.blockHash = "aaa_fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
    val str4: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
    try {
        val stateRootHash4 = getStateRootHashTest.getStateRootHash(str4)
        println("stateRootHash4:" + stateRootHash4)
    } catch (e: IllegalArgumentException){}
    //Call5:  Get state root hash with BlockIdentifier of type Block Height with incorrect Block Height,  expected result:  Error
    bi.blockType = BlockIdentifierType.HEIGHT
    bi.blockHeight = 667303389999u
    try {
        val str5:  String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
        getStateRootHashTest.getStateRootHash(str5)
    } catch (e: IllegalArgumentException) {
        println("Error Get State Root Hash,  invalid parameter")
    }
}
```
    
You will see the state root hash being retrieved and printed out in the log region.
    
<img width="1440" alt="Screen Shot 2022-06-28 at 17 55 43" src="https://user-images.githubusercontent.com/94465107/176162501-71b41b02-cb82-467b-b56b-37edd887efec.png">

