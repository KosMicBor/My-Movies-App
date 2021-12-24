package kosmicbor.giftapp.mymoviesapp.view

sealed class AppState<T>

data class Success<T>(val value: T) : AppState<T>()

data class Error<T>(val error: Throwable) : AppState<T>()

object LoadingState : AppState<Nothing>()

