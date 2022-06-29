package com.casper.sdk.serialization

import com.casper.sdk.CasperUtils
import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.getdeploy.Approval
import com.casper.sdk.getdeploy.Deploy
import com.casper.sdk.getdeploy.DeployHeader
//This class provides the serialization for Deploy object
class DeploySerializeHelper {

    companion object {
        /**
        Serialization for the Deploy Header
        Rule for serialization:
        Returned result = deployHeader.account + U64.serialize(deployHeader.timeStampMiliSecondFrom1970InU64) + U64.serialize(deployHeader.ttlMilisecondsFrom1980InU64) + U64.serialize(gas_price) + deployHeader.bodyHash
         */
        fun serializeForHeader(header:DeployHeader):String {
            val ret:String
            val timeStampMiliSeconds = CasperUtils.fromTimeStampToU64Str(header.timeStamp)
            val ttlMiliSeconds = CasperUtils.ttlToMilisecond(header.ttl)
            val clParseTimeStamp = CLParsed()
            clParseTimeStamp.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
            clParseTimeStamp.itsValueInStr = timeStampMiliSeconds.toString()
            val timeStampSerialization:String = CLParseSerialization.serializeFromCLParse(clParseTimeStamp)
            val clParseTTL = CLParsed()
            clParseTTL.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
            clParseTTL.itsValueInStr  = ttlMiliSeconds.toString()
            val ttlSerialization:String = CLParseSerialization.serializeFromCLParse(clParseTTL)
            val clParseGasPrice = CLParsed()
            clParseGasPrice.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
            clParseGasPrice.itsValueInStr  = header.gasPrice.toString()
            val gasPriceSerialization:String = CLParseSerialization.serializeFromCLParse(clParseGasPrice)
            var dependencySerialization : String
            val totalDependency:Int = header.dependencies.size
            val clDependency = CLParsed()
            clDependency.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
            clDependency.itsValueInStr = totalDependency.toString()
            dependencySerialization = CLParseSerialization.serializeFromCLParse(clDependency)
            if(totalDependency > 0) {
                for(i in 0..totalDependency-1) {
                    dependencySerialization = dependencySerialization + header.dependencies.get(i)
                }
            }
            val chainCLParse = CLParsed()
            chainCLParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
            chainCLParse.itsValueInStr = header.chainName
            val chainSerialization:String = CLParseSerialization.serializeFromCLParse(chainCLParse)
            ret = header.account + timeStampSerialization + ttlSerialization + gasPriceSerialization + header.bodyHash + dependencySerialization + chainSerialization
            return ret
        }
        /**
        Serialization for the Deploy Approvals
        Rule for serialization:
        If the approval list is empty, just return "00000000", which is equals to U32.serialize(0)
        If the approval list is not empty, then first get the approval list length, then take the prefixStr = U32.serialize(approvalList.length)
        Then concatenate all the elements in the approval list with rule for each element:
        1 element serialization = singer + signature
        Final result = prefix + (list.serialize)
         */
        fun serializeForDeployApproval(listApprovals:MutableList<Approval>):String {
            var ret:String
            val totalApproval:Int = listApprovals.size
            val clParse32 = CLParsed()
            clParse32.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
            clParse32.itsValueInStr = totalApproval.toString()
            ret = CLParseSerialization.serializeFromCLParse(clParse32)
            for(i in 0..totalApproval-1) {
                val oneA:Approval = listApprovals.get(i)
                ret = ret + oneA.signer + oneA.signature
            }
            return  ret
        }
        /*
        This function do the serialization for the whole deploy object.
        Input: a deploy object
        Output: the serialization of the deploy, built with the rule: header serialization + deploy.hash + payment serialization + session serialization + approval serialization
         */
        fun serializeForDeploy(deploy:Deploy):String {
            var ret:String = serializeForHeader(deploy.header)
            ret = ret + deploy.hash
            val paymentSerialization:String = ExecutableDeployItemSerializationHelper.serializeForExecutableDeployItem(deploy.payment)
            val sessionSerialization:String = ExecutableDeployItemSerializationHelper.serializeForExecutableDeployItem(deploy.session)
            val approvalSerialization:String = serializeForDeployApproval(deploy.approvals)
            ret = ret + paymentSerialization + sessionSerialization + approvalSerialization
            return ret
        }
    }
}