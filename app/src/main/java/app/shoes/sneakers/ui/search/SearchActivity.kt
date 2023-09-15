package app.shoes.sneakers.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import app.shoes.sneakers.databinding.ActivitySearchBinding
import app.shoes.sneakers.ui.details.DetailedActivity
import app.shoes.sneakers.ui.home.adapter.ShoesListAdapter
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.ui.search.viewmodel.SearchViewModel
import app.shoes.sneakers.utils.BaseResponse
import app.shoes.sneakers.utils.LinearSpaceItemDecoration
import app.shoes.sneakers.utils.MyConstants
import app.shoes.sneakers.utils.setOnClickListenerDebounce
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val mViewModel by viewModels<SearchViewModel>()
    private lateinit var mBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        implementView()
        implementSearch()
    }

    private fun implementView(){
        mBinding.ivBack.setOnClickListenerDebounce { finish() }
    }

    private fun implementSearch(){
        val queryObservable = Observable.create<String> { emitter ->
             mBinding.edSearch.addTextChangedListener {
                 val text=it.toString()
                 if (text.length<2)return@addTextChangedListener
                 emitter.onNext(text)
             }
        }.debounce(300, TimeUnit.MILLISECONDS)

         disposable=queryObservable
            .observeOn(Schedulers.io())
            .switchMap { query ->
                Observable.fromCallable {
                    mViewModel.requestSearchItems(query)
                }
                    .subscribeOn(AndroidSchedulers.mainThread())
            }.subscribe {
                implementShoesList(it)
            }
    }

    private var disposable: Disposable?=null

    private fun implementShoesList(response: ArrayList<ShoesDetailModel>){
        with(mBinding.rvSearchItem) {
            if (adapter==null) {
                adapter = ShoesListAdapter(response, {
                    sendToDetails(it)
                },ShoesListAdapter.VIEW_TYPE_SEARCH)
                layoutManager= LinearLayoutManager(this@SearchActivity)
                addItemDecoration(LinearSpaceItemDecoration(16))
            }
            else
                (adapter as? ShoesListAdapter)?.updateList(response)
        }
    }

    private fun sendToDetails(model: ShoesDetailModel){
        startActivity(
            Intent(this, DetailedActivity::class.java)
            .putExtra(MyConstants.INTENT_PASS_MODEL,model))
    }


}