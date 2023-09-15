package app.shoes.sneakers.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import app.shoes.sneakers.databinding.ActivityHomeBinding
import app.shoes.sneakers.ui.cart.CartActivity
import app.shoes.sneakers.ui.details.DetailedActivity
import app.shoes.sneakers.ui.home.adapter.ShoesListAdapter
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.ui.home.viewmodel.HomeViewModel
import app.shoes.sneakers.ui.search.SearchActivity
import app.shoes.sneakers.utils.BaseResponse
import app.shoes.sneakers.utils.GridSpacingItemDecoration
import app.shoes.sneakers.utils.MyConstants.INTENT_PASS_MODEL
import app.shoes.sneakers.utils.setOnClickListenerDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel by viewModels<HomeViewModel>()
    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        implementView()
    }

    private fun implementView(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                mViewModel.shoesList
                    .collectLatest {
                        when(it){
                        is BaseResponse.Loading-> {
                            mBinding.shimmer.visibility=View.VISIBLE
                            mBinding.shimmer.startShimmer()
                        }
                        is BaseResponse.Success-> {
                            mBinding.shimmer.stopShimmer()
                            mBinding.shimmer.visibility=View.GONE
                            implementShoesList(it.data)
                        }
                        else-> {
                        }
                    }
                }
            }
        }
        mBinding.addCart.setOnClickListenerDebounce { sendToCart() }
        mBinding.ivSearch.setOnClickListenerDebounce { sendToSearch() }
    }

    private fun implementShoesList(response: ArrayList<ShoesDetailModel>){
        with(mBinding.rvShoesList) {
            if (adapter==null) {
                adapter = ShoesListAdapter(response, {
                    sendToDetails(it)
                })
                layoutManager= GridLayoutManager(this@MainActivity,2)
                addItemDecoration(GridSpacingItemDecoration(2,20,true))
            }
            else
                (adapter as? ShoesListAdapter)?.updateList(response)
        }
    }

    private fun sendToDetails(model: ShoesDetailModel){
        startActivity(Intent(this, DetailedActivity::class.java)
            .putExtra(INTENT_PASS_MODEL,model))
    }

    private fun sendToCart( ){
        startActivity(Intent(this, CartActivity::class.java))
    }

    private fun sendToSearch( ){
        startActivity(Intent(this, SearchActivity::class.java))
    }
}