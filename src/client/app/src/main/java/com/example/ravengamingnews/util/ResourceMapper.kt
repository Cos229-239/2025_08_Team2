package com.example.ravengamingnews.util

import com.example.ravengamingnews.R
import com.example.ravengamingnews.data.TopicEnum

/**
 * Utility class to map game and topic IDs to their corresponding string resource IDs.
 * This enables proper localization of game and topic names without requiring
 * the backend to handle localization.
 */
object ResourceMapper {

    /**
     * Maps a game ID to its corresponding string resource ID.
     *
     * @param gameId The game ID from the API
     * @return The string resource ID for the localized game name, or fallback if no mapping exists
     */
    fun getGameNameResourceId(gameId: Int): Int? {
        return gameNameMap[gameId]
    }

    /**
     * Maps a topic ID to its corresponding string resource ID.
     *
     * @param topicId The topic ID from the API
     * @return The string resource ID for the localized topic name, or fallback if no mapping exists
     */
    fun getTopicNameResourceId(topicId: TopicEnum?): Int? {
        return topicNameMap[topicId]
    }

    /**
     * Map of game IDs to string resource IDs.
     * Add entries here whenever a new game is added to the backend.
     */
    private val gameNameMap = mapOf(
        1 to R.string.game_1,
        2 to R.string.game_2,
        3 to R.string.game_3,
        4 to R.string.game_4,
        5 to R.string.game_5
        // Add more mappings as needed
    )

    /**
     * Map of topic IDs to string resource IDs.
     * Add entries here whenever a new topic is added to the backend.
     */
    private val topicNameMap = mapOf(
        TopicEnum.PATCH_NOTES to R.string.topic_1,
        TopicEnum.GAME_UPDATES to R.string.topic_2,
        TopicEnum.COSMETICS to R.string.topic_3,
        TopicEnum.GAME_MODES to R.string.topic_4,
        // Add more mappings as needed
    )
}
