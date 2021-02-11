package models

data class AnimeSearchResponse(
    val results: List<Anime>
) {

}

data class Anime(
    val mal_id: String,
    val title: String,
    val image_url: String,
    val synopsis: String
) {

}