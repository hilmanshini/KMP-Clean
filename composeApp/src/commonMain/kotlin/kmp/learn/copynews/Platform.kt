package kmp.learn.copynews

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform