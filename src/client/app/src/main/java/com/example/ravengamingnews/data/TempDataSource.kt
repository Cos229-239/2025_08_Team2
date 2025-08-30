package com.example.ravengamingnews.data

/**
 * Temporary data source for games, topics, and articles.
 * This is used for testing and prototyping before integrating a real data source like a database or API.
 */
object TempDataSource {
    val games = listOf(
        "League of Legends",
        "Dota 2",
        "Smite",
        "Heroes of the Storm",
        "Vainglory",
    )

    val topics = listOf(
        "Game Updates",
        "Cosmetic",
        "Game Modes",
        "Patch Update"
    )

    val fakeArticleList = listOf(
        Article(
            id = 1,
            title = "New Champion Released in League of Legends",
            summary = "A new champion has been released in League of Legends, bringing fresh gameplay mechanics and strategies to the game.",
            content = "The new champion, named 'Aether', is a mage with powerful area-of-effect abilities. Players can expect to see Aether dominating mid-lane with her unique skill set. Riot Games has also announced that Aether will be available for free during the first week after release.",
            author = "Jane Doe",
            date = "2025-08-20",
            game = "League of Legends",
            topic = "Game Updates"
        ),
        Article(
            id = 2,
            title = "Dota 2 Announces New Battle Pass",
            summary = "Dota 2 has announced a new Battle Pass for the upcoming season, featuring exclusive rewards and challenges for players.",
            content = "The new Battle Pass includes a variety of cosmetic items, including skins, loading screens, and voice lines. Players can also participate in seasonal challenges to earn additional rewards. The Battle Pass is available for purchase now and will run until the end of the season.",
            author = "John Smith",
            date = "2025-07-01",
            game = "Dota 2",
            topic = "Cosmetic"
        ),
        Article(
            id = 3,
            title = "Smite Introduces New Game Mode: Capture the Flag",
            summary = "Smite has introduced a new game mode called Capture the Flag, where teams compete to capture each other's flags while defending their own.",
            content = "In Capture the Flag, players must work together to strategize and coordinate their efforts to capture the enemy's flag while protecting their own. The mode features unique maps and objectives, providing a fresh experience for Smite players. The mode is now live and available to play.",
            author = "Alice Johnson",
            date = "2024-12-31",
            game = "Smite",
            topic = "Game Modes"
        ),
        Article(
            id = 4,
            title = "Heroes of the Storm Adds New Hero: Nova",
            summary = "Heroes of the Storm has added a new hero named Nova, a stealthy assassin with high burst damage capabilities.",
            content = "Nova is a ranged assassin who excels at picking off key targets with her precision shots. Her abilities allow her to remain hidden and strike from the shadows, making her a formidable opponent. Players can unlock Nova through in-game currency or purchase her directly from the shop.",
            author = "Bob Brown",
            date = "2023-02-01",
            game = "Heroes of the Storm",
            topic = "Game Updates"
        ),
        Article(
            id = 5,
            title = "Vainglory Releases Latest Patch",
            summary = "Vainglory has released a new Patch Update providing key buffs and nerfs to an interesting selection of heroes.",
            content = "Saw and Lyra have seen an interesting buff that should make them more viable in group combat. Whereas Idris and Malene are going to be questionable in single lane focus moving forward. Be on the lookout for how Skye performs under pressure when focusing towers.",
            author = "Brandon Morgan",
            date = "2024-05-19",
            game = "Vainglory",
            topic = "Patch Update"
        )
    )
}
