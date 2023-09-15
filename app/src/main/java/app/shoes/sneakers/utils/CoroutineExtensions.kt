package app.shoes.sneakers.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job


fun errorHandlerScope(scope:CoroutineScope,errorHandler:CoroutineExceptionHandler)=
    CoroutineScope(scope.coroutineContext.job+errorHandler)


