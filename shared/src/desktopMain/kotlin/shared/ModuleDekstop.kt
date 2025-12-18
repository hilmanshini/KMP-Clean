package shared

import org.koin.core.KoinApplication
import org.koin.dsl.module
import shared.data.firebase.DesktopPlatformConfigRepositoryFirebase
import shared.domain.DesktopPlatformConfigRepository

fun KoinApplication.registerSharedModuleDesktop() = modules(
    module {
        single<DesktopPlatformConfigRepository> { DesktopPlatformConfigRepositoryFirebase() }
    }
)