package com.casper.sdk.serialization

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.clvalue.CLType
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.Approval
import com.casper.sdk.getdeploy.Deploy
import com.casper.sdk.getdeploy.DeployHeader
import com.casper.sdk.getdeploy.ExecutableDeployItem.*
import org.junit.jupiter.api.Test

internal class DeploySerializeHelperTest {
    @Test
    fun testAll() {
        testDeployHeaderSerialization()
        testDeployApprovalSerialization()
        testDeploySerialization()
    }
    fun testDeploySerialization() {
        val deploy = Deploy()
        //Set up for header
        val deployHeader = DeployHeader()
        deployHeader.account = "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"
        deployHeader.timeStamp = "2020-11-17T00:39:24.072Z"
        deployHeader.ttl = "1h"
        deployHeader.gasPrice = 1u
        deployHeader.bodyHash = "4811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f"
        deployHeader.dependencies.add("0101010101010101010101010101010101010101010101010101010101010101")
        deployHeader.chainName = "casper-example"
        deploy.header = deployHeader
        // Setup for payment
        val payment = ExecutableDeployItem()
        payment.itsType = ExecutableDeployItem.STORED_CONTRACT_BY_NAME
        val edi_mb = ExecutableDeployItem_StoredContractByName()
        edi_mb.itsName = "casper-example"
        edi_mb.entryPoint = "example-entry-point"
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1 NamedArgs
        val oneNA = NamedArg()
        oneNA.itsName = "quantity"
        val oneCLValue = CLValue()
        val oneCLType = CLType()
        oneCLType.itsTypeStr = ConstValues.CLTYPE_I32
        oneCLValue.itsCLType = oneCLType
        val oneCLParse = CLParsed()
        oneCLParse.itsCLType = oneCLType
        oneCLParse.itsValueInStr = "1000"
        oneCLValue.itsParse = oneCLParse
        oneNA.clValue = oneCLValue
        val ra = RuntimeArgs()
        ra.listNamedArg.add(oneNA)
        edi_mb.args = ra
        payment.itsValue.add(edi_mb)
        deploy.payment = payment
        //Setup for session
        val session = ExecutableDeployItem()
        session.itsType = ExecutableDeployItem.TRANSFER
        val ediSession = ExecutableDeployItem_Transfer()
        //set up RuntimeArgs with 1 element of NamedArg only
        //setup 1 NamedArgs
        val oneNASession = NamedArg()
        oneNASession.itsName = "amount"
        val oneCLValueSession = CLValue()
        val oneCLTypeSession = CLType()
        oneCLTypeSession.itsTypeStr = ConstValues.CLTYPE_I32
        oneCLValueSession.itsCLType = oneCLTypeSession
        val oneCLParseSession = CLParsed()
        oneCLParseSession.itsCLType = oneCLTypeSession
        oneCLParseSession.itsValueInStr = "1000"
        oneCLValueSession.itsParse = oneCLParseSession
        oneNASession.clValue = oneCLValueSession
        val raSession = RuntimeArgs()
        raSession.listNamedArg.add(oneNASession)
        ediSession.args = raSession
        session.itsValue.add(ediSession)
        deploy.session = session
        // Setup approvals
        val listApprovals:MutableList<Approval> = mutableListOf()
        val oneA = Approval()
        oneA.signer = "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"
        oneA.signature = "012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08"
        listApprovals.add(oneA)
        deploy.approvals = listApprovals
        deploy.hash = "01da3c604f71e0e7df83ff1ab4ef15bb04de64ca02e3d2b78de6950e8b5ee187"
        val deploySerialization:String = DeploySerializeHelper.serializeForDeploy(deploy)
        assert(deploySerialization == "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900ca856a4d37501000080ee36000000000001000000000000004811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f0100000001010101010101010101010101010101010101010101010101010101010101010e0000006361737065722d6578616d706c6501da3c604f71e0e7df83ff1ab4ef15bb04de64ca02e3d2b78de6950e8b5ee187020e0000006361737065722d6578616d706c65130000006578616d706c652d656e7472792d706f696e7401000000080000007175616e7469747904000000e803000001050100000006000000616d6f756e7404000000e8030000010100000001d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08")

    }
    fun testDeployApprovalSerialization() {
        val listApprovals:MutableList<Approval> = mutableListOf()
        //Test for Approval list with 1 element
        val oneA = Approval()
        oneA.signer = "0149bae8d79362d088197f8f68c5e8431fa074f780d290a2b1988b7201872cb5bc"
        oneA.signature = "012d56b8a5620326484df6cdf770e6d2e054e64deabba5c4f34573bd6331239f8a4bfe2db9c14ec19b0cb4d6694bb3c1727e3d33501187391cfd47136840117b04"
        listApprovals.add(oneA)
        val approvalSerialization:String = DeploySerializeHelper.serializeForDeployApproval(listApprovals)
        assert(approvalSerialization == "010000000149bae8d79362d088197f8f68c5e8431fa074f780d290a2b1988b7201872cb5bc012d56b8a5620326484df6cdf770e6d2e054e64deabba5c4f34573bd6331239f8a4bfe2db9c14ec19b0cb4d6694bb3c1727e3d33501187391cfd47136840117b04")
        //Test for Approval list with 2 elements
        val listApproval2:MutableList<Approval> = mutableListOf()
        val oneA1 = Approval()
        oneA1.signer = "013b665cdf8447cec49c26ee66ae80e749a39c103b085e7229a23e0a21f913d9a7"
        oneA1.signature = "017c47aaf78b6d086327c480ab5fde436a2646fd3030c48b764c8c5636379af69176a666f2bc8996dbb32f7413028a5c7387f73336dfcec2588e65eae37c4b2204"
        val oneA2 = Approval()
        oneA2.signer = "01282d57031a3c5a2acd33eb3fe75b218150d46f2272f6dfc31acd9b41253fbc29"
        oneA2.signature = "012ff0833208a80630ca24b7ade59402479df8e50cc5539d48f065728cc156a064685befd8149df81c2af04bd1120ce11f906880f040e0906c19808be02f626b01"
        listApproval2.add(oneA1)
        listApproval2.add(oneA2)
        val approvalSerialization2:String = DeploySerializeHelper.serializeForDeployApproval(listApproval2)
        assert(approvalSerialization2 == "02000000013b665cdf8447cec49c26ee66ae80e749a39c103b085e7229a23e0a21f913d9a7017c47aaf78b6d086327c480ab5fde436a2646fd3030c48b764c8c5636379af69176a666f2bc8996dbb32f7413028a5c7387f73336dfcec2588e65eae37c4b220401282d57031a3c5a2acd33eb3fe75b218150d46f2272f6dfc31acd9b41253fbc29012ff0833208a80630ca24b7ade59402479df8e50cc5539d48f065728cc156a064685befd8149df81c2af04bd1120ce11f906880f040e0906c19808be02f626b01")
    }
    fun testDeployHeaderSerialization() {
        val deployHeader = DeployHeader()
        deployHeader.account = "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"
        deployHeader.timeStamp = "2020-11-17T00:39:24.072Z"
        deployHeader.ttl = "1h"
        deployHeader.gasPrice = 1u
        deployHeader.bodyHash = "4811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f"
        deployHeader.dependencies.add("0101010101010101010101010101010101010101010101010101010101010101")
        deployHeader.chainName = "casper-example"
        val deployHeaderSerialization:String = DeploySerializeHelper.serializeForHeader(deployHeader)
        assert(deployHeaderSerialization == "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900ca856a4d37501000080ee36000000000001000000000000004811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f0100000001010101010101010101010101010101010101010101010101010101010101010e0000006361737065722d6578616d706c65")
    }
}