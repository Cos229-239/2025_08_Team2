package com.example.ravengamingnews.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TopicEnum {
    @SerialName("Patch Notes")
    PATCH_NOTES,

    @SerialName("Game Updates")
    GAME_UPDATES,

    @SerialName("Cosmetics")
    COSMETICS,

    @SerialName("Game Modes")
    GAME_MODES;
}
