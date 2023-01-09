package com.example.modele_net.common.exception

@JvmField
val UnknownException = NetException(NetExceptionCode.UNKNOWN, NetExceptionMsg.UNKNOWN)

@JvmField
val UninitializedException = NetException(NetExceptionCode.UNINITIALIZED, NetExceptionMsg.UNINITIALIZED)

@JvmField
val ClientUninitializedException = NetException(NetExceptionCode.CLIENT_UNINITIALIZED, NetExceptionMsg.CLIENT_UNINITIALIZED)