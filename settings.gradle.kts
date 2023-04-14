rootProject.name = "FLOrganizer"

sourceControl {
    gitRepository(java.net.URI("https://github.com/LucasAlfare/FLListening.git")) {
        producesModule("com.lucasalfare.fllistening:FLListening")
    }

    gitRepository(java.net.URI("https://github.com/LucasAlfare/FLBinary.git")) {
        producesModule("com.lucasalfare.flbinary:FLBinary")
    }
}

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
