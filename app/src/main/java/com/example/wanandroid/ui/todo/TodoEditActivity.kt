package com.example.wanandroid.ui.todo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.extension.getColorRes
import com.example.module_common.utils.extension.getStringRes
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityTodoEditBinding
import com.example.wanandroid.model.TodoInfo
import com.example.wanandroid.model.TodoModel
import com.example.wanandroid.mvi.todo.TodoViewIntent
import com.example.wanandroid.mvi.todo.TodoViewModel
import com.example.wanandroid.mvi.todo.TodoViewState
import com.example.wanandroid.utils.extension.tintColor
import com.example.wanandroid.view.dialog.date_picker.DatePickerDialog
import com.example.wanandroid.view.dialog.menu.MenuDialog
import com.example.wanandroid.view.dialog.menu.MenuItem
import com.example.wanandroid.view.widget.roundDrawable
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author: Yang
 * @date: 2023/9/2
 * @description: todo编辑页面
 */
@Route(path = RoutePath.TODO_EDIT)
class TodoEditActivity :
    BaseMVIActivity<ActivityTodoEditBinding, TodoViewState, TodoViewIntent, TodoViewModel>() {

    companion object {
        const val TODO_INFO = "todo_info"
    }

    @Autowired(name = TODO_INFO)
    @JvmField
    var todoInfo: TodoInfo? = null

    //todo模型
    private val todoModel by lazy { TodoModel.createFromInfo(todoInfo) }

    override val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    override fun handleViewState(viewState: TodoViewState) {
        if (viewState is TodoViewState.UpdateTodo ||
            viewState is TodoViewState.AddTodo ||
            viewState is TodoViewState.DeleteTodo
        ) {
            finish()
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.apply {
            setOnLeftClick { finish() }
            setOnRightClick { save() }
            setOnExtraClick { delete() }
            //任务已存在显示删除
            showExtraButton(todoInfo != null)
        }
        //日期
        mBinding.llDate.apply {
            background = roundDrawable(10.dp2px().toFloat(), Color.WHITE)
            setOnClickListener { showDatePicker() }
        }
        updateDateView()
        //标签
        mBinding.llTag.apply {
            background = roundDrawable(10.dp2px().toFloat(), Color.WHITE)
            setOnClickListener { showTagDialog() }
        }
        updateTagView()
        //优先级
        mBinding.llPriority.apply {
            background = roundDrawable(10.dp2px().toFloat(), Color.WHITE)
            setOnClickListener { showPriorityDialog() }
        }
        updatePriorityView()
        //标题
        mBinding.etTitle.setText(todoModel.title)
        //描述
        mBinding.etContent.setText(todoModel.content)
    }

    /**
     * 更新日期
     */
    private fun updateDateView() {
        mBinding.tvDate.text = todoModel.date
    }

    /**
     * 修改标签显示
     */
    private fun updateTagView() {
        val typeColor = getColorRes(TodoInfo.getTypeColor(todoModel.type))
        mBinding.tvTag.text = todoModel.tag
        mBinding.tvTag.setTextColor(typeColor)
        mBinding.ivTag.tintColor(typeColor)
    }

    /**
     * 修改优先级显示
     */
    private fun updatePriorityView() {
        val priorityColor = getColorRes(TodoInfo.getPriorityColor(todoModel.priority))
        mBinding.tvPriority.text = todoModel.priorityStr
        mBinding.tvPriority.setTextColor(priorityColor)
        mBinding.ivPriority.tintColor(priorityColor)
    }

    /**
     * 保存
     */
    private fun save() {
        //标题和描述
        todoModel.title = mBinding.etTitle.text.toString()
        todoModel.content = mBinding.etContent.text.toString()
        if (todoInfo == null) {
            //添加todo
            sendIntent(TodoViewIntent.AddTodo(todoModel))
        } else {
            //更新todo
            sendIntent(TodoViewIntent.UpdateTodo(todoInfo!!.id, todoModel))
        }
    }

    /**
     * 删除
     */
    private fun delete() {
        val id = todoInfo?.id ?: return
        sendIntent(TodoViewIntent.Delete(id))
    }

    /**
     * 显示日期选择
     */
    private fun showDatePicker() {
        val date = SimpleDateFormat(TodoModel.FORMAT_DATE, Locale.getDefault())
            .parse(todoModel.date) ?: return
        DatePickerDialog(this).apply {
            setDate(date)
            setOnConfirm {
                todoModel.date = it
                updateDateView()
            }
            show()
        }
    }

    /**
     * 显示标签选择弹窗
     */
    private fun showTagDialog() {
        val list = mutableListOf<MenuItem>()
        for (tag in 0..4) {
            list.add(
                MenuItem(
                    R.drawable.icon_tag,
                    if (tag == 0) getStringRes(R.string.common_uncategorized) else getStringRes(
                        R.string.todo_tag,
                        tag
                    ),
                    TodoInfo.typeColorList[tag]
                )
            )
        }
        MenuDialog(this).apply {
            setMenu(list)
            setOnSelectListener {
                todoModel.type = it
                updateTagView()
            }
            show()
        }
    }

    /**
     * 显示优先级选择弹窗
     */
    private fun showPriorityDialog() {
        val list = mutableListOf<MenuItem>()
        for (priority in 1..4) {
            list.add(
                MenuItem(
                    R.drawable.icon_priority,
                    getStringRes(R.string.todo_priority, priority),
                    TodoInfo.priorityColorList[priority - 1]
                )
            )
        }
        MenuDialog(this).apply {
            setMenu(list)
            setOnSelectListener {
                todoModel.priority = it + 1
                updatePriorityView()
            }
            show()
        }
    }

}