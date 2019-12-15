package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extentions.TimeUnits
import ru.skillbranch.devintensive.extentions.add
import ru.skillbranch.devintensive.extentions.format
import ru.skillbranch.devintensive.extentions.toUserView
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.Chat
import ru.skillbranch.devintensive.models.User
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user = User( "1")

        println("$user")
    }

    @Test
    fun test_factory(){
        //val user = User.makeUser("John Cena")
        //val user2 = User.makeUser("John Weak")
        val user3 = User.makeUser("null")
        val user2 = user3.copy(id="2", lastName="Cena")
        print("$user3\n$user2")
    }

    @Test
    fun test_decomposition() {
        val user = User.makeUser("John ")

        fun getUserInfo() = user

        val (id, firstName, lastName) = getUserInfo()
        println("$id, $firstName, $lastName")
        println("${user.component1()}, ${user.component2()}, ${user.component3()}")
    }

    @Test
    fun test_copy() {
        val user = User.makeUser("John Doe")
        var user2 = user.copy(lastVisit = Date())
        var user3 = user.copy(lastName = "Weak", lastVisit = Date().add(-2,TimeUnits.SECOND))
        var user4 = user.copy(lastVisit = Date().add(2,TimeUnits.HOUR))

        println("""
            ${user .lastVisit?.format()}
            ${user2.lastVisit?.format()} 
            ${user3.lastVisit?.format()}
            ${user4.lastVisit?.format()}
        """.trimIndent())
    }

    @Test
    fun test_data_mapping(){
        val user = User.makeUser("Михаил Grizzly")
        println(user)
        user.lastVisit = Date().add(-359, TimeUnits.DAY)
        val userView = user.toUserView()

        userView.printMe()

    }

    @Test fun test_abstract_factory(){
        val user: User = User.makeUser("John Doe")
        val textMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any text message", type = "text")
        val imageMessage = BaseMessage.makeMessage(user, Chat("1"), payload = "any image message", type = "image")

        when(textMessage){
        }
    }

    @Test fun test_truncate(){
        val user = User.makeUser("Любовь Морковь")
        val textMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "message message message", type = "text")

    }
}
