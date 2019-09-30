package network.matic.maticsdkkotlinexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    BalanceOfERC20.balanceOfERC20Example()      
//    DepositEther.depositEtherExample()
//    TransferERC20.transferERC20Example()()
//    DepositERC20.approveERC20Example()
//    DepositERC20.depositERC20Example()
//    WithdrawERC20.withdrawERC20Example()()
//    WithdrawComplete.withdrawCompleteExample()
//    SignTypedData.signTypedDataExample()
  }

}
