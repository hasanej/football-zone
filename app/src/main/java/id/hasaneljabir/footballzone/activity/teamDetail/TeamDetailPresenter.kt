package id.hasaneljabir.footballzone.activity.teamDetail

import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation

class TeamDetailPresenter(
    val mView: TeamDetailContract.View,
    val localRepositoryImplementation: LocalRepositoryImplementation
) : TeamDetailContract.Presenter {


    override fun deleteTeam(id: String) {
        localRepositoryImplementation.deleteTeamData(id)
    }

    override fun checkTeam(id: String) {
        mView.setFavoriteState(localRepositoryImplementation.checkFavTeam(id))
    }

    override fun insertTeam(id: String, imgUrl: String) {
        localRepositoryImplementation.insertTeamData(id, imgUrl)
    }
}