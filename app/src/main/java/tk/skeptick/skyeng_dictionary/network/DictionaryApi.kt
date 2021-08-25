package tk.skeptick.skyeng_dictionary.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import tk.skeptick.skyeng_dictionary.network.models.MeaningResponse
import tk.skeptick.skyeng_dictionary.network.models.WordResponse

interface DictionaryApi {

    @GET("v1/meanings")
    fun getMeanings(@Query("ids") ids: List<Int>): Single<List<MeaningResponse>>

    @GET("v1/words/search")
    fun search(@Query("search") text: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<List<WordResponse>>

}