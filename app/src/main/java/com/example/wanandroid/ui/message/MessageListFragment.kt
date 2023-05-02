package com.example.wanandroid.ui.message

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Message
import com.example.wanandroid.mvi.message.MessageListViewModel
import com.example.wanandroid.ui.list.SimpleListFragment
import com.example.wanandroid.ui.web.WebActivity

/**
 * @author: Yang
 * @date: 2023/5/2
 * @description: 消息列表Fragment
 */
class MessageListFragment : SimpleListFragment<Message, MessageListViewModel>() {

    companion object {
        const val TYPE_READ_LIST = "readed_list"
        const val TYPE_UNREAD_LIST = "unread_list"

        const val EXTRA_KEY_TYPE = "message_type"

        /**
         * 创建实例
         */
        fun newInstance(type: String): MessageListFragment {
            val fragment = MessageListFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_KEY_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val viewModel: MessageListViewModel by viewModels {
        MessageListViewModel.Factory(type)
    }

    override val adapter = MessageListAdapter()

    @Autowired(name = EXTRA_KEY_TYPE)
    @JvmField
    var type: String = TYPE_UNREAD_LIST

    override fun showDivider() = false

    override fun showTopButton() = false

    override fun onItemClick(position: Int) {
        val link = adapter.data[position].fullLink
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, link)
            .navigation()
    }

}