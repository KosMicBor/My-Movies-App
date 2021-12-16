package kosmicbor.giftapp.mymoviesapp.domain

import kosmicbor.giftapp.mymoviesapp.view.AppState

interface Repository {

    fun getLocalData(): List<Movie>
    fun getRemoteData()
    fun getCollections(): HashMap<String,List <Movie>>
}
