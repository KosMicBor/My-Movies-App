package kosmicbor.giftapp.mymoviesapp.domain

sealed class MoviesCollection

class DefaultCollection(val collectionName: String = "Default Name") : MoviesCollection()

