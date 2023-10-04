package com.example.wanandroid.mvi.todo

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import androidx.fragment.app.FragmentActivity
import com.example.module_common.utils.log.logE
import com.example.module_common.utils.permission.PermissionUtils
import com.example.wanandroid.app.WanApplication
import com.example.wanandroid.model.TodoInfo
import com.example.wanandroid.model.TodoModel
import java.util.Calendar
import java.util.TimeZone


/**
 * @author: Yang
 * @date: 2023/10/3
 * @description: 日历事件工具类
 */
object CalendarEventUtils {

    private const val TAG = "CalendarEventUtils"
    private const val EXTENDED_EXTRA_ID = "extraId"
    private const val CALENDAR_ACCOUNT_NAME = "WanAndroid"
    private const val CALENDAR_ACCOUNT_TYPE = "com.example.wanandroid"
    private const val CALENDAR_NAME = "TODO"
    private const val CALENDAR_DISPLAY_NAME = "TODO"
    private const val CALENDAR_OWNER_ACCOUNT = "owner@wanandroid.com"

    //自定义字段uri
    private val extendedPropertiesUri
        get() = CalendarContract.ExtendedProperties.CONTENT_URI.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDAR_ACCOUNT_TYPE)
            .build()


    /**
     * 检查权限
     */
    fun requestCalendarPermission(activity: FragmentActivity, callback: (Boolean) -> Unit) {
        PermissionUtils.requestPermission(
            activity,
            mutableListOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
            )
        ) { _, allSuccess -> callback.invoke(allSuccess) }
    }

    /**
     * 添加或更新日历事件提醒
     */
    fun setCalendarEventByTodo(todoModel: TodoModel) {
        //结束时间，一天结束时间
        val endTime = Calendar.getInstance()
        endTime.time = todoModel.calendar.time
        endTime.set(Calendar.HOUR_OF_DAY, 23)
        endTime.set(Calendar.MINUTE, 59)
        endTime.set(Calendar.SECOND, 59)
        //添加或者更新日历事件
        createOrUpdateEventAndReminder(
            WanApplication.context,
            todoModel.id.toString(),
            todoModel.title,
            todoModel.content,
            todoModel.calendar,
            endTime
        )
    }

    /**
     * 删除对应的日历事件和提醒
     */
    fun deleteCalendarEventById(id: Long) {
        //删除对应的日历事件
        deleteEventAndReminder(WanApplication.context, id.toString())
    }

    /**
     * 检查todo是否存在日历事件，已设置则修改其提醒时间
     */
    fun checkTodoCalendarEvent(todoInfo: TodoInfo?): TodoInfo? {
        if (todoInfo == null) return null
        WanApplication.context.apply {
            val eventId = getEventIdByExtraId(this, todoInfo.id.toString())
            if (eventId != -1L) {
                val eventUri =
                    ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId)
                val projection = arrayOf(CalendarContract.Events.DTSTART)
                contentResolver.query(eventUri, projection, null, null, null)?.let {
                    if (it.moveToFirst()) {
                        val index = it.getColumnIndex(CalendarContract.Events.DTSTART)
                        //设置时间
                        todoInfo.clockCalendar = Calendar.getInstance().apply {
                            timeInMillis = it.getLong(index)
                        }
                    }
                }
            }
        }
        return todoInfo
    }

    /**
     * 删除日历事件和提醒
     */
    private fun deleteEventAndReminder(
        context: Context,
        extraId: String
    ) {
        //查询指定EventId
        val eventId = getEventIdByExtraId(context, extraId)
        if (eventId != -1L) {
            val eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId)
            val where = "${CalendarContract.ExtendedProperties.EVENT_ID}=?"
            val selectionArgs = arrayOf(eventId.toString())
            context.contentResolver.apply {
                //删除事件
                delete(eventUri, null, null)
                //删除提醒
                delete(CalendarContract.Reminders.CONTENT_URI, where, selectionArgs)
                //删除自定义字段
                delete(extendedPropertiesUri, where, selectionArgs)
            }
        }
    }

    /**
     * 创建或者添加日历事件提醒
     */
    private fun createOrUpdateEventAndReminder(
        context: Context,
        extraId: String,
        eventTitle: String,
        eventDesc: String,
        startCalendar: Calendar,
        endCalendar: Calendar
    ) {
        //查询指定Event
        val eventId = getEventIdByExtraId(context, extraId)
        //判断是否存在该event
        if (eventId != -1L) {
            //更新
            updateEventAndReminder(
                context,
                eventId,
                eventTitle,
                eventDesc,
                startCalendar,
                endCalendar
            )
        } else {
            //不存在该事件，新增事件提醒
            createEventAndReminder(
                context,
                extraId,
                eventTitle,
                eventDesc,
                startCalendar,
                endCalendar
            )
        }
    }

    /**
     * 更新事件
     */
    private fun updateEventAndReminder(
        context: Context,
        eventId: Long,
        eventTitle: String,
        eventDesc: String,
        startCalendar: Calendar,
        endCalendar: Calendar
    ) {
        //创建新实例
        val event = ContentValues().apply {
            // 事件标题
            put(CalendarContract.Events.TITLE, eventTitle)
            // 事件描述
            put(CalendarContract.Events.DESCRIPTION, eventDesc)
            // 开始时间
            put(CalendarContract.Events.DTSTART, startCalendar.timeInMillis)
            // 结束时间
            put(CalendarContract.Events.DTEND, endCalendar.timeInMillis)
            // 时区
            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        }
        //更新
        val updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId)
        context.contentResolver.update(updateUri, event, null, null)
    }

    /**
     * 新增事件提醒
     */
    private fun createEventAndReminder(
        context: Context,
        extraId: String,
        eventTitle: String,
        eventDesc: String,
        startCalendar: Calendar,
        endCalendar: Calendar
    ) {
        //获取默认日历id
        val calendarId = getDefaultCalendarId(context)
        if (calendarId == -1L) {
            logE(TAG, "the calendar id is -1.")
            return
        }
        //构建事件实例
        val event = ContentValues().apply {
            //指定日历
            put(CalendarContract.Events.CALENDAR_ID, calendarId)
            //事件标题
            put(CalendarContract.Events.TITLE, eventTitle)
            //事件描述
            put(CalendarContract.Events.DESCRIPTION, eventDesc)
            //开始时间
            put(CalendarContract.Events.DTSTART, startCalendar.timeInMillis)
            //结束时间
            put(CalendarContract.Events.DTEND, endCalendar.timeInMillis)
            //时区
            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
//            //颜色
//            put(CalendarContract.Events.EVENT_COLOR, Color.RED)
//            //全天
//            put(CalendarContract.Events.ALL_DAY, 1)
//            //持续时间
//            put(CalendarContract.Events.DURATION, "PT1H")
        }
        //插入事件
        val result =
            context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, event)
        //添加提醒
        result?.let { ContentUris.parseId(it) }?.let { eventId ->
            //构建提醒实例
            val reminder = ContentValues().apply {
                //事件提醒提前时间,准时提醒
                put(CalendarContract.Reminders.MINUTES, 0)
                //事件id
                put(CalendarContract.Reminders.EVENT_ID, eventId)
                //提醒方式
                put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
            }
            //插入提醒
            context.contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminder)
            //添加自定义字段，extraId，第三方唯一EventId，用于唯一指定Event
            val extendedValue = ContentValues().apply {
                put(CalendarContract.ExtendedProperties.EVENT_ID, eventId)
                put(CalendarContract.ExtendedProperties.NAME, EXTENDED_EXTRA_ID)
                put(CalendarContract.ExtendedProperties.VALUE, extraId)
            }
            context.contentResolver.insert(
                extendedPropertiesUri,
                extendedValue
            )
        }
    }

    /**
     * 根据第三方id获取对应的事件id
     */
    private fun getEventIdByExtraId(
        context: Context,
        extraId: String
    ): Long {
        //查询指定Event
        val projection = arrayOf(CalendarContract.ExtendedProperties.EVENT_ID)
        val selection =
            "${CalendarContract.ExtendedProperties.NAME}=? AND ${CalendarContract.ExtendedProperties.VALUE}=?"
        val selectionArgs = arrayOf(EXTENDED_EXTRA_ID, extraId)
        val cursor = context.contentResolver.query(
            extendedPropertiesUri,
            projection,
            selection,
            selectionArgs,
            null
        )
        //判断是否存在该event
        if (cursor?.moveToFirst() == true) {
            //获取EventId
            val index = cursor.getColumnIndex(CalendarContract.ExtendedProperties.EVENT_ID)
            return cursor.getLong(index)
        }
        return -1L
    }

    /**
     * 获取默认的日历id
     */
    private fun getDefaultCalendarId(context: Context): Long {
        var calendarId = -1L
        val uri = CalendarContract.Calendars.CONTENT_URI
        val projection = arrayOf(CalendarContract.Calendars._ID)
        val selection =
            "${CalendarContract.Calendars.ACCOUNT_NAME}=? AND ${CalendarContract.Calendars.ACCOUNT_TYPE}=?"
        val selectionArgs = arrayOf(CALENDAR_ACCOUNT_NAME, CALENDAR_ACCOUNT_TYPE)
        //查询日历表
        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.apply {
            if (moveToFirst()) {
                //获取查询到的日历id
                val index = getColumnIndex(CalendarContract.Calendars._ID)
                if (index != -1) {
                    calendarId = getLong(index)
                }
            }
            close()
        }
        return if (calendarId == -1L) createCalendar(context) else calendarId
    }

    /**
     * 创建日历
     */
    private fun createCalendar(context: Context): Long {
        //构建实例
        val values = ContentValues().apply {
            put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME)
            put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDAR_ACCOUNT_TYPE)
            put(CalendarContract.Calendars.NAME, CALENDAR_NAME)
            put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_DISPLAY_NAME)
            //TODO 颜色
            put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
            put(
                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                CalendarContract.Calendars.CAL_ACCESS_OWNER
            )
            put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDAR_OWNER_ACCOUNT)
            put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, TimeZone.getDefault().id)
        }
        val calendarUri = CalendarContract.Calendars.CONTENT_URI.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDAR_ACCOUNT_TYPE)
            .build()
        //添加日历
        val result = context.contentResolver.insert(calendarUri, values)
        return if (result != null) {
            ContentUris.parseId(result)
        } else {
            -1L
        }
    }


}