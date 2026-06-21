pluginManagement {
    repositories {
        gradlePluginPortal()     // KSP 等插件优先从这里解析
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ShanghaiPrimaryApp"
include(":app")
