import com.casper.sdk.getdeploy.GetDeployParams
import com.casper.sdk.getdeploy.GetDeployRPC

fun main(args: Array<String>) {
    var getDeployParams:GetDeployParams = GetDeployParams()
    getDeployParams.deploy_hash = "6e74f836d7b10dd5db7430497e106ddf56e30afee993dd29b85a91c1cd903583";
    var str = getDeployParams.toJsonStr();
    var getDeployResult :GetDeployRPC = GetDeployRPC()
    getDeployResult.getDeployFromJsonStr(str)
}