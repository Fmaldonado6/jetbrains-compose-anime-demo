package pages.browse.states

import models.Anime

open abstract class BrowseState {
}

class LoadedState(val animes: List<Anime>?) : BrowseState() {
}


class LoadingState() : BrowseState() {

}

class ErrorState() : BrowseState() {}

class EmptyState() : BrowseState() {}
