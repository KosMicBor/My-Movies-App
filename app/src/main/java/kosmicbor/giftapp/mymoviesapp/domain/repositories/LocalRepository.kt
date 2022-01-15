package kosmicbor.giftapp.mymoviesapp.domain.repositories

import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.LocalMovie

interface LocalRepository {
    fun getAllHistory(
        getAllHistoryListener: LocalRepoImpl.GetAllHistoryListener<List<LocalMovie>>
    )

    fun saveEntity(movie: LocalMovie)
    fun updateEntity(movie: LocalMovie)
    fun removeEntity(movie: LocalMovie)
    fun getEntity(
        id: Int?,
        getEntityListener: LocalRepoImpl.GetEntityListener<LocalMovie>
    )
}