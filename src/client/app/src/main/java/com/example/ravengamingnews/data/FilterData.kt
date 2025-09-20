package com.example.ravengamingnews.data

import com.example.ravengamingnews.R

abstract class Filter(
    open val titleResId: Int,
    open var isChecked: Boolean = false
)

data class GameFilter(
    override val titleResId: Int,
    val gameId: Int,
    override var isChecked: Boolean = false
) : Filter(titleResId) {
    companion object {
        fun getSwitchData(): List<GameFilter> {
            return listOf(
                GameFilter(R.string.game_1, 1),
                GameFilter(R.string.game_2, 2),
                GameFilter(R.string.game_3, 3),
                GameFilter(R.string.game_4, 4),
                GameFilter(R.string.game_5, 5)
            )
        }
    }
}

data class TopicFilter(
    override val titleResId: Int,
    val topicEnum: TopicEnum,
    override var isChecked: Boolean = false
) : Filter(titleResId) {
    companion object {
        fun getSwitchData(): List<TopicFilter> {
            return listOf(
                TopicFilter(R.string.topic_1, TopicEnum.PATCH_NOTES),
                TopicFilter(R.string.topic_2, TopicEnum.GAME_UPDATES),
                TopicFilter(R.string.topic_3, TopicEnum.COSMETICS),
                TopicFilter(R.string.topic_4, TopicEnum.GAME_MODES)
            )
        }
    }
}

