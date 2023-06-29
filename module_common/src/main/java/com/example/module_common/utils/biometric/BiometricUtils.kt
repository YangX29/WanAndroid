package com.example.module_common.utils.biometric

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.module_common.utils.log.logE

/**
 * @author: Yang
 * @date: 2023/6/24
 * @description: 生物识别工具类
 */
object BiometricUtils {

    private const val TAG = "BiometricUtils"

    /**
     * 是否身份验证是否可用
     */
    fun canAuthenticate(context: Context, flag: Int): Boolean {
        return BiometricManager.from(context).run {
            when (canAuthenticate(flag)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {//可用
                    true
                }

                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {//安全漏洞，需要更新
                    logE(TAG, "biometric error, security update required.")
                    false
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {//无硬件
                    logE(TAG, "biometric error, no hardware.")
                    false
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {//未注册生物识别凭证
                    logE(TAG, "biometric error, none enrolled.")
                    false
                }

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {//硬件不可用
                    logE(TAG, "biometric error, hardware unavailable.")
                    false
                }

                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {//Android版本不兼容
                    logE(TAG, "biometric error, unsupported.")
                    false
                }

                BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {//无法确定是否可以进行身份验证
                    logE(TAG, "biometric error, unknown.")
                    false
                }

                else -> {
                    logE(TAG, "biometric error.")
                    false
                }
            }
        }
    }

    /**
     * 显示身份验证弹窗
     */
    fun showBiometricPrompt(
        activity: FragmentActivity,
        flag: Int,
        infoBuild: BiometricPrompt.PromptInfo.Builder.() -> BiometricPrompt.PromptInfo.Builder,
        onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
        onFailed: ((String) -> Unit)? = null
    ) {
        BiometricManager.from(activity).run {
            when (canAuthenticate(flag)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {//可用
                    val prompt =
                        BiometricPrompt(activity, ContextCompat.getMainExecutor(activity), object :
                            BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationError(
                                errorCode: Int,
                                errString: CharSequence
                            ) {
                                onFailed?.invoke("${errorCode}:$errString")
                            }

                            override fun onAuthenticationFailed() {
                                onFailed?.invoke("onAuthenticationFailed")
                            }

                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                onSuccess.invoke(result)
                            }
                        })
                    //弹窗信息
                    val infoBuilder = BiometricPrompt.PromptInfo.Builder().run {
                        setAllowedAuthenticators(flag)
                        infoBuild()
                    }
                    prompt.authenticate(infoBuilder.build())
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {//未注册生物识别凭证
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        //录入生物识别信息
                        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, flag)
                        }
                        activity.startActivity(enrollIntent)
                    } else {
                        //未录入生物识别信息
                        onFailed?.invoke("has none enroll.")
                    }
                }

                else -> {
                    //提示错误
                    onFailed?.invoke("can not Authenticate.")
                }
            }
        }
    }
}