package com.casper.sdk.putdeploy


import com.casper.sdk.CasperUtils
import com.casper.sdk.getdeploy.Deploy
import com.casper.sdk.serialization.ExecutableDeployItemSerializationHelper
import org.bouncycastle.crypto.digests.Blake2bDigest
import org.bouncycastle.jcajce.provider.digest.Blake2b.Blake2b256
import org.bouncycastle.util.encoders.Hex
import java.io.*


class PutDeployRPC {
    companion object {
        fun putDeploy(deploy: Deploy): Boolean {
            var ret: Boolean = true
            //val serialization:String = "00000000000100000006000000616d6f756e74050000000400ca9a3b08050400000006000000616d6f756e740500000004005ed0b2080600000074617267657421000000015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a16020000006964090000000100000000000000000d05070000007370656e6465722100000001dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b0b"
            val deployHash: String = getBodyHash(deploy)//CasperUtils.getBlake2bFromStr(serialization)
            println("Deploy hash is:" + deployHash)
            return ret
        }

        fun getBodyHash(deploy: Deploy): String {
            val paymentSerialization: String =
                ExecutableDeployItemSerializationHelper.serializeForExecutableDeployItem(deploy.payment)
            val sessionSerialization =
                ExecutableDeployItemSerializationHelper.serializeForExecutableDeployItem(deploy.session)
            println("payment:" + paymentSerialization)
            println("session:" + sessionSerialization)
            println("full body serialize:" + paymentSerialization + sessionSerialization)
            val deployHash: String = CasperUtils.getBlake2bFromStr(paymentSerialization + sessionSerialization)
            return deployHash
        }
    }
}