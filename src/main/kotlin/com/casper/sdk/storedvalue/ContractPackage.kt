package com.casper.sdk.storedvalue

import com.casper.sdk.getdeploy.ExecutableDeployItem.ContractVersion
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import java.lang.Runtime.Version

class ContractPackage {
    var accessKey: String = ""
    var disabledVersions: MutableList<DisabledVersion> = mutableListOf()
    var groups: MutableList<Groups> = mutableListOf()
    var versions: MutableList<ContractVersion> = mutableListOf()
    companion object {
        fun  fromJsonObjectToContractPackage(from: JsonObject): ContractPackage {
            var ret: ContractPackage = ContractPackage()
            // Get AccessKey
            ret.accessKey = from["access_key"].toString()
            // Get DisableVersions
            val disabledVersions = from["disabled_versions"]
            if(disabledVersions != null) {
                val disabledVersionList = from["disabled_versions"] as JsonArray
                val totalDV: Int = disabledVersionList.count()-1
                if(totalDV >= 0) {
                    for(i in 0 ..totalDV) {
                        val oneDV: DisabledVersion = DisabledVersion.fromJsonObjectToDisabledVersion(disabledVersionList[i] as JsonObject)
                        ret.disabledVersions.add(oneDV)
                    }
                }
            }
            // Get Versions
            val versions = from["versions"]
            if(versions != null) {
                val versionList = from["versions"] as JsonArray
                val totalVersion :  Int = versionList.count() - 1
                if(totalVersion >=0) {
                    for(i in 0 .. totalVersion) {
                        val oneVersion:  ContractVersion = ContractVersion.fromJsonToContractVersion(versionList[i] as JsonObject)
                        ret.versions.add(oneVersion)
                    }
                }
            }
            // Get Groups
            val groups = from["groups"]
            if(groups != null) {
                val groupList = from["groups"] as JsonArray
                val totalGroup: Int = groupList.count() - 1
                if(totalGroup >= 0) {
                    for(i in 0..totalGroup) {
                        val oneGroup: Groups = Groups.fromJsonObjectToGroups(groupList[i] as JsonObject)
                        ret.groups.add(oneGroup)
                    }
                }
            }
            return ret
        }
    }
}