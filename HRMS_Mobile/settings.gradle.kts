pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Thêm jcenter() để hỗ trợ thư viện Glide
        jcenter()
    }
}

rootProject.name = "HRMS_Mobile"
include(":app")



