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
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.junit.jupiter.api.Test

import java.time.LocalDateTime

//Test for account_put_deploy RPC call , with total of 8 test case
internal class PutDeployRPCTest {
    @Test
    fun  testAll() {
        //Positive path - put deploy for ed25519 account
        //Use this account 0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53
        val deployHashEd25519 = testPutDeploy(isEd25519 = true)
        println("Put deploy successfull with deploy hash Ed25519:" + deployHashEd25519);
        //Positive path - put deploy for secp256k1 account
        //Use this account 0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d9635
        val deployHashSecp256k1 = testPutDeploy(isEd25519 = false)
        println("Put deploy successfull with deploy hash Secp256k1:" + deployHashSecp256k1);
        assert(deployHashEd25519.length > 0)
        assert(deployHashSecp256k1.length > 0)
        //Negative path 1 - put deploy with wrong deploy hash - for ed25519 account
        val deploy1 = setupDeploy(isEd25519=true)
        //Try to make a wrong deploy by give it a fake deploy hash
        deploy1.hash = "66273a933cd78da040d83165398cff94cbd0122dd297d9f3867a35ae633fdbb2"
        val deployHash:String = PutDeployRPC.putDeploy(deploy1)
        assert(deployHash == ConstValues.PUT_DEPLOY_ERROR_MESSAGE)

        //Negative path 2 - put deploy with wrong deploy hash - for secp256k1 account
        val deploy2 = setupDeploy(isEd25519=false)
        //Try to make a wrong deploy by give it a fake deploy hash
        deploy2.hash = "62889cf406766131e99200acfd210290051e6391254b25411d265834c7ba8219"
        val deployHash2:String = PutDeployRPC.putDeploy(deploy2)
        assert(deployHash2 == ConstValues.PUT_DEPLOY_ERROR_MESSAGE)

        //Negative path 3 - put a transfer deploy for Ed25519 account, to a wrong public key address, stored in toTransferPublicKey input
        val deploy3:Deploy = setupDeploy2(isEd25519 = true, toTransferPublicKey = "bbaf92858ce861a3d47e3a8616f1918b4c68387c90d56aafc5efa49b71ebca32", amountToTransfer = "30000000")
        val deployHash3:String = PutDeployRPC.putDeploy(deploy3)
        assert(deployHash3 == ConstValues.PUT_DEPLOY_ERROR_MESSAGE)

        //Negative path 4 - put a transfer deploy for Secp256k1 account, to a wrong public key address, stored in toTransferPublicKey input
        val deploy4:Deploy = setupDeploy2(isEd25519 = false, toTransferPublicKey = "8cbd1b6924203fbff0015503a3e7f708c92ed4b1a303c544de5340b9bbf55c5c", amountToTransfer = "30000000")
        val deployHash4:String = PutDeployRPC.putDeploy(deploy4)
        assert(deployHash4 == ConstValues.PUT_DEPLOY_ERROR_MESSAGE)

        //Negative path 5 - put a transfer deploy for Ed25519 account, to a correct public key address, stored in toTransferPublicKey input, but wrong amount to send - too small amount to send
        //Try to send only 300, when the minimum amount is 2500000000
        val deploy5:Deploy = setupDeploy2(isEd25519 = true, toTransferPublicKey = "01fd708b1df7264949b649c423395f882ac7f35732116c989a0edfed3166fbb729", amountToTransfer = "300")
        val deployHash5:String = PutDeployRPC.putDeploy(deploy5)
        assert(deployHash5 == ConstValues.PUT_DEPLOY_ERROR_MESSAGE)

        //Negative path 6 - put a transfer deploy for Secp256k1 account, to a correct public key address, stored in toTransferPublicKey input, but wrong amount to send - too small amount to send
        //Try to send only 2500, when the minimum amount is 2500000000
        val deploy6:Deploy = setupDeploy2(isEd25519 = false, toTransferPublicKey = "01fd708b1df7264949b649c423395f882ac7f35732116c989a0edfed3166fbb729", amountToTransfer = "2500")
        val deployHash6:String = PutDeployRPC.putDeploy(deploy6)
        assert(deployHash6 == ConstValues.PUT_DEPLOY_ERROR_MESSAGE)
    }
    private fun setupDeploy2(isEd25519:Boolean, toTransferPublicKey:String, amountToTransfer:String) :Deploy {
        val accountEd25519 = "0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53"
        val accountSecp256k1 = "0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d9635"
        val deploy = Deploy()
        //Set up for header
        val deployHeader = DeployHeader()
        if(isEd25519) {
            deployHeader.account = accountEd25519
        } else {
            deployHeader.account = accountSecp256k1
        }
        var current = LocalDateTime.now().toString()
        current = current.substring(0,current.length-3)
        current = current + "Z"
        deployHeader.timeStamp = current
        deployHeader.ttl = "1h 30m"
        deployHeader.gasPrice = 1u
        deployHeader.chainName = "casper-test"
        deploy.header = deployHeader
        // Setup for payment
        val payment = ExecutableDeployItem()
        payment.itsType = ExecutableDeployItem.MODULE_BYTES
        val ediMB = ExecutableDeployItem_ModuleBytes()
        ediMB.module_bytes = ""
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1st NamedArgs
        val oneNA = NamedArg()
        oneNA.itsName = "amount"
        val oneCLValue = CLValue()
        val oneCLType = CLType()
        oneCLType.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValue.itsCLType = oneCLType
        oneCLValue.itsBytes = "0400ca9a3b"
        val oneCLParse = CLParsed()
        oneCLParse.itsCLType = oneCLType
        oneCLParse.itsValueInStr = "1000000000"
        oneCLValue.itsParse = oneCLParse
        oneNA.clValue = oneCLValue
        val ra = RuntimeArgs()
        ra.listNamedArg.add(oneNA)
        ediMB.args = ra
        payment.itsValue.add(ediMB)
        deploy.payment = payment
        //Setup for session
        val session = ExecutableDeployItem()
        session.itsType = ExecutableDeployItem.TRANSFER
        val ediSession = ExecutableDeployItem_Transfer()
        //set up RuntimeArgs with 4 elements of NamedArg
        //setup 1st NamedArgs
        val oneNASession1 = NamedArg()
        oneNASession1.itsName = "amount"
        val oneCLValueSession1 = CLValue()
        val oneCLTypeSession1 = CLType()
        oneCLTypeSession1.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValueSession1.itsCLType = oneCLTypeSession1
        oneCLValueSession1.itsBytes = "04005ed0b2"
        val oneCLParseSession1 = CLParsed()
        oneCLParseSession1.itsCLType = oneCLTypeSession1
        oneCLParseSession1.itsValueInStr = amountToTransfer
        oneCLValueSession1.itsParse = oneCLParseSession1
        oneNASession1.clValue = oneCLValueSession1

        //setup 2nd NamedArgs
        val oneNASession2 = NamedArg()
        oneNASession2.itsName = "target"
        val oneCLValueSession2 = CLValue()
        val oneCLTypeSession2 = CLType()
        oneCLTypeSession2.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        oneCLValueSession2.itsCLType = oneCLTypeSession2
        oneCLValueSession2.itsBytes = toTransferPublicKey
        val oneCLParseSession2 = CLParsed()
        oneCLParseSession2.itsCLType = oneCLTypeSession2
        oneCLParseSession2.itsValueInStr = toTransferPublicKey
        oneCLValueSession2.itsParse = oneCLParseSession2
        oneNASession2.clValue = oneCLValueSession2

        //setup 3rd NamedArgs - Option(U64(0))
        val oneNASession3 = NamedArg()
        oneNASession3.itsName = "id"
        //clValue - Option(U64(0)) assignment
        val oneCLValueSession3 = CLValue()
        //CLType assignment
        val oneCLTypeSession3 = CLType()
        oneCLTypeSession3.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLTypeSession3.innerCLType1 = CLType()
        oneCLTypeSession3.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLValueSession3.itsCLType = oneCLTypeSession3
        //bytes assignment
        oneCLValueSession3.itsBytes = "010000000000000000"
        //CLParse assignment
        val oneCLParseSession3 = CLParsed()
        oneCLParseSession3.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLParseSession3.innerParsed1 = CLParsed()
        oneCLParseSession3.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLParseSession3.innerParsed1.itsValueInStr = "0"
        oneCLValueSession3.itsParse = CLParsed()
        oneCLValueSession3.itsParse = oneCLParseSession3
        oneNASession3.clValue = oneCLValueSession3
        // 4th namedArg of type Key
        val oneNASession4 = NamedArg()
        oneNASession4.itsName = "spender"
        val oneCLValueSession4 = CLValue()
        val oneCLTypeSession4 = CLType()
        oneCLTypeSession4.itsTypeStr = ConstValues.CLTYPE_KEY
        oneCLValueSession4.itsCLType = oneCLTypeSession4
        oneCLValueSession4.itsBytes = "01dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        val oneCLParseSession4 = CLParsed()
        oneCLParseSession4.itsCLType = oneCLTypeSession4
        oneCLParseSession4.itsValueInStr = "hash-dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        oneCLValueSession4.itsParse = oneCLParseSession4
        oneNASession4.clValue = oneCLValueSession4

        val raSession = RuntimeArgs()
        raSession.listNamedArg.add(oneNASession1)
        raSession.listNamedArg.add(oneNASession2)
        raSession.listNamedArg.add(oneNASession3)
        raSession.listNamedArg.add(oneNASession4)
        ediSession.args = raSession
        session.itsValue.add(ediSession)
        deploy.session = session
        val deployBodyHash:String = Deploy.getBodyHash(deploy)
        deployHeader.bodyHash = deployBodyHash
        deploy.hash = Deploy.getDeployHash(deploy)
        // Setup approvals
        val listApprovals:MutableList<Approval> = mutableListOf()
        val oneA = Approval()
        if(isEd25519) {
            oneA.signer = accountEd25519
            val privateKey: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_ED25519)
            var signature:String = Ed25519Handle.signMessage(deploy.hash,privateKey)
            signature = "01" + signature
            oneA.signature = signature
        } else {
            oneA.signer = accountSecp256k1
            val privateKey:BCECPrivateKey = Secp256k1Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_SECP256k1)
            PutDeployUtils.privateKey = privateKey
            oneA.signature = "02" + Secp256k1Handle.signMessage(deploy.hash,privateKey)
        }
        listApprovals.add(oneA)
        deploy.approvals = listApprovals
        return deploy
    }
    private fun setupDeploy(isEd25519:Boolean) :Deploy {
        val accountEd25519 = "0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53"
        val accountSecp256k1 = "0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d9635"
        val deploy = Deploy()
        //Set up for header
        val deployHeader = DeployHeader()
        if(isEd25519) {
            deployHeader.account = accountEd25519
        } else {
            deployHeader.account = accountSecp256k1
        }
        var current = LocalDateTime.now().toString()
        current = current.substring(0,current.length-3)
        current = current + "Z"
        deployHeader.timeStamp = current
        deployHeader.ttl = "1h 30m"
        deployHeader.gasPrice = 1u
        deployHeader.chainName = "casper-test"
        deploy.header = deployHeader
        // Setup for payment
        val payment = ExecutableDeployItem()
        payment.itsType = ExecutableDeployItem.MODULE_BYTES
        val ediMB = ExecutableDeployItem_ModuleBytes()
        ediMB.module_bytes = ""
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1st NamedArgs
        val oneNA = NamedArg()
        oneNA.itsName = "amount"
        val oneCLValue = CLValue()
        val oneCLType = CLType()
        oneCLType.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValue.itsCLType = oneCLType
        oneCLValue.itsBytes = "0400ca9a3b"
        val oneCLParse = CLParsed()
        oneCLParse.itsCLType = oneCLType
        oneCLParse.itsValueInStr = "1000000000"
        oneCLValue.itsParse = oneCLParse
        oneNA.clValue = oneCLValue
        val ra = RuntimeArgs()
        ra.listNamedArg.add(oneNA)
        ediMB.args = ra
        payment.itsValue.add(ediMB)
        deploy.payment = payment
        //Setup for session
        val session = ExecutableDeployItem()
        session.itsType = ExecutableDeployItem.TRANSFER
        val ediSession = ExecutableDeployItem_Transfer()
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1st NamedArgs
        val oneNASession1 = NamedArg()
        oneNASession1.itsName = "amount"
        val oneCLValueSession1 = CLValue()
        val oneCLTypeSession1 = CLType()
        oneCLTypeSession1.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValueSession1.itsCLType = oneCLTypeSession1
        oneCLValueSession1.itsBytes = "04005ed0b2"
        val oneCLParseSession1 = CLParsed()
        oneCLParseSession1.itsCLType = oneCLTypeSession1
        oneCLParseSession1.itsValueInStr = "3000000000"
        oneCLValueSession1.itsParse = oneCLParseSession1
        oneNASession1.clValue = oneCLValueSession1

        //setup 2nd NamedArgs
        val oneNASession2 = NamedArg()
        oneNASession2.itsName = "target"
        val oneCLValueSession2 = CLValue()
        val oneCLTypeSession2 = CLType()
        oneCLTypeSession2.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        oneCLValueSession2.itsCLType = oneCLTypeSession2
        oneCLValueSession2.itsBytes = "015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a"
        val oneCLParseSession2 = CLParsed()
        oneCLParseSession2.itsCLType = oneCLTypeSession2
        oneCLParseSession2.itsValueInStr = "015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a"
        oneCLValueSession2.itsParse = oneCLParseSession2
        oneNASession2.clValue = oneCLValueSession2

        //setup 3rd NamedArgs - Option(U64(0))
        val oneNASession3 = NamedArg()
        oneNASession3.itsName = "id"
        //clValue - Option(U64(0)) assignment
        val oneCLValueSession3 = CLValue()
        //CLType assignment
        val oneCLTypeSession3 = CLType()
        oneCLTypeSession3.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLTypeSession3.innerCLType1 = CLType()
        oneCLTypeSession3.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLValueSession3.itsCLType = oneCLTypeSession3
        //bytes assignment
        oneCLValueSession3.itsBytes = "010000000000000000"
        //CLParse assignment
        val oneCLParseSession3 = CLParsed()
        oneCLParseSession3.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLParseSession3.innerParsed1 = CLParsed()
        oneCLParseSession3.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLParseSession3.innerParsed1.itsValueInStr = "0"
        oneCLValueSession3.itsParse = CLParsed()
        oneCLValueSession3.itsParse = oneCLParseSession3
        oneNASession3.clValue = oneCLValueSession3
        // 4th namedArg of type Key
        val oneNASession4 = NamedArg()
        oneNASession4.itsName = "spender"
        val oneCLValueSession4 = CLValue()
        val oneCLTypeSession4 = CLType()
        oneCLTypeSession4.itsTypeStr = ConstValues.CLTYPE_KEY
        oneCLValueSession4.itsCLType = oneCLTypeSession4
        oneCLValueSession4.itsBytes = "01dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        val oneCLParseSession4 = CLParsed()
        oneCLParseSession4.itsCLType = oneCLTypeSession4
        oneCLParseSession4.itsValueInStr = "hash-dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        oneCLValueSession4.itsParse = oneCLParseSession4
        oneNASession4.clValue = oneCLValueSession4

        val raSession = RuntimeArgs()
        raSession.listNamedArg.add(oneNASession1)
        raSession.listNamedArg.add(oneNASession2)
        raSession.listNamedArg.add(oneNASession3)
        raSession.listNamedArg.add(oneNASession4)
        ediSession.args = raSession
        session.itsValue.add(ediSession)
        deploy.session = session
        val deployBodyHash:String = Deploy.getBodyHash(deploy)
        deployHeader.bodyHash = deployBodyHash
        deploy.hash = Deploy.getDeployHash(deploy)
        // Setup approvals
        val listApprovals:MutableList<Approval> = mutableListOf()
        val oneA = Approval()
        if(isEd25519) {
            oneA.signer = accountEd25519
            val privateKey: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_ED25519)
            var signature:String = Ed25519Handle.signMessage(deploy.hash,privateKey)
            signature = "01" + signature
            oneA.signature = signature
        } else {
            oneA.signer = accountSecp256k1
            val privateKey:BCECPrivateKey = Secp256k1Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_SECP256k1)
            PutDeployUtils.privateKey = privateKey
            oneA.signature = "02" + Secp256k1Handle.signMessage(deploy.hash,privateKey)
        }
        listApprovals.add(oneA)
        deploy.approvals = listApprovals
        return deploy
    }

    //Positive test, put deploy with correct information for both Ed25519 and Secp256k1 account
    private fun testPutDeploy(isEd25519:Boolean) : String {
        val accountEd25519 = "0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53"
        val accountSecp256k1 = "0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d9635"
        val deploy = Deploy()
        //Set up for header
        val deployHeader = DeployHeader()
        if(isEd25519) {
            deployHeader.account = accountEd25519
        } else {
            deployHeader.account = accountSecp256k1
        }
        var current = LocalDateTime.now().toString()
        current = current.substring(0,current.length-3)
        current = current + "Z"
        deployHeader.timeStamp = current
        deployHeader.ttl = "1h 30m"
        deployHeader.gasPrice = 1u
        deployHeader.chainName = "casper-test"
        deploy.header = deployHeader
        // Setup for payment
        val payment = ExecutableDeployItem()
        payment.itsType = ExecutableDeployItem.MODULE_BYTES
        val ediMB = ExecutableDeployItem_ModuleBytes()
        ediMB.module_bytes = ""
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1st NamedArgs
        val oneNA = NamedArg()
        oneNA.itsName = "amount"
        val oneCLValue = CLValue()
        val oneCLType = CLType()
        oneCLType.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValue.itsCLType = oneCLType
        oneCLValue.itsBytes = "0400ca9a3b"
        val oneCLParse = CLParsed()
        oneCLParse.itsCLType = oneCLType
        oneCLParse.itsValueInStr = "1000000000"
        oneCLValue.itsParse = oneCLParse
        oneNA.clValue = oneCLValue
        val ra = RuntimeArgs()
        ra.listNamedArg.add(oneNA)
        ediMB.args = ra
        payment.itsValue.add(ediMB)
        deploy.payment = payment
        //Setup for session
        val session = ExecutableDeployItem()
        session.itsType = ExecutableDeployItem.TRANSFER
        val ediSession = ExecutableDeployItem_Transfer()
        //set up RuntimeArgs with 4 elements of NamedArg only
        //setup 1st NamedArg
        val oneNASession1 = NamedArg()
        oneNASession1.itsName = "amount"
        val oneCLValueSession1 = CLValue()
        val oneCLTypeSession1 = CLType()
        oneCLTypeSession1.itsTypeStr = ConstValues.CLTYPE_U512
        oneCLValueSession1.itsCLType = oneCLTypeSession1
        oneCLValueSession1.itsBytes = "04005ed0b2"
        val oneCLParseSession1 = CLParsed()
        oneCLParseSession1.itsCLType = oneCLTypeSession1
        oneCLParseSession1.itsValueInStr = "3000000000"
        oneCLValueSession1.itsParse = oneCLParseSession1
        oneNASession1.clValue = oneCLValueSession1

        //setup 2nd NamedArg
        val oneNASession2 = NamedArg()
        oneNASession2.itsName = "target"
        val oneCLValueSession2 = CLValue()
        val oneCLTypeSession2 = CLType()
        oneCLTypeSession2.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        oneCLValueSession2.itsCLType = oneCLTypeSession2
        oneCLValueSession2.itsBytes = "015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a"
        val oneCLParseSession2 = CLParsed()
        oneCLParseSession2.itsCLType = oneCLTypeSession2
        oneCLParseSession2.itsValueInStr = "015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a"
        oneCLValueSession2.itsParse = oneCLParseSession2
        oneNASession2.clValue = oneCLValueSession2

        //setup 3rd NamedArgs - Option(U64(0))
        val oneNASession3 = NamedArg()
        oneNASession3.itsName = "id"
        //clValue - Option(U64(0)) assignment
        val oneCLValueSession3 = CLValue()
        //CLType assignment
        val oneCLTypeSession3 = CLType()
        oneCLTypeSession3.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLTypeSession3.innerCLType1 = CLType()
        oneCLTypeSession3.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLValueSession3.itsCLType = oneCLTypeSession3
        //bytes assignment
        oneCLValueSession3.itsBytes = "010000000000000000"
        //CLParse assignment
        val oneCLParseSession3 = CLParsed()
        oneCLParseSession3.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
        oneCLParseSession3.innerParsed1 = CLParsed()
        oneCLParseSession3.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
        oneCLParseSession3.innerParsed1.itsValueInStr = "0"
        oneCLValueSession3.itsParse = CLParsed()
        oneCLValueSession3.itsParse = oneCLParseSession3
        oneNASession3.clValue = oneCLValueSession3
        // 4th namedArg of type Key
        val oneNASession4 = NamedArg()
        oneNASession4.itsName = "spender"
        val oneCLValueSession4 = CLValue()
        val oneCLTypeSession4 = CLType()
        oneCLTypeSession4.itsTypeStr = ConstValues.CLTYPE_KEY
        oneCLValueSession4.itsCLType = oneCLTypeSession4
        oneCLValueSession4.itsBytes = "01dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        val oneCLParseSession4 = CLParsed()
        oneCLParseSession4.itsCLType = oneCLTypeSession4
        oneCLParseSession4.itsValueInStr = "hash-dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"
        oneCLValueSession4.itsParse = oneCLParseSession4
        oneNASession4.clValue = oneCLValueSession4

        val raSession = RuntimeArgs()
        raSession.listNamedArg.add(oneNASession1)
        raSession.listNamedArg.add(oneNASession2)
        raSession.listNamedArg.add(oneNASession3)
        raSession.listNamedArg.add(oneNASession4)
        ediSession.args = raSession
        session.itsValue.add(ediSession)
        deploy.session = session
        val deployBodyHash:String = Deploy.getBodyHash(deploy)
        deployHeader.bodyHash = deployBodyHash
        deploy.hash = Deploy.getDeployHash(deploy)
        // Setup approvals
        val listApprovals:MutableList<Approval> = mutableListOf()
        val oneA = Approval()
        if(isEd25519) {
            oneA.signer = accountEd25519
            val privateKey: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_ED25519)
            var signature:String = Ed25519Handle.signMessage(deploy.hash,privateKey)
            signature = "01" + signature
            oneA.signature = signature
        } else {
            oneA.signer = accountSecp256k1
            val privateKey:BCECPrivateKey = Secp256k1Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_SECP256k1)
            PutDeployUtils.privateKey = privateKey
            oneA.signature = "02" + Secp256k1Handle.signMessage(deploy.hash,privateKey)
        }
        listApprovals.add(oneA)
        deploy.approvals = listApprovals
        val deployHash:String = PutDeployRPC.putDeploy(deploy)
        assert(deploy.hash == deployHash)
        return deployHash
    }
}