package id.hasaneljabir.footballzone.feature.teamdetail

import id.hasaneljabir.footballzone.entity.repository.LocalRepositoryImpl

class TeamDetailPresenter(
    val mView: TeamDetailContract.View,
    val localRepositoryImpl: LocalRepositoryImpl
) : TeamDetailContract.Presenter {


    override fun deleteTeam(id: String) {
        localRepositoryImpl.deleteTeamData(id)
    }

    override fun checkTeam(id: String) {
        mView.setFavoriteState(localRepositoryImpl.checkFavTeam(id))
    }

    override fun insertTeam(id: String, imgUrl: String) {
        localRepositoryImpl.insertTeamData(id, imgUrl)
    }
}