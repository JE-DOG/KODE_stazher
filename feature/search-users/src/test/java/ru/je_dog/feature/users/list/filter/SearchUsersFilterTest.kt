package ru.je_dog.feature.users.list.filter

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation

class SearchUsersFilterTest {

    private val emptyFilterItem = ListFilterItem.Empty<UserPresentation>()
    private val searchUsersFilter = SearchUsersFilter(emptyFilterItem)

    @Before
    fun beforeEach(){
        emptyFilterItem.updateFilterValue(true)
    }

    @Test
    fun `update list without filters`(){
        //ready
        val expect = List(10){
            UserPresentation.testInstance()
        }
        //call
        val actual = searchUsersFilter.setList(expect)
        //compare
        Assert.assertEquals(expect,actual)
    }

    @Test
    fun `update list with impossible filters`(){
        //ready
        val expect = List(10){
            UserPresentation.testInstance()
        }
        //call
        emptyFilterItem.updateFilterValue(false)
        val actual = searchUsersFilter.setList(expect)
        //compare
        Assert.assertNotEquals(expect,actual)
    }

    @Test
    fun `update list with some filters`(){
        //ready
        val list = List(10){
            if (it % 2 == 0)
                UserPresentation.testInstance()
            else
                UserPresentation.testInstance(id = "Something")
        }
        //call
        emptyFilterItem.expectOutput = {
            it.id == "Something"
        }
        val actual = searchUsersFilter.setList(list).size
        val expect = list.size / 2
        //compare
        Assert.assertEquals(expect,actual)
    }

}