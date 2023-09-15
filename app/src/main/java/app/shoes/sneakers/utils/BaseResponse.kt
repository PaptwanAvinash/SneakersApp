package app.shoes.sneakers.utils


sealed interface BaseResponse<T>{

    class Loading<T>() : BaseResponse<T>
    data class Success<T>(val data:T):BaseResponse<T>
    data class Failed<T>(val error:String):BaseResponse<T>
}

