package fr.epita.android.games

data class Informations(
    val game_id: Int,
    val name: String,
    val type: String,
    val players: String,
    val year: Int,
    val url: String,
    val picture: String,
    val description_fr: String,
    val description_en: String
) {
}