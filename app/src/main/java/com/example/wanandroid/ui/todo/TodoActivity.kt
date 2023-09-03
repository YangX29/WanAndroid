package com.example.wanandroid.ui.todo

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.findFirstIndex
import com.example.module_common.utils.extension.linearLayout
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityTodoBinding
import com.example.wanandroid.model.TodoInfo
import com.example.wanandroid.model.TodoSearchParams
import com.example.wanandroid.mvi.todo.TodoViewIntent
import com.example.wanandroid.mvi.todo.TodoViewModel
import com.example.wanandroid.mvi.todo.TodoViewState
import com.example.wanandroid.utils.user.LoginInterceptor
import com.example.wanandroid.view.widget.CustomLoadMoreView
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/8/10
 * @description: TODO页面
 */
@Route(path = RoutePath.TODO, extras = LoginInterceptor.INTERCEPTOR_PAGE)
class TodoActivity :
    BaseMVIActivity<ActivityTodoBinding, TodoViewState, TodoViewIntent, TodoViewModel>() {

    override val viewModel: TodoViewModel by viewModels()

    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        initData()
        //观察监听
        observeData()
    }

    override fun handleViewState(viewState: TodoViewState) {
        when (viewState) {
            is TodoViewState.RefreshSuccess -> {
                //刷新
                onRefresh(viewState.isFinish, viewState.data)
            }

            is TodoViewState.RefreshFailed -> {
                //刷新失败
                onRefreshFailed()
            }


            is TodoViewState.LoadMoreSuccess -> {
                //加载更多成功
                onLoadMore(viewState.isFinish, viewState.data)
            }

            is TodoViewState.LoadMoreFailed -> {
                //加载更多失败
                onLoadMoreFailed()
            }

            is TodoViewState.UpdateTodo -> {
                //更新任务
                updateTodo(viewState.info)
            }

            is TodoViewState.AddTodo -> {
                //添加任务
                addTodo(viewState.info)
            }

            is TodoViewState.DoneTodo -> {
                //完成任务
                doneTodo(viewState.id, viewState.done)
            }

            is TodoViewState.DeleteTodo -> {
                //删除任务
                deleteTodo(viewState.id)
            }
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //标题栏
        mBinding.toolbar.apply {
            setOnLeftClick { finish() }
        }
        //添加按钮
        mBinding.fabAdd.setOnClickListener { jumpToEdit() }
        //todo列表
        adapter = TodoListAdapter().apply {
            //点击事件
            setOnItemClickListener { _, _, position ->
                itemClick(position)
            }
            //子view点击事件
            setOnItemChildClickListener { _, view, position ->
                itemChildClick(view.id, position)
            }
            //加载更多
            loadMoreModule.loadMoreView = CustomLoadMoreView()
            loadMoreModule.setOnLoadMoreListener { loadMore() }

        }
        mBinding.rvTodo.apply {
            linearLayout()
            adapter = this@TodoActivity.adapter
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        val defaultParams = TodoSearchParams()
        sendIntent(TodoViewIntent.Refresh(defaultParams))
    }

    /**
     * 监听LiveEventBus
     */
    private fun observeData() {
        //更新
        LiveEventBus.get<TodoInfo>(LiveEventKey.KEY_TODO_UPDATE).observe(this) {
            updateTodo(it)
        }
        //删除
        LiveEventBus.get<Long>(LiveEventKey.KEY_TODO_DELETE).observe(this) {
            deleteTodo(it)
        }
        //添加
        LiveEventBus.get<TodoInfo>(LiveEventKey.KEY_TODO_ADD).observe(this) {
            addTodo(it)
        }
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        val defaultParams = TodoSearchParams()
        sendIntent(TodoViewIntent.LoadMore(defaultParams))
    }

    /**
     * 查看任务详情
     */
    private fun itemClick(position: Int) {
        val item = adapter.getItem(position)
        jumpToEdit(item)
    }

    /**
     * 子view点击事件
     */
    private fun itemChildClick(id: Int, position: Int) {
        val item = adapter.getItem(position)
        if (id == R.id.ivChecked) {
            //修改选中状态
            sendIntent(TodoViewIntent.ModifyStatus(item.id, !item.isDone))
        }
    }

    /**
     * 跳转到编辑页
     */
    private fun jumpToEdit(todoInfo: TodoInfo? = null) {
        ARouter.getInstance().build(RoutePath.TODO_EDIT)
            .withParcelable(TodoEditActivity.TODO_INFO, todoInfo)
            .navigation()
    }

    /**
     * 刷新
     */
    private fun onRefresh(isFinish: Boolean, data: MutableList<TodoInfo>) {
        //加载更多状态
        if (isFinish) {
            adapter.loadMoreModule.loadMoreEnd()
        } else {
            adapter.loadMoreModule.loadMoreComplete()
        }
        //设置数据
        adapter.setList(data)
    }

    /**
     * 刷新失败
     */
    private fun onRefreshFailed() {
        //TODO
    }

    /**
     * 加载更多
     */
    private fun onLoadMore(isFinish: Boolean, data: MutableList<TodoInfo>) {
        //加载更多状态
        if (isFinish) {
            adapter.loadMoreModule.loadMoreEnd()
        } else {
            adapter.loadMoreModule.loadMoreComplete()
        }
        //设置数据
        adapter.addData(data)
    }

    /**
     * 加载更多失败
     */
    private fun onLoadMoreFailed() {
        //加载失败
        adapter.loadMoreModule.loadMoreFail()
    }

    /**
     * 更新任务
     */
    private fun updateTodo(info: TodoInfo) {
        val index = adapter.data.findFirstIndex { it.id == info.id }
        //更新
        if (index != -1) {
            adapter.setData(index, info)
        }
    }

    /**
     * 更新完成任务状态
     */
    private fun doneTodo(id: Long, done: Boolean) {
        //更新完成状态
        adapter.data.find { it.id == id }?.let {
            val index = adapter.data.indexOf(it)
            it.isDone = done
            adapter.notifyItemChanged(index, TodoListAdapter.PAYLOAD_CHECK)
        }
    }

    /**
     * 删除任务
     */
    private fun deleteTodo(id: Long) {
        //删除任务
        adapter.data.find { it.id == id }?.let {
            adapter.remove(it)
        }
    }

    /**
     * 添加任务
     */
    private fun addTodo(info: TodoInfo) {
        // 添加任务,不能直接添加到后面,排序
        initData()
    }


}