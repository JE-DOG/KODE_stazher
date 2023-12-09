package ru.je_dog.feature.users.list.filter.items

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.filter.filter_items.UsersDepartmentFilterItemDecorator
import ru.je_dog.feature.users.model.UsersDepartmentTab

class UsersDepartmentFilterItemDecoratorTest {

    private val departmentFilterItemDecorator = UsersDepartmentFilterItemDecorator()

    @Before
    fun beforeEach(){
        departmentFilterItemDecorator.updateFilterValue(UsersDepartmentTab.All)
    }

    @Test
    fun `execute with right value`(){
        //ready
        val testUser = UserPresentation.testInstance(
            department = UsersDepartmentTab.Analytics.tag!!
        )
        //call
        departmentFilterItemDecorator.updateFilterValue(UsersDepartmentTab.Analytics)
        val actual = departmentFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertTrue(actual)
    }

    @Test
    fun `execute with wrong value`(){
        //ready
        val testUser = UserPresentation.testInstance(
            department = UsersDepartmentTab.Android.tag!!
        )
        //call
        departmentFilterItemDecorator.updateFilterValue(UsersDepartmentTab.Analytics)
        val actual = departmentFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertFalse(actual)
    }

    @Test
    fun `execute without filters`(){
        //ready
        val testUser = UserPresentation.testInstance(
            department = UsersDepartmentTab.Android.tag!!
        )
        //call
        departmentFilterItemDecorator.updateFilterValue(UsersDepartmentTab.All)
        val actual = departmentFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertTrue(actual)
    }

}