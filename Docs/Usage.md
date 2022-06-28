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

With this "CasperKotlinSDK.jar" file, you can import to other project to call for Casper RPC methods.

## Call RPC method from the exported "jar" file from the SDK.

Create a new project in IntelliJ IDEA


