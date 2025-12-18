package shared.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kmp.learn.copynews.CopyNews
import kmp.learn.copynews.cache.User
import shared.domain.local_data.LocalDataClient
import shared.domain.local_data.LocalDataClientFactory

class SQlDelightLocalDataClientFactory : LocalDataClientFactory {

    override fun createClient(): LocalDataClient {
        val driver = JdbcSqliteDriver("jdbc:sqlite:launch.db")
        return SQLDelightLocalDataClient(driver, CopyNews(driver))
    }

}

class SQLDelightLocalDataClient(val jdbcSqliteDriver: JdbcSqliteDriver, val copyNews: CopyNews) : LocalDataClient {
    fun test() {
        copyNews.userQueries.countToken().executeAsOne()
    }
}