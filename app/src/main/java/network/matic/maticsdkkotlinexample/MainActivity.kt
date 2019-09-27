package network.matic.maticsdkkotlinexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
      
    SignTypedData.testSignTypedData()
//    BalanceOfERC20.testBalanceOfERC20()
//    DepositEther.testDepositEther()
//    TransferERC20.testTransferERC20()
//    DepositERC20.testApproveERC20()
//    DepositERC20.testDepositERC20()
//    WithdrawERC20.testWithdrawERC20()
//    WithdrawComplete.testWithdrawComplete()
  }

}
