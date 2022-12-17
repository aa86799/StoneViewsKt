package com.stone.stoneviewskt.common.mvi

interface IUiState

/**
 * 正在加载
 */
class LoadingState(val isShow: Boolean) : IUiState

/**
 * 加载失败
 */
class LoadErrorState(val error: String) : IUiState

class LoadSuccessState<T>(val subState: IUiState, val data: T?) : IUiState

class LoadSuccessPageDataState<T>(val subState: IUiState, val data: List<T>?, val page: Int) : IUiState