package com.example.module_common.utils.view_binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * @author: Yang
 * @date: 2023/1/11
 * @description: ViewBinding工具类
 */
object VBUtils {


    /**
     * ViewBinding通用实例构造方法，需要明确指定ViewBiding类型
     */
    inline fun <reified VB : ViewBinding> inflate(
        layoutInflater: LayoutInflater,
        parent: ViewGroup? = null,
        attachToParent: Boolean = false
    ): VB {
        return VB::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, layoutInflater, parent, attachToParent) as VB
    }

    /**
     * ViewBinding通用实例构造方法
     */
    fun <VB : ViewBinding> inflate(
        owner: Any,
        layoutInflater: LayoutInflater,
        parent: ViewGroup? = null,
        attachToParent: Boolean = false
    ): VB {
        return witchVBClass(owner) { clazz ->
            clazz.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, layoutInflater, parent, attachToParent) as VB
        }
    }

    /**
     * 反射获取ViewBinding的class对象
     */
    private inline fun <VB : ViewBinding> witchVBClass(owner: Any, block: (Class<VB>) -> VB): VB {
        var genericSuperclass = owner.javaClass.genericSuperclass
        var superclass = owner.javaClass.superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                genericSuperclass.actualTypeArguments.forEach {
                    try {
                        return block.invoke(it as Class<VB>)
                    } catch (e: InvocationTargetException) {
                        throw e.targetException
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        throw IllegalArgumentException("There is no generic of ViewBinding.")
    }

}