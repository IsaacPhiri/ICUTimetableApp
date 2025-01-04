package com.example.icutimetableapp

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // Fetch all users
    @GET("users.php")
    fun getUsers(): Call<List<User>>

    // Create a new user
    @POST("users.php")
    @Headers("Content-Type: application/json")
    fun createUser(@Body user: User): Call<Void>

    // Update a user
    @PUT("users.php")
    @Headers("Content-Type: application/json")
    fun updateUser(@Body user: User): Call<Void>

    // Delete a user
    @DELETE("users.php")
    fun deleteUser(@Query("user_id") userId: Int): Call<Void>

    @POST("timetable.php")
    fun createTimetable(@Body timetable: Timetable): Call<Void>

    @GET("timetable.php")
    fun getAllTimetables(): Call<List<Timetable>>

    @GET("timetable.php/date/{date}")
    fun getTimetableByDate(@Path("date") date: String): Call<List<Timetable>>

    @PUT("timetable.php/{id}")
    fun updateTimetable(@Path("id") id: Int, @Body timetable: Timetable): Call<Void>

    @DELETE("timetable.php/{id}")
    fun deleteTimetable(@Path("id") id: Int): Call<Void>

    @GET("courses.php")
    fun getCourses(): Call<List<Course>>

    @POST("courses.php")
    fun addCourse(@Body course: CourseRequest): Call<Void>

    @PUT("courses.php/{id}")
    fun updateCourse(@Path("id") id: Int, @Body course: CourseRequest): Call<Void>

    @DELETE("courses.php/{id}")
    fun deleteCourse(@Path("id") id: Int): Call<Void>

    @GET("programs.php")
    fun getPrograms(): Call<List<Program>>

    @POST("programs.php")
    fun addProgram(@Body program: ProgramRequest): Call<Void>

    @PUT("programs.php/{id}")
    fun updateProgram(@Path("id") id: Int, @Body program: ProgramRequest): Call<Void>

    @DELETE("programs.php/{id}")
    fun deleteProgram(@Path("id") id: Int): Call<Void>

    @GET("users.php/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @GET("courses.php/{id}")
    fun getCourseById(@Path("id") id: Int): Call<Course>

    @GET("programs.php/{id}")
    fun getProgramById(@Path("id") id: Int): Call<Program>

    @GET("timetable.php/{id}")
    fun getTimetableById(@Path("id") id: Int): Call<Timetable>

}
