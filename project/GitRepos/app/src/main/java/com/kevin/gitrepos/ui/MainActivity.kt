package com.kevin.gitrepos.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kevin.gitrepos.App
import com.kevin.gitrepos.R
import com.kevin.gitrepos.data.model.Status
import com.kevin.gitrepos.util.ResourcesWrapper
import com.kevin.gitrepos.viewmodel.GitViewModelFactory
import com.kevin.gitrepos.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: GitViewModelFactory
    @Inject
    lateinit var resourcesWrapper: ResourcesWrapper

    private lateinit var adapter: RepoAdapter
    private lateinit var viewModel: ReposViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).component.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ReposViewModel::class.java)

        subscribe()

        init()
    }

    private fun init() {
        adapter = RepoAdapter(resourcesWrapper)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val last = (recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val total = recyclerView.adapter.itemCount

                if(total - last < LOAD_MORE &&
                        viewModel.loadMoreData.value == false) viewModel.loadMoreData.value = true
            }
        })

        input.setOnEditorActionListener{ _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                refresh()
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun subscribe() {
        viewModel.reposLiveData.observe(this, Observer { repos ->
            repos?.let {
                when(repos.status) {
                    Status.LOADING -> loading()
                    Status.ERROR -> error()
                    Status.SUCCESS -> {
                        repos.data?.let {
                            adapter.repos.clear()
                            adapter.repos.addAll(it)
                            adapter.notifyDataSetChanged()
                            success()
                        } ?: error()
                    }
                    Status.EMPTY -> empty()
                }
            }
        })

        viewModel.moreReposLiveData.observe(this, Observer { moreRepos ->
            moreRepos?.let {
                when(moreRepos.status) {
                    Status.LOADING -> loadingMore()
                    Status.ERROR -> {
                        hideLoadingMore()
                        viewModel.loadMoreData.value = false
                    }
                    Status.SUCCESS -> {
                        hideLoadingMore()
                        moreRepos.data?.let {
                            Handler().post{
                                val initialSize = adapter.repos.size
                                adapter.repos.addAll(it)
                                adapter.notifyItemRangeInserted(initialSize, it.size)
                            }
                        }
                        viewModel.loadMoreData.value = false
                    }
                    Status.EMPTY -> viewModel.loadMoreData.value = false
                }
            }
        })
    }

    private fun refresh() {
        if(input.text.isEmpty()) {
            input.error = getString(R.string.error)
            input.requestFocus()
            error()
            return
        }

        viewModel.usernameLiveData.value = input.text.toString()
        recyclerView.scrollToPosition(0)
    }

    /**
     *  method when loading
     */
    private fun loading() {
        recyclerView.visibility = View.GONE
        none.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }

    /**
     *  method when error occurred
     */
    private fun error() {
        recyclerView.visibility = View.GONE
        none.visibility = View.GONE
        progress.visibility = View.GONE

        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    /**
     *  method when repos are empty
     */
    private fun empty() {
        recyclerView.visibility = View.GONE
        none.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    private fun success() {
        recyclerView.visibility = View.VISIBLE
        none.visibility = View.GONE
        progress.visibility = View.GONE
    }

    /**
     *  method when load more repos
     */
    private fun loadingMore() {
        Handler().post({
            adapter.repos.add(null)
            adapter.notifyItemChanged(adapter.repos.size-1)
        })
    }

    /**
     *  method when hide loading more
     */
    private fun hideLoadingMore() {
        Handler().post({
            adapter.repos.removeAt(adapter.repos.size-1)
            adapter.notifyItemRemoved(adapter.repos.size)
        })
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    companion object {
        const val LOAD_MORE = 5
    }
}
