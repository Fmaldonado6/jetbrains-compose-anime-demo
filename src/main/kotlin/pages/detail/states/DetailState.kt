package pages.detail.states

import models.Character
import models.CharacterResponse

abstract class DetailState {
}

class LoadedState(val characters: List<Character>?) : DetailState() {
}

class LoadingState() : DetailState() {

}

class ErrorState() : DetailState() {}