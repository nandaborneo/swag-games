package app.web.sleepcoder.core.utils

import app.web.sleepcoder.core.domain.model.Game

object DataDummy {

    fun dummyGame(data: Int, isFavorite: Boolean = false) = Game(
        gameId = "$data",
        slug = "SlugID $data",
        name = "Name $data",
        nameOriginal = "NameOriginal $data",
        isFavorite = isFavorite
    )

    val dummyListGame: List<Game>
        get() {
            val result = arrayListOf<Game>()
            for (i in 0..10) {
                result.add(
                    dummyGame(i)
                )
            }
            return result
        }

    val dummyListFavoriteGame: List<Game>
        get() {
            val result = arrayListOf<Game>()
            for (i in 0..5) {
                result.add(
                    dummyGame(i,true)
                )
            }
            return result
        }
}