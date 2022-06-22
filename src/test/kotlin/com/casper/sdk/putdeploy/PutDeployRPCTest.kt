package com.casper.sdk.putdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.clvalue.CLType
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.crypto.Ed25519Handle
import com.casper.sdk.crypto.Secp256k1Handle
import com.casper.sdk.getdeploy.Approval
import com.casper.sdk.getdeploy.Deploy
import com.casper.sdk.getdeploy.DeployHeader
import com.casper.sdk.getdeploy.ExecutableDeployItem.*
import com.casper.sdk.serialization.DeploySerializeHelper
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

internal class PutDeployRPCTest {

    @Test
    fun  testAll() {
        testPutDeploy(isEd25519 = false)
    }
    fun testPutDeploy(isEd25519:Boolean) {
        val accountEd25519:String = "0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53"
        val accountSecp256k1:String = "0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d9635"
        val deploy:Deploy = Deploy()
        //Set up for header
        val deployHeader: DeployHeader = DeployHeader()
        if(isEd25519) {
            deployHeader.account = accountEd25519
        } else {
            deployHeader.account = accountSecp256k1
        }
        var current = LocalDateTime.now().toString()
        current = current.substring(0,current.length-3)
        current = current + "Z"
        println("Current Date and Time is: $current")
        //current = "2022-06-14T09:58:16.223Z"
       // deployHeader.timeStamp = "2020-11-17T00:39:24.072Z"
        deployHeader.timeStamp = current
        deployHeader.ttl = "1h 30m"
        deployHeader.gasPrice = 1u
       // deployHeader.bodyHash = "4811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f"
        //deployHeader.dependencies.add("0101010101010101010101010101010101010101010101010101010101010101")
        deployHeader.chainName = "casper-test"
        deploy.header = deployHeader
        // Setup for payment
        val payment: ExecutableDeployItem = ExecutableDeployItem()
        payment.itsType = ExecutableDeployItem.MODULE_BYTES
        val edi_mb: ExecutableDeployItem_ModuleBytes = ExecutableDeployItem_ModuleBytes()
        edi_mb.module_bytes = ""
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1st NamedArgs
        val oneNA: NamedArg = NamedArg()
        oneNA.itsName = "amount"
        val oneCLValue: CLValue = CLValue()
        val oneCLType: CLType = CLType()
        oneCLType.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValue.itsCLType = oneCLType
        oneCLValue.itsBytes = "0400ca9a3b"
        val oneCLParse: CLParsed = CLParsed()
        oneCLParse.itsCLType = oneCLType
        oneCLParse.itsValueInStr = "1000000000"
        oneCLValue.itsParse = oneCLParse
        oneNA.clValue = oneCLValue
        val ra: RuntimeArgs = RuntimeArgs()
        ra.listNamedArg.add(oneNA)
        edi_mb.args = ra
        payment.itsValue.add(edi_mb)
        deploy.payment = payment
        //Setup for session
        val session: ExecutableDeployItem = ExecutableDeployItem()
        session.itsType = ExecutableDeployItem.TRANSFER
        val ediSession: ExecutableDeployItem_Transfer = ExecutableDeployItem_Transfer()
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1st NamedArgs
        val oneNASession1: NamedArg = NamedArg()
        oneNASession1.itsName = "amount"
        val oneCLValueSession1: CLValue = CLValue()
        val oneCLTypeSession1: CLType = CLType()
        oneCLTypeSession1.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValueSession1.itsCLType = oneCLTypeSession1
        oneCLValueSession1.itsBytes = "04005ed0b2"
        val oneCLParseSession1: CLParsed = CLParsed()
        oneCLParseSession1.itsCLType = oneCLTypeSession1
        oneCLParseSession1.itsValueInStr = "3000000000"
        oneCLValueSession1.itsParse = oneCLParseSession1
        oneNASession1.clValue = oneCLValueSession1

        //setup 2nd NamedArgs
        val oneNASession2: NamedArg = NamedArg()
        oneNASession2.itsName = "target"
        val oneCLValueSession2: CLValue = CLValue()
        val oneCLTypeSession2: CLType = CLType()
        oneCLTypeSession2.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        oneCLValueSession2.itsCLType = oneCLTypeSession2
        oneCLValueSession2.itsBytes = "015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a"
        val oneCLParseSession2: CLParsed = CLParsed()
        oneCLParseSession2.itsCLType = oneCLTypeSession2
        oneCLParseSession2.itsValueInStr = "015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a"
        oneCLValueSession2.itsParse = oneCLParseSession2
        oneNASession2.clValue = oneCLValueSession2

        //setup 3rd NamedArgs - Option(U64(0))
        val oneNASession3: NamedArg = NamedArg()
        oneNASession3.itsName = "id"
        //clValue - Option(U64(0)) assignment
        val oneCLValueSession3: CLValue = CLValue()
        //CLType assignment
        val oneCLTypeSession3: CLType = CLType()
        oneCLTypeSession3.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLTypeSession3.innerCLType1 = CLType()
        oneCLTypeSession3.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLValueSession3.itsCLType = oneCLTypeSession3
        //bytes assignment
        oneCLValueSession3.itsBytes = "010000000000000000"
        //CLParse assignment
        val oneCLParseSession3: CLParsed = CLParsed()
        oneCLParseSession3.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLParseSession3.innerParsed1 = CLParsed()
        oneCLParseSession3.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLParseSession3.innerParsed1.itsValueInStr = "0"
        oneCLValueSession3.itsParse = CLParsed()
        oneCLValueSession3.itsParse = oneCLParseSession3
        oneNASession3.clValue = oneCLValueSession3
        // 4rd namedArg of type Key
        val oneNASession4: NamedArg = NamedArg()
        oneNASession4.itsName = "spender"
        val oneCLValueSession4: CLValue = CLValue()
        val oneCLTypeSession4: CLType = CLType()
        oneCLTypeSession4.itsTypeStr = ConstValues.CLTYPE_KEY
        oneCLValueSession4.itsCLType = oneCLTypeSession4
        oneCLValueSession4.itsBytes = "01dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        val oneCLParseSession4: CLParsed = CLParsed()
        oneCLParseSession4.itsCLType = oneCLTypeSession4
        oneCLParseSession4.itsValueInStr = "hash-dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        oneCLValueSession4.itsParse = oneCLParseSession4
        oneNASession4.clValue = oneCLValueSession4

        val raSession: RuntimeArgs = RuntimeArgs()
        raSession.listNamedArg.add(oneNASession1)
        raSession.listNamedArg.add(oneNASession2)
        raSession.listNamedArg.add(oneNASession3)
        raSession.listNamedArg.add(oneNASession4)
        ediSession.args = raSession
        session.itsValue.add(ediSession)
        deploy.session = session
        val deployBodyHash:String = Deploy.getBodyHash(deploy)
        println("Body hash is: " + deployBodyHash)
        deployHeader.bodyHash = deployBodyHash
        deploy.hash = Deploy.getDeployHash(deploy)
        println("Deploy hash is:" + deploy.hash)
        // Setup approvals
        val listApprovals:MutableList<Approval> = mutableListOf()
        val oneA: Approval = Approval()
        if(isEd25519) {
            oneA.signer = accountEd25519
            val privateKey: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile("KotlinEd25519PrivateKey.pem")
            var signature:String = Ed25519Handle.signMessage(deploy.hash,privateKey)
            signature = "01" + signature
            println("Signature ed25519 is:" + signature)
            oneA.signature = signature
        } else {
            oneA.signer = accountSecp256k1
            val fileName:String = "KotlinSecp256k1PrivateKey.pem"
            //val privateKeyStr:String = Secp256k1Handle.readPrivateKeyFromPemFile(fileName)
            // Secp256k1Handle.signMessage3("aa")
            oneA.signature = Secp256k1Handle.loadPemFile(fileName,deploy.hash)
            //oneA.signature = Secp256k1Handle.signMessage3(deploy.hash)
        }
        listApprovals.add(oneA)
        deploy.approvals = listApprovals
        PutDeployRPC.putDeploy(deploy)
    }
}