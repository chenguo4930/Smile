package com.yhao.module.pic

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yhao.model.bean.Joke
import com.yhao.model.service.JokeService
import com.yhao.module.R
import com.yhao.module.showSnackbar
import kotlinx.android.synthetic.main.fragment_pic_classify.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.properties.Delegates

class JokeFragment : Fragment() {

    private var mData: MutableList<Joke> = ArrayList()
    private var mPage: Int = 1
    private var mLoading by Delegates.observable(true) { _, _, new ->
        mSwipeRefreshLayout.isRefreshing = new
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_pic_classify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        loadData()
    }

    private fun initView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        //下文翻译:RecyclerView的尺寸在每次改变时，比如你加任何些东西。setHasFixedSize 的作用就是确保尺寸
        // 是通过用户输入从而确保RecyclerView的尺寸是一个常数。RecyclerView 的Item宽或者高不会变。
        // 每一个Item添加或者删除都不会变。如果你没有设置setHasFixedSized没有设置的代价将会是非常昂贵的。
        // 因为RecyclerView会需要而外计算每个item的size，
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener {
            mPage = 1
            loadData()
        }
        mRecyclerView.setOnTouchListener { _, _ ->
            if (!mLoading && !mRecyclerView.canScrollVertically(1)) {
                mPage++
                loadData()
            }
            false
        }
    }

    private fun loadData() {
        mLoading = true
        doAsync {
            val data = JokeService.getData(mPage)
            uiThread {
                mLoading = false
                if (data == null) {
                    showSnackbar(view as ViewGroup, "加载失败")
                    return@uiThread
                }
                when {
                    mRecyclerView.adapter == null -> {
                        mData.addAll(data)
                        initAdapter()
                    }
                    mPage > 1 -> {
                        val pos = mData.size
                        mData.addAll(data)
                        mRecyclerView.adapter.notifyItemRangeInserted(pos, data.size)
                    }
                    else -> {
                        mData.clear()
                        mData.addAll(data)
                        mRecyclerView.adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        mRecyclerView.adapter = JokeAdapter(mData)
    }
}
