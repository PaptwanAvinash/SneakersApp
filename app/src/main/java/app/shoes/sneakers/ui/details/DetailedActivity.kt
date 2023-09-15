package app.shoes.sneakers.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.shoes.sneakers.R
import app.shoes.sneakers.databinding.ActivityDetailBinding
import app.shoes.sneakers.ui.cart.CartActivity
import app.shoes.sneakers.ui.details.viewmodel.DetailViewModel
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.BaseResponse
import app.shoes.sneakers.utils.MyConstants
import app.shoes.sneakers.utils.setImageUrl
import app.shoes.sneakers.utils.setOnClickListenerDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailedActivity : AppCompatActivity() {

        @Inject
        lateinit var factory: DetailViewModel.Factory
        private val mViewModel by viewModels<DetailViewModel> {
               provideFactory()
        }
        private lateinit var mBinding: ActivityDetailBinding

        private fun provideFactory():ViewModelProvider.Factory{
                val model=intent?.getParcelableExtra(MyConstants.INTENT_PASS_MODEL) as? ShoesDetailModel
              return  DetailViewModel.getDetailVMModel(factory,model!!)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                mBinding = ActivityDetailBinding.inflate(layoutInflater)
                setContentView(mBinding.root)
                registerObserver()
        }

        private fun registerObserver(){
                mViewModel.addProductStatus.observe(this@DetailedActivity) {
                        mBinding.progressBar.visibility = if (it) {
                                sendToCart()
                                View.GONE
                        }else
                                View.VISIBLE
                }
                lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED){
                                mViewModel.shoesDetail.collectLatest {
                                        when(it){
                                                is BaseResponse.Loading->
                                                        mBinding.shimmer.startShimmer()
                                                is BaseResponse.Success->
                                                        implementView(it.data)
                                                is BaseResponse.Failed->{}
                                        }
                                }
                        }
                }
        }

        private fun implementView(model: ShoesDetailModel){
                mViewModel.mProductDetailModel=model
                mBinding.apply {
                        mBinding.shimmer.stopShimmer()
                        mBinding.shimmer.visibility=View.GONE
                        detailView.visibility=View.VISIBLE
                        tvTitle.text=model.title
                        tvName.text=model.name
                        tvDetail.text="${model.brand} ${ model.gender } ${model.colorway}"
                        tvPrice.text=getString(R.string.rupees)+" "+model.retailPrice.toString()
                        addCart.setOnClickListenerDebounce { sendToCart() }
                        mBinding.ivBack.setOnClickListenerDebounce { finish() }
                        btnAddCart.setOnClickListenerDebounce {  mViewModel.addProductToCart() }
                }
        }

        private fun sendToCart( ){
               startActivity(Intent(this,CartActivity::class.java))
        }
}
