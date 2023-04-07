sourceControl {
    gitRepository(java.net.URI("https://github.com/LucasAlfare/FLListening.git")) {
        producesModule("com.lucasalfare.fllistening:FLListening")
    }
}

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "FLOrganizer"

