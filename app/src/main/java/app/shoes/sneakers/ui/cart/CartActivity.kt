package app.shoes.sneakers.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import app.shoes.sneakers.R
import app.shoes.sneakers.databinding.ActivityCartBinding
import app.shoes.sneakers.databinding.ActivityHomeBinding
import app.shoes.sneakers.db.entities.EntityShoes
import app.shoes.sneakers.ui.cart.viewmodel.CartViewModel
import app.shoes.sneakers.ui.details.DetailedActivity
import app.shoes.sneakers.ui.home.MainActivity
import app.shoes.sneakers.ui.home.adapter.ShoesListAdapter
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.BaseResponse
import app.shoes.sneakers.utils.LinearSpaceItemDecoration
import app.shoes.sneakers.utils.MyConstants
import app.shoes.sneakers.utils.setOnClickListenerDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : AppCompatActivity(),CartListener {

    private val mViewModel by viewModels<CartViewModel>()
    private lateinit var mBinding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.shimmer.startShimmer()
        implementView()
    }

    private fun implementView(){
        mViewModel.cartItems.observe(this) {
            mBinding.shimmer.stopShimmer()
            mBinding.shimmer.visibility=View.GONE
            implementCartItems(it)
        }

        mBinding.ivBack.setOnClickListenerDebounce { finish() }

        mBinding.btnCheckout.setOnClickListenerDebounce {
            val itemCount=(mBinding.rvItems.adapter as? CartAdapter)?.itemCount?:0
            if (itemCount<1) {
                Toast.makeText(this,"Please Add Item.",Toast.LENGTH_SHORT).show()
                return@setOnClickListenerDebounce
            }
            mViewModel.clearDatabase()
            lifecycleScope.launch {
                mBinding.checkoutView.visibility= View.VISIBLE
                var n=3
                while (n>0){
                    mBinding.tvTimer.text="Thanks! you are redirecting to home in $n sec"
                    delay(1000)
                    n--
                }
                finish()
                startActivity(Intent(this@CartActivity,MainActivity::class.java))
            }
        }
    }

    private fun implementCartItems(response: List<EntityShoes>){
        with(mBinding) {
            rvItems.apply {
                if (adapter==null) {
                    adapter = CartAdapter(response, this@CartActivity)
                    layoutManager= LinearLayoutManager(this@CartActivity)
                    addItemDecoration(LinearSpaceItemDecoration(16))
                }
                else
                    (adapter as? CartAdapter)?.updateList(response)
            }
            var total=0
            response.map { total+=it.totalAmount
            }
            tvTotal.text =getString(R.string.rupees) + total.toString()
        }
    }

    private fun sendToDetails(model: ShoesDetailModel){
        startActivity(
            Intent(this, DetailedActivity::class.java)
            .putExtra(MyConstants.INTENT_PASS_MODEL,model))
    }

    override fun addProduct(model: EntityShoes) {
        mViewModel.addProduct(model)
    }

    override fun removeAllProduct(model: EntityShoes) {
        mViewModel.deleteAllProduct(model)
    }

    override fun reduceQuantity(model: EntityShoes) {
        mViewModel.reduceQuantity(model)
    }
}