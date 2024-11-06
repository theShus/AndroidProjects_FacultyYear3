package com.example.rmaproject2.data.datasource.remote

import com.example.rmaproject2.data.models.course.CourseResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface  CourseService {
    @GET("raspored/json.php")
    fun getAll(): Observable<List<CourseResponse>>
}