package models


data class CharacterResponse(
    val characters:List<Character>
) {
}

data class Character(
    val name:String,
    val image_url:String,
)