package kosmicbor.giftapp.mymoviesapp.domain

object RepositoryImpl : Repository {

    override val favoritesList: MutableList<Movie> = mutableListOf()

    override fun getLocalData(): List<Movie> {
        return listOf(
            Movie("Мортал Комбат", "Фильм про драки", "2021", 7.0),
            Movie("Сияние", "Страшно", "1987", 8.0),
            Movie("Зелёная миля", "Шедевр по Кингу", "1999", 10.0),
            Movie("Топган", "Фильм про самолёты", "1986", 6.5),
            Movie("Горячие головы", "Смешной фильм про самолёты", "1991", 7.5),
            Movie("Звёзные войны: эпизод 4", "Звёздная сага", "1987", 7.5),
            Movie("Звёзные войны: эпизод 5", "Звёздная сага", "1989", 7.5),
            Movie("Звёзные войны: эпизод 6", "Звёздная сага", "1991", 7.5),
            Movie("Звёзные войны: эпизод 1", "Звёздная сага", "2001", 7.5),
            Movie("Звёзные войны: эпизод 2", "Звёздная сага", "2003", 7.5),
            Movie("Звёзные войны: эпизод 3", "Звёздная сага", "2007", 7.5)
        )
    }

    override fun getRemoteData() {
        TODO("Not yet implemented")
    }

    override fun getCollections(): HashMap<String, List<Movie>> {
        val popularList = getLocalData()
        val topRatedList = getLocalData()
        val nowPlayingList = getLocalData()
        val upcomingList = getLocalData()

        return hashMapOf(
            "Popular" to popularList,
            "Top Rated" to topRatedList,
            "Now playing" to nowPlayingList,
            "Upcoming" to upcomingList
        )
    }

    override fun getFavorites(): List<Movie> = favoritesList

    override fun addFavoriteMovie(movie: Movie) {
        favoritesList.add(movie)
    }

    override fun removeFavoriteMovie(movie: Movie) {
        favoritesList.remove(movie)
    }
}
