package ru.je_dog.feature.users.list.filter.items

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.filter.filter_items.UsersInputFilterItemDecorator

class UsersInputFilterItemDecoratorTest {

    private val inputFilterItemDecorator = UsersInputFilterItemDecorator()

    @Before
    fun beforeEach(){
        inputFilterItemDecorator.updateFilterValue("")
    }

    @Test
    fun `execute with right firstname value`(){
        //ready
        val testUser = UserPresentation.testInstance(
            firstname = "Akhmed"
        )
        val input = "med"
        //call
        inputFilterItemDecorator.updateFilterValue(input)
        val actual = inputFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertTrue(actual)
    }

    @Test
    fun `execute with right lastname value`(){
        //ready
        val testUser = UserPresentation.testInstance(
            lastname = "Magomedov"
        )
        val input = "med"
        //call
        inputFilterItemDecorator.updateFilterValue(input)
        val actual = inputFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertTrue(actual)
    }

    @Test
    fun `execute with right user tag value`(){
        //ready
        val testUser = UserPresentation.testInstance(
            userTag = "su"
        )
        val input = "u"
        //call
        inputFilterItemDecorator.updateFilterValue(input)
        val actual = inputFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertTrue(actual)
    }

    @Test
    fun `execute without right values-lastname,firstname,userTag `(){
        //ready
        val testUser = UserPresentation.testInstance(
            userTag = "su",
            firstname = "Akhmed",
            lastname = "Magomedov"
        )
        val input = "asdfaf"
        //call
        inputFilterItemDecorator.updateFilterValue(input)
        val actual = inputFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertFalse(actual)
    }

    @Test
    fun `execute without filters`(){
        //ready
        val testUser = UserPresentation.testInstance(
            lastname = "SLFj;alfj;sldf",
            firstname = "SLFj;alfj;sldf",
            userTag = "SLFj;alfj;sldf",
        )
        //call
        inputFilterItemDecorator.updateFilterValue("")
        val actual = inputFilterItemDecorator.execute(testUser)
        //compare
        Assert.assertTrue(actual)
    }


}