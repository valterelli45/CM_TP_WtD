package intro.android.cm_tp_wtd.api

import intro.android.cm_tp_wtd.models.Dashboard
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("trips")
    fun getTrips(): Call<List<Dashboard>>

    @GET("trips-img")
    fun getTripsImages(): Call<List<Dashboard>>
}
