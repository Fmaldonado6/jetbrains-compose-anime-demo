package services

import okhttp3.ResponseBody
import java.io.IOException

open class AppError(val error: ResponseBody?) : IOException() {
}

class BadInput(error: ResponseBody?) : AppError(error) {
}
class EmptySearch(error: ResponseBody?) : AppError(error) {
}

class NotFoundError(error: ResponseBody?) : AppError(error) {
}